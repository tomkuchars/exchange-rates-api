package com.app.exchangerates.db.counter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.app.exchangerates.db.counter.model.Counter;

import java.time.LocalDate;

@Transactional
public interface CounterRepository extends JpaRepository<Counter, Integer> {

    Counter findByCurrencyAndDate(String currency, LocalDate date);

    @Modifying
    @Query("update Counter c set c.counter = c.counter + 1 where c.currency = :currency and c.date = :date")
    void incrementCounter(@Param("currency") String currency, @Param("date") LocalDate date);

}