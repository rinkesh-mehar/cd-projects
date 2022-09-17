package com.krishi.fls.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "farm_case")
public class FarmCase implements Serializable {

	/** Primary key. */
	protected static final String PK = "id";

	@Id
	private String id;
	@Column(name = "farm_id")
	private String farmId;

	@Transient
	private Boolean syncReq;
	
	@Column(name = "nmd")
	private Date nmd;
	
	@Column(name = "case_sample_status")
	private String caseSampleStatus;
	
	@Column(name="due_amount")
	private Float dueAmount;

	@Column(name = "amount_collected")
	private Float amountCollected;
	
	@Column(name = "crop_type")
	private Integer cropType;

	public Float getAmountCollected() {
		return amountCollected;
	}

	public void setAmountCollected(Float amountCollected) {
		this.amountCollected = amountCollected;
	}

	public Integer getCropType() {
		return cropType;
	}

	public void setCropType(Integer cropType) {
		this.cropType = cropType;
	}

	public Date getNmd() {
		return nmd;
	}

	public void setNmd(Date nmd) {
		this.nmd = nmd;
	}

	public String getCaseSampleStatus() {
		return caseSampleStatus;
	}

	public void setCaseSampleStatus(String caseSampleStatus) {
		this.caseSampleStatus = caseSampleStatus;
	}

	public Boolean getSyncReq() {
		return syncReq;
	}

	public void setSyncReq(Boolean syncReq) {
		this.syncReq = syncReq;
	}

	/** Default constructor. */
	public FarmCase() {
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
	 * Access method for farmId.
	 *
	 * @return the current value of farmId
	 */
	public String getFarmId() {
		return farmId;
	}

	/**
	 * Setter method for farmId.
	 *
	 * @param aFarmId the new value for farmId
	 */
	public void setFarmId(String aFarmId) {
		farmId = aFarmId;
	}

	public Float getDueAmount() {
		return dueAmount;
	}

	public void setDueAmount(Float dueAmount) {
		this.dueAmount = dueAmount;
	}
	



}
