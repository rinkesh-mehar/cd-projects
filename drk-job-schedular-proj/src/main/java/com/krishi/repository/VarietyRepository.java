package com.krishi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krishi.entity.Variety;

public interface VarietyRepository extends JpaRepository<Variety, Integer>{

	Variety getUomByIdAndCommodityId(Integer varietyId, Integer commodityId);
	
   

}
