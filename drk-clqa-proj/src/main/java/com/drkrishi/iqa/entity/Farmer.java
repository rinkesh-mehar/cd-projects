package com.drkrishi.iqa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="farmer")
public class Farmer {

  
    @Id
    @Column(name="id")
    private String id;
    
    @Column(name="alternative_mob_number")
    private String alternativeMobNumber;
    
    @Column(name="crop_area")
    private double cropArea;
    
    @Column(name="farm_size")
    private double farmSize;
    
    @Column(name="farmer_father_husband_name")
    private String farmerFatherHusbandName;
    
    @Column(name="farmer_name")
    private String farmerName;
    
    @Column(name="has_government_id_proof")
    private short hasGovernmentIdProof;
    
    @Column(name="has_irrigation_land")
    private short hasIrrigationLand;
    
    @Column(name="has_own_land")
    private short hasOwnLand;
    
    @Column(name="is_vip")
    private short isVip;
    
    @Column(name="land_ownership_images")
    private String landOwnershipImages;
    
    @Column(name="major_crop")
    private String majorCrop;
    
    @Column(name="mobile_type_id")
    private int mobileTypeId;
    
    @Column(name="primary_mob_number")
    private String primaryMobNumber;
    
    @Column(name="reference_person")
    private String referencePerson;
    
    @Column(name="reference_person_mobile_number")
    private String referencePersonMobileNumber;
    
    @Column(name="relationship_to_reference")
    private String relationshipToReference;
    
    @Column(name="speaking_language_id")
    private int speakingLanguageId;
    
    @Column(name="village_id")
    private int villageId;
    
    @Column(name="willingness_for_cdt")
    private short willingnessForCdt;
    
    @Column(name="has_outstanding_loan")
    private boolean hasOutstandingLoan;
    
    @Column(name="has_life_insurance")
    private boolean hasLifeInsurance;
    
    @Column(name="has_health_insurance")
    private boolean hasHealthInsurance;
    
    @Column(name="has_crop_insurance")
    private boolean hasCropInsurance;
    
    @Column(name="is_drk_cust")
    private boolean isDrkCust;
    
    @Column(name="is_agriota_cust")
    private boolean isAgriotaCust;
    
    @Column(name="due_amount")
    private float dueAmount;

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

	public double getCropArea() {
		return cropArea;
	}

	public void setCropArea(double cropArea) {
		this.cropArea = cropArea;
	}

	public double getFarmSize() {
		return farmSize;
	}

	public void setFarmSize(double farmSize) {
		this.farmSize = farmSize;
	}

	public String getFarmerFatherHusbandName() {
		return farmerFatherHusbandName;
	}

	public void setFarmerFatherHusbandName(String farmerFatherHusbandName) {
		this.farmerFatherHusbandName = farmerFatherHusbandName;
	}

	public String getFarmerName() {
		return farmerName;
	}

	public void setFarmerName(String farmerName) {
		this.farmerName = farmerName;
	}

	public short getHasGovernmentIdProof() {
		return hasGovernmentIdProof;
	}

	public void setHasGovernmentIdProof(short hasGovernmentIdProof) {
		this.hasGovernmentIdProof = hasGovernmentIdProof;
	}

	public short getHasIrrigationLand() {
		return hasIrrigationLand;
	}

	public void setHasIrrigationLand(short hasIrrigationLand) {
		this.hasIrrigationLand = hasIrrigationLand;
	}

	public short getHasOwnLand() {
		return hasOwnLand;
	}

	public void setHasOwnLand(short hasOwnLand) {
		this.hasOwnLand = hasOwnLand;
	}

	public short getIsVip() {
		return isVip;
	}

	public void setIsVip(short isVip) {
		this.isVip = isVip;
	}

	public String getLandOwnershipImages() {
		return landOwnershipImages;
	}

	public void setLandOwnershipImages(String landOwnershipImages) {
		this.landOwnershipImages = landOwnershipImages;
	}

	public String getMajorCrop() {
		return majorCrop;
	}

	public void setMajorCrop(String majorCrop) {
		this.majorCrop = majorCrop;
	}

	public int getMobileTypeId() {
		return mobileTypeId;
	}

	public void setMobileTypeId(int mobileTypeId) {
		this.mobileTypeId = mobileTypeId;
	}

	public String getPrimaryMobNumber() {
		return primaryMobNumber;
	}

	public void setPrimaryMobNumber(String primaryMobNumber) {
		this.primaryMobNumber = primaryMobNumber;
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

	public String getRelationshipToReference() {
		return relationshipToReference;
	}

	public void setRelationshipToReference(String relationshipToReference) {
		this.relationshipToReference = relationshipToReference;
	}

	public int getSpeakingLanguageId() {
		return speakingLanguageId;
	}

	public void setSpeakingLanguageId(int speakingLanguageId) {
		this.speakingLanguageId = speakingLanguageId;
	}

	public int getVillageId() {
		return villageId;
	}

	public void setVillageId(int villageId) {
		this.villageId = villageId;
	}

	public short getWillingnessForCdt() {
		return willingnessForCdt;
	}

	public void setWillingnessForCdt(short willingnessForCdt) {
		this.willingnessForCdt = willingnessForCdt;
	}

	public boolean isHasOutstandingLoan() {
		return hasOutstandingLoan;
	}

	public void setHasOutstandingLoan(boolean hasOutstandingLoan) {
		this.hasOutstandingLoan = hasOutstandingLoan;
	}

	public boolean isHasLifeInsurance() {
		return hasLifeInsurance;
	}

	public void setHasLifeInsurance(boolean hasLifeInsurance) {
		this.hasLifeInsurance = hasLifeInsurance;
	}

	public boolean isHasHealthInsurance() {
		return hasHealthInsurance;
	}

	public void setHasHealthInsurance(boolean hasHealthInsurance) {
		this.hasHealthInsurance = hasHealthInsurance;
	}

	public boolean isHasCropInsurance() {
		return hasCropInsurance;
	}

	public void setHasCropInsurance(boolean hasCropInsurance) {
		this.hasCropInsurance = hasCropInsurance;
	}

	public boolean isDrkCust() {
		return isDrkCust;
	}

	public void setDrkCust(boolean isDrkCust) {
		this.isDrkCust = isDrkCust;
	}

	public boolean isAgriotaCust() {
		return isAgriotaCust;
	}

	public void setAgriotaCust(boolean isAgriotaCust) {
		this.isAgriotaCust = isAgriotaCust;
	}

	public float getDueAmount() {
		return dueAmount;
	}

	public void setDueAmount(float dueAmount) {
		this.dueAmount = dueAmount;
	}

}
