package com.krishi.fls.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.krishi.fls.entity.PhenophaseDuration;

public interface PhenophaseDurationRepository extends JpaRepository<PhenophaseDuration, Integer>{
	@Query("select distinct pd from Users as u inner join PhenophaseDuration as pd on u.stateId = pd.stateId "
			+ " where  week(curdate()) between pd.sowingStartWeek and pd.harvestEndWeek AND u.id = :userId ")
	public List<PhenophaseDuration> findPhenophaseDurationByStateId(@Param("userId") Integer userId);
	
	
	@Query("select distinct pd.commodityId from PhenophaseDuration as pd inner join Users as u " +
			 " on u.stateId = pd.stateId where u.id = :userId")
	Set<Integer> getCommodityList(@Param("userId") Integer stateId);

	@Query("select distinct pd.aczId from PhenophaseDuration as pd  where pd.stateId = :stateId")
	Set<Integer> getAczIdList(@Param("stateId") Integer stateId);

	@Query("select distinct pd.varietyId from PhenophaseDuration pd  where pd.aczId in (select distinct tz.aczId from TileZL11 tz where tz.regionId = :regionId)")
	Set<Integer> getVarietyIdsByRegion(@Param("regionId") Integer regionId);
}
