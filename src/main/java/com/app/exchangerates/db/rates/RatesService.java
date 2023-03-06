package com.app.exchangerates.db.rates;

import java.time.LocalDate;
import java.util.List;

import com.app.exchangerates.db.rates.model.Rates;

public interface RatesService {

    Rates getRatesById(Integer id);

    Rates getRatesByBaseAndCurrencyAndDate(String base, String currency, LocalDate date);

    Rates getRatesByCurrencyAndDate(String currency, LocalDate date);

    List<Rates> getRatesListByDate(LocalDate date);

    Rates save(Rates rates);

    Iterable<Rates> saveAll(List<Rates> ratesList);
}
