package in.cropdata.portal.gstmDataModels.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author cropdata-ujwal
 * @package in.cropdata.portal.gstmDataModels.model
 * @date 05/09/20
 * @time 6:21 PM
 * To change this template use File | Settings | File and Code Templates
 */
@Data
@Entity
@Table(name = "pvi_daily_market_price", schema = "gstm_data_models")
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class DailyMarketPrice
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "StateCode")
    private Integer stateCode;

    @Column(name = "DistrictCode")
    private Integer districtCode;

    @Column(name = "MarketID")
    private Integer marketID;

    @Column(name = "CommodityID")
    private Integer commodityID;

    @Column(name = "PricingAgriVarietyID")
    private Integer PricingAgriVarietyID;

    @Column(name = "ArrivalDate")
    private Date ArrivalDate;

    @Column(name = "MinPrice")
    private Double minPrice;

    @Column(name = "MaxPrice")
    private Double maxPrice;

    @Column(name = "ModalPrice")
    private Double modalPrice;

    @Column(name = "MinPriceChangePercent")
    private Double MinPriceChangePercent;

    @Column(name = "MaxPriceChangePercent")
    private Double maxPriceChangePercent;
}
