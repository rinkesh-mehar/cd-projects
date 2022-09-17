package com.krishi.fls.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krishi.fls.entity.CaseFieldDetails;

public interface CaseDetailRepository  extends JpaRepository<CaseFieldDetails, String>{

	List<CaseFieldDetails> findByCaseIdIn(List<String> caseId);

}
