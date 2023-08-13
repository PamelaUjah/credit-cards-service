package com.clearscore.creditcards;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class CreditCard {

    private String provider;

    private String name;

    private Double apr;

    private Double cardScore;
}
