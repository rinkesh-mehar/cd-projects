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
@Table(name = "opportunities_education", schema = "cdt_website")
public class OpportunitiesEducation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;

	@Column(name = "OpportunityID")
	private Integer opportunityID;
	
	@Column(name = "EducationID")
	private Integer educationID;
	
	@Transient
	private Date createdAt;

	@Transient
	private Date updatedAt;
	
}
