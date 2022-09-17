package com.krishi.fls.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krishi.fls.entity.CaseCropInfo;

public interface CaseCropRepository extends JpaRepository<CaseCropInfo, String>{

	List<CaseCropInfo> findByCaseIdIn(List<String> caseId);

}
