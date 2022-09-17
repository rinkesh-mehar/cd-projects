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
@Table(name = "applicant_other_business_details", schema = "cdt_website")
@JsonInclude(value = Include.NON_NULL)
public class ApplicantOtherBusinessDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer  id;
	
	@Column(name = "ApplicantID")
	private Integer  applicantID;
	
	@Column(name = "ApplicationAplicantID")
	private Integer  applicationAplicantID;
	
//	@Column(name = "FirmName")
//	private String  firmName;
//	
//	@Column(name = "Product")
//	private String  product;
//	
//	@Column(name = "OtherBusinessSamePremises")
//	private String otherBusinessSamePremises;
//	
//	@Column(name = "FirmTypeID")
//	private Integer  firmTypeID;
	
	@Column(name = "NatureOfBusiness")
	private String  natureOfBusiness;
	
//	@Column(name = "YearOfEstablishment")
//	private String  yearOfEstablishment;
//	
//	@Column(name = "PresentNetWorth")
//	private String  presentNetWorth;
//	
//	@Column(name = "City")
//	private String  city;
	
	@Column(name = "CurrencyID")
	private Integer  currencyID;
	
	@Column(name = "BusinessTypeID")
	private Integer businessTypeID;
	
	@Column(name = "AverageRevenue")
	private String averageRevenue;
	
	@Column(name = "NonAgriculturalBusinessName")
	private String nonAgriculturalBusinessName;
	
	@Transient
	private Date  createdAt;
	
	@Transient
	private Date  updatedAt;
	
	@Transient
	private String  status;
	
}
