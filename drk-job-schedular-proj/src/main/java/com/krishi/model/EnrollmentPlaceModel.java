package com.krishi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.krishi.entity.EducationType;
import com.krishi.entity.EnrollmentPlace;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EnrollmentPlaceModel {

	@JsonProperty
	private Integer ID;

	@JsonProperty
	private String Name;

	@JsonProperty
	private String Status;

	public EnrollmentPlaceModel() {
		super();
	}

	public EnrollmentPlaceModel(Integer iD, String name, String status) {
		super();
		ID = iD;
		Name = name;
		Status = status;
	}

	public Integer getID() {
		return ID;
	}

	public void setID(Integer iD) {
		ID = iD;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public EnrollmentPlace getEntity() {
		EnrollmentPlace entity = new EnrollmentPlace();
		entity.setID(ID);
		entity.setStatus(Status != null && Status.equalsIgnoreCase("Active") ? 1 : 0);
		entity.setName(this.Name);
		return entity;
	}
	
}
