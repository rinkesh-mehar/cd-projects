package com.krishi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krishi.entity.PlantPart;

public interface PlantPartRepository  extends JpaRepository<PlantPart, Integer> {

}
