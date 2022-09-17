package com.krishi.entity;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.krishi.model.EntityModel;

@Entity(name="govt_official")
public class GovtOfficial implements Serializable, EntityModel {

    @Id
    @Column(name="id")
    private String govtOfficialId;
    @Column(name="alternate_mob_number")
    private String alternateMobNumber;
    @Column(name="govt_department_id")
    private int govtDepartmentId;
    @Column(name="govt_designation_id")
    private int govtDesignationId;
    @Column
    private String name;
    @Column(name="primary_mob_number")
    private String primaryMobNumber;
    @Column(name="village_id")
    private int villageId;
    
    private String otherDesignation;

    /** Default constructor. */
    public GovtOfficial() {
        super();
    }
    
    public String getId() {
    	return this.govtOfficialId;
    }
    
    public void setId(String id) {
    	this.govtOfficialId = id;
    }

    public String getGovtOfficialId() {
        return govtOfficialId;
    }

    public void setGovtOfficialId(String govtOfficialId) {
        this.govtOfficialId = govtOfficialId;
    }


    public String getAlternateMobNumber() {
        return alternateMobNumber;
    }

    public void setAlternateMobNumber(String alternateMobNumber) {
        this.alternateMobNumber = alternateMobNumber;
    }

    public void setPrimaryMobNumber(String primaryMobNumber) {
        this.primaryMobNumber = primaryMobNumber;
    }

    public int getGovtDepartmentId() {
        return govtDepartmentId;
    }

    /**
     * Setter method for govtDepartmentId.
     *
     * @param aGovtDepartmentId the new value for govtDepartmentId
     */
    public void setGovtDepartmentId(int aGovtDepartmentId) {
        govtDepartmentId = aGovtDepartmentId;
    }

    /**
     * Access method for govtDesignationId.
     *
     * @return the current value of govtDesignationId
     */
    public int getGovtDesignationId() {
        return govtDesignationId;
    }

    /**
     * Setter method for govtDesignationId.
     *
     * @param aGovtDesignationId the new value for govtDesignationId
     */
    public void setGovtDesignationId(int aGovtDesignationId) {
        govtDesignationId = aGovtDesignationId;
    }

    /**
     * Access method for name.
     *
     * @return the current value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method for name.
     *
     * @param aName the new value for name
     */
    public void setName(String aName) {
        name = aName;
    }

    public String getPrimaryMobNumber() {
        return primaryMobNumber;
    }

    public int getVillageId() {
        return villageId;
    }

    /**
     * Setter method for villageId.
     *
     * @param aVillageId the new value for villageId
     */
    public void setVillageId(int aVillageId) {
        villageId = aVillageId;
    }

	public String getOtherDesignation() {
		return otherDesignation;
	}

	public void setOtherDesignation(String otherDesignation) {
		this.otherDesignation = otherDesignation;
	}








}
