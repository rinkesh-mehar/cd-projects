package in.cropdata.cdtmasterdata.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import in.cropdata.cdtmasterdata.dto.interfaces.AgriCommodityStressStageInfDto;
import in.cropdata.cdtmasterdata.model.AgriCommodityStressStage;
import in.cropdata.cdtmasterdata.model.vo.AgriStageVO;

public interface AgriCommodityStressStageRepository extends JpaRepository<AgriCommodityStressStage, Integer> {
//
//	@Query(value = "SELECT ASS.ID,ASS.RegionID,ASS.CommodityID,ASS.PhenophaseID,ASS.StressID,ASS.StressTypeID,\n"
//			+ "ASS.Name,ASS.Description,ASS.StartWeek,ASS.EndWeek,ASS.UpdatedAt,ASS.CreatedAt,ASS.Status,\n"
//			+ "GR.Name as Region,AC.Name as Commodity,AP.Name as Phenophase,AST.Name as StressType, ABST.Name as Stress\n"
//			+ "FROM agri_stress_stage ASS \n" + "LEFT JOIN geo_region GR ON (ASS.RegionID = GR.RegionID )    \n"
//			+ "LEFT JOIN agri_commodity AC ON ( ASS.CommodityID = AC.ID)\n"
//			+ "LEFT JOIN agri_phenophase AP ON (ASS.PhenophaseID = AP.ID)\n"
//			+ "LEFT JOIN agri_stress_type AST ON (ASS.StressTypeID = AST.ID)\n"
//			+ "LEFT JOIN agri_biotic_stress ABST ON (ASS.StressID = ABST.ID)", nativeQuery = true)

	@Query(value = "SELECT ACSS.ID,ACSS.CommodityID,ACSS.StressID,ACSS.StartPhenophaseID,ACSS.EndPhenophaseID,\n" + 
			"			AP_S.Name as startPhenophaseName,AP_E.Name as endPhenophaseName,agri_stage.Name,ACSS.Description,\n" + 
			"            ACSS.UpdatedAt,ACSS.CreatedAt,ACSS.Status,AC.Name as Commodity, agri_stress.Name as Stress\n" + 
			"			FROM agri_commodity_stress_stage ACSS\n" + 
			"			LEFT JOIN agri_commodity AC ON ( ACSS.CommodityID = AC.ID)\n" + 
			"			LEFT JOIN agri_phenophase AP_S ON (AP_S.ID = ACSS.StartPhenophaseID)\n" + 
			"			LEFT JOIN agri_phenophase AP_E ON (AP_E.ID = ACSS.EndPhenophaseID)\n" + 
			"			Inner JOIN agri_stress agri_stress ON (agri_stress.ID = ACSS.StressID)\n" + 
			"			INNER JOIN agri_stage agri_stage ON (agri_stage.ID = ACSS.StageID)\n" + 
			"			left JOIN agri_commodity_stress ACS on (ACS.CommodityID = ACSS.CommodityID  and ACS.StressID = ACSS.StressID)\n" + 
			"			INNER JOIN agri_stress_stage ASS ON(ASS.StressID = ACSS.StressID AND ASS.StageID = ACSS.StageID) ", nativeQuery = true)
	List<AgriCommodityStressStageInfDto> getAgriCommodityStressStageList();
	
