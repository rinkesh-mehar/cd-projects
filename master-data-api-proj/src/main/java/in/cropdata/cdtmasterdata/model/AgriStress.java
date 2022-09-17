package in.cropdata.cdtmasterdata.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity(name = "agri_stress")
public class AgriStress {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;

	@Column(name = "Name")
	private String name;
	
	@Column(name = "StressTypeID")
	private Integer stressTypeId;

	//	@Column(name = "UpdatedAt")
	@Transient
	private Date updatedAt;
	
//	@Column(name = "CreatedAt")
	@Transient
	private Date createdAt;
	
	@Column(name = "Status")
	private String status;

	@Transient
	private Boolean isValid;
}
