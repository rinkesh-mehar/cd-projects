package com.krishi.repository;

import java.util.List;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.krishi.entity.ViewNonTechnicalCallingListForward;
import org.springframework.stereotype.Repository;

@Repository
public interface ViewNonTechnicalCallingListForwardRepository extends DataTablesRepository<ViewNonTechnicalCallingListForward, Integer>  {

	@Query("SELECT l.stateId, l.stateName FROM ViewNonTechnicalCallingListForward l group by l.stateId, l.stateName order by l.stateName")
	List<Object[]> findAllStateList();

	@Query("select l.regionId, l.regionName from ViewNonTechnicalCallingListForward l where l.stateId=:stateId group by l.regionId, l.regionName order by l.regionName")
	List<Object[]> findAllRegionList(@Param("stateId") Integer stateId);

	@Query("select l.districtId, l.districtName from ViewNonTechnicalCallingListForward l where l.regionId=:regionId group by l.districtId, l.districtName order by l.districtName")
	List<Object[]> findAllDistrictList(@Param("regionId") Integer regionId);

	@Query("select l.commodityId, l.commodityName from ViewNonTechnicalCallingListForward l group by l.commodityId, l.commodityName order by l.commodityName")
	List<Object[]> findAllCommodityList();

	@Query("select l.id, l.name from AgriLandHoldingSize l group by l.id, l.name order by l.id")
	List<Object[]> getLandHoldingSize();
	
	@Query("select l.villageId, l.villageName from ViewNonTechnicalCallingListForward l where l.districtId=:districtId group by l.villageId, l.villageName order by l.villageName")
	List<Object[]> findAllVillageList(@Param("districtId") Integer districtId);

	@Query("select distinct l.sellerType from ViewNonTechnicalCallingListForward l where l.taskId=:taskId ")
	String findSellerTypeNameByTaskId(@Param("taskId") String taskId);
}
