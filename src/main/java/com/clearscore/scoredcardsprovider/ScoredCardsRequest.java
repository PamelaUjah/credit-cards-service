package com.clearscore.scoredcardsprovider;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data //generates getters and setters
@Builder //creates instance of Customer
@AllArgsConstructor
public class ScoredCardsRequest {

    private String name;

    private Integer score;

    private Integer salary;
}
