package com.clearscore.cscardprovider;

import com.clearscore.creditcards.CreditCardSearch;

import java.util.ArrayList;
import java.util.List;

public interface CsCardsService {

    List<CsCardResponse> retrieveCreditCardProducts(CsCardsRequest request);

    CsCardsRequest buildCSCardsRequest(CreditCardSearch creditCardSearch);
}
