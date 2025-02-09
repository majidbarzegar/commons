package com.penovatech.common.base.service;

import com.penovatech.common.base.repository.AbstractJpaRepository;
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

public abstract class AbstractServiceImpl<
        MODEL extends AbstractPersistable<ID>,
        ID extends Comparable<ID>,
        REPOSITORY extends AbstractJpaRepository<MODEL, ID>>
        implements AbstractService<MODEL, ID> {

    protected final REPOSITORY repository;

    @SuppressWarnings("unchecked")
    public AbstractServiceImpl(REPOSITORY repository) {
        this.repository = repository;
    }

    @Override
    public <SUB_MODEL extends MODEL> SUB_MODEL saveAndFlush(SUB_MODEL entity) {
        return repository.saveAndFlush(entity);
    }

    @Override
    public <SUB_MODEL extends MODEL> List<SUB_MODEL> saveAllAndFlush(Iterable<SUB_MODEL> entities) {
        return repository.saveAllAndFlush(entities);
    }

    @Override
    public <SUB_MODEL extends MODEL> List<SUB_MODEL> saveAll(Iterable<SUB_MODEL> entities) {
        return repository.saveAll(entities);
    }

    @Override
    public <SUB_MODEL extends MODEL> SUB_MODEL save(SUB_MODEL entity) {
        return repository.save(entity);
    }

    @Override
    public void deleteAllInBatch(Iterable<MODEL> entities) {
        repository.deleteAllInBatch(entities);
    }

    @Override
    public void deleteAllByIdInBatch(Iterable<ID> ids) {
        repository.deleteAllByIdInBatch(ids);
    }

    @Override
    public void deleteAllInBatch() {
        repository.deleteAllInBatch();
    }

    @Override
    public void deleteById(ID id) {
        repository.deleteById(id);
    }

    @Override
    public void delete(MODEL entity) {
        repository.delete(entity);
    }

    @Override
    public void deleteAllById(Iterable<? extends ID> ids) {
        repository.deleteAllById(ids);
    }

    @Override
    public void deleteAll(Iterable<? extends MODEL> entities) {
        repository.deleteAll(entities);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public long delete(@Nullable Specification<MODEL> spec) {
        return repository.delete(spec);
    }

    @Override
    public MODEL getReferenceById(ID id) {
        return repository.getReferenceById(id);
    }

    @Override
    public <SUB_MODEL extends MODEL> List<SUB_MODEL> findAll(Example<SUB_MODEL> example) {
        return repository.findAll(example);
    }

    @Override
    public <SUB_MODEL extends MODEL> List<SUB_MODEL> findAll(Example<SUB_MODEL> example, Sort sort) {
        return repository.findAll(example, sort);
    }

    @Override
    public List<MODEL> findAll() {
        return repository.findAll();
    }

    @Override
    public List<MODEL> findAllById(Iterable<ID> ids) {
        return repository.findAllById(ids);
    }

    @Override
    public List<MODEL> findAll(Sort sort) {
        return repository.findAll(sort);
    }

    @Override
    public <SUB_MODEL extends MODEL> Optional<SUB_MODEL> findOne(Example<SUB_MODEL> example) {
        return repository.findOne(example);
    }

    @Override
    public <SUB_MODEL extends MODEL> Page<SUB_MODEL> findAll(Example<SUB_MODEL> example, Pageable pageable) {
        return repository.findAll(example, pageable);
    }

    @Override
    public <SUB_MODEL extends MODEL, R> R findBy(Example<SUB_MODEL> example, Function<FluentQuery.FetchableFluentQuery<SUB_MODEL>, R> queryFunction) {
        return repository.findBy(example, queryFunction);
    }

    @Override
    public Optional<MODEL> findById(ID id) {
        return repository.findById(id);
    }

    @Override
    public Page<MODEL> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Optional<MODEL> findOne(Specification<MODEL> spec) {
        return repository.findOne(spec);
    }

    @Override
    public List<MODEL> findAll(@Nullable Specification<MODEL> spec) {
        return repository.findAll(spec);
    }

    @Override
    public Page<MODEL> findAll(@Nullable Specification<MODEL> spec, Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public List<MODEL> findAll(@Nullable Specification<MODEL> spec, Sort sort) {
        return repository.findAll(spec, sort);
    }

    @Override
    public <SUB_MODEL extends MODEL, R> R findBy(Specification<MODEL> spec, Function<FluentQuery.FetchableFluentQuery<SUB_MODEL>, R> queryFunction) {
        return repository.findBy(spec, queryFunction);
    }

    @Override
    public void flush() {
        repository.flush();
    }

    @Override
    public <SUB_MODEL extends MODEL> long count(Example<SUB_MODEL> example) {
        return repository.count();
    }

    @Override
    public <SUB_MODEL extends MODEL> boolean exists(Example<SUB_MODEL> example) {
        return repository.exists(example);
    }

    @Override
    public boolean existsById(ID id) {
        return repository.existsById(id);
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public long count(@Nullable Specification<MODEL> spec) {
        return repository.count(spec);
    }

    @Override
    public boolean exists(Specification<MODEL> spec) {
        return repository.exists(spec);
    }

}
