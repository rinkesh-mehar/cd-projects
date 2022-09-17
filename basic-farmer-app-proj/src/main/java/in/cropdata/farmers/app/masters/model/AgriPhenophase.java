package in.cropdata.farmers.app.masters.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name="agri_phenophase", schema="cdt_master_data")
public class AgriPhenophase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;

	@Column(name = "Name")
	private String name;

//	@Column(name = "UpdatedAt")
	@Transient
	private Date updatedAt;
	
//	@Column(name = "CreatedAt")
	@Transient
	private Date createdAt;
	
	@Column(name = "Status")
	private String status;

}
