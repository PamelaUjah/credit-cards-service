package com.clearscore.creditcards;

import com.clearscore.exceptions.InvalidParametersException;
import com.clearscore.exceptions.ServerException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<CreditCard> retrieveCreditCardRecommendations(@RequestBody CreditCardRequest creditCardRequest) throws ServerException {
        try {
            log.info("Get Customer Credit Card Recommendations {}", creditCardRequest);
            return creditCardService.retrieveCreditCardRecommendations(creditCardRequest);
        } catch (Exception exception) {
            throw new InvalidParametersException(exception);
        }
    }
}
