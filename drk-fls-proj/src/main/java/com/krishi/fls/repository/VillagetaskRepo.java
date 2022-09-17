package com.krishi.fls.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import com.krishi.fls.entity.VillageTask;

public interface VillagetaskRepo extends CrudRepository<VillageTask, Integer>,
QueryByExampleExecutor<VillageTask> {
	
	@Query("SELECT d,v FROM VillageTask AS d,Village as v where d.villagesId=:villageId and v.id=d.villagesId")
	List<VillageTask> findByStateCode(@Param(value = "villageId") int villageId);
}
