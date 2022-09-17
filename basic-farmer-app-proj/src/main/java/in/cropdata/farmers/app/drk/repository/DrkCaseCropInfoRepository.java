package in.cropdata.farmers.app.drk.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import in.cropdata.farmers.app.DTO.CaseCropInfoDTO;
import in.cropdata.farmers.app.drk.model.DrkCaseCropInfo;

/**
 * @author RinkeshKM
 * @Date 05/02/21
 */
@Repository
public interface DrkCaseCropInfoRepository extends JpaRepository<DrkCaseCropInfo, String> {

    Optional<DrkCaseCropInfo> findById(String id);

    @Query(value = "select ImageUrl from image_store where SourceID = ?", nativeQuery = true)
    List<String> findDocumentURLByFarmerID(Object sourceID);

    @Query(value = "select if(ff.has_own_land = 1, 1, 0 ) AS OwnershipTypeID, cci.ID,\n" + 
    		"                              cci.irrigation_source_id AS IrrigationSourceID\n" + 
    		"                       from drkrishi_ts.case_crop_info cci\n" + 
    		"					   inner join drkrishi_ts.farm_case fc on (cci.case_id = fc.ID)\n" + 
    		"                       inner join drkrishi_ts.farmer_farm ff on (fc.farm_id = ff.ID)\n" + 
    		"                       where ff.farmer_id =?2\n" + 
    		"                       and cci.ID =?1 limit 1", nativeQuery = true)
    Optional<CaseCropInfoDTO> findCaseByFarmerIDAndCaseID(String caseId, String farmerID);

    @Query(value = "select f.id from drkrishi_ts.farmer_farm ff\n" +
            "    inner join drkrishi_ts.farm_case fc on fc.farm_id = ff.id\n" +
            "    inner join drkrishi_ts.farmer f on f.id = ff.farmer_id\n" +
            "    inner join drkrishi_ts.case_crop_info cci on cci.case_id = fc.id\n" +
            "    where cci.id=?1", nativeQuery = true)
    String findFarmerIdByCaseID(String caseID);

    Optional<DrkCaseCropInfo> findAllByCaseID(String caseID);

    @Query(value = "select COUNT(cci.id) caseCount from drkrishi_ts.case_crop_info cci\n" +
            "    inner join drkrishi_ts.farm_case fc on fc.id = cci.case_id\n" +
            "    inner join drkrishi_ts.farmer_farm ff on ff.id = fc.farm_id\n" +
            "    inner join drkrishi_ts.farmer f on f.id = ff.farmer_id\n" +
            "where f.primary_mob_number = ?1 and cci.case_id = ?2", nativeQuery = true)
    Integer getCountOfCaseByCaseIDAndFarmerID(String primaryMobileNo, String caseID);


    @Transactional
    @Modifying
    @Query(value = " UPDATE  drkrishi_ts.case_crop_info as cd \n" + 
    		"INNER JOIN ( \n" + 
    		"select cci.case_id as caseID ,cci.sowing_date,DATEDIFF(curdate(), cci.sowing_date) DAS,zp.PhenophaseID as currentPheno ,zp.StartDas,zp.EndDas,temp.maxDas \n" + 
    		"from drkrishi_ts.case_crop_info cci \n" + 
    		"inner join drkrishi_ts.farm_case fc on fc.id = cci.case_id \n" + 
    		"inner join cdt_master_data.zonal_variety zv on zv.AczID = cci.acz_id and zv.VarietyID = cci.variety_id and (cci.corrected_sowing_week between zv.SowingWeekStart and zv.SowingWeekEnd)  \n" + 
    		"inner join cdt_master_data.zonal_phenophase_duration zp on zp.ZonalVarietyID = zv.ID and (DATEDIFF(curdate(), cci.sowing_date) between zp.StartDas and zp.EndDas) \n" + 
    		"inner join (select cci.case_id as caseID ,max(zp.EndDas) maxDas, group_concat(zp.EndDas) \n" + 
    		"from drkrishi_ts.case_crop_info cci \n" + 
    		"inner join drkrishi_ts.farm_case fc on fc.id = cci.case_id \n" + 
    		"inner join cdt_master_data.zonal_variety zv on zv.AczID = cci.acz_id and zv.VarietyID = cci.variety_id and (cci.corrected_sowing_week between zv.SowingWeekStart and zv.SowingWeekEnd)  \n" + 
    		"inner join cdt_master_data.zonal_phenophase_duration zp on zp.ZonalVarietyID = zv.ID  \n" + 
    		"group by caseID) temp on temp.caseID = cci.case_id  \n" + 
    		"where  zp.PhenophaseID > temp.maxDas ) tmp on tmp.caseID = cd.case_id \n" + 
    		"SET `cd`.`case_status` = 26 ", nativeQuery = true)
    Integer checkAndUpdateCaseStatus();

