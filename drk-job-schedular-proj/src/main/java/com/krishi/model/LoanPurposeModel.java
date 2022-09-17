package com.krishi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.krishi.entity.LoanPurpose;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LoanPurposeModel {

	private static final long serialVersionUID = 1L;

	@JsonProperty
	private int ID;

	@JsonProperty
	private String Name;

	@JsonProperty
	private String Status;

	public LoanPurposeModel() {
		super();
	}

	public LoanPurposeModel(int iD, String name, String status) {
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
		return "LoanPurpose [ID=" + ID + ", Name=" + Name + ", Status=" + Status + "]";
	}

	public LoanPurpose getEntity() {
		LoanPurpose entity = new LoanPurpose();
		entity.setId(ID);
		entity.setName(Name);
		entity.setStatus(Status != null && Status.equalsIgnoreCase("Active") ? 1 : 0);
		return entity;
	}
}
