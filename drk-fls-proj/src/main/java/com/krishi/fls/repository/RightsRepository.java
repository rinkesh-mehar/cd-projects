package com.krishi.fls.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krishi.fls.entity.Rights;

public interface RightsRepository extends JpaRepository<Rights, String> {

	List<Rights> findByCaseIdIn(List<String> caseId);
}
