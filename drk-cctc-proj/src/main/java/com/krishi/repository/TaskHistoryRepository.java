package com.krishi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.krishi.entity.TaskHistory;

public interface TaskHistoryRepository extends JpaRepository<TaskHistory, String> {

	@Query(value = "select count(th.task_id) as count from task_history as th "
			+ "where th.comment in (select name from calling_status where id in (1, 2, 4)) "
			+ "and th.task_id = :taskId group by th.task_id ", nativeQuery = true)
	Integer getCountForCommentByTaskId(String taskId);
	
	TaskHistory findTop1ByTaskIdAndStatusAndTaskTypeIdInOrderByTaskTypeIdDesc(String taskId, Integer status, List<Integer> taskTypeId);
}
