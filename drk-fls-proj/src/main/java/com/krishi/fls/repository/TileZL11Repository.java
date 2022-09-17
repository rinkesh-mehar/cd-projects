package com.krishi.fls.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.krishi.fls.entity.TileZL11;

/**
 * @author CDT-Ujwal
 *
 */

@Repository
public interface TileZL11Repository extends JpaRepository<TileZL11, String> {

	@Query("select tzl from TileZL11 as tzl inner join Users as u on u.regionId = tzl.regionId "+
			" where u.id = :userId")
	List<TileZL11> findTileZl11ByRegion(@Param("userId") Integer userId);

	@Query("select distinct tz.aczId from TileZL11 tz where tz.regionId = :regionId")
	Set<Integer> getAczIdByRegion(@Param("regionId") Integer regionId);
}
