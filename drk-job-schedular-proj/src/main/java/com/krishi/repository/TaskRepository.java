package com.krishi.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.krishi.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Integer>{

	//update taskTypeId (1,2,3,4,5) to (3,4,5,6,7) and removed status - 27/09/21
//	@Query("from Task where (taskTypeId IN (1,2,3,4,5) AND status IN (0,1,4) AND taskDate < CURRENT_DATE) and farmerCropInfoId is not NULL")
	@Query("from Task where (taskTypeId IN (3,4,5,6,7) AND status IN (0,1,4) AND taskDate < CURRENT_DATE) and farmerCropInfoId is not NULL")
	public List<Task> taskPending();

	/** Not Used*/
	/*@Query(value = "select any_value(t.id) as id, any_value(t.task_date) as taskDate, any_value(t.start_time) as startTime, any_value(t.end_time) as endTime, any_value(t.task_type_id) as taskTypeId,  any_value(t.entity_type_id) as entityTypeId,\n" +
			"t.entity_id as entityId, any_value(t.pushback) as pushback,any_value(t.ordering) as ordering ,fci.lead_calling_status as leadCallingStatus from dev.task t\n" +
			"inner join dev.farmer_crop_info fci on fci.farmer_id = t.entity_id\n" +
			"where (t.task_type_id IN (1,2,3,4,5) AND t.status in(1,2)  AND t.task_date <= curdate()) OR t.status=0 and fci.lead_calling_status =0\n" +
			"group by t.entity_id, fci.lead_calling_status", nativeQuery = true)*/
	@Query(value = "select any_value(t.id) as id, any_value(t.task_date) as taskDate, /*any_value(t.start_time) as startTime, any_value(t.end_time) as endTime,*/ any_value(t.task_type_id) as taskTypeId,\n" +
			"t.entity_id as entityId, any_value(t.assignee_id) as assigneeId, any_value(t.pushback) as pushback,any_value(t.ordering) as ordering ,fci.lead_calling_status as leadCallingStatus from task t\n" +
			"inner join farmer_crop_info fci on fci.farmer_id = t.entity_id\n" +
			"where (t.task_type_id IN (1,2,3,4,5) AND t.status in (0,1,3,4) AND t.task_date <= curdate()) and fci.lead_calling_status=1\n" +
			"group by t.entity_id, fci.lead_calling_status;", nativeQuery = true)
	public List<Object[]> getTaskPending();
	
	/** get pushback using taskId : Ujwal */
	@Query(value= "select t.pushback from Task as t where t.id = ?1")
	public Integer getPushback(String id);
}
	