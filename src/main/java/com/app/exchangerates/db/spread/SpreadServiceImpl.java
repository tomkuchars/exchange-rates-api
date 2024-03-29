package com.app.exchangerates.db.spread;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.exchangerates.db.spread.model.Spread;
import com.app.exchangerates.fixer.FixerProperties;

@Service
@Transactional
public class SpreadServiceImpl implements SpreadService {

    private final SpreadRepository spreadRepository;
    private final FixerProperties fixerProperties;

    public SpreadServiceImpl(SpreadRepository spreadRepository, FixerProperties fixerProperties) {
        this.spreadRepository = spreadRepository;
        this.fixerProperties = fixerProperties;
    }

    @Override
    public Spread getSpreadByCurrency(String currency) {
        if (fixerProperties.baseCurrency().equals(currency)) {
            return spreadRepository.findByCurrency("BASE");
        } else {
            Spread spread = spreadRepository.findByCurrency(currency);
            if (spread == null) {
                spread = spreadRepository.findByCurrency("ELSE");
            }
            return spread;
        }
    }
}
