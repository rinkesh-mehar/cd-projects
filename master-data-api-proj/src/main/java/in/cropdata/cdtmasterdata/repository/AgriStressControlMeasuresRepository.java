package in.cropdata.cdtmasterdata.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.cdtmasterdata.dto.interfaces.AgriStressControlMeasuresInfDto;
import in.cropdata.cdtmasterdata.dto.interfaces.AgriDistrictCommodityStressInfDto;
import in.cropdata.cdtmasterdata.model.AgriStressControlMeasures;

public interface AgriStressControlMeasuresRepository extends JpaRepository<AgriStressControlMeasures, Integer> {

	@Query(value = "select ASCM.ID,AC.Name as Commodity,agri_stress.Name as stress,ASCM.Status,ASSE.Name as StressSeverity,\n" + 
			"			ACM.Name as StressControlMeasure From agri_stress_control_measures ASCM\n" + 
			"			LEFT JOIN agri_commodity AC ON (AC.ID = ASCM.CommodityID)\n" + 
			"			INNER JOIN agri_stress agri_stress on (agri_stress.ID = ASCM.StressID)\n" + 
			"			LEFT JOIN agri_stress_severity ASSE ON (ASSE.ID = ASCM.StressSeverityID)\n" + 
			"			LEFT JOIN agri_control_measures ACM ON (ACM.ID = ASCM.StressControlMeasureID)\n" + 
			"            INNER JOIN agri_commodity_stress ACS ON (ACS.CommodityID = ASCM.CommodityID  and ACS.StressID = agri_stress.ID)", nativeQuery = true)
	List<AgriStressControlMeasuresInfDto> getAgriStressControlMeasures();

	@Query(value = "select ASCM.ID,ASCM.CommodityID,ASCM.StressID,ASCM.StressSeverityID,ASCM.StressControlMeasureID,AC.Name as Commodity,\n" + 
			"		ASCM.Status,agri_stress.Name as Stress,ASSE.Name as StressSeverity,\n" + 
			"								ACM.Name as StressControlMeasure, ASCM.IsValid, ASCM.ErrorMessage From agri_stress_control_measures ASCM\n" + 
			"								LEFT JOIN agri_commodity AC ON (AC.ID = ASCM.CommodityID)\n" + 
			"								INNER JOIN agri_stress agri_stress on (agri_stress.ID = ASCM.StressID)\n" + 
			"								LEFT JOIN agri_stress_severity ASSE ON (ASSE.ID = ASCM.StressSeverityID)\n" + 
			"								LEFT JOIN agri_control_measures ACM ON (ACM.ID = ASCM.StressControlMeasureID)\n" + 
			"                                INNER JOIN agri_commodity_stress ACS ON (ACS.CommodityID = ASCM.CommodityID  and ACS.StressID = agri_stress.ID)\n" + 
			"					            where agri_stress.Name like :searchText\n" + 
			"		                        OR AC.Name like :searchText\n" + 
			"		                        OR ASSE.Name like :searchText\n" + 
			"		                        OR ACM.Name like :searchText", 
			countQuery = "select ASCM.ID,ASCM.CommodityID,ASCM.StressID,ASCM.StressSeverityID,ASCM.StressControlMeasureID,AC.Name as Commodity,\n" + 
					"		ASCM.Status,agri_stress.Name as Stress,ASSE.Name as StressSeverity,\n" + 
					"								ACM.Name as StressControlMeasure, ASCM.IsValid, ASCM.ErrorMessage From agri_stress_control_measures ASCM\n" + 
					"								LEFT JOIN agri_commodity AC ON (AC.ID = ASCM.CommodityID)\n" + 
					"								INNER JOIN agri_stress agri_stress on (agri_stress.ID = ASCM.StressID)\n" + 
					"								LEFT JOIN agri_stress_severity ASSE ON (ASSE.ID = ASCM.StressSeverityID)\n" + 
					"								LEFT JOIN agri_control_measures ACM ON (ACM.ID = ASCM.StressControlMeasureID)\n" + 
					"                                INNER JOIN agri_commodity_stress ACS ON (ACS.CommodityID = ASCM.CommodityID  and ACS.StressID = agri_stress.ID)\n" + 
					"					            where agri_stress.Name like :searchText\n" + 
					"		                        OR AC.Name like :searchText\n" + 
					"		                        OR ASSE.Name like :searchText\n" + 
					"		                        OR ACM.Name like :searchText", nativeQuery = true)
	Page<AgriStressControlMeasuresInfDto> getAgriStressControlMeasures(Pageable sortedByIdDesc, String searchText);
	
