package com.krishi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import com.krishi.entity.Machinery;

public interface MachineryRepository extends JpaRepository<Machinery, Integer>, QueryByExampleExecutor<Machinery> {
}