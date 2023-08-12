package com.clearscore.creditcards;

import java.util.ArrayList;

public interface CreditCardService {
    ArrayList<CreditCard> retrieveCreditCardRecommendations(CreditCardRequest creditCardRequest);
}
