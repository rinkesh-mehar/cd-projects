package com.krishi.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import com.krishi.entity.TaskAllocation;

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
	
	@Query("select t from TaskAllocation As t,Farmer As f,Village AS v, FarmCase As fc, FarmerFarm As ff WHERE t.entityId = fc.id AND fc.farmId = ff.id AND ff.farmerId = f.farmerId AND t.taskTypeId in (:prioritytype) AND t.assigneeId IS  0 AND f.villageId = v.id AND f.agriotaCust=1 AND v.regionId =:regionId AND t.taskDate <= CURRENT_DATE AND t.id NOT IN (:taskIds)" )
	public List<TaskAllocation> getAgrigotaTaskDataWithTask(@Param(value = "prioritytype") List<Integer> prioritytype,
			@Param(value = "regionId") int regionId, @Param(value = "taskIds") Set<String> taskIds);
	
	@Query("select t from TaskAllocation As t,Farmer As f,Village AS v, FarmCase As fc, FarmerFarm As ff WHERE t.entityId = fc.id AND fc.farmId = ff.id AND ff.farmerId = f.farmerId AND t.taskTypeId in(:prioritytype) AND t.assigneeId IS  0 AND f.villageId = v.id AND f.agriotaCust=0 AND f.drkCust=1 AND v.regionId =:regionId AND t.taskDate <= CURRENT_DATE AND t.id NOT IN (:taskIds)")
	public List<TaskAllocation> getDrkTaskDataWithTask(@Param(value = "prioritytype") List<Integer> prioritytype,
			@Param(value = "regionId") int regionId, @Param(value = "taskIds") Set<String> taskIds);

	@Query("select t from TaskAllocation As t,Farmer As f,Village AS v WHERE t.entityId = f.farmerId AND t.taskTypeId in(:prioritytype) AND t.assigneeId IS  0 AND t.entityId = f.farmerId AND f.villageId = v.id AND f.agriotaCust=0 AND f.drkCust=0 AND f.willingnessForCdt=1 AND v.regionId =:regionId AND t.taskDate <= CURRENT_DATE AND t.id NOT IN (:taskIds)")
	public List<TaskAllocation> getWillingDrkTaskDataWithTask(@Param(value = "prioritytype") List<Integer> prioritytype,
			@Param(value = "regionId") int regionId, @Param(value = "taskIds") Set<String> taskIds);
	
	@Query("select t from TaskAllocation As t,Farmer As f,Village AS v WHERE t.entityId = f.farmerId AND t.taskTypeId in(:prioritytype) AND t.assigneeId = 0 AND t.entityId = f.farmerId AND f.villageId = v.id AND (f.agriotaCust=1 OR f.drkCust=1 OR f.willingnessForCdt=1) AND v.regionId =:regionId AND t.taskDate <= CURRENT_DATE")
	public List<TaskAllocation> getTaskData(@Param(value = "prioritytype") List<Integer> prioritytype,
			@Param(value = "regionId") int regionId);
	
	@Query("select t from TaskAllocation As t,Farmer As f,Village AS v WHERE t.entityId = f.farmerId AND t.taskTypeId in(:prioritytype) AND t.assigneeId = 0 AND t.entityId = f.farmerId AND f.villageId = v.id AND (f.agriotaCust=1 OR f.drkCust=1 OR f.willingnessForCdt=1) AND v.regionId =:regionId AND t.taskDate <= CURRENT_DATE AND t.id NOT IN (:taskIds)")
	public List<TaskAllocation> getTaskDataWithTask(@Param(value = "prioritytype") List<Integer> prioritytype,
			@Param(value = "regionId") int regionId, @Param(value = "taskIds") Set<String> taskIds);
	
	
	
}
