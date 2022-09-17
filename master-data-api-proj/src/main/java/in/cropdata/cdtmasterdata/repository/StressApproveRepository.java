package in.cropdata.cdtmasterdata.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.cdtmasterdata.dto.interfaces.StressApproveDto;
import in.cropdata.cdtmasterdata.model.ValidateStressGroundTruth;

public interface StressApproveRepository extends JpaRepository<ValidateStressGroundTruth,Integer> {

    @Query(value = "select tsd.id, v.id AS varietyID, v.name AS varietyName, c.id AS commodityID, c.name AS commodityName,\n" +
            "        apd.PhenophaseID, ap.Name AS PhenophaseName, cd.CaseID AS CaseID, cd.DistrictCode AS DistrictID, gd.name AS DistrictName,\n" +
            "       tsd.front_photo AS FrontPhoto, tsd.left_photo AS LeftPhoto, gd.StateCode, gd.StateName,\n" +
            "       tsd.right_photo AS RightPhoto from drkrishi.task_stress_details tsd\n" +
            "    inner join gstm_transitory.case_details cd on cd.CaseID = tsd.case_id\n" +
            "    inner join drkrishi.variety v on v.id = cd.VarietyID\n" +
            "    inner join drkrishi.commodity c on v.commodity_id = c.id\n" +
            "    inner join cdt_master_data.agri_phenophase_duration apd on (apd.CommodityID = c.id\n" +
            "            and apd.VarietyID = v.id\n" +
            "            and apd.SeasonID = cd.SeasonID\n" +
            "            and apd.StateCode = cd.StateCode)\n" +
            "    inner join cdt_master_data.agri_phenophase ap on ap.ID = apd.PhenophaseID\n" +
            "    inner join cdt_master_data.geo_district gd on gd.id = cd.DistrictCode\n" +
            "where (datediff(curdate(), STR_TO_DATE(concat(cd.CurrentSowingYear,cd.SowingWeek, ' Wednesday'), '%X%V %W'))\n" +
            "    between apd.PhenophaseStart and apd.PhenophaseEnd) and tsd.is_verified = 'Pending' and (ap.Name like :searchText or gd.name like :searchText\n" +
            "    or c.name like :searchText or v.name like :searchText)",
            countQuery = "select tsd.id, v.id AS varietyID, v.name AS varietyName, c.id AS commodityID, c.name AS commodityName,\n" +
                    "        apd.PhenophaseID, ap.Name AS PhenophaseName, cd.CaseID AS CaseID, cd.DistrictCode AS DistrictID, gd.name AS DistrictName,\n" +
                    "       tsd.front_photo AS FrontPhoto, tsd.left_photo AS LeftPhoto, gd.StateCode, gd.StateName,\n" +
                    "       tsd.right_photo AS RightPhoto from drkrishi.task_stress_details tsd\n" +
                    "    inner join gstm_transitory.case_details cd on cd.CaseID = tsd.case_id\n" +
                    "    inner join drkrishi.variety v on v.id = cd.VarietyID\n" +
                    "    inner join drkrishi.commodity c on v.commodity_id = c.id\n" +
                    "    inner join cdt_master_data.agri_phenophase_duration apd on (apd.CommodityID = c.id\n" +
                    "            and apd.VarietyID = v.id\n" +
                    "            and apd.SeasonID = cd.SeasonID\n" +
                    "            and apd.StateCode = cd.StateCode)\n" +
                    "    inner join cdt_master_data.agri_phenophase ap on ap.ID = apd.PhenophaseID\n" +
                    "    inner join cdt_master_data.geo_district gd on gd.id = cd.DistrictCode\n" +
                    "where (datediff(curdate(), STR_TO_DATE(concat(cd.CurrentSowingYear,cd.SowingWeek, ' Wednesday'), '%X%V %W'))\n" +
                    "    between apd.PhenophaseStart and apd.PhenophaseEnd) and tsd.is_verified = 'Pending' and (ap.Name like :searchText or gd.name like :searchText\n" +
                    "    or c.name like :searchText or v.name like :searchText)",nativeQuery = true)
    Page<StressApproveDto> getValidateStressListByPagenation(Pageable sortedByIdDesc, String searchText);

