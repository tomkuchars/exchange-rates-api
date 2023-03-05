package com.app.exchangerates.db.counter;

import java.time.LocalDate;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.app.exchangerates.db.counter.model.Counter;

@Service
public class CounterServiceImpl implements CounterService {

    private final CounterRepository counterRepository;

    public CounterServiceImpl(CounterRepository counterRepository) {
        this.counterRepository = counterRepository;
    }

    @Lazy
    @Autowired
    private CounterService counterService;

    @Override
    public Counter getCounterByCurrencyAndDate(String currency, LocalDate date) {
        return counterRepository.findByCurrencyAndDate(currency, date);
    }

    @Override
    public Counter save(Counter counter) {
        return counterRepository.saveAndFlush(counter);
    }

    @Override
    public void incrementCounterByCurrencyAndDate(String currency, LocalDate date) {
        try {
            counterService.createCounter(currency, date);
        } catch (DataIntegrityViolationException exception) {
            if (Pattern.compile(Pattern.quote("counter_unique_currency_date"), Pattern.CASE_INSENSITIVE)
                    .matcher(exception.getMessage()).find()) {
                Counter counter = getCounterByCurrencyAndDate(currency, date);
                if (counter == null) {
                    throw new DataIntegrityViolationException("Coulld not find or create counter");
                }
            } else {
                throw exception;
            }
        }
        counterService.incrementCounter(currency, date);
    }

    @Override
    public void createCounter(String currency, LocalDate date) throws DataIntegrityViolationException {
        Counter counter = getCounterByCurrencyAndDate(currency, date);
        if (counter == null) {
            counter = new Counter(currency, date);
            save(counter);
        }
    }

    @Override
    public void incrementCounter(String currency, LocalDate date) {
        // counterRepository.incrementCounter(currency, date);
        counterRepository.incrementCounterUsingCriteria(currency, date);
    }
}
