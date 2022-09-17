package in.cropdata.cdtmasterdata.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.cdtmasterdata.dto.interfaces.AgriVarietyQualityInfDto;
import in.cropdata.cdtmasterdata.model.AgriVarietyQuality;

/**
 * <p>
 * This repository is used for fetching <b>agri_variety_quality</b> data to be
 * displayed in Master UI.
 * </p>
 * Updated queries to fetch data according to the updated table structure.
 * 
 * @author PranaySK
 * @Date 25-08-2020
 */
public interface AgriVarietyQualityRepository extends JpaRepository<AgriVarietyQuality, Integer> {

	@Query(value = "select avq.ID, avq.StateCode, avq.RegionID, avq.CommodityID, VarietyID, CurrentQualityBandID, ab.Name CurrentQualityBandName,\n" +
			"       EstimatedQualityBandID, ab2.Name EstimatedQualityBandName, AllowableVarianceInQualityBandID, ab3.Name AllowableVarianceInQualityName,\n" +
			"       AV.Name as Variety, gs.Name as State, gr.Name as Region\n" +
			"from cdt_master_data.agri_variety_quality avq\n" +
			"    inner join cdt_master_data.agri_band ab on ab.ID = avq.CurrentQualityBandID\n" +
			"    inner join cdt_master_data.agri_band ab2 on ab2.ID = avq.EstimatedQualityBandID\n" +
			"    inner join cdt_master_data.agri_band ab3 on ab3.ID = avq.AllowableVarianceInQualityBandID\n" +
			"     Left Join agri_commodity AC on avq.CommodityID = AC.ID\n" +
			"     Left Join agri_variety AV on avq.VarietyID = AV.ID\n" +
			"     Left Join geo_state gs on gs.StateCode = avq.StateCode\n" +
			"     Left Join geo_region gr on gr.RegionID = avq.RegionID;\n", nativeQuery = true)
	List<AgriVarietyQualityInfDto> getAgriVarietyQualityList();

	@Query(value = "select avq.ID, avq.StateCode, avq.RegionID, avq.CommodityID, VarietyID, CurrentQualityBandID, ab.Name CurrentQuality,\n" +
			"       EstimatedQualityBandID, ab2.Name EstimatedQuality, AllowableVarianceInQualityBandID, ab3.Name AllowableVarianceInQuality,\n" +
			"       AV.Name as Variety, gs.Name as State, gr.Name as Region, AC.Name as Commodity, avq.Status, avq.IsValid, avq.ErrorMessage\n" +
			"from cdt_master_data.agri_variety_quality avq\n" +
			"    inner join cdt_master_data.agri_band ab on ab.ID = avq.CurrentQualityBandID\n" +
			"    inner join cdt_master_data.agri_band ab2 on ab2.ID = avq.EstimatedQualityBandID\n" +
			"    inner join cdt_master_data.agri_band ab3 on ab3.ID = avq.AllowableVarianceInQualityBandID\n" +
			"    Left Join agri_commodity AC on avq.CommodityID = AC.ID\n" +
			"    Left Join agri_variety AV on avq.VarietyID = AV.ID\n" +
			"    Left Join geo_state gs on gs.StateCode = avq.StateCode\n" +
			"    Left Join geo_region gr on gr.RegionID = avq.RegionID\n" +
			"    where AV.Name like :searchText OR AC.Name like :searchText OR ab.Name like :searchText\n" +
			"        or gs.Name like :searchText",
			countQuery = "select avq.ID, avq.StateCode, avq.RegionID, avq.CommodityID, VarietyID, CurrentQualityBandID, ab.Name CurrentQuality,\n" +
					"       EstimatedQualityBandID, ab2.Name EstimatedQuality, AllowableVarianceInQualityBandID, ab3.Name AllowableVarianceInQuality,\n" +
					"       AV.Name as Variety, gs.Name as State, gr.Name as Region, AC.Name as Commodity, avq.Status, avq.IsValid, avq.ErrorMessage\n" +
					"from cdt_master_data.agri_variety_quality avq\n" +
					"    inner join cdt_master_data.agri_band ab on ab.ID = avq.CurrentQualityBandID\n" +
					"    inner join cdt_master_data.agri_band ab2 on ab2.ID = avq.EstimatedQualityBandID\n" +
					"    inner join cdt_master_data.agri_band ab3 on ab3.ID = avq.AllowableVarianceInQualityBandID\n" +
					"    Left Join agri_commodity AC on avq.CommodityID = AC.ID\n" +
					"    Left Join agri_variety AV on avq.VarietyID = AV.ID\n" +
					"    Left Join geo_state gs on gs.StateCode = avq.StateCode\n" +
					"    Left Join geo_region gr on gr.RegionID = avq.RegionID\n" +
					"    where AV.Name like :searchText OR AC.Name like :searchText OR ab.Name like :searchText\n" +
					"        or gs.Name like :searchText", nativeQuery = true)
	Page<AgriVarietyQualityInfDto> getAgriVarietyQualityPagination(Pageable pageable, String searchText);
	
