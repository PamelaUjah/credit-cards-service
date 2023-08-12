package com.clearscore.creditcards;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Slf4j
@RestController
@RequestMapping("api/v1/creditcards")
public class CreditCardsController {

    private final CreditCardService creditCardService;
    @Autowired
    public CreditCardsController(CreditCardService creditCardService) {
        this.creditCardService = creditCardService;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK, reason = "A sorted list of credit cards the user is eligible to apply for.")
    public ArrayList<CreditCard> retrieveCreditCardRecommendations(@RequestBody CreditCardRequest creditCardRequest) {
        log.info("Get Customer Credit Card Recommendations {}", creditCardRequest);
        return creditCardService.retrieveCreditCardRecommendations(creditCardRequest);
    }
}
