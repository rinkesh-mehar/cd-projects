package com.drk.tools.drkrishi.repository;

import com.drk.tools.drkrishi.model.Village;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author RinkeshKM
 * @Date 09/09/20
 */

@Repository
public interface VillageRepository extends JpaRepository<Village, Integer> {

	@Query(value = "select distinct a.id as ID,a.name ,a.region_id as regionID,b.village_id as villageID,a.latitude,a.longitude from village a,\n"
			+ "prs_task b where a.id=b.village_id and a.region_id=?1", nativeQuery = true)
	List<Map<String, Object>> findAllByRegionId(Integer regionId);

//	@Query(value = "select Distinct id as ID,name from region order by name", nativeQuery = true)
	@Query(value = "select Distinct RegionID as ID, Name as name from cdt_master_data.geo_region order by Name", nativeQuery = true)
	List<Map<String, Object>> getAllRegion();

	@Query(value = "select id as ID,farmer_name as name,primary_mob_number as mobileNumber from farmer where village_id=?1", nativeQuery = true)
	List<Map<String, Object>> getAllFarmersByVillage(Integer villageId);
}
