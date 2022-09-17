package in.cropdata.portal.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "job_applications", schema = "cdt_website")
@JsonInclude(value = Include.NON_NULL)
public class JobApplication {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;

	@Column(name = "OpportunityID")
	private Integer opportunityID;

	@Column(name = "Name")
	private String name;

	@Column(name = "Email")
	private String email;

	@Column(name = "Mobile")
	private String mobile;

	@Column(name = "ResumeUrl")
	private String resumeUrl;

	@Column(name = "Address")
	private String address;
	
	@Column(name = "AppliedCount")
	private Integer appliedCount;

	@Transient
	private Date createdAt;

	@Transient
	private Date updatedAt;
	
}
