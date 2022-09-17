package com.krishi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.krishi.entity.Farmer;

public interface FarmerRepository extends JpaRepository<Farmer, String>{
	
//	@Query("select f from Farmer f where drkCust=0 and farmerId not "
//			+ " in (select t.entityId from Task t where t.entityTypeId = 4)")
@Query("select f from Farmer f " +
		"inner join FarmerCropInfo  fci on fci.farmerId= f.farmerId where fci.leadCallingStatus=0 and f.farmerId not "
		+ " in (select t.entityId from Task t where t.entityTypeId = 4) group by f.farmerId")
	List<Farmer> findNewFarmer();

	@Query("select f from Farmer f inner join FarmerFarm ff on f.id = ff.farmerId inner join FarmCase fc on ff.id = fc.farmId where fc.id = :caseId")
	Farmer findByCaseId(@Param("caseId") String caseId);
	
	
	@Query("select f from Farmer f where f.farmerId= :farmerId")
	Farmer findByfarmerId(@Param("farmerId") String farmerId);
	
	/** added for existing farmer */
	@Query("select f from Farmer f where f.primaryMobNumber= :primaryMobileNumber")
	Farmer findByPrimaryMobile(@Param("primaryMobileNumber") String primaryMobileNumber);


	@Query("select f from Farmer f \n" +
			"inner join FarmerFarm ff on ff.farmerId = f.farmerId \n " +
			"inner join FarmCase  fc on fc.farmId = ff.id \n"+
			"where fc.id =:caseId")
	Farmer findFarmerByCaseId(@Param("caseId") String caseId);

}
