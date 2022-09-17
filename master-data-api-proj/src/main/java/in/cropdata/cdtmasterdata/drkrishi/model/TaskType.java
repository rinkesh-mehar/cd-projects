/**
 * 
 */
package in.cropdata.cdtmasterdata.drkrishi.model;

import java.util.Date;

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
@Table(name = "task_type", schema ="drkrishi")
public class TaskType {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "name")
	private String name; 
	
	@Column(name = "duration")
	private Integer duration; 
	
	@Column(name = "parent_task_type_id")
	private Integer parentTaskTypeId; 
	
	@Column(name = "user_roles")
	private String userRoles; 
	
	@Column(name = "RegionID")
	private Integer regionId; 
	
	@Transient
	private Date createdAt; 
	
	@Transient
	private Date updatedAt; 
	
	@Column(name = "SourceID")
	private Integer sourceId; 

	

}
