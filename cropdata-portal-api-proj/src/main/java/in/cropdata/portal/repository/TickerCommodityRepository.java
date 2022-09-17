package in.cropdata.portal.repository;

import in.cropdata.portal.vo.GeoDistrictInfVO;
import in.cropdata.portal.model.TickerCommodity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface TickerCommodityRepository extends JpaRepository<TickerCommodity, Integer> {

    @Query(value = "select ID,Name from cdt_master_data.agri_commodity", nativeQuery = true)
    List<Map<String, Object>> getAllCommodities();

    @Query(value = "select ID,VarietyName as Name from cdt_master_data.pricing_agri_variety where CommodityID = ?1", nativeQuery = true)
    List<Map<String, Object>> getVarietiesByCommodity(Integer commodityId);

    @Query(value = "select ID,StateCode,Name from cdt_master_data.geo_state", nativeQuery = true)
    List<Map<String, Object>> getAllState();

    @Query(value = "select ID,DistrictCode,Name from cdt_master_data.geo_district where StateCode=?1", nativeQuery = true)
    List<Map<String, Object>> getDistrictByState(Integer stateCode);

    @Query(value = "select ID,Name from cdt_master_data.agri_market where StateCode=?1 and DistrictCode=?2", nativeQuery = true)
    List<Map<String, Object>> getMarketByStateAndDistrict(Integer stateCode, Integer districtCode);

    @Query(value = "select StateCode , DistrictCode from cdt_master_data.agri_market where id = ?1", nativeQuery = true)
    GeoDistrictInfVO getStateAndDistrictByMarketId(Integer marketId);

    @Query(value = "SELECT apd.PhenophaseID as ID, ap.Name FROM cdt_master_data.agri_phenophase_duration as apd\n"
            + "INNER JOIN cdt_master_data.agri_phenophase as ap ON ap.ID = apd.PhenophaseID\n"
            + "WHERE apd.CommodityID = ?1 group by apd.CommodityID, apd.PhenophaseID\n"
            + "order by ap.Name", nativeQuery = true)
    List<Map<String, Object>> getPhenophase(Integer commodityId);

    @Query(value = "SELECT distinct ID, Name FROM cdt_master_data.agri_commodity_stress \n"
            + "WHERE CommodityID = ?1 and find_in_set(?2, Phenophases) ORDER BY Name", nativeQuery = true)
    List<Map<String, Object>> getStress(Integer commodityId, Integer phenophaseId);

    @Query(value = "select TC.ID as TickerCommodityID,TC.CommodityID,\n"
            + "			TC.PhenophaseID,AC.Name as Commodity,AP.Name as Phenophase\n"
            + "            ,group_concat(TCS.ID) as TickerCommodityStressID,\n"
            + "            group_concat(ASS.Name) as Stress\n"
            + "			from cdt_website.ticker_commodity_stress TCS \n"
            + "			INNER JOIN cdt_website.ticker_commodity TC ON (TCS.TickerID = TC.ID)\n"
            + "			INNER JOIN cdt_master_data.agri_commodity AC ON (AC.ID = TC.CommodityID)\n"
            + "			INNER JOIN cdt_master_data.agri_phenophase AP ON (AP.ID = TC.PhenophaseID)\n"
            + "			INNER JOIN cdt_master_data.agri_commodity_stress ASS ON (ASS.ID = TCS.StressID)\n"
            + "            group by TC.ID,TC.CommodityID,TC.PhenophaseID,AC.Name,AP.Name", countQuery = "select TC.ID as TickerCommodityID,TC.CommodityID,\n"
            + "			TC.PhenophaseID,AC.Name as Commodity,AP.Name as Phenophase\n"
            + "            ,group_concat(TCS.ID) as TickerCommodityStressID,\n"
            + "            group_concat(ASS.Name) as Stress\n"
            + "			from cdt_website.ticker_commodity_stress TCS \n"
            + "			INNER JOIN cdt_website.ticker_commodity TC ON (TCS.TickerID = TC.ID)\n"
            + "			INNER JOIN cdt_master_data.agri_commodity AC ON (AC.ID = TC.CommodityID)\n"
            + "			INNER JOIN cdt_master_data.agri_phenophase AP ON (AP.ID = TC.PhenophaseID)\n"
            + "			INNER JOIN cdt_master_data.agri_commodity_stress ASS ON (ASS.ID = TCS.StressID)\n"
            + "            group by TC.ID,TC.CommodityID,TC.PhenophaseID,AC.Name,AP.Name", nativeQuery = true)
    Page<Map<String, Object>> getCommodityStressListPaginated(Pageable pageable, String searchText);

    @Query(value = "select TC.ID as TickerCommodityID, TC.Status as status, TC.CommodityID,\n"
            + "			TC.PhenophaseID,AC.Name as Commodity,AP.Name as Phenophase\n"
            + "            ,group_concat(TCS.ID) as TickerCommodityStressID,\n"
            + "            group_concat(ASS.ID) as StressID, group_concat(ASS.Name) as Stress\n"
            + "			from cdt_website.ticker_commodity_stress TCS \n"
            + "			INNER JOIN cdt_website.ticker_commodity TC ON (TCS.TickerID = TC.ID)\n"
            + "			INNER JOIN cdt_master_data.agri_commodity AC ON (AC.ID = TC.CommodityID)\n"
            + "			INNER JOIN cdt_master_data.agri_phenophase AP ON (AP.ID = TC.PhenophaseID)\n"
            + "			INNER JOIN cdt_master_data.agri_stress ASS ON (ASS.ID = TCS.StressID)	\n"
            + "            group by TC.ID,TC.CommodityID,TC.PhenophaseID,AC.Name,AP.Name", nativeQuery = true)
    List<Map<String, Object>> getCommodityStressList();

    @Query(value = "select TC.ID as tickerCommodityId, TC.Status as status, TC.CommodityID as commodityId,\n"
            + "			TC.PhenophaseID as phenophaseId,AC.Name as commodity,AP.Name as phenophase\n"
            + "            ,group_concat(TCS.ID) as tickerCommodityStressList,\n"
            + "            group_concat(ASS.ID) as stressId, group_concat(ASS.Name) as stressList\n"
            + "			from cdt_website.ticker_commodity_stress TCS \n"
            + "			INNER JOIN cdt_website.ticker_commodity TC ON (TCS.TickerID = TC.ID)\n"
            + "			INNER JOIN cdt_master_data.agri_commodity AC ON (AC.ID = TC.CommodityID)\n"
            + "			INNER JOIN cdt_master_data.agri_phenophase AP ON (AP.ID = TC.PhenophaseID)\n"
            + "			INNER JOIN cdt_master_data.agri_commodity_stress ASS ON (ASS.ID = TCS.StressID)	\n" + "where TC.ID = ?1"
            + "            group by TC.ID,TC.CommodityID,TC.PhenophaseID,AC.Name,AP.Name", nativeQuery = true)
    Map<String, Object> findCommodityAndStress(Integer id);

    @Query(value = "select Commodity,group_concat(Phenophase) as Phenophase, group_concat(Stress separator '##') as Stress from (\n"
            + "select AC.Name as Commodity,AP.Name as Phenophase,group_concat(ASS.Name separator ', ') as Stress\n"
            + "from cdt_website.ticker_commodity TC\n"
            + "INNER JOIN cdt_website.ticker_commodity_stress TCS ON (TC.ID=TCS.TickerID)\n"
            + "INNER JOIN cdt_master_data.agri_commodity AC ON (TC.CommodityID=AC.ID)\n"
            + "INNER JOIN cdt_master_data.agri_phenophase AP ON (TC.PhenophaseID = AP.ID)\n"
            + "INNER JOIN cdt_master_data.agri_stress ASS ON (TCS.StressID = ASS.ID)\n"
            + "group by Commodity,Phenophase) temp group by Commodity", nativeQuery = true)
    List<Map<String, Object>> getCommodityWiseStress();

}
