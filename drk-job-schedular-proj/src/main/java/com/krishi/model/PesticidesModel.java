package com.krishi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.krishi.entity.Pesticides;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PesticidesModel {
	private static final long serialVersionUID = 1L;

	@JsonProperty
	int ID;
	@JsonProperty
	String BrandName;
	@JsonProperty
	String CompanyID;
	@JsonProperty
	String StressTypeID;
	@JsonProperty
	int AgrochemicalTypeID;
	@JsonProperty
	String RemedialStatus;
	@JsonProperty
	String Status;

	public PesticidesModel() {
		super();
	}

	public PesticidesModel(int iD, String brandName, String companyID, String stressTypeID, int agrochemicalTypeID,
			String remedialStatus, String status) {
		super();
		ID = iD;
		BrandName = brandName;
		CompanyID = companyID;
		StressTypeID = stressTypeID;
		AgrochemicalTypeID = agrochemicalTypeID;
		RemedialStatus = remedialStatus;
		Status = status;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getBrandName() {
		return BrandName;
	}

	public void setBrandName(String brandName) {
		BrandName = brandName;
	}

	public String getCompanyID() {
		return CompanyID;
	}

	public void setCompanyID(String companyID) {
		CompanyID = companyID;
	}

	public String getStressTypeID() {
		return StressTypeID;
	}

	public void setStressTypeID(String stressTypeID) {
		StressTypeID = stressTypeID;
	}

	public int getAgrochemicalTypeID() {
		return AgrochemicalTypeID;
	}

	public void setAgrochemicalTypeID(int agrochemicalTypeID) {
		AgrochemicalTypeID = agrochemicalTypeID;
	}

	public String getRemedialStatus() {
		return RemedialStatus;
	}

	public void setRemedialStatus(String remedialStatus) {
		RemedialStatus = remedialStatus;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	@Override
	public String toString() {
		return "PesticidesModel [ID=" + ID + ", BrandName=" + BrandName + ", CompanyID=" + CompanyID + ", StressTypeID="
				+ StressTypeID + ", AgrochemicalTypeID=" + AgrochemicalTypeID + ", RemedialStatus=" + RemedialStatus
				+ ", Status=" + Status + "]";
	}

	
	public Pesticides getEntity() {
		Pesticides pesticidesEntity = new Pesticides();
		pesticidesEntity.setId(ID);
		pesticidesEntity.setName(BrandName);
		return pesticidesEntity;
	}
}
