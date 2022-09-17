package com.krishi.fls.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "stress_severity_control_measures")
public class StressSeverityControlMeasures {

	@Id
	@Column(name = "id")
	private Integer id;

	@Column(name = "stress_id")
	private Integer stressId;

	@Column(name = "severity_id")
	private Integer severityId;

	@Column(name = "control_measure_id")
	private Integer StressControlMeasureID;

	@Column(name = "status")
	private Integer Status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStressId() {
		return stressId;
	}

	public void setStressId(Integer stressId) {
		this.stressId = stressId;
	}

	public Integer getSeverityId() {
		return severityId;
	}

	public void setSeverityId(Integer severityId) {
		this.severityId = severityId;
	}

	public Integer getStressControlMeasureID() {
		return StressControlMeasureID;
	}

	public void setStressControlMeasureID(Integer stressControlMeasureID) {
		StressControlMeasureID = stressControlMeasureID;
	}

	public Integer getStatus() {
		return Status;
	}

	public void setStatus(Integer status) {
		Status = status;
	}

}
