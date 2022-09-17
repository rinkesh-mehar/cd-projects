package com.krishi.fls.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.krishi.fls.entity.PlantPart;

public interface PlantPartRepository extends JpaRepository<PlantPart, Integer> {
	public List<PlantPart> getPlantPartByIdIn(@Param("ids") Set<Integer> ids);
}
