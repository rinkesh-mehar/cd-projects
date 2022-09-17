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
@Table(name = "report_download_request", schema = "cdt_website")
@JsonInclude(value = Include.NON_NULL)
public class ReportDownloadRequest {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "ReportID")
	private Integer reportId;

	@Column(name = "Name")
	private String name;

	@Column(name = "Email")
	private String email;

	@Column(name = "Mobile")
	private String mobile;

	@Column(name = "OtherIndustry")
	private String otherIndustry;

	@Column(name = "Organization")
	private String organization;
	
	@Column(name = "IndustryID")
	private Integer IndustryID;

	@Transient
	private Date createdAt;

	@Transient
	private Date updatedAt;
}
