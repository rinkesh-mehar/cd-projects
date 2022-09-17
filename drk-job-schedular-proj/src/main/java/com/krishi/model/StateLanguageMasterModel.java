package com.krishi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.krishi.entity.StateLanguage;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StateLanguageMasterModel {
	@JsonProperty
	private Integer ID;
	
	@JsonProperty
	private Integer StateCode;
	@JsonProperty
	private Integer LanguageID;
	@JsonProperty
	private String Status;

	public StateLanguageMasterModel() {
		super();
	}

	public StateLanguageMasterModel(Integer iD, Integer stateCode, Integer languageID, String status) {
		super();
		ID = iD;
		StateCode = stateCode;
		LanguageID = languageID;
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

	public Integer getLanguageID() {
		return LanguageID;
	}

	public void setLanguageID(Integer languageID) {
		LanguageID = languageID;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	@Override
	public String toString() {
		return "StateLanguageMasterModel [ID=" + ID + ", StateCode=" + StateCode + ", LanguageID=" + LanguageID + ", Status="
				+ Status + "]";
	}
	
	public StateLanguage getEntity() {
		StateLanguage stateLanguage = new StateLanguage();
		stateLanguage.setId(ID);
		stateLanguage.setCode(StateCode);
		stateLanguage.setLanguageId(LanguageID);
		stateLanguage.setStatus(Status != null && Status.equalsIgnoreCase("Active") ? 1 : 0);
		return stateLanguage;
	}

}
