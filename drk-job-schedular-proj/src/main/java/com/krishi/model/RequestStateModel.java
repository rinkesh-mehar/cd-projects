package com.krishi.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.krishi.entity.StateEntity;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestStateModel implements Serializable {

	private static final long serialVersionUID = 1L;
	@JsonProperty
	private Integer ID;
	@JsonProperty
	private Integer StateCode;
	@JsonProperty
	private Integer CountryCode;
	@JsonProperty
	private String Name;
	@JsonProperty
	private String Status;

	public RequestStateModel() {
		super();
	}

	public RequestStateModel(Integer iD, Integer stateCode, Integer countryCode, String name, String status) {
		super();
		ID = iD;
		StateCode = stateCode;
		CountryCode = countryCode;
		Name = name;
		Status = status;
	}

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

	public Integer getCountryCode() {
		return CountryCode;
	}

	public void setCountryCode(Integer countryCode) {
		CountryCode = countryCode;
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

	@Override
	public String toString() {
		return "RequestStateModel [ID=" + ID + ", StateCode=" + StateCode + ", CountryCode=" + CountryCode + ", Name="
				+ Name + ", Status=" + Status + "]";
	}
	
	public StateEntity getEntity() {
		StateEntity entity = new StateEntity();
		entity.setStatus(Status != null && Status.equalsIgnoreCase("Active") ? 1 : 0);
		entity.setStateId(this.ID);
		entity.setStateName(Name);
		entity.setStateCode(this.StateCode);
		entity.setCountryCode(this.CountryCode);
		return entity;
	}

}
