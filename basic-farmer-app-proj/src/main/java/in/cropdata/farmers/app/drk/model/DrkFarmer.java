/**
 * 
 */
package in.cropdata.farmers.app.drk.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author pallavi-waghmare
 *
 */
@Data
@Entity
@Table(name ="farmer",schema= "drkrishi_ts")
@JsonIgnoreProperties(ignoreUnknown = true)
public class DrkFarmer implements Serializable {
	
	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false, unique = true)
	@JsonProperty("farmerId")
	private String id;
	
	@Column(name="primary_mob_number")
	private String primaryMobNumber ;
	
	@Column(name = "auth_token")
	private String authToken;
	
	@Column(name = "farmer_name")
	private String farmerName;
		
	@Column(name = "address_id")
	private Integer addressId;
	
	@Column(name = "village_id")
	@JsonProperty("villageId")
	private Integer villageCode;
	
	@Column(name = "crop_area")
	private Double cropArea;
	
	@Column(name = "latitude")
	private Double latitude;
	
	@Column(name = "longitude")
	private Double longitude;
	
	@Column(name = "farm_size")
	private Double farmSize;
	
	@Column(name = "RegionID")
	private Integer regionID;
}
