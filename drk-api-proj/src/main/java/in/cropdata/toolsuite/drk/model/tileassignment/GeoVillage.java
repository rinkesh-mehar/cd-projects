package in.cropdata.toolsuite.drk.model.tileassignment;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity(name = "geo_village")
@Data
public class GeoVillage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;

	@JsonIgnore
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

	@Column(name = "VillageCode")
	private int villageCode;

	@Column(name = "RegionID")
	private int regionId;

	@Column(name = "SubRegionID")
	private String subRegionId;

	@Column(name = "VillageVersion")
	private int villageVersion;

	@Column(name = "Name")
	private String name;

	@Column(name = "updated_at")
	private Date updatedAt;

	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "Latitude")
	private double latitude;

	@Column(name = "Longitude")
	private double longitude;

	@Column(name = "PIN")
	private int pin;

	@Column(name = "created_by")
	private int createdBy;

	@Column(name = "updated_by")
	private int updatedBy;

	@Column(name = "approval_1")
	private boolean approval1;

	@Column(name = "approver_1")
	private int approver1;

	@Column(name = "approval_2")
	private boolean approval2;

	@Column(name = "approver_2")
	private int approver2;

}
