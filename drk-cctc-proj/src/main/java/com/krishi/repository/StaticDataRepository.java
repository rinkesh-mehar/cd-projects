package com.krishi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krishi.entity.StaticData;

public interface StaticDataRepository extends JpaRepository<StaticData, Integer>{
	
	List<StaticData> findByDataKeyIn(List<String> key);

	StaticData findByDataKey(String string);

}
