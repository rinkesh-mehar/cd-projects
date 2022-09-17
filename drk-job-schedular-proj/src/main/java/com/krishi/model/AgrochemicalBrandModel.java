package com.krishi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.krishi.entity.AgrochemicalBrand;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AgrochemicalBrandModel {
	
	@JsonProperty
	private Integer ID;
	
	@JsonProperty
	private String BrandName;
	
	@JsonProperty
	private Integer CompanyID;
	
	@JsonProperty
	private Integer AgrochemicalID;
	
	@JsonProperty
	private String AgrochemicalStatus;
	
	@JsonProperty
	private String Status;

	public Integer getID() {
		return ID;
	}

	public void setID(Integer iD) {
		ID = iD;
	}

	public String getBrandName() {
		return BrandName;
	}

	public void setBrandName(String brandName) {
		BrandName = brandName;
	}

	public Integer getCompanyID() {
		return CompanyID;
	}

	public void setCompanyID(Integer companyID) {
		CompanyID = companyID;
	}

	public Integer getAgrochemicalID() {
		return AgrochemicalID;
	}

	public void setAgrochemicalID(Integer agrochemicalID) {
		AgrochemicalID = agrochemicalID;
	}

	public String getAgrochemicalStatus() {
		return AgrochemicalStatus;
	}

	public void setAgrochemicalStatus(String agrochemicalStatus) {
		AgrochemicalStatus = agrochemicalStatus;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}
	
	public AgrochemicalBrand getEntity() {
		AgrochemicalBrand entity = new AgrochemicalBrand();
		entity.setId(ID);
		entity.setName(BrandName);
		entity.setAgrochemicalCompanyId(CompanyID);
		entity.setAgrochemicalId(AgrochemicalID);
		entity.setStatus(Status != null && Status.equalsIgnoreCase("Active") ? 1 : 0);
		return entity;
	}

}
