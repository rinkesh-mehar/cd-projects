package com.krishi.repository;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.krishi.entity.ViewNonTechnicalCallingList;

public interface ViewNonTechnicalCallingListRepository extends DataTablesRepository<ViewNonTechnicalCallingList, Integer>  {

	@Query("SELECT l.stateId, l.stateName FROM ViewNonTechnicalCallingList l group by l.stateId, l.stateName")
	List<Object[]> findAllStateList();

	@Query("select l.regionId, l.regionName from ViewNonTechnicalCallingList l where l.stateId=:stateId group by l.regionId, l.regionName")
	List<Object[]> findAllRegionList(@Param("stateId") Integer stateId);

	@Query("select l.districtId, l.districtName from ViewNonTechnicalCallingList l where l.regionId=:regionId group by l.districtId, l.districtName")
	List<Object[]> findAllDistrictList(@Param("regionId") Integer regionId);
	
	@Query("select l.villageId, l.villageName from ViewNonTechnicalCallingList l where l.districtId=:districtId group by l.villageId, l.villageName")
	List<Object[]> findAllVillageList(@Param("districtId") Integer districtId);

}
