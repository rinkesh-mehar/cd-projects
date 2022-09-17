// Generated with g9.

package com.drkrishi.rlt.entity;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="case_field_details")
public class CaseFieldDetails implements Serializable {

    /** Primary key. */
    protected static final String PK = "id";

    @Id
    private String id;
    @Column(name="irrigation_source_id", nullable=false, length=50)
    private String irrigationSourceId;
    @Column(name="irrigation_method_id", nullable=false, length=50)
    private String irrigationMethodId;
    @Column(name="number_of_irrigation", nullable=false, precision=10)
    private int numberOfIrrigation;
    @Column(name="week_of_irrigation", nullable=false, precision=10)
    private int weekOfIrrigation;
    @Column(name="year_of_irrigation", nullable=false, precision=10)
    private int yearOfIrrigation;
    @Column(name="fertilizer_id", nullable=false, precision=10)
    private int fertilizerId;
    @Column(name="application_id", nullable=false, precision=10)
    private int applicationId;
    @Column(name="fertilizer_uom_id", nullable=false, precision=10)
    private int fertilizerUomId;
    @Column(name="fertilizer_dose", nullable=false, precision=10)
    private int fertilizerDose;
    @Column(name="fertilizer_split_dose", nullable=false, length=50)
    private String fertilizerSplitDose;
    @Column(name="fertilizer_week_of_applcation", nullable=false, precision=10)
    private int fertilizerWeekOfApplcation;
    @Column(name="fertilizer_year_of_applcation", nullable=false, precision=10)
    private int fertilizerYearOfApplcation;
    @Column(name="seed_treatment", nullable=false, length=3)
    private boolean seedTreatment;
    @Column(name="seed_treatment_agent_id", nullable=false, precision=10)
    private int seedTreatmentAgentId;
    @Column(name="seed_uom_id", nullable=false, precision=10)
    private int seedUomId;
    @Column(name="seed_dose", nullable=false, precision=10)
    private int seedDose;
    @Column(name="pesticides_id", nullable=false, precision=10)
    private int pesticidesId;
    @Column(name="pesticides_uom_id", nullable=false, precision=10)
    private int pesticidesUomId;
    @Column(name="pesticides_dose", nullable=false, precision=10)
    private int pesticidesDose;
    @Column(name="pesticides_week_of_applcation", nullable=false, precision=10)
    private int pesticidesWeekOfApplcation;
    @Column(name="pesticides_year_of_applcation", nullable=false, precision=10)
    private int pesticidesYearOfApplcation;
    @Column(name="case_id", nullable=false, precision=10)
    private String caseId;
        
    @Column(name="agrochemical_brand_id")
    private int agrochemicalBrandId;
    
    @Column(name="agrochemical_uom_id")
    private int agrochemicalUomId;
    
    @Column(name="agrochemical_dose")
    private int agrochemicalDose;
    
    @Column(name="agrochemical_week_of_applcation")
    private int agrochemicalWeekOfApplcation;
    
    @Column(name="agrochemical_year_of_applcation")
    private int agrochemicalYearOfApplcation;

    /** Default constructor. */
    public CaseFieldDetails() {
        super();
    }

    /**
     * Access method for id.
     *
     * @return the current value of id
     */
    public String getId() {
        return id;
    }

    /**
     * Setter method for id.
     *
     * @param aId the new value for id
     */
    public void setId(String aId) {
        id = aId;
    }

    /**
     * Access method for irrigationSourceId.
     *
     * @return the current value of irrigationSourceId
     */
    public String getIrrigationSourceId() {
        return irrigationSourceId;
    }

    /**
     * Setter method for irrigationSourceId.
     *
     * @param aIrrigationSourceId the new value for irrigationSourceId
     */
    public void setIrrigationSourceId(String aIrrigationSourceId) {
        irrigationSourceId = aIrrigationSourceId;
    }

    /**
     * Access method for irrigationMethodId.
     *
     * @return the current value of irrigationMethodId
     */
    public String getIrrigationMethodId() {
        return irrigationMethodId;
    }

    /**
     * Setter method for irrigationMethodId.
     *
     * @param aIrrigationMethodId the new value for irrigationMethodId
     */
    public void setIrrigationMethodId(String aIrrigationMethodId) {
        irrigationMethodId = aIrrigationMethodId;
    }

    /**
     * Access method for numberOfIrrigation.
     *
     * @return the current value of numberOfIrrigation
     */
    public int getNumberOfIrrigation() {
        return numberOfIrrigation;
    }

	/**
     * Setter method for numberOfIrrigation.
     *
     * @param aNumberOfIrrigation the new value for numberOfIrrigation
     */
    public void setNumberOfIrrigation(int aNumberOfIrrigation) {
        numberOfIrrigation = aNumberOfIrrigation;
    }

    /**
     * Access method for weekOfIrrigation.
     *
     * @return the current value of weekOfIrrigation
     */
    public int getWeekOfIrrigation() {
        return weekOfIrrigation;
    }

