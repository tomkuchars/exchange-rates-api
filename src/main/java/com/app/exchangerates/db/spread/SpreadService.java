package com.app.exchangerates.db.spread;

import com.app.exchangerates.db.spread.model.Spread;

public interface SpreadService {

    Spread getSpreadByCurrency(String currency);

}
