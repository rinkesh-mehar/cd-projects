package com.krishi.fls.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krishi.fls.entity.GovtOfficial;

public interface GovtOfficalRepository extends JpaRepository<GovtOfficial, Integer> {
	
	Set<GovtOfficial> findDistinctByVillageIdIn(List<Integer> villageIds);

}
