package com.krishi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.krishi.entity.AlliedActivity;
import com.krishi.model.DataInsertionModel;

/**
 * @author CDT - Pranay
 */

public interface AlliedActivityRepository extends JpaRepository<AlliedActivity, Integer> {

	@Query(value = "select id, name from agri_allied_activity", nativeQuery = true)
	List<DataInsertionModel> getAlliedActivityData();
}
