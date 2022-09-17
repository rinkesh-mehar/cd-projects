package com.krishi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krishi.entity.StressSeverity;

public interface StressSeverityRepository extends JpaRepository<StressSeverity, Integer> {

}
