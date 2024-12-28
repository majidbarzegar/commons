package com.bypen.common.mvc.service;

import com.bypen.common.model.AbstractCriteria;
import com.bypen.common.model.AbstractDto;
import com.bypen.common.model.AbstractPersistable;

import java.util.List;
import java.util.Optional;

public interface AbstractService<M extends AbstractPersistable<I>, C extends AbstractCriteria<I>, D extends AbstractDto<I>, I extends Comparable<I>> {

    M save(D dto);

    List<M> save(List<D> dtoList);

    List<M> saveModel(List<M> modelList);

    M save(M model);

    M update(D dto);

    M update(M model);

    Optional<M> get(I id);

    List<M> get(List<I> idList);

    List<M> get(C criteria);

    Optional<M> getFirst(C criteria);

    Optional<M> getLast(C criteria);

    long getCount(C criteria);

    boolean isExist(C criteria);

    boolean isNotExist(C criteria);

    long delete(C criteria);

    long delete(I id);

    long delete(List<I> idList);
}
