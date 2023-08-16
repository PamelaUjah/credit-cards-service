package com.clearscore.scoredcardsprovider;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ScoredCardsRequest {

    private String name;

    private Integer score;

    private Integer salary;
}
