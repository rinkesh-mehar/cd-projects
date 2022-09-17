package com.krishi.fls.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krishi.fls.entity.TaskHistory;

public interface TaskhistoryRepository  extends JpaRepository<TaskHistory, Integer>{

}
