package com.marcura.exchangerates.db.rates.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(indexes = {
        @Index(name = "rates_base_currency", columnList = "base,currency"),
        @Index(name = "rates_base_currency_date", columnList = "base,currency,date")})
public class Rates {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    public Integer id;
    @Column(nullable = false)
    public String base;
    @Column(nullable = false)
    public String currency;
    @Column(nullable = false)
    public LocalDate date;
    @Column(nullable = false, precision = 20, scale = 10)
    public BigDecimal rate;

    public Rates(Integer id, String base, String currency, LocalDate date, BigDecimal rate) {
        this(base, currency, date, rate);
        this.id = id;
    }

    public Rates(String base, String currency, LocalDate date, BigDecimal rate) {
        this.base = base;
        this.currency = currency;
        this.date = date;
        this.rate = rate;
    }

    public Rates() {

    }

}