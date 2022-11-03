package com.marcura.exchangerates.db.spread;

import com.marcura.exchangerates.db.spread.model.Spread;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface SpreadRepository extends JpaRepository<Spread, String> {

    Spread findByCurrency(String currency);

}