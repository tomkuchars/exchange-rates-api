package com.marcura.exchangerates.scheduler;

import com.marcura.exchangerates.db.rates.model.Rates;

import java.time.LocalDateTime;
import java.util.List;

class LoadRatesRunnable implements Runnable {
    private final LoadRates loadRates;

    public LoadRatesRunnable(LoadRates loadRates) {
        this.loadRates = loadRates;
    }

    @Override
    public void run() {
        System.out.println(LocalDateTime.now() + " Start loading exchange rates, thread name: " + Thread.currentThread().getName());
        List<Rates> ratesList = loadRates.load();
        System.out.println(LocalDateTime.now() + " Finish loading exchange rates, loaded: " + ratesList.size());
    }
}
