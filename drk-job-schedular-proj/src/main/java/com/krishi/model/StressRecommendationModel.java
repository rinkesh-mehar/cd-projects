package com.krishi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.krishi.entity.StressRecommendation;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StressRecommendationModel {

	@JsonProperty
	private Integer ID;

	@JsonProperty
	private Integer CommodityID;
	@JsonProperty
	private Integer StressControlMeasureID;
	@JsonProperty
	private Integer StressID;
	@JsonProperty
	private String Instructions;
	@JsonProperty
	private String AgroChemicalInstructions = null;
	@JsonProperty
	private Integer AgrochemicalID = null;
	@JsonProperty
	private String DosePerAcre = null;
	@JsonProperty
	private Integer PerAcreUomID = null;
	@JsonProperty
	private String WaterPerAcre = null;
	@JsonProperty
	private Integer PerAcreWaterUomID = null;
	@JsonProperty
	private Integer AgrochemApplicationID = null;
	@JsonProperty
	private Integer SowingWeekStart = null;
	@JsonProperty
	private Integer AczID = null;
	@JsonProperty
	private Integer SowingWeekEnd = null;
	@JsonProperty
	private Integer RecomendationID = null;
	@JsonProperty
	private Integer AgrochemicalInstructionID = null;
	@JsonProperty
	private Integer AgrochemApplicationTypeID = null;
	@JsonProperty
	private String Status;

	public Integer getID() {
		return ID;
	}

	public void setID(Integer iD) {
		ID = iD;
	}

	public Integer getSowingWeekStart() {
		return SowingWeekStart;
	}

	public void setSowingWeekStart(Integer sowingWeekStart) {
		SowingWeekStart = sowingWeekStart;
	}

	public Integer getAczID() {
		return AczID;
	}

	public void setAczID(Integer aczID) {
		AczID = aczID;
	}

	public Integer getSowingWeekEnd() {
		return SowingWeekEnd;
	}

	public void setSowingWeekEnd(Integer sowingWeekEnd) {
		SowingWeekEnd = sowingWeekEnd;
	}

	public Integer getRecomendationID() {
		return RecomendationID;
	}

	public void setRecomendationID(Integer recomendationID) {
		RecomendationID = recomendationID;
	}

	public Integer getAgrochemicalInstructionID() {
		return AgrochemicalInstructionID;
	}

	public void setAgrochemicalInstructionID(Integer agrochemicalInstructionID) {
		AgrochemicalInstructionID = agrochemicalInstructionID;
	}

	public Integer getAgrochemApplicationTypeID() {
		return AgrochemApplicationTypeID;
	}

	public void setAgrochemApplicationTypeID(Integer agrochemApplicationTypeID) {
		AgrochemApplicationTypeID = agrochemApplicationTypeID;
	}

	public Integer getCommodityID() {
		return CommodityID;
	}

	public void setCommodityID(Integer commodityID) {
		CommodityID = commodityID;
	}

	public Integer getStressControlMeasureID() {
		return StressControlMeasureID;
	}

	public void setStressControlMeasureID(Integer stressControlMeasureID) {
		StressControlMeasureID = stressControlMeasureID;
	}

	public Integer getStressID() {
		return StressID;
	}

	public void setStressID(Integer stressID) {
		StressID = stressID;
	}

	public String getInstructions() {
		return Instructions;
	}

	public void setInstructions(String instructions) {
		Instructions = instructions;
	}

	public String getAgroChemicalInstructions() {
		return AgroChemicalInstructions;
	}

	public void setAgroChemicalInstructions(String agroChemicalInstructions) {
		AgroChemicalInstructions = agroChemicalInstructions;
	}

	public Integer getAgrochemicalID() {
		return AgrochemicalID;
	}

	public void setAgrochemicalID(Integer agrochemicalID) {
		AgrochemicalID = agrochemicalID;
	}

	public String getDosePerAcre() {
		return DosePerAcre;
	}

	public void setDosePerAcre(String dosePerAcre) {
		DosePerAcre = dosePerAcre;
	}

	public Integer getPerAcreUomID() {
		return PerAcreUomID;
	}

	public void setPerAcreUomID(Integer perAcreUomID) {
		PerAcreUomID = perAcreUomID;
	}

	public String getWaterPerAcre() {
		return WaterPerAcre;
	}

	public void setWaterPerAcre(String waterPerAcre) {
		WaterPerAcre = waterPerAcre;
	}

	public Integer getPerAcreWaterUomID() {
		return PerAcreWaterUomID;
	}

	public void setPerAcreWaterUomID(Integer perAcreWaterUomID) {
		PerAcreWaterUomID = perAcreWaterUomID;
	}

	public Integer getAgrochemApplicationID() {
		return AgrochemApplicationID;
	}

	public void setAgrochemApplicationID(Integer agrochemApplicationID) {
		AgrochemApplicationID = agrochemApplicationID;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public StressRecommendation getEntity() {

		StressRecommendation entity = new StressRecommendation();
		entity.setId(ID);
		entity.setCommodityId(CommodityID);
		entity.setStressId(StressID);
		entity.setControlMeasureId(StressControlMeasureID);
		entity.setInstruction(Instructions);
		entity.setAgrochemicalId(AgrochemicalID);
		entity.setAgrochemicalInstruction(AgroChemicalInstructions);
		entity.setAgrochemicalApplicationId(AgrochemApplicationID);
		entity.setDose(DosePerAcre);
		entity.setDoseUomId(PerAcreUomID);
		entity.setWater(WaterPerAcre);
		entity.setWaterUomId(PerAcreWaterUomID);
		entity.setSowingWeekStart(SowingWeekStart);
		entity.setAczId(AczID);
		entity.setSowingWeekEnd(SowingWeekEnd);
		entity.setRecommendationId(RecomendationID);
		entity.setAgrochemicalInstructionID(AgrochemicalInstructionID);
		entity.setAgrochemApplicationTypeID(AgrochemApplicationTypeID);
		entity.setStatus(Status != null && Status.equalsIgnoreCase("Active") ? 1 : 0);

		return entity;
	}

}
