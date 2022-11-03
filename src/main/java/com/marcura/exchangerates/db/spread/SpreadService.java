package com.marcura.exchangerates.db.spread;

import com.marcura.exchangerates.db.spread.model.Spread;

public interface SpreadService {

    Spread getSpreadByCurrency(String currency);

}
