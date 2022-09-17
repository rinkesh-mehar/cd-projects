package in.cropdata.cdtmasterdata.website.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * Used for fetching/updating ticker_market_price data.
 * 
 * @author kunal
 * @since 1.0
 */

@Data
@Entity
@Table(name = "ticker_market_price", schema = "cdt_website")
public class MarketPrice {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;

	@Column(name = "CommodityID")
	private Integer commodityId;

	@Column(name = "VarietyID")
	private Integer varietyId;

	@Column(name = "MarketID")
	private Integer marketId;

	@Column(name = "MinPrice")
	private BigDecimal minPrice;

	@Column(name = "MaxPrice")
	private BigDecimal maxPrice;

	@Column(name = "ModalPrice")
	private BigDecimal modalPrice;

	@Transient
	private Integer districtId;

	@Transient
	private Integer stateId;

	@Transient
	private Date createdAt;

	@Transient
	private Date updatedAt;

//	@Transient
	@Column(name = "Status")
	private String status;
}
