package com.krishi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.krishi.entity.Farmer;

public interface FarmerRepository extends JpaRepository<Farmer, String>{

	@Modifying
	@Query("UPDATE Farmer f SET f.farmerFatherHusbandName = :fatherName,"
			+ " f.alternativeMobNumber = :alterMobile,"
			+ " f.referencePerson = :referPerson,"
			+ " f.referencePersonMobileNumber = :referMob,"
			+ " f.hasGovernmentIdProof = :govtIdProof,"
			+ " f.hasOwnLand = :ownLand,"
			+ " f.hasIrrigationLand = :irrigationLand,"
			+ " f.farmSize = :farmSize,"
			/*+ " f.majorCrop = :majorCrop,"*/
			+ " f.cropArea = :cropArea,"
			+ " f.speakingLanguageId = :speakLang,"
			+ " f.mobileTypeId = :mobType,"
			+ " f.willingnessForCdt = :cdt,"
			+ " f.isVip = :vip,"
			+ " f.meetingPoint = :meetingPoint" // added for POI - Pranay
//			+ " f.farmerName = :farmerName" // added farmerName - Ujwal
			+ " WHERE f.id = :id")
	int updateDetail(@Param("fatherName") String fatherName, 
			@Param("alterMobile") String alterMobile,
			@Param("referPerson") String referPerson,
			@Param("referMob") String referMob,
			@Param("govtIdProof") Integer govtIdProof,
			@Param("irrigationLand") Integer irrigationLand,
			@Param("ownLand") Integer ownLand,
			@Param("farmSize") Double farmSize,
			/*@Param("majorCrop") String majorCrop,*/
			@Param("cropArea") Double cropArea,
			@Param("speakLang") String speakLang,
			@Param("mobType") Integer mobType,
			@Param("cdt") Integer cdt,
			@Param("vip") Integer vip,
			@Param("meetingPoint") String meetingPoint, // added for POI - Pranay
//			@Param("farmerName") String farmerName, // added farmerName - Ujwal
			@Param("id") String id);
	
	@Query("select f from Farmer f join FarmerFarm ff on f.id = ff.farmerId join FarmCase fc on ff.id = fc.farmId where fc.id = :caseId")
	Optional<Farmer> findByCaseId(@Param("caseId") String caseId);

}
