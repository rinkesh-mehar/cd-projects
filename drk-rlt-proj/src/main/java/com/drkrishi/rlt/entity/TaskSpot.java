package com.drkrishi.rlt.entity;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
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
	
	@Column(name = "gstm_spot_id")
	private String gstmSpotId;
	
	@Column(name = "is_benchmark")
	private Integer isBenchmark;
	
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
	
}
