package com.penovatech.common.model;

import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@MappedSuperclass
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractAuditableDto<I extends Comparable<I>> extends AbstractDto<I> {
    protected AbstractUser<?> user;
    protected OffsetDateTime createdAt;
    protected OffsetDateTime updatedAt;
}

