package com.app.exchangerates.rest.model;

import java.math.BigDecimal;

public class ExchangeResponse {

    public String from;
    public String to;
    public BigDecimal exchange;

    public ExchangeResponse(String from, String to, BigDecimal exchange) {
        this.from = from;
        this.to = to;
        this.exchange = exchange;
    }
}
