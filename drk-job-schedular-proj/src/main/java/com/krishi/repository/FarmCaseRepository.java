package com.krishi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.krishi.entity.FarmCase;

public interface FarmCaseRepository extends JpaRepository<FarmCase, String> {

	
	@Query("select f from FarmCase f where f.id=:caseID" )
	public FarmCase getBycaseID(@Param(value = "caseID") String caseID);
	
	List<FarmCase> findAllByFarmId(String id);
	
//	@Query("select f from FarmCase f where f.simpleNdviLastSync is null or DATEDIFF(CURDATE(), f.simpleNdviLastSync) <= 7" )
@Query("select f from FarmCase f where f.simpleNdviLastSync is null" )
	public List<FarmCase> findSimpleNdviToSync();

}
