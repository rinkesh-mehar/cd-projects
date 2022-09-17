package com.drkrishi.usermanagement.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.drkrishi.usermanagement.entity.StaticData;

public interface StaticDataReository extends JpaRepository<StaticData, Integer> {

	StaticData findByKey(String string);

}