    /**
     * Setter method for weekOfIrrigation.
     *
     * @param aWeekOfIrrigation the new value for weekOfIrrigation
     */
    public void setWeekOfIrrigation(int aWeekOfIrrigation) {
        weekOfIrrigation = aWeekOfIrrigation;
    }

    /**
     * Access method for yearOfIrrigation.
     *
     * @return the current value of yearOfIrrigation
     */
    public int getYearOfIrrigation() {
        return yearOfIrrigation;
    }

    /**
     * Setter method for yearOfIrrigation.
     *
     * @param aYearOfIrrigation the new value for yearOfIrrigation
     */
    public void setYearOfIrrigation(int aYearOfIrrigation) {
        yearOfIrrigation = aYearOfIrrigation;
    }

    /**
     * Access method for fertilizerId.
     *
     * @return the current value of fertilizerId
     */
    public int getFertilizerId() {
        return fertilizerId;
    }

    /**
     * Setter method for fertilizerId.
     *
     * @param aFertilizerId the new value for fertilizerId
     */
    public void setFertilizerId(int aFertilizerId) {
        fertilizerId = aFertilizerId;
    }

    /**
     * Access method for applicationId.
     *
     * @return the current value of applicationId
     */
    public int getApplicationId() {
        return applicationId;
    }

    /**
     * Setter method for applicationId.
     *
     * @param aApplicationId the new value for applicationId
     */
    public void setApplicationId(int aApplicationId) {
        applicationId = aApplicationId;
    }

    /**
     * Access method for fertilizerUomId.
     *
     * @return the current value of fertilizerUomId
     */
    public int getFertilizerUomId() {
        return fertilizerUomId;
    }

    /**
     * Setter method for fertilizerUomId.
     *
     * @param aFertilizerUomId the new value for fertilizerUomId
     */
    public void setFertilizerUomId(int aFertilizerUomId) {
        fertilizerUomId = aFertilizerUomId;
    }

    /**
     * Access method for fertilizerDose.
     *
     * @return the current value of fertilizerDose
     */
    public int getFertilizerDose() {
        return fertilizerDose;
    }

    /**
     * Setter method for fertilizerDose.
     *
     * @param aFertilizerDose the new value for fertilizerDose
     */
    public void setFertilizerDose(int aFertilizerDose) {
        fertilizerDose = aFertilizerDose;
    }

    /**
     * Access method for fertilizerSplitDose.
     *
     * @return the current value of fertilizerSplitDose
     */
    public String getFertilizerSplitDose() {
        return fertilizerSplitDose;
    }

    /**
     * Setter method for fertilizerSplitDose.
     *
     * @param aFertilizerSplitDose the new value for fertilizerSplitDose
     */
    public void setFertilizerSplitDose(String aFertilizerSplitDose) {
        fertilizerSplitDose = aFertilizerSplitDose;
    }

    /**
     * Access method for fertilizerWeekOfApplcation.
     *
     * @return the current value of fertilizerWeekOfApplcation
     */
    public int getFertilizerWeekOfApplcation() {
        return fertilizerWeekOfApplcation;
    }

    /**
     * Setter method for fertilizerWeekOfApplcation.
     *
     * @param aFertilizerWeekOfApplcation the new value for fertilizerWeekOfApplcation
     */
    public void setFertilizerWeekOfApplcation(int aFertilizerWeekOfApplcation) {
        fertilizerWeekOfApplcation = aFertilizerWeekOfApplcation;
    }
    

    public int getAgrochemicalBrandId() {
		return agrochemicalBrandId;
	}

	public void setAgrochemicalBrandId(int agrochemicalBrandId) {
		this.agrochemicalBrandId = agrochemicalBrandId;
	}

	public int getAgrochemicalUomId() {
		return agrochemicalUomId;
	}

	public void setAgrochemicalUomId(int agrochemicalUomId) {
		this.agrochemicalUomId = agrochemicalUomId;
	}

	public int getAgrochemicalDose() {
		return agrochemicalDose;
	}

	public void setAgrochemicalDose(int agrochemicalDose) {
		this.agrochemicalDose = agrochemicalDose;
	}

	public int getAgrochemicalWeekOfApplcation() {
		return agrochemicalWeekOfApplcation;
	}

	public void setAgrochemicalWeekOfApplcation(int agrochemicalWeekOfApplcation) {
		this.agrochemicalWeekOfApplcation = agrochemicalWeekOfApplcation;
	}

	public int getAgrochemicalYearOfApplcation() {
		return agrochemicalYearOfApplcation;
	}

	public void setAgrochemicalYearOfApplcation(int agrochemicalYearOfApplcation) {
		this.agrochemicalYearOfApplcation = agrochemicalYearOfApplcation;
	}

