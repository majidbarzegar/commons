package com.penovatech.common.base.controller;

import com.penovatech.common.base.mapper.BaseMapper;
import com.penovatech.common.base.service.AbstractSpecService;
import com.penovatech.common.dto.ResultDto;
import com.penovatech.common.model.AbstractCriteria;
import com.penovatech.common.model.AbstractDto;
import com.penovatech.common.model.AbstractPersistable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public abstract class AbstractSpecRestController<
        MODEL extends AbstractPersistable<ID>,
        CRITERIA extends AbstractCriteria<ID>,
        DTO extends AbstractDto<ID>,
        ID extends Comparable<ID>,
        SERVICE extends AbstractSpecService<MODEL, ID, CRITERIA>,
        MAPPER extends BaseMapper<MODEL, DTO>>
        extends AbstractRestController<MODEL, DTO, ID, SERVICE, MAPPER> {

    @SuppressWarnings("unchecked")
    public AbstractSpecRestController(SERVICE service, MAPPER mapper) {
        super(service, mapper);
    }

    @PostMapping(value = "search", consumes = APPLICATION_JSON_VALUE)
    public ResultDto<Page<DTO>> findAll(@RequestBody @Validated CRITERIA criteria) {
        Page<MODEL> modelPage = service.findAllInPage(criteria);
        List<DTO> dtoList = modelPage.getContent().stream()
                .map(model -> mapper.toDto(model)) // Use your mapping method
                .collect(Collectors.toList());
        Pageable pageable = PageRequest.of(modelPage.getNumber(), modelPage.getSize(), modelPage.getSort());
        return new ResultDto<>(new PageImpl<>(dtoList, pageable, modelPage.getTotalElements()));
    }
}
