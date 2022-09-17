package com.krishi.fls.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krishi.fls.entity.TaskHealthDetails;
import com.krishi.fls.entity.TaskHealthPhoto;

public interface TaskHealthDetailsRepository extends JpaRepository<TaskHealthDetails, String> {

}
