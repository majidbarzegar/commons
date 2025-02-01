package com.penovatech.common.base.repository;

import com.penovatech.common.model.AbstractCriteria;
import com.penovatech.common.model.AbstractPersistable;

import java.util.List;
import java.util.Optional;

public interface AbstractRepository<M extends AbstractPersistable<I>, C extends AbstractCriteria<I>, I extends Comparable<I>> {

    Optional<M> get(I id);

    List<M> get(List<I> idList);

    List<M> getAll();

    List<M> get(C criteria);

    Optional<M> getFirst(C criteria);

    Optional<M> getLast(C criteria);

    long delete(C criteria);

    long delete(I id);

    long delete(List<I> idList);

    M save(M model);

    List<M> save(List<M> modelList);

    M update(M model);

    long getCount(C criteria);

    boolean isExist(C criteria);

    boolean isNotExist(C criteria);

}
