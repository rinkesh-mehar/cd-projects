package com.krishi.fls.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.krishi.fls.entity.StressSeverityControlMeasures;

public interface StressSeverityControlMeasuresRepository  extends JpaRepository<StressSeverityControlMeasures, Integer>{
	public List<StressSeverityControlMeasures> getStressSeverityControlMeasuresByStressIdIn(@Param("stressIds") Set<Integer> stressIds);
}
