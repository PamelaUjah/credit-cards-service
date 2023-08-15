package com.clearscore.creditcards;

import com.clearscore.cscardprovider.CsCardResponse;
import com.clearscore.cscardprovider.CsCardsRequest;
import com.clearscore.cscardprovider.CsCardsServiceImpl;
import com.clearscore.scoredcardsprovider.ScoredCardsRequest;
import com.clearscore.scoredcardsprovider.ScoredCardsResponse;
import com.clearscore.scoredcardsprovider.ScoredCardsServiceImpl;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreditCardServiceImplTest {

    @Mock
    private CreditCardRequest creditCardRequest;

    @Mock
    private CreditCardSearch creditCardSearch;

    @Mock
    private CsCardsRequest csCardsRequest;

    @Mock
    private ScoredCardsRequest scoredCardsRequest;

    @Mock
    private CsCardsServiceImpl csCardsService;

    @Mock
    private ScoredCardsServiceImpl scoredCardsService;

    @Mock
    private CsCardResponse csCardResponse1;

    @Mock
    private CsCardResponse csCardResponse2;

    @Mock
    private CsCardResponse csCardResponse3;

    @Mock
    private ScoredCardsResponse scoredCardsResponse1;

    @Mock
    private Validator validator;

    @InjectMocks
    private CreditCardServiceImpl creditCardService;

    private List<CreditCard> creditCardList;

    private Exception exception;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        reset(csCardsService, scoredCardsService);
    }

    @Test
    @DisplayName("Given valid card request is received, when requests are sent to external providers, then successful response is received")
    void testRetrievingValidRequestToExternalApi() {
        givenValidCardRequest();
        givenValidCreditCardSearch();
        givenValidCsCardProviderRequest();
        givenValidScoreCardProviderRequest();
        givenValidCsCardsResponse();
        givenValidScoredCardsResponse();

        whenCreatingRecommendationsFromMultipleProviders();

        thenCreditCardListIsNotEmpty();

    }

    private void givenValidScoreCardProviderRequest() {
        scoredCardsRequest = new ScoredCardsRequest("Samantha Jenkins", 600, 70000);
        when(scoredCardsService.buildScoredCardRequest(creditCardSearch)).thenReturn(scoredCardsRequest);
    }

    private void givenValidCsCardProviderRequest() {
        csCardsRequest = new CsCardsRequest("Samantha Jenkins", 600);
        when(csCardsService.buildCsCardsRequest(creditCardSearch)).thenReturn(csCardsRequest);
    }

    private void givenValidCreditCardSearch() {
        creditCardSearch = new CreditCardSearch("Samantha Jenkins", 600, 70000);
    }

    private void givenValidCsCardsResponse() {
        csCardResponse1 = new CsCardResponse("CsCards", "SuperSaver Card", 21.6, 6.3, null);
        csCardResponse2 = new CsCardResponse("CsCards", "SuperSpender Card", 19.2, 5.0, null);
        csCardResponse3 = new CsCardResponse("CsCards", "SuperSpender Card", 15.2, 8.0, null);

        List<CsCardResponse> list = new ArrayList<>();
        list.add(csCardResponse1);
        list.add(csCardResponse2);
        list.add(csCardResponse3);

        when(csCardsService.buildCsCardsRequest(creditCardSearch)).thenReturn(csCardsRequest);
        when(csCardsService.retrieveCreditCardProducts(csCardsRequest)).thenReturn(list);
    }

    private void givenValidCardRequest() {
        creditCardRequest = new CreditCardRequest("Samantha Jenkins", 600, 70000);
    }

    private void givenValidScoredCardsResponse() {
        scoredCardsResponse1 = new ScoredCardsResponse("Scored Cards", "ScoredCard Card", 31.5, 2.3, null);

        List<ScoredCardsResponse> list = new ArrayList<>();
        list.add(scoredCardsResponse1);

        when(scoredCardsService.buildScoredCardRequest(creditCardSearch)).thenReturn(scoredCardsRequest);
        when(scoredCardsService.retrieveCreditCardProducts(scoredCardsRequest)).thenReturn(list);
    }

    private void whenCreatingRecommendationsFromMultipleProviders() {
        try {
            creditCardList = creditCardService.retrieveCreditCardRecommendations(creditCardRequest);
        } catch (Exception e) {
            exception = e;
        }
    }

    private void thenCreditCardListIsNotEmpty() {
        assertThat(exception).isNull();
        assertThat(creditCardList).size().isEqualTo(4);
    }

    @Test
    void validateRequest() {
    }

}