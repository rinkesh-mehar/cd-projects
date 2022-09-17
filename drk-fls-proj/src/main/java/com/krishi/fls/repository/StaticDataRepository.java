package com.krishi.fls.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krishi.fls.entity.StaticData;

public interface StaticDataRepository extends JpaRepository<StaticData, Integer>{

	List<StaticData> findByDataKeyIn(List<String> keys);
	StaticData findByDataKey(String key);



}
