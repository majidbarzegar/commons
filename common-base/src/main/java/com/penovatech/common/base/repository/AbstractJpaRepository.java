package com.penovatech.common.base.repository;

import com.penovatech.common.model.AbstractPersistable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface AbstractJpaRepository<MODEL extends AbstractPersistable<ID>, ID extends Comparable<ID>> extends JpaRepository<MODEL, ID> , JpaSpecificationExecutor<MODEL> {
}
