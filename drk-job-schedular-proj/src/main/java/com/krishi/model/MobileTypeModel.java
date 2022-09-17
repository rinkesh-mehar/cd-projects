package com.krishi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.krishi.entity.MobileType;
import com.krishi.entity.Panchayat;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MobileTypeModel {

	@JsonProperty
	private Integer ID;

	@JsonProperty
	private String MobileType;

	@JsonProperty
	private String Status;

	public MobileTypeModel() {
		super();
	}

	public MobileTypeModel(Integer iD, String mobileType, String status) {
		super();
		ID = iD;
		MobileType = mobileType;
		Status = status;
	}

	public Integer getID() {
		return ID;
	}

	public void setID(Integer iD) {
		ID = iD;
	}

	public String getMobileType() {
		return MobileType;
	}

	public void setMobileType(String mobileType) {
		MobileType = mobileType;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public MobileType getEntity() {
		MobileType p = new MobileType();

		p.setId(ID);
		p.setMobileType(MobileType);
		p.setStatus(Status != null && Status.equalsIgnoreCase("Active") ? 1 : 0);
		p.setStatus(Status != null && Status.equalsIgnoreCase("Active") ? 1 : 0);
		return p;
	}

}
