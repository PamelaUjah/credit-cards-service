package com.clearscore.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
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
        return new RestTemplate();
    }
}
