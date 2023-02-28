package com.app.exchangerates.db.spread;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.app.exchangerates.db.spread.model.Spread;

@Repository
@Transactional
public interface SpreadRepository extends JpaRepository<Spread, String> {

    Spread findByCurrency(String currency);

}