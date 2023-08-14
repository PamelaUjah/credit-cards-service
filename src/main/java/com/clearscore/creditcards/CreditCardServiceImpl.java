package com.clearscore.creditcards;

import com.clearscore.cscardprovider.CsCardResponse;
import com.clearscore.cscardprovider.CsCardsRequest;
import com.clearscore.cscardprovider.CsCardsService;
import com.clearscore.exceptions.InvalidParametersException;
import com.clearscore.scoredcardsprovider.ScoredCardsRequest;
import com.clearscore.scoredcardsprovider.ScoredCardsResponse;
import com.clearscore.scoredcardsprovider.ScoredCardsService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class CreditCardServiceImpl implements CreditCardService {

    @Autowired
    private final Validator validator;
    @Autowired
    private ScoredCardsService scoredCardsService;
    @Autowired
    private CsCardsService csCardsService;

    private CsCardsRequest csCardsRequest;

    private ScoredCardsRequest scoredCardsRequest;

    private static final Logger logger = LoggerFactory.getLogger(CreditCardServiceImpl.class);

    @Autowired
    public CreditCardServiceImpl(Validator validator) {
        this.validator = validator;
    }

    public List<CreditCard> retrieveCreditCardRecommendations(CreditCardRequest creditCardRequest) {
        CreditCardSearch creditCardSearch = buildCreditCardSearch(creditCardRequest);

        validateRequest(creditCardSearch);

        buildCardRequest(creditCardSearch);

        List<CsCardResponse> csCardResponses = csCardsService.retrieveCreditCardProducts(csCardsRequest);
        List<ScoredCardsResponse> scoredCardResponses = scoredCardsService.retrieveCreditCardProducts(scoredCardsRequest);

        csCardResponses.forEach(CsCardResponse::setCardScore);
        scoredCardResponses.forEach(ScoredCardsResponse::setCardScore);

        List<CreditCard> creditCardsFromCsProvider = csCardResponses.stream().map(CreditCardServiceImpl::buildCreditCardForCsCardProvider).toList();
        List<CreditCard> creditCardsFromScoredCardsProvider = scoredCardResponses.stream().map(CreditCardServiceImpl::buildCreditCardForScoredCardsProvider).toList();

        return createRecommendations(creditCardsFromCsProvider, creditCardsFromScoredCardsProvider);
    }

    private void buildCardRequest(CreditCardSearch creditCardSearch) {
        csCardsRequest = csCardsService.buildCsCardsRequest(creditCardSearch);
        scoredCardsRequest = scoredCardsService.buildScoredCardRequest(creditCardSearch);
    }

    private List<CreditCard> createRecommendations(List<CreditCard> creditCardsFromCsProvider, List<CreditCard> creditCardsFromScoredCardsProvider) {
        return Stream.concat(creditCardsFromCsProvider
                .stream(), creditCardsFromScoredCardsProvider.stream()).sorted(Comparator.comparing(CreditCard::getCardScore).reversed()).collect(Collectors.toList());
    }

    private static CreditCardSearch buildCreditCardSearch(CreditCardRequest creditCardRequest) {
        return CreditCardSearch.builder()
                .name(creditCardRequest.getName())
                .creditScore(creditCardRequest.getCreditScore())
                .salary(creditCardRequest.getSalary())
                .build();
    }

    private static CreditCard buildCreditCardForCsCardProvider(CsCardResponse csCardResponse) {
        return CreditCard.builder()
                .provider(csCardResponse.getProvider())
                .name((csCardResponse.getName()))
                .apr(csCardResponse.getApr())
                .cardScore(csCardResponse.getCardScore())
                .build();
    }

    private static CreditCard buildCreditCardForScoredCardsProvider(ScoredCardsResponse scoredCardsResponse) {
        return CreditCard.builder()
                .provider(scoredCardsResponse.getProvider())
                .name((scoredCardsResponse.getName()))
                .apr(scoredCardsResponse.getApr())
                .cardScore(scoredCardsResponse.getCardScore())
                .build();
    }

    public void validateRequest(CreditCardSearch creditCardSearch) {
        if (creditCardSearch == null) {
            throw new InvalidParametersException("Error: Request body is null");
        } else {
            Set<ConstraintViolation<CreditCardSearch>> violations = validator.validate(creditCardSearch);

            if (!violations.isEmpty()) {
                for (ConstraintViolation<CreditCardSearch> violation : violations) {
                    logger.error(violation.getMessage());
                }
                throw new InvalidParametersException(String.valueOf(violations));
            }
        }
    }
}
