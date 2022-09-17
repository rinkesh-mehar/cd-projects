package com.krishi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krishi.entity.VipStatus;

public interface VipStatusRepository extends JpaRepository<VipStatus, Integer>{
}