package com.krishi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import com.krishi.entity.GovtOfficialDept;

public interface GovtOfficialDeptRepository extends JpaRepository<GovtOfficialDept, Integer> , QueryByExampleExecutor<GovtOfficialDept> {
}
