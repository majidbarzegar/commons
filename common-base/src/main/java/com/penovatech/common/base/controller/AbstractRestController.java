package com.penovatech.common.base.controller;

import com.penovatech.common.dto.ResultDto;
import com.penovatech.common.model.AbstractCriteria;
import com.penovatech.common.model.AbstractDto;
import com.penovatech.common.model.AbstractPersistable;
import com.penovatech.common.model.ValidationGroup;
import com.penovatech.common.base.service.AbstractService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public abstract class AbstractRestController<
        M extends AbstractPersistable<I>,
        C extends AbstractCriteria<I>,
        D extends AbstractDto<I>,
        I extends Comparable<I>,
        S extends AbstractService<M, C, D, I>> {

    @SuppressWarnings("unchecked")
    public AbstractRestController(S service) {
        this.service = service;
    }

    protected S service;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResultDto<M> save(@RequestBody @Validated(ValidationGroup.Save.class) D dto) {
        return new ResultDto<>(service.save(dto));
    }

    @GetMapping("/{id}")
    public ResultDto<M> get(@PathVariable("id") I id) {
        return service.get(id).map(ResultDto::new).orElseGet(ResultDto::new);
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE)
    public ResultDto<M> update(@RequestBody @Validated(ValidationGroup.Update.class) D dto) {
        return new ResultDto<>(service.update(dto));
    }

    @DeleteMapping("/{id}")
    public ResultDto<Long> delete(@PathVariable I id) {
        return new ResultDto<>(service.delete(id));
    }

    @PostMapping(value = "list", consumes = APPLICATION_JSON_VALUE)
    public ResultDto<List<M>> list(@RequestBody @Validated C criteria) {
        return new ResultDto<>(service.get(criteria));
    }
}
