package com.krishi.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.krishi.entity.District;


@JsonIgnoreProperties(ignoreUnknown = true)
public class DistrictModel implements Serializable {
	
	
	
	
	private static final long serialVersionUID = 1L;
	@JsonProperty
	private Integer ID;
	@JsonProperty
	private Integer StateCode;
	@JsonProperty
	private Integer DistrictCode;
	@JsonProperty
	private String Name;
	@JsonProperty
	private String Status;
	
	public Integer getID() {
		return ID;
	}
	public void setID(Integer iD) {
		ID = iD;
	}
	public Integer getStateCode() {
		return StateCode;
	}
	public void setStateCode(Integer stateCode) {
		StateCode = stateCode;
	}
	public Integer getDistrictCode() {
		return DistrictCode;
	}
	public void setDistrictCode(Integer districtCode) {
		DistrictCode = districtCode;
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
	
	public District getEntity() {
		District districtEntity = new District();
		districtEntity.setStatus(Status != null && Status.equalsIgnoreCase("Active") ? 1 : 0);
		districtEntity.setId(ID);
		districtEntity.setName(Name);
		districtEntity.setDistrictCode(DistrictCode);
		districtEntity.setStateId(StateCode);
		return districtEntity;
	}


}
