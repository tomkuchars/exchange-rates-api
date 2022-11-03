package com.marcura.exchangerates.rest;

import com.marcura.exchangerates.db.counter.CounterService;
import com.marcura.exchangerates.db.rates.RatesService;
import com.marcura.exchangerates.db.rates.model.Rates;
import com.marcura.exchangerates.db.spread.SpreadService;
import com.marcura.exchangerates.db.spread.model.Spread;
import com.marcura.exchangerates.scheduler.LoadRates;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(Controller.class)
public class ControllerTest {

    private static final LocalDate NOW = LocalDate.now();
    @Autowired
    private MockMvc mvc;

    @MockBean
    private RatesService ratesService;

    @MockBean
    private SpreadService spreadService;

    @MockBean
    private CounterService counterService;

    @MockBean
    private LoadRates loadRates;

    @Test
    public void givenRatesAndSpread_whenGetExchange_thenReturnJson() throws Exception {
        Rates eurRates = new Rates(0, Currencies.USD, Currencies.EUR, NOW, BigDecimal.valueOf(0.8));
        Rates plnRates = new Rates(0, Currencies.USD, Currencies.PLN, NOW, BigDecimal.valueOf(3.7));
        given(ratesService.getRatesByCurrencyAndDate(eq(Currencies.EUR), eq(NOW))).willReturn(eurRates);
        given(ratesService.getRatesByCurrencyAndDate(eq(Currencies.PLN), eq(NOW))).willReturn(plnRates);

        Spread eurSpread = new Spread(Currencies.EUR, BigDecimal.valueOf(0.01));
        Spread plnSpread = new Spread(Currencies.PLN, BigDecimal.valueOf(0.04));
        given(spreadService.getSpreadByCurrency(eq(Currencies.EUR))).willReturn(eurSpread);
        given(spreadService.getSpreadByCurrency(eq(Currencies.PLN))).willReturn(plnSpread);

        mvc.perform(get("/exchange")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param(Controller.EXCHANGE_PARAM_FROM, Currencies.EUR)
                        .param(Controller.EXCHANGE_PARAM_TO, Currencies.PLN)
                        .param(Controller.EXCHANGE_PARAM_DATE, NOW.format(DateTimeFormatter.ISO_DATE)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("from", is(Currencies.EUR)))
                .andExpect(jsonPath("to", is(Currencies.PLN)))
                .andExpect(jsonPath("exchange", is(4.44)));
    }

    private interface Currencies {
        String USD = "USD";
        String EUR = "EUR";
        String PLN = "PLN";
    }
}
