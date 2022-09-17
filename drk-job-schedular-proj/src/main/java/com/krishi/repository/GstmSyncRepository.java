package com.krishi.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.krishi.entity.GstmSync;

public interface GstmSyncRepository extends JpaRepository<GstmSync, Integer> {

	 public GstmSync findBySchemaName(String schemaName);
}
