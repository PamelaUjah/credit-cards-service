package com.clearscore.creditcards;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //generates getters and setters
@Builder //creates instance of CreditCardRequest
@NoArgsConstructor
@AllArgsConstructor
public class CreditCardRequest {
    private String name;
    private Integer creditScore;
    private Integer salary;
}
