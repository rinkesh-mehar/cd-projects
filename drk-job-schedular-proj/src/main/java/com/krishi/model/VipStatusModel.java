package com.krishi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.krishi.entity.VipStatus;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VipStatusModel {

	@JsonProperty
	private Integer ID;

	@JsonProperty
	private String VipStatus;

	@JsonProperty
	private String Status;

	public Integer getID() {
		return ID;
	}

	public void setID(Integer iD) {
		ID = iD;
	}

	public String getVipStatus() {
		return VipStatus;
	}

	public void setVipStatus(String vipStatus) {
		VipStatus = vipStatus;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public com.krishi.entity.VipStatus getEntity() {
		
		com.krishi.entity.VipStatus v= new VipStatus();
		v.setId(ID);
		v.setVipStatus(VipStatus);
		v.setStatus(Status != null && Status.equalsIgnoreCase("Active") ? 1 : 0);
		
		return v;
	}
	
}
