package com.krishi.fls.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krishi.fls.entity.District;

public interface DistrictRepository extends JpaRepository<District, Integer> {
	
}
