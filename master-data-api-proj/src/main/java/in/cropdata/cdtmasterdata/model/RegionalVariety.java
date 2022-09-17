package in.cropdata.cdtmasterdata.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.Data;

@Entity(name = "regional_variety")
@Data
public class RegionalVariety {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;

	@Column(name = "StateCode")
	private int stateCode;

	@Transient
	private String state;
	
	@Column(name = "SeasonID")
	private int seasonId;

	@Transient
	private String season;

//	@Column(name = "RegionID")
//	private int regionId;
//
//	@Transient
//	private String region;

	@Column(name = "CommodityID")
	private int commodityId;

	@Transient
	private String commodity;

	@Column(name = "SowingWeekStart")
	private int sowingWeekStart;

	@Column(name = "SowingWeekEnd")
	private int sowingWeekEnd;

	@Column(name = "HarvestWeekStart")
	private int harvestWeekStart;

	@Column(name = "HarvestWeekEnd")
	private int harvestWeekEnd;

	@Column(name = "VarietyID")
	private int varietyId;

	@Transient
	private String variety;

//	@Column(name = "UpdatedAt")
	@Transient
	private Date updatedAt;
	
//	@Column(name = "CreatedAt")
	@Transient
	private Date createdAt;
	
	@Column(name = "Status")
	private String status;

	
}
