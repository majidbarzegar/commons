package com.penovatech.common.base;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public class PredicateBuilder<T> {

    private final List<Predicate> predicates = new ArrayList<>();
    private final CriteriaBuilder cb;
    private final Root<T> root;

    public PredicateBuilder(CriteriaBuilder cb, Root<T> root) {
        this.cb = cb;
        this.root = root;
    }

    public PredicateBuilder<T> addCondition(BiFunction<CriteriaBuilder, Root<T>, Predicate> condition, boolean shouldAdd) {
        if (shouldAdd) {
            predicates.add(condition.apply(cb, root));
        }
        return this;
    }

    public Predicate[] build() {
        return predicates.toArray(new Predicate[0]);
    }
}