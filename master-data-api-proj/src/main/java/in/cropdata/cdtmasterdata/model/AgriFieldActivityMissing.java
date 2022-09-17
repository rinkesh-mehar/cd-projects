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
@Entity(name = "agri_field_activity_missing")
public class AgriFieldActivityMissing {

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
	
	@Column(name = "SeasonID")
	private int seasonId;
	
	@Transient
	private String season;

	@Column(name = "PhenophaseID")
	private int phenophaseId;
	
	@Transient
	private String phenophase;
	
	@Column(name = "Name")
	private String name;
	
	@Column(name = "Description")
	private String description;
	
//	@Column(name = "UpdatedAt")
	@Transient
	private Date updatedAt;
	
//	@Column(name = "CreatedAt")
	@Transient
	private Date createdAt;
	
	@Column(name = "Status")
	private String status;

	

}
