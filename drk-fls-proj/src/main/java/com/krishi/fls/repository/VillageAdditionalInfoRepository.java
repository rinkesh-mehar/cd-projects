package com.krishi.fls.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krishi.fls.entity.VillageAdditionalInfo;

public interface VillageAdditionalInfoRepository extends JpaRepository<VillageAdditionalInfo, String>{
	List<VillageAdditionalInfo> findByVillageIdIn(List<Integer> villageId);
}
