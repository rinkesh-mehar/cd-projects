package com.krishi.fls.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krishi.fls.entity.ViewRight;

public interface ViewRightRepository extends JpaRepository<ViewRight, String> {

	List<ViewRight> findByCaseId(String caseId);

}
