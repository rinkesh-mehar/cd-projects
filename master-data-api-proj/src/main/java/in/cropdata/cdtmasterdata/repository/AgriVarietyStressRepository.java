package in.cropdata.cdtmasterdata.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.cdtmasterdata.dto.interfaces.AgriVarietyStressInfDto;
import in.cropdata.cdtmasterdata.model.AgriVarietyStress;

public interface AgriVarietyStressRepository extends JpaRepository<AgriVarietyStress, Integer> {

//	String queryForGetAgrivarietyStress = "SELECT AVS.ID,AVS.StateCode,AVS.RegionID,AVS.CommodityID,AVS.StressTypeID,AVS.VarietyID,\n"
//			+ "AVS.ResistantStressID, AVS.SusceptibleStressID,AVS.TolerantStressID,AVS.UpdatedAt,\n"
//			+ "AVS.CreatedAt, AVS.Status,AVS.Description,GS.Name as State,AC.Name as Commodity,\n"
//			+ "GR.Name as Region, AV.Name as Variety,AST.Name as StressType,BSR.Name as ResistantStress,\n"
//			+ "BSS.Name as SusceptibleStress, BST.Name as TolerantStress\n" + "FROM agri_variety_stress AVS \n"
//			+ "LEFT JOIN geo_state GS ON (AVS.StateCode = GS.StateCode)\n"
//			+ "LEFT JOIN agri_commodity AC ON (AVS.CommodityID = AC.ID)\n"
//			+ "LEFT JOIN geo_region GR ON (AVS.RegionID = GR.RegionID)\n"
//			+ "LEFT JOIN agri_variety AV ON (AVS.VarietyID = AV.ID)\n"
//			+ "LEFT JOIN agri_stress_type AST ON (AVS.StressTypeID = AST.ID)\n"
//			+ "LEFT JOIN agri_biotic_stress BSR ON (AVS.ResistantStressID = BSR.ID)\n"
//			+ "LEFT JOIN agri_biotic_stress BSS ON (AVS.SusceptibleStressID = BSS.ID)\n"
//			+ "LEFT JOIN agri_biotic_stress BST ON (AVS.TolerantStressID = BST.ID)";

//	@Query(value = queryForGetAgrivarietyStress, nativeQuery = true)
//	List<AgriVarietyStressInfDto> getAgrivarietyStress();

//	@Query(value = queryForGetAgrivarietyStress, countQuery = "SELECT count(AVS.ID) FROM agri_variety_stress AVS \n"
//			+ "LEFT JOIN geo_state GS ON (AVS.StateCode = GS.StateCode)\n"
//			+ "LEFT JOIN agri_commodity AC ON (AVS.CommodityID = AC.ID)\n"
//			+ "LEFT JOIN geo_region GR ON (AVS.RegionID = GR.RegionID)\n"
//			+ "LEFT JOIN agri_variety AV ON (AVS.VarietyID = AV.ID)\n"
//			+ "LEFT JOIN agri_stress_type AST ON (AVS.StressTypeID = AST.ID)\n"
//			+ "LEFT JOIN agri_biotic_stress BSR ON (AVS.ResistantStressID = BSR.ID)\n"
//			+ "LEFT JOIN agri_biotic_stress BSS ON (AVS.SusceptibleStressID = BSS.ID)\n"
//			+ "LEFT JOIN agri_biotic_stress BST ON (AVS.TolerantStressID = BST.ID)", nativeQuery = true)
//	Page<AgriVarietyStressInfDto> getAgrivarietyStress(Pageable sortedByIdDesc);
	
	
	@Query(value = "SELECT  AVS.* ,AV.Name ,GS.Name as State,AC.Name as Commodity, GR.Name as Region, AV.Name as Variety,AST.Name as StressType,\n" + 
			"								(SELECT GROUP_CONCAT(agri_stress.Name SEPARATOR ', ') as AVRSName FROM agri_variety_stress_resistant_stress AVRS\n" + 
			"										 LEFT JOIN agri_stress agri_stress ON (AVRS.StressID = agri_stress.ID) where AVRS.VarietyStressID =AVS.ID) as ResistantStress,\n" + 
			"								(SELECT GROUP_CONCAT(agri_stress.Name SEPARATOR ', ') as AVSSName FROM agri_variety_stress_susceptible_stress AVSS \n" + 
			"										LEFT JOIN agri_stress agri_stress ON (AVSS.StressID = agri_stress.ID) where AVSS.VarietyStressID =AVS.ID) as SusceptibleStress,\n" + 
			"								(SELECT GROUP_CONCAT(agri_stress.Name SEPARATOR ', ') as AVTSName FROM agri_variety_stress_tolerant_stress AVTS \n" + 
			"										LEFT JOIN agri_stress agri_stress ON (AVTS.StressID = agri_stress.ID) where AVTS.VarietyStressID =AVS.ID) as TolerantStress \n" + 
			"								FROM cdt_master_data.agri_variety_stress AVS \n" + 
			"								LEFT JOIN geo_state GS ON (AVS.StateCode = GS.StateCode) \n" + 
			"								LEFT JOIN agri_commodity AC ON (AVS.CommodityID = AC.ID) \n" + 
			"								LEFT JOIN geo_region GR ON (AVS.RegionID = GR.RegionID) \n" + 
			"								LEFT JOIN agri_variety AV ON (AVS.VarietyID = AV.ID) \n" + 
			"								LEFT JOIN agri_stress_type AST ON (AVS.StressTypeID = AST.ID)\n" + 
			"								 WHERE  AV.Name like :searchText\n" + 
			"								 Or  AC.Name like :searchText\n" + 
			"								 Or  GS.Name like :searchText\n" + 
			"								 Or  AST.Name like :searchText\n" + 
			"								 Or  AVS.Description like :searchText \n" + 
			"					             order by AVS.ID desc",
			countQuery = "SELECT  AVS.* ,AV.Name ,GS.Name as State,AC.Name as Commodity, GR.Name as Region, AV.Name as Variety,AST.Name as StressType,\n" + 
					"								(SELECT GROUP_CONCAT(agri_stress.Name SEPARATOR ', ') as AVRSName FROM agri_variety_stress_resistant_stress AVRS\n" + 
					"										 LEFT JOIN agri_stress agri_stress ON (AVRS.StressID = agri_stress.ID) where AVRS.VarietyStressID =AVS.ID) as ResistantStress,\n" + 
					"								(SELECT GROUP_CONCAT(agri_stress.Name SEPARATOR ', ') as AVSSName FROM agri_variety_stress_susceptible_stress AVSS \n" + 
					"										LEFT JOIN agri_stress agri_stress ON (AVSS.StressID = agri_stress.ID) where AVSS.VarietyStressID =AVS.ID) as SusceptibleStress,\n" + 
					"								(SELECT GROUP_CONCAT(agri_stress.Name SEPARATOR ', ') as AVTSName FROM agri_variety_stress_tolerant_stress AVTS \n" + 
					"										LEFT JOIN agri_stress agri_stress ON (AVTS.StressID = agri_stress.ID) where AVTS.VarietyStressID =AVS.ID) as TolerantStress \n" + 
					"								FROM cdt_master_data.agri_variety_stress AVS \n" + 
					"								LEFT JOIN geo_state GS ON (AVS.StateCode = GS.StateCode) \n" + 
					"								LEFT JOIN agri_commodity AC ON (AVS.CommodityID = AC.ID) \n" + 
					"								LEFT JOIN geo_region GR ON (AVS.RegionID = GR.RegionID) \n" + 
					"								LEFT JOIN agri_variety AV ON (AVS.VarietyID = AV.ID) \n" + 
					"								LEFT JOIN agri_stress_type AST ON (AVS.StressTypeID = AST.ID)\n" + 
					"								 WHERE  AV.Name like :searchText\n" + 
					"								 Or  AC.Name like :searchText\n" + 
					"								 Or  GS.Name like :searchText\n" + 
					"								 Or  AST.Name like :searchText\n" + 
					"								 Or  AVS.Description like :searchText \n" + 
					"					             order by AVS.ID desc", nativeQuery = true)
	Page<AgriVarietyStressInfDto> getAgrivarietyStress(Pageable sortedByIdDesc, String searchText);
	
