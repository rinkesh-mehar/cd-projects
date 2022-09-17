package in.cropdata.farmers.app.masters.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="agri_source_of_water", schema="cdt_master_data")
public class IrrigationSource {
	
	@Id
	@Column(name = "ID", nullable = false, unique = true)
	private Integer id;
	
	@Column(name="Name")
	private String name;

}
