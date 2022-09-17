package in.cropdata.cdtmasterdata.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.cdtmasterdata.dto.interfaces.RegionalCommodityDtoInf;
import in.cropdata.cdtmasterdata.model.RegionalCommodity;
import in.cropdata.cdtmasterdata.model.vo.AgriCommodityVo;

public interface RegionalCommodityRepository extends JpaRepository<RegionalCommodity, Integer> {

	@Query(value = "select RC.CommodityID , AC.Name  as Commodity,AC.Status from regional_commodity RC \n"
			+ "Inner Join agri_commodity AC on RC.CommodityID = AC.ID \n"
			+ "where  RC.SeasonID=5 group by RC.CommodityID , AC.Name", nativeQuery = true)
	List<AgriCommodityVo> findByCommodityId(int commodityId);

	@Query(value = "SELECT RC.ID,RC.StateCode,RC.RegionID,RC.SeasonID,RC.CommodityID,RC.TargetValue,RC.MinLotSize,\n" +
			"RC.MaxRigtsInLot,RC.HarvestRelaxation,RC.Status,GS.Name as State,GR.Name as Region, \n" + 
			"			ASS.Name as Season,AC.Name as Commodity, RC.IsValid, RC.ErrorMessage FROM regional_commodity RC \n" +
			"			LEFT JOIN geo_state GS ON (RC.StateCode = GS.StateCode)\n" + 
			"			LEFT JOIN agri_season ASS ON (RC.SeasonID = ASS.ID) \n" + 
			"			LEFT JOIN geo_region GR ON(RC.RegionID = GR.RegionID)\n" + 
			"			LEFT JOIN agri_commodity AC ON (RC.CommodityID = AC.ID)\n" + 
			"			where AC.Name like :searchText\n" + 
			"			OR GS.Name like :searchText\n" + 
			"			OR GR.Name like :searchText\n" + 
			"			OR ASS.Name like :searchText\n" + 
			"			OR RC.TargetValue like :searchText\n" +
			"			OR RC.MinLotSize like :searchText\n" + 
			"			OR RC.MaxRigtsInLot like :searchText\n" + 
			"			OR RC.HarvestRelaxation like :searchText\n" + 
			"			OR RC.Status like :searchText", 
			countQuery = "SELECT RC.ID,RC.StateCode,RC.RegionID,RC.SeasonID,RC.CommodityID,RC.TargetValue,RC.MinLotSize,\n" +
					"RC.MaxRigtsInLot,RC.HarvestRelaxation,RC.Status,GS.Name as State,GR.Name as Region, \n" + 
					"			ASS.Name as Season,AC.Name as Commodity, RC.IsValid, RC.ErrorMessage FROM regional_commodity RC \n" +
					"			LEFT JOIN geo_state GS ON (RC.StateCode = GS.StateCode)\n" + 
					"			LEFT JOIN agri_season ASS ON (RC.SeasonID = ASS.ID) \n" + 
					"			LEFT JOIN geo_region GR ON(RC.RegionID = GR.RegionID)\n" + 
					"			LEFT JOIN agri_commodity AC ON (RC.CommodityID = AC.ID)\n" + 
					"			where AC.Name like :searchText\n" + 
					"			OR GS.Name like :searchText\n" + 
					"			OR GR.Name like :searchText\n" + 
					"			OR ASS.Name like :searchText\n" + 
					"			OR RC.TargetValue like :searchText\n" +
					"			OR RC.MinLotSize like :searchText\n" + 
					"			OR RC.MaxRigtsInLot like :searchText\n" + 
					"			OR RC.HarvestRelaxation like :searchText\n" + 
					"			OR RC.Status like :searchText", nativeQuery = true)
	Page<RegionalCommodityDtoInf> getRegionalCommodity(Pageable pageable, String searchText);
	
