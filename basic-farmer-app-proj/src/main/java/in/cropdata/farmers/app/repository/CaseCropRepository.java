package in.cropdata.farmers.app.repository;

import in.cropdata.farmers.app.DTO.CaseCropInfoDTO;
import in.cropdata.farmers.app.model.CaseCropInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * @author RinkeshKM
 * @Date 05/02/21
 */
public interface CaseCropRepository extends JpaRepository<CaseCropInfo, String> {

    @Query(value = " select cci.ID, CaseID, FarmerID, VarietyID, av.Name AS VarietyName, CropArea, SowingWeek, SowingYear, HarvestYear, HarvestWeek,\n" +
    		"cci.CommodityID, ac.Name AS CommodityName, fc.CropType as cropTypeID, act.Name as cropTypeName, cci.SeasonID AS seasonID from farmers_app.case_crop_info cci\n" +
    		"inner join farmers_app.farm_case fc on fc.id = cci.CaseID\n" + 
    		"inner join cdt_master_data.agri_variety av on (cci.VarietyID = av.ID)\n" + 
    		"inner join cdt_master_data.agri_commodity ac on (ac.ID = cci.CommodityID)\n" + 
    		"inner join cdt_master_data.agri_crop_type act on act.ID = fc.CropType\n" + 
    		"where cci.FarmerID = ?1 ", nativeQuery = true)
    List<CaseCropInfoDTO> findByFarmerId(String farmerId);

    @Query(value = "select cci.ID, cci.FarmerID, ff.ID AS FarmID, cci.CropArea, cci.SowingWeek, cci.SowingYear, cci.Quantity, cci.CommodityID, ac.Name AS CommodityName, cci.VarietyID, av.Name AS VarietyName, cci.IrrigationSourceID,\n" +
            "       aim.Name AS IrrigationSourceName, fc.CropType AS CropTypeID, act.Name AS CropTypeName, ff.OwnershipType AS OwnershipTypeID,\n" +
            "       ffot.Name AS OwnershipTypeName\n" +
            "from farmers_app.case_crop_info cci\n" +
            "    inner join cdt_master_data.agri_commodity ac on (ac.ID = cci.CommodityID)\n" +
            "    inner join cdt_master_data.agri_variety av on (av.ID = cci.VarietyID and av.CommodityID = cci.CommodityID)\n" +
            "    inner join cdt_master_data.agri_irrigation_method aim on (aim.ID = cci.IrrigationSourceID)\n" +
            "    inner join farmers_app.farm_case fc on (fc.ID = cci.CaseID)\n" +
            "    inner join cdt_master_data.agri_crop_type act on (act.ID = fc.CropType)\n" +
            "    inner join farmers_app.farmer_farm ff on (ff.FarmerID = cci.FarmerID)\n" +
            "    inner join cdt_master_data.farmer_farm_ownership_type ffot on (ffot.ID = ff.OwnershipType)\n" +
            "where cci.ID = ?1", nativeQuery = true)
    Optional<CaseCropInfoDTO> findAllByCaseId(Integer caseId);

    @Query(value = "select if(ff.has_own_land = 1, 1, 0 ) AS OwnershipTypeID, cci.ID,\n" +
            "                  cci.irrigation_source_id AS IrrigationSourceID\n" +
            "           from drkrishi_ts.case_crop_info cci\n" +
            "                    inner join drkrishi_ts.farmer_farm ff\n" +
            "           where ff.farmer_id =?2\n" +
            "           and cci.ID =?1 limit 1", nativeQuery = true)
    Optional<CaseCropInfoDTO> findCaseByFarmerIDAndCaseID(String caseId, String farmerID);



    @Query(value = "select f.id from drkrishi_ts.farmer_farm ff\n" +
            "    inner join drkrishi_ts.farm_case fc on fc.farm_id = ff.id\n" +
            "    inner join drkrishi_ts.farmer f on f.id = ff.farmer_id\n" +
            "    inner join drkrishi_ts.case_crop_info cci on cci.case_id = fc.id\n" +
            "    where cci.id=?1", nativeQuery = true)
    String findFarmerIdByCaseID(String caseID);

}
