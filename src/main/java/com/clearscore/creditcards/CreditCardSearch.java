package com.clearscore.creditcards;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CreditCardSearch {

    @NotBlank(message = "Full Name is mandatory")
    @Schema(name = "Full Name", example = "John Smith", description = "Users full name")
    private String name;

    @Min(0)
    @Max(700)
    @Schema(name = "Credit Score", example = "John 341", description = "Credit score between 0 and 700")
    private Integer creditScore;

    @Min(0)
    @Schema(name = "Salary", example = "28000", description = "Users annual salary")
    private Integer salary;
}
