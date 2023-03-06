package com.app.exchangerates.db.rates;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import com.app.exchangerates.db.rates.model.Rates;

@Transactional
public interface RatesRepository extends PagingAndSortingRepository<Rates, Integer> {

    Rates findTopByBaseAndCurrencyAndDateOrderByIdDesc(String base, String currency, LocalDate date);

    Rates findTopByBaseAndCurrencyOrderByDateDescIdDesc(String base, String currency);

    List<Rates> findByDate(LocalDate date);

}