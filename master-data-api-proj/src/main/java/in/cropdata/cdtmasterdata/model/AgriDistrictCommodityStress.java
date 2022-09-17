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
@Entity(name = "agri_district_commodity_stress")
public class AgriDistrictCommodityStress {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;

	@Column(name = "StateCode")
	private Integer stateCode;

	@Column(name = "DistrictCode")
	private Integer districtCode;

	@Column(name = "SeasonID")
	private Integer seasonId;
	
	@Column(name = "CommodityID")
	private int commodityId;

	@Transient
	private String commodity;

	@Column(name = "VarietyID")
	private Integer varietyId;

	@Column(name = "StressTypeID")
	private int stressTypeId;

	@Transient
	private String stressType;
	
	@Column(name = "StressID")
	private Integer stressId;

	@Column(name = "StartPhenophaseID")
	private int startPhenophaseId;

	@Transient
	private String startPhenophase;

	@Column(name = "EndPhenophaseID")
	private int endPhenophaseId;

	@Column(name = "ImageID")
	private String imageId;

	@Column(name = "FileUrl")
	private String imageURL;

	@Column(name = "Phenophases")
	private String phenophases;
	
	@Transient
	private String endPhenophase;

//	@Column(name = "Name")
//	private String name;

//	@Column(name = "ScientificName")
//	private String scientificName;

//	@Transient
//	private Set<FlipbookSymptoms> symptomsList;

//	@Transient
//	private String symptomsListName;

//	@Column(name = "UpdatedAt")
	@Transient
	private Date updatedAt;
	
//	@Column(name = "CreatedAt")
	@Transient
	private Date createdAt;
	
	@Column(name = "Status")
	private String status;
}
