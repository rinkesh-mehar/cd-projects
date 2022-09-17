package com.drkrishi.rlt.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "stress_severity_control_measures")
public class StressSeverityControlMeasures {
	
	@Id
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "stress_id")
	private Integer stressId;
	
	@Column(name = "severity_id")
	private Integer severityId;
	
	@Column(name = "control_measure_id")
	private Integer controlMeasureId;
	
	@Column(name = "status")
	private Integer status;

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

	public Integer getControlMeasureId() {
		return controlMeasureId;
	}

	public void setControlMeasureId(Integer controlMeasureId) {
		this.controlMeasureId = controlMeasureId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
