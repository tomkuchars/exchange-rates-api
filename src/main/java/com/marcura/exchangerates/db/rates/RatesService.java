package com.marcura.exchangerates.db.rates;

import com.marcura.exchangerates.db.rates.model.Rates;

import java.time.LocalDate;
import java.util.List;

public interface RatesService {

    Rates getRatesById(Integer id);

    Rates getRatesByBaseAndCurrencyAndDate(String base, String currency, LocalDate date);

    Rates getRatesByCurrencyAndDate(String currency, LocalDate date);

    List<Rates> getRatesListByDate(LocalDate date);

    Rates save(Rates rates);

    List<Rates> saveAll(List<Rates> ratesList);
}
