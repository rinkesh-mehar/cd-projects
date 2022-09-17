package com.krishi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import com.krishi.entity.Commodity;
import com.krishi.entity.GovtOfficialDesignation;


public interface GovtOfficialDesignationrepository
  extends JpaRepository<GovtOfficialDesignation, Integer> , QueryByExampleExecutor<GovtOfficialDesignation> {
}