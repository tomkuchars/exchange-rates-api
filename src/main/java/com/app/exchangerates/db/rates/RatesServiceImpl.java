package com.app.exchangerates.db.rates;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.exchangerates.db.rates.model.Rates;
import com.app.exchangerates.fixer.FixerProperties;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class RatesServiceImpl implements RatesService {

    private final RatesRepository ratesRepository;

    private final FixerProperties fixerProperties;

    @Autowired
    public RatesServiceImpl(RatesRepository ratesRepository, FixerProperties fixerProperties) {
        this.ratesRepository = ratesRepository;
        this.fixerProperties = fixerProperties;
    }

    @Override
    public Rates getRatesById(Integer id) {
        return ratesRepository.findById(id).orElse(null);
    }

    @Override
    public Rates getRatesByBaseAndCurrencyAndDate(String base, String currency, LocalDate date) {
        Rates rates;
        if (date != null) {
            rates = ratesRepository.findTopByBaseAndCurrencyAndDateOrderByIdDesc(base, currency, date);
        } else {
            rates = ratesRepository.findTopByBaseAndCurrencyOrderByDateDescIdDesc(base, currency);
        }
        return rates;
    }

    @Override
    public Rates getRatesByCurrencyAndDate(String currency, LocalDate date) {
        return getRatesByBaseAndCurrencyAndDate(fixerProperties.baseCurrency(), currency, date);
    }

    @Override
    public List<Rates> getRatesListByDate(LocalDate date) {
        return ratesRepository.findByDate(date);
    }

    @Override
    public Rates save(Rates rates) {
        return ratesRepository.save(rates);
    }

    @Override
    public List<Rates> saveAll(List<Rates> ratesList) {
        return ratesRepository.saveAll(ratesList);
    }

}
