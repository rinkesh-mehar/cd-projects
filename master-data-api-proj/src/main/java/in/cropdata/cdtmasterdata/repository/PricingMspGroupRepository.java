package in.cropdata.cdtmasterdata.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.cdtmasterdata.dto.interfaces.PricingMspGroupInfo;
import in.cropdata.cdtmasterdata.model.PricingMaster;
import org.springframework.data.repository.query.Param;

public interface PricingMspGroupRepository extends JpaRepository<PricingMaster, Integer> {

    @Query(value = "SELECT pmg.ID,pmg.CommodityID,pmg.Name,pmg.UpdatedAt,pmg.CreatedAt,pmg.Status, ac.Name as Commodity FROM pricing_msp_group pmg\n" +
            "			LEFT JOIN agri_commodity ac ON(ac.ID = pmg.CommodityID)", nativeQuery = true)
    List<PricingMspGroupInfo> getAllPricingMspGroup();

    @Query(value = "select ID AS commodityID, Name as name\n" +
            "from cdt_master_data.agri_commodity\n" +
            "where Status = 'Active' ORDER BY Name", nativeQuery = true)
    List<Map<String, Object>> getAllCommodities();

    @Query(value = "SELECT distinct PM.StateCode AS StateCode, GS.Name AS StateName, PM.RegionID AS RegionID, GR.Name AS RegionName, PM.Status AS Status\n" +
            "FROM cdt_master_data.pricing_master_mbep PM\n" +
            "INNER JOIN cdt_master_data.geo_state GS ON GS.StateCode = PM.StateCode\n" +
            "INNER JOIN cdt_master_data.geo_region GR ON GR.RegionID = PM.RegionID\n" +
            "GROUP BY PM.StateCode, GS.Name, PM.RegionID, GR.Name, PM.Status\n" +
            "ORDER BY GS.Name, GR.Name", nativeQuery = true)
    List<PricingMspGroupInfo> getAllStateAndRegion();

    @Query(value = "SELECT distinct PM.StateCode, GS.Name AS StateName, PM.RegionID, GR.Name AS RegionName,\n" +
            "                PM.CommodityID, AC.Name AS CommodityName, PM.Status AS Status\n" +
            "FROM cdt_master_data.pricing_master_mbep PM\n" +
            "INNER JOIN cdt_master_data.geo_state GS ON GS.StateCode = PM.StateCode\n" +
            "INNER JOIN cdt_master_data.geo_region GR ON GR.RegionID = PM.RegionID\n" +
            "INNER JOIN cdt_master_data.agri_commodity AC ON AC.ID = PM.CommodityID\n" +
            "WHERE PM.StateCode = ?1\n" +
            "  and PM.RegionID = ?2\n" +
            "GROUP BY PM.StateCode, GS.Name, PM.RegionID, GR.Name,\n" +
            "         PM.CommodityID, AC.Name, PM.Status\n" +
            "ORDER BY GS.Name, GR.Name, AC.Name", nativeQuery = true)
    List<PricingMspGroupInfo> getCommodityByStateAndRegion(final Integer stateCode, final Integer regionID);

    @Query(value = "SELECT distinct PM.StateCode, GS.Name AS StateName, PM.RegionID, GR.Name AS RegionName,\n" +
            "                PM.CommodityID, AC.Name AS CommodityName, PM.VarietyID, AV.Name AS VarietyName,\n" +
            "                PM.BandID, AB.Name GradeName, PM.Msp, PM.Mfp, PM.IsBenchmark, PM.Status AS Status\n" +
            "FROM cdt_master_data.pricing_master_mbep PM\n" +
            "INNER  JOIN cdt_master_data.geo_state GS ON GS.StateCode = PM.StateCode\n" +
            "INNER  JOIN cdt_master_data.geo_region GR ON GR.RegionID = PM.RegionID\n" +
            "INNER  JOIN cdt_master_data.agri_commodity AC ON AC.ID = PM.CommodityID\n" +
            "INNER  JOIN cdt_master_data.agri_variety AV ON AV.ID = PM.VarietyID\n" +
            "INNER  JOIN cdt_master_data.agri_band AB ON AB.ID = PM.BandID\n" +
            "WHERE PM.StateCode = ?1\n" +
            "  and PM.RegionID = ?2\n" +
            "  and PM.CommodityID = ?3\n" +
            "GROUP BY PM.StateCode, GS.Name, PM.RegionID, GR.Name,\n" +
            "         PM.CommodityID, AC.Name, PM.VarietyID, AV.Name,\n" +
            "         PM.BandID, PM.Mfp, AB.Name, PM.Msp, PM.IsBenchmark, PM.Status\n" +
            "ORDER BY GS.Name, GR.Name, AC.Name", nativeQuery = true)
    List<PricingMspGroupInfo> getMspByStateRegionCommodity(final Integer stateCode, final Integer regionID, final Integer commodityID);

