package com.krishi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.krishi.entity.StressSeverityControlMeasures;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StressSeverityControlMeasuresModel {

	@JsonProperty
	private Integer ID;

	@JsonProperty
	private Integer StressID;

	@JsonProperty
	private Integer StressSeverityID;

	@JsonProperty
	private Integer StressControlMeasureID;

	@JsonProperty
	private String Status;

	public Integer getID() {
		return ID;
	}

	public void setID(Integer iD) {
		ID = iD;
	}

	public Integer getStressID() {
		return StressID;
	}

	public void setStressID(Integer stressID) {
		StressID = stressID;
	}

	public Integer getStressSeverityID() {
		return StressSeverityID;
	}

	public void setStressSeverityID(Integer stressSeverityID) {
		StressSeverityID = stressSeverityID;
	}

	public Integer getStressControlMeasureID() {
		return StressControlMeasureID;
	}

	public void setStressControlMeasureID(Integer stressControlMeasureID) {
		StressControlMeasureID = stressControlMeasureID;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public StressSeverityControlMeasures getEntity() {
		StressSeverityControlMeasures entity = new StressSeverityControlMeasures();

		entity.setId(ID);
		entity.setSeverityId(StressSeverityID);
		entity.setStatus(Status != null && Status.equalsIgnoreCase("Active") ? 1 : 0);
		entity.setStress_id(StressID);
		entity.setStressControlMeasureID(StressControlMeasureID);

		return entity;

	}

}