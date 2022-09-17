package in.cropdata.cdtmasterdata.website.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

@Data
@Entity
@Table(name = "opportunities", schema = "cdt_website")
public class Opportunities {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;

	@Column(name = "PlatformID")
	private Integer platformID;
	
	@Column(name = "DepartmentID")
	private Integer departmentID;

	@Column(name = "PositionID")
	private Integer positionID;

//	@Column(name = "EducationID")
//	private Integer educationID;

	@Column(name = "Experience")
	private String experience;

//	@Column(name = "StateCode")
//	private Integer stateCode;
//
//	@Column(name = "DistrictCode")
//	private Integer districtCode;

	@Column(name = "Description")
	private String description;

	@Column(name = "Profile")
	private String profile;

	@Column(name = "Remuneration")
	private String remuneration;

	@Column(name = "ApplyTo")
	private String applyTo;
	
	@Column(name = "Location")
	private String location;

	@Transient
	private Date createdAt;

	@Transient
	private Date updatedAt;

	@Transient
	private String status;
	
	@Transient
	private Integer[] educationID;

}
