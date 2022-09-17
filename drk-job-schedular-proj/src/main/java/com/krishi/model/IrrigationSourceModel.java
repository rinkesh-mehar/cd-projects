package com.krishi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.krishi.entity.IrrigationSource;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IrrigationSourceModel {

	private static final long serialVersionUID = 1L;
	@JsonProperty
	private int ID;
	@JsonProperty
	private String Name;
	@JsonProperty
	private String Status;

	public IrrigationSourceModel() {
		super();
	}

	public IrrigationSourceModel(int iD, String name, String status) {
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "IrrigationSource [ID=" + ID + ", Name=" + Name + ", Status=" + Status + "]";
	}

	public IrrigationSource getEntity() {
		IrrigationSource irrigationSourceEntity = new IrrigationSource();
		irrigationSourceEntity.setStatus(Status != null && Status.equalsIgnoreCase("Active") ? 1 : 0);
		irrigationSourceEntity.setId(ID);
		irrigationSourceEntity.setName(Name);
		return irrigationSourceEntity;
	}
}
