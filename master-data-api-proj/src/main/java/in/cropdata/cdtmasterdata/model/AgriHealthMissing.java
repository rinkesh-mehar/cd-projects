/**
 * 
 */
package in.cropdata.cdtmasterdata.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.Data;

/**
 * @author cropdata-Aniket Naik
 *
 */

@Data
@Entity(name = "agri_health_missing")
public class AgriHealthMissing {
	
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	@Column(name = "ID")
	private int Id;
	
	@Column(name = "CommodityID")
	private int commodityId;
	
	@Transient
	private String commodity;
	
	@Column(name = "PhenophaseID")
	private int phenophaseId;
	
	@Transient
	private String phenophase;
	
	@Column(name = "HealthParameterID")
	private int healthParameterId;
	
	@Transient
	private String healthParameter;
	
	@Column(name = "Specification")
	private String specification;
	
//	@Column(name = "UpdatedAt")
	@Transient
	private Date updatedAt;
	
//	@Column(name = "CreatedAt")
	@Transient
	private Date createdAt;
	
	@Column(name = "Status")
	private String status;

}
