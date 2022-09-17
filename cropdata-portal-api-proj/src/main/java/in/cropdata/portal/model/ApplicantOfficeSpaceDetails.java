package in.cropdata.portal.model;

import java.math.BigDecimal;
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
@Table(name = "applicant_office_space_details", schema = "cdt_website")
@JsonInclude(value = Include.NON_NULL)
public class ApplicantOfficeSpaceDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer  id;
	
	@Transient
	private Date createdAt;
	
	@Transient
	private Date updatedAt;
	
	@Transient
	private String status;
	
	@Column(name = "ApplicantID")
	private Integer applicantID;
	
	@Column(name = "ApplicationAplicantID")
	private Integer applicationAplicantID;
	
	@Column(name = "OfficeSpace")
	private String officeSpace;
	
	@Column(name = "UsableArea")
	private BigDecimal usableArea;
	
	@Column(name = "OfficeAddress")
	private String officeAddress;
	
	@Column(name = "InfrastructureFacilitiesInOffice")
	private String infrastructureFacilitiesInOffice;
	
}