	@Query(value = "SELECT ACSS.ID,ACSS.CommodityID,ACSS.StressID,ACSS.StartPhenophaseID,ACSS.EndPhenophaseID,\n" + 
			"											AP_S.Name as startPhenophaseName,AP_E.Name as endPhenophaseName,\n" + 
			"											agri_stage.Name,ACSS.Description,ACSS.Status,\n" + 
			"											AC.Name as Commodity, agri_stress.Name as Stress\n" + 
			"											FROM agri_commodity_stress_stage ACSS\n" + 
			"											LEFT JOIN agri_commodity AC ON ( ACSS.CommodityID = AC.ID)\n" + 
			"											LEFT JOIN agri_phenophase AP_S ON (AP_S.ID = ACSS.StartPhenophaseID)\n" + 
			"											LEFT JOIN agri_phenophase AP_E ON (AP_E.ID = ACSS.EndPhenophaseID)\n" + 
			"                                            Inner JOIN agri_stress agri_stress ON (agri_stress.ID = ACSS.StressID)\n" + 
			"                                            INNER JOIN agri_stage agri_stage ON (agri_stage.ID = ACSS.StageID)\n" + 
			"                                            left JOIN agri_commodity_stress ACS on (ACS.CommodityID = ACSS.CommodityID  and ACS.StressID = ACSS.StressID)\n" + 
			"                                            INNER JOIN agri_stress_stage ASS ON(ASS.StressID = ACSS.StressID AND ASS.StageID = ACSS.StageID) \n" + 
			"                                            where ACSS.ID like :searchText\n" + 
			"                                            OR AP_S.Name like :searchText\n" + 
			"					                        OR AP_E.Name like :searchText\n" + 
			"                                            OR agri_stage.Name like :searchText																	\n" + 
			"					                        OR ACSS.Description like :searchText\n" + 
			"											OR AC.Name like :searchText			\n" + 
			"                                            OR agri_stress.Name like :searchText",
			countQuery = "SELECT ACSS.ID,ACSS.CommodityID,ACSS.StressID,ACSS.StartPhenophaseID,ACSS.EndPhenophaseID,\n" + 
					"											AP_S.Name as startPhenophaseName,AP_E.Name as endPhenophaseName,\n" + 
					"											agri_stage.Name,ACSS.Description,ACSS.Status,\n" + 
					"											AC.Name as Commodity, agri_stress.Name as Stress\n" + 
					"											FROM agri_commodity_stress_stage ACSS\n" + 
					"											LEFT JOIN agri_commodity AC ON ( ACSS.CommodityID = AC.ID)\n" + 
					"											LEFT JOIN agri_phenophase AP_S ON (AP_S.ID = ACSS.StartPhenophaseID)\n" + 
					"											LEFT JOIN agri_phenophase AP_E ON (AP_E.ID = ACSS.EndPhenophaseID)\n" + 
					"                                            Inner JOIN agri_stress agri_stress ON (agri_stress.ID = ACSS.StressID)\n" + 
					"                                            INNER JOIN agri_stage agri_stage ON (agri_stage.ID = ACSS.StageID)\n" + 
					"                                            left JOIN agri_commodity_stress ACS on (ACS.CommodityID = ACSS.CommodityID  and ACS.StressID = ACSS.StressID)\n" + 
					"                                            INNER JOIN agri_stress_stage ASS ON(ASS.StressID = ACSS.StressID AND ASS.StageID = ACSS.StageID) \n" + 
					"                                            where ACSS.ID like :searchText\n" + 
					"                                            OR AP_S.Name like :searchText\n" + 
					"					                        OR AP_E.Name like :searchText\n" + 
					"                                            OR agri_stage.Name like :searchText																	\n" + 
					"					                        OR ACSS.Description like :searchText\n" + 
					"											OR AC.Name like :searchText			\n" + 
					"                                            OR agri_stress.Name like :searchText", nativeQuery = true)
	Page<AgriCommodityStressStageInfDto> getAgriCommodityStressStage(Pageable sortedByIdDesc, String searchText);
	
