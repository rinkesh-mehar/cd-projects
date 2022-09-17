package com.krishi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krishi.entity.GovtOfficialDesignation;

public interface GovtOfficialDesignationRepository extends JpaRepository<GovtOfficialDesignation, Integer>{

	List<GovtOfficialDesignation> findByStatus(Integer status);

}
