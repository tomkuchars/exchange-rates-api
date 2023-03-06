package com.app.exchangerates.scheduler;

import java.time.LocalDateTime;

class LoadRatesRunnable implements Runnable {
    private final LoadRates loadRates;

    public LoadRatesRunnable(LoadRates loadRates) {
        this.loadRates = loadRates;
    }

    @Override
    public void run() {
        System.out.println(LocalDateTime.now() + " Start loading exchange rates, thread name: " + Thread.currentThread().getName());
        int size = loadRates.load();
        System.out.println(LocalDateTime.now() + " Finish loading exchange rates, loaded: " + size);
    }
}
