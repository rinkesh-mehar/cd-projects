package in.cropdata.portal.gstmDataModels.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author cropdata-ujwal
 * @package in.cropdata.portal.gstmDataModels.repository.model.winnerMarket
 * @date 13/08/20
 * @time 3:20 PM
 * To change this template use File | Settings | File and Code Templates
 */

@Data
@Entity
@Table(name = "pvi_winner_markets", schema = "gstm_data_models")
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class WinnerMarket
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "StateCide")
    private String stateCode;

    @Column(name = "DistrictCode")
    private String districtCode;

    @Column(name = "CommodityID")
    private Integer commodityID;

    @Column(name = "PricingAgriVarietyID")
    private String pricingAgriVarietyID;

    @Column(name = "MarketID")
    private String marketID;

    @Column(name = "MinPrice")
    private String minPrice;

    @Column(name = "MaxPrice")
    private String maxPrice;

    @Column(name = "ModalPrice")
    private String modalPrice;

    @Transient
    private String commodityName;

    @Transient
    private String marketName;

    @Transient
    private String stateName;

    @Transient
    private String districtName;

    @Transient
    private String pricingAgriVarietyName;

    @Transient
    private String arrivalDate;

    @Transient
    private String status;

    @Transient
    private String varietyName;

    @Transient
    private Date createdAt;

    @Transient
    private Date updatedAt;

    @Transient
    public String minPriceChangePercentage;

    @Transient
    public String maxPriceChangePercentage;

    @Transient
    public String modalPriceChangePercentage;

    @Transient
    private Date dates;
}
