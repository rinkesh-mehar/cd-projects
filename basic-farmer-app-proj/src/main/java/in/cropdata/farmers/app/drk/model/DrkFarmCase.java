/**
 * 
 */
package in.cropdata.farmers.app.drk.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

/**
 * @author pallavi-waghmare
 *
 */
@Data
@Entity
@Table(name ="farm_case",schema= "drkrishi_ts")
public class DrkFarmCase {
	
	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("ID")
	@Column(name = "ID", nullable = false, unique = true)
	private String id;
	
	@Column(name="farm_id")
	private String farmId ;
	
	@Column(name = "nmd")
	private Date nmd;
	
	@Column(name = "case_sample_status")
	private String caseSampleStatus;
	
	@Column(name = "due_amount")
	private Double dueAmount;

	@Column(name = "payment_status")
	private String paymentStatus;

	@Column(name = "RegionID")
	private Integer regionID;

	@Column(name = "SourceID")
	private Integer sourceID;

	@Column(name = "stress_index")
	private Integer stressIndex;

	@Column(name = "estimated_production")
	private Integer estimatedProduction;

	@Column(name = "estimated_value")
	private Integer estimatedValue;

	@Column(name = "current_production")
	private Integer currentProduction;

	@Column(name = "current_value")
	private Integer currentValue;

	@Column(name = "case_yar_percent")
	private Integer caseYarPercent;

	@Column(name = "crop_type")
	private Integer cropType;
}
