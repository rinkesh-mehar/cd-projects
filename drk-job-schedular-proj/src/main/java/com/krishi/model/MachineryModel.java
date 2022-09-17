package com.krishi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.krishi.entity.Machinery;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MachineryModel {

	private static final long serialVersionUID = 1L;
	@JsonProperty
	private int ID;
	@JsonProperty
	private String Name;
	@JsonProperty
	private String Status;

	public MachineryModel() {
		super();
	}

	public MachineryModel(int iD, String name, String status) {
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
		return "MachineryModel [ID=" + ID + ", Name=" + Name + ", Status=" + Status + "]";
	}

	public Machinery getEntity() {
		Machinery entity = new Machinery();
		entity.setId(ID);
		entity.setName(Name);
		entity.setStatus(Status != null && Status.equalsIgnoreCase("Active") ? 1 : 0);	
		
		return entity;
	}
}
