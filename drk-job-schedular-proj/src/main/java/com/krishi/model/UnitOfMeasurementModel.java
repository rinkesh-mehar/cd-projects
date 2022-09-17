package com.krishi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.krishi.entity.UnitOfMeasurement;

@JsonIgnoreProperties(ignoreUnknown = true)

public class UnitOfMeasurementModel {

	private static final long serialVersionUID = 1L;
	@JsonProperty
	private int ID;
	@JsonProperty
	private String Name;
	@JsonProperty
	private String Status;

	@JsonProperty
	private String Lbs;

	public UnitOfMeasurementModel() {
		super();
	}

	public UnitOfMeasurementModel(int iD, String name, String lbs, String status) {
		super();
		ID = iD;
		Name = name;
		Lbs = lbs;
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

	public String getLbs() {
		return Lbs;
	}

	public void setLbs(String lbs) {
		Lbs = lbs;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	@Override
	public String toString() {
		return "UnitOfMeasurementModel [ID=" + ID + ", Name=" + Name + ", Status=" + Status + ", Lbs=" + Lbs + "]";
	}

	public UnitOfMeasurement getEntity() {

		UnitOfMeasurement unitOfMeasurementEntity = new UnitOfMeasurement();

		unitOfMeasurementEntity.setStatus(Status != null && Status.equalsIgnoreCase("Active") ? 1 : 0);
		unitOfMeasurementEntity.setId(ID);
		unitOfMeasurementEntity.setName(Name);

		return unitOfMeasurementEntity;
	}

}