	@Query(value = "SELECT  AVS.* ,AV.Name ,GS.Name as State,AC.Name as Commodity, GR.Name as Region, AV.Name as Variety,AST.Name as StressType,\n" +
			"			(SELECT GROUP_CONCAT(ACS.Name SEPARATOR ', ') as AVRSName FROM agri_variety_stress_resistant_stress AVRS\n" +
			"					 LEFT JOIN agri_stress ACS ON (AVRS.StressID = ACS.ID) where AVRS.VarietyStressID =AVS.ID) as ResistantStress,\n" +
			"			(SELECT GROUP_CONCAT(ACS.Name SEPARATOR ', ') as AVSSName FROM agri_variety_stress_susceptible_stress AVSS \n" +
			"					LEFT JOIN agri_stress ACS ON (AVSS.StressID = ACS.ID) where AVSS.VarietyStressID =AVS.ID) as SusceptibleStress,\n" +
			"			(SELECT GROUP_CONCAT(ACS.Name SEPARATOR ', ') as AVTSName FROM agri_variety_stress_tolerant_stress AVTS \n" +
			"					LEFT JOIN agri_stress ACS ON (AVTS.StressID = ACS.ID) where AVTS.VarietyStressID =AVS.ID) as TolerantStress \n" +
			"			FROM cdt_master_data.agri_variety_stress_missing AVS \n" + 
			"			LEFT JOIN geo_state GS ON (AVS.StateCode = GS.StateCode) \n" + 
			"			LEFT JOIN agri_commodity AC ON (AVS.CommodityID = AC.ID) \n" + 
			"			LEFT JOIN geo_region GR ON (AVS.RegionID = GR.RegionID) \n" + 
			"			LEFT JOIN agri_variety AV ON (AVS.VarietyID = AV.ID) \n" + 
			"			LEFT JOIN agri_stress_type AST ON (AVS.StressTypeID = AST.ID)\n" + 
			"			 WHERE  AV.Name like :searchText\n" + 
			"			 Or  AC.Name like :searchText\n" + 
			"			 Or  GS.Name like :searchText\n" + 
			"			 Or  AST.Name like :searchText\n" + 
			"			 Or  AVS.Description like :searchText\n" + 
			"             order by AVS.ID desc",
			countQuery = "SELECT  AVS.* ,AV.Name ,GS.Name as State,AC.Name as Commodity, GR.Name as Region, AV.Name as Variety,AST.Name as StressType,\n" +
					"			(SELECT GROUP_CONCAT(ACS.Name SEPARATOR ', ') as AVRSName FROM agri_variety_stress_resistant_stress AVRS\n" +
					"					 LEFT JOIN agri_stress ACS ON (AVRS.StressID = ACS.ID) where AVRS.VarietyStressID =AVS.ID) as ResistantStress,\n" +
					"			(SELECT GROUP_CONCAT(ACS.Name SEPARATOR ', ') as AVSSName FROM agri_variety_stress_susceptible_stress AVSS \n" +
					"					LEFT JOIN agri_stress ACS ON (AVSS.StressID = ACS.ID) where AVSS.VarietyStressID =AVS.ID) as SusceptibleStress,\n" +
					"			(SELECT GROUP_CONCAT(ACS.Name SEPARATOR ', ') as AVTSName FROM agri_variety_stress_tolerant_stress AVTS \n" +
					"					LEFT JOIN agri_stress ACS ON (AVTS.StressID = ACS.ID) where AVTS.VarietyStressID =AVS.ID) as TolerantStress \n" +
					"			FROM cdt_master_data.agri_variety_stress_missing AVS \n" + 
					"			LEFT JOIN geo_state GS ON (AVS.StateCode = GS.StateCode) \n" + 
					"			LEFT JOIN agri_commodity AC ON (AVS.CommodityID = AC.ID) \n" + 
					"			LEFT JOIN geo_region GR ON (AVS.RegionID = GR.RegionID) \n" + 
					"			LEFT JOIN agri_variety AV ON (AVS.VarietyID = AV.ID) \n" + 
					"			LEFT JOIN agri_stress_type AST ON (AVS.StressTypeID = AST.ID)\n" + 
					"			 WHERE  AV.Name like :searchText\n" + 
					"			 Or  AC.Name like :searchText\n" + 
					"			 Or  GS.Name like :searchText\n" + 
					"			 Or  AST.Name like :searchText\n" + 
					"			 Or  AVS.Description like :searchText\n" + 
					"             order by AVS.ID desc", nativeQuery = true)
	Page<AgriVarietyStressInfDto> getAgrivarietyStressMissing(Pageable sortedByIdDesc, String searchText);

