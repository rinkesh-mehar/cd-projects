package com.drkrishi.prm.repository;

import com.drkrishi.prm.entity.RegionEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.List;

public interface RegionRepository  extends CrudRepository<RegionEntity, Integer>,
QueryByExampleExecutor<RegionEntity> {

	@Query("SELECT r FROM RegionEntity AS r where r.stateId = :stateCode")
	List<RegionEntity> findByState(@Param(value ="stateCode") int code);

	
	
}
