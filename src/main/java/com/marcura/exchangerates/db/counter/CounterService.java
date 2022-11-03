package com.marcura.exchangerates.db.counter;

import com.marcura.exchangerates.db.counter.model.Counter;

import java.time.LocalDate;

public interface CounterService {

    Counter getCounterByCurrencyAndDate(String currency, LocalDate date);

    Counter save(Counter counter);

    Counter incrementCounterByCurrencyAndDate(String currency, LocalDate date);

}
