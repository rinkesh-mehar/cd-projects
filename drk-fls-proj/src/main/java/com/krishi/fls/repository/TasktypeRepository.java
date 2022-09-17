package com.krishi.fls.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import com.krishi.fls.entity.CaseType;
import com.krishi.fls.entity.TaskAllocation;
import com.krishi.fls.entity.TaskType;

public interface TasktypeRepository extends CrudRepository<TaskType, Integer>,
QueryByExampleExecutor<TaskType> {
 
 @Query("select t from TaskType as t where t.parentTaskTypeId in(0,1,2) ")
 public List<TaskType> getTaskList();
 
 @Query("select t from TaskType as t where t.parentTaskTypeId in(3) ")
 public List<TaskType> getAgrigotaList();
 
 @Query("select t from TaskType as t ")
 public List<TaskType> getcompleteTask();
 
 @Query("select t from CaseType as t ")
 public List<CaseType> getcompleteCaseType();
 
 @Query("select t from TaskAllocation as t where t.taskDate = CURRENT_DATE ")
 public List<TaskAllocation> getAllTask();

}
