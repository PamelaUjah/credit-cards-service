package com.clearscore.cscardprovider;

import com.clearscore.configuration.CreditCardsConfig;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;


@ExtendWith(MockitoExtension.class)
class CsCardsServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private CreditCardsConfig creditCardsConfig;

    @Mock
    private CsCardsRequest csCardsRequest;

    @InjectMocks
    private CsCardsService csCardsService = new CsCardsServiceImpl(creditCardsConfig, restTemplate);

    @Test
    void retrieveCreditCardProducts() {
        // Mockito.when(restTemplate.postForObject(creditCardsConfig.getCsCards(), )).thenReturn()
    }

    private void givenCsCardsRequest() {
        csCardsRequest = new CsCardsRequest("Michael Smith", 550);
    }
}