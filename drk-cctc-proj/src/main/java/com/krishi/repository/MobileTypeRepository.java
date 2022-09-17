package com.krishi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krishi.entity.MobileType;

public interface MobileTypeRepository extends JpaRepository<MobileType, Integer> {

	List<MobileType> findByStatus(Integer status);

}
