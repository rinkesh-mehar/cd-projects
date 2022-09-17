package com.krishi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.krishi.entity.Vip;

public interface VipRepository extends JpaRepository<Vip, Integer> {

	@Modifying
	@Query("UPDATE Vip v SET v.status=:statusId, v.vipDesignation = :designationId, v.otherDesignation = :otherDesignation WHERE v.farmerId = :farmerId")
	Integer updateStatusAndVipDesignation(@Param("statusId") Integer statusId, 
			@Param("designationId") Integer designationId, @Param("otherDesignation") String otherDesignation,
			@Param("farmerId") String farmerId);

	Vip findByFarmerId(String entityId);

}
