package com.krishi.fls.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.transaction.annotation.Transactional;

import com.krishi.fls.entity.FlsTask;
import com.krishi.fls.entity.TaskAllocation;

public interface FlsTaskRepository extends JpaRepository<FlsTask , Integer> , QueryByExampleExecutor<FlsTask>  {

	@Transactional
	@Modifying
	 
	/*
	 * @Query(value =
	 * "update fls_task set assignee_id = :userId where region_id = :regionId and batch_number = (select *  from fls_task where region_id = 1 and task_date = curdate() and batch_number in (select min(ft.batch_number) from fls_task as ft where ft.region_id = :regionId and ft.assignee_id is null))"
	 * , nativeQuery = true)
	 */
	@Query(value = "update fls_task set assignee_id = :userId where region_id = :regionId and batch_number = (select * from (select min(ft.batch_number) from fls_task as ft where ft.region_id = :regionId and task_date = curdate() and ft.assignee_id is null) as s )", nativeQuery = true)
	Integer updateFlsTaskByRegionId(Integer regionId, Integer userId);
	
}
