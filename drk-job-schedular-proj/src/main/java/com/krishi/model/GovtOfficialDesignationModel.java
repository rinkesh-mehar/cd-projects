package com.krishi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.krishi.entity.GovtOfficialDept;
import com.krishi.entity.GovtOfficialDesignation;

public class GovtOfficialDesignationModel {

	private static final long serialVersionUID = 1L;

	@JsonProperty
	int ID;
	@JsonProperty
	String Name;

	@JsonProperty
	Integer DepartmentID;
	@JsonProperty
	String Status;

	public GovtOfficialDesignationModel() {
		super();
	}

	public GovtOfficialDesignationModel(int iD, String name, String status) {
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

	public Integer getDepartmentID() {
		return DepartmentID;
	}

	public void setDepartmentID(Integer departmentID) {
		DepartmentID = departmentID;
	}

	@Override
	public String toString() {
		return "GovtOfficialDesignationModel [ID=" + ID + ", Name=" + Name + ", Status=" + Status + "]";
	}

	public GovtOfficialDesignation getEntity() {
		GovtOfficialDesignation govtOfficialDesignationEntity = new GovtOfficialDesignation();
		govtOfficialDesignationEntity.setId(ID);
		govtOfficialDesignationEntity.setName(Name);
		govtOfficialDesignationEntity.setDeptId(DepartmentID);
		govtOfficialDesignationEntity.setStatus(Status != null && Status.equalsIgnoreCase("Active") ? 1 : 0);
		return govtOfficialDesignationEntity;
		
	}
}
