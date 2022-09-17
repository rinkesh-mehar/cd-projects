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
@Table(name = "applicant_contact_details", schema = "cdt_website")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApplicantContactDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer  id;

	@Column(name = "BuildingName")
	private String buildingName;

	@Column(name = "StreetName")
	private String streetName;

	@Column(name = "PostalCode")
	private String postalCode;

	@Column(name = "Country")
	private String country;

	@Column(name = "CountryCode")
	private String countryCode;

	@Column(name = "State")
	private String state;

	@Column(name = "City")
	private String city;

	@Transient
	private Date createdAt;

	@Transient
	private Date updatedAt;

	@Transient
	private String status;

}
