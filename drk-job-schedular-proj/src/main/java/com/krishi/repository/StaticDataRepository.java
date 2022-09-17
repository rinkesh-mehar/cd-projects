package com.krishi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krishi.entity.StaticData;

public interface StaticDataRepository extends JpaRepository<StaticData, Integer>{

	StaticData findByKey(String key);
	List<StaticData> findByKeyIn(List<String> keys);
}
