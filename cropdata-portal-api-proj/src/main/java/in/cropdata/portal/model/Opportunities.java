package in.cropdata.portal.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "opportunities", schema = "cdt_website")
public class Opportunities
{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "PlatformID")
	private Integer platformID;

	@Column(name = "DepartmentID")
	private Integer departmentID;

	@Column(name = "PositionID")
	private Integer positionID;

	@Column(name = "Experience")
	private String experience;

	@Column(name = "Description")
	private String description;

	@Column(name = "Profile")
	private String profile;

	@Column(name = "Remuneration")
	private String remuneration;

	@Column(name = "ApplyTo")
	private String applyTo;
	
	@Column(name = "Location")
	private String location;

	@Transient
	private Date createdAt;

	@Transient
	private Date updatedAt;

	@Transient
	private String status;

}
