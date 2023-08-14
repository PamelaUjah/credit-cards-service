package com.clearscore.scoredcardsprovider;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.math3.util.Precision;

@Data //generates getters and setters
@Builder //creates instance of Customer
@AllArgsConstructor
@NoArgsConstructor
public class ScoredCardsResponse {

    private String provider = "ScoredCards";

    @JsonAlias("card")
    private String name;

    private Double apr;

    @JsonAlias("approvalRating")
    private Double eligibility;

    private Double cardScore;

    public void setCardScore() {
        Double number = Math.pow((1 / apr), 2);
        Double eligibilityScaled = eligibility * 100;
        this.cardScore = Precision.round(number * eligibilityScaled, 3, 1);
    }
}
