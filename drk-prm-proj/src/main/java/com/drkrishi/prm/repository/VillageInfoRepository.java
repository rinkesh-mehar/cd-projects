package com.drkrishi.prm.repository;

import com.drkrishi.prm.entity.Village_InfoEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface VillageInfoRepository extends CrudRepository<Village_InfoEntity, Integer>,
QueryByExampleExecutor<Village_InfoEntity> {

	/*
	 * @Query("SELECT v FROM Village_InfoEntity AS v where v.village.village_id = :villageCode"
	 * ) List<Village_InfoEntity> findByvillageCode(@Param(value = "villageCode")
	 * int villageCode);
	 */

}
