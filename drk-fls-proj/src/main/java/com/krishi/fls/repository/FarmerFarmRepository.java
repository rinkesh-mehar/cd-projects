package com.krishi.fls.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.krishi.fls.entity.FarmerFarm;

public interface FarmerFarmRepository extends JpaRepository<FarmerFarm, String> {

	/*
	 * @Query(name="select f from FarmerFarm  f where f.id in (:farmId)")
	 * List<FarmerFarm> findby(@Param("farmId") List<String> farmId);
	 */
	
	@Query("Select f from FarmerFarm f where f.id=:farmId")
	public FarmerFarm findbyfarmId(@Param(value = "farmId") String farmId);
	
}
