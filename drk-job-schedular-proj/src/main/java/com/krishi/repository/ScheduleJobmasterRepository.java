package com.krishi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krishi.entity.ScheduleJobmaster;

public interface ScheduleJobmasterRepository extends JpaRepository<ScheduleJobmaster, Integer>{

	ScheduleJobmaster findByName(String name);

}
