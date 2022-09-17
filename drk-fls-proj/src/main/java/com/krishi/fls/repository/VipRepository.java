package com.krishi.fls.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.krishi.fls.entity.Vip;

public interface VipRepository extends JpaRepository<Vip, Integer> {

//	@Query(name="select v from Vip where v.villageId in(:villageId)")
//	public List<Vip> findbyVillageId(@Param("villageId")  );
	
	public List<Vip> findByVillageIdIn(List<Integer> villageId);
}
