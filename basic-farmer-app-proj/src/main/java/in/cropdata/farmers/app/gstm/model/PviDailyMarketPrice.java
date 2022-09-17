/**
 * 
 */
package in.cropdata.farmers.app.gstm.model;



import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

/**
 * @author cropdata-Aniket Naik
 *
 */
@Data
@Entity
@Table(name = "pvi_daily_market_price", schema = "gstm_data_models")
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class PviDailyMarketPrice {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "ID")
	    private Integer id;

	    @Column(name = "StateCode")
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
