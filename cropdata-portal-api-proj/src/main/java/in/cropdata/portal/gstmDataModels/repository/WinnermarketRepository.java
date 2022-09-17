package in.cropdata.portal.gstmDataModels.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import in.cropdata.portal.gstmDataModels.dto.WinnerMarketDTO;
import in.cropdata.portal.gstmDataModels.model.WinnerMarket;

/**
 * @author cropdata-ujwal
 * @package in.cropdata.portal.gstmDataModels.repository
 * @date 13/08/20
 * @time 3:34 PM
 * To change this template use File | Settings | File and Code Templates
 */
@Repository
public interface WinnermarketRepository extends JpaRepository<WinnerMarket, Integer>
{

    @Query(value = "select ac.ID from cdt_master_data.agri_commodity ac where ac.Name = ?1", nativeQuery = true)
    Integer getCommodityID(String name);

    @Query(value = "select pwm.StateCode, lower(gs.Name) stateName, pwm.DistrictCode, lower(gd.Name) districtName,\n" +
            "      pwm.CommodityID commodityID,lower(ac.Name) commodityName, pwm.PricingAgriVarietyID, lower(pav.VarietyName) pricingAgriVarietyName, pwm.MarketID, lower(av.Name) marketName,pwm.MinPrice,pwm.MaxPrice,\n" +
            "      pwm.ModalPrice, pwm.MinPriceChangePercent minPriceChangePercentage," +
            "pwm.MaxPriceChangePercent maxPriceChangePercentage, pwm.ModalPriceChangePercent modalPriceChangePercentage\n" +
            "      from gstm_data_models.pvi_winner_markets pwm\n" +
            "      inner join cdt_master_data.geo_state gs on gs.StateCode = pwm.StateCode\n" +
            "      inner join cdt_master_data.geo_district gd on gd.DistrictCode = pwm.DistrictCode\n" +
            "      inner join cdt_master_data.agri_commodity ac on ac.ID = pwm.CommodityID\n" +
            "      inner join cdt_master_data.pricing_agri_variety pav on pav.ID = pwm.PricingAgriVarietyID\n" +
            "      inner join cdt_master_data.agri_market av on av.ID = pwm.MarketID where pwm.CommodityID = ?1", nativeQuery = true)
    public WinnerMarketDTO getWinnerMarketByCommodity(Integer commodityName);

   /* @Query(value = "select distinct date_format(psd.ArrivalDate, '%d-%b-%y')ArrivalDate,MONTH(psd.ArrivalDate) ,DATE(psd.ArrivalDate), " +
            "psd.StateCode, gs.Name stateName, psd.DistrictCode, gd.Name districtName,ac.Name commodityName,psd.CreatedAt,\n" +
            "pav.VarietyName varietyName,am.Name marketName, psd.MarketID,psd.PricingAgriVarietyID,\n" +
            "psd.CommodityID,psd.MaxPrice,psd.ModalPrice,psd.MinPrice from\n" +
            "gstm_data_models.pvi_daily_market_price psd\n" +
            "inner join cdt_master_data.geo_state gs on gs.StateCode = psd.StateCode\n" +
            "inner join cdt_master_data.geo_district gd on gd.DistrictCode = psd.DistrictCode\n" +
            "inner join cdt_master_data.agri_commodity ac on ac.ID = psd.CommodityID\n" +
            "left join cdt_master_data.pricing_agri_variety pav on pav.ID = psd.PricingAgriVarietyID\n" +
            "left join cdt_master_data.agri_market am on am.ID = psd.MarketID\n" +
            "where psd.StateCode = ?1 and psd.DistrictCode = ?2 and\n" +
            "psd.CommodityID = ?3 and psd.PricingAgriVarietyID = ?4 and psd.MarketID = ?5\n" +
            "and (ArrivalDate between DATE(NOW() - INTERVAL 60 DAY) and  CURDATE())\n" +
            "order by MONTH(psd.ArrivalDate) desc,DATE(psd.ArrivalDate) desc limit 15", nativeQuery = true)*/
    @Query(value = "SELECT DISTINCT\n" +
            "    DATE_FORMAT(psd.ArrivalDate, '%d-%b-%y') ArrivalDate,\n" +
            "    MONTH(psd.ArrivalDate),\n" +
            "    DATE(psd.ArrivalDate),\n" +
            "    psd.StateCode,\n" +
            "    gs.Name stateName,\n" +
            "    psd.DistrictCode,\n" +
            "    gd.Name districtName,\n" +
            "    ac.Name commodityName,\n" +
            "    psd.CreatedAt,\n" +
            "    pav.VarietyName varietyName,\n" +
            "    am.Name marketName,\n" +
            "    psd.MarketID,\n" +
            "    psd.PricingAgriVarietyID,\n" +
            "    psd.CommodityID,\n" +
            "    psd.MaxPrice,\n" +
            "    psd.ModalPrice,\n" +
            "    psd.MinPrice,\n" +
            "    psd.ArrivalDate as arrDate\n" +
            "FROM\n" +
            "    gstm_data_models.pvi_daily_market_price psd\n" +
            "        INNER JOIN\n" +
            "    cdt_master_data.geo_state gs ON gs.StateCode = psd.StateCode\n" +
            "        INNER JOIN\n" +
            "    cdt_master_data.geo_district gd ON gd.DistrictCode = psd.DistrictCode\n" +
            "        INNER JOIN\n" +
            "    cdt_master_data.agri_commodity ac ON ac.ID = psd.CommodityID\n" +
            "        LEFT JOIN\n" +
            "    cdt_master_data.pricing_agri_variety pav ON pav.ID = psd.PricingAgriVarietyID\n" +
            "        LEFT JOIN\n" +
            "    cdt_master_data.agri_market am ON am.ID = psd.MarketID\n" +
            "WHERE\n" +
            "    psd.StateCode = ?1\n" +
            "        AND psd.DistrictCode = ?2\n" +
            "        AND psd.CommodityID = ?3\n" +
            "        AND psd.PricingAgriVarietyID = ?4\n" +
            "        AND psd.MarketID = ?5\n" +
            "        AND (ArrivalDate BETWEEN DATE(NOW() - INTERVAL 60 DAY) AND CURDATE())\n" +
            "ORDER BY psd.ArrivalDate DESC\n" +
            "LIMIT 15;", nativeQuery = true)
    public List<WinnerMarketDTO>
    getTreadingViewRecord(Integer stateCode, Integer districtCode,
                                                                 Integer commodityId, Integer varietyId,
                                                                 Integer marketId);

