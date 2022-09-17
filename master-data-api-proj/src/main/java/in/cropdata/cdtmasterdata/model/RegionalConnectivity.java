/**
 * 
 */
package in.cropdata.cdtmasterdata.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

/**
 * @author cropdata-Aniket Naik
 *
 */
@Data
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "regional_connectivity_time",schema = "cdt_master_data")
public class RegionalConnectivity {

	@Id
	@Column(name = "ID",nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "RegionID")
	private Integer regionId;
	
	@Column(name = "SurfacedProportion")
	private Double surfacedProportion;
	
	@Column(name = "UnsurfacedProportion")
	private Double unsurfacedProportion;
	
	@Column(name = "SurfacedTimeMinPerkm")
	private Double surfacedTimeMin;
	
	@Column(name = "UnsurfacedTimeMinPerKm")
	private Double unsurfacedTimeMin;
	
	@Transient
	private Timestamp createdAt;
	
	@Transient
	private Timestamp updatedAt;
	
	@Column(name = "Status")
	private String status;
	
	
}
