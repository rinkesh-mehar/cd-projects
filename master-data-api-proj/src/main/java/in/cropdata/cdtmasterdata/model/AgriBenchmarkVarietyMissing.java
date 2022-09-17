package in.cropdata.cdtmasterdata.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.Data;

@Entity(name = "agri_benchmark_variety_missing")
@Data
public class AgriBenchmarkVarietyMissing {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "ID")
	private int id;
	
	@Column(name = "StateCode")
	private int stateCode;
	
	@Transient
	private String state;

	@Column(name = "RegionID")
	private int regionId;
	
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
	
	
	@Column(name = "VarietyID")
	private int varietyId;
	
	@Transient
	private String variety;
	
	@Column(name = "IsDrkBenchmark")
	private String isDrkBenchmark;
	
	@Column(name = "IsAgmBenchmark")
	private String isAgmBenchmark;
	
//	@Column(name = "UpdatedAt")
	@Transient
	private Date updatedAt;
	
//	@Column(name = "CreatedAt")
	@Transient
	private Date createdAt;
	
	@Column(name = "Status")
	private String status;
}
