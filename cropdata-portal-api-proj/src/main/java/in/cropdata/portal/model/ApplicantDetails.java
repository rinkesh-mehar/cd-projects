package in.cropdata.portal.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@Entity
@Table(name = "alpplicant_details", schema = "cdt_website")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApplicantDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	
	//Contact Person Name
	@Column(name = "FirstName")
	private String firstName;

	@Column(name = "LastName")
	private String lastName;

	@Column(name = "MiddleName")
	private String middlename;

	
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

//	@Column(name = "BuildingName")
//	private String buildingName;
//
//	@Column(name = "StreetName")
//	private String streetName;
//
//	@Column(name = "PostalCode")
//	private String postalCode;
//
//	@Column(name = "Country")
//	private String country;
//
//	@Column(name = "State")
//	private String state;
//
//	@Column(name = "City")
//	private String city;

	@Column(name = "AuthToken")
	private String authToken;

	@Column(name = "MembershipType")
	private String membershipType;

	@Column(name = "RegisteredAddressID")
	private String registeredAddressID;

	@Column(name = "StepsCompleted")
	private Integer stepsCompleted;

	@Column(name = "CountryCode")
	private String countryCode;

	@Column(name = "IsdCode")
	private String isdCode;

	@Transient
	private Date createdAt;
	
	@Transient
	private Date updatedAt;
	
	@Transient
	private String status;

}
