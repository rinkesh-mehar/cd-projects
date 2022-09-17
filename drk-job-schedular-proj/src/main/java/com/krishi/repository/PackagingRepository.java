package com.krishi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.krishi.entity.PackagingType;
import com.krishi.model.DataInsertionModel;

/**
 * @author CDT-Ujwal
 */

public interface PackagingRepository extends JpaRepository<PackagingType, Integer> {

	@Query(value = "select id, name from packaging_type", nativeQuery = true)
	List<DataInsertionModel> getPackagingData();
}
