package com.krishi.entity;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.krishi.model.EntityModel;

@Entity
@Table(name="farmer_general_info")
public class FarmerGeneralInfo implements Serializable, EntityModel {

    public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	/** Primary key. */
    protected static final String PK = "id";

    @Id
    @Column(name="id")
    private String id;
    @Column(name="matrial_status", nullable=false)
    private int matrialStatus;
    @Column(name="farmer_id")
    private String farmerId;
    @Column(length=50)
    private String language;
    @Column(name="house_type")
    private int houseType;
    @Column(name="education_type_id")
    private int educationTypeId;
    @Column(name="enrollment_place_id")
    private int enrollmentPlaceId;
    @Column(name="dependents_count")
    private int dependentsCount;
    @Column(length=20)
    private String email;
    @Column(name="has_kcc", length=3)
    private boolean hasKcc;
    
	/** added annual income - Ujwal : Start */
    @Column(name = "annual_income")
    private Double annualIncome;
    
    public Double getAnnualIncome() {
		return annualIncome;
	}
    
	public void setAnnualIncome(Double annualIncome) {
		this.annualIncome = annualIncome;
	}
	/** added annual income - Ujwal : End */
	
	/** Default constructor. */
    public FarmerGeneralInfo() {
        super();
    }
    /**
     * Access method for matrialStatus.
     *
     * @return the current value of matrialStatus
     */
    public int getMatrialStatus() {
        return matrialStatus;
    }

    /**
     * Setter method for matrialStatus.
     *
     * @param aMatrialStatus the new value for matrialStatus
     */
    public void setMatrialStatus(int aMatrialStatus) {
        matrialStatus = aMatrialStatus;
    }

    /**
     * Access method for farmerId.
     *
     * @return the current value of farmerId
     */
    public String getFarmerId() {
        return farmerId;
    }

    /**
     * Setter method for farmerId.
     *
     * @param aFarmerId the new value for farmerId
     */
    public void setFarmerId(String aFarmerId) {
        farmerId = aFarmerId;
    }

    /**
     * Access method for language.
     *
     * @return the current value of language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Setter method for language.
     *
     * @param aLanguage the new value for language
     */
    public void setLanguage(String aLanguage) {
        language = aLanguage;
    }

    /**
     * Access method for houseType.
     *
     * @return the current value of houseType
     */
    public int getHouseType() {
        return houseType;
    }

    /**
     * Setter method for houseType.
     *
     * @param aHouseType the new value for houseType
     */
    public void setHouseType(int aHouseType) {
        houseType = aHouseType;
    }

    /**
     * Access method for educationTypeId.
     *
     * @return the current value of educationTypeId
     */
    public int getEducationTypeId() {
        return educationTypeId;
    }

    /**
     * Setter method for educationTypeId.
     *
     * @param aEducationTypeId the new value for educationTypeId
     */
    public void setEducationTypeId(int aEducationTypeId) {
        educationTypeId = aEducationTypeId;
    }

    /**
     * Access method for enrollmentPlaceId.
     *
     * @return the current value of enrollmentPlaceId
     */
    public int getEnrollmentPlaceId() {
        return enrollmentPlaceId;
    }

    /**
     * Setter method for enrollmentPlaceId.
     *
     * @param aEnrollmentPlaceId the new value for enrollmentPlaceId
     */
    public void setEnrollmentPlaceId(int aEnrollmentPlaceId) {
        enrollmentPlaceId = aEnrollmentPlaceId;
    }

    /**
     * Access method for dependentsCount.
     *
     * @return the current value of dependentsCount
     */
    public int getDependentsCount() {
        return dependentsCount;
    }

    /**
     * Setter method for dependentsCount.
     *
     * @param aDependentsCount the new value for dependentsCount
     */
    public void setDependentsCount(int aDependentsCount) {
        dependentsCount = aDependentsCount;
    }

    /**
     * Access method for email.
     *
     * @return the current value of email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter method for email.
     *
     * @param aEmail the new value for email
     */
    public void setEmail(String aEmail) {
        email = aEmail;
    }

    /**
     * Access method for hasKcc.
     *
     * @return true if and only if hasKcc is currently true
     */
    public boolean getHasKcc() {
        return hasKcc;
    }

    /**
     * Setter method for hasKcc.
     *
     * @param aHasKcc the new value for hasKcc
     */
    public void setHasKcc(boolean aHasKcc) {
        hasKcc = aHasKcc;
    }

  
   



}
