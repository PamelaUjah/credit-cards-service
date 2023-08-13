package com.clearscore.scoredcardsprovider;

import com.clearscore.creditcards.CreditCardSearch;

import java.util.List;

public interface ScoredCardsService {
    List<ScoredCardsResponse> retrieveCreditCardProducts(ScoredCardsRequest request);

    ScoredCardsRequest buildScoredCardRequest(CreditCardSearch creditCardSearch);
}
