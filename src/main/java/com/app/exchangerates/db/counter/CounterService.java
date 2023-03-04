package com.app.exchangerates.db.counter;

import java.time.LocalDate;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.app.exchangerates.db.counter.model.Counter;

@Transactional
public interface CounterService {

    Counter getCounterByCurrencyAndDate(String currency, LocalDate date);

    Counter save(Counter counter);

    void incrementCounterByCurrencyAndDate(String currency, LocalDate date);

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    void createCounter(String currency, LocalDate date) throws DataIntegrityViolationException;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    void incrementCounter(String currency, LocalDate date);

}
