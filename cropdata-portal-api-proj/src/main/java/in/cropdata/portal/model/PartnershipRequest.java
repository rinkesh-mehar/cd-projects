package in.cropdata.portal.model;

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

/**
 * Used for fetching/updating partnership_request data.
 * 
 * @author Kunal
 * @since 1.0
 */

@Data
@Entity
@Table(name = "partnership_request")
@JsonInclude(value = Include.NON_NULL)
public class PartnershipRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;

	@Column(name = "Name")
	private String name;

	@Column(name = "Email")
	private String email;

	@Column(name = "Mobile")
	private String mobile;

	@Column(name = "OrganisationName")
	private String organisationName;

	@Column(name = "OrganisationEmail")
	private String organisationEmail;

	@Column(name = "IndustryID")
	private Integer industryId;

	@Column(name = "OtherIndustry")
	private String otherIndustry;

	@Column(name = "PartnershipProgramID")
	private Integer partnershipProgramId;

	@Column(name = "More")
	private String more;

	@Transient
	private Date createdAt;

	@Transient
	private Date updatedAt;

}