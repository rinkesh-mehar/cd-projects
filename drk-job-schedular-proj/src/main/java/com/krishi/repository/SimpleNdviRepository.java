package com.krishi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.krishi.entity.SimpleNdvi;

public interface SimpleNdviRepository extends JpaRepository<SimpleNdvi, Integer>{

	List<SimpleNdvi> findByCaseIdAndWeekAndYear(String caseId, Integer week, Integer year);
	
	@Query("Select cn from SimpleNdvi as cn where cn.caseId =:caseId")
	List<SimpleNdvi> findByCaseId(@Param(value ="caseId") String caseId);
}
