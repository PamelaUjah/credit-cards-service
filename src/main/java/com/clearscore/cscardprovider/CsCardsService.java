package com.clearscore.cscardprovider;

import com.clearscore.creditcards.CreditCardSearch;

public interface CsCardsService {

    CsCardResponse retrieveCreditCardProducts(CsCardsRequest request);

    CsCardsRequest buildCSCardsRequest(CreditCardSearch creditCardSearch);
}
