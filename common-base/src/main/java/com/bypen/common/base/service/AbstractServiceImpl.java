package com.bypen.common.base.service;

import com.bypen.common.model.AbstractCriteria;
import com.bypen.common.model.AbstractDto;
import com.bypen.common.model.AbstractPersistable;
import com.bypen.common.base.mapper.BaseMapper;
import com.bypen.common.base.repository.AbstractRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractServiceImpl<M extends AbstractPersistable<I>, C extends AbstractCriteria<I>, D extends AbstractDto<I>, I extends Comparable<I>> implements AbstractService<M, C, D, I> {

    @SuppressWarnings("unchecked")
    public AbstractServiceImpl(AbstractRepository<M, C, I> repository, BaseMapper<M, D> mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    private AbstractRepository<M, C, I> repository;
    private BaseMapper<M, D> mapper;

    @Transactional
    @Override
    public M save(D dto) {
        Assert.notNull(dto, "DTO cannot be null");
        Assert.isNull(dto.getId(), "DTO`s id in save case must be null.");
        M model = mapper.toModel(dto);
        return repository.save(model);
    }

    @Transactional
    @Override
    public List<M> saveModel(List<M> modelList) {
        Assert.notEmpty(modelList, "Model list cannot be empty");
        return repository.save(modelList);
    }

    @Transactional
    @Override
    public List<M> save(List<D> dtoList) {
        Assert.notEmpty(dtoList, "DTO list cannot be empty");
        List<M> modelList = new ArrayList<>();
        for (D d : dtoList) {
            modelList.add(mapper.toModel(d));
        }
        return this.saveModel(modelList);
    }

    @Transactional
    @Override
    public M save(M model) {
        Assert.notNull(model, "Model cannot be null");
        return repository.save(model);
    }

    @Transactional
    @Override
    public M update(D dto) {
        Assert.notNull(dto, "DTO cannot be null");
        Assert.notNull(dto.getId(), "DTO`s id cannot be null");
        Optional<M> model = this.get(dto.getId());
        Assert.isTrue(model.isPresent(), "Model not fount with DTO`s id");
        mapper.updateModelFromDto(model.get(), dto);
        return this.update(model.get());
    }

    @Transactional
    @Override
    public M update(M model) {
        Assert.notNull(model, "Model cannot be null");
        Assert.notNull(model.getId(), "Model`s id cannot be null");
        return repository.update(model);
    }

    @Override
    public Optional<M> get(I id) {
        Assert.notNull(id, "ID cannot be null");
        return repository.get(id);
    }

    @Override
    public List<M> get(List<I> idList) {
        Assert.notEmpty(idList, "ID list cannot be empty");
        return repository.get(idList);
    }

    @Override
    public List<M> get(C criteria) {
        Assert.notNull(criteria, "Criteria cannot be null");
        return repository.get(criteria);
    }

    @Override
    public Optional<M> getFirst(C criteria) {
        Assert.notNull(criteria, "Criteria cannot be null");
        return repository.getFirst(criteria);
    }

    @Override
    public Optional<M> getLast(C criteria) {
        Assert.notNull(criteria, "Criteria cannot be null");
        return repository.getLast(criteria);
    }

    @Override
    public long getCount(C criteria) {
        Assert.notNull(criteria, "Criteria cannot be null");
        return repository.getCount(criteria);
    }

    @Override
    public boolean isExist(C criteria) {
        Assert.notNull(criteria, "Criteria cannot be null");
        return repository.isExist(criteria);
    }

    @Override
    public boolean isNotExist(C criteria) {
        Assert.notNull(criteria, "Criteria cannot be null");
        return repository.isNotExist(criteria);
    }

    @Transactional
    @Override
    public long delete(C criteria) {
        Assert.notNull(criteria, "Criteria cannot be null");
        return repository.delete(criteria);
    }

    @Transactional
    @Override
    public long delete(I id) {
        Assert.notNull(id, "ID cannot be null");
        return repository.delete(id);
    }

    @Transactional
    @Override
    public long delete(List<I> idList) {
        Assert.notEmpty(idList, "ID list cannot be empty");
        return repository.delete(idList);
    }
}

