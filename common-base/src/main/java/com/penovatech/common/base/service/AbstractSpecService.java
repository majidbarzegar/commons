package com.penovatech.common.base.service;

import com.penovatech.common.model.AbstractCriteria;
import com.penovatech.common.model.AbstractPersistable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface AbstractSpecService<
        MODEL extends AbstractPersistable<ID>,
        ID extends Comparable<ID>,
        CRITERIA extends AbstractCriteria<ID>
        > extends AbstractService<MODEL, ID> {

    Optional<MODEL> findOne(CRITERIA criteria);

    List<MODEL> findAll(CRITERIA criteria);

    Page<MODEL> findAllInPage(CRITERIA criteria);

    Page<MODEL> findAll(CRITERIA criteria, Pageable pageable);

    List<MODEL> findAll(CRITERIA criteria, Sort sort);

    long count(CRITERIA criteria);

    boolean exists(CRITERIA criteria);

    long delete(CRITERIA criteria);

    <SUB_MODEL extends MODEL, R> R findBy(CRITERIA criteria, Function<FluentQuery.FetchableFluentQuery<SUB_MODEL>, R> queryFunction);

}
