package com.penovatech.common.base.service;

import com.penovatech.common.base.criteria.SpecPredicateBuilder;
import com.penovatech.common.base.repository.AbstractJpaRepository;
import com.penovatech.common.model.AbstractCriteria;
import com.penovatech.common.model.AbstractPersistable;
import com.penovatech.common.model.AbstractPersistable_;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public abstract class AbstractSpecServiceImpl<
        MODEL extends AbstractPersistable<ID>,
        ID extends Comparable<ID>,
        CRITERIA extends AbstractCriteria<ID>,
        REPOSITORY extends AbstractJpaRepository<MODEL, ID>>
        extends AbstractServiceImpl<MODEL, ID, REPOSITORY>
        implements AbstractSpecService<MODEL, ID, CRITERIA> {

    @SuppressWarnings("unchecked")
    public AbstractSpecServiceImpl(REPOSITORY repository) {
        super(repository);
    }

    private Specification<MODEL> createSpecification(CRITERIA criteria) {
        SpecPredicateBuilder<MODEL> predicateBuilder = new SpecPredicateBuilder<>();
        this.addSpec(predicateBuilder, criteria);
        predicateBuilder.in(AbstractPersistable_.ID, criteria.getIdIn(), !CollectionUtils.isEmpty(criteria.getIdIn()));
        predicateBuilder.notIn(AbstractPersistable_.ID, criteria.getIdNotIn(), !CollectionUtils.isEmpty(criteria.getIdNotIn()));
        return predicateBuilder.build();
    }

    protected abstract void addSpec(SpecPredicateBuilder<MODEL> predicateBuilder, CRITERIA criteria);

    public Optional<MODEL> findOne(CRITERIA criteria) {
        return this.findOne(this.createSpecification(criteria));
    }

    public List<MODEL> findAll(CRITERIA criteria) {
        Specification<MODEL> specification = this.createSpecification(criteria);
        return this.findAll(specification);
    }

    public Page<MODEL> findAllInPage(CRITERIA criteria) {
        Specification<MODEL> specification = this.createSpecification(criteria);
        Pageable pageable = PageRequest.of(criteria.getPage(), criteria.getSize());
        return this.findAll(specification, pageable);
    }

    public Page<MODEL> findAll(CRITERIA criteria, Pageable pageable) {
        return this.findAll(this.createSpecification(criteria), pageable);
    }

    public List<MODEL> findAll(CRITERIA criteria, Sort sort) {
        return this.findAll(this.createSpecification(criteria), sort);
    }

    public long count(CRITERIA criteria) {
        return this.count(this.createSpecification(criteria));
    }

    public boolean exists(CRITERIA criteria) {
        return this.exists(this.createSpecification(criteria));
    }

    public long delete(CRITERIA criteria) {
        return this.delete(this.createSpecification(criteria));
    }

    public <SUB_MODEL extends MODEL, R> R findBy(CRITERIA criteria, Function<FluentQuery.FetchableFluentQuery<SUB_MODEL>, R> queryFunction) {
        return this.findBy(this.createSpecification(criteria), queryFunction);
    }

}
