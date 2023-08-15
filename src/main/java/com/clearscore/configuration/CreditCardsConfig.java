package com.clearscore.configuration;

import com.clearscore.utils.RestTemplateResponseErrorHandler;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@ConfigurationProperties(prefix = "external-services")
@Data
public class CreditCardsConfig {

    private String csCards;

    private String scoredCards;

    private String userAgent;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder()
                .errorHandler(new RestTemplateResponseErrorHandler())
                //.setConnectTimeout(Duration.ofSeconds(20)) --> include timeout requirement as required
                //.setReadTimeout(Duration.ofSeconds(20))
                .build();
    }
}
