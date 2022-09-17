package com.krishi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CompanyModel {

	private static final long serialVersionUID = 1L;
	@JsonProperty
	private int ID;
	@JsonProperty
	private String Name;
	@JsonProperty
	private String Description;
	@JsonProperty
	private String Status;

	public CompanyModel() {
		super();
	}

	public CompanyModel(int iD, String name, String description, String status) {
		super();
		ID = iD;
		Name = name;
		Description = description;
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

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	@Override
	public String toString() {
		return "CompanyModel [ID=" + ID + ", Name=" + Name + ", Description=" + Description + ", Status=" + Status
				+ "]";
	}

}
