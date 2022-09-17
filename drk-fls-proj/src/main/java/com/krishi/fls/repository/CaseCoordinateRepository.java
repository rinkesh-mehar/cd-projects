package com.krishi.fls.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krishi.fls.entity.CaseCoordinates;

public interface CaseCoordinateRepository extends JpaRepository<CaseCoordinates, String>{

	List<CaseCoordinates> findByCaseIdIn(List<String> caseId);

}
