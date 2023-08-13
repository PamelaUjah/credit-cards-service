package com.clearscore.cscardprovider;

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

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CsCardsServiceImplTest {

    public static final String CS_CARDS_URL = "https://app.clearscore.com/api/global/backend-tech-test/v1/cards";
    public static final String SCORED_CARDS_URL = "https://app.clearscore.com/api/global/backend-tech-test/v2/creditcards";
    public static final String USER_AGENT = "Mozilla/5.0 Firefox/26.0";
    @Autowired
    private RestTemplate restTemplate;

    @Mock
    private CreditCardsConfig creditCardsConfig;

    @Mock
    private CsCardsRequest csCardsRequest;

    @Mock
    private CsCardResponse csCardResponse;

    @InjectMocks
    private CsCardsServiceImpl csCardsService;

    private List<CsCardResponse> csCardResponses;

    private Exception exception;

    @BeforeEach
    void setUp() {
        creditCardsConfig = new CreditCardsConfig();
        restTemplate = new RestTemplate();
        csCardsService = new CsCardsServiceImpl(creditCardsConfig, restTemplate);
    }

    @Test
    @DisplayName("Given request to CsCard provider for a user with cards available, when service is called, then credit cards are successfully returned")
    void testRetrieveCreditCardProducts() {
        givenSuccessfulCsCardsRequest();
        givenConfiguration();
        givenAvailableCards();

        whenCreditCardsAreRetrieved();

        thenSuccessfulResponse();
    }

    @Test
    @DisplayName("Given bad request to provider, when service is called, then exception is thrown")
    void testBadRequest() {
        givenBadCsCardsRequest();
        givenConfiguration();
        givenAvailableCards();

        whenCreditCardsAreRetrieved();

        thenBadRequestResponse();
    }

    private void thenBadRequestResponse() {
        assertThat(exception).isInstanceOf(InvalidParametersException.class);
        assertThat(exception).hasMessage("The request contained invalid parameters");
    }

    private void thenSuccessfulResponse() {
        assertThat(csCardResponses.size()).isEqualTo(2);
    }

    private void givenAvailableCards() {
        CsCardResponse csCardResponse1 = new CsCardResponse("CsCards", "SuperSaver Card", 21.6, 6.3, null);
        CsCardResponse csCardResponse2 = new CsCardResponse("CsCards", "SuperSpender Card", 19.2, 5.0, null);

        List<CsCardResponse> list = new ArrayList<>();
        list.add(csCardResponse1);
        list.add(csCardResponse2);
    }

    private void givenConfiguration() {
        creditCardsConfig.setCsCards(CS_CARDS_URL);
        creditCardsConfig.setScoredCards(SCORED_CARDS_URL);
        creditCardsConfig.setUserAgent(USER_AGENT);
    }

    private void whenCreditCardsAreRetrieved() {
        try {
            csCardResponses = csCardsService.retrieveCreditCardProducts(csCardsRequest);
        } catch (Exception e) {
            exception = e;
        }
    }

    private void givenSuccessfulCsCardsRequest() {
        csCardsRequest = new CsCardsRequest("Michael Smith", 550);
    }

    private void givenBadCsCardsRequest() {
        csCardsRequest = new CsCardsRequest(null, null);
    }

    //todo: add testcases to test other types of exceptions
}