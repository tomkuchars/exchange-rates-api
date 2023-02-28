package com.app.exchangerates.db.counter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.app.exchangerates.db.counter.model.Counter;

import java.time.LocalDate;

@Repository
@Transactional
public interface CounterRepository extends JpaRepository<Counter, Integer> {

    Counter findByCurrencyAndDate(String currency, LocalDate date);

}