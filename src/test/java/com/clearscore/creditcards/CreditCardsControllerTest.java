package com.clearscore.creditcards;

import com.clearscore.CreditCardsServiceApplication;
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

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc()
class CreditCardsControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Mock
    private CreditCardService creditCardService;

    @Autowired
    private MockMvc mockMvc;

    private ArrayList<CreditCard> creditCards;

    @Mock
    private CreditCardRequest creditCardRequest;

    @Test
    @DisplayName("Given valid credit card search is made, when recommended credit cards are returned, successful response is provided ")
    void testRetrieveCreditCardRecommendations() {
        givenCreditCardSearch();

        whenCreditCardsAreReturned();

        thenSuccessfulResponse();

    }

    private void thenSuccessfulResponse() {
        try {
            String requestBody = objectMapper.writeValueAsString(creditCardRequest);
            this.mockMvc.perform(post("https://www.clearscore.com/api/v1/creditcards")
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

    private void givenCreditCardSearch() {
        creditCardRequest = new CreditCardRequest("John Smith", 400, 50000);
    }
}