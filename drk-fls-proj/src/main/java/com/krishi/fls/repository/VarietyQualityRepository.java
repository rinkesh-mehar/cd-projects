package com.krishi.fls.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.krishi.fls.entity.VarietyQuality;

public interface VarietyQualityRepository extends JpaRepository<VarietyQuality, Integer> {
	public List<VarietyQuality> getVarietyQualityByVarietyIdIn(@Param("varietyIds") Set<Integer> varietyIds);
}
