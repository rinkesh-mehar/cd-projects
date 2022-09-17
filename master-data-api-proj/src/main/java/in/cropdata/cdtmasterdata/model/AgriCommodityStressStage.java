package in.cropdata.cdtmasterdata.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@Entity(name = "agri_commodity_stress_stage")
@JsonInclude(value = Include.NON_NULL)
public class AgriCommodityStressStage {
	
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
	
//	@Column(name = "StressTypeID")
//	private int stressTypeId;
	
	@Transient
	private String stressType;
	
	@Column(name = "Phenophases")
	private String phenophase;
	
	@Column(name = "StressID")
	private int stressId;
	
	@Transient
	private String stress;
	
//	@Column(name = "Name")
//	private String name;
	
	@Column(name = "StageID")
	private int stageId;
	
	@Column(name = "Description")
	private String description;
	

	@Column(name = "StartPhenophaseID")
	private Integer startPhenophaseId;
	
	@Transient
	private String startPhenophaseName;
	
	@Column(name = "EndPhenophaseID")
	private Integer endPhenophaseId;
	
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
