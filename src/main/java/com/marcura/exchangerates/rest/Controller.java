package com.marcura.exchangerates.rest;

import com.marcura.exchangerates.db.counter.CounterService;
import com.marcura.exchangerates.db.rates.RatesService;
import com.marcura.exchangerates.db.rates.model.Rates;
import com.marcura.exchangerates.db.spread.SpreadService;
import com.marcura.exchangerates.db.spread.model.Spread;
import com.marcura.exchangerates.rest.model.ExchangeResponse;
import com.marcura.exchangerates.scheduler.LoadRates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@RestController
public class Controller {

    public static final String EXCHANGE_PARAM_FROM = "from";
    public static final String EXCHANGE_PARAM_TO = "to";
    public static final String EXCHANGE_PARAM_DATE = "date";
    private final RatesService ratesService;
    private final SpreadService spreadService;
    private final CounterService counterService;
    private final LoadRates loadRates;

    @Autowired
    public Controller(RatesService ratesService, SpreadService spreadService, CounterService counterService, LoadRates loadRates) {
        this.ratesService = ratesService;
        this.spreadService = spreadService;
        this.counterService = counterService;
        this.loadRates = loadRates;
    }

    @GetMapping("/exchange")
    public ExchangeResponse exchange(@RequestParam(value = EXCHANGE_PARAM_FROM) String from,
                                     @RequestParam(value = EXCHANGE_PARAM_TO) String to,
                                     @RequestParam(value = EXCHANGE_PARAM_DATE, required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        Rates fromRates = getRatesAndIncrement(from, EXCHANGE_PARAM_FROM, date);
        Rates toRates = getRatesAndIncrement(to, EXCHANGE_PARAM_TO, date);
        Spread fromSpread = spreadService.getSpreadByCurrency(from);
        Spread toSpread = spreadService.getSpreadByCurrency(to);
        Spread spread = fromSpread.spread.compareTo(toSpread.spread) > 0 ? fromSpread : toSpread;
        BigDecimal exchange = toRates.rate.divide(fromRates.rate, 10, RoundingMode.HALF_UP).multiply(BigDecimal.ONE.subtract(spread.spread));
        ExchangeResponse exchangeResponse = new ExchangeResponse(from, to, exchange);
        return exchangeResponse;
    }

    private Rates getRatesAndIncrement(String paramValue, String paramName, LocalDate date) throws ResponseStatusException {
        Rates rates = ratesService.getRatesByCurrencyAndDate(paramValue, date);
        if (rates == null) {
            counterService.incrementCounterByCurrencyAndDate(paramValue, date);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Rates not found for " + paramName + "=" + paramValue);
        } else {
            counterService.incrementCounterByCurrencyAndDate(paramValue, rates.date);
        }
        return rates;
    }

    @PutMapping("/exchange")
    public Integer exchange() {
        List<Rates> ratesList = loadRates.load();
        return ratesList.size();
    }

}