    @Query(value = "select cci.id AS ID, cci.case_id AS CaseID, cci.seed_source_id AS SeedSourceID, cci.variety_id AS VarietyID, cci.crop_area AS CropArea,\n" +
            "       cci.spacing_row AS SpacingRow, cci.spacing_plant AS SpacingPlant, cci.corrected_sowing_week AS SowingWeek,\n" +
            "       cci.corrected_sowing_year AS SowingYear, cci.harvest_week AS HarvestWeek, cci.harvest_year AS HarvestYear,\n" +
            "       cci.farmer_given_sowing_week AS farmerGivenSowingWeek,\n" +
            "       cci.seeds_sample_received_info AS seedsSampleReceivedInfo, cci.RegionID, cci.SourceID,\n" +
            "       cci.irrigation_source_id AS IrrigationSourceId, cci.latitude, cci.longitude, cci.advisory_type AS advisoryType\n" +
            "from drkrishi_ts.case_crop_info cci\n" +
            "            inner join drkrishi_ts.farm_case fc on fc.id = cci.case_id\n" +
            "            inner join drkrishi_ts.farmer_farm ff on ff.id = fc.farm_id\n" +
            "            inner join drkrishi_ts.farmer f on f.id = ff.farmer_id\n" +
            " where f.id=?2 and cci.case_id=?1", nativeQuery = true)
    Optional<CaseCropInfoDTO> findAllByCaseIDAndFarmerID(String caseID, String farmerID);
    
    @Query(value=" select cci.id as ID, cci.case_id as caseID, ff.farmer_id as farmerID, cci.variety_id as varietyID, av.name as varietyName, cci.crop_area as cropArea,  \n" + 
    		"cci.corrected_sowing_week as sowingWeek, cci.corrected_sowing_year as sowingYear, av.CommodityID as commodityID, ac.name as commodityName, cci.case_status as caseStatusID,  \n" + 
    		"fc.crop_type as cropTypeID, act.name as cropTypeName, gcs.name as caseStatus,  \n" + 
    		"COALESCE(DATE_FORMAT(cci.sowing_date, '%Y-%m-%d'),STR_TO_DATE(concat(Year(curdate()), cci.corrected_sowing_week, ' Wednesday'), '%X %V %W')) as sowingDate  \n" + 
    		"from drkrishi_ts.case_crop_info cci   \n" + 
    		"inner join drkrishi_ts.farm_case fc on fc.id = cci.case_id  \n" + 
    		"inner join drkrishi_ts.farmer_farm ff on ff .id = fc.farm_id  \n" + 
    		"left join cdt_master_data.general_case_status gcs on gcs.ID = cci.case_status  \n" + 
    		"inner join cdt_master_data.agri_variety av on av.ID = cci.variety_id  \n" + 
    		"inner join cdt_master_data.agri_commodity ac on ac.ID = av.CommodityID  \n" + 
    		"inner join cdt_master_data.agri_crop_type act on act.ID = fc.crop_type  \n" + 
    		"where ff.farmer_id = ?1 order by fc.CreatedAt desc ",nativeQuery = true)
    List<Map<String,Object>> getCaseListByFarmer(String farmerId);
    
    @Query(value="Select STR_TO_DATE(concat(cci.corrected_sowing_year,cci.corrected_sowing_week, ' Wednesday'), '%X%V %W')  as CorrectedSowingDate\n" + 
    		"from drkrishi_ts.case_crop_info cci where cci.case_id = ?1",nativeQuery = true)
    Map<String,Date> getCorrectedSowingDate(String caseId);
}


