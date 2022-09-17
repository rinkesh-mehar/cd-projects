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
@Table(name = "applicant_contact_details", schema = "cdt_website")
@JsonInclude(value = Include.NON_NULL)
public class ApplicantContactDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer  id;
	
	@Column(name = "ApplicantID")
	private Integer applicantID;
	
	@Column(name = "ApplicationAplicantID")
	private Integer applicationAplicantID;
	
	@Column(name = "CompanyAuthSignName")
	private String companyAuthSignName;
	
	@Column(name = "CompanyAuthContactNumber")
	private String companyAuthContactNumber;
	
	@Column(name = "CompanyAuthSignEmail")
	private String companyAuthSignEmail;
	
	@Column(name = "CompanyFaxNumber")
	private String companyFaxNumber;
	
	@Column(name = "CompanyTelephoneNumber")
	private String companyTelephoneNumber;
	
	@Column(name = "CompanyMobileNumber")
	private String companyMobileNumber;
	
	@Column(name = "CompanyRepresentativeName")
	private String companyRepresentativeName;
	
	@Column(name = "CompanyRepresentativeContactNumber")
	private String companyRepresentativeContactNumber;
	
	@Column(name = "CompanyRepresentativeEmailID")
	private String companyRepresentativeEmailID;
	
	@Transient
	private Date createdAt;
	
	@Transient
	private Date updatedAt;
	
	@Transient
	private String status;

}
