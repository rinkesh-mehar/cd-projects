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

@Entity(name="vip")
public class Vip implements Serializable, EntityModel {

    @Id
    @Column(name="id")
    private String vipId;
    @Column(name="alternate_number")
    private String alternateNumber;

    @Column
    private String name;
    @Column(name="primary_number")
    private String primaryNumber;
    @Column
    private int status;

    @Column(name="village_id")
    private int villageId;
    @Column(name="vip_designation")
    private Integer vipDesignation;
    @Column(name="farmer_id")
    private String farmerId;
    
    private String otherDesignation;

    public Vip() {
        super();
    }

    public String getVipId() {
        return vipId;
    }

    public void setVipId(String vipId) {
        this.vipId = vipId;
    }

    /**
     * Access method for vipId.
     *
     * @return the current value of vipId
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

    public String getAlternateNumber() {
        return alternateNumber;
    }

    public void setAlternateNumber(String alternateNumber) {
        this.alternateNumber = alternateNumber;
    }

    public String getPrimaryNumber() {
        return primaryNumber;
    }

    public void setPrimaryNumber(String primaryNumber) {
        this.primaryNumber = primaryNumber;
    }


    /**
     * Access method for status.
     *
     * @return the current value of status
     */
    public int getStatus() {
        return status;
    }

    /**
     * Setter method for status.
     *
     * @param aStatus the new value for status
     */
    public void setStatus(int aStatus) {
        status = aStatus;
    }

    /**
     * Access method for talukaId.
     *
     * @return the current value of talukaId
     */

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

    public Integer getVipDesignation() {
        return vipDesignation;
    }

    public void setVipDesignation(Integer vipDesignation) {
        this.vipDesignation = vipDesignation;
    }

    public String getFarmerId() {
        return farmerId;
    }

    public void setFarmerId(String farmerId) {
        this.farmerId = farmerId;
    }

	public String getOtherDesignation() {
		return otherDesignation;
	}

	public void setOtherDesignation(String otherDesignation) {
		this.otherDesignation = otherDesignation;
	}

	@Override
	public String getId() {
		return vipId;
	}

	@Override
	public void setId(String id) {
		this.vipId = id;
		
	}
}
