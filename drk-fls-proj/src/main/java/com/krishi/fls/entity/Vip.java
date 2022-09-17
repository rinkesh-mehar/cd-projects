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

@Entity
@Table(name = "vip")
public class Vip implements Serializable {

	/** Primary key. */
	protected static final String PK = "vipId";

	@Id
	@Column(name = "id")
	private String vipId;
	@Column(name = "alternate_number", precision = 19)
	private String alternateNumber;
	
	@Column(name="name" ,length = 255)
	private String name;
	@Column(name = "primary_number", nullable = false, precision = 19)
	private String primaryNumber;
	@Column(name = "status")
	private int status;
	@Column(name = "village_id", nullable = false, precision = 10)
	private int villageId;
	@Column(name = "vip_designation", length = 255)
	private int vipDesignation;
	@Column(name = "farmer_id",nullable=false)
	private String farmerId;

	/** Default constructor. */
	public Vip() {
		super();
	}

	/**
	 * Access method for vipId.
	 *
	 * @return the current value of vipId
	 */
	public String getVipId() {
		return vipId;
	}

	/**
	 * Setter method for vipId.
	 *
	 * @param aVipId the new value for vipId
	 */
	public void setVipId(String aVipId) {
		vipId = aVipId;
	}

	/**
	 * Access method for alternateNumber.
	 *
	 * @return the current value of alternateNumber
	 */
	public String getAlternateNumber() {
		return alternateNumber;
	}

	/**
	 * Setter method for alternateNumber.
	 *
	 * @param aAlternateNumber the new value for alternateNumber
	 */
	public void setAlternateNumber(String aAlternateNumber) {
		alternateNumber = aAlternateNumber;
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

	/**
	 * Access method for primaryNumber.
	 *
	 * @return the current value of primaryNumber
	 */
	public String getPrimaryNumber() {
		return primaryNumber;
	}

	/**
	 * Setter method for primaryNumber.
	 *
	 * @param aPrimaryNumber the new value for primaryNumber
	 */
	public void setPrimaryNumber(String aPrimaryNumber) {
		primaryNumber = aPrimaryNumber;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * Access method for villageId.
	 *
	 * @return the current value of villageId
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

	/**
	 * Access method for vipDesignation.
	 *
	 * @return the current value of vipDesignation
	 */
	public int getVipDesignation() {
		return vipDesignation;
	}

	/**
	 * Setter method for vipDesignation.
	 *
	 * @param aVipDesignation the new value for vipDesignation
	 */
	public void setVipDesignation(int aVipDesignation) {
		vipDesignation = aVipDesignation;
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

}
