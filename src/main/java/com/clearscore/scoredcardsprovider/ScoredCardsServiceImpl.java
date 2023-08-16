package com.clearscore.scoredcardsprovider;

import com.clearscore.config.CreditCardsConfig;
import com.clearscore.creditcards.CreditCardSearch;
import com.clearscore.utils.RestTemplateResponseErrorHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ScoredCardsServiceImpl implements ScoredCardsService {

    private CreditCardsConfig creditCardsConfig;

    private RestTemplate restTemplate;

    @Autowired
    public ScoredCardsServiceImpl(CreditCardsConfig creditCardsConfig, RestTemplate restTemplate) {
        this.creditCardsConfig = creditCardsConfig;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<ScoredCardsResponse> retrieveCreditCardProducts(ScoredCardsRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonRequest = mapper.writeValueAsString(request);

            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.USER_AGENT, creditCardsConfig.getUserAgent());
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> httpRequest = new HttpEntity<>(jsonRequest, headers);
            restTemplate.setErrorHandler(new RestTemplateResponseErrorHandler());

            ResponseEntity<List<ScoredCardsResponse>> response = restTemplate.exchange(creditCardsConfig.getScoredCards(), HttpMethod.POST, httpRequest,
                    new ParameterizedTypeReference<>() {});
            return response.getBody();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ScoredCardsRequest buildScoredCardRequest(CreditCardSearch creditCardSearch) {
        return ScoredCardsRequest.builder()
                .name(creditCardSearch.getName())
                .score(creditCardSearch.getCreditScore())
                .salary(creditCardSearch.getSalary())
                .build();
    }
}
