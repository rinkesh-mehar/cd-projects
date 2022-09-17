package com.krishi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krishi.entity.FarmerGeneralInfo;

public interface FarmerGeneralInfoRepository extends JpaRepository<FarmerGeneralInfo, String>{

	List<FarmerGeneralInfo> findByFarmerIdIn(List<String> farmerId);

	FarmerGeneralInfo findByFarmerId(String id);

}
