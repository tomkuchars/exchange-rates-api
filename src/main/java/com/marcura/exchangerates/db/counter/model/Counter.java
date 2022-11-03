package com.marcura.exchangerates.db.counter.model;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDate;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(indexes = @Index(name = "counter_currency_date", columnList = "currency,date"))
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

    public void increment() {
        if (counter == null) {
            counter = BigInteger.ZERO;
        }
        counter = counter.add(BigInteger.ONE);
    }

}