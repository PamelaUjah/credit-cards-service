package com.clearscore.scoredcardsprovider;

import com.clearscore.config.CreditCardsConfig;
import com.clearscore.creditcards.CreditCardSearch;
import com.clearscore.utils.RestTemplateResponseErrorHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
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
            log.info("Scored Card provider has successfully returned credit cards");
            return response.getBody();
        } catch (JsonProcessingException exception) {
            log.error("Issue converting to Json Object when requesting from Cs Card Provider", exception);
            throw new RuntimeException(exception);
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
