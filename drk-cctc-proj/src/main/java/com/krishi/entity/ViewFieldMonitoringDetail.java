package com.krishi.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="view_field_monitoring_detail")
public class ViewFieldMonitoringDetail {
	
	@Id
	private String taskId;
	
	private String entityId;
	
	private Integer taskTypeId;
	
	private Date taskDate;
	
	private String cropName;
	
	private String varietyName;
	
	private Double cropArea;
	
//	private String seasonName;
	
	private Integer sowingWeek;
	
	private Integer sowingYear;
	
	private String seedName;
	
	private Boolean seedsSampleReceived;
	
	private Double seedsRates;
	
	private String seedUnitName;
	
	private Double spacingRow;
	
	private Double spacingPlant;
	
	private String irrigationSourceId;
	
	private String irrigationMethodId;
	
	private Integer numberOfIrrigation;
	
	private Integer weekOfIrrigation;
	
	private Integer yearOfIrrigation;
	
	private String fertilizerName;
	
	private String fertilizerTypeOfApplication;
	
	private String fertilizerUnitName;
	
	private Integer fertilizerDose;
	
	private String fertilizerSplitDose;
	
	private Integer fertilizerWeekOfApplcation;
	
	private Integer fertilizerYearOfApplcation;
	
	private Boolean seedTreatment;
	
	private Integer seedTreatmentAgentId;
	
	private String seedTreatmentUnitName;
	
	private Integer seedTreatmentDose;
	
	private String agrochemicalName;
	
	private String agrochemicalBrandName;
	
	private Integer agrochemicalDose;
	
	private String agrochemicalUnitName;
	
	private Integer agrochemicalWeekOfApplcation;
	
	private Integer agrochemicalYearOfApplcation;
	
	private String farmerPrimaryMobNumber;
	
	private String farmerAlternativeMobNumber;
	
	private Date nextMonitoringDate;

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public Date getTaskDate() {
		return taskDate;
	}

	public void setTaskDate(Date taskDate) {
		this.taskDate = taskDate;
	}

	public String getCropName() {
		return cropName;
	}

	public void setCropName(String cropName) {
		this.cropName = cropName;
	}

	public String getVarietyName() {
		return varietyName;
	}

	public void setVarietyName(String varietyName) {
		this.varietyName = varietyName;
	}

	public Double getCropArea() {
		return cropArea;
	}

	public void setCropArea(Double cropArea) {
		this.cropArea = cropArea;
	}

//	public String getSeasonName() {
//		return seasonName;
//	}
//
//	public void setSeasonName(String seasonName) {
//		this.seasonName = seasonName;
//	}

	public Integer getSowingWeek() {
		return sowingWeek;
	}

	public void setSowingWeek(Integer sowingWeek) {
		this.sowingWeek = sowingWeek;
	}

	public String getSeedName() {
		return seedName;
	}

	public void setSeedName(String seedName) {
		this.seedName = seedName;
	}

	public Boolean getSeedsSampleReceived() {
		return seedsSampleReceived;
	}

	public void setSeedsSampleReceived(Boolean seedsSampleReceived) {
		this.seedsSampleReceived = seedsSampleReceived;
	}

	public Double getSeedsRates() {
		return seedsRates;
	}

	public void setSeedsRates(Double seedsRates) {
		this.seedsRates = seedsRates;
	}

	public String getSeedUnitName() {
		return seedUnitName;
	}

	public void setSeedUnitName(String seedUnitName) {
		this.seedUnitName = seedUnitName;
	}

	public Double getSpacingRow() {
		return spacingRow;
	}

	public void setSpacingRow(Double spacingRow) {
		this.spacingRow = spacingRow;
	}

	public Double getSpacingPlant() {
		return spacingPlant;
	}

	public void setSpacingPlant(Double spacingPlant) {
		this.spacingPlant = spacingPlant;
	}

	public String getIrrigationSourceId() {
		return irrigationSourceId;
	}

	public void setIrrigationSourceId(String irrigationSourceId) {
		this.irrigationSourceId = irrigationSourceId;
	}

	public String getIrrigationMethodId() {
		return irrigationMethodId;
	}

	public void setIrrigationMethodId(String irrigationMethodId) {
		this.irrigationMethodId = irrigationMethodId;
	}

	public Integer getNumberOfIrrigation() {
		return numberOfIrrigation;
	}

	public void setNumberOfIrrigation(Integer numberOfIrrigation) {
		this.numberOfIrrigation = numberOfIrrigation;
	}

	public Integer getWeekOfIrrigation() {
		return weekOfIrrigation;
	}

	public void setWeekOfIrrigation(Integer weekOfIrrigation) {
		this.weekOfIrrigation = weekOfIrrigation;
	}

	public String getFertilizerName() {
		return fertilizerName;
	}

	public void setFertilizerName(String fertilizerName) {
		this.fertilizerName = fertilizerName;
	}

	public String getFertilizerTypeOfApplication() {
		return fertilizerTypeOfApplication;
	}

	public void setFertilizerTypeOfApplication(String fertilizerTypeOfApplication) {
		this.fertilizerTypeOfApplication = fertilizerTypeOfApplication;
	}

