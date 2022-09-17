package com.krishi.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import com.krishi.entity.FlsTask;

public interface FlsTaskRepository extends JpaRepository<FlsTask , Integer> , QueryByExampleExecutor<FlsTask>  {

	@Modifying
    @Query( value = "truncate table fls_task", nativeQuery = true)
	void truncateFlsTask();
	
	@Query("select taskId from FlsTask where regionId = :regionId")
	public Set<String> getFlsTaskByRegionId(Integer regionId);
	

}
