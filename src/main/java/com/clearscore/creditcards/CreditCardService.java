package com.clearscore.creditcards;

import java.util.List;

public interface CreditCardService {

    List<CreditCard> retrieveCreditCardRecommendations(CreditCardRequest creditCardRequest);
}