	public String getFertilizerUnitName() {
		return fertilizerUnitName;
	}

	public void setFertilizerUnitName(String fertilizerUnitName) {
		this.fertilizerUnitName = fertilizerUnitName;
	}

	public Integer getFertilizerDose() {
		return fertilizerDose;
	}

	public void setFertilizerDose(Integer fertilizerDose) {
		this.fertilizerDose = fertilizerDose;
	}

	public String getFertilizerSplitDose() {
		return fertilizerSplitDose;
	}

	public void setFertilizerSplitDose(String fertilizerSplitDose) {
		this.fertilizerSplitDose = fertilizerSplitDose;
	}

	public Integer getFertilizerWeekOfApplcation() {
		return fertilizerWeekOfApplcation;
	}

	public void setFertilizerWeekOfApplcation(Integer fertilizerWeekOfApplcation) {
		this.fertilizerWeekOfApplcation = fertilizerWeekOfApplcation;
	}

	public Boolean getSeedTreatment() {
		return seedTreatment;
	}

	public void setSeedTreatment(Boolean seedTreatment) {
		this.seedTreatment = seedTreatment;
	}

	public Integer getSeedTreatmentAgentId() {
		return seedTreatmentAgentId;
	}

	public void setSeedTreatmentAgentId(Integer seedTreatmentAgentId) {
		this.seedTreatmentAgentId = seedTreatmentAgentId;
	}

	public String getSeedTreatmentUnitName() {
		return seedTreatmentUnitName;
	}

	public void setSeedTreatmentUnitName(String seedTreatmentUnitName) {
		this.seedTreatmentUnitName = seedTreatmentUnitName;
	}

	public Integer getSeedTreatmentDose() {
		return seedTreatmentDose;
	}

	public void setSeedTreatmentDose(Integer seedTreatmentDose) {
		this.seedTreatmentDose = seedTreatmentDose;
	}

	public String getAgrochemicalName() {
		return agrochemicalName;
	}

	public void setAgrochemicalName(String agrochemicalName) {
		this.agrochemicalName = agrochemicalName;
	}

	public String getAgrochemicalBrandName() {
		return agrochemicalBrandName;
	}

	public void setAgrochemicalBrandName(String agrochemicalBrandName) {
		this.agrochemicalBrandName = agrochemicalBrandName;
	}

	public Integer getAgrochemicalDose() {
		return agrochemicalDose;
	}

	public void setAgrochemicalDose(Integer agrochemicalDose) {
		this.agrochemicalDose = agrochemicalDose;
	}

	public String getAgrochemicalUnitName() {
		return agrochemicalUnitName;
	}

	public void setAgrochemicalUnitName(String agrochemicalUnitName) {
		this.agrochemicalUnitName = agrochemicalUnitName;
	}

	public Integer getAgrochemicalWeekOfApplcation() {
		return agrochemicalWeekOfApplcation;
	}

	public void setAgrochemicalWeekOfApplcation(Integer agrochemicalWeekOfApplcation) {
		this.agrochemicalWeekOfApplcation = agrochemicalWeekOfApplcation;
	}

	public String getFarmerPrimaryMobNumber() {
		return farmerPrimaryMobNumber;
	}

	public void setFarmerPrimaryMobNumber(String farmerPrimaryMobNumber) {
		this.farmerPrimaryMobNumber = farmerPrimaryMobNumber;
	}

	public String getFarmerAlternativeMobNumber() {
		return farmerAlternativeMobNumber;
	}

	public void setFarmerAlternativeMobNumber(String farmerAlternativeMobNumber) {
		this.farmerAlternativeMobNumber = farmerAlternativeMobNumber;
	}

	public Date getNextMonitoringDate() {
		return nextMonitoringDate;
	}

	public void setNextMonitoringDate(Date nextMonitoringDate) {
		this.nextMonitoringDate = nextMonitoringDate;
	}

	public Integer getSowingYear() {
		return sowingYear;
	}

	public void setSowingYear(Integer sowingYear) {
		this.sowingYear = sowingYear;
	}

	public Integer getYearOfIrrigation() {
		return yearOfIrrigation;
	}

	public void setYearOfIrrigation(Integer yearOfIrrigation) {
		this.yearOfIrrigation = yearOfIrrigation;
	}

	public Integer getFertilizerYearOfApplcation() {
		return fertilizerYearOfApplcation;
	}

	public void setFertilizerYearOfApplcation(Integer fertilizerYearOfApplcation) {
		this.fertilizerYearOfApplcation = fertilizerYearOfApplcation;
	}

	public Integer getAgrochemicalYearOfApplcation() {
		return agrochemicalYearOfApplcation;
	}

	public void setAgrochemicalYearOfApplcation(Integer agrochemicalYearOfApplcation) {
		this.agrochemicalYearOfApplcation = agrochemicalYearOfApplcation;
	}

	public Integer getTaskTypeId() {
		return taskTypeId;
	}

	public void setTaskTypeId(Integer taskTypeId) {
		this.taskTypeId = taskTypeId;
	}
	
	

}
