/**
 * 
 */
package com.krishi.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author CDT-Ujwal
 *
 */

@Entity
@Table(name = "task_type_specifications")
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskTypeSpecifications implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
//	@JsonProperty("ID")
	private Integer id;

	@Column(name = "state_id")
	@JsonProperty("StateCode")
	private Integer stateId;

	@Column(name = "acz_id")
	@JsonProperty("AczID")
	private Integer aczID;

	@Column(name = "commodity_id")
	@JsonProperty("CommodityID")
	private Integer commodityID;

	@Column(name = "sowing_start_week")
	@JsonProperty("SowingWeekStart")
	private Integer sowingWeekStart;
	
	@Column(name = "sowing_end_week")
	@JsonProperty("SowingWeekEnd")
	private Integer sowingWeekEnd;
	
	@Column(name = "phenophase_id")
	@JsonProperty("PhenophaseID")
	private Integer phenophaseID;

	@Column(name = "push_back_limit")
	@JsonProperty("PushBackLimit")
	private Integer pushBackLimit;

	@Column(name = "priority")
	@JsonProperty("Priority")
	private Integer priority;

	@Column(name = "task_time")
	@JsonProperty("TaskTime")
	private Integer taskTime;

	@Column(name = "task_type_id")
	@JsonProperty("TaskTypeID")
	private Integer taskTypeID;

	@Column(name = "spot_dependency")
	@JsonProperty("SpotDependency")
	private String spotDependency;

	@Column(name = "status")
	@JsonProperty("Status")
	private Integer status;

	@Transient
	private Date updatedAt;

	@Transient
	private Date createdAt;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStateId() {
		return stateId;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}

	public Integer getAczID() {
		return aczID;
	}

	public void setAczID(Integer aczID) {
		this.aczID = aczID;
	}

	public Integer getSowingWeekStart() {
		return sowingWeekStart;
	}

	public void setSowingWeekStart(Integer sowingWeekStart) {
		this.sowingWeekStart = sowingWeekStart;
	}

	public Integer getSowingWeekEnd() {
		return sowingWeekEnd;
	}

	public void setSowingWeekEnd(Integer sowingWeekEnd) {
		this.sowingWeekEnd = sowingWeekEnd;
	}

	public Integer getCommodityID() {
		return commodityID;
	}

	public void setCommodityID(Integer commodityID) {
		this.commodityID = commodityID;
	}

	public Integer getPhenophaseID() {
		return phenophaseID;
	}

	public void setPhenophaseID(Integer phenophaseID) {
		this.phenophaseID = phenophaseID;
	}

	public Integer getPushBackLimit() {
		return pushBackLimit;
	}

	public void setPushBackLimit(Integer pushBackLimit) {
		this.pushBackLimit = pushBackLimit;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Integer getTaskTime() {
		return taskTime;
	}

	public void setTaskTime(Integer taskTime) {
		this.taskTime = taskTime;
	}

	public Integer getTaskTypeID() {
		return taskTypeID;
	}

	public void setTaskTypeID(Integer taskTypeID) {
		this.taskTypeID = taskTypeID;
	}

	public String getSpotDependency() {
		return spotDependency;
	}

	public void setSpotDependency(String spotDependency) {
		this.spotDependency = spotDependency;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setStatus(String status) {
		this.status = ((status != null && status.equalsIgnoreCase("Active")) ? 1 : 0);
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((commodityID == null) ? 0 : commodityID.hashCode());
		result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((phenophaseID == null) ? 0 : phenophaseID.hashCode());
		result = prime * result + ((priority == null) ? 0 : priority.hashCode());
		result = prime * result + ((pushBackLimit == null) ? 0 : pushBackLimit.hashCode());
		result = prime * result + ((stateId == null) ? 0 : stateId.hashCode());
		result = prime * result + ((aczID == null) ? 0 : aczID.hashCode());
		result = prime * result + ((sowingWeekStart == null) ? 0 : sowingWeekStart.hashCode());
		result = prime * result + ((sowingWeekEnd == null) ? 0 : sowingWeekEnd.hashCode());
		result = prime * result + ((spotDependency == null) ? 0 : spotDependency.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((taskTime == null) ? 0 : taskTime.hashCode());
		result = prime * result + ((taskTypeID == null) ? 0 : taskTypeID.hashCode());
		result = prime * result + ((updatedAt == null) ? 0 : updatedAt.hashCode());
		return result;
	}

	public TaskTypeSpecifications(Integer id, Integer zonalCommodityID, Integer aczID, Integer commodityID,
			Integer sowingWeekStart, Integer sowingWeekEnd, Integer phenophaseID, Integer pushBackLimit,
			Integer priority, Integer taskTime, Integer taskTypeID, String spotDependency, Integer status,
			Date updatedAt, Date createdAt) {
		super();
		this.id = id;
		this.aczID = aczID;
		this.commodityID = commodityID;
		this.sowingWeekStart = sowingWeekStart;
		this.sowingWeekEnd = sowingWeekEnd;
		this.phenophaseID = phenophaseID;
		this.pushBackLimit = pushBackLimit;
		this.priority = priority;
		this.taskTime = taskTime;
		this.taskTypeID = taskTypeID;
		this.spotDependency = spotDependency;
		this.status = status;
		this.updatedAt = updatedAt;
		this.createdAt = createdAt;
	}

	public TaskTypeSpecifications() {
		super();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TaskTypeSpecifications other = (TaskTypeSpecifications) obj;
		if (commodityID == null) {
			if (other.commodityID != null)
				return false;
		} else if (!commodityID.equals(other.commodityID))
			return false;
		if (stateId == null) {
			if (other.stateId != null)
				return false;
		} else if (!stateId.equals(other.stateId))
			return false;
		if (aczID == null) {
			if (other.aczID != null)
				return false;
		} else if (!aczID.equals(other.aczID))
			return false;
		if (sowingWeekStart == null) {
			if (other.sowingWeekStart != null)
				return false;
		} else if (!sowingWeekStart.equals(other.sowingWeekStart))
			return false;
		if (sowingWeekEnd == null) {
			if (other.sowingWeekEnd != null)
				return false;
		} else if (!sowingWeekEnd.equals(other.sowingWeekEnd))
			return false;
		if (createdAt == null) {
			if (other.createdAt != null)
				return false;
		} else if (!createdAt.equals(other.createdAt))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (phenophaseID == null) {
			if (other.phenophaseID != null)
				return false;
		} else if (!phenophaseID.equals(other.phenophaseID))
			return false;
		if (priority == null) {
			if (other.priority != null)
				return false;
		} else if (!priority.equals(other.priority))
			return false;
		if (pushBackLimit == null) {
			if (other.pushBackLimit != null)
				return false;
		} else if (!pushBackLimit.equals(other.pushBackLimit))
			return false;
		if (spotDependency == null) {
			if (other.spotDependency != null)
				return false;
		} else if (!spotDependency.equals(other.spotDependency))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (taskTime == null) {
			if (other.taskTime != null)
				return false;
		} else if (!taskTime.equals(other.taskTime))
			return false;
		if (taskTypeID == null) {
			if (other.taskTypeID != null)
				return false;
		} else if (!taskTypeID.equals(other.taskTypeID))
			return false;
		if (updatedAt == null) {
			if (other.updatedAt != null)
				return false;
		} else if (!updatedAt.equals(other.updatedAt))
			return false;
		return true;
	}
}
