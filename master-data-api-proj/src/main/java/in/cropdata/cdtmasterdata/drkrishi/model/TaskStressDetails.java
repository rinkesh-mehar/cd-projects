/**
 * 
 */
package in.cropdata.cdtmasterdata.drkrishi.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

/**
 * @author cropdata-Aniket Naik
 *
 */
@Data
@Entity
@Table(name = "task_stress_details", schema = "drkrishi")
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class TaskStressDetails {

	@Id
	@Column(name = "id", nullable = false)
	private String id;

	@Column(name = "task_id")
	private String taskID;

	@Column(name = "symptom_id")
	private Integer symptomID;

	@Column(name = "is_verified")
	private String isVerified;

	@Transient
	private Date createdAt;

	@Transient
	private Date updatedAt;

	@Column(name = "SourceID")
	private Integer sourceId;

	@Column(name = "RegionID")
	private Integer regionId;


}
