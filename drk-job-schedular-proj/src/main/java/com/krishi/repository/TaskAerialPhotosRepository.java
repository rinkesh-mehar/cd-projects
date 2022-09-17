package com.krishi.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.krishi.entity.TaskAerialPhotos;

public interface TaskAerialPhotosRepository extends JpaRepository<TaskAerialPhotos, String>{

	
	@Query("SELECT ta FROM TaskAerialPhotos ta,TaskHistory th, Task t where ta.taskId = t.id and t.id = th.taskId and th.taskTypeId = 5 and t.entityId =:caseId")
	List<TaskAerialPhotos> findByCaseId(@Param(value ="caseId")String caseId, Pageable page);
	
}
