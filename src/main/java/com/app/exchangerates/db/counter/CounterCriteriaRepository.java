package com.app.exchangerates.db.counter;

import java.time.LocalDate;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CounterCriteriaRepository {

    void incrementCounterUsingCriteria(String currency, LocalDate date);

}