	@Query(value = "SELECT  AVS.* ,AV.Name ,GS.Name as State,AC.Name as Commodity, GR.Name as Region, AV.Name as Variety,AST.Name as StressType,\n" +
			"			(SELECT GROUP_CONCAT(ACS.Name SEPARATOR ', ') as AVRSName FROM agri_variety_stress_resistant_stress AVRS\n" +
			"					 LEFT JOIN agri_stress ACS ON (AVRS.StressID = ACS.ID) where AVRS.VarietyStressID =AVS.ID) as ResistantStress,\n" +
			"			(SELECT GROUP_CONCAT(ACS.Name SEPARATOR ', ') as AVSSName FROM agri_variety_stress_susceptible_stress AVSS \n" +
			"					LEFT JOIN agri_stress ACS ON (AVSS.StressID = ACS.ID) where AVSS.VarietyStressID =AVS.ID) as SusceptibleStress,\n" +
			"			(SELECT GROUP_CONCAT(ACS.Name SEPARATOR ', ') as AVTSName FROM agri_variety_stress_tolerant_stress AVTS \n" +
			"					LEFT JOIN agri_stress ACS ON (AVTS.StressID = ACS.ID) where AVTS.VarietyStressID =AVS.ID) as TolerantStress \n" +
			"			FROM cdt_master_data.agri_variety_stress AVS \n" +
			"			LEFT JOIN geo_state GS ON (AVS.StateCode = GS.StateCode) \n" +
			"			LEFT JOIN agri_commodity AC ON (AVS.CommodityID = AC.ID) \n" +
			"			LEFT JOIN geo_region GR ON (AVS.RegionID = GR.RegionID) \n" +
			"			LEFT JOIN agri_variety AV ON (AVS.VarietyID = AV.ID) \n" +
			"			LEFT JOIN agri_stress_type AST ON (AVS.StressTypeID = AST.ID)\n" +
			"			 where  AVS.IsValid = 0  and (AV.Name like :searchText\n" +
			"			 Or  AC.Name like :searchText\n" +
			"			 Or  GS.Name like :searchText\n" +
			"			 Or  AST.Name like :searchText\n" +
			"			 Or  AVS.Description like :searchText)\n" +
			"             order by AVS.ID desc",
			countQuery = "SELECT  AVS.* ,AV.Name ,GS.Name as State,AC.Name as Commodity, GR.Name as Region, AV.Name as Variety,AST.Name as StressType,\n" +
					"			(SELECT GROUP_CONCAT(ACS.Name SEPARATOR ', ') as AVRSName FROM agri_variety_stress_resistant_stress AVRS\n" +
					"					 LEFT JOIN agri_stress ACS ON (AVRS.StressID = ACS.ID) where AVRS.VarietyStressID =AVS.ID) as ResistantStress,\n" +
					"			(SELECT GROUP_CONCAT(ACS.Name SEPARATOR ', ') as AVSSName FROM agri_variety_stress_susceptible_stress AVSS \n" +
					"					LEFT JOIN agri_stress ACS ON (AVSS.StressID = ACS.ID) where AVSS.VarietyStressID =AVS.ID) as SusceptibleStress,\n" +
					"			(SELECT GROUP_CONCAT(ACS.Name SEPARATOR ', ') as AVTSName FROM agri_variety_stress_tolerant_stress AVTS \n" +
					"					LEFT JOIN agri_stress ACS on (ACS.ID = ACS.StressID) where AVTS.VarietyStressID =AVS.ID) as TolerantStress \n" +
					"			FROM cdt_master_data.agri_variety_stress AVS \n" +
					"			LEFT JOIN geo_state GS ON (AVS.StateCode = GS.StateCode) \n" +
					"			LEFT JOIN agri_commodity AC ON (AVS.CommodityID = AC.ID) \n" +
					"			LEFT JOIN geo_region GR ON (AVS.RegionID = GR.RegionID) \n" +
					"			LEFT JOIN agri_variety AV ON (AVS.VarietyID = AV.ID) \n" +
					"			LEFT JOIN agri_stress_type AST ON (AVS.StressTypeID = AST.ID)\n" +
					"			 where  AVS.IsValid = 0  and (AV.Name like :searchText\n" +
					"			 Or  AC.Name like :searchText\n" +
					"			 Or  GS.Name like :searchText\n" +
					"			 Or  AST.Name like :searchText\n" +
					"			 Or  AVS.Description like :searchText)\n" +
					"             order by AVS.ID desc", nativeQuery = true)
	Page<AgriVarietyStressInfDto> getAgrivarietyStressInvalidated(Pageable sortedByIdDesc, String searchText);
	
