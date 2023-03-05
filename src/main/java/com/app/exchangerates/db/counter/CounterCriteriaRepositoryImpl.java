package com.app.exchangerates.db.counter;

import java.time.LocalDate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import com.app.exchangerates.db.counter.model.Counter;
import com.app.exchangerates.db.counter.model.Counter_;

public class CounterCriteriaRepositoryImpl implements CounterCriteriaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void incrementCounterUsingCriteria(String currency, LocalDate date) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<Counter> updateCounter = builder.createCriteriaUpdate(Counter.class);
        Root<Counter> counterRoot = updateCounter.from(Counter.class);
        updateCounter.set(Counter_.COUNTER, builder.sum(counterRoot.get(Counter_.COUNTER), 1));
        updateCounter.where(
                builder.and(
                        builder.equal(counterRoot.get(Counter_.CURRENCY), currency),
                        builder.equal(counterRoot.get(Counter_.DATE), date)));
        entityManager.createQuery(updateCounter).executeUpdate();
    }

}