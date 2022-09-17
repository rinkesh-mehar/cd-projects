package in.cropdata.cdtmasterdata.website.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

/**
 * Used for fetching/updating ticker_commodity_stress data.
 * 
 * @author kunal
 * @since 1.0
 */

@Data
@Entity
@Table(name = "ticker_commodity_stress", schema = "cdt_website")
public class TickerCommodityStress {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;

	@Column(name = "TickerID")
	private Integer tickerId;

	@Column(name = "StressID")
	private Integer stressId;

//	@ManyToOne
//	@JoinColumn(name = "TickerID", referencedColumnName = "ID", insertable = false, updatable = false)
//	private TickerCommodity tickerCommodity;

	@Transient
	private Date createdAt;

	@Transient
	private Date updatedAt;

	@Column(name = "Status")
	private String status;

}
