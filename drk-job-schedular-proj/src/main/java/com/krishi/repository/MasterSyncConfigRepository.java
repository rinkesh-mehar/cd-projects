package com.krishi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krishi.entity.MasterSyncConfig;


public interface MasterSyncConfigRepository extends JpaRepository<MasterSyncConfig, Integer> {

}
