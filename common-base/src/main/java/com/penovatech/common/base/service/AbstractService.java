package com.penovatech.common.base.service;

import com.penovatech.common.model.AbstractPersistable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface AbstractService<MODEL extends AbstractPersistable<ID>, ID extends Comparable<ID>> {

    <SUB_MODEL extends MODEL> SUB_MODEL saveAndFlush(SUB_MODEL entity);

    <SUB_MODEL extends MODEL> List<SUB_MODEL> saveAllAndFlush(Iterable<SUB_MODEL> entities);

    <SUB_MODEL extends MODEL> List<SUB_MODEL> saveAll(Iterable<SUB_MODEL> entities);

    <SUB_MODEL extends MODEL> SUB_MODEL save(SUB_MODEL entity);

    void deleteAllInBatch(Iterable<MODEL> entities);

    void deleteAllByIdInBatch(Iterable<ID> ids);

    void deleteAllInBatch();

    void deleteById(ID id);

    void delete(MODEL entity);

    void deleteAllById(Iterable<? extends ID> ids);

    void deleteAll(Iterable<? extends MODEL> entities);

    void deleteAll();

    MODEL getReferenceById(ID id);

    <SUB_MODEL extends MODEL> List<SUB_MODEL> findAll(Example<SUB_MODEL> example);

    <SUB_MODEL extends MODEL> List<SUB_MODEL> findAll(Example<SUB_MODEL> example, Sort sort);

    List<MODEL> findAll();

    List<MODEL> findAllById(Iterable<ID> ids);

    List<MODEL> findAll(Sort sort);

    <SUB_MODEL extends MODEL> Optional<SUB_MODEL> findOne(Example<SUB_MODEL> example);

    <SUB_MODEL extends MODEL> Page<SUB_MODEL> findAll(Example<SUB_MODEL> example, Pageable pageable);

    <SUB_MODEL extends MODEL, R> R findBy(Example<SUB_MODEL> example, Function<FluentQuery.FetchableFluentQuery<SUB_MODEL>, R> queryFunction);

    Optional<MODEL> findById(ID id);

    Page<MODEL> findAll(Pageable pageable);

    Optional<MODEL> findOne(Specification<MODEL> spec);

    List<MODEL> findAll(@Nullable Specification<MODEL> spec);

    Page<MODEL> findAll(@Nullable Specification<MODEL> spec, Pageable pageable);

    List<MODEL> findAll(@Nullable Specification<MODEL> spec, Sort sort);

    <SUB_MODEL extends MODEL, R> R findBy(Specification<MODEL> spec, Function<FluentQuery.FetchableFluentQuery<SUB_MODEL>, R> queryFunction);

    void flush();

    <SUB_MODEL extends MODEL> long count(Example<SUB_MODEL> example);

    <SUB_MODEL extends MODEL> boolean exists(Example<SUB_MODEL> example);

    boolean existsById(ID id);

    long count();

    long count(@Nullable Specification<MODEL> spec);

    boolean exists(Specification<MODEL> spec);

    long delete(@Nullable Specification<MODEL> spec);

}
