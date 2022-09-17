package com.krishi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.krishi.entity.KycDocType;

@JsonIgnoreProperties(ignoreUnknown = true)

public class KycDocTypeModel {

	private static final long serialVersionUID = 1L;
	@JsonProperty
	private int ID;
	@JsonProperty
	private String Name;
	@JsonProperty
	private String Status;

	public KycDocTypeModel() {
		super();
	}

	public KycDocTypeModel(int iD, String name, String status) {
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
		return "KycDocType [ID=" + ID + ", Name=" + Name + ", Status=" + Status + "]";
	}

	public KycDocType getEntity() {

		KycDocType entity = new KycDocType();
		entity.setId(ID);
		entity.setName(Name);
		entity.setStatus(Status != null && Status.equalsIgnoreCase("Active") ? 1 : 0);

		return entity;
	}
}
