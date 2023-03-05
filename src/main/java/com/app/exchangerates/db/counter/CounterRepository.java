package com.app.exchangerates.db.counter;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.app.exchangerates.db.counter.model.Counter;
import com.app.exchangerates.db.counter.model.Counter_;

@Transactional
public interface CounterRepository extends JpaRepository<Counter, Integer>, CounterCriteriaRepository {

    Counter findByCurrencyAndDate(String currency, LocalDate date);

    @Modifying
    @Query("update Counter c set c." + Counter_.COUNTER + " = c." + Counter_.COUNTER + " + 1 where c."
            + Counter_.CURRENCY + "=:" + Counter_.CURRENCY + " and c." + Counter_.DATE + "=:" + Counter_.DATE)
    void incrementCounter(@Param(Counter_.CURRENCY) String currency, @Param(Counter_.DATE) LocalDate date);

}