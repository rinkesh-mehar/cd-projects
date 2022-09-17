package com.krishi.repository;

import com.krishi.entity.ViewNonTechnicalCallingListSpot;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ViewNonTechnicalCallingListSpotRepository extends DataTablesRepository<ViewNonTechnicalCallingListSpot, Integer>  {

	@Query("SELECT l.stateId, l.stateName FROM ViewNonTechnicalCallingListSpot l group by l.stateId, l.stateName")
	List<Object[]> findAllStateList();

	@Query("select l.commodityName, l.commodityId from ViewNonTechnicalCallingListSpot l group by l.commodityId, l.commodityName order by l.commodityName")
	List<Object[]> findAllCommodityList();

	@Query("select l.regionId, l.regionName from ViewNonTechnicalCallingListSpot l where l.stateId=:stateId group by l.regionId, l.regionName")
	List<Object[]> findAllRegionList(@Param("stateId") Integer stateId);

	@Query("select l.districtId, l.districtName from ViewNonTechnicalCallingListSpot l where l.regionId=:regionId group by l.districtId, l.districtName")
	List<Object[]> findAllDistrictList(@Param("regionId") Integer regionId);

	@Query("select l.villageId, l.villageName from ViewNonTechnicalCallingListSpot l where l.districtId=:districtId group by l.villageId, l.villageName order by l.villageName")
	List<Object[]> findAllVillageList(@Param("districtId") Integer districtId);

	@Query("select distinct l.sellerType from ViewNonTechnicalCallingListSpot l where l.taskId=:taskId ")
	String findSellerTypeNameByTaskId(@Param("taskId") String taskId);
}
