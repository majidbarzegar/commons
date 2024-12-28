package com.bypen.common.model;

import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class AbstractDto<I extends Comparable<I>> {
    @NotNull(groups = ValidationGroup.Update.class)
    @Null(groups = ValidationGroup.Save.class)
    private I id;
}

