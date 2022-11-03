package com.marcura.exchangerates.db.spread.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Spread {

    @Id
    @Column(nullable = false, unique = true)
    public String currency;

    @Column(nullable = false, precision = 11, scale = 10)
    public BigDecimal spread;

    public Spread(String currency, BigDecimal spread) {
        this.currency = currency;
        this.spread = spread;
    }

    public Spread() {

    }
}