	/**
     * Access method for fertilizerYearOfApplcation.
     *
     * @return the current value of fertilizerYearOfApplcation
     */
    public int getFertilizerYearOfApplcation() {
        return fertilizerYearOfApplcation;
    }

    /**
     * Setter method for fertilizerYearOfApplcation.
     *
     * @param aFertilizerYearOfApplcation the new value for fertilizerYearOfApplcation
     */
    public void setFertilizerYearOfApplcation(int aFertilizerYearOfApplcation) {
        fertilizerYearOfApplcation = aFertilizerYearOfApplcation;
    }

    /**
     * Access method for seedTreatment.
     *
     * @return true if and only if seedTreatment is currently true
     */
    public boolean getSeedTreatment() {
        return seedTreatment;
    }

    /**
     * Setter method for seedTreatment.
     *
     * @param aSeedTreatment the new value for seedTreatment
     */
    public void setSeedTreatment(boolean aSeedTreatment) {
        seedTreatment = aSeedTreatment;
    }

    /**
     * Access method for seedTreatmentAgentId.
     *
     * @return the current value of seedTreatmentAgentId
     */
    public int getSeedTreatmentAgentId() {
        return seedTreatmentAgentId;
    }

    /**
     * Setter method for seedTreatmentAgentId.
     *
     * @param aSeedTreatmentAgentId the new value for seedTreatmentAgentId
     */
    public void setSeedTreatmentAgentId(int aSeedTreatmentAgentId) {
        seedTreatmentAgentId = aSeedTreatmentAgentId;
    }

    /**
     * Access method for seedUomId.
     *
     * @return the current value of seedUomId
     */
    public int getSeedUomId() {
        return seedUomId;
    }

    /**
     * Setter method for seedUomId.
     *
     * @param aSeedUomId the new value for seedUomId
     */
    public void setSeedUomId(int aSeedUomId) {
        seedUomId = aSeedUomId;
    }

    /**
     * Access method for seedDose.
     *
     * @return the current value of seedDose
     */
    public int getSeedDose() {
        return seedDose;
    }

    /**
     * Setter method for seedDose.
     *
     * @param aSeedDose the new value for seedDose
     */
    public void setSeedDose(int aSeedDose) {
        seedDose = aSeedDose;
    }

    /**
     * Access method for pesticidesId.
     *
     * @return the current value of pesticidesId
     */
    public int getPesticidesId() {
        return pesticidesId;
    }

    /**
     * Setter method for pesticidesId.
     *
     * @param aPesticidesId the new value for pesticidesId
     */
    public void setPesticidesId(int aPesticidesId) {
        pesticidesId = aPesticidesId;
    }

    /**
     * Access method for pesticidesUomId.
     *
     * @return the current value of pesticidesUomId
     */
    public int getPesticidesUomId() {
        return pesticidesUomId;
    }

    /**
     * Setter method for pesticidesUomId.
     *
     * @param aPesticidesUomId the new value for pesticidesUomId
     */
    public void setPesticidesUomId(int aPesticidesUomId) {
        pesticidesUomId = aPesticidesUomId;
    }

    /**
     * Access method for pesticidesDose.
     *
     * @return the current value of pesticidesDose
     */
    public int getPesticidesDose() {
        return pesticidesDose;
    }

    /**
     * Setter method for pesticidesDose.
     *
     * @param aPesticidesDose the new value for pesticidesDose
     */
    public void setPesticidesDose(int aPesticidesDose) {
        pesticidesDose = aPesticidesDose;
    }

    /**
     * Access method for pesticidesWeekOfApplcation.
     *
     * @return the current value of pesticidesWeekOfApplcation
     */
    public int getPesticidesWeekOfApplcation() {
        return pesticidesWeekOfApplcation;
    }

    /**
     * Setter method for pesticidesWeekOfApplcation.
     *
     * @param aPesticidesWeekOfApplcation the new value for pesticidesWeekOfApplcation
     */
    public void setPesticidesWeekOfApplcation(int aPesticidesWeekOfApplcation) {
        pesticidesWeekOfApplcation = aPesticidesWeekOfApplcation;
    }

    /**
     * Access method for pesticidesYearOfApplcation.
     *
     * @return the current value of pesticidesYearOfApplcation
     */
    public int getPesticidesYearOfApplcation() {
        return pesticidesYearOfApplcation;
    }

    /**
     * Setter method for pesticidesYearOfApplcation.
     *
     * @param aPesticidesYearOfApplcation the new value for pesticidesYearOfApplcation
     */
    public void setPesticidesYearOfApplcation(int aPesticidesYearOfApplcation) {
        pesticidesYearOfApplcation = aPesticidesYearOfApplcation;
    }

    /**
     * Access method for caseId.
     *
     * @return the current value of caseId
     */
    public String getCaseId() {
        return caseId;
    }

    /**
     * Setter method for caseId.
     *
     * @param aCaseId the new value for caseId
     */
    public void setCaseId(String aCaseId) {
        caseId = aCaseId;
    }
}
