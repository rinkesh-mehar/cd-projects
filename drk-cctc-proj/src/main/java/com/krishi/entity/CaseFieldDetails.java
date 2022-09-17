// Generated with g9.

package com.krishi.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="case_field_details")
public class CaseFieldDetails {

    @Id
    private Integer id;
    
    private String irrigationSourceId;

    private String irrigationMethodId;

    private Integer numberOfIrrigation;

    private Integer weekOfIrrigation;
    
    private Integer fertilizerId;
    
    private Integer fertilizerWeekOfApplcation;
    
    private Integer fertilizerDose;
    
    private String fertilizerSplitDose;
    
    private Integer fertilizerUomId;
    
    private Integer seedTreatment;
    
    private Integer seedTreatmentAgentId;
    
    private Integer seedUomId;
    
    private Integer seedDose;
    
    private Integer agrochemicalBrandId;
    
    private Integer agrochemicalDose;
    
    private Integer agrochemicalUomId;
    
    private Integer agrochemicalWeekOfApplcation;
    
    
    private String caseId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Integer getFertilizerId() {
		return fertilizerId;
	}

	public void setFertilizerId(Integer fertilizerId) {
		this.fertilizerId = fertilizerId;
	}

	public Integer getFertilizerWeekOfApplcation() {
		return fertilizerWeekOfApplcation;
	}

	public void setFertilizerWeekOfApplcation(Integer fertilizerWeekOfApplcation) {
		this.fertilizerWeekOfApplcation = fertilizerWeekOfApplcation;
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

	public Integer getFertilizerUomId() {
		return fertilizerUomId;
	}

	public void setFertilizerUomId(Integer fertilizerUomId) {
		this.fertilizerUomId = fertilizerUomId;
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public Integer getSeedTreatment() {
		return seedTreatment;
	}

	public void setSeedTreatment(Integer seedTreatment) {
		this.seedTreatment = seedTreatment;
	}

	public Integer getSeedTreatmentAgentId() {
		return seedTreatmentAgentId;
	}

	public void setSeedTreatmentAgentId(Integer seedTreatmentAgentId) {
		this.seedTreatmentAgentId = seedTreatmentAgentId;
	}

	public Integer getSeedUomId() {
		return seedUomId;
	}

	public void setSeedUomId(Integer seedUomId) {
		this.seedUomId = seedUomId;
	}

	public Integer getSeedDose() {
		return seedDose;
	}

	public void setSeedDose(Integer seedDose) {
		this.seedDose = seedDose;
	}

	public Integer getAgrochemicalBrandId() {
		return agrochemicalBrandId;
	}

	public void setAgrochemicalBrandId(Integer agrochemicalBrandId) {
		this.agrochemicalBrandId = agrochemicalBrandId;
	}

	public Integer getAgrochemicalDose() {
		return agrochemicalDose;
	}

	public void setAgrochemicalDose(Integer agrochemicalDose) {
		this.agrochemicalDose = agrochemicalDose;
	}

	public Integer getAgrochemicalUomId() {
		return agrochemicalUomId;
	}

	public void setAgrochemicalUomId(Integer agrochemicalUomId) {
		this.agrochemicalUomId = agrochemicalUomId;
	}

	public Integer getAgrochemicalWeekOfApplcation() {
		return agrochemicalWeekOfApplcation;
	}

	public void setAgrochemicalWeekOfApplcation(Integer agrochemicalWeekOfApplcation) {
		this.agrochemicalWeekOfApplcation = agrochemicalWeekOfApplcation;
	}

}
