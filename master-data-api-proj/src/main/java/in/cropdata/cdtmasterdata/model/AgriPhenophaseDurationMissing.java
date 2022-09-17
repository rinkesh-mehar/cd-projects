package in.cropdata.cdtmasterdata.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Entity(name = "agri_phenophase_duration_missing")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AgriPhenophaseDurationMissing {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;
	
	@Column(name = "StateCode")
	private int stateCode;
	
	@Transient
	private String state;
	
	@Column(name = "CommodityID")
	private int commodityId;
	
	@Transient
	private String commodity;
	
	@Column(name = "VarietyID")
	private int varietyId;
	
	@Transient
	private String variety;
	
	@Column(name = "SeasonID")
	private int seasonId;
	
	@Transient
	private String season;
	
	@Column(name = "PhenophaseID")
	private int phenophaseId;
	
	@Transient
	private String phenophase;
	
	@Column(name = "PhenophaseStart")
	private int phenophaseStart;
	
	@Column(name = "PhenophaseEnd")
	private int phenophaseEnd;
	
	@Column(name = "ImageID")
	private String imageId;

	@Column(name = "FileUrl")
	private String imageUrl;
	
	
//	@Column(name = "UpdatedAt")
	@Transient
	private Date updatedAt;
	
//	@Column(name = "CreatedAt")
	@Transient
	private Date createdAt;
	
	@Column(name = "Status")
	private String status;
}

