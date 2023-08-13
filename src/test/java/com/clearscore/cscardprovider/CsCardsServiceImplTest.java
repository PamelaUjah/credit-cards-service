package com.clearscore.cscardprovider;

import com.clearscore.configuration.CreditCardsConfig;

import com.clearscore.exceptions.InvalidParametersException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureMockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockRestServiceServer
class CsCardsServiceImplTest {

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

    private List<CsCardResponse> mockResponse;

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
        assertThat(mockResponse.size()).isEqualTo(2);
    }

    private void givenAvailableCards() {
        CsCardResponse csCardResponse1 = new CsCardResponse("SuperSaver Card", 21.6, 6.3);
        CsCardResponse csCardResponse2 = new CsCardResponse("SuperSpender Card", 19.2, 5.0);

        List<CsCardResponse> list = new ArrayList<>();
        list.add(csCardResponse1);
        list.add(csCardResponse2);
    }

    private void givenConfiguration() {
        creditCardsConfig.setCsCards("https://app.clearscore.com/api/global/backend-tech-test/v1/cards");
        creditCardsConfig.setScoredCards("https://app.clearscore.com/api/global/backend-tech-test/v2/creditcards");
        creditCardsConfig.setUserAgent("Mozilla/5.0 Firefox/26.0");
    }

    private void whenCreditCardsAreRetrieved() {
        try {
            mockResponse = csCardsService.retrieveCreditCardProducts(csCardsRequest);
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

}