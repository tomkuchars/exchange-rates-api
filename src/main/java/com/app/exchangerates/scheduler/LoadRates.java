package com.app.exchangerates.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.app.exchangerates.db.rates.RatesService;
import com.app.exchangerates.db.rates.model.Rates;
import com.app.exchangerates.fixer.FixerClient;
import com.app.exchangerates.fixer.model.LatestResponse;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LoadRates {
    private final FixerClient fixerClient;

    private final RatesService ratesService;

    @Autowired
    public LoadRates(FixerClient fixerClient, RatesService ratesService) {
        this.fixerClient = fixerClient;
        this.ratesService = ratesService;
    }

    public List<Rates> load() {
        LatestResponse latestResponse = fixerClient.getLatest();
        List<Rates> ratesList = latestResponse.rates.entrySet().stream()
                .map(entry -> new Rates(latestResponse.base, entry.getKey(), latestResponse.date, entry.getValue()))
                .collect(Collectors.toList());
        return ratesService.saveAll(ratesList);
    }
}
