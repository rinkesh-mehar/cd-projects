/**
 * 
 */
package in.cropdata.farmers.app.masters.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * @author pallavi-waghmare
 *
 */
@Data
@Entity
@Table(name="farmer_farm_ownership_type", schema="cdt_master_data")
public class FarmerFarmOwnershipType {
	
	@Id
	@Column(name = "ID", nullable = false, unique = true)
	private Integer id;
	
	
	@Column(name="Name")
	private String name;

}
