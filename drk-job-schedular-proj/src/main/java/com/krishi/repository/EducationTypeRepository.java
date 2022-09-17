package com.krishi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import com.krishi.entity.EducationType;

public interface EducationTypeRepository extends JpaRepository<EducationType, Integer> , QueryByExampleExecutor<EducationType> {

}