	@Query(value = "SELECT RC.ID,RC.StateCode,RC.RegionID,RC.SeasonID,RC.CommodityID,RC.TargetValue,RC.MinLotSize,\n" +
			"RC.MaxRigtsInLot,RC.HarvestRelaxation,RC.Status,GS.Name as State,GR.Name as Region, \n" + 
			"			ASS.Name as Season,AC.Name as Commodity FROM regional_commodity_missing RC \n" + 
			"			LEFT JOIN geo_state GS ON (RC.StateCode = GS.StateCode)\n" + 
			"			LEFT JOIN agri_season ASS ON (RC.SeasonID = ASS.ID) \n" + 
			"			LEFT JOIN geo_region GR ON(RC.RegionID = GR.RegionID)\n" + 
			"			LEFT JOIN agri_commodity AC ON (RC.CommodityID = AC.ID)\n" + 
			"			where AC.Name like :searchText\n" + 
			"			OR GS.Name like :searchText\n" + 
			"			OR GR.Name like :searchText\n" + 
			"			OR ASS.Name like :searchText\n" + 
			"			OR RC.TargetValue like :searchText\n" +
			"			OR RC.MinLotSize like :searchText\n" + 
			"			OR RC.MaxRigtsInLot like :searchText\n" + 
			"			OR RC.HarvestRelaxation like :searchText\n" + 
			"			OR RC.Status like :searchText", 
			countQuery = "SELECT RC.ID,RC.StateCode,RC.RegionID,RC.SeasonID,RC.CommodityID,RC.TargetValue,RC.MinLotSize,\n" +
					"RC.MaxRigtsInLot,RC.HarvestRelaxation,RC.Status,GS.Name as State,GR.Name as Region, \n" + 
					"			ASS.Name as Season,AC.Name as Commodity FROM regional_commodity_missing RC \n" + 
					"			LEFT JOIN geo_state GS ON (RC.StateCode = GS.StateCode)\n" + 
					"			LEFT JOIN agri_season ASS ON (RC.SeasonID = ASS.ID) \n" + 
					"			LEFT JOIN geo_region GR ON(RC.RegionID = GR.RegionID)\n" + 
					"			LEFT JOIN agri_commodity AC ON (RC.CommodityID = AC.ID)\n" + 
					"			where AC.Name like :searchText\n" + 
					"			OR GS.Name like :searchText\n" + 
					"			OR GR.Name like :searchText\n" + 
					"			OR ASS.Name like :searchText\n" + 
					"			OR RC.TargetValue like :searchText\n" +
					"			OR RC.MinLotSize like :searchText\n" + 
					"			OR RC.MaxRigtsInLot like :searchText\n" + 
					"			OR RC.HarvestRelaxation like :searchText\n" + 
					"			OR RC.Status like :searchText", nativeQuery = true)
	Page<RegionalCommodityDtoInf> getMissingRegionalCommodity(Pageable pageable, String searchText);

	@Query(value = "SELECT RC.ID,RC.StateCode,RC.RegionID,RC.SeasonID,RC.CommodityID,RC.TargetValue,RC.MinLotSize,\n" +
			"RC.MaxRigtsInLot,RC.HarvestRelaxation,RC.Status,GS.Name as State,GR.Name as Region, \n" +
			"			ASS.Name as Season,AC.Name as Commodity, RC.IsValid, RC.ErrorMessage FROM regional_commodity RC \n" +
			"			LEFT JOIN geo_state GS ON (RC.StateCode = GS.StateCode)\n" +
			"			LEFT JOIN agri_season ASS ON (RC.SeasonID = ASS.ID) \n" +
			"			LEFT JOIN geo_region GR ON(RC.RegionID = GR.RegionID)\n" +
			"			LEFT JOIN agri_commodity AC ON (RC.CommodityID = AC.ID)\n" +
			"			where RC.IsValid = 0 and (AC.Name like :searchText\n" +
			"			OR GS.Name like :searchText\n" +
			"			OR GR.Name like :searchText\n" +
			"			OR ASS.Name like :searchText\n" +
			"			OR RC.TargetValue like :searchText\n" +
			"			OR RC.MinLotSize like :searchText\n" +
			"			OR RC.MaxRigtsInLot like :searchText\n" +
			"			OR RC.HarvestRelaxation like :searchText\n" +
			"			OR RC.Status like :searchText)",
			countQuery = "SELECT RC.ID,RC.StateCode,RC.RegionID,RC.SeasonID,RC.CommodityID,RC.TargetValue,RC.MinLotSize,\n" +
					"RC.MaxRigtsInLot,RC.HarvestRelaxation,RC.Status,GS.Name as State,GR.Name as Region, \n" +
					"			ASS.Name as Season,AC.Name as Commodity, RC.IsValid, RC.ErrorMessage  FROM regional_commodity RC \n" +
					"			LEFT JOIN geo_state GS ON (RC.StateCode = GS.StateCode)\n" +
					"			LEFT JOIN agri_season ASS ON (RC.SeasonID = ASS.ID) \n" +
					"			LEFT JOIN geo_region GR ON(RC.RegionID = GR.RegionID)\n" +
					"			LEFT JOIN agri_commodity AC ON (RC.CommodityID = AC.ID)\n" +
					"			where RC.IsValid = 0 and (AC.Name like :searchText\n" +
					"			OR GS.Name like :searchText\n" +
					"			OR GR.Name like :searchText\n" +
					"			OR ASS.Name like :searchText\n" +
					"			OR RC.TargetValue like :searchText\n" +
					"			OR RC.MinLotSize like :searchText\n" +
					"			OR RC.MaxRigtsInLot like :searchText\n" +
					"			OR RC.HarvestRelaxation like :searchText\n" +
					"			OR RC.Status like :searchText)", nativeQuery = true)
	Page<RegionalCommodityDtoInf> getRegionalCommodityInvalidated(Pageable pageable, String searchText);
	
