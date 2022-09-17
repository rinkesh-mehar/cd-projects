

package com.krishi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.krishi.model.EntityModel;

import java.io.Serializable;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Entity
@Table(name="farmer")
public class Farmer implements Serializable, EntityModel {

	private static  final SimpleDateFormat FORMAT = new SimpleDateFormat("dd-MM-yyyy hh-mm-ss");

    @Id
    @Column(name="id")
    private String farmerId;
    @Column(name="alternative_mob_number")
    private String alternativeMobNumber;
    @Column(name="crop_area")
    private Double cropArea;
    @Column(name="farm_size")
    private Double farmSize;
    @Column(name="farmer_father_husband_name")
    private String farmerFatherHusbandName;
    @Column(name="farmer_name")
    private String farmerName;
    @Column(name="has_government_id_proof")
    private Short hasGovernmentIdProof;
    @Column(name="has_irrigation_land")
    private Short hasIrrigationLand;
    @Column(name="has_own_land")
    private Short hasOwnLand;
    @Column(name="is_vip")
    private Short isVip;
    @Column(name="land_ownership_images")
    private String landOwnershipImages;
    @Column(name="major_crop")
    private String majorCrop;
    @Column(name="mobile_type_id")
    private Integer mobileTypeId;
    @Column(name="primary_mob_number")
    private String primaryMobNumber;
    @Column(name="reference_person")
    private String referencePerson;
    @Column(name="reference_person_mobile_number")
    private String referencePersonMobileNumber;
    @Column(name="relationship_to_reference")
    private String relationshipToReference;
    @Column(name="speaking_language_id")
    private String speakingLanguageId;
    @Column(name="village_id")
    private Integer villageId;
    @Column(name="willingness_for_cdt")
    private Short willingnessForCdt;
    @Column(name="has_outstanding_loan")
    private Boolean hasOutstandingLoan;
    @Column(name="has_life_insurance")
    private Boolean hasLifeInsurance;
    @Column(name="has_health_insurance")
    private Boolean hasHealthInsurance;
    @Column(name="has_crop_insurance")
    private Boolean hasCropInsurance;
    @Column(name="is_drk_cust")
    private Boolean drkCust;
    @Column(name="is_agriota_cust")
    private Boolean agriotaCust;
    @Column(name="due_amount")
    private Float dueAmount;

    private Integer createdBy;

    private Date createdDate;

    private Integer modifiedBy;

    private Date modifiedDate;

	/** added new variable in PRS day end sync- start CDT-Ujwal */    
    
    @Column(name = "seller_type")
    private Integer sellerType;
    
    public Integer getSellerType() {
		return sellerType;
	}
    
    public void setSellerType(Integer sellerType) {
		this.sellerType = sellerType;
	}
    
    /** added new variable in PRS day end sync- start CDT-Ujwal */  

	public Farmer() {
        super();
    }

    public String getId() {
    	return farmerId;
    }
    public void setId(String id) {
    	this.farmerId =id;
    }

	public String getFarmerId() {
		return farmerId;
	}

	public void setFarmerId(String farmerId) {
		this.farmerId = farmerId;
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

	public String getFarmerName() {
		return farmerName;
	}

	public void setFarmerName(String farmerName) {
		this.farmerName = farmerName;
	}

	public Short getHasGovernmentIdProof() {
		return hasGovernmentIdProof;
	}

	public void setHasGovernmentIdProof(Short hasGovernmentIdProof) {
		this.hasGovernmentIdProof = hasGovernmentIdProof;
	}

	public Short getHasIrrigationLand() {
		return hasIrrigationLand;
	}

	public void setHasIrrigationLand(Short hasIrrigationLand) {
		this.hasIrrigationLand = hasIrrigationLand;
	}

	public Short getHasOwnLand() {
		return hasOwnLand;
	}

	public void setHasOwnLand(Short hasOwnLand) {
		this.hasOwnLand = hasOwnLand;
	}

	public Short getIsVip() {
		return isVip;
	}

	public void setIsVip(Short isVip) {
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

	public Integer getMobileTypeId() {
		return mobileTypeId;
	}

	public void setMobileTypeId(Integer mobileTypeId) {
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

	public String getSpeakingLanguageId() {
		return speakingLanguageId;
	}

	public void setSpeakingLanguageId(String speakingLanguageId) {
		this.speakingLanguageId = speakingLanguageId;
	}

	public Integer getVillageId() {
		return villageId;
	}

	public void setVillageId(Integer villageId) {
		this.villageId = villageId;
	}

	public Short getWillingnessForCdt() {
		return willingnessForCdt;
	}

	public void setWillingnessForCdt(Short willingnessForCdt) {
		this.willingnessForCdt = willingnessForCdt;
	}

	public Boolean getHasOutstandingLoan() {
		return hasOutstandingLoan;
	}

	public void setHasOutstandingLoan(Boolean hasOutstandingLoan) {
		this.hasOutstandingLoan = hasOutstandingLoan;
	}

	public Boolean getHasLifeInsurance() {
		return hasLifeInsurance;
	}

	public void setHasLifeInsurance(Boolean hasLifeInsurance) {
		this.hasLifeInsurance = hasLifeInsurance;
	}

	public Boolean getHasHealthInsurance() {
		return hasHealthInsurance;
	}

	public void setHasHealthInsurance(Boolean hasHealthInsurance) {
		this.hasHealthInsurance = hasHealthInsurance;
	}

	public Boolean getHasCropInsurance() {
		return hasCropInsurance;
	}

	public void setHasCropInsurance(Boolean hasCropInsurance) {
		this.hasCropInsurance = hasCropInsurance;
	}

	public Boolean getDrkCust() {
		return drkCust;
	}

	public void setDrkCust(Boolean drkCust) {
		this.drkCust = drkCust;
	}

	public Boolean getAgriotaCust() {
		return agriotaCust;
	}

	public void setAgriotaCust(Boolean agriotaCust) {
		this.agriotaCust = agriotaCust;
	}

	public Float getDueAmount() {
		return dueAmount;
	}

	public void setDueAmount(Float dueAmount) {
		this.dueAmount = dueAmount;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public void setCreatedDate(String createdDate) {
		try {
			this.createdDate = new Date(FORMAT.parse(createdDate).getTime());
		} catch (ParseException e) {
		}
	}


	public Integer getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		try {
			this.modifiedDate = new Date(FORMAT.parse(modifiedDate).getTime());
		} catch (ParseException e) {
		}
	}
}
