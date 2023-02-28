package com.app.exchangerates.db.spread.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Spread {

    @Id
    @Column(nullable = false, unique = true)
    public String currency;

    @Column(nullable = false, precision = 9, scale = 8)
    public BigDecimal spread;

    public Spread(String currency, BigDecimal spread) {
        this.currency = currency;
        this.spread = spread;
    }

    public Spread() {

    }
}