	@Query(value = "SELECT RC.ID,RC.StateCode,RC.RegionID,RC.SeasonID,RC.CommodityID,RC.TargetValue,RC.MinLotSize,\n" +
			"RC.MaxRigtsInLot,RC.HarvestRelaxation,RC.Status,GS.Name as State,GR.Name as Region, \n" +
			"			ASS.Name as Season,AC.Name as Commodity, RC.IsValid FROM regional_commodity_missing RC \n" +
			"			LEFT JOIN geo_state GS ON (RC.StateCode = GS.StateCode)\n" +
			"			LEFT JOIN agri_season ASS ON (RC.SeasonID = ASS.ID) \n" +
			"			LEFT JOIN geo_region GR ON(RC.RegionID = GR.RegionID)\n" +
			"			LEFT JOIN agri_commodity AC ON (RC.CommodityID = AC.ID)\n" +
			"			where RC.IsValid = 0 and (AC.Name like :searchText\n" +
			"			OR GS.Name like :searchText\n" +
			"			OR GR.Name like :searchText\n" +
			"			OR ASS.Name like :searchText\n" +
			"			OR RC.TargetValue like :searchText\n" +
			"			OR RC.MinLotSize like :searchText\n" +
			"			OR RC.MaxRigtsInLot like :searchText\n" +
			"			OR RC.HarvestRelaxation like :searchText\n" +
			"			OR RC.Status like :searchText)",
			countQuery = "SELECT RC.ID,RC.StateCode,RC.RegionID,RC.SeasonID,RC.CommodityID,RC.TargetValue,RC.MinLotSize,\n" +
					"RC.MaxRigtsInLot,RC.HarvestRelaxation,RC.Status,GS.Name as State,GR.Name as Region, \n" +
					"			ASS.Name as Season,AC.Name as Commodity, RC.IsValid  FROM regional_commodity_missing RC \n" +
					"			LEFT JOIN geo_state GS ON (RC.StateCode = GS.StateCode)\n" +
					"			LEFT JOIN agri_season ASS ON (RC.SeasonID = ASS.ID) \n" +
					"			LEFT JOIN geo_region GR ON(RC.RegionID = GR.RegionID)\n" +
					"			LEFT JOIN agri_commodity AC ON (RC.CommodityID = AC.ID)\n" +
					"			where RC.IsValid = 0 and (AC.Name like :searchText\n" +
					"			OR GS.Name like :searchText\n" +
					"			OR GR.Name like :searchText\n" +
					"			OR ASS.Name like :searchText\n" +
					"			OR RC.TargetValue like :searchText\n" +
					"			OR RC.MinLotSize like :searchText\n" +
					"			OR RC.MaxRigtsInLot like :searchText\n" +
					"			OR RC.HarvestRelaxation like :searchText\n" +
					"			OR RC.Status like :searchText)", nativeQuery = true)
	Page<RegionalCommodityDtoInf> getRegionalCommodityMissingInvalidated(Pageable pageable, String searchText);

}
