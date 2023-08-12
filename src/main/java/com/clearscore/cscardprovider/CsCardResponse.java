package com.clearscore.cscardprovider;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //generates getters and setters
@Builder //creates instance of Customer
@AllArgsConstructor
@NoArgsConstructor
public class CsCardResponse {

    private String cardName;
    private Double apr;
    private Double eligibility;

}
