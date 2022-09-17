package in.cropdata.farmers.app.drk.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import in.cropdata.farmers.app.DTO.CaseCropInfoDTO;
import in.cropdata.farmers.app.DTO.FarmerNotifyDTO;
import in.cropdata.farmers.app.drk.model.DrkFarmCase;

/**
 * @author pallavi-waghmare
 *
 */
@Repository
public interface DrkFarmCaseRepository extends JpaRepository<DrkFarmCase, String> {

    Optional<DrkFarmCase> findById(String id);

    @Query(value = " select cci.ID, cci.case_id AS CaseID, f.id AS FarmerID, av.ID AS VarietyID, av.Name AS VarietyName, cci.crop_area AS CropArea,\n" + 
    		"cci.corrected_sowing_week AS SowingWeek, cci.corrected_sowing_year AS SowingYear, cci.harvest_year AS HarvestYear,\n" + 
    		"cci.harvest_week AS HarvestWeek, ac.ID AS CommodityID, ac.Name AS CommodityName, gcs.ID as caseStatusID, \n" + 
    		"fc.crop_type as cropTypeID, act.Name as cropTypeName, gcs.Name as caseStatus,cci.sowing_date as sowingDate\n" + 
    		"from   drkrishi_ts.farm_case fc\n" + 
    		"inner join drkrishi_ts.farmer_farm ff on ff.id = fc.farm_id\n" + 
    		"inner join drkrishi_ts.farmer f  on (f.id = ff.farmer_id)\n" + 
    		"inner join drkrishi_ts.case_crop_info cci on (cci.case_id = fc.id)\n" + 
    		"inner join cdt_master_data.general_case_status gcs on (gcs.ID = cci.case_status)\n" + 
    		"inner join cdt_master_data.agri_variety av on (cci.variety_id = av.ID)\n" + 
    		"inner join cdt_master_data.agri_commodity ac on (ac.ID = av.CommodityID)\n" + 
    		"inner join cdt_master_data.agri_crop_type act on act.ID = fc.crop_type\n" + 
    		"where f.id = ?1 order by cci.CreatedAt desc ", nativeQuery = true)
    List<CaseCropInfoDTO> findByFarmerID(String farmerId);
    
    
    
    @Query(value="SELECT \n"
    		+ "    d.CaseID,\n"
    		+ "    CONCAT(GROUP_CONCAT(DISTINCT ass.Name),\n"
    		+ "            'is/are being observed in your area. Keep regular monitoring for these stresses in your farm.') AS runningText,\n"
    		+ "    ac.Name AS CommodityName,\n"
    		+ "    ac.Logo AS CommodityPhoto,\n"
    		+ "    fdt.DeviceToken,\n"
    		+ "   cd.FarmerID\n"
    		+ "FROM\n"
    		+ "    cdt_master_data.dcc_daily_case_stress d\n"
    		+ "        INNER JOIN\n"
    		+ "    cdt_master_data.dcc_daily_zonal_stress zs ON zs.id = d.dailyzonalstressID\n"
    		+ "        INNER JOIN\n"
    		+ "    cdt_master_data.zonal_variety zv ON zv.ZonalCommodityID = zs.ZonalCommodityID\n"
    		+ "        INNER JOIN\n"
    		+ "    cdt_master_data.agri_commodity ac ON zv.CommodityID = ac.ID\n"
    		+ "        INNER JOIN\n"
    		+ "    cdt_master_data.agri_stress ass ON zs.StressID = ass.ID\n"
    		+ "        inner JOIN\n"
    		+ "    gstm_transitory.case_details cd ON cd.CaseID = d.CaseID\n"
    		+ "        inner JOIN\n"
    		+ "    drkrishi_ts.farmer_device_tokens fdt ON fdt.FarmerID = cd.FarmerID\n"
    		+ "WHERE\n"
    		+ "    d.CaseID in (?1)\n"
    		+ "        AND d.IsNotified = 'No'\n"
    		+ "        AND d.CalendarWeek = WEEK(CURRENT_DATE())\n"
    		+ "        AND d.calendarYear = YEAR(CURRENT_DATE())\n"
    		+ "GROUP BY d.CaseID , ass.ID , ass.Name , ac.Name,fdt.DeviceToken,fdt.FarmerID",nativeQuery = true)
    List<FarmerNotifyDTO> getFarmerNotification(List<Map<String,Object>> caseIds);
    
}