	@Query(value = "SELECT ASS.ID,ASS.CommodityID,ASS.StressID,ASS.StressTypeID,ASS.StartPhenophaseID,ASS.EndPhenophaseID,\n" + 
			"						AP_S.Name as startPhenophaseName,AP_E.Name as endPhenophaseName,\n" + 
			"						ASS.Name,ASS.Description,ASS.Status,\n" + 
			"						AC.Name as Commodity,AST.Name as StressType, ACS.Name as Stress\n" +
			"						FROM agri_stress_stage_missing ASS  \n" + 
			"						LEFT JOIN agri_commodity AC ON ( ASS.CommodityID = AC.ID)\n" + 
			"						LEFT JOIN agri_stress_type AST ON (ASS.StressTypeID = AST.ID)\n" + 
			"						LEFT JOIN agri_commodity_stress ACS on (ACS.ID = ASS.StressID)\n" +
			"						LEFT JOIN agri_phenophase AP_S ON (AP_S.ID = ASS.StartPhenophaseID)\n" + 
			"						LEFT JOIN agri_phenophase AP_E ON (AP_E.ID = ASS.EndPhenophaseID)\n" + 
			"			            where ASS.Name like :searchText\n" + 
			"						OR AC.Name like :searchText\n" + 
			"						OR AST.Name like :searchText\n" + 
			"						OR ACS.Name like :searchText\n" +
			"                        OR AP_S.Name like :searchText\n" + 
			"                        OR AP_E.Name like :searchText\n" + 
			"                        OR ASS.Name like :searchText\n" + 
			"                        OR ASS.Description like :searchText",
			countQuery = "SELECT ASS.ID,ASS.CommodityID,ASS.StressID,ASS.StressTypeID,ASS.StartPhenophaseID,ASS.EndPhenophaseID,\n" + 
					"						AP_S.Name as startPhenophaseName,AP_E.Name as endPhenophaseName,\n" + 
					"						ASS.Name,ASS.Description,ASS.Status,\n" + 
					"						AC.Name as Commodity,AST.Name as StressType, ACS.Name as Stress\n" +
					"						FROM agri_stress_stage_missing ASS  \n" + 
					"						LEFT JOIN agri_commodity AC ON ( ASS.CommodityID = AC.ID)\n" + 
					"						LEFT JOIN agri_stress_type AST ON (ASS.StressTypeID = AST.ID)\n" + 
					"						LEFT JOIN agri_commodity_stress ACS on (ACS.ID = ASS.StressID)\n" +
					"						LEFT JOIN agri_phenophase AP_S ON (AP_S.ID = ASS.StartPhenophaseID)\n" + 
					"						LEFT JOIN agri_phenophase AP_E ON (AP_E.ID = ASS.EndPhenophaseID)\n" + 
					"			            where ASS.Name like :searchText\n" + 
					"						OR AC.Name like :searchText\n" + 
					"						OR AST.Name like :searchText\n" + 
					"						OR ACS.Name like :searchText\n" +
					"                        OR AP_S.Name like :searchText\n" + 
					"                        OR AP_E.Name like :searchText\n" + 
					"                        OR ASS.Name like :searchText\n" + 
					"                        OR ASS.Description like :searchText", nativeQuery = true)
	Page<AgriCommodityStressStageInfDto> getAgriStressStageMissing(Pageable sortedByIdDesc, String searchText);

	@Query(value = "SELECT ASS.ID,ASS.CommodityID,ASS.StressID,ASS.StressTypeID,ASS.StartPhenophaseID,ASS.EndPhenophaseID,\n" +
			"						AP_S.Name as startPhenophaseName,AP_E.Name as endPhenophaseName,\n" +
			"						ASS.Name,ASS.Description,ASS.Status,\n" +
			"						AC.Name as Commodity,AST.Name as StressType, ACS.Name as Stress, ASS.IsValid, ASS.ErrorMessage\n" +
			"						FROM agri_stress_stage ASS  \n" +
			"						LEFT JOIN agri_commodity AC ON ( ASS.CommodityID = AC.ID)\n" +
			"						LEFT JOIN agri_stress_type AST ON (ASS.StressTypeID = AST.ID)\n" +
			"						LEFT JOIN agri_commodity_stress ACS on (ACS.ID = ASS.StressID)\n" +
			"						LEFT JOIN agri_phenophase AP_S ON (AP_S.ID = ASS.StartPhenophaseID)\n" +
			"						LEFT JOIN agri_phenophase AP_E ON (AP_E.ID = ASS.EndPhenophaseID)\n" +
			"			            where ASS.IsValid = 0 and (ASS.Name like :searchText\n" +
			"						OR AC.Name like :searchText\n" +
			"						OR AST.Name like :searchText\n" +
			"						OR ACS.Name like :searchText\n" +
			"                        OR AP_S.Name like :searchText\n" +
			"                        OR AP_E.Name like :searchText\n" +
			"                        OR ASS.Name like :searchText\n" +
			"                        OR ASS.Description like :searchText)",
			countQuery = "SELECT ASS.ID,ASS.CommodityID,ASS.StressID,ASS.StressTypeID,ASS.StartPhenophaseID,ASS.EndPhenophaseID,\n" +
					"						AP_S.Name as startPhenophaseName,AP_E.Name as endPhenophaseName,\n" +
					"						ASS.Name,ASS.Description,ASS.Status,\n" +
					"						AC.Name as Commodity,AST.Name as StressType, ACS.Name as Stress, ASS.IsValid, ASS.ErrorMessage\n" +
					"						FROM agri_stress_stage ASS  \n" +
					"						LEFT JOIN agri_commodity AC ON ( ASS.CommodityID = AC.ID)\n" +
					"						LEFT JOIN agri_stress_type AST ON (ASS.StressTypeID = AST.ID)\n" +
					"						LEFT JOIN agri_commodity_stress ACS on (ACS.ID = ASS.StressID)\n" +
					"						LEFT JOIN agri_phenophase AP_S ON (AP_S.ID = ASS.StartPhenophaseID)\n" +
					"						LEFT JOIN agri_phenophase AP_E ON (AP_E.ID = ASS.EndPhenophaseID)\n" +
					"			            where ASS.IsValid = 0 and (ASS.Name like :searchText\n" +
					"						OR AC.Name like :searchText\n" +
					"						OR AST.Name like :searchText\n" +
					"						OR ACS.Name like :searchText\n" +
					"                        OR AP_S.Name like :searchText\n" +
					"                        OR AP_E.Name like :searchText\n" +
					"                        OR ASS.Name like :searchText\n" +
					"                        OR ASS.Description like :searchText)", nativeQuery = true)
	Page<AgriCommodityStressStageInfDto> getAgriStressStageInvalidated(Pageable sortedByIdDesc, String searchText);
	
