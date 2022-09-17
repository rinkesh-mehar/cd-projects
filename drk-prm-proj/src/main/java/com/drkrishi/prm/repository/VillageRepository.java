package com.drkrishi.prm.repository;

import com.drkrishi.prm.entity.VillageEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.List;

public interface VillageRepository extends CrudRepository<VillageEntity, Integer>,
QueryByExampleExecutor<VillageEntity> {
	

	@Query("SELECT t FROM VillageEntity AS t where t.code = :villageCode")
	List<VillageEntity> findByVillageCode(@Param(value = "villageCode") int villageCode);

	

}
