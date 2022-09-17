package com.krishi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.krishi.entity.GovtOfficialDept;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GovtOfficialDeptModel {

	private static final long serialVersionUID = 1L;
	@JsonProperty
	private int ID;
	@JsonProperty
	private String DepartmentName;
	@JsonProperty
	private String Status;

	public GovtOfficialDeptModel() {
		super();
	}

	public GovtOfficialDeptModel(int iD, String departmentName, String status) {
		super();
		ID = iD;
		DepartmentName = departmentName;
		Status = status;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getDepartmentName() {
		return DepartmentName;
	}

	public void setDepartmentName(String departmentName) {
		DepartmentName = departmentName;
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
		return "GovtOfficialDept [ID=" + ID + ", DepartmentName=" + DepartmentName + ", Status=" + Status + "]";
	}

	public GovtOfficialDept getEntity() {
		GovtOfficialDept govtOfficialDeptEnity = new GovtOfficialDept();
		govtOfficialDeptEnity.setStatus(Status != null && Status.equalsIgnoreCase("Active") ? 1 : 0);
		govtOfficialDeptEnity.setId(ID);
		govtOfficialDeptEnity.setName(DepartmentName);
		return govtOfficialDeptEnity;
	}
}
