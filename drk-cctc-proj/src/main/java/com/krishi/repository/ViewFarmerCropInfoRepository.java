package com.krishi.repository;

import java.util.List;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.krishi.entity.ViewFarmerCropInfo;

/**
 * @author CDT-Ujwal
 *
 */

@Repository
public interface ViewFarmerCropInfoRepository extends JpaRepository<ViewFarmerCropInfo, String>{

	@Query("select vfc from ViewFarmerCropInfo as vfc where vfc.taskId =:taskId and vfc.leadCallingStatus = :callingStatus")
	List<ViewFarmerCropInfo> findByTaskId(@Param("taskId")String taskId, @Param("callingStatus") Integer callingStatus);
	
	@Query("select vfc from ViewFarmerCropInfo as vfc where vfc.cropInfoId =:farmercropId")
	List<ViewFarmerCropInfo> findCropInfoByCropInfoId(@Param("farmercropId") String farmercropId);
}
