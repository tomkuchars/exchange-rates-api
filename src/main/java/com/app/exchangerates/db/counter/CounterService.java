package com.app.exchangerates.db.counter;

import java.time.LocalDate;

import com.app.exchangerates.db.counter.model.Counter;

public interface CounterService {

    Counter getCounterByCurrencyAndDate(String currency, LocalDate date);

    Counter save(Counter counter);

    Counter incrementCounterByCurrencyAndDate(String currency, LocalDate date);

}
