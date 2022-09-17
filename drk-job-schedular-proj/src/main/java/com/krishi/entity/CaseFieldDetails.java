package com.krishi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.krishi.model.EntityModel;

import java.io.Serializable;

@Entity
@Table(name="case_field_details")
public class CaseFieldDetails implements Serializable, EntityModel {

    /** Primary key. */
    protected static final String PK = "id";

    @Id
    private String id;
    @Column(name="irrigation_source_id")
    private String irrigationSourceId;
    @Column(name="irrigation_method_id")
    private String irrigationMethodId;
    @Column(name="number_of_irrigation")
    private int numberOfIrrigation;
    @Column(name="week_of_irrigation")
    private int weekOfIrrigation;
    @Column(name="year_of_irrigation")
    private int yearOfIrrigation;
    @Column(name="fertilizer_id")
    private int fertilizerId;
    @Column(name="application_id")
    private int applicationId;
    @Column(name="fertilizer_uom_id")
    private int fertilizerUomId;
    @Column(name="fertilizer_dose")
    private int fertilizerDose;
    @Column(name="fertilizer_split_dose")
    private String fertilizerSplitDose;
    @Column(name="fertilizer_week_of_applcation")
    private int fertilizerWeekOfApplcation;
    @Column(name="fertilizer_year_of_applcation")
    private int fertilizerYearOfApplcation;
    @Column(name="seed_treatment")
    private boolean seedTreatment;
    @Column(name="seed_treatment_agent_id")
    private int seedTreatmentAgentId;
    @Column(name="seed_uom_id")
    private int seedUomId;
    @Column(name="seed_dose")
    private int seedDose;
    
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
    
    @Column(name="case_id")
    private String caseId;

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

    /**
     * Compares the key for this instance with another CaseFieldDetails.
     *
     * @param other The object to compare to
     * @return True if other object is instance of class CaseFieldDetails and the key objects are equal
     */
    private boolean equalKeys(Object other) {
        if (this==other) {
            return true;
        }
        if (!(other instanceof CaseFieldDetails)) {
            return false;
        }
        CaseFieldDetails that = (CaseFieldDetails) other;
        if (this.getId() != that.getId()) {
            return false;
        }
        return true;
    }

    /**
     * Compares this instance with another CaseFieldDetails.
     *
     * @param other The object to compare to
     * @return True if the objects are the same
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof CaseFieldDetails)) return false;
        return this.equalKeys(other) && ((CaseFieldDetails)other).equalKeys(this);
    }

   

}
