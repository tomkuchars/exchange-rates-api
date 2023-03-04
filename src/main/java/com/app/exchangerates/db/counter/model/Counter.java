package com.app.exchangerates.db.counter.model;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDate;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "counter_unique_currency_date", columnNames = { "currency", "date" }) })
public class Counter {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    public Integer id;
    @Column(nullable = false)
    public String currency;
    @Column
    public LocalDate date;
    @Column(nullable = false)
    public BigInteger counter;

    public Counter(String currency, LocalDate date) {
        this.currency = currency;
        this.date = date;
        this.counter = BigInteger.ZERO;
    }

    public Counter() {

    }

}