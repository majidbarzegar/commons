package com.penovatech.common.base.controller;

import com.penovatech.common.base.mapper.BaseMapper;
import com.penovatech.common.base.service.AbstractService;
import com.penovatech.common.dto.ResultDto;
import com.penovatech.common.exception.BusinessException;
import com.penovatech.common.exception.CommonExceptionMessage;
import com.penovatech.common.model.AbstractDto;
import com.penovatech.common.model.AbstractPersistable;
import com.penovatech.common.model.ValidationGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public abstract class AbstractRestController<
        MODEL extends AbstractPersistable<ID>,
        DTO extends AbstractDto<ID>,
        ID extends Comparable<ID>,
        SERVICE extends AbstractService<MODEL, ID>,
        MAPPER extends BaseMapper<MODEL, DTO>> {

    @SuppressWarnings("unchecked")
    public AbstractRestController(SERVICE service, MAPPER mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    protected SERVICE service;
    protected MAPPER mapper;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResultDto<DTO> save(@RequestBody @Validated(ValidationGroup.Save.class) DTO dto) {
        MODEL model = mapper.toModel(dto);
        MODEL savedModel = service.save(model);
        DTO savedDto = mapper.toDto(savedModel);
        return new ResultDto<>(savedDto);
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE)
    public ResultDto<DTO> update(@RequestBody @Validated(ValidationGroup.Update.class) DTO dto) {
        Optional<MODEL> modelOptional = service.findById(dto.getId());
        if (modelOptional.isEmpty()) {
            throw new BusinessException(CommonExceptionMessage.RECOURSE_NOT_FOUND);
        }
        MODEL model = modelOptional.get();
        mapper.updateModelFromDto(model, dto);
        MODEL savedModel = service.save(model);
        DTO savedDto = mapper.toDto(savedModel);
        return new ResultDto<>(savedDto);
    }

    @DeleteMapping("{id}")
    public ResultDto<Void> deleteById(@PathVariable("id") ID id) {
        service.deleteById(id);
        return new ResultDto<>();
    }

    @GetMapping("{id}")
    public ResultDto<MODEL> findById(@PathVariable("id") ID id) {
        return new ResultDto<>(service.getReferenceById(id));
    }

    @GetMapping
    public ResultDto<Page<DTO>> findAll(@RequestParam int page, @RequestParam int size) {
        Page<MODEL> modelPage = service.findAll(PageRequest.of(page, size));
        List<DTO> dtoList = modelPage.getContent().stream()
                .map(model -> mapper.toDto(model)) // Use your mapping method
                .collect(Collectors.toList());
        Pageable pageable = PageRequest.of(modelPage.getNumber(), modelPage.getSize(), modelPage.getSort());
        return new ResultDto<>(new PageImpl<>(dtoList, pageable, modelPage.getTotalElements()));
    }
}