    @Query(value = "SELECT distinct PM.StateCode, GS.Name AS StateName, PM.RegionID, GR.Name AS RegionName,\n" +
            "                PM.CommodityID, AC.Name AS CommodityName, PM.VarietyID, AV.Name AS VarietyName,\n" +
            "                PM.BandID, AB.Name AS GradeName, PM.MarginConstant, PM.BuyerConstant, PM.SellerConstant,\n" +
            "                PM.IsBenchmark, PM.Status AS Status\n" +
            "FROM cdt_master_data.pricing_master_mbep PM\n" +
            "         INNER  JOIN cdt_master_data.geo_state GS ON GS.StateCode = PM.StateCode\n" +
            "         INNER  JOIN cdt_master_data.geo_region GR ON GR.RegionID = PM.RegionID\n" +
            "         INNER  JOIN cdt_master_data.agri_commodity AC ON AC.ID = PM.CommodityID\n" +
            "         INNER  JOIN cdt_master_data.agri_variety AV ON AV.ID = PM.VarietyID\n" +
            "         INNER  JOIN cdt_master_data.agri_band AB ON AB.ID = PM.BandID\n" +
            "WHERE PM.StateCode = ?1\n" +
            "  and PM.RegionID = ?2\n" +
            "  and PM.CommodityID = ?3\n" +
            "GROUP BY PM.StateCode, GS.Name, PM.RegionID, GR.Name,\n" +
            "         PM.CommodityID, AC.Name, PM.VarietyID, AV.Name, PM.BandID, AB.Name,\n" +
            "         PM.MarginConstant, PM.BuyerConstant, PM.SellerConstant,\n" +
            "         PM.IsBenchmark, PM.Status\n" +
            "ORDER BY GS.Name, GR.Name, AC.Name", nativeQuery = true)
    List<PricingMspGroupInfo> getConstants(final Integer stateCode, final Integer regionID, final Integer commodityID);

    @Query(value = "SELECT distinct PM.StateCode, GS.Name AS StateName, PM.RegionID, GR.Name AS RegionName,\n" +
            "                PM.CommodityID, AC.Name AS CommodityName, PM.VarietyID, AV.Name AS VarietyName,\n" +
            "                PM.BandID, AB.Name AS GradeName, PM.IsBenchmark, PM.Status AS Status,\n" +
            "                PM.Mbep, PM.Pmp, PM.PriceSpread\n" +
            "FROM cdt_master_data.pricing_master_mbep PM\n" +
            "         INNER  JOIN cdt_master_data.geo_state GS ON GS.StateCode = PM.StateCode\n" +
            "         INNER  JOIN cdt_master_data.geo_region GR ON GR.RegionID = PM.RegionID\n" +
            "         INNER  JOIN cdt_master_data.agri_commodity AC ON AC.ID = PM.CommodityID\n" +
            "         INNER  JOIN cdt_master_data.agri_variety AV ON AV.ID = PM.VarietyID\n" +
            "         INNER  JOIN cdt_master_data.agri_band AB ON AB.ID = PM.BandID\n" +
            "WHERE PM.StateCode = ?1\n" +
            "  and PM.RegionID = ?2\n" +
            "  and PM.CommodityID = ?3\n" +
            "GROUP BY PM.StateCode, GS.Name, PM.RegionID, GR.Name,\n" +
            "         PM.CommodityID, AC.Name, PM.VarietyID, AV.Name, PM.BandID, AB.Name,\n" +
            "         PM.Mbep, PM.Pmp, PM.PriceSpread,\n" +
            "         PM.IsBenchmark, PM.Status\n" +
            "ORDER BY GS.Name, GR.Name, AC.Name", nativeQuery = true)
    List<PricingMspGroupInfo> getMbepAndPmp(final Integer stateCode, final Integer regionID, final Integer commodityID);

