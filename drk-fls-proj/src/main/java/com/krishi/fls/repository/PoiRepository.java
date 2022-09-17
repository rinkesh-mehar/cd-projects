package com.krishi.fls.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krishi.fls.entity.Poi;

public interface PoiRepository extends JpaRepository<Poi, Integer> {

	Set<Poi> findDistinctByVillageIdIn(List<Integer> villageIds);

}
