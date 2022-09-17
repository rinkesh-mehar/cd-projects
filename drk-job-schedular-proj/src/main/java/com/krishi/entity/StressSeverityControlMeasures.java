package com.krishi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "stress_severity_control_measures")
public class StressSeverityControlMeasures {

	@Id
	@Column(name = "id")
	private Integer id;

	@Column(name = "stress_id")
	private float stress_id;

	@Column(name = "severity_id")
	private float severityId;

	@Column(name = "control_measure_id")
	private float StressControlMeasureID;

	@Column(name = "status")
	private Integer Status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public float getStress_id() {
		return stress_id;
	}

	public void setStress_id(float stress_id) {
		this.stress_id = stress_id;
	}

	public float getSeverityId() {
		return severityId;
	}

	public void setSeverityId(float severityId) {
		this.severityId = severityId;
	}

	public float getStressControlMeasureID() {
		return StressControlMeasureID;
	}

	public void setStressControlMeasureID(float stressControlMeasureID) {
		StressControlMeasureID = stressControlMeasureID;
	}

	public Integer getStatus() {
		return Status;
	}

	public void setStatus(Integer status) {
		Status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Status == null) ? 0 : Status.hashCode());
		result = prime * result + Float.floatToIntBits(StressControlMeasureID);
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + Float.floatToIntBits(severityId);
		result = prime * result + Float.floatToIntBits(stress_id);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StressSeverityControlMeasures other = (StressSeverityControlMeasures) obj;
		if (Status == null) {
			if (other.Status != null)
				return false;
		} else if (!Status.equals(other.Status))
			return false;
		if (Float.floatToIntBits(StressControlMeasureID) != Float.floatToIntBits(other.StressControlMeasureID))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (Float.floatToIntBits(severityId) != Float.floatToIntBits(other.severityId))
			return false;
		if (Float.floatToIntBits(stress_id) != Float.floatToIntBits(other.stress_id))
			return false;
		return true;
	}

}
