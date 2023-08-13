package com.clearscore.scoredcardsprovider;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //generates getters and setters
@Builder //creates instance of Customer
@AllArgsConstructor
@NoArgsConstructor
public class ScoredCardsResponse {

    private String card;
    private Double apr;
    private Double approvalRating;

}