	@Query(value = "select avq.ID, avq.StateCode, avq.RegionID, avq.CommodityID, VarietyID, CurrentQualityBandID, ab.Name CurrentQuality,\n" +
			"       EstimatedQualityBandID, ab2.Name EstimatedQuality, AllowableVarianceInQualityBandID, ab3.Name AllowableVarianceInQuality,\n" +
			"       AV.Name as Variety, gs.Name as State, gr.Name as Region, AC.Name as Commodity, avq.Status, avq.IsValid \n" +
			"from cdt_master_data.agri_variety_quality_missing avq\n" +
			"    inner join cdt_master_data.agri_band ab on ab.ID = avq.CurrentQualityBandID\n" +
			"    inner join cdt_master_data.agri_band ab2 on ab2.ID = avq.EstimatedQualityBandID\n" +
			"    inner join cdt_master_data.agri_band ab3 on ab3.ID = avq.AllowableVarianceInQualityBandID\n" +
			"    Left Join agri_commodity AC on avq.CommodityID = AC.ID\n" +
			"    Left Join agri_variety AV on avq.VarietyID = AV.ID\n" +
			"    Left Join geo_state gs on gs.StateCode = avq.StateCode\n" +
			"    Left Join geo_region gr on gr.RegionID = avq.RegionID\n" +
			"    where AV.Name like :searchText OR AC.Name like :searchText OR ab.Name like :searchText\n" +
			"        or gs.Name like :searchText",
			countQuery = "select avq.ID, avq.StateCode, avq.RegionID, avq.CommodityID, VarietyID, CurrentQualityBandID, ab.Name CurrentQuality,\n" +
					"       EstimatedQualityBandID, ab2.Name EstimatedQuality, AllowableVarianceInQualityBandID, ab3.Name AllowableVarianceInQuality,\n" +
					"       AV.Name as Variety, gs.Name as State, gr.Name as Region, AC.Name as Commodity, avq.Status, avq.IsValid \n" +
					"from cdt_master_data.agri_variety_quality_missing avq\n" +
					"    inner join cdt_master_data.agri_band ab on ab.ID = avq.CurrentQualityBandID\n" +
					"    inner join cdt_master_data.agri_band ab2 on ab2.ID = avq.EstimatedQualityBandID\n" +
					"    inner join cdt_master_data.agri_band ab3 on ab3.ID = avq.AllowableVarianceInQualityBandID\n" +
					"    Left Join agri_commodity AC on avq.CommodityID = AC.ID\n" +
					"    Left Join agri_variety AV on avq.VarietyID = AV.ID\n" +
					"    Left Join geo_state gs on gs.StateCode = avq.StateCode\n" +
					"    Left Join geo_region gr on gr.RegionID = avq.RegionID\n" +
					"    where AV.Name like :searchText OR AC.Name like :searchText OR ab.Name like :searchText\n" +
					"        or gs.Name like :searchText", nativeQuery = true)
	Page<AgriVarietyQualityInfDto> getAgriVarietyQualityMissingPagination(Pageable pageable, String searchText);

