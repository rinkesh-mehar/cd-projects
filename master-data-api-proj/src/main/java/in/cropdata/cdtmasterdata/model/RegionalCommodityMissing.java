package in.cropdata.cdtmasterdata.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.Data;

@Entity(name = "regional_commodity_missing")
@Data
public class RegionalCommodityMissing {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int  id;
		
	@Column(name = "StateCode")
	private int stateCode;
	
	@Transient
	private String state;

	@Column(name = "RegionID")
	private Integer regionId;
	
	@Transient
	private String region;
	
	@Column(name = "SeasonID")
	private int seasonId;
	
	@Transient
	private String season;

	@Column(name = "CommodityID")
	private int commodityId;
	
	@Transient
	private String commodity;
	
	@Column(name = "TargetValue")
	private int targetValue;
	
	@Column(name = "MinLotSize")
	private int minLotSize;
	
	@Column(name = "MaxRigtsInLot")
	private int maxRigtsInLot;
	
	@Column(name = "HarvestRelaxation")
	private int harvestRelaxation;
	
//	@Column(name = "UpdatedAt")
	@Transient
	private Date updatedAt;
	
//	@Column(name = "CreatedAt")
	@Transient
	private Date createdAt;
	
	@Column(name = "Status")
	private String status;

}