	@Query(value = "SELECT ASS.ID,ASS.CommodityID,ASS.StressID,ASS.StressTypeID,ASS.StartPhenophaseID,ASS.EndPhenophaseID,\n" +
			"						AP_S.Name as startPhenophaseName,AP_E.Name as endPhenophaseName,\n" +
			"						ASS.Name,ASS.Description,ASS.Status,\n" +
			"						AC.Name as Commodity,AST.Name as StressType, ACS.Name as Stress\n" +
			"						FROM agri_stress_stage_missing ASS  \n" +
			"						LEFT JOIN agri_commodity AC ON ( ASS.CommodityID = AC.ID)\n" +
			"						LEFT JOIN agri_stress_type AST ON (ASS.StressTypeID = AST.ID)\n" +
			"						LEFT JOIN agri_commodity_stress ACS on (ACS.ID = ASS.StressID)\n" +
			"						LEFT JOIN agri_phenophase AP_S ON (AP_S.ID = ASS.StartPhenophaseID)\n" +
			"						LEFT JOIN agri_phenophase AP_E ON (AP_E.ID = ASS.EndPhenophaseID)\n" +
			"			            where ASS.IsValid = 0 and (ASS.Name like :searchText\n" +
			"						OR AC.Name like :searchText\n" +
			"						OR AST.Name like :searchText\n" +
			"						OR ACS.Name like :searchText\n" +
			"                        OR AP_S.Name like :searchText\n" +
			"                        OR AP_E.Name like :searchText\n" +
			"                        OR ASS.Name like :searchText\n" +
			"                        OR ASS.Description like :searchText)",
			countQuery = "SELECT ASS.ID,ASS.CommodityID,ASS.StressID,ASS.StressTypeID,ASS.StartPhenophaseID,ASS.EndPhenophaseID,\n" +
					"						AP_S.Name as startPhenophaseName,AP_E.Name as endPhenophaseName,\n" +
					"						ASS.Name,ASS.Description,ASS.Status,\n" +
					"						AC.Name as Commodity,AST.Name as StressType, ACS.Name as Stress\n" +
					"						FROM agri_stress_stage_missing ASS  \n" +
					"						LEFT JOIN agri_commodity AC ON ( ASS.CommodityID = AC.ID)\n" +
					"						LEFT JOIN agri_stress_type AST ON (ASS.StressTypeID = AST.ID)\n" +
					"						LEFT JOIN agri_commodity_stress ACS on (ACS.ID = ASS.StressID)\n" +
					"						LEFT JOIN agri_phenophase AP_S ON (AP_S.ID = ASS.StartPhenophaseID)\n" +
					"						LEFT JOIN agri_phenophase AP_E ON (AP_E.ID = ASS.EndPhenophaseID)\n" +
					"			            where ASS.IsValid = 0 and (ASS.Name like :searchText\n" +
					"						OR AC.Name like :searchText\n" +
					"						OR AST.Name like :searchText\n" +
					"						OR ACS.Name like :searchText\n" +
					"                        OR AP_S.Name like :searchText\n" +
					"                        OR AP_E.Name like :searchText\n" +
					"                        OR ASS.Name like :searchText\n" +
					"                        OR ASS.Description like :searchText)", nativeQuery = true)
	Page<AgriCommodityStressStageInfDto> getAgriStressStageMissingInvalidated(Pageable sortedByIdDesc, String searchText);
	
	@Query(value = "select agri_stage.ID,agri_stage.Name ,ass.StressID from agri_stage agri_stage inner join agri_stress_stage ass on (ass.StageID = agri_stage.ID)\n" + 
			"inner join agri_stress agri_stress on(agri_stress.ID = ass.StressID) where ass.StressID = ? order by agri_stage.Name", nativeQuery = true)
	List<AgriStageVO> getStageListByStressId(int stressId);

}
