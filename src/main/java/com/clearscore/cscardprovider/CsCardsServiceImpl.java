package com.clearscore.cscardprovider;

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
import org.springframework.web.client.*;

import java.util.List;

@Slf4j
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
            log.info("Cs Card provider has successfully returned credit cards");
            return response.getBody();
        } catch (JsonProcessingException exception) {
            log.error("Issue converting to Json Object when requesting from Cs Card Provider", exception);
            throw new RuntimeException(exception);
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
