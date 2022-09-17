package com.krishi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.krishi.model.EntityModel;

@Entity
@Table(name = "task_spot")
public class TaskSpot implements EntityModel {
	
	@Id
	@Column(name = "id")
	private String id;
	
	@Column(name = "task_id")
	private String taskId;
	
	@Column(name = "latitude")
	private Double latitude;
	
	@Column(name = "longitude")
	private Double longitude;
	
	@Column(name="gstm_spot_id")
	private String gstmSpotId;
	
	@Column(name="is_benchmark")
	private Integer isBenchmark;
	
	private Integer isGstmSynced;

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
	
	public String getGstmSpotId() {
		return gstmSpotId;
	}
	public void setGstmSpotId(String gstmSpotId) {
		this.gstmSpotId = gstmSpotId;
	}
	public Integer getIsBenchmark() {
		return isBenchmark;
	}
	public void setIsBenchmark(Integer isBenchmark) {
		this.isBenchmark = isBenchmark;
	}
	public Integer getIsGstmSynced() {
		return isGstmSynced;
	}
	public void setIsGstmSynced(Integer isGstmSynced) {
		this.isGstmSynced = isGstmSynced;
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