	@Query(value = "select avq.ID, avq.StateCode, avq.RegionID, avq.CommodityID, VarietyID, CurrentQualityBandID, ab.Name CurrentQuality,\n" +
			"       EstimatedQualityBandID, ab2.Name EstimatedQuality, AllowableVarianceInQualityBandID, ab3.Name AllowableVarianceInQuality,\n" +
			"       AV.Name as Variety, gs.Name as State, gr.Name as Region, AC.Name as Commodity, avq.Status, avq.IsValid, avq.ErrorMessage\n" +
			"from cdt_master_data.agri_variety_quality avq\n" +
			"    inner join cdt_master_data.agri_band ab on ab.ID = avq.CurrentQualityBandID\n" +
			"    inner join cdt_master_data.agri_band ab2 on ab2.ID = avq.EstimatedQualityBandID\n" +
			"    inner join cdt_master_data.agri_band ab3 on ab3.ID = avq.AllowableVarianceInQualityBandID\n" +
			"    Left Join agri_commodity AC on avq.CommodityID = AC.ID\n" +
			"    Left Join agri_variety AV on avq.VarietyID = AV.ID\n" +
			"    Left Join geo_state gs on gs.StateCode = avq.StateCode\n" +
			"    Left Join geo_region gr on gr.RegionID = avq.RegionID\n" +
			"    where  avq.IsValid = 0 and (AC.Name like :searchText or AV.Name like :searchText OR ab.Name like :searchText or gs.Name like :searchText)",
			countQuery = "select avq.ID, avq.StateCode, avq.RegionID, avq.CommodityID, VarietyID, CurrentQualityBandID, ab.Name CurrentQuality,\n" +
					"        EstimatedQualityBandID, ab2.Name EstimatedQuality, AllowableVarianceInQualityBandID, ab3.Name AllowableVarianceInQuality,\n" +
					"        AV.Name as Variety, gs.Name as State, gr.Name as Region, AC.Name as Commodity, avq.Status, avq.IsValid, avq.ErrorMessage\n" +
					" from cdt_master_data.agri_variety_quality avq\n" +
					"     inner join cdt_master_data.agri_band ab on ab.ID = avq.CurrentQualityBandID\n" +
					"     inner join cdt_master_data.agri_band ab2 on ab2.ID = avq.EstimatedQualityBandID\n" +
					"     inner join cdt_master_data.agri_band ab3 on ab3.ID = avq.AllowableVarianceInQualityBandID\n" +
					"     Left Join agri_commodity AC on avq.CommodityID = AC.ID\n" +
					"     Left Join agri_variety AV on avq.VarietyID = AV.ID\n" +
					"     Left Join geo_state gs on gs.StateCode = avq.StateCode\n" +
					"     Left Join geo_region gr on gr.RegionID = avq.RegionID\n" +
					"     where  avq.IsValid = 0 and (AC.Name like :searchText or AV.Name like :searchText OR ab.Name like :searchText or gs.Name like :searchText)", nativeQuery = true)
	Page<AgriVarietyQualityInfDto> getAgriVarietyQualityPaginationWithInvalidated(Pageable pageable, String searchText);
	
	@Query(value = "select avq.ID, avq.StateCode, avq.RegionID, avq.CommodityID, VarietyID, CurrentQualityBandID, ab.Name CurrentQuality,\n" +
			"       EstimatedQualityBandID, ab2.Name EstimatedQuality, AllowableVarianceInQualityBandID, ab3.Name AllowableVarianceInQuality,\n" +
			"       AV.Name as Variety, gs.Name as State, gr.Name as Region, AC.Name as Commodity, avq.Status, avq.IsValid \n" +
			"from cdt_master_data.agri_variety_quality_missing avq\n" +
			"    inner join cdt_master_data.agri_band ab on ab.ID = avq.CurrentQualityBandID\n" +
			"    inner join cdt_master_data.agri_band ab2 on ab2.ID = avq.EstimatedQualityBandID\n" +
			"    inner join cdt_master_data.agri_band ab3 on ab3.ID = avq.AllowableVarianceInQualityBandID\n" +
			"    Left Join agri_commodity AC on avq.CommodityID = AC.ID\n" +
			"    Left Join agri_variety AV on avq.VarietyID = AV.ID\n" +
			"    Left Join geo_state gs on gs.StateCode = avq.StateCode\n" +
			"    Left Join geo_region gr on gr.RegionID = avq.RegionID\n" +
			"    where  avq.IsValid = 0 and (AC.Name like :searchText or AV.Name like :searchText OR ab.Name like :searchText or gs.Name like :searchText)",
			countQuery = "select avq.ID, avq.StateCode, avq.RegionID, avq.CommodityID, VarietyID, CurrentQualityBandID, ab.Name CurrentQuality,\n" +
					"        EstimatedQualityBandID, ab2.Name EstimatedQuality, AllowableVarianceInQualityBandID, ab3.Name AllowableVarianceInQuality,\n" +
					"        AV.Name as Variety, gs.Name as State, gr.Name as Region, AC.Name as Commodity, avq.Status, avq.IsValid \n" +
					" from cdt_master_data.agri_variety_quality_missing avq\n" +
					"     inner join cdt_master_data.agri_band ab on ab.ID = avq.CurrentQualityBandID\n" +
					"     inner join cdt_master_data.agri_band ab2 on ab2.ID = avq.EstimatedQualityBandID\n" +
					"     inner join cdt_master_data.agri_band ab3 on ab3.ID = avq.AllowableVarianceInQualityBandID\n" +
					"     Left Join agri_commodity AC on avq.CommodityID = AC.ID\n" +
					"     Left Join agri_variety AV on avq.VarietyID = AV.ID\n" +
					"     Left Join geo_state gs on gs.StateCode = avq.StateCode\n" +
					"     Left Join geo_region gr on gr.RegionID = avq.RegionID\n" +
					"     where  avq.IsValid = 0 and (AC.Name like :searchText or AV.Name like :searchText OR ab.Name like :searchText or gs.Name like :searchText)", nativeQuery = true)
	Page<AgriVarietyQualityInfDto> getAgriVarietyQualityMissingPaginationWithInvalidated(Pageable pageable, String searchText);

}
