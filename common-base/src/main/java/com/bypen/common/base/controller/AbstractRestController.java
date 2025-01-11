package com.bypen.common.base.controller;

import com.bypen.common.dto.ResponseDto;
import com.bypen.common.model.AbstractCriteria;
import com.bypen.common.model.AbstractDto;
import com.bypen.common.model.AbstractPersistable;
import com.bypen.common.model.ValidationGroup;
import com.bypen.common.base.service.AbstractService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public abstract class AbstractRestController<M extends AbstractPersistable<I>, C extends AbstractCriteria<I>, D extends AbstractDto<I>, I extends Comparable<I>> {

    @SuppressWarnings("unchecked")
    public AbstractRestController(AbstractService<M, C, D, I> service) {
        this.service = service;
    }

    private AbstractService<M, C, D, I> service;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseDto<M> save(@RequestBody @Validated(ValidationGroup.Save.class) D dto) {
        return new ResponseDto<>(service.save(dto));
    }

    @GetMapping("/{id}")
    public ResponseDto<M> get(@PathVariable("id") I id) {
        return service.get(id).map(ResponseDto::new).orElseGet(ResponseDto::new);
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseDto<M> update(@RequestBody @Validated(ValidationGroup.Update.class) D dto) {
        return new ResponseDto<>(service.update(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseDto<Long> delete(@PathVariable I id) {
        return new ResponseDto<>(service.delete(id));
    }

    @PostMapping(value = "list", consumes = APPLICATION_JSON_VALUE)
    public ResponseDto<List<M>> list(@RequestBody @Validated C criteria) {
        return new ResponseDto<>(service.get(criteria));
    }
}
