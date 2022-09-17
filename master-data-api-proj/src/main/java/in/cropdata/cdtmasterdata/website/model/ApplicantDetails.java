package in.cropdata.cdtmasterdata.website.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@Entity
@Table(name = "alpplicant_details", schema = "cdt_website")
@JsonInclude(value = Include.NON_NULL)
public class ApplicantDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	
	//Contact Person Name
	@Column(name = "Name")
	private String name;
	
	@Column(name = "DesignationID")
	private Integer designationID;
	
	@Column(name = "OtherDesignationName")
	private String otherDesignationName;
	
	@Column(name = "TelephoneNumber")
	private String telephoneNumber;
	
	@Column(name = "MobileNumber")
	private String mobileNumber;
	
	@Column(name = "EmailAddress")
	private String emailAddress;
	
//	@Column(name = "RegisteredAddress")
//	private String registeredAddress;
	
	//Origin
	@Column(name = "Nationality")
	private String nationality;
	
//	@Column(name = "LocationOfInterest")
//	private String locationOfInterest;
	
	@Column(name = "ApplicantTypeID")
	private Integer applicantTypeID;
	
	@Column(name = "Website")
	private String website;
	
	@Transient
	private Date createdAt;
	
	@Transient
	private Date updatedAt;
	
	@Transient
	private String status;

}
