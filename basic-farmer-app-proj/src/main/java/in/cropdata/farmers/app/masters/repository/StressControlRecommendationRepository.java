package in.cropdata.farmers.app.masters.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import in.cropdata.farmers.app.DTO.FarmerDetailsByCaseIdDTO;
import in.cropdata.farmers.app.DTO.StressDetailsDTO;
import in.cropdata.farmers.app.masters.dto.StressControlRecommendationDTO;
import in.cropdata.farmers.app.masters.model.StressControlRecommendation;

@Repository
public interface StressControlRecommendationRepository extends JpaRepository<StressControlRecommendation, Integer> {

    @Query(value = "select \n" +
            "\tif(ascr.StressControlMeasureID = 3, ascr.AgrochemicalInstructionID, ascr.RecomendationID) Id,\n" +
            "    acm.Name as StressControlMeasureName,\n" +
            "\tif (ascr.StressControlMeasureID = 3, concat(aaat.Name, \" of \", aa.Name, \" @ \", ascr.DosePerAcre, \" \", gu1.Description, \" in \", ascr.WaterPerAcre, \" \", gu2.Description, \".\"),ar.Name) as Recommendation\n" +
            "\tfrom cdt_master_data.agri_stress_control_recommendation as ascr\n" +
            "left join cdt_master_data.agri_recommendation as ar on ar.ID = ascr.RecomendationID\n" +
            "left join cdt_master_data.agri_agrochemical as aa on aa.ID = ascr.AgrochemicalID\n" +
            "left join cdt_master_data.agri_agrochemical_instructions as aai on aai.ID = ascr.AgrochemicalInstructionID\n" +
            "left join cdt_master_data.general_uom as gu1 on gu1.ID = ascr.PerAcreUomID\n" +
            "left join cdt_master_data.general_uom as gu2 on gu2.ID = ascr.PerAcreWaterUomID\n" +
            "left join cdt_master_data.agri_agrochemical_application_type as aaat on aaat.ID = ascr.AgrochemApplicationTypeID\n" +
            "left join cdt_master_data.agri_control_measures as acm on acm.ID = ascr.StressControlMeasureID\n" +
            "where DistrictCode = ?1 and ascr.CommodityID = ?2 and StressID in ( \n" +
            "select distinct stressID from cdt_master_data.flipbook_symptoms fs where fs.ID in (?3)) \n" +
            "order by StressControlMeasureID", nativeQuery = true)
    List<StressControlRecommendationDTO> listOfStressControlRecommendation(Integer districtCode, Integer commodityId , List<Integer> symptomIDs);

    @Query(value = "select f.village_id as VillageId,\n" + 
    		"f.id as FarmerId,\n" + 
    		"8 as GtTypeId,\n" + 
    		"'Farmer' as RoleName,\n" + 
    		"'Farm' as GtLevel from drkrishi_ts.farmer f inner join\n" + 
    		"drkrishi_ts.farmer_farm ff on f.id=ff.farmer_id\n" + 
    		"inner join drkrishi_ts.farm_case fc on fc.farm_id=ff.id\n" + 
    		"inner join drkrishi_ts.case_crop_info cci on fc.id=cci.case_id where cci.case_id=?1", nativeQuery = true)
    FarmerDetailsByCaseIdDTO getFarmerDetailsByCaseId(String caseID);
    
    @Query(value = "select a.CaseID,\n" + 
    		"a.SymptomID,\n" + 
    		" fs.GenericImage,\n" + 
    		" ass.Name as StressName,\n" + 
    		" fs.Symptom ,\n" + 
    		" any_value(fs.CommodityID) as CommodityID,\n" + 
    		" if (a.Reported='Yes' ,'true','false') as Reported \n" + 
    		" from cdt_master_data.case_advisory_cache a inner join \n" + 
    		"cdt_master_data.flipbook_symptoms fs on a.SymptomID=fs.ID\n" + 
    		"inner join cdt_master_data.agri_stress ass on fs.StressID=ass.ID\n" + 
    		"where a.CaseID = ?1 \n" + 
    		" and a.SymptomID in (?2)\n" + 
    		" and a.CalendarWeek = WEEK(CURRENT_DATE())\n" + 
    		"AND a.calendarYear = YEAR(CURRENT_DATE())", nativeQuery = true)
    List<StressDetailsDTO> getStressList(String caseID, List<Integer> symptomIDs);
    
}
