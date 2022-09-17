package in.cropdata.cdtmasterdata.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import in.cropdata.cdtmasterdata.dto.interfaces.AgriStressControlRecommendationInfDto;
import in.cropdata.cdtmasterdata.model.AgriStressControlRecommendation;

@Repository
public interface AgriStressControlRecommendationRepository
		extends JpaRepository<AgriStressControlRecommendation, Integer> {

	@Query(value = "SELECT ASCR.ID, AC.Name as Commodity,ASCR.Status,ASCM.Name as StressControlMeasure ,ACS.Name as Stress,\n"
			+ "AAG.Name as Agrochemical, STP.Name as StressType, AAAT.Name as AgroApplicationType,\n"
			+ "Instructions,AgroChemicalInstructions,DosePerAcre,DosePerHectare,WaterPerAcre,WaterPerHectare,\n"
			+ "perHectareUOM.Name as PerHectareUOM,perAcreUOM.Name as PerAcreUOM,perHectareWaterUOM.Name as PerHectareWaterUOM,\n"
			+ "perAcreWaterUOM.Name as PerAcreWaterUOM " + " FROM agri_stress_control_recommendation ASCR \n"
			+ "LEFT JOIN agri_commodity AC ON (AC.ID = ASCR.CommodityID)\n"
			+ "LEFT JOIN agri_stress_control_measures ASCM ON (ASCM.ID = ASCR.StressControlMeasureID)\n"
			+ "LEFT join agri_commodity_stress ACS on (ACS.ID = ASCR.StressID)\n"
			+ "LEFT JOIN agri_commodity_agrochemical AAG ON (AAG.ID = ASCR.AgrochemicalID)\n"
			+ "LEFT JOIN agri_stress_type STP ON (STP.ID = ASCR.StressTypeID)\n"
			+ "LEFT JOIN   agri_agrochemical_application_type AAAT ON (AAAT.ID = ASCR.AgrochemApplicationTypeID)"
			+ "LEFT JOIN general_uom perHectareUOM ON (perHectareUOM.ID = ASCR.PerHectareUomID)\n"
			+ "LEFT JOIN general_uom perAcreUOM ON (perAcreUOM.ID = ASCR.PerAcreUomID)\n"
			+ "LEFT JOIN general_uom perHectareWaterUOM ON (perHectareWaterUOM.ID = ASCR.PerHectareWaterUomID)\n"
			+ "LEFT JOIN general_uom perAcreWaterUOM ON (perAcreWaterUOM.ID = ASCR.PerAcreWaterUomID) order by ASCR.ID desc", nativeQuery = true)
	List<AgriStressControlRecommendationInfDto> getAgriStressRecommendation();

	@Query(value = "SELECT ASCR.ID,GS.Name as State, GD.Name as District,ASCR.Status,AC.Name as Commodity,ACM.Name as StressControlMeasure ,agri_stress.Name as Stress,\n" + 
			"															AA.Name as Agrochemical, AAAT.Name as AgroApplicationType,\n" +
			"															AAI.Name as AgroChemicalInstructions,DosePerAcre,DosePerHectare,WaterPerAcre,WaterPerHectare,\n" + 
			"															perHectareUOM.Name as PerHectareUOM,perAcreUOM.Name as PerAcreUOM,perHectareWaterUOM.Name as PerHectareWaterUOM,\n" + 
			"															perAcreWaterUOM.Name as PerAcreWaterUOM,AR.Name as RecommendationName, ASCR.IsValid, ASCR.ErrorMessage FROM agri_stress_control_recommendation ASCR\n" + 
			"															LEFT JOIN agri_commodity AC ON (AC.ID = ASCR.CommodityID)\n" + 
			"															LEFT JOIN agri_control_measures ACM ON (ACM.ID = ASCR.StressControlMeasureID)\n" + 
			"					                                        Inner JOIN agri_stress agri_stress ON (agri_stress.ID = ASCR.StressID)\n" + 
			"														     left JOIN agri_commodity_stress ACS on (ACS.CommodityID = ASCR.CommodityID  and ACS.StressID = ASCR.StressID)\n" + 
			"															LEFT JOIN agri_commodity_agrochemical AAG ON (AAG.ID = ASCR.AgrochemicalID)\n" +
			"															LEFT JOIN agri_agrochemical AA ON (AA.ID = AAG.AgrochemicalID)\n" +
			"															LEFT JOIN   agri_agrochemical_application_type AAAT ON (AAAT.ID = ASCR.AgrochemApplicationTypeID)\n" +
			"															LEFT JOIN general_uom perHectareUOM ON (perHectareUOM.ID = ASCR.PerHectareUomID)\n" + 
			"															LEFT JOIN general_uom perAcreUOM ON (perAcreUOM.ID = ASCR.PerAcreUomID)\n" + 
			"															LEFT JOIN general_uom perHectareWaterUOM ON (perHectareWaterUOM.ID = ASCR.PerHectareWaterUomID) \n" + 
			"															LEFT JOIN general_uom perAcreWaterUOM ON (perAcreWaterUOM.ID = ASCR.PerAcreWaterUomID)\n" + 
			"					                                        inner Join agri_recommendation AR ON (AR.ID = ASCR.RecomendationID)\n" + 
			"					                                        left join agri_agrochemical_instructions AAI ON (AAI.ID = ASCR.AgrochemicalInstructionID)\n" + 
			"                                                            Inner join geo_state GS ON (GS.StateCode = ASCR.StateCode)\n" + 
			"                                                            Inner join geo_district GD ON (GD.DistrictCode = ASCR.DistrictCode and GD.StateCode = GS.StateCode)\n" + 
			"													           where agri_stress.Name like :searchText\n" + 
			"																OR AC.Name like :searchText\n" + 
			"																OR ACM.Name like :searchText\n" + 
			"																OR AA.Name like :searchText\n" +
			"																OR AAAT.Name like :searchText\n" + 
			"																OR AAI.Name like :searchText\n" + 
			"																OR ASCR.DosePerAcre like :searchText\n" + 
			"																OR ASCR.DosePerHectare like :searchText\n" + 
			"																OR ASCR.WaterPerAcre like :searchText\n" + 
			"																OR ASCR.WaterPerHectare like :searchText\n" + 
			"																OR perHectareUOM.Name like :searchText\n" + 
			"																OR perAcreUOM.Name like :searchText\n" + 
			"																OR perHectareWaterUOM.Name like :searchText\n" + 
			"																OR perAcreWaterUOM.Name like :searchText\n" + 
			"					                                            OR AR.Name like :searchText",
			countQuery = "SELECT count(ASCR.ID) as Count \n" + 
					" FROM agri_stress_control_recommendation ASCR\n" + 
					"															LEFT JOIN agri_commodity AC ON (AC.ID = ASCR.CommodityID)\n" + 
					"															LEFT JOIN agri_control_measures ACM ON (ACM.ID = ASCR.StressControlMeasureID)\n" + 
					"					                                        Inner JOIN agri_stress agri_stress ON (agri_stress.ID = ASCR.StressID)\n" + 
					"														     left JOIN agri_commodity_stress ACS on (ACS.CommodityID = ASCR.CommodityID  and ACS.StressID = ASCR.StressID)\n" + 
					"															LEFT JOIN agri_commodity_agrochemical AAG ON (AAG.ID = ASCR.AgrochemicalID)\n" + 
					"															LEFT JOIN agri_agrochemical AA ON (AA.ID = AAG.AgrochemicalID)\n" +
					"															LEFT JOIN   agri_agrochemical_application_type AAAT ON (AAAT.ID = ASCR.AgrochemApplicationTypeID)\n" +
					"															LEFT JOIN general_uom perHectareUOM ON (perHectareUOM.ID = ASCR.PerHectareUomID)\n" + 
					"															LEFT JOIN general_uom perAcreUOM ON (perAcreUOM.ID = ASCR.PerAcreUomID)\n" + 
					"															LEFT JOIN general_uom perHectareWaterUOM ON (perHectareWaterUOM.ID = ASCR.PerHectareWaterUomID) \n" + 
					"															LEFT JOIN general_uom perAcreWaterUOM ON (perAcreWaterUOM.ID = ASCR.PerAcreWaterUomID)\n" + 
					"					                                        inner Join agri_recommendation AR ON (AR.ID = ASCR.RecomendationID)\n" + 
					"					                                        left join agri_agrochemical_instructions AAI ON (AAI.ID = ASCR.AgrochemicalInstructionID)\n" + 
					"                                                            Inner join geo_state GS ON (GS.StateCode = ASCR.StateCode)\n" + 
					"                                                            Inner join geo_district GD ON (GD.DistrictCode = ASCR.DistrictCode and GD.StateCode = GS.StateCode)\n" + 
					"													           where agri_stress.Name like :searchText\n" + 
					"																OR AC.Name like :searchText\n" + 
					"																OR ACM.Name like :searchText\n" + 
					"																OR AA.Name like :searchText\n" +
					"																OR AAAT.Name like :searchText\n" + 
					"																OR AAI.Name like :searchText\n" + 
					"																OR ASCR.DosePerAcre like :searchText\n" + 
					"																OR ASCR.DosePerHectare like :searchText\n" + 
					"																OR ASCR.WaterPerAcre like :searchText\n" + 
					"																OR ASCR.WaterPerHectare like :searchText\n" + 
					"																OR perHectareUOM.Name like :searchText\n" + 
					"																OR perAcreUOM.Name like :searchText\n" + 
					"																OR perHectareWaterUOM.Name like :searchText\n" + 
					"																OR perAcreWaterUOM.Name like :searchText\n" + 
					"					                                            OR AR.Name like :searchText", nativeQuery = true)
	Page<AgriStressControlRecommendationInfDto> getAgriStressControlRecommendation(Pageable sortedByIdDesc, String searchText);
	
	@Query(value = "SELECT ASCR.ID,ASCR.Status, AC.Name as Commodity,ASCM.Name as StressControlMeasure ,ACS.Name as Stress,\n" +
			"					AA.Name as Agrochemical, STP.Name as StressType, AAAT.Name as AgroApplicationType,\n" +
			"					Instructions,AgroChemicalInstructions,DosePerAcre,DosePerHectare,WaterPerAcre,WaterPerHectare,\n" + 
			"					perHectareUOM.Name as PerHectareUOM,perAcreUOM.Name as PerAcreUOM,perHectareWaterUOM.Name as PerHectareWaterUOM,\n" + 
			"					perAcreWaterUOM.Name as PerAcreWaterUOM FROM agri_stress_control_recommendation_missing ASCR \n" +
			"					LEFT JOIN agri_commodity AC ON (AC.ID = ASCR.CommodityID)\n" + 
			"					LEFT JOIN agri_stress_control_measures ASCM ON (ASCM.ID = ASCR.StressControlMeasureID)\n" + 
			"					LEFT join agri_commodity_stress ACS on (ACS.ID = ASCR.StressID)\n" +
			"					LEFT JOIN agri_commodity_agrochemical AAG ON (AAG.ID = ASCR.AgrochemicalID)\n" +
			"					LEFT JOIN agri_agrochemical AA ON (AA.ID = AAG.AgrochemicalID)\n" +
			"					LEFT JOIN agri_stress_type STP ON (STP.ID = ASCR.StressTypeID)\n" +
			"					LEFT JOIN   agri_agrochemical_application_type AAAT ON (AAAT.ID = ASCR.AgrochemApplicationTypeID)\n" +
			"					LEFT JOIN general_uom perHectareUOM ON (perHectareUOM.ID = ASCR.PerHectareUomID)\n" + 
			"					LEFT JOIN general_uom perAcreUOM ON (perAcreUOM.ID = ASCR.PerAcreUomID)\n" + 
			"					LEFT JOIN general_uom perHectareWaterUOM ON (perHectareWaterUOM.ID = ASCR.PerHectareWaterUomID)\n" + 
			"					LEFT JOIN general_uom perAcreWaterUOM ON (perAcreWaterUOM.ID = ASCR.PerAcreWaterUomID)\n" + 
			"			           where ACS.Name like :searchText\n" +
			"						OR AC.Name like :searchText\n" + 
			"						OR ASCM.Name like :searchText\n" + 
			"						OR AA.Name like :searchText\n" +
			"						OR STP.Name like :searchText\n" + 
			"						OR AAAT.Name like :searchText\n" + 
			"						OR ASCR.Instructions like :searchText\n" + 
			"						OR ASCR.AgroChemicalInstructions like :searchText\n" + 
			"						OR ASCR.DosePerAcre like :searchText\n" + 
			"						OR ASCR.DosePerHectare like :searchText\n" + 
			"						OR ASCR.WaterPerAcre like :searchText\n" + 
			"						OR ASCR.WaterPerHectare like :searchText\n" + 
			"						OR perHectareUOM.Name like :searchText\n" + 
			"						OR perAcreUOM.Name like :searchText\n" + 
			"						OR perHectareWaterUOM.Name like :searchText\n" + 
			"						OR perAcreWaterUOM.Name like '%Wheat%'",
			countQuery = "SELECT count(ASCR.ID) as Count \n" +
					" FROM agri_stress_control_recommendation_missing ASCR\n" +
					"					LEFT JOIN agri_commodity AC ON (AC.ID = ASCR.CommodityID)\n" +
					"					LEFT JOIN agri_stress_control_measures ASCM ON (ASCM.ID = ASCR.StressControlMeasureID)\n" +
					"					LEFT join agri_commodity_stress ACS on (ACS.ID = ASCR.StressID)\n" +
					"					LEFT JOIN agri_commodity_agrochemical AAG ON (AAG.ID = ASCR.AgrochemicalID)\n" +
					"					LEFT JOIN agri_agrochemical AA ON (AA.ID = AAG.AgrochemicalID)\n" +
					"					LEFT JOIN agri_stress_type STP ON (STP.ID = ASCR.StressTypeID)\n" +
					"					LEFT JOIN   agri_agrochemical_application_type AAAT ON (AAAT.ID = ASCR.AgrochemApplicationTypeID)\n" +
					"					LEFT JOIN general_uom perHectareUOM ON (perHectareUOM.ID = ASCR.PerHectareUomID)\n" +
					"					LEFT JOIN general_uom perAcreUOM ON (perAcreUOM.ID = ASCR.PerAcreUomID)\n" +
					"					LEFT JOIN general_uom perHectareWaterUOM ON (perHectareWaterUOM.ID = ASCR.PerHectareWaterUomID)\n" +
					"					LEFT JOIN general_uom perAcreWaterUOM ON (perAcreWaterUOM.ID = ASCR.PerAcreWaterUomID)" +
					"			           where ACS.Name like :searchText\n" +
					"						OR AC.Name like :searchText\n" + 
					"						OR ASCM.Name like :searchText\n" + 
					"						OR AA.Name like :searchText\n" +
					"						OR STP.Name like :searchText\n" + 
					"						OR AAAT.Name like :searchText\n" + 
					"						OR ASCR.Instructions like :searchText\n" + 
					"						OR ASCR.AgroChemicalInstructions like :searchText\n" + 
					"						OR ASCR.DosePerAcre like :searchText\n" + 
					"						OR ASCR.DosePerHectare like :searchText\n" + 
					"						OR ASCR.WaterPerAcre like :searchText\n" + 
					"						OR ASCR.WaterPerHectare like :searchText\n" + 
					"						OR perHectareUOM.Name like :searchText\n" + 
					"						OR perAcreUOM.Name like :searchText\n" + 
					"						OR perHectareWaterUOM.Name like :searchText\n" + 
					"						OR perAcreWaterUOM.Name like '%Wheat%'", nativeQuery = true)
	Page<AgriStressControlRecommendationInfDto> getAgriStressControlRecommendationMissing(Pageable sortedByIdDesc, String searchText);

	@Query(value = "SELECT ASCR.ID,GS.Name as State, GD.Name as District,ASCR.Status, AC.Name as Commodity,ACM.Name as StressControlMeasure ,agri_stress.Name as Stress,\n" + 
			"															AA.Name as Agrochemical, AAAT.Name as AgroApplicationType,\n" +
			"					                                        AAI.Name as AgroChemicalInstructions,DosePerAcre,DosePerHectare,WaterPerAcre,WaterPerHectare,\n" + 
			"															perHectareUOM.Name as PerHectareUOM,perAcreUOM.Name as PerAcreUOM,perHectareWaterUOM.Name as PerHectareWaterUOM,\n" + 
			"															perAcreWaterUOM.Name as PerAcreWaterUOM,AR.Name as RecommendationName, ASCR.IsValid, ASCR.ErrorMessage FROM agri_stress_control_recommendation ASCR \n" + 
			"															LEFT JOIN agri_commodity AC ON (AC.ID = ASCR.CommodityID)\n" + 
			"															LEFT JOIN agri_control_measures ACM ON (ACM.ID = ASCR.StressControlMeasureID)\n" + 
			"					                                        Inner JOIN agri_stress agri_stress ON (agri_stress.ID = ASCR.StressID)\n" + 
			"															left JOIN agri_commodity_stress ACS on (ACS.CommodityID = ASCR.CommodityID  and ACS.StressID = ASCR.StressID)\n" + 
			"															LEFT JOIN agri_commodity_agrochemical AAG ON (AAG.ID = ASCR.AgrochemicalID)\n" +
			"															LEFT JOIN agri_agrochemical AA ON (AA.ID = AAG.AgrochemicalID)\n" +
			"															LEFT JOIN   agri_agrochemical_application_type AAAT ON (AAAT.ID = ASCR.AgrochemApplicationTypeID)\n" +
			"															LEFT JOIN general_uom perHectareUOM ON (perHectareUOM.ID = ASCR.PerHectareUomID) \n" + 
			"															LEFT JOIN general_uom perAcreUOM ON (perAcreUOM.ID = ASCR.PerAcreUomID)\n" + 
			"															LEFT JOIN general_uom perHectareWaterUOM ON (perHectareWaterUOM.ID = ASCR.PerHectareWaterUomID)\n" + 
			"															LEFT JOIN general_uom perAcreWaterUOM ON (perAcreWaterUOM.ID = ASCR.PerAcreWaterUomID)\n" + 
			"					                                        inner Join agri_recommendation AR ON (AR.ID = ASCR.RecomendationID)\n" + 
			"					                                        left join agri_agrochemical_instructions AAI ON (AAI.ID = ASCR.AgrochemicalInstructionID)\n" + 
			"                                                            Inner join geo_state GS ON (GS.StateCode = ASCR.StateCode)\n" + 
			"                                                            Inner join geo_district GD ON (GD.DistrictCode = ASCR.DistrictCode and GD.StateCode = GS.StateCode)\n" + 
			"													           where ASCR.IsValid = 0 and (agri_stress.Name like :searchText\n" + 
			"																OR AC.Name like :searchText\n" + 
			"																OR ACM.Name like :searchText\n" + 
			"																OR AA.Name like :searchText\n" +
			"																OR AAAT.Name like :searchText\n" + 
			"																OR ASCR.Instructions like :searchText\n" + 
			"																OR ASCR.AgroChemicalInstructions like :searchText\n" + 
			"																OR ASCR.DosePerAcre like :searchText\n" + 
			"																OR ASCR.DosePerHectare like :searchText\n" + 
			"																OR ASCR.WaterPerAcre like :searchText\n" + 
			"																OR ASCR.WaterPerHectare like :searchText\n" + 
			"																OR perHectareUOM.Name like :searchText\n" + 
			"																OR perAcreUOM.Name like :searchText\n" + 
			"																OR perHectareWaterUOM.Name like :searchText\n" + 
			"																OR perAcreWaterUOM.Name like :searchText)",
			countQuery = "SELECT count(ASCR.ID) as Count \n" + 
					" FROM agri_stress_control_recommendation ASCR \n" + 
					"															LEFT JOIN agri_commodity AC ON (AC.ID = ASCR.CommodityID)\n" + 
					"															LEFT JOIN agri_control_measures ACM ON (ACM.ID = ASCR.StressControlMeasureID)\n" + 
					"					                                        Inner JOIN agri_stress agri_stress ON (agri_stress.ID = ASCR.StressID)\n" + 
					"															left JOIN agri_commodity_stress ACS on (ACS.CommodityID = ASCR.CommodityID  and ACS.StressID = ASCR.StressID)\n" + 
					"															LEFT JOIN agri_commodity_agrochemical AAG ON (AAG.ID = ASCR.AgrochemicalID)\n" +
					"															LEFT JOIN agri_agrochemical AA ON (AA.ID = AAG.AgrochemicalID)\n" +
					"															LEFT JOIN   agri_agrochemical_application_type AAAT ON (AAAT.ID = ASCR.AgrochemApplicationTypeID)\n" +
					"															LEFT JOIN general_uom perHectareUOM ON (perHectareUOM.ID = ASCR.PerHectareUomID) \n" + 
					"															LEFT JOIN general_uom perAcreUOM ON (perAcreUOM.ID = ASCR.PerAcreUomID)\n" + 
					"															LEFT JOIN general_uom perHectareWaterUOM ON (perHectareWaterUOM.ID = ASCR.PerHectareWaterUomID)\n" + 
					"															LEFT JOIN general_uom perAcreWaterUOM ON (perAcreWaterUOM.ID = ASCR.PerAcreWaterUomID)\n" + 
					"					                                        inner Join agri_recommendation AR ON (AR.ID = ASCR.RecomendationID)\n" + 
					"					                                        left join agri_agrochemical_instructions AAI ON (AAI.ID = ASCR.AgrochemicalInstructionID)\n" + 
					"                                                            Inner join geo_state GS ON (GS.StateCode = ASCR.StateCode)\n" + 
					"                                                            Inner join geo_district GD ON (GD.DistrictCode = ASCR.DistrictCode and GD.StateCode = GS.StateCode)\n" + 
					"													           where ASCR.IsValid = 0 and (agri_stress.Name like :searchText\n" + 
					"																OR AC.Name like :searchText\n" + 
					"																OR ACM.Name like :searchText\n" + 
					"																OR AA.Name like :searchText\n" +
					"																OR AAAT.Name like :searchText\n" + 
					"																OR ASCR.Instructions like :searchText\n" + 
					"																OR ASCR.AgroChemicalInstructions like :searchText\n" + 
					"																OR ASCR.DosePerAcre like :searchText\n" + 
					"																OR ASCR.DosePerHectare like :searchText\n" + 
					"																OR ASCR.WaterPerAcre like :searchText\n" + 
					"																OR ASCR.WaterPerHectare like :searchText\n" + 
					"																OR perHectareUOM.Name like :searchText\n" + 
					"																OR perAcreUOM.Name like :searchText\n" + 
					"																OR perHectareWaterUOM.Name like :searchText\n" + 
					"																OR perAcreWaterUOM.Name like :searchText)", nativeQuery = true)
	Page<AgriStressControlRecommendationInfDto> getAgriStressControlRecommendationInvalidated(Pageable sortedByIdDesc,
			String searchText);
	
	@Query(value = "SELECT ASCR.ID,ASCR.Status, AC.Name as Commodity,ASCM.Name as StressControlMeasure ,ACS.Name as Stress,\n" +
			"					AA.Name as Agrochemical, STP.Name as StressType, AAAT.Name as AgroApplicationType,\n" +
			"					Instructions,AgroChemicalInstructions,DosePerAcre,DosePerHectare,WaterPerAcre,WaterPerHectare,\n" +
			"					perHectareUOM.Name as PerHectareUOM,perAcreUOM.Name as PerAcreUOM,perHectareWaterUOM.Name as PerHectareWaterUOM,\n" +
			"					perAcreWaterUOM.Name as PerAcreWaterUOM FROM agri_stress_control_recommendation_missing ASCR \n" +
			"					LEFT JOIN agri_commodity AC ON (AC.ID = ASCR.CommodityID)\n" +
			"					LEFT JOIN agri_stress_control_measures ASCM ON (ASCM.ID = ASCR.StressControlMeasureID)\n" +
			"					LEFT JOIN agri_commodity_stress ACS on (ACS.ID = ASCR.StressID)\n" +
			"					LEFT JOIN agri_commodity_agrochemical AAG ON (AAG.ID = ASCR.AgrochemicalID)\n" +
			"					LEFT JOIN agri_agrochemical AA ON (AA.ID = AAG.AgrochemicalID)\n" +
			"					LEFT JOIN agri_stress_type STP ON (STP.ID = ASCR.StressTypeID)\n" +
			"					LEFT JOIN   agri_agrochemical_application_type AAAT ON (AAAT.ID = ASCR.AgrochemApplicationTypeID)\n" +
			"					LEFT JOIN general_uom perHectareUOM ON (perHectareUOM.ID = ASCR.PerHectareUomID)\n" +
			"					LEFT JOIN general_uom perAcreUOM ON (perAcreUOM.ID = ASCR.PerAcreUomID)\n" +
			"					LEFT JOIN general_uom perHectareWaterUOM ON (perHectareWaterUOM.ID = ASCR.PerHectareWaterUomID)\n" +
			"					LEFT JOIN general_uom perAcreWaterUOM ON (perAcreWaterUOM.ID = ASCR.PerAcreWaterUomID)\n" +
			"			           where ASCR.IsValid = 0 and (ACS.Name like :searchText\n" +
			"						OR AC.Name like :searchText\n" +
			"						OR ASCM.Name like :searchText\n" +
			"						OR AA.Name like :searchText\n" +
			"						OR STP.Name like :searchText\n" +
			"						OR AAAT.Name like :searchText\n" +
			"						OR ASCR.Instructions like :searchText\n" +
			"						OR ASCR.AgroChemicalInstructions like :searchText\n" +
			"						OR ASCR.DosePerAcre like :searchText\n" +
			"						OR ASCR.DosePerHectare like :searchText\n" +
			"						OR ASCR.WaterPerAcre like :searchText\n" +
			"						OR ASCR.WaterPerHectare like :searchText\n" +
			"						OR perHectareUOM.Name like :searchText\n" +
			"						OR perAcreUOM.Name like :searchText\n" +
			"						OR perHectareWaterUOM.Name like :searchText\n" +
			"						OR perAcreWaterUOM.Name like '%Wheat%')",
			countQuery = "SELECT coun(ASCR.ID) as Count \n" +
					" FROM agri_stress_control_recommendation_missing ASCR \n" +
					"					LEFT JOIN agri_commodity AC ON (AC.ID = ASCR.CommodityID)\n" +
					"					LEFT JOIN agri_stress_control_measures ASCM ON (ASCM.ID = ASCR.StressControlMeasureID)\n" +
					"					LEFT JOIN agri_commodity_stress ACS on (ACS.ID = ASCR.StressID)\n" +
					"					LEFT JOIN agri_commodity_agrochemical AAG ON (AAG.ID = ASCR.AgrochemicalID)\n" +
					"					LEFT JOIN agri_agrochemical AA ON (AA.ID = AAG.AgrochemicalID)\n" +
					"					LEFT JOIN agri_stress_type STP ON (STP.ID = ASCR.StressTypeID)\n" +
					"					LEFT JOIN   agri_agrochemical_application_type AAAT ON (AAAT.ID = ASCR.AgrochemApplicationTypeID)\n" +
					"					LEFT JOIN general_uom perHectareUOM ON (perHectareUOM.ID = ASCR.PerHectareUomID)\n" +
					"					LEFT JOIN general_uom perAcreUOM ON (perAcreUOM.ID = ASCR.PerAcreUomID)\n" +
					"					LEFT JOIN general_uom perHectareWaterUOM ON (perHectareWaterUOM.ID = ASCR.PerHectareWaterUomID)\n" +
					"					LEFT JOIN general_uom perAcreWaterUOM ON (perAcreWaterUOM.ID = ASCR.PerAcreWaterUomID)\n" +
					"			           where ASCR.IsValid = 0 and (ACS.Name like :searchText\n" +
					"						OR AC.Name like :searchText\n" +
					"						OR ASCM.Name like :searchText\n" +
					"						OR AA.Name like :searchText\n" +
					"						OR STP.Name like :searchText\n" +
					"						OR AAAT.Name like :searchText\n" +
					"						OR ASCR.Instructions like :searchText\n" +
					"						OR ASCR.AgroChemicalInstructions like :searchText\n" +
					"						OR ASCR.DosePerAcre like :searchText\n" +
					"						OR ASCR.DosePerHectare like :searchText\n" +
					"						OR ASCR.WaterPerAcre like :searchText\n" +
					"						OR ASCR.WaterPerHectare like :searchText\n" +
					"						OR perHectareUOM.Name like :searchText\n" +
					"						OR perAcreUOM.Name like :searchText\n" +
					"						OR perHectareWaterUOM.Name like :searchText\n" +
					"						OR perAcreWaterUOM.Name like '%Wheat%')", nativeQuery = true)
	Page<AgriStressControlRecommendationInfDto> getAgriStressControlRecommendationMissingInvalidated(Pageable sortedByIdDesc,
			String searchText);
	
	@Query(value = "SELECT ASCR.ID,GS.Name as State, GD.Name as District,ASCR.Status,AC.Name as Commodity,ACM.Name as StressControlMeasure ,agri_stress.Name as Stress, \n" + 
			"																		AA.Name as Agrochemical, AAAT.Name as AgroApplicationType,\n" + 
			"																		AAI.Name as AgroChemicalInstructions,DosePerAcre,DosePerHectare,WaterPerAcre,WaterPerHectare, \n" + 
			"																		perHectareUOM.Name as PerHectareUOM,perAcreUOM.Name as PerAcreUOM,perHectareWaterUOM.Name as PerHectareWaterUOM, \n" + 
			"																		perAcreWaterUOM.Name as PerAcreWaterUOM,AR.Name as RecommendationName, ASCR.IsValid, ASCR.ErrorMessage FROM agri_stress_control_recommendation ASCR \n" + 
			"																		LEFT JOIN agri_commodity AC ON (AC.ID = ASCR.CommodityID) \n" + 
			"																		LEFT JOIN agri_control_measures ACM ON (ACM.ID = ASCR.StressControlMeasureID) \n" + 
			"								                                        Inner JOIN agri_stress agri_stress ON (agri_stress.ID = ASCR.StressID) \n" + 
			"																	     left JOIN agri_commodity_stress ACS on (ACS.CommodityID = ASCR.CommodityID  and ACS.StressID = ASCR.StressID) \n" + 
			"																		LEFT JOIN agri_commodity_agrochemical AAG ON (AAG.ID = ASCR.AgrochemicalID)\n" + 
			"																		LEFT JOIN agri_agrochemical AA ON (AA.ID = AAG.AgrochemicalID)\n" + 
			"																		LEFT JOIN   agri_agrochemical_application_type AAAT ON (AAAT.ID = ASCR.AgrochemApplicationTypeID)\n" + 
			"																		LEFT JOIN general_uom perHectareUOM ON (perHectareUOM.ID = ASCR.PerHectareUomID) \n" + 
			"																		LEFT JOIN general_uom perAcreUOM ON (perAcreUOM.ID = ASCR.PerAcreUomID) \n" + 
			"																		LEFT JOIN general_uom perHectareWaterUOM ON (perHectareWaterUOM.ID = ASCR.PerHectareWaterUomID)  \n" + 
			"																		LEFT JOIN general_uom perAcreWaterUOM ON (perAcreWaterUOM.ID = ASCR.PerAcreWaterUomID) \n" + 
			"								                                        inner Join agri_recommendation AR ON (AR.ID = ASCR.RecomendationID) \n" + 
			"								                                        left join agri_agrochemical_instructions AAI ON (AAI.ID = ASCR.AgrochemicalInstructionID) \n" + 
			"			                                                            Inner join geo_state GS ON (GS.StateCode = ASCR.StateCode) \n" + 
			"			                                                            Inner join geo_district GD ON (GD.DistrictCode = ASCR.DistrictCode and GD.StateCode = GS.StateCode) \n" + 
			"																		where ASCR.StateCode = :stateCode OR ASCR.DistrictCode = :districtCode OR ASCR.CommodityID = :commodityId OR ASCR.StressControlMeasureID = :controlMeasureId\n" + 
			"                                                                        OR ASCR.StressID = :stressId OR ASCR.RecomendationID = :recomendationID OR ASCR.AgrochemicalID = :agrochemicalId", 
			countQuery = "SELECT count(ASCR.ID) as Count \n" + 
					"																		FROM agri_stress_control_recommendation ASCR \n" + 
					"																		LEFT JOIN agri_commodity AC ON (AC.ID = ASCR.CommodityID) \n" + 
					"																		LEFT JOIN agri_control_measures ACM ON (ACM.ID = ASCR.StressControlMeasureID) \n" + 
					"								                                        Inner JOIN agri_stress agri_stress ON (agri_stress.ID = ASCR.StressID) \n" + 
					"																	     left JOIN agri_commodity_stress ACS on (ACS.CommodityID = ASCR.CommodityID  and ACS.StressID = ASCR.StressID) \n" + 
					"																		LEFT JOIN agri_commodity_agrochemical AAG ON (AAG.ID = ASCR.AgrochemicalID)\n" + 
					"																		LEFT JOIN agri_agrochemical AA ON (AA.ID = AAG.AgrochemicalID)\n" + 
					"																		LEFT JOIN   agri_agrochemical_application_type AAAT ON (AAAT.ID = ASCR.AgrochemApplicationTypeID)\n" + 
					"																		LEFT JOIN general_uom perHectareUOM ON (perHectareUOM.ID = ASCR.PerHectareUomID) \n" + 
					"																		LEFT JOIN general_uom perAcreUOM ON (perAcreUOM.ID = ASCR.PerAcreUomID) \n" + 
					"																		LEFT JOIN general_uom perHectareWaterUOM ON (perHectareWaterUOM.ID = ASCR.PerHectareWaterUomID)  \n" + 
					"																		LEFT JOIN general_uom perAcreWaterUOM ON (perAcreWaterUOM.ID = ASCR.PerAcreWaterUomID) \n" + 
					"								                                        inner Join agri_recommendation AR ON (AR.ID = ASCR.RecomendationID) \n" + 
					"								                                        left join agri_agrochemical_instructions AAI ON (AAI.ID = ASCR.AgrochemicalInstructionID) \n" + 
					"			                                                            Inner join geo_state GS ON (GS.StateCode = ASCR.StateCode) \n" + 
					"			                                                            Inner join geo_district GD ON (GD.DistrictCode = ASCR.DistrictCode and GD.StateCode = GS.StateCode) \n" + 
					"																		where ASCR.StateCode = :stateCode OR ASCR.DistrictCode = :districtCode OR ASCR.CommodityID = :commodityId OR ASCR.StressControlMeasureID = :controlMeasureId\n" + 
					"                                                                        OR ASCR.StressID = :stressId OR ASCR.RecomendationID = :recomendationID OR ASCR.AgrochemicalID = :agrochemicalId", nativeQuery = true)
	Page<AgriStressControlRecommendationInfDto> getAgriStressControlRecommendationByMultiSearchFilters(Pageable sortedByIdDesc,String stateCode,String districtCode,String commodityId,String controlMeasureId,String stressId,String recomendationID,String agrochemicalId);

}
