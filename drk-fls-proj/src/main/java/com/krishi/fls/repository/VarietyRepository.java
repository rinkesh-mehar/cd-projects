package com.krishi.fls.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.krishi.fls.entity.Variety;

public interface VarietyRepository extends JpaRepository<Variety, Integer> {
	public List<Variety> getVarietyByCommodityIdIn(@Param("commodityIds") Set<Integer> commodityIds);
}
