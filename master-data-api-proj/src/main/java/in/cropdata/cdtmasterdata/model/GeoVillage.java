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

@Entity(name = "geo_village")
@Data
@JsonInclude(value = Include.NON_NULL)
public class GeoVillage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;

	@Column(name = "StateCode")
	private int stateCode;

	@Transient
	private String state;

	@Column(name = "DistrictCode")
	private int districtCode;
	
	@Transient
	private String district;

	@Column(name = "TehsilCode")
	private int tehsilCode;

	@Transient
	private String tehsil;

	@Column(name = "PanchayatCode")
	private int panchayatCode;
	
//	@Transient
//	private String panchayat;

	@Column(name = "VillageCode")
	private int villageCode;

//	@Column(name = "RegionID")
//	private int regionId;

	@Column(name = "SubRegionID")
	private int subRegionId;

//	@Column(name = "VillageVersion")
//	private int villageVersion;

	@Column(name = "Name")
	private String name;

	@Column(name = "Latitude")
	private double latitude;

	@Column(name = "Longitude")
	private double longitude;

	@Column(name = "PIN")
	private int pin;

//	@Column(name = "UpdatedAt")
	@Transient
	private Date updatedAt;
	
//	@Column(name = "CreatedAt")
	@Transient
	private Date createdAt;
	
	@Column(name = "Status")
	private String status;

}
