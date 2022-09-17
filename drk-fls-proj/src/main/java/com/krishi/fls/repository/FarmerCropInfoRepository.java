package com.krishi.fls.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.krishi.fls.entity.FarmerCropInfo;



/**
 * @author CDT-Ujwal
 *
 */


public interface FarmerCropInfoRepository extends JpaRepository<FarmerCropInfo, String>  {

	 @Query(value = "select group_concat(distinct fc.commodity_id) as major_crop from farmer_crop_info as fc \n"
	 		+ "inner join  farmer as f on fc.farmer_id = f.id and f.id = ?1", nativeQuery = true)
	  public String getMajorCommoditybyFarmerId(String farmerId);
}
