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
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

/**
 * @author Vivek Gajbhiye
 *
 */
@Data
@Entity
@Table(name ="tracking_queue",schema= "drkrishi_ts")
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrackingQueue {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@JsonAlias("regionID")
	@Column(name = "region_id")
	private Integer regionId;
	
	@Transient
//	@Column(name = "right_id")
	private String rightId;
	
	@Column(name = "device_token")
	private String deviceToken;
	
	@JsonAlias("longitude_farmer")
	@Column(name = "longitude")
	private Double longitude;
	
	@JsonAlias("latitude_farmer")
	@Column(name = "latitude")
	private Double latitude;
	
	@JsonAlias("ZLTileID_Farmer")
	@Column(name = "current_tile_id")
	private String currentTileId;
	
//	@Column(name = "distance")
	@Transient
	private Double distance;
	
	@Column(name = "status")
	private String status;
	
	@Transient
	private String mobileNo;

}
