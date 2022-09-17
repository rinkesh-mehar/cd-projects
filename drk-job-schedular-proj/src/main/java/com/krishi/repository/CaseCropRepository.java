package com.krishi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krishi.entity.CaseCropInfo;

public interface CaseCropRepository extends JpaRepository<CaseCropInfo, String>{

	List<CaseCropInfo> findByCaseIdIn(List<String> caseId);

	CaseCropInfo findByCaseId(String id);
	
	

}
