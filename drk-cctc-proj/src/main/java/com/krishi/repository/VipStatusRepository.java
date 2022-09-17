package com.krishi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krishi.entity.VipStatus;

public interface VipStatusRepository extends JpaRepository<VipStatus, Integer>{

	List<VipStatus> findByStatus(Integer i);

}
