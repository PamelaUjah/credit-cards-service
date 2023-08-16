package com.clearscore.cscardprovider;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CsCardsRequest {

    private String name;

    private Integer creditScore;
}
