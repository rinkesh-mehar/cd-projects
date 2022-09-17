package com.krishi.fls.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.krishi.fls.entity.MbepEntity;

public interface MbepRepository extends JpaRepository<MbepEntity, Integer> {

	@Query("select distinct m from Users as u inner join MbepEntity "
			+ " as m on u.stateId = m.stateCode and u.regionId = m.regionId where u.id = :userId and m.status = 1")
	public List<MbepEntity> findMbepEntity(@Param("userId") Integer userId);
}
