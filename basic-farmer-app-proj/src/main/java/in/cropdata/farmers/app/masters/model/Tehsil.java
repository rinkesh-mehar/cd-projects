/**
 * 
 */
package in.cropdata.farmers.app.masters.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * @author cropdata-Aniket Naik
 *
 */
@Data
@Entity
@Table(name = "geo_tehsil",schema = "cdt_master_data")
public class Tehsil {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false, unique = true)
	private Integer ID;
	
	@Column(name = "TehsilCode")
	private Integer teshilCode;
	
	@Column(name = "Name")
	private String name;
	
	@Column(name = "DistrictCode")
	private Integer districtCode;
	
}
