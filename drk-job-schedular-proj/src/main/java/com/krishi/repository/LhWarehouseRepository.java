package com.krishi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.krishi.entity.LhWarehouseModel;
import com.krishi.model.DataInsertionModel;

/**
 * @author CDT-Ujwal
 */
public interface LhWarehouseRepository extends JpaRepository<LhWarehouseModel, Integer> {

	@Query(value = "select lh.id,lh.name,lh.address,lh.state_code as stateCode,lh.district_code as districtCode from lh_warehouse as lh "
			+ "where lh.region_id =?1", nativeQuery = true)
	List<DataInsertionModel> LhWarehouseList(Integer districtCode);

	@Query(value = "select distinct r.id  from region as r \n"
			+ "	inner join lh_warehouse as lh on lh.region_id = r.id;", nativeQuery = true)
	List<Integer> regionList();
}
