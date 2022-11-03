package com.marcura.exchangerates.fixer.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

public class LatestResponse {

    public String base;
    public LocalDate date;
    public Map<String, BigDecimal> rates;
    public Boolean success;
    public Long timestamp;

}
