package com.krishi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krishi.entity.TaskType;

public interface TaskTypeRepository extends JpaRepository<TaskType, Integer>{

}
