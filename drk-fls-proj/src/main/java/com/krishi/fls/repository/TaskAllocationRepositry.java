package com.krishi.fls.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import com.krishi.fls.entity.TaskAllocation;

public interface TaskAllocationRepositry
		extends CrudRepository<TaskAllocation, Integer>, QueryByExampleExecutor<TaskAllocation> {

	@Query("select t from TaskAllocation As t,Farmer As f,Village AS v, FarmCase As fc, FarmerFarm As ff WHERE t.entityId = fc.id AND fc.farmId = ff.id AND ff.farmerId = f.farmerId AND t.taskTypeId in (:prioritytype) AND t.assigneeId IS  0 AND f.villageId = v.id AND f.agriotaCust=1 AND v.regionId =:regionId AND t.taskDate <= CURRENT_DATE" )
	public List<TaskAllocation> getAgrigotaTaskData(@Param(value = "prioritytype") List<Integer> prioritytype,
			@Param(value = "regionId") int regionId);

	@Query("select t from TaskAllocation As t,Farmer As f,Village AS v, FarmCase As fc, FarmerFarm As ff WHERE t.entityId = fc.id AND fc.farmId = ff.id AND ff.farmerId = f.farmerId AND t.taskTypeId in(:prioritytype) AND t.assigneeId IS  0 AND f.villageId = v.id AND f.agriotaCust=0 AND f.drkCust=1 AND v.regionId =:regionId AND t.taskDate <= CURRENT_DATE")
	public List<TaskAllocation> getDrkTaskData(@Param(value = "prioritytype") List<Integer> prioritytype,
			@Param(value = "regionId") int regionId);

	@Query("select t from TaskAllocation As t,Farmer As f,Village AS v WHERE t.entityId = f.farmerId AND t.taskTypeId in(:prioritytype) AND t.assigneeId IS  0 AND t.entityId = f.farmerId AND f.villageId = v.id AND f.agriotaCust=0 AND f.drkCust=0 AND f.willingnessForCdt=1 AND v.regionId =:regionId AND t.taskDate <= CURRENT_DATE")
	public List<TaskAllocation> getWillingDrkTaskData(@Param(value = "prioritytype") List<Integer> prioritytype,
			@Param(value = "regionId") int regionId);

	/*
	 * @Query("select t from FlsTask as ft inner join TaskAllocation as t on ft.taskId = t.id where t.status = 0 and t.assigneeId = 0 and ft.regionId = :regionId and ft.assigneeId = :assigneeId"
	 * )
	 * public List<TaskAllocation> getFlsTaskByRegionIdAndAssigneeId(Integer assigneeId);
	 */
	@Query("select t from FlsTask as ft inner join TaskAllocation as t on ft.taskId = t.id where ft.assigneeId = :assigneeId and ft.regionId =:regionId and ft.taskDate = curdate() and t.taskTypeId in (1,2,3,4,5)")
	public List<TaskAllocation> getFlsTaskByAssigneeId(Integer assigneeId, Integer regionId);

	
	/*
	 * @Query("select t from FlsTask as ft inner join TaskAllocation as t on ft.taskId = t.id where t.status = 1 and t.assigneeId = :assigneeId and ft.regionId = :regionId and ft.assigneeId = :assigneeId"
	 * ) public List<TaskAllocation>
	 * getExistingFlsTaskByRegionIdAndAssigneeId(Integer regionId, Integer
	 * assigneeId);
	 */
	 
	 
	/*
	 * @Query("select t from FlsTask as ft inner join TaskAllocation as t on ft.taskId = t.id where t.status = 1 and t.assigneeId = :assigneeId and ft.regionId = :regionId and ft.assigneeId = :assigneeId and ft.taskDate = curdate()"
	 * ) public List<TaskAllocation>
	 * getExistingFlsTaskByRegionIdAndAssigneeId(Integer regionId, Integer
	 * assigneeId);
	 */

	@Query("select t from FlsTask as ft inner join TaskAllocation as t on ft.taskId = t.id where ft.assigneeId = :assigneeId and ft.regionId = :regionId and ft.taskDate = curdate() and t.taskTypeId in (1,2,3,4,5)")
	public List<TaskAllocation> getExistingFlsTaskByAssigneeId(Integer assigneeId, Integer regionId);
}
