package com.penovatech.common.model;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public abstract class AbstractCriteria<I extends Comparable<I>> {
    @Min(1)
    protected Integer page;
    @Min(1)
    protected Integer size;
    protected List<I> idNotIn;
    protected List<I> idIn;
}
