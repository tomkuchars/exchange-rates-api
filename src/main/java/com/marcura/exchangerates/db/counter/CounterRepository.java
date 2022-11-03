package com.marcura.exchangerates.db.counter;

import com.marcura.exchangerates.db.counter.model.Counter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Repository
@Transactional
public interface CounterRepository extends JpaRepository<Counter, Integer> {

    Counter findByCurrencyAndDate(String currency, LocalDate date);

}