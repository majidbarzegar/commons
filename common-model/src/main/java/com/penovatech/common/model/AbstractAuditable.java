package com.penovatech.common.model;

import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractAuditable<I extends Comparable<I>> extends AbstractPersistable<I> {
    public AbstractAuditable(I id) {
        super(id);
    }

    protected LocalDateTime createdDate;
}