    @Query(value = "select tsd.id, tsd.symptom_id AS SymptomID, cd.DistrictCode AS DistrictID, d.name AS DistrictName, fs.Symptom AS SymptomDescription,\n" +
            "       c.id AS CommodityID, c.name AS CommodityName, ass.ID AS StageID, ass.Name AS StageName,\n" +
            "       v.id AS VarietyID, v.name AS VarietyName, ap.id AS PhenophaseID, ap.Name AS PhenophaseName, agst.ID AS StressID,\n" +
            "       agst.Name AS StressName, app.ID AS PlantPartID, app.Name AS PlantPartName, tsd.front_photo AS FarmerGivenImage,\n" +
            "       ast.ID AS StressTypeID, ast.Name AS StressTypeName\n" +
            "       ,(select group_concat(GenericImage) from cdt_master_data.flipbook_symptoms\n" +
            "           where StressID = (select  distinct StressID from cdt_master_data.flipbook_symptoms fs1 where fs1.ID = tsd.symptom_id))\n" +
            "           as ReferenceImages\n" +
            "from drkrishi.task_stress_details tsd\n" +
            "         left join gstm_transitory.case_details cd on cd.CaseID = tsd.case_id\n" +
            "         left join cdt_master_data.flipbook_symptoms fs on fs.ID = tsd.symptom_id\n" +
            "         left join drkrishi.variety v on v.id = cd.VarietyID\n" +
            "         left join drkrishi.commodity c on v.commodity_id = c.id\n" +
            "         left join cdt_master_data.agri_phenophase_duration apd on (apd.CommodityID = c.id\n" +
            "            and apd.VarietyID = v.id\n" +
            "            and apd.SeasonID = cd.SeasonID\n" +
            "            and apd.StateCode = cd.StateCode)\n" +
            "         left join cdt_master_data.agri_phenophase ap on ap.ID = apd.PhenophaseID\n" +
            "         left join drkrishi.district d on d.id = cd.DistrictCode\n" +
            "         left join cdt_master_data.agri_plant_part app on app.ID = fs.PlantPartID\n" +
            "         left join cdt_master_data.agri_stage ass on ass.ID = fs.StressStageID\n" +
            "         left join cdt_master_data.agri_stress agst on agst.ID = fs.StressID\n" +
            "         left join cdt_master_data.agri_stress_type ast on ast.ID = agst.StressTypeID\n" +
            "where tsd.id=?1 and (datediff(curdate(), STR_TO_DATE(concat(cd.CurrentSowingYear,cd.SowingWeek, ' Wednesday'), '%X%V %W'))\n" +
            "    between apd.PhenophaseStart and apd.PhenophaseEnd)",nativeQuery = true)
    StressApproveDto getDetailsByTaskStressDetailID(String taskStressDetailID);

    @Query(value = "select gtz20.StressID, gtz20.SymptomID/*,gtz20.StressImage*/ from gstm_transitory.ground_truth_zl20 gtz20\n" +
            "    where gtz20.CaseID = ?1 and gtz20.StressID = ?2 and SymptomID = ?3",nativeQuery = true)
    StressApproveDto getStressDetailsById(String id, Integer stressId, Integer symptomId);

    @Query(value = "select distinct fs.ID AS SymptomID, fs.Symptom AS SymptomDescription, fs.GenericImage AS SymptomImageUrl,\n" +
            "       agst.ID As StressID, agst.Name AS StressName\n" +
            "from cdt_master_data.agri_district_commodity_stress adcs\n" +
            "    inner join cdt_master_data.flipbook_symptoms fs on fs.CommodityID = adcs.CommodityID and fs.StressID = adcs.StressID\n" +
            "    inner join cdt_master_data.agri_stress agst on agst.ID = adcs.StressID\n" +
            "    where adcs.DistrictCode=?4 and adcs.CommodityID=?1\n" +
            "      and adcs.VarietyID=?2\n" +
            "      and fs.PhenophaseID=?3\n" +
            "and find_in_set(?3, adcs.Phenophases)", nativeQuery = true)
    List<StressApproveDto> getSymptomBySpec(Integer commodityID, Integer varietyID, Integer phenophaseID, Integer districtID);

    @Query(value = "select distinct fs.ID AS SymptomID, fs.Symptom AS SymptomDescription,\n" +
            "       ass.ID AS StageID, ass.Name AS StageName,\n" +
            "       agst.ID AS StressID, agst.Name AS StressName, app.ID AS PlantPartID, app.Name AS PlantPartName,\n" +
            "       ast.ID AS StressTypeID, ast.Name AS StressTypeName\n" +
            "       ,(select group_concat(GenericImage) from cdt_master_data.flipbook_symptoms\n" +
            "          where StressID = (\n" +
            "              select distinct StressID from cdt_master_data.flipbook_symptoms where ID=?1)\n" +
            "           ) as ReferenceImages, fs.ID as symptomID\n" +
            "from cdt_master_data.flipbook_symptoms fs\n" +
            "         left join cdt_master_data.agri_commodity c on fs.CommodityID = c.id\n" +
            "         left join cdt_master_data.agri_variety v on v.CommodityID = c.ID\n" +
            "         left join cdt_master_data.agri_plant_part app on app.ID = fs.PlantPartID\n" +
            "         left join cdt_master_data.agri_stage ass on ass.ID = fs.StressStageID\n" +
            "         left join cdt_master_data.agri_stress agst on agst.ID = fs.StressID\n" +
            "         left join cdt_master_data.agri_stress_type ast on ast.ID = agst.StressTypeID\n" +
            "where fs.ID=?1", nativeQuery = true)
        StressApproveDto getSymptomDetailsBySymptom(Integer symptomID); 
    
   
}