	@Query(value = "select AR.ID,AR.CommodityID,AR.StressID,AR.StressSeverityID,AR.StressControlMeasureID,AC.Name as Commodity,\n" + 
			"AR.Status,ASS.Name as Stress,ASSE.Name as StressSeverity,\n" + 
			"						ASCM.Name as StressControlMeasure From agri_recommendation_missing AR \n" +
			"						LEFT JOIN agri_commodity AC ON (AC.ID = AR.CommodityID)\n" + 
			"						LEFT JOIN agri_stress ASS ON (ASS.ID = AR.StressID)\n" +
			"						LEFT JOIN agri_stress_severity ASSE ON (ASSE.ID = AR.StressSeverityID)\n" + 
			"						LEFT JOIN agri_stress_control_measures ASCM ON (ASCM.ID = AR.StressControlMeasureID)\n" + 
			"			            where ASS.Name like :searchText\n" + 
			"                        OR AC.Name like :searchText\n" + 
			"                        OR ASSE.Name like :searchText\n" + 
			"                        OR ASCM.Name like :searchText", 
			countQuery = "select AR.ID,AR.CommodityID,AR.StressID,AR.StressSeverityID,AR.StressControlMeasureID,AC.Name as Commodity,\n" + 
					"AR.Status,ASS.Name as Stress,ASSE.Name as StressSeverity,\n" + 
					"						ASCM.Name as StressControlMeasure From agri_recommendation_missing AR \n" +
					"						LEFT JOIN agri_commodity AC ON (AC.ID = AR.CommodityID)\n" + 
					"						LEFT JOIN agri_stress ASS ON (ASS.ID = AR.StressID)\n" +
					"						LEFT JOIN agri_stress_severity ASSE ON (ASSE.ID = AR.StressSeverityID)\n" + 
					"						LEFT JOIN agri_stress_control_measures ASCM ON (ASCM.ID = AR.StressControlMeasureID)\n" + 
					"			            where ASS.Name like :searchText\n" + 
					"                        OR AC.Name like :searchText\n" + 
					"                        OR ASSE.Name like :searchText\n" + 
					"                        OR ASCM.Name like :searchText", nativeQuery = true)
	Page<AgriStressControlMeasuresInfDto> getAgriRecommendationMissing(Pageable sortedByIdDesc, String searchText);

	@Query(value = "select ASCM.ID,ASCM.CommodityID,ASCM.StressID,ASCM.StressSeverityID,ASCM.StressControlMeasureID,AC.Name as Commodity,\n" + 
			"		ASCM.Status,agri_stress.Name as Stress,ASSE.Name as StressSeverity,\n" + 
			"								ACM.Name as StressControlMeasure, ASCM.IsValid, ASCM.ErrorMessage From agri_stress_control_measures ASCM\n" + 
			"								LEFT JOIN agri_commodity AC ON (AC.ID = ASCM.CommodityID)\n" + 
			"								INNER JOIN agri_stress agri_stress on (agri_stress.ID = ASCM.StressID)\n" + 
			"								LEFT JOIN agri_stress_severity ASSE ON (ASSE.ID = ASCM.StressSeverityID)\n" + 
			"								LEFT JOIN agri_control_measures ACM ON (ACM.ID = ASCM.StressControlMeasureID)\n" + 
			"                                INNER JOIN agri_commodity_stress ACS ON (ACS.CommodityID = ASCM.CommodityID  and ACS.StressID = agri_stress.ID)\n" + 
			"					            where ASCM.IsValid = 0 and (agri_stress.Name like :searchText\n" + 
			"		                        OR AC.Name like :searchText\n" + 
			"		                        OR ASSE.Name like :searchText\n" + 
			"		                        OR ACM.Name like :searchText)",
			countQuery = "select ASCM.ID,ASCM.CommodityID,ASCM.StressID,ASCM.StressSeverityID,ASCM.StressControlMeasureID,AC.Name as Commodity,\n" + 
					"		ASCM.Status,agri_stress.Name as Stress,ASSE.Name as StressSeverity,\n" + 
					"								ACM.Name as StressControlMeasure, ASCM.IsValid, ASCM.ErrorMessage From agri_stress_control_measures ASCM\n" + 
					"								LEFT JOIN agri_commodity AC ON (AC.ID = ASCM.CommodityID)\n" + 
					"								INNER JOIN agri_stress agri_stress on (agri_stress.ID = ASCM.StressID)\n" + 
					"								LEFT JOIN agri_stress_severity ASSE ON (ASSE.ID = ASCM.StressSeverityID)\n" + 
					"								LEFT JOIN agri_control_measures ACM ON (ACM.ID = ASCM.StressControlMeasureID)\n" + 
					"                                INNER JOIN agri_commodity_stress ACS ON (ACS.CommodityID = ASCM.CommodityID  and ACS.StressID = agri_stress.ID)\n" + 
					"					            where ASCM.IsValid = 0 and (agri_stress.Name like :searchText\n" + 
					"		                        OR AC.Name like :searchText\n" + 
					"		                        OR ASSE.Name like :searchText\n" + 
					"		                        OR ACM.Name like :searchText)", nativeQuery = true)
	Page<AgriStressControlMeasuresInfDto> getAgriStressControlMeasuresInvalidated(Pageable sortedByIdDesc, String searchText);
	
