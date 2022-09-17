package in.cropdata.cdtmasterdata.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.Data;

@Data
@Entity(name = "agri_stress_stage_missing")
public class AgriStressStageMissing {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;
	
//	@Column(name = "RegionID")
//	private int regionId;
//	
//	@Transient
//	private String region;
	
	@Column(name = "CommodityID")
	private int commodityId;
	
	@Transient
	private String commodity;
	
//	@Column(name = "PhenophaseID")
//	private int phenophaseId;
	
	@Column(name = "StressTypeID")
	private int stressTypeId;
	
	@Transient
	private String stressType;
	
//	@Transient
//	private String phenophase;
	
	@Column(name = "StressID")
	private int stressId;
	
	@Transient
	private String stress;
	
	@Column(name = "Name")
	private String name;
	
	@Column(name = "Description")
	private String description;
	

	@Column(name = "StartPhenophaseID")
	private int startPhenophaseId;
	
	@Transient
	private String startPhenophaseName;
	
	@Column(name = "EndPhenophaseID")
	private int endPhenophaseId;
	
	@Transient
	private String endPhenophaseName;


//	@Column(name = "UpdatedAt")
	@Transient
	private Date updatedAt;
	
//	@Column(name = "CreatedAt")
	@Transient
	private Date createdAt;
	
	@Column(name = "Status")
	private String status;

	
}
