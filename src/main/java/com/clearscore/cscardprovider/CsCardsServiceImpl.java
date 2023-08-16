package com.clearscore.cscardprovider;

import com.clearscore.config.CreditCardsConfig;
import com.clearscore.creditcards.CreditCardSearch;
import com.clearscore.utils.RestTemplateResponseErrorHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.*;

import java.util.List;

@Service
public class CsCardsServiceImpl implements CsCardsService {

    private CreditCardsConfig creditCardsConfig;

    private RestTemplate restTemplate;

    @Autowired
    public CsCardsServiceImpl(CreditCardsConfig creditCardsConfig, RestTemplate restTemplate) {
        this.creditCardsConfig = creditCardsConfig;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<CsCardResponse> retrieveCreditCardProducts(CsCardsRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonRequest = mapper.writeValueAsString(request);

            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.USER_AGENT, creditCardsConfig.getUserAgent());
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> httpRequest = new HttpEntity<>(jsonRequest, headers);
            restTemplate.setErrorHandler(new RestTemplateResponseErrorHandler());

            ResponseEntity<List<CsCardResponse>> response = restTemplate.exchange(creditCardsConfig.getCsCards(),
                    HttpMethod.POST, httpRequest, new ParameterizedTypeReference<>() {});
            return response.getBody();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public CsCardsRequest buildCsCardsRequest(CreditCardSearch creditCardSearch) {
        return CsCardsRequest.builder()
                .name(creditCardSearch.getName())
                .creditScore(creditCardSearch.getCreditScore())
                .build();
    }
}
