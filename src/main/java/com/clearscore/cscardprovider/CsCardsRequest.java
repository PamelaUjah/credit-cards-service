package com.clearscore.cscardprovider;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data //generates getters and setters
@Builder //creates instance of Customer
@AllArgsConstructor
public class CsCardsRequest {

    private String name;
    private Integer creditScore;
}
