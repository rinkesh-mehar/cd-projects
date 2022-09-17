package in.cropdata.cdtmasterdata.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity(name = "geo_region")
@Data
public class GeoRegion {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "RegionID")
	private int regionId;
//	@Column(name = "TileID")
//	private int tileId;
	
	@Column(name = "StateCode")
	private int stateCode;

	@Column(name = "DistrictCode")
	private Integer districtCode;

	@Transient
	private String state;
	
	@Column(name = "RlLatitude")
	private float rlLatitude;
	
	@Column(name = "RlLongitude")
	private float rlLongitude;
	
	@Column(name = "Name")
	private String name;
	
	@Column(name = "Description")
	private String description;
	
	@Transient
	@Column(name = "Onboarded")
	private String onboarded;

	@Column(name = "Address")
	private String address;

	@Column(name = "Pin")
	private String pin;

	@Column(name = "PhoneNumber")
	private String phoneNumber;

	@Column(name = "ContactPerson")
	private String contactPerson;

	@Transient
	@Column(name = "FileUrl")
	private String fileUrl;

	@Transient
	@Column(name = "MmpkUrl")
	private String mmpkUrl;

	@Transient
	@Column(name = "GraphMlUrl")
	private String graphMlUrl;

//	@Column(name = "UpdatedAt")
	@Transient
	private Date updatedAt;
	
//	@Column(name = "CreatedAt")
	@Transient
	private Date createdAt;
	
	@Column(name = "Status")
	private String status;

	public GeoRegion(String fileUrl, String mmpkUrl,String graphMlUrl, int stateCode, Integer districtCode, float rlLatitude, float rlLongitude, String name, String description, String address, String pin, String phoneNumber, String contactPerson)
	{
		this.stateCode = stateCode;
		this.districtCode = districtCode;
		this.rlLatitude = rlLatitude;
		this.rlLongitude = rlLongitude;
		this.name = name;
		this.description = description;
		this.address = address;
		this.pin = pin;
		this.phoneNumber = phoneNumber;
		this.contactPerson = contactPerson;
		this.fileUrl = fileUrl;
		this.mmpkUrl = mmpkUrl;
		this.graphMlUrl = graphMlUrl;
	}

	public GeoRegion()
	{

	}
}
