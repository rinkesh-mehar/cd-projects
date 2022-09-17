package in.cropdata.configserver.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Entity(name = "application_properties")
@Data
public class ApplicationProperties {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;

	@Column(name = "ApplicationName")
	private String applicationName;

	@Column(name = "EnvProfile")
	private String envProfile;

	@Column(name = "Label")
	private String label;

	@Column(name = "AttrKey")
	private String attrKey;

	@Column(name = "AttrValue")
	private String attrValue;

	@Column(name = "ApplicationID")
	private int applicationId;

	@Column(name = "UpdatedAt")
	private Date updatedAt;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CreatedAt")
	private Date createdAt;

	@Column(name = "Status")
	private String status;
}
