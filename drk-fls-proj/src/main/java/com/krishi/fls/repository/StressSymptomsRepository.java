package com.krishi.fls.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.krishi.fls.entity.StressSymptoms;

public interface StressSymptomsRepository extends JpaRepository<StressSymptoms, Integer>{
	public List<StressSymptoms> getStressSymptomsByStressIdIn(@Param("stressIds") Set<Integer> stressIds);
}
