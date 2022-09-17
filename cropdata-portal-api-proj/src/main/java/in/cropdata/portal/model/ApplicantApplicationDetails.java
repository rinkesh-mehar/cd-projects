package in.cropdata.portal.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@Entity
@Table(name = "applicant_application_details", schema = "cdt_website")
@JsonIgnoreProperties(ignoreUnknown = true)
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

	@Column(name = "DateOfIncorporationLiscenceIssueDate")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date dateOfIncorporation;

	@Column(name = "DateOfIncorporationRegistration")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date dateOfIncorporationRegistration;

	@Column(name = "LiscenceExpiryDate")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date liscenceExpiryDate;

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

	@Column(name = "Reference")
	private String reference;

	@Column(name = "Nationality")
	private String nationality;

	@Column(name = "UdyogAdhaarNumber")
	private String udyogAdhaarNumber;

	@Column(name = "TanNo")
	private String tanNo;

	@Column(name = "UdyogAadharUrl")
	private String udyouAadharUrl;

	@Column(name = "PanUrl")
	private String panUrl;

	@Column(name = "GstUrl")
	private String gstUrl;

	@Column(name = "TanUrl")
	private String tanUrl;

	@Column(name = "CinUrl")
	private String cinUrl;

	@Column(name = "VatUrl")
	private String vatUrl;

	@Column(name = "IncomeTaxUrl")
	private String incomeTaxUrl;

	@Column(name = "CompanyRegistrationDocumentUrl")
	private String companyRegistrationDocumentUrl;

	@Column(name = "BussinessTypeID")
	private Integer bussinessTypeID;

	@Column(name = "CurrentOrInterestedProducts")
	private String currentOrInterestedProducts;

	@Column(name = "BusinessAddressID")
	private String businessAddressID;

	@Column(name = "AgreementAccepted")
	private String agreementAccepted;

	@Column(name = "IncomeTaxNumber")
	private String incomeTaxNumber;

	@Column(name = "ParentReference")
	private String parentReference;

	@Column(name = "MarketLicenseNumber")
	private String marketLicenseNumber;

	@Column(name = "MarketLicenseDocumentUrl")
	private String marketLicenseDocumentUrl;


	@Column(name = "CinLiscenceExpiryDate")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date cinLiscenceExpiryDate;

	@Transient
	private Date createdAt;

	@Transient
	private Date updatedAt;

	@Transient
	private String status;

}
