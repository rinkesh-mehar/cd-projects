package com.krishi.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="lead_calling_detail_info")
public class ViewLeadCallingDetailInfo {
	
	@Id
	private String taskId;
	
	private String stateName;
	
	private String districtName;
	
	private String tehsilName;
	
	private String villageName;
	
	private Integer villageId;
	
	private Boolean isVip;
	
	private Integer vipStatus;
	
	private String vipDesignation;
	
	private String otherDesignation;
	
	private String farmerName;
	
	private String primaryMobNumber;
	
	private String alternativeMobNumber;
	
	private String referencePerson;
	
	//private Integer relationshipToReference;

	private String relationshipToReferenceName;

	private String referencePersonMobileNumber;
	
	private Boolean hasGovernmentIdProof;
	
	private Boolean hasOwnLand;
	
	private Boolean hasIrrigationLand;
	
	private Double  farmSize;
	
	private String majorCropList;
	
	private Double cropArea;
	
	private Boolean willingnessForCdt;
	
	private String speakingLanguageList;
	
	private Integer mobileTypeId;
	
	private String farmerFatherHusbandName;
	
	private Integer farmerCreatedBy;
	
	private String prsFirstName;
	
	private String prsMiddleName;
	
	private String prsLastName;
	
	/** added for farmer major crop*/
	private String farmerId;
	
	/** added seller type for seller */
	private String sellerType;

	private Integer regionId;

	public String getSellerType() {
		return sellerType;
	}

	public void setSellerType(String sellerType) {
		this.sellerType = sellerType;
	}

	public String getFarmerId() {
		return farmerId;
	}

	public void setFarmerId(String farmerId) {
		this.farmerId = farmerId;
	}
	
	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getTehsilName() {
		return tehsilName;
	}

	public void setTehsilName(String tehsilName) {
		this.tehsilName = tehsilName;
	}

	public String getVillageName() {
		return villageName;
	}

	public void setVillageName(String villageName) {
		this.villageName = villageName;
	}

	public Integer getVillageId() {
		return villageId;
	}

	public void setVillageId(Integer villageId) {
		this.villageId = villageId;
	}

	public Boolean getIsVip() {
		return isVip;
	}

	public void setIsVip(Boolean isVip) {
		this.isVip = isVip;
	}

	public Integer getVipStatus() {
		return vipStatus;
	}

	public void setVipStatus(Integer vipStatus) {
		this.vipStatus = vipStatus;
	}

	public String getVipDesignation() {
		return vipDesignation;
	}

	public void setVipDesignation(String vipDesignation) {
		this.vipDesignation = vipDesignation;
	}

	public String getOtherDesignation() {
		return otherDesignation;
	}

	public void setOtherDesignation(String otherDesignation) {
		this.otherDesignation = otherDesignation;
	}

	public String getFarmerName() {
		return farmerName;
	}

	public void setFarmerName(String farmerName) {
		this.farmerName = farmerName;
	}

	public String getPrimaryMobNumber() {
		return primaryMobNumber;
	}

	public void setPrimaryMobNumber(String primaryMobNumber) {
		this.primaryMobNumber = primaryMobNumber;
	}

	public String getAlternativeMobNumber() {
		return alternativeMobNumber;
	}

	public void setAlternativeMobNumber(String alternativeMobNumber) {
		this.alternativeMobNumber = alternativeMobNumber;
	}

	public String getReferencePerson() {
		return referencePerson;
	}

	public void setReferencePerson(String referencePerson) {
		this.referencePerson = referencePerson;
	}

	/*
	 * public Integer getRelationshipToReference() { return relationshipToReference;
	 * }
	 * 
	 * public void setRelationshipToReference(Integer relationshipToReference) {
	 * this.relationshipToReference = relationshipToReference; }
	 */

	public Boolean getHasGovernmentIdProof() {
		return hasGovernmentIdProof;
	}

	public String getReferencePersonMobileNumber() {
		return referencePersonMobileNumber;
	}

	public void setReferencePersonMobileNumber(String referencePersonMobileNumber) {
		this.referencePersonMobileNumber = referencePersonMobileNumber;
	}

	public void setHasGovernmentIdProof(Boolean hasGovernmentIdProof) {
		this.hasGovernmentIdProof = hasGovernmentIdProof;
	}

	public Boolean getHasOwnLand() {
		return hasOwnLand;
	}

	public void setHasOwnLand(Boolean hasOwnLand) {
		this.hasOwnLand = hasOwnLand;
	}

	public Boolean getHasIrrigationLand() {
		return hasIrrigationLand;
	}

	public void setHasIrrigationLand(Boolean hasIrrigationLand) {
		this.hasIrrigationLand = hasIrrigationLand;
	}

	public Double getFarmSize() {
		return farmSize;
	}

	public void setFarmSize(Double farmSize) {
		this.farmSize = farmSize;
	}

	public String getMajorCropList() {
		return majorCropList;
	}

	public void setMajorCropList(String majorCropList) {
		this.majorCropList = majorCropList;
	}

	public Double getCropArea() {
		return cropArea;
	}

	public void setCropArea(Double cropArea) {
		this.cropArea = cropArea;
	}

	public Boolean getWillingnessForCdt() {
		return willingnessForCdt;
	}

	public void setWillingnessForCdt(Boolean willingnessForCdt) {
		this.willingnessForCdt = willingnessForCdt;
	}

	public String getSpeakingLanguageList() {
		return speakingLanguageList;
	}

	public void setSpeakingLanguageList(String speakingLanguageList) {
		this.speakingLanguageList = speakingLanguageList;
	}

	public Integer getMobileTypeId() {
		return mobileTypeId;
	}

	public void setMobileTypeId(Integer mobileTypeId) {
		this.mobileTypeId = mobileTypeId;
	}

	public String getFarmerFatherHusbandName() {
		return farmerFatherHusbandName;
	}

	public void setFarmerFatherHusbandName(String farmerFatherHusbandName) {
		this.farmerFatherHusbandName = farmerFatherHusbandName;
	}

	public Integer getFarmerCreatedBy() {
		return farmerCreatedBy;
	}

	public void setFarmerCreatedBy(Integer farmerCreatedBy) {
		this.farmerCreatedBy = farmerCreatedBy;
	}

	public String getPrsFirstName() {
		return prsFirstName;
	}

	public void setPrsFirstName(String prsFirstName) {
		this.prsFirstName = prsFirstName;
	}

	public String getPrsMiddleName() {
		return prsMiddleName;
	}

	public void setPrsMiddleName(String prsMiddleName) {
		this.prsMiddleName = prsMiddleName;
	}

	public String getPrsLastName() {
		return prsLastName;
	}

	public void setPrsLastName(String prsLastName) {
		this.prsLastName = prsLastName;
	}

	public Integer getRegionId() {
		return regionId;
	}

	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}

	public String getRelationshipToReferenceName() {
		return relationshipToReferenceName;
	}

	public void setRelationshipToReferenceName(String relationshipToReferenceName) {
		this.relationshipToReferenceName = relationshipToReferenceName;
	}
}
