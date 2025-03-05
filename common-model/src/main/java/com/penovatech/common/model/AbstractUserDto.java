package com.penovatech.common.model;

import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
@AllArgsConstructor
public class AbstractUserDto<I extends Comparable<I>> extends AbstractDto<I>{
}
