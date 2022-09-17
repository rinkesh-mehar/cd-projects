/**
 * 
 */
package in.cropdata.farmers.app.drk.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

/**
 * @author pallavi-waghmare
 *
 */
@Data
@Entity
@Table(name="farmer_device_tokens",schema="drkrishi_ts")
public class FarmerDeviceToken {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false, unique = true)
	private Integer ID;
	
	@Column(name="FarmerID")
	private String farmerID;

	@Column(name="DeviceToken")
	private String deviceToken;
	
	@Transient
	private Timestamp createdAt;
	
	@Transient
	private Timestamp UpdatedAt;
	
	@Column(name="Status")
	private String status;

}
