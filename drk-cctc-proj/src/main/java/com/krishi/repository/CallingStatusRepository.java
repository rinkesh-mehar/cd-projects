package com.krishi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.krishi.entity.CallingStatus;
import org.springframework.data.repository.query.Param;

/**
 * @author CDT - Pranay
 */

public interface CallingStatusRepository extends JpaRepository<CallingStatus, Integer> {

	@Query("select cs.callingStatusId, cs.name from CallingStatus cs order by cs.name")
	List<Object[]> findAllCallingStatusList();

	@Query("select cs.name from CallingStatus cs where cs.callingStatusId = :callingStatusId")
	String findNameByCallingStatusId(Integer callingStatusId);

	@Query("select cs.callingStatusId from CallingStatus cs where cs.name = :name")
	Integer findIdByName(String name);

	@Query("select cs from CallingStatus cs where cs.status = :status order by cs.name")
	List<CallingStatus> findCallingStatusForCrop(Integer status);

}
