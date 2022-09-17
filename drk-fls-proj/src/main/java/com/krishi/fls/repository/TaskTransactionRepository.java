package com.krishi.fls.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krishi.fls.entity.TaskTransaction;

public interface TaskTransactionRepository extends JpaRepository<TaskTransaction, String>{

}
