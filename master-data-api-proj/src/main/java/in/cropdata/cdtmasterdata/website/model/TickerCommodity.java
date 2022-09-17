package in.cropdata.cdtmasterdata.website.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

/**
 * Used for fetching/updating ticker_commodity data.
 * 
 * @author kunal
 * @since 1.0
 */

@Data
@Entity
@Table(name = "ticker_commodity", schema = "cdt_website")
public class TickerCommodity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;

	@Column(name = "CommodityID")
	private Integer commodityId;

	@Column(name = "PhenophaseID")
	private Integer phenophaseId;

//	@OneToMany(mappedBy = "tickerCommodity", cascade = CascadeType.ALL)
	@Transient
	private List<Integer> tickerCommodityStressList;

	@Transient
	private Date createdAt;

	@Transient
	private Date updatedAt;

//	@Transient
	@Column(name = "Status")
	private String status;
}
