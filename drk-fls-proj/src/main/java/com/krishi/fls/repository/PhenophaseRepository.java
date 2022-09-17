package com.krishi.fls.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.krishi.fls.entity.Phenophase;

public interface PhenophaseRepository extends JpaRepository<Phenophase, Integer>{
	public List<Phenophase> getPhenophaseByIdIn(@Param("ids") Set<Integer> ids);
}
