package com.clearscore.cscardprovider;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //generates getters and setters
@Builder //creates instance of Customer
@AllArgsConstructor
@NoArgsConstructor
public class CsCardResponse {

    private String provider = "CSCards";

    @JsonAlias("cardName")
    private String name;

    private Double apr;

    private Double eligibility;

    private Double cardScore;

    public void setCardScore() {
        Double number = Math.pow((1 / apr), 2);
        Double eligibilityScaled = eligibility * 10;
        this.cardScore = number * eligibilityScaled;
    }
}
