package com.krishi.fls.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.krishi.fls.entity.Stress;

public interface StressRepository extends JpaRepository<Stress, Integer>{
	
	public List<Stress> getStressByCommodityIdIn(@Param("commodityIds") Set<Integer> commodityIds);

	public List<Stress> getStressByAczIdIn(@Param("aczIds") Set<Integer> aczIds);
}
