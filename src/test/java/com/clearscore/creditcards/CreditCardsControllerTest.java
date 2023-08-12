package com.clearscore.creditcards;

import com.clearscore.cscardprovider.CsCardResponse;
import com.clearscore.cscardprovider.CsCardsRequest;
import com.clearscore.cscardprovider.CsCardsService;
import com.clearscore.exceptions.InvalidParametersException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc()
class CreditCardsControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String URL = "https://www.clearscore.com/api/v1/creditcards";

    @Mock
    private CreditCardService creditCardService;

    @Mock
    private CsCardsService csCardsService;

    @Autowired
    private MockMvc mockMvc;

    private ArrayList<CreditCard> creditCards;

    @Mock
    private CsCardsRequest csCardsRequest;

    @Mock
    private CreditCardSearch creditCardSearch;

    @Mock
    private CsCardResponse csCardResponse1;

    @Mock
    private CsCardResponse csCardResponse2;

    @Mock
    private CreditCardRequest creditCardRequest;

    @BeforeEach
    void beforeAll() {
        reset(creditCardRequest, creditCardService);
    }

    //todo: test not found

    @Test
    @DisplayName("Given valid credit card search is made, when recommended credit cards are returned, successful response is provided ")
    void testRetrieveCreditCardRecommendations() {
        givenCreditCardRequest();
        givenValidCreditCardSearch();
        givenValidCsCardsRequest();
        givenValidCsCardsResponse();

        whenCsCardsProviderReturnsSuccessfulResponse();
        whenCreditCardsAreReturned();

        thenSuccessfulResponse();
    }

    private void whenCsCardsProviderReturnsSuccessfulResponse() {
        when(csCardsService.buildCSCardsRequest(creditCardSearch)).thenReturn(csCardsRequest);
    }

    @Test
    @DisplayName("Given invalid credit card search is made, when recommended credit cards is not returned, unsuccessful response ")
    void testInvalidRequest() {
        givenInvalidCreditCardSearch();

        whenBadRequest();

        thenBadRequestResponse();
    }

    private void thenBadRequestResponse() {
        try {
            String requestBody = objectMapper.writeValueAsString(creditCardRequest);
            this.mockMvc.perform(post(URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isBadRequest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void thenSuccessfulResponse() {
        try {
            String requestBody = objectMapper.writeValueAsString(creditCardRequest);
            this.mockMvc.perform(post(URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void whenCreditCardsAreReturned() {
        creditCards = new ArrayList<>();
        creditCards.add(new CreditCard("ScoredCards", "ScoredCard Builder", 19.4, 0.212));
        creditCards.add(new CreditCard("CSCards", "SuperSaver Card", 21.4, 0.137));
        creditCards.add(new CreditCard("CSCards", "SuperSpender Card", 19.2, 0.135));

        when(creditCardService.retrieveCreditCardRecommendations(creditCardRequest)).thenReturn(creditCards);
    }

    private void whenBadRequest() {
        when(creditCardService.retrieveCreditCardRecommendations(null)).thenThrow(InvalidParametersException.class);
    }

    private void givenCreditCardRequest() {
        creditCardRequest = new CreditCardRequest("John Smith", 400, 50000);
    }

    private void givenValidCsCardsRequest() {
        csCardsRequest = new CsCardsRequest("John Smith", 400);
    }

    private void givenValidCreditCardSearch() {
        creditCardSearch = new CreditCardSearch("John Smith", 400, 50000);
    }


    private void givenValidCsCardsResponse() {
        csCardResponse1 = new CsCardResponse("SuperSaver Card", 21.6, 6.3);
        csCardResponse2 = new CsCardResponse("SuperSpender Card", 19.2, 5.0);
        //String response1 = objectMapper.writeValueAsString(csCardResponse1);
        //String response2 = objectMapper.writeValueAsString(csCardResponse2);

        List<CsCardResponse> list = new ArrayList<>();
        list.add(csCardResponse1);
        list.add(csCardResponse2);
        when(csCardsService.retrieveCreditCardProducts(csCardsRequest)).thenReturn(list);

        //csCardResponseList = new CsCardResponseList();
        //csCardResponseList.setCsCardResponses(list);

    }

    private void givenInvalidCreditCardSearch() {
        creditCardRequest = null;
    }
}