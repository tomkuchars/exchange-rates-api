package com.app.exchangerates.db.rates;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.app.exchangerates.db.rates.model.Rates;

import java.time.LocalDate;
import java.util.List;

@Transactional
public interface RatesRepository extends JpaRepository<Rates, Integer> {

    Rates findTopByBaseAndCurrencyAndDateOrderByIdDesc(String base, String currency, LocalDate date);

    Rates findTopByBaseAndCurrencyOrderByDateDescIdDesc(String base, String currency);

    List<Rates> findByDate(LocalDate date);

}