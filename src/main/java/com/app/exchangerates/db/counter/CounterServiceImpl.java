package com.app.exchangerates.db.counter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.app.exchangerates.db.counter.model.Counter;

import java.time.LocalDate;

@Service
@Transactional
public class CounterServiceImpl implements CounterService {

    private final CounterRepository counterRepository;

    @Autowired
    public CounterServiceImpl(CounterRepository counterRepository) {
        this.counterRepository = counterRepository;
    }

    @Override
    public Counter getCounterByCurrencyAndDate(String currency, LocalDate date) {
        return counterRepository.findByCurrencyAndDate(currency, date);
    }

    @Override
    public Counter save(Counter counter) {
        return counterRepository.saveAndFlush(counter);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Counter incrementCounterByCurrencyAndDate(String currency, LocalDate date) {
        Counter counter = getCounterByCurrencyAndDate(currency, date);
        if (counter == null) {
            counter = new Counter(currency, date);
        }
        counter.increment();
        return save(counter);
    }
}
