package com.krishi.fls.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krishi.fls.entity.VillageInfo;

public interface VillageInfoRepository extends JpaRepository<VillageInfo, String> {

	List<VillageInfo> findByVillageIdIn(List<Integer> villageId);

}
