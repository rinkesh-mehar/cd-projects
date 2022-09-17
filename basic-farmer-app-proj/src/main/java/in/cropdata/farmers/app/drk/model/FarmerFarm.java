/**
 * 
 */
package in.cropdata.farmers.app.drk.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author RinkeshKM
 * @Date 05/02/21
 */
@Data
@Entity
@Table(name = "farmer_farm", schema = "drkrishi_ts")
public class FarmerFarm {

	@Id
	@Column(name = "ID",nullable = false)
	@JsonProperty("ID")
	private String id;

	@Column(name = "farmer_id")
	private String farmerID;

	@Column(name = "has_own_land")
	private Integer hasOwnLand;

	@Column(name = "has_leased_land")
	private Integer hasLeasedLand;

	@Transient
	private Timestamp createdAt;
	
	@Transient
	private Timestamp updatedAt;
}