    @Query(value = "select distinct pwm.StateCode, lower(gs.Name) stateName, pwm.DistrictCode, lower(gd.Name) districtName,pwm.CreatedAt,\n" +
            "pwm.CommodityID,lower(ac.Name) commodityName, pwm.PricingAgriVarietyID,\n" +
            "lower(pav.VarietyName) VarietyName, pwm.MarketID, lower(am.Name) marketName,\n" +
            "pwm.MaxPrice,pwm.ModalPrice,pwm.MinPrice,date_format(pwm.ArrivalDate, '%d-%b-%y') Dates,pwm.MinPriceChangePercent minPriceChangePercentage,\n" +
            "pwm.MaxPriceChangePercent maxPriceChangePercentage,pwm.ModalPriceChangePercent modalPriceChangePercentage\n" +
            "from gstm_data_models.pvi_daily_market_price pwm\n" +
            "inner join cdt_master_data.geo_state gs on gs.StateCode = pwm.StateCode\n" +
            "inner join cdt_master_data.geo_district gd on gd.DistrictCode = pwm.DistrictCode\n" +
            "inner join cdt_master_data.agri_commodity ac on ac.ID = pwm.CommodityID\n" +
            "inner join  cdt_master_data.pricing_agri_variety pav on pav.ID = pwm.PricingAgriVarietyID\n" +
            "inner join  cdt_master_data.agri_market am on am.ID = pwm.MarketID where pwm.CommodityID=?1\n" +
            "and DATE(pwm.ArrivalDate) between DATE(NOW() - INTERVAL 2 DAY) and CURDATE() " +
            "order by stateName asc, districtName asc",nativeQuery = true)
    List<WinnerMarketDTO> getListOfMarketRecords(Integer commodityID);

    @Query(value = "select pwm.StateCode, lower(gs.Name) stateName, pwm.DistrictCode, lower(gd.Name) districtName,\n" +
            "pwm.CommodityID,lower(ac.Name) commodityName, pwm.PricingAgriVarietyID,\n" +
            "lower(pav.VarietyName) marketVarietyName, pwm.MarketID, lower(am.Name) marketName,lower(pav.VarietyName) VarietyName,\n" +
            "pwm.MaxPrice,pwm.ModalPrice,pwm.MinPrice,date_format(pwm.ArrivalDate, '%d-%b-%y') Dates,pwm.MinPriceChangePercent minPriceChangePercentage,\n" +
            "pwm.MaxPriceChangePercent maxPriceChangePercentage,pwm.ModalPriceChangePercent modalPriceChangePercentage\n" +
            "from gstm_data_models.pvi_daily_market_price pwm\n" +
            "inner join cdt_master_data.geo_state gs on gs.StateCode = pwm.StateCode\n" +
            "inner join cdt_master_data.geo_district gd on gd.DistrictCode = pwm.DistrictCode\n" +
            "inner join cdt_master_data.agri_commodity ac on ac.ID = pwm.CommodityID\n" +
            "inner join  cdt_master_data.pricing_agri_variety pav on pav.ID = pwm.PricingAgriVarietyID\n" +
            "inner join  cdt_master_data.agri_market am on am.ID = pwm.MarketID where pwm.CommodityID=?1 and pwm.StateCode = ?2\n" +
            "and DATE(pwm.ArrivalDate)  between DATE(NOW() - INTERVAL 2 DAY) and  CURDATE() " +
            "order by stateName asc, districtName asc", nativeQuery = true)
    List<WinnerMarketDTO> getMarketListByCommodityAndState(Integer commodityID, Integer stateCode);
}
