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
@Entity(name = "agri_fertilizer")
public class AgriFertilizer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;
	
	@Column(name = "StateCode")
	private int stateCode;
	
	@Transient
	private String state;

//	@Column(name = "RegionID")
//	private int regionId;
//	
//	@Transient
//	private String region;
	
	@Column(name = "SeasonID")
	private int seasonId;
	
	@Transient
	private String season; 
	
	@Column(name = "DoseFactorID")
	private int doseFactorId;
	
	@Transient
	private String doseFactor;

	@Column(name = "CommodityID")
	private int commodityId;
	
	@Transient
	private String commodity;

	@Column(name = "Name")
	private String name;

	@Column(name = "UomID")
	private int uomId;
	
	@Transient
	private String uom;

	@Column(name = "Dose")
	private double dose;

	@Column(name = "Note")
	private String note;

//	@Column(name = "UpdatedAt")
	@Transient
	private Date updatedAt;
	
//	@Column(name = "CreatedAt")
	@Transient
	private Date createdAt;
	
	@Column(name = "Status")
	private String status;
}
