package com.clearscore.creditcards;

import com.clearscore.cscardprovider.CsCardResponse;
import com.clearscore.cscardprovider.CsCardsRequest;
import com.clearscore.cscardprovider.CsCardsService;
import com.clearscore.exceptions.InvalidParametersException;
import com.clearscore.scoredcardsprovider.ScoredCardsService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class CreditCardServiceImpl implements CreditCardService {

    @Autowired
    private final Validator validator;
    @Autowired
    private ScoredCardsService scoredCardsService;
    @Autowired
    private CsCardsService csCardsService;

    private static final Logger logger = LoggerFactory.getLogger(CreditCardServiceImpl.class);

    @Autowired
    public CreditCardServiceImpl(Validator validator) {
        this.validator = validator;
    }

    public ArrayList<CreditCard> retrieveCreditCardRecommendations(CreditCardRequest creditCardRequest) {
        CreditCardSearch creditCardSearch = buildCreditCardSearch(creditCardRequest);

        validateRequest(creditCardSearch);

        CsCardsRequest request = csCardsService.buildCSCardsRequest(creditCardSearch);

        List<CsCardResponse> csCardResponses = csCardsService.retrieveCreditCardProducts(request);
        return null;
    }

    private static CreditCardSearch buildCreditCardSearch(CreditCardRequest creditCardRequest) {
        return CreditCardSearch.builder()
                .name(creditCardRequest.getName())
                .creditScore(creditCardRequest.getCreditScore())
                .salary(creditCardRequest.getSalary())
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

//todo: add two methods to send requests to providers (reference method in individual services)
//todo: method to collate response into response back to user
}
