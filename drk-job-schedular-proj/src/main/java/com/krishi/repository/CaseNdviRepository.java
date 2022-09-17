package com.krishi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.krishi.entity.CaseNdvi;

public interface CaseNdviRepository extends JpaRepository<CaseNdvi, Integer>{

	@Query("Select cn from CaseNdvi as cn where cn.caseId =:caseId")
	List<CaseNdvi> findByCaseId(@Param(value ="caseId") String caseId);

	List<CaseNdvi> findByCaseIdAndWeekAndYear(String caseId, Integer week, Integer year);

}
