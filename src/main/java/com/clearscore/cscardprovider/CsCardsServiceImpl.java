package com.clearscore.cscardprovider;

import com.clearscore.configuration.CreditCardsConfig;
import com.clearscore.creditcards.CreditCardSearch;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class CsCardsServiceImpl implements CsCardsService {

    private CreditCardsConfig creditCardsConfig;
    private final RestTemplate restTemplate;

    @Override
    public List<CsCardResponse> retrieveCreditCardProducts(CsCardsRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonRequest = mapper.writeValueAsString(request);
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.USER_AGENT, creditCardsConfig.getUserAgent());
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> httpRequest = new HttpEntity<>(jsonRequest, headers);
            ResponseEntity<List<CsCardResponse>> response = restTemplate.exchange(creditCardsConfig.getCsCards(), HttpMethod.POST, httpRequest,
                    new ParameterizedTypeReference<>() {});
            return response.getBody();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public CsCardsRequest buildCSCardsRequest(CreditCardSearch creditCardSearch) {
        return CsCardsRequest.builder()
                .name(creditCardSearch.getName())
                .creditScore(creditCardSearch.getCreditScore())
                .build();
    }
}
