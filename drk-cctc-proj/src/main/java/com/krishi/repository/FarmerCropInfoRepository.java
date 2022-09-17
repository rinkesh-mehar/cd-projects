package com.krishi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.krishi.entity.FarmerCropInfo;


/**
 * @author CDT-Ujwal
 *
 */
@Repository
public interface FarmerCropInfoRepository extends JpaRepository<FarmerCropInfo, String>  {

	@Query("select fc from FarmerCropInfo as fc, Task as t where t.entityId = fc.farmerId and t.id = :taskId")
	List<FarmerCropInfo> findByTaskId(@Param("taskId")String taskId);
	
	
	@Query("select fc from FarmerCropInfo as fc where fc.farmerId = :farmerId\n"
			+ "and fc.commodityId =:commodityId and fc.varietyId =:varietyId")
	FarmerCropInfo existRecordForSpec(@Param("farmerId") String farmerId,
			/* @Param("seasonId") Integer seasonId, */
												@Param("commodityId") Integer commodityid, 
												@Param("varietyId") Integer varietyId);
	
	Optional<FarmerCropInfo> findCropInfoByCropInfoId(String farmerCropId);

	@Query("select fc from FarmerCropInfo  as fc where fc.cropInfoId in (:cropInfoIds)")
	List<FarmerCropInfo> getFarmerCropInfoByCropInfoIds(@Param("cropInfoIds") List<String> cropInfos);

	@Query("select count(fc.cropInfoId) from FarmerCropInfo as fc where fc.farmerId = :farmerId")
	Integer getFarmerCropCount(@Param("farmerId") String farmerId);
}
