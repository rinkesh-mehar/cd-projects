package in.cropdata.cdtmasterdata.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.Data;

@Entity(name = "geo_panchayat")
@Data
public class GeoPanchayat {

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
	
	@Column(name = "Name")
	private String name;

	@Transient
	private String tehsil;

	@Transient
	private String village;

	@Column(name = "PanchayatCode")
	private int panchayatCode;

	@Transient
	private int localBodyCode;

//	@Column(name = "UpdatedAt")
	@Transient
	private Date updatedAt;
	
//	@Column(name = "CreatedAt")
	@Transient
	private Date createdAt;
	
	@Column(name = "Status")
	private String status;

}
