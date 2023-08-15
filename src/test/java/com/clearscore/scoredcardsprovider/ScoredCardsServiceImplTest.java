package com.clearscore.scoredcardsprovider;

import com.clearscore.configuration.CreditCardsConfig;
import com.clearscore.exceptions.InvalidParametersException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ScoredCardsServiceImplTest {

    public static final String CS_CARD_URL = "https://app.clearscore.com/api/global/backend-tech-test/v1/cards";
    public static final String SCORED_CARDS_URL = "https://app.clearscore.com/api/global/backend-tech-test/v2/creditcards";
    public static final String USER_AGENT = "Mozilla/5.0 Firefox/26.0";
    @Autowired
    private RestTemplate restTemplate;

    @Mock
    private CreditCardsConfig creditCardsConfig;

    @Mock
    private ScoredCardsRequest scoredCardsRequest;

    @Mock
    private ScoredCardsResponse scoredCardsResponse;

    @InjectMocks
    private ScoredCardsServiceImpl scoredCardsService;

    private List<ScoredCardsResponse> scoredCardsResponses;

    private Exception exception;

    @BeforeEach
    void setUp() {
        creditCardsConfig = new CreditCardsConfig();
        restTemplate = new RestTemplate();
        scoredCardsService = new ScoredCardsServiceImpl(creditCardsConfig, restTemplate);
    }

    @Test
    @DisplayName("Given request to Scored Cards provider for a user with cards available, when service is called, then credit cards are successfully returned")
    void testRetrieveCreditCardProducts() {
        givenSuccessfulScoredCardsRequest();
        givenConfiguration();

        whenCreditCardsAreRetrieved();

        thenSuccessfulResponse();
    }

    @Test
    @DisplayName("Given bad request to provider, when service is called, then exception is thrown")
    void testBadRequest() {
        givenBadCsCardsRequest();
        givenConfiguration();

        whenCreditCardsAreRetrieved();

        thenBadRequestResponse();
    }

    private void thenBadRequestResponse() {
        assertThat(exception).isInstanceOf(InvalidParametersException.class);
        assertThat(exception).hasMessage("The request contained invalid parameters");
    }

    private void thenSuccessfulResponse() {
        assertThat(scoredCardsResponses.size()).isEqualTo(1);
    }

    private void givenConfiguration() {
        creditCardsConfig.setCsCards(CS_CARD_URL);
        creditCardsConfig.setScoredCards(SCORED_CARDS_URL);
        creditCardsConfig.setUserAgent(USER_AGENT);
    }

    private void whenCreditCardsAreRetrieved() {
        try {
            scoredCardsResponses = scoredCardsService.retrieveCreditCardProducts(scoredCardsRequest);
        } catch (Exception e) {
            exception = e;
        }
    }

    private void givenSuccessfulScoredCardsRequest() {
        scoredCardsRequest = new ScoredCardsRequest("Michael Smith", 550, 70000);
    }

    private void givenBadCsCardsRequest() {
        scoredCardsRequest = new ScoredCardsRequest(null, null, null);
    }

    //todo: add testcases to test other types of exceptions
}