	@Query(value = "SELECT  AVS.* ,AV.Name ,GS.Name as State,AC.Name as Commodity, GR.Name as Region, AV.Name as Variety,AST.Name as StressType,\n" +
			"			(SELECT GROUP_CONCAT(ACS.Name SEPARATOR ', ') as AVRSName FROM agri_variety_stress_resistant_stress AVRS\n" +
			"					 LEFT JOIN agri_stress ACS ON (AVRS.StressID = ACS.ID) where AVRS.VarietyStressID =AVS.ID) as ResistantStress,\n" +
			"			(SELECT GROUP_CONCAT(ACS.Name SEPARATOR ', ') as AVSSName FROM agri_variety_stress_susceptible_stress AVSS \n" +
			"					LEFT JOIN agri_stress ACS ON (AVSS.StressID = ACS.ID) where AVSS.VarietyStressID =AVS.ID) as SusceptibleStress,\n" +
			"			(SELECT GROUP_CONCAT(ACS.Name SEPARATOR ', ') as AVTSName FROM agri_variety_stress_tolerant_stress AVTS \n" +
			"					LEFT JOIN agri_stress ACS ON (AVTS.StressID = ACS.ID) where AVTS.VarietyStressID =AVS.ID) as TolerantStress \n" +
			"			FROM cdt_master_data.agri_variety_stress_missing AVS \n" +
			"			LEFT JOIN geo_state GS ON (AVS.StateCode = GS.StateCode) \n" +
			"			LEFT JOIN agri_commodity AC ON (AVS.CommodityID = AC.ID) \n" +
			"			LEFT JOIN geo_region GR ON (AVS.RegionID = GR.RegionID) \n" +
			"			LEFT JOIN agri_variety AV ON (AVS.VarietyID = AV.ID) \n" +
			"			LEFT JOIN agri_stress_type AST ON (AVS.StressTypeID = AST.ID)\n" +
			"			 where  AVS.IsValid = 0  and (AV.Name like :searchText\n" +
			"			 Or  AC.Name like :searchText\n" +
			"			 Or  GS.Name like :searchText\n" +
			"			 Or  AST.Name like :searchText\n" +
			"			 Or  AVS.Description like :searchText)\n" +
			"             order by AVS.ID desc",
			countQuery = "SELECT  AVS.* ,AV.Name ,GS.Name as State,AC.Name as Commodity, GR.Name as Region, AV.Name as Variety,AST.Name as StressType,\n" +
					"			(SELECT GROUP_CONCAT(ACS.Name SEPARATOR ', ') as AVRSName FROM agri_variety_stress_resistant_stress AVRS\n" +
					"					 LEFT JOIN agri_stress ACS ON (AVRS.StressID = ACS.ID) where AVRS.VarietyStressID =AVS.ID) as ResistantStress,\n" +
					"			(SELECT GROUP_CONCAT(ACS.Name SEPARATOR ', ') as AVSSName FROM agri_variety_stress_susceptible_stress AVSS \n" +
					"					LEFT JOIN agri_stress ACS ON (AVSS.StressID = ACS.ID) where AVSS.VarietyStressID =AVS.ID) as SusceptibleStress,\n" +
					"			(SELECT GROUP_CONCAT(ACS.Name SEPARATOR ', ') as AVTSName FROM agri_variety_stress_tolerant_stress AVTS \n" +
					"					LEFT JOIN agri_stress ACS ON (AVTS.StressID = ACS.ID) where AVTS.VarietyStressID =AVS.ID) as TolerantStress \n" +
					"			FROM cdt_master_data.agri_variety_stress_missing AVS \n" +
					"			LEFT JOIN geo_state GS ON (AVS.StateCode = GS.StateCode) \n" +
					"			LEFT JOIN agri_commodity AC ON (AVS.CommodityID = AC.ID) \n" +
					"			LEFT JOIN geo_region GR ON (AVS.RegionID = GR.RegionID) \n" +
					"			LEFT JOIN agri_variety AV ON (AVS.VarietyID = AV.ID) \n" +
					"			LEFT JOIN agri_stress_type AST ON (AVS.StressTypeID = AST.ID)\n" +
					"			 where  AVS.IsValid = 0  and (AV.Name like :searchText\n" +
					"			 Or  AC.Name like :searchText\n" +
					"			 Or  GS.Name like :searchText\n" +
					"			 Or  AST.Name like :searchText\n" +
					"			 Or  AVS.Description like :searchText)\n" +
					"             order by AVS.ID desc", nativeQuery = true)
	Page<AgriVarietyStressInfDto> getAgrivarietyStressMissingInvalidated(Pageable sortedByIdDesc, String searchText);
}