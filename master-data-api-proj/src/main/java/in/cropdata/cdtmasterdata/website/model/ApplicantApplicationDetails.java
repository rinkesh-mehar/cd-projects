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
@Table(name = "applicant_application_details", schema = "cdt_website")
@JsonInclude(value = Include.NON_NULL)
public class ApplicantApplicationDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "ApplicantID")
	private Integer applicantID;
	
	@Column(name = "ApplicationTypeID")
	private Integer applicationTypeID;
	
	@Column(name = "CompanyName")
	private String companyName;
	
	@Column(name = "DateOfIncorporation")
	private String dateOfIncorporation;
	
	@Column(name = "PanNumber")
	private String panNumber;
	
	@Column(name = "CinNumber")
	private String cinNumber;
	
	@Column(name = "GstNumber")
	private String gstNumber;
	
	@Column(name = "Vat")
	private String vat;
	
	@Column(name = "CompanyRegistrationNumber")
	private String companyRegistrationNumber;
	
	@Column(name = "BuildingName")
	private String buildingName;
	
	@Column(name = "StreetName")
	private String streetName;
	
	@Column(name = "PostalCode")
	private String postalCode;
	
	@Column(name = "Country")
	private String country;
	
	@Column(name = "State")
	private String state;
	
	@Column(name = "City")
	private String city;	
	
//	@Column(name = "BusinessAddress")
//	private String businessAddress;
//	
	@Column(name = "Reference")
	private String reference;
	
	@Transient
	private Date createdAt;
	
	@Transient
	private Date updatedAt;
	
	@Transient
	private String status;
	
}
