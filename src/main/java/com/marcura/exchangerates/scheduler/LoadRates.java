package com.marcura.exchangerates.scheduler;

import com.marcura.exchangerates.db.rates.RatesService;
import com.marcura.exchangerates.db.rates.model.Rates;
import com.marcura.exchangerates.fixer.FixerClient;
import com.marcura.exchangerates.fixer.model.LatestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
