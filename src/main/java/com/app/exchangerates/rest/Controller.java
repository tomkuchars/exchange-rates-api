package com.app.exchangerates.rest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.app.exchangerates.db.counter.CounterService;
import com.app.exchangerates.db.rates.RatesService;
import com.app.exchangerates.db.rates.model.Rates;
import com.app.exchangerates.db.spread.SpreadService;
import com.app.exchangerates.db.spread.model.Spread;
import com.app.exchangerates.rest.model.ExchangeResponse;
import com.app.exchangerates.scheduler.LoadRates;

@RestController
public class Controller {

    public static final String EXCHANGE_PARAM_FROM = "from";
    public static final String EXCHANGE_PARAM_TO = "to";
    public static final String EXCHANGE_PARAM_DATE = "date";
    public static final int EXCHANGE_SCALE = 30;
    private final RatesService ratesService;
    private final SpreadService spreadService;
    private final CounterService counterService;
    private final LoadRates loadRates;

    public Controller(RatesService ratesService, SpreadService spreadService, CounterService counterService,
            LoadRates loadRates) {
        this.ratesService = ratesService;
        this.spreadService = spreadService;
        this.counterService = counterService;
        this.loadRates = loadRates;
    }

    @GetMapping("/exchange")
    public ResponseEntity<ExchangeResponse> exchange(@RequestParam(value = EXCHANGE_PARAM_FROM) String from,
            @RequestParam(value = EXCHANGE_PARAM_TO) String to,
            @RequestParam(value = EXCHANGE_PARAM_DATE, required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        Rates fromRates = getRatesAndIncrement(from, EXCHANGE_PARAM_FROM, date);
        Rates toRates = getRatesAndIncrement(to, EXCHANGE_PARAM_TO, date);
        Spread fromSpread = spreadService.getSpreadByCurrency(from);
        Spread toSpread = spreadService.getSpreadByCurrency(to);
        Spread spread = fromSpread.spread.compareTo(toSpread.spread) > 0 ? fromSpread : toSpread;
        BigDecimal exchange = toRates.rate.divide(fromRates.rate, EXCHANGE_SCALE, RoundingMode.HALF_UP)
                .multiply(BigDecimal.ONE.subtract(spread.spread)).stripTrailingZeros();
        ExchangeResponse exchangeResponse = new ExchangeResponse(from, to, exchange);
        return ResponseEntity.ok(exchangeResponse);
    }

    private Rates getRatesAndIncrement(String paramValue, String paramName, LocalDate date)
            throws ResponseStatusException {
        Rates rates = ratesService.getRatesByCurrencyAndDate(paramValue, date);
        if (rates == null) {
            counterService.incrementCounterByCurrencyAndDate(paramValue, date);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Rates not found for " + paramName + "=" + paramValue);
        } else {
            counterService.incrementCounterByCurrencyAndDate(paramValue, rates.date);
        }
        return rates;
    }

    @PutMapping("/exchange")
    public ResponseEntity<Integer> exchange() {
        List<Rates> ratesList = loadRates.load();
        return ResponseEntity.ok(ratesList.size());
    }

}
