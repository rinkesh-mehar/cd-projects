package com.krishi.fls.repository;

import java.util.List;
import java.util.Set;

import com.krishi.fls.entity.TaskAllocation;
import org.springframework.data.jpa.repository.JpaRepository;

import com.krishi.fls.entity.TaskFutureDates;
import org.springframework.data.jpa.repository.Query;

public interface TaskFutureDatesRepository extends JpaRepository<TaskFutureDates, Integer> {

	Set<TaskFutureDates> findByTaskIdIn(List<String> taskIds);

	@Query("select tfd from TaskFutureDates as tfd where tfd.taskId in (:taskIds) and tfd.taskDate > curdate()")
	Set<TaskFutureDates> findAllFutureDatesByTaskId(List<String> taskIds);

}
