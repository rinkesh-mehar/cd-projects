package in.cropdata.cdtmasterdata.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.Data;

@Data
@Entity(name = "agri_variety_quality_missing")
public class AgriVarietyQualityMissing {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;
	
	@Column(name = "StateCode")
	public int stateCode;
	
	@Transient
	private String state;
	
	@Column(name = "RegionID")
	private int regionId;
	
	@Transient
	private String region;
	
	@Column(name = "CommodityID")
	private int commodityId;
	
	@Transient
	private String commodity;
	
	@Column(name = "VarietyID")
	private int varietyId;
	
	@Transient
	private String variety;
	
	@Column(name = "CurrentQualityBandID")
	private String currentQuality;
	
	@Column(name = "EstimatedQualityBandID")
	private String estimatedQuality;
	
//	@Column(name = "CurrentQualityBand")
//	private String currentQualityBand;

	@Column(name = "AllowableVarianceInQualityBandID")
	private String allowableVarianceInQuality;
	
//	@Column(name = "AllowableVarianceInQualityGradePositive")
//	private float allowableVarianceInQualityGradePositive;
//	
//	@Column(name = "AllowableVarianceInQualityGradeNegative")
//	private float allowableVarianceInQualityGradeNegative;
	
//	@Column(name = "UpdatedAt")
	@Transient
	private Date updatedAt;
	
//	@Column(name = "CreatedAt")
	@Transient
	private Date createdAt;
	
	@Column(name = "Status")
	private String status;

}
