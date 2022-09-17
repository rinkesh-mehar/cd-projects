package in.cropdata.farmers.app.masters.repository;

import in.cropdata.farmers.app.masters.model.FlipbookSymptoms;
import in.cropdata.farmers.app.masters.dto.FlipbookSymptomsDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author RinkeshKM
 * @project BasicFarmersAppAPI
 * @created 22/03/2021 - 12:48 PM
 */

@Repository
public interface FlipbookSymptomRepository extends JpaRepository<FlipbookSymptoms, Integer> {

    @Query(value = "SELECT\n" + 
    		"fs.id as SymptomID,\n" + 
    		"fs.GenericImage,\n" + 
    		"cd.FarmerID,\n" + 
    		"ass.Name as StressName,\n" + 
    		"fs.Symptom,\n" + 
    		"fdt.DeviceToken,\n" + 
    		"any_value(fs.CommodityID) as CommodityID\n" + 
    		"FROM\n" + 
    		"cdt_master_data.dcc_daily_case_stress d\n" + 
    		"inner JOIN\n" + 
    		"cdt_master_data.dcc_daily_zonal_stress zs ON zs.id = d.dailyzonalstressID \n" + 
    		"inner JOIN\n" + 
    		"cdt_master_data.zonal_variety zv ON zv.ZonalCommodityID = zs.ZonalCommodityID\n" + 
    		"inner JOIN\n" + 
    		"cdt_master_data.flipbook_symptoms fs ON fs.stressID = zs.StressID\n" + 
    		" AND zv.CommodityID = fs.CommodityID\n" + 
    		"inner join\n" + 
    		"gstm_transitory.case_details cd on cd.CaseID = d.CaseID\n" + 
    		"inner JOIN\n" + 
    		"drkrishi_ts.farmer_device_tokens fdt on fdt.FarmerID = cd.FarmerID\n" + 
    		"inner join cdt_master_data.agri_stress ass on zs.StressID=ass.ID\n" + 
    		"WHERE d.CaseID = ?1\n" + 
    		"AND d.CalendarWeek = WEEK(CURRENT_DATE())\n" + 
    		"AND d.calendarYear = YEAR(CURRENT_DATE())\n" + 
    		"AND zs.StressStatus > 3\n" + 
    		"group by \n" + 
    		"d.CaseID,\n" + 
    		"fs.id,\n" + 
    		"fs.GenericImage ,\n" + 
    		"cd.FarmerID,\n" + 
    		"fdt.DeviceToken", nativeQuery = true)
    List<FlipbookSymptomsDTO> findImagesByCommodityIDAndPhenophaseID(String caseID);

    FlipbookSymptoms findAllById(Integer symptomID);
}
