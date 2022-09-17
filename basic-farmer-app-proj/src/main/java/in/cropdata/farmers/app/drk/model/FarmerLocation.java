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

@Data
@Entity
@Table(name ="farmer_location",schema= "drkrishi_ts")
@JsonIgnoreProperties(ignoreUnknown = true)
public class FarmerLocation implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false, unique = true)
	private String id;
	
	@Column(name = "FarmerID")
	private String farmerID;
	
	@Column(name = "Latitude")
	private Double latitude;
	
	@Column(name = "Longitude")
	private Double longitude;
}
