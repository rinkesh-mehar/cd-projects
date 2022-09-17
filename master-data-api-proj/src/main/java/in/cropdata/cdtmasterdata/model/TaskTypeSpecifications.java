package in.cropdata.cdtmasterdata.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Entity(name = "task_type_specifications")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskTypeSpecifications {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "StateID")
	private Integer stateID; 
	
	@Column(name = "SeasonID")
	private Integer seasonID; 
	
	@Column(name = "CommodityID")
	private Integer commodityID; 
	
	@Column(name = "VarietyID")
	private Integer varietyID; 
	
	@Column(name = "PhenophaseID")
	private Integer phenophaseID; 
	
	@Column(name = "PushBackLimit")
	private Integer pushBackLimit; 
	
	@Column(name = "Priority")
	private Integer priority; 
	
	@Column(name = "TaskTime")
	private Integer taskTime; 
	
	@Column(name = "TaskTypeID")
	private Integer taskTypeID;  
	
	@Column(name = "SpotDependency")
	private String spotDependency; 
	
	@Transient
	private Date updatedAt;
	
	@Transient
	private Date createdAt;
	
	@Column(name = "Status")
	private String status;
	
	@Transient
	private String stateName ;
	
	@Transient
	private String seasonName ;
	
	@Transient
	private String commodityName;
	
	@Transient
	private String varietyName ;
	
	@Transient
	private String phenophaseName ;
	
	@Transient
	private String taskTypeName ;
	
}