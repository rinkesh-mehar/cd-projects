package in.cropdata.cdtmasterdata.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.Data;

@Data
@Entity(name = "agri_variety_stress")
public class AgriVarietyStress {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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

	@Column(name = "CommodityID")
	private int commodityId;

	@Transient
	private String commodity;

	@Column(name = "VarietyID")
	private int varietyId;

	@Transient
	private String variety;

	@Column(name = "StressTypeID")
	private int stressTypeId;

	@Transient
	private String stressType;

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

	@Transient
	private List<AgriStress> resistantStress;

	@Transient
	private List<AgriStress> susceptibleStress;

	@Transient
	private List<AgriStress> tolerantStress;
}
