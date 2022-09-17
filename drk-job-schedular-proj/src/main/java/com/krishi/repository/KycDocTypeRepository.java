package com.krishi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import com.krishi.entity.KycDocType;

public interface KycDocTypeRepository extends JpaRepository<KycDocType, Integer>, QueryByExampleExecutor<KycDocType> 
{}