    @Query(value = "SELECT distinct PM.StateCode, GS.Name AS StateName, PM.RegionID, GR.Name AS RegionName,\n" +
            "                PM.CommodityID, AC.Name AS CommodityName, PM.VarietyID, AV.Name AS VarietyName,\n" +
            "                PM.BandID, AB.Name AS GradeName, PM.IsBenchmark, PM.Status AS Status,\n" +
            "                PM.PriceSpreadGrade, PM.PriceSpreadVariety\n" +
            "FROM cdt_master_data.pricing_master PM\n" +
            "         INNER  JOIN cdt_master_data.geo_state GS ON GS.StateCode = PM.StateCode\n" +
            "         INNER  JOIN cdt_master_data.geo_region GR ON GR.RegionID = PM.RegionID\n" +
            "         INNER  JOIN cdt_master_data.agri_commodity AC ON AC.ID = PM.CommodityID\n" +
            "         INNER  JOIN cdt_master_data.agri_variety AV ON AV.ID = PM.VarietyID\n" +
            "         INNER  JOIN cdt_master_data.agri_band AB ON AB.ID = PM.BandID\n" +
            "WHERE PM.StateCode = ?1\n" +
            "  and PM.RegionID = ?2\n" +
            "  and PM.CommodityID = ?3\n" +
            "GROUP BY PM.StateCode, GS.Name, PM.RegionID, GR.Name,\n" +
            "         PM.CommodityID, AC.Name, PM.VarietyID, AV.Name, PM.BandID, AB.Name,\n" +
            "         PM.PriceSpreadGrade, PM.PriceSpreadVariety,\n" +
            "         PM.IsBenchmark, PM.Status\n" +
            "ORDER BY GS.Name, GR.Name, AC.Name", nativeQuery = true)
    List<PricingMspGroupInfo> getPriceSpread(final Integer stateCode, final Integer regionID, final Integer commodityID);

    @Query(value = "select RV.VarietyID AS id, AV.Name AS name\n" +
            "from cdt_master_data.regional_variety RV\n" +
            "         inner join cdt_master_data.agri_variety AV on RV.VarietyID = AV.ID\n" +
            "where RV.StateCode = ?1\n" +
            "  and RV.CommodityID = ?2\n" +
            "  and RV.Status = 'Active'\n" +
            "group by VarietyID, Name\n" +
            "order by AV.Name", nativeQuery = true)
    List<PricingMspGroupInfo> getVariety(Integer stateCode, Integer commodityID);

    @Query(value = "select GR.RegionID, GR.Name\n" +
            "from cdt_master_data.geo_region GR\n" +
            "where GR.StateCode = ?1\n" +
            "  and GR.Status = 'Active'", nativeQuery = true)
    List<PricingMspGroupInfo> getRegion(Integer stateCode);

   /* @Query(value = "SELECT distinct  CEIL(count(PM.ID) / 200) totalRecordsCount\n" +
            "FROM cdt_master_data.pricing_master_mbep PM\n" +
            "            INNER  JOIN cdt_master_data.geo_state GS ON GS.StateCode = PM.StateCode\n" +
            "            INNER  JOIN cdt_master_data.geo_region GR ON GR.RegionID = PM.RegionID\n" +
            "            INNER  JOIN cdt_master_data.agri_commodity AC ON AC.ID = PM.CommodityID\n" +
            "            INNER  JOIN cdt_master_data.agri_variety AV ON AV.ID = PM.VarietyID\n" +
            "            INNER  JOIN cdt_master_data.agri_band AB ON AB.ID = PM.BandID\n" +
            "            WHERE PM.StateCode = ?1\n" +
            "              and PM.RegionID = ?2\n" +
            "              and PM.CommodityID = ?3\n", nativeQuery = true)
    Integer totalNoOfPages(Integer stateCode, Integer regionId,  Integer commodityId);*/
}
