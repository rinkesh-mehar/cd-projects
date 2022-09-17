package com.krishi.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.krishi.entity.Task;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface TaskRepository extends JpaRepository<Task, String> {

	List<Task> findByStatusAndTaskTypeIdIn(Integer status, List<Integer> taskTypeIds);

	List<Task> findByTaskTypeIdAndStatusAndTaskDateLessThanEqual(Integer taskTypeId, Integer status, Date date, Pageable page);

	@Query("select distinct t from Task t where t.taskTypeId = 10 and t.farmerCropInfoId = :farmerCropInfoId")
	Task getTaskByFarmerCropInfoId(@Param("farmerCropInfoId") String farmerCropInfoId);


	@Query("select distinct count(t.farmerCropInfoId) from Task t where t.entityId = :farmerId and t.farmerCropInfoId != 0")
	int getAllFarmerCropInfoIds(@Param("farmerId") String farmerId);

	@Transactional
	@Modifying
	@Query("delete from Task t where t.id = :taskId")
	int deleteExtraTask(@Param("taskId") String taskId);
	
}
