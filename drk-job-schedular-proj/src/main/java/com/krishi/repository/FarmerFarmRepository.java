package com.krishi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.krishi.entity.FarmerFarm;

public interface FarmerFarmRepository extends JpaRepository<FarmerFarm, String> {

	List<FarmerFarm> findByFarmerId(String farmerId);
	
	@Query("Select f from FarmerFarm f where f.id=:farmId")
	public FarmerFarm findbyfarmId(@Param(value = "farmId") String farmId);

}
