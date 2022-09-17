package com.krishi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.krishi.entity.StressSymptoms;

public interface StressSymptomsRepository
		extends JpaRepository<StressSymptoms, Integer>{
}