package com.krishi.fls.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "task_spot")
public class TaskSpot {
	
	@Id
	@Column(name = "id")
	private String id;
	
	@Column(name = "task_id")
	private String taskId;
	
	@Column(name = "latitude")
	private Double latitude;
	
	@Column(name = "longitude")
	private Double longitude;

	@Column(name = "current_yield")
	private Double currentYield;

	@Column(name = "standard_yield")
	private Double standardYield;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getCurrentYield() {
		return currentYield;
	}

	public void setCurrentYield(Double currentYield) {
		this.currentYield = currentYield;
	}

	public Double getStandardYield() {
		return standardYield;
	}

	public void setStandardYield(Double standardYield) {
		this.standardYield = standardYield;
	}
}
