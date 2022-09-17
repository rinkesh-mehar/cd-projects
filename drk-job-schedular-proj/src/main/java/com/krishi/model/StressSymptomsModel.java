package com.krishi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.krishi.entity.AgrochemicalBrand;
import com.krishi.entity.StressSymptoms;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StressSymptomsModel {

	@JsonProperty
	private Integer ID;

	@JsonProperty
	private Integer CommodityID;

	@JsonProperty
	private Integer PhenophaseID;

	@JsonProperty
	private Integer PlantPartID;

	@JsonProperty
	private Integer StressTypeID;

	@JsonProperty
	private Integer StressID;

	@JsonProperty
	private Integer StressStageID;

	@JsonProperty
	private String Symptom;

	@JsonProperty
	private String Status;

	/** added sync processes - Ujwal : Start */
	/** added genericImageID variable */
	@JsonProperty
	private Integer GenericImageID;
	
	/** added genericImage variable */
	@JsonProperty
	private String GenericImage;
	
	
	public Integer getGenericImageID() {
		return GenericImageID;
	}

	public void setGenericImageID(Integer genericImageID) {
		GenericImageID = genericImageID;
	}

	public String getGenericImage() {
		return GenericImage;
	}

	public void setGenericImage(String genericImage) {
		GenericImage = genericImage;
	}
	/** added sync processes - Ujwal : Start */
	
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

	public Integer getPlantPartID() {
		return PlantPartID;
	}

	public void setPlantPartID(Integer plantPartID) {
		PlantPartID = plantPartID;
	}

	public Integer getStressTypeID() {
		return StressTypeID;
	}

	public void setStressTypeID(Integer stressTypeID) {
		StressTypeID = stressTypeID;
	}

	public Integer getStressID() {
		return StressID;
	}

	public void setStressID(Integer stressID) {
		StressID = stressID;
	}

	public Integer getStressStageID() {
		return StressStageID;
	}

	public void setStressStageID(Integer stressStageID) {
		StressStageID = stressStageID;
	}

	public String getSymptom() {
		return Symptom;
	}

	public void setSymptom(String symptom) {
		Symptom = symptom;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public StressSymptoms getEntity() {

		StressSymptoms entity = new StressSymptoms();
		entity.setId(ID);
		entity.setCommodityId(CommodityID);
//		entity.setPhenophaseId(PhenophaseID);
		entity.setPlantPartId(PlantPartID);
		entity.setSymptomDesc(Symptom);
		entity.setImportant(false);
		entity.setStressId(StressID);
		entity.setStatus(Status != null && Status.equalsIgnoreCase("Active") ? 1 : 0);
		entity.setImage(GenericImage);
		entity.setStressTypeId(StressTypeID);
//		entity.setStressStageId(StressStageID);
		entity.setGenericImageId(GenericImageID);
		return entity;
	}

}
