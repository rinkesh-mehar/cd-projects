package in.cropdata.farmers.app.masters.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;


@Data
@Entity
@Table(name="agri_crop_type", schema="cdt_master_data")
public class AgriCropType {

	@Id
	@Column(name = "ID", nullable = false, unique = true)
	private Integer id;
	
	@Column(name="Name")
	private String name;
	
	@Column(name="Status")
	private String status;
}
