package com.clearscore.cscardprovider;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.math3.util.Precision;

import java.math.RoundingMode;

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
        this.cardScore = Precision.round(number * eligibilityScaled, 3, 1);
    }
}
