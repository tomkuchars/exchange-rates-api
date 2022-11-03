package com.marcura.exchangerates.db.spread;

import com.marcura.exchangerates.db.spread.model.Spread;
import com.marcura.exchangerates.fixer.FixerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SpreadServiceImpl implements SpreadService {

    private final SpreadRepository spreadRepository;
    private final FixerProperties fixerProperties;

    @Autowired
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
