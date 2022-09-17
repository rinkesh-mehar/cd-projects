package com.krishi.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.krishi.model.EntityModel;

@Entity
@Table(name = "farm_case")
public class FarmCase implements Serializable, EntityModel {

	@Id
	private String id;

	@Column(name = "farm_id")
	private String farmId;

	private Date nmd;

	private String caseSampleStatus;
	
	private Float dueAmount;
	
	@Column(name = "amount_collected")
	private Double amountCollected;
	
	/** added cropType field -CDT-Ujwal- Start*/ 
	@Column(name = "crop_type")
	private Integer cropType;
	
	@Column(name = "simple_ndvi_last_sync")
	private java.util.Date simpleNdviLastSync;

	public Integer getCropType() {
		return cropType;
	}
	/** added cropType field -CDT-Ujwal- End*/ 

	public void setCropType(Integer cropType) {
		this.cropType = cropType;
	}

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFarmId() {
		return farmId;
	}

	public void setFarmId(String farmId) {
		this.farmId = farmId;
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

	public Float getDueAmount() {
		return dueAmount;
	}

	public void setDueAmount(Float dueAmount) {
		this.dueAmount = dueAmount;
	}

	public Double getAmountCollected() {
		return amountCollected;
	}

	public void setAmountCollected(Double amountCollected) {
		this.amountCollected = amountCollected;
	}

	public java.util.Date getSimpleNdviLastSync() {
		return simpleNdviLastSync;
	}

	public void setSimpleNdviLastSync(java.util.Date simpleNdviLastSync) {
		this.simpleNdviLastSync = simpleNdviLastSync;
	}

	
}
