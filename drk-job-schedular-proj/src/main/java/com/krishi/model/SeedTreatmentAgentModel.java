package com.krishi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.krishi.entity.ProxyRelationType;
import com.krishi.entity.SeedTreatmentAgent;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SeedTreatmentAgentModel {
	@JsonProperty
	private Integer ID;
	@JsonProperty
	private Integer VarietyID;
	@JsonProperty
	private String Name;
	@JsonProperty
	private Integer Dose;
	@JsonProperty
	private Integer UomID;
	@JsonProperty
	private String Status;

	public Integer getID() {
		return ID;
	}

	public void setID(Integer iD) {
		ID = iD;
	}

	public Integer getVarietyID() {
		return VarietyID;
	}

	public void setVarietyID(Integer varietyID) {
		VarietyID = varietyID;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public Integer getDose() {
		return Dose;
	}

	public void setDose(Integer dose) {
		Dose = dose;
	}

	public Integer getUomID() {
		return UomID;
	}

	public void setUomID(Integer uomID) {
		UomID = uomID;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public SeedTreatmentAgent getEntity() {
		SeedTreatmentAgent entity = new SeedTreatmentAgent();
		entity.setId(ID);
		entity.setVarietyID(VarietyID);
		entity.setName(Name);
		entity.setUomId(UomID);
		entity.setDose(Dose);
		entity.setStatus(Status != null && Status.equalsIgnoreCase("Active") ? 1 : 0);
		return entity;

	}

}
