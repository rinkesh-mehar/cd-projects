package com.krishi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import com.krishi.entity.ArgochemicalType;



public interface ArgochemicalTypeRepository
  extends JpaRepository<ArgochemicalType, Integer> , QueryByExampleExecutor<ArgochemicalType> {
	
}