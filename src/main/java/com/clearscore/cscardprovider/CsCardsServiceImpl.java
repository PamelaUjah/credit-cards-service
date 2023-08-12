package com.clearscore.cscardprovider;

import com.clearscore.configuration.CreditCardsConfig;
import com.clearscore.creditcards.CreditCardSearch;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class CsCardsServiceImpl implements CsCardsService {

    private CreditCardsConfig creditCardsConfig;
    private final RestTemplate restTemplate;

    @Override
    public CsCardResponse retrieveCreditCardProducts(CsCardsRequest request) {
        CsCardResponse csCardResponse = new CsCardResponse();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.USER_AGENT, creditCardsConfig.getUserAgent());
            HttpEntity<String> httpRequest = new HttpEntity<String>(request.toString(), headers);
            csCardResponse = restTemplate.postForObject(creditCardsConfig.getCsCards(), httpRequest, CsCardResponse.class);
        } catch (Exception exception) {
            //throw exception
        }
        return csCardResponse;
    }

    @Override
    public CsCardsRequest buildCSCardsRequest(CreditCardSearch creditCardSearch) {
        return CsCardsRequest.builder()
                .name(creditCardSearch.getName())
                .creditScore(creditCardSearch.getCreditScore())
                .build();
    }
}
