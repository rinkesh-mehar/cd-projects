package com.krishi.fls.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krishi.fls.entity.CaseKml;

public interface CaseKmlRepository extends JpaRepository<CaseKml, String>{

	List<CaseKml> findByFarmCaseIdIn(List<String> caseId);

}
