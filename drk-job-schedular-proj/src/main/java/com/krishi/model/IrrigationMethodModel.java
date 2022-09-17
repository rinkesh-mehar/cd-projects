package com.krishi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.krishi.entity.IrrigationMethod;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IrrigationMethodModel {

	private static final long serialVersionUID = 1L;
	@JsonProperty
	private int ID;
	@JsonProperty
	private String Name;
	@JsonProperty
	private String Status;

	public IrrigationMethodModel() {
		super();
	}

	public IrrigationMethodModel(int iD, String name, String status) {
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
		return "IrrigationMethod [ID=" + ID + ", Name=" + Name + ", Status=" + Status + "]";
	}

	public IrrigationMethod getEntity() {
		IrrigationMethod irrigationMethodEntity = new IrrigationMethod();
		irrigationMethodEntity.setStatus(Status != null && Status.equalsIgnoreCase("Active") ? 1 : 0);
		irrigationMethodEntity.setId(ID);
		irrigationMethodEntity.setName(Name);
		return irrigationMethodEntity;
	}
}