	@Query(value = "select AR.ID,AR.CommodityID,AR.StressID,AR.StressSeverityID,AR.StressControlMeasureID,AC.Name as Commodity,\n" +
			"AR.Status,ASS.Name as Stress,ASSE.Name as StressSeverity,\n" +
			"						ASCM.Name as StressControlMeasure From agri_recommendation_missing AR \n" +
			"						LEFT JOIN agri_commodity AC ON (AC.ID = AR.CommodityID)\n" +
			"						LEFT JOIN agri_stress ASS ON (ASS.ID = AR.StressID)\n" +
			"						LEFT JOIN agri_stress_severity ASSE ON (ASSE.ID = AR.StressSeverityID)\n" +
			"						LEFT JOIN agri_stress_control_measures ASCM ON (ASCM.ID = AR.StressControlMeasureID)\n" +
			"			            where AR.IsValid = 0 and (ASS.Name like :searchText\n" +
			"                        OR AC.Name like :searchText\n" +
			"                        OR ASSE.Name like :searchText\n" +
			"                        OR ASCM.Name like :searchText)",
			countQuery = "select AR.ID,AR.CommodityID,AR.StressID,AR.StressSeverityID,AR.StressControlMeasureID,AC.Name as Commodity,\n" +
					"AR.Status,ASS.Name as Stress,ASSE.Name as StressSeverity,\n" +
					"						ASCM.Name as StressControlMeasure From agri_recommendation_missing AR \n" +
					"						LEFT JOIN agri_commodity AC ON (AC.ID = AR.CommodityID)\n" +
					"						LEFT JOIN agri_stress ASS ON (ASS.ID = AR.StressID)\n" +
					"						LEFT JOIN agri_stress_severity ASSE ON (ASSE.ID = AR.StressSeverityID)\n" +
					"						LEFT JOIN agri_stress_control_measures ASCM ON (ASCM.ID = AR.StressControlMeasureID)\n" +
					"			            where AR.IsValid = 0 and ( ASS.Name like :searchText\n" +
					"                        OR AC.Name like :searchText\n" +
					"                        OR ASSE.Name like :searchText\n" +
					"                        OR ASCM.Name like :searchText)", nativeQuery = true)
	Page<AgriStressControlMeasuresInfDto> getAgriRecommendationMissingInvalidated(Pageable sortedByIdDesc, String searchText);
	
	@Query(value = "select ASCM.ID,ASCM.CommodityID,ASCM.StressID,ASCM.StressSeverityID,ASCM.StressControlMeasureID,AC.Name as Commodity, \n" + 
			"					ASCM.Status,agri_stress.Name as Stress,ASSE.Name as StressSeverity, \n" + 
			"											ACM.Name as StressControlMeasure, ASCM.IsValid, ASCM.ErrorMessage From agri_stress_control_measures ASCM \n" + 
			"											LEFT JOIN agri_commodity AC ON (AC.ID = ASCM.CommodityID) \n" + 
			"											INNER JOIN agri_stress agri_stress on (agri_stress.ID = ASCM.StressID) \n" + 
			"											LEFT JOIN agri_stress_severity ASSE ON (ASSE.ID = ASCM.StressSeverityID) \n" + 
			"											LEFT JOIN agri_control_measures ACM ON (ACM.ID = ASCM.StressControlMeasureID) \n" + 
			"			                                INNER JOIN agri_commodity_stress ACS ON (ACS.CommodityID = ASCM.CommodityID  and ACS.StressID = agri_stress.ID) \n" + 
			"								            where ASCM.CommodityID = :commodityId OR ASCM.StressID = :stressId OR ASCM.StressSeverityID = :stressSeverityId OR ASCM.StressControlMeasureID = :controlMeasureId", 
			countQuery = "select count(ASCM.ID) as Count \n" +
					"											From agri_stress_control_measures ASCM \n" + 
					"											LEFT JOIN agri_commodity AC ON (AC.ID = ASCM.CommodityID) \n" + 
					"											INNER JOIN agri_stress agri_stress on (agri_stress.ID = ASCM.StressID) \n" + 
					"											LEFT JOIN agri_stress_severity ASSE ON (ASSE.ID = ASCM.StressSeverityID) \n" + 
					"											LEFT JOIN agri_control_measures ACM ON (ACM.ID = ASCM.StressControlMeasureID) \n" + 
					"			                                INNER JOIN agri_commodity_stress ACS ON (ACS.CommodityID = ASCM.CommodityID  and ACS.StressID = agri_stress.ID) \n" + 
					"								            where ASCM.CommodityID = :commodityId OR ASCM.StressID = :stressId OR ASCM.StressSeverityID = :stressSeverityId OR ASCM.StressControlMeasureID = :controlMeasureId", nativeQuery = true)
	Page<AgriStressControlMeasuresInfDto> getAgriStressControlMeasuresByMultiSearchFilters(Pageable sortedByIdDesc, String commodityId,String stressId,String stressSeverityId,String controlMeasureId);

	@Query(value = "select agri_stress.ID,agri_stress.NAME from agri_commodity_stress  acs\n" + 
			"INNER JOIN agri_stress agri_stress on (acs.StressID = agri_stress.ID) where acs.CommodityID = ?\n" + 
			"order by agri_stress.NAME ASC", nativeQuery = true)
	List<AgriDistrictCommodityStressInfDto> getAllStressByCommodityId(int commodityId);
}
