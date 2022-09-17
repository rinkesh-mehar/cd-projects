package com.krishi.fls.entity;
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

@Entity
@Table(name="farmer_insurance_info")
public class FarmerInsuranceInfo implements Serializable {

    /** Primary key. */
    protected static final String PK = "id";

    @Id
    @Column(name="id")
    private String id;
    @Column(name="farmer_id", precision=10)
    private String farmerId;
    @Column(name="life_insurance_company_id", precision=10)
    private int lifeInsuranceCompanyId;
    @Column(name="health_insurance_company_id", precision=10)
    private int healthInsuranceCompanyId;
    @Column(name="crop_insurance_company_id", precision=10)
    private int cropInsuranceCompanyId;
    
    @Column(name="life_ins_sa")
    private Float lifeInsSa;
    
    @Column(name="health_ins_sa")
    private Float healthInsSa;
    
    @Column(name="crop_ins_sa")
    private Float cropInsSa;

    /** Default constructor. */
    public FarmerInsuranceInfo() {
        super();
    }

    public Float getLifeInsSa() {
		return lifeInsSa;
	}


	public void setLifeInsSa(Float lifeInsSa) {
		this.lifeInsSa = lifeInsSa;
	}


	public Float getHealthInsSa() {
		return healthInsSa;
	}


	public void setHealthInsSa(Float healthInsSa) {
		this.healthInsSa = healthInsSa;
	}


	public Float getCropInsSa() {
		return cropInsSa;
	}


	public void setCropInsSa(Float cropInsSa) {
		this.cropInsSa = cropInsSa;
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
     * Access method for lifeInsuranceCompanyId.
     *
     * @return the current value of lifeInsuranceCompanyId
     */
    public int getLifeInsuranceCompanyId() {
        return lifeInsuranceCompanyId;
    }

    /**
     * Setter method for lifeInsuranceCompanyId.
     *
     * @param aLifeInsuranceCompanyId the new value for lifeInsuranceCompanyId
     */
    public void setLifeInsuranceCompanyId(int aLifeInsuranceCompanyId) {
        lifeInsuranceCompanyId = aLifeInsuranceCompanyId;
    }

    /**
     * Access method for healthInsuranceCompanyId.
     *
     * @return the current value of healthInsuranceCompanyId
     */
    public int getHealthInsuranceCompanyId() {
        return healthInsuranceCompanyId;
    }

    /**
     * Setter method for healthInsuranceCompanyId.
     *
     * @param aHealthInsuranceCompanyId the new value for healthInsuranceCompanyId
     */
    public void setHealthInsuranceCompanyId(int aHealthInsuranceCompanyId) {
        healthInsuranceCompanyId = aHealthInsuranceCompanyId;
    }

    /**
     * Access method for cropInsuranceCompanyId.
     *
     * @return the current value of cropInsuranceCompanyId
     */
    public int getCropInsuranceCompanyId() {
        return cropInsuranceCompanyId;
    }

    /**
     * Setter method for cropInsuranceCompanyId.
     *
     * @param aCropInsuranceCompanyId the new value for cropInsuranceCompanyId
     */
    public void setCropInsuranceCompanyId(int aCropInsuranceCompanyId) {
        cropInsuranceCompanyId = aCropInsuranceCompanyId;
    }

   
}
