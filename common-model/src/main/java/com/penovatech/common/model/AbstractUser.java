package com.penovatech.common.model;

import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
@AllArgsConstructor
public abstract class AbstractUser<I extends Comparable<I>> extends AbstractPersistable<I> {
    public AbstractUser(I id) {
        super(id);
    }
}
