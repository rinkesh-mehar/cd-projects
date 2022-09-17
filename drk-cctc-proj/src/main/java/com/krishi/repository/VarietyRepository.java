package com.krishi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.krishi.entity.Variety;
/**
 * @author CDT-Ujwal
 *
 */

@Repository
public interface VarietyRepository extends JpaRepository<Variety, Integer> {

	@Query("select v.id, v.name from Variety v where v.commodityId = :commodityId order by v.name")
	List<Object[]> findVarietyByCommdityId(@Param("commodityId") Integer commodityId);
}
