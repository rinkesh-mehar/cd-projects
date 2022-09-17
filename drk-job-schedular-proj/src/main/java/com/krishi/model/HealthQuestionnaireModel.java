package com.krishi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.krishi.entity.HealthQuestionnaire;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HealthQuestionnaireModel {

	@JsonProperty
	private Integer ID;

	@JsonProperty
	private Integer CommodityID;

	@JsonProperty
	private Integer PhenophaseID;

	@JsonProperty
	private String HealthParameter;

	@JsonProperty
	private String Specification;

	@JsonProperty
	private String Status;

	public Integer getID() {
		return ID;
	}

	public void setID(Integer iD) {
		ID = iD;
	}

	public Integer getCommodityID() {
		return CommodityID;
	}

	public void setCommodityID(Integer commodityID) {
		CommodityID = commodityID;
	}

	public Integer getPhenophaseID() {
		return PhenophaseID;
	}

	public void setPhenophaseID(Integer phenophaseID) {
		PhenophaseID = phenophaseID;
	}

	public String getHealthParameter() {
		return HealthParameter;
	}

	public void setHealthParameter(String healthParameter) {
		HealthParameter = healthParameter;
	}

	public String getSpecification() {
		return Specification;
	}

	public void setSpecification(String specification) {
		Specification = specification;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public HealthQuestionnaire getEntity() {

		HealthQuestionnaire entity = new HealthQuestionnaire();
		entity.setId(ID);
		entity.setCommodityId(CommodityID);
		// defult select
		entity.setInputType("Select");
		entity.setInputValues(Specification);
		entity.setName(HealthParameter);
		entity.setPhenophaseId(PhenophaseID);
		entity.setStatus(Status != null && Status.equalsIgnoreCase("Active") ? 1 : 0);
		return entity;

	}

}
