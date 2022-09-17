package com.krishi.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="farmer")
public class Farmer {
	
	@Id
	private String id;
	
	private String alternativeMobNumber;
	
	private Double cropArea;
	
	private Double farmSize;
	
	private String farmerName;
	
	private String farmerFatherHusbandName;
	
	private Integer hasGovernmentIdProof;
	
	private Integer hasIrrigationLand;
	
	private Integer hasOwnLand;
	
	private Integer isVip;
	
	private String majorCrop;
	
	private Integer mobileTypeId;
	
	private String referencePerson;
	
	private String referencePersonMobileNumber;
	
	private String speakingLanguageId;
	
	private Integer willingnessForCdt;
	
	private String primaryMobNumber;
	
	private Integer villageId;
	
	/** added for POI Meeting Point - Pranay : Start */
	private String meetingPoint;

	public String getMeetingPoint() {
		return meetingPoint;
	}

	public void setMeetingPoint(String meetingPoint) {
		this.meetingPoint = meetingPoint;
	}
	/** added for POI Meeting Point - Pranay : End */

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAlternativeMobNumber() {
		return alternativeMobNumber;
	}

	public void setAlternativeMobNumber(String alternativeMobNumber) {
		this.alternativeMobNumber = alternativeMobNumber;
	}

	public Double getCropArea() {
		return cropArea;
	}

	public void setCropArea(Double cropArea) {
		this.cropArea = cropArea;
	}

	public Double getFarmSize() {
		return farmSize;
	}

	public void setFarmSize(Double farmSize) {
		this.farmSize = farmSize;
	}

	public String getFarmerFatherHusbandName() {
		return farmerFatherHusbandName;
	}

	public void setFarmerFatherHusbandName(String farmerFatherHusbandName) {
		this.farmerFatherHusbandName = farmerFatherHusbandName;
	}

	public Integer getHasGovernmentIdProof() {
		return hasGovernmentIdProof;
	}

	public void setHasGovernmentIdProof(Integer hasGovernmentIdProof) {
		this.hasGovernmentIdProof = hasGovernmentIdProof;
	}

	public Integer getHasIrrigationLand() {
		return hasIrrigationLand;
	}

	public void setHasIrrigationLand(Integer hasIrrigationLand) {
		this.hasIrrigationLand = hasIrrigationLand;
	}

	public Integer getHasOwnLand() {
		return hasOwnLand;
	}

	public void setHasOwnLand(Integer hasOwnLand) {
		this.hasOwnLand = hasOwnLand;
	}

	public Integer getIsVip() {
		return isVip;
	}

	public void setIsVip(Integer isVip) {
		this.isVip = isVip;
	}

	public String getMajorCrop() {
		return majorCrop;
	}

	public void setMajorCrop(String majorCrop) {
		this.majorCrop = majorCrop;
	}

	public Integer getMobileTypeId() {
		return mobileTypeId;
	}

	public void setMobileTypeId(Integer mobileTypeId) {
		this.mobileTypeId = mobileTypeId;
	}

	public String getReferencePerson() {
		return referencePerson;
	}

	public void setReferencePerson(String referencePerson) {
		this.referencePerson = referencePerson;
	}

	public String getReferencePersonMobileNumber() {
		return referencePersonMobileNumber;
	}

	public void setReferencePersonMobileNumber(String referencePersonMobileNumber) {
		this.referencePersonMobileNumber = referencePersonMobileNumber;
	}

	public String getSpeakingLanguageId() {
		return speakingLanguageId;
	}

	public void setSpeakingLanguageId(String speakingLanguageId) {
		this.speakingLanguageId = speakingLanguageId;
	}

	public Integer getWillingnessForCdt() {
		return willingnessForCdt;
	}

	public void setWillingnessForCdt(Integer willingnessForCdt) {
		this.willingnessForCdt = willingnessForCdt;
	}

	public String getPrimaryMobNumber() {
		return primaryMobNumber;
	}

	public void setPrimaryMobNumber(String primaryMobNumber) {
		this.primaryMobNumber = primaryMobNumber;
	}

	public Integer getVillageId() {
		return villageId;
	}

	public void setVillageId(Integer villageId) {
		this.villageId = villageId;
	}

	public String getFarmerName() {
		return farmerName;
	}

	public void setFarmerName(String farmerName) {
		this.farmerName = farmerName;
	}
	
	

}
