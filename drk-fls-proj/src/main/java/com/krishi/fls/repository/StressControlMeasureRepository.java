package com.krishi.fls.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krishi.fls.entity.StressControlMeasure;



public interface StressControlMeasureRepository
  extends JpaRepository<StressControlMeasure, Integer> {
}