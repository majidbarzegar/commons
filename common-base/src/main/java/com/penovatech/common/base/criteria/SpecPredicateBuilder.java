package com.penovatech.common.base.criteria;

import com.penovatech.common.model.AbstractPersistable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class SpecPredicateBuilder<T extends AbstractPersistable<?>> {

    private Specification<T> specification;

    public SpecPredicateBuilder() {
        this.specification = (root, query, criteriaBuilder) -> criteriaBuilder.conjunction(); // Empty predicate
    }

    public SpecPredicateBuilder<T> equal(String field, Object value, boolean shouldAdd) {
        if (shouldAdd && value != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get(field), value));
        }
        return this;
    }

    public SpecPredicateBuilder<T> notEqual(String field, Object value, boolean shouldAdd) {
        if (shouldAdd && value != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.notEqual(root.get(field), value));
        }
        return this;
    }

    public SpecPredicateBuilder<T> in(String field, List<?> value, boolean shouldAdd) {
        if (shouldAdd && value != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    root.get(field).in(value));
        }
        return this;
    }

    public SpecPredicateBuilder<T> notIn(String field, List<?> value, boolean shouldAdd) {
        if (shouldAdd && value != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.not(root.get(field).in(value)));
        }
        return this;
    }

    public SpecPredicateBuilder<T> like(String field, String value, boolean shouldAdd) {
        if (shouldAdd && value != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(root.get(field), "%" + value + "%"));
        }
        return this;
    }

    public SpecPredicateBuilder<T> notLike(String field, String value, boolean shouldAdd) {
        if (shouldAdd && value != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.notLike(root.get(field), "%" + value + "%"));
        }
        return this;
    }

    public SpecPredicateBuilder<T> greaterThan(String field, Comparable value, boolean shouldAdd) {
        if (shouldAdd && value != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.greaterThan(root.get(field), value));
        }
        return this;
    }

    public SpecPredicateBuilder<T> greaterThanOrEqualTo(String field, Comparable value, boolean shouldAdd) {
        if (shouldAdd && value != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.greaterThanOrEqualTo(root.get(field), value));
        }
        return this;
    }

    public SpecPredicateBuilder<T> lessThan(String field, Comparable value, boolean shouldAdd) {
        if (shouldAdd && value != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.lessThan(root.get(field), value));
        }
        return this;
    }

    public SpecPredicateBuilder<T> lessThanOrEqualTo(String field, Comparable value, boolean shouldAdd) {
        if (shouldAdd && value != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.lessThanOrEqualTo(root.get(field), value));
        }
        return this;
    }

    public Specification<T> build() {
        return specification;
    }
}