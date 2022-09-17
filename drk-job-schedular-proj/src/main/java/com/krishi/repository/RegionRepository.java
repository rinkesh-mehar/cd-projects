package com.krishi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import com.krishi.entity.Commodity;
import com.krishi.entity.Region;


public interface RegionRepository
  extends JpaRepository<Region , Integer> , QueryByExampleExecutor<Region> {
	
	/* Added for region Id list - Pranay */
	@Query(value = "select distinct id from region", nativeQuery = true)
	List<Integer> getRegionIds();
}