package com.bypen.common.base.mapper;

import com.bypen.common.model.AbstractDto;
import com.bypen.common.model.AbstractPersistable;

import java.util.List;

public interface BaseMapper<M extends AbstractPersistable, D extends AbstractDto> {

    D toDto(M model);

    M toModel(D dto);

    List<D> toDtoList(List<M> modelList);

    List<M> toModelList(List<D> dtoList);

    void updateModelFromDto(M model, D dto);
}