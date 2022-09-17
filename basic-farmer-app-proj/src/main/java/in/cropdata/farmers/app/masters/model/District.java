package in.cropdata.farmers.app.masters.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="geo_district", schema="cdt_master_data")
public class District {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false, unique = true)
	private Integer id;
	
	@Column(name="DistrictCode")
	private Integer districtCode;

	@Column(name="Name")
	private String name;

	@Column(name="StateCode")
	private Integer stateCode;
	
	@Transient
	private String neighbourDistricts;
}
