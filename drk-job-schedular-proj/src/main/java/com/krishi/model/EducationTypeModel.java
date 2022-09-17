package com.krishi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.krishi.entity.EducationType;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EducationTypeModel {

	private static final long serialVersionUID = 1L;
	@JsonProperty
	private int ID;
	@JsonProperty
	private String Name;
	@JsonProperty
	private String Status;

	public EducationTypeModel() {
		super();
	}

	public EducationTypeModel(int iD, String name, String status) {
		super();
		ID = iD;
		Name = name;
		Status = status;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
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

	@Override
	public String toString() {
		return "EducationType [ID=" + ID + ", Name=" + Name + ", Status=" + Status + "]";
	}

	public EducationType getEntity() {
		EducationType entity = new EducationType();
		entity.setId(ID);
		entity.setStatus(Status != null && Status.equalsIgnoreCase("Active") ? 1 : 0);
		entity.setName(this.Name);
		return entity;
	}
	
}
