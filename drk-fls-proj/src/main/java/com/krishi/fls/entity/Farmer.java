package com.krishi.fls.entity;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "farmer")
public class Farmer implements Serializable {

	private static final SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

	/** Primary key. */
	protected static final String PK = "farmerId";

	@Id
	@Column(name = "id")
	private String farmerId;
	@Column(name = "alternative_mob_number", precision = 19)
	private String alternativeMobNumber;
	@Column(name = "crop_area", nullable = false, length = 22)
	private Double cropArea;
	@Column(name = "farm_size", length = 22)
	private Double farmSize;
	@Column(name = "farmer_father_husband_name", length = 255)
	private String farmerFatherHusbandName;
	@Column(name = "farmer_name", length = 255)
	private String farmerName;
	@Column(name = "has_government_id_proof", precision = 3)
	private Integer hasGovernmentIdProof;
	@Column(name = "has_irrigation_land", precision = 3)
	private Integer hasIrrigationLand;
	@Column(name = "has_own_land", precision = 3)
	private Integer hasOwnLand;
	@Column(name = "is_vip", precision = 3)
	private Integer isVip;
	@Column(name = "land_ownership_images", length = 255)
	private String landOwnershipImages;
	@Column(name = "major_crop", precision = 10)
	private String majorCrop;
	@Column(name = "mobile_type_id", precision = 10)
	private Integer mobileTypeId;
	@Column(name = "primary_mob_number", precision = 19)
	private String primaryMobNumber;
	@Column(name = "reference_person", length = 255)
	private String referencePerson;
	@Column(name = "reference_person_mobile_number", precision = 19)
	private String referencePersonMobileNumber;
	@Column(name = "relationship_to_reference", length = 255)
	private String relationshipToReference;
	@Column(name = "speaking_language_id", precision = 10)
	private String speakingLanguageId;
	@Column(name = "village_id", precision = 10)
	private Integer villageId;
	@Column(name = "willingness_for_cdt", precision = 3)
	private Integer willingnessForCdt;
	@Column(name = "has_outstanding_loan", length = 3)
	private Boolean hasOutstandingLoan;
	@Column(name = "has_life_insurance", length = 3)
	private Boolean hasLifeInsurance;
	@Column(name = "has_health_insurance", length = 3)
	private Boolean hasHealthInsurance;
	@Column(name = "has_crop_insurance", length = 3)
	private Boolean hasCropInsurance;
	@Column(name = "is_drk_cust", length = 3)
	private Boolean drkCust;
	@Column(name = "is_agriota_cust", length = 3)
	private Boolean agriotaCust;
	@Column(name = "due_amount", nullable = false, precision = 8, scale = 2)
	private Float dueAmount;

	@Column(name = "created_by")
	private Integer createdBy;
	
	//@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	@Column(name = "created_date")
	private Date createdDate;
	
	
	@Column(name = "modified_by")
	private Integer modifiedBy;

	
	//@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	@Column(name = "modified_date")
	private java.util.Date modifiedDate;
	
	/** added new sellerType field -CDT-Ujwal- Start*/
	@Column(name = "seller_type")
	private Integer sellerType;
	

	public Integer getSellerType() {
		return sellerType;
	}

	public void setSellerType(Integer sellerType) {
		this.sellerType = sellerType;
	}
	/** added new sellerType field -CDT-Ujwal- End*/

	public Farmer(String farmerId, String alternativeMobNumber, Double cropArea, Double farmSize,
			String farmerFatherHusbandName, String farmerName, Integer hasGovernmentIdProof, Integer hasIrrigationLand,
			Integer hasOwnLand, Integer isVip, String landOwnershipImages, String majorCrop, Integer mobileTypeId,
			String primaryMobNumber, String referencePerson, String referencePersonMobileNumber,
			String relationshipToReference, String speakingLanguageId, Integer villageId, Integer willingnessForCdt,
			Boolean hasOutstandingLoan, Boolean hasLifeInsurance, Boolean hasHealthInsurance, Boolean hasCropInsurance,
			Boolean drkCust, Boolean agriotaCust, Float dueAmount, Integer createdBy, Date createDate,
			Integer modifiedBy, Date modifiedDate, Integer sellerType) {
		super();
		this.farmerId = farmerId;
		this.alternativeMobNumber = alternativeMobNumber;
		this.cropArea = cropArea;
		this.farmSize = farmSize;
		this.farmerFatherHusbandName = farmerFatherHusbandName;
		this.farmerName = farmerName;
		this.hasGovernmentIdProof = hasGovernmentIdProof;
		this.hasIrrigationLand = hasIrrigationLand;
		this.hasOwnLand = hasOwnLand;
		this.isVip = isVip;
		this.landOwnershipImages = landOwnershipImages;
		this.majorCrop = majorCrop;
		this.mobileTypeId = mobileTypeId;
		this.primaryMobNumber = primaryMobNumber;
		this.referencePerson = referencePerson;
		this.referencePersonMobileNumber = referencePersonMobileNumber;
		this.relationshipToReference = relationshipToReference;
		this.speakingLanguageId = speakingLanguageId;
		this.villageId = villageId;
		this.willingnessForCdt = willingnessForCdt;
		this.hasOutstandingLoan = hasOutstandingLoan;
		this.hasLifeInsurance = hasLifeInsurance;
		this.hasHealthInsurance = hasHealthInsurance;
		this.hasCropInsurance = hasCropInsurance;
		this.drkCust = drkCust;
		this.agriotaCust = agriotaCust;
		this.dueAmount = dueAmount;
		this.createdBy = createdBy;
		this.createdDate = createDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.sellerType = sellerType;
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
	 * Access method for alternativeMobNumber.
	 *
	 * @return the current value of alternativeMobNumber
	 */
	public String getAlternativeMobNumber() {
		return alternativeMobNumber;
	}

	/**
	 * Setter method for alternativeMobNumber.
	 *
	 * @param aAlternativeMobNumber the new value for alternativeMobNumber
	 */
	public void setAlternativeMobNumber(String aAlternativeMobNumber) {
		alternativeMobNumber = aAlternativeMobNumber;
	}

	/**
	 * Access method for cropArea.
	 *
	 * @return the current value of cropArea
	 */
	public Double getCropArea() {
		return cropArea;
	}

	/**
	 * Setter method for cropArea.
	 *
	 * @param aCropArea the new value for cropArea
	 */
	public void setCropArea(Double aCropArea) {
		cropArea = aCropArea;
	}

	/**
	 * Access method for farmSize.
	 *
	 * @return the current value of farmSize
	 */
	public Double getFarmSize() {
		return farmSize;
	}

	/**
	 * Setter method for farmSize.
	 *
	 * @param aFarmSize the new value for farmSize
	 */
	public void setFarmSize(Double aFarmSize) {
		farmSize = aFarmSize;
	}

	/**
	 * Access method for farmerFatherHusbandName.
	 *
	 * @return the current value of farmerFatherHusbandName
	 */
	public String getFarmerFatherHusbandName() {
		return farmerFatherHusbandName;
	}

	/**
	 * Setter method for farmerFatherHusbandName.
	 *
	 * @param aFarmerFatherHusbandName the new value for farmerFatherHusbandName
	 */
	public void setFarmerFatherHusbandName(String aFarmerFatherHusbandName) {
		farmerFatherHusbandName = aFarmerFatherHusbandName;
	}

	/**
	 * Access method for farmerName.
	 *
	 * @return the current value of farmerName
	 */
	public String getFarmerName() {
		return farmerName;
	}

	/**
	 * Setter method for farmerName.
	 *
	 * @param aFarmerName the new value for farmerName
	 */
	public void setFarmerName(String aFarmerName) {
		farmerName = aFarmerName;
	}

	/**
	 * Access method for hasGovernmentIdProof.
	 *
	 * @return the current value of hasGovernmentIdProof
	 */
	public Integer getHasGovernmentIdProof() {
		return hasGovernmentIdProof;
	}

	/**
	 * Setter method for hasGovernmentIdProof.
	 *
	 * @param aHasGovernmentIdProof the new value for hasGovernmentIdProof
	 */
	public void setHasGovernmentIdProof(Integer aHasGovernmentIdProof) {
		hasGovernmentIdProof = aHasGovernmentIdProof;
	}

	/**
	 * Access method for hasIrrigationLand.
	 *
	 * @return the current value of hasIrrigationLand
	 */
	public Integer getHasIrrigationLand() {
		return hasIrrigationLand;
	}

	/**
	 * Setter method for hasIrrigationLand.
	 *
	 * @param aHasIrrigationLand the new value for hasIrrigationLand
	 */
	public void setHasIrrigationLand(Integer aHasIrrigationLand) {
		hasIrrigationLand = aHasIrrigationLand;
	}

	/**
	 * Access method for hasOwnLand.
	 *
	 * @return the current value of hasOwnLand
	 */
	public Integer getHasOwnLand() {
		return hasOwnLand;
	}

	/**
	 * Setter method for hasOwnLand.
	 *
	 * @param aHasOwnLand the new value for hasOwnLand
	 */
	public void setHasOwnLand(Integer aHasOwnLand) {
		hasOwnLand = aHasOwnLand;
	}

	/**
	 * Access method for isVip.
	 *
	 * @return the current value of isVip
	 */
	public Integer getIsVip() {
		return isVip;
	}

	

	/**
	 * Access method for landOwnershipImages.
	 *
	 * @return the current value of landOwnershipImages
	 */
	public String getLandOwnershipImages() {
		return landOwnershipImages;
	}

	/**
	 * Setter method for landOwnershipImages.
	 *
	 * @param aLandOwnershipImages the new value for landOwnershipImages
	 */
	public void setLandOwnershipImages(String aLandOwnershipImages) {
		landOwnershipImages = aLandOwnershipImages;
	}

	/**
	 * Access method for majorCrop.
	 *
	 * @return the current value of majorCrop
	 */
	public String getMajorCrop() {
		return majorCrop;
	}

	/**
	 * Setter method for majorCrop.
	 *
	 * @param aMajorCrop the new value for majorCrop
	 */
	public void setMajorCrop(String aMajorCrop) {
		majorCrop = aMajorCrop;
	}

	/**
	 * Access method for mobileTypeId.
	 *
	 * @return the current value of mobileTypeId
	 */
	public Integer getMobileTypeId() {
		return mobileTypeId;
	}

	/**
	 * Setter method for mobileTypeId.
	 *
	 * @param aMobileTypeId the new value for mobileTypeId
	 */
	public void setMobileTypeId(Integer aMobileTypeId) {
		mobileTypeId = aMobileTypeId;
	}

	/**
	 * Access method for primaryMobNumber.
	 *
	 * @return the current value of primaryMobNumber
	 */
	public String getPrimaryMobNumber() {
		return primaryMobNumber;
	}

	/**
	 * Setter method for primaryMobNumber.
	 *
	 * @param aPrimaryMobNumber the new value for primaryMobNumber
	 */
	public void setPrimaryMobNumber(String aPrimaryMobNumber) {
		primaryMobNumber = aPrimaryMobNumber;
	}

	/**
	 * Access method for referencePerson.
	 *
	 * @return the current value of referencePerson
	 */
	public String getReferencePerson() {
		return referencePerson;
	}

	/**
	 * Setter method for referencePerson.
	 *
	 * @param aReferencePerson the new value for referencePerson
	 */
	public void setReferencePerson(String aReferencePerson) {
		referencePerson = aReferencePerson;
	}

	/**
	 * Access method for referencePersonMobileNumber.
	 *
	 * @return the current value of referencePersonMobileNumber
	 */
	public String getReferencePersonMobileNumber() {
		return referencePersonMobileNumber;
	}

	/**
	 * Setter method for referencePersonMobileNumber.
	 *
	 * @param aReferencePersonMobileNumber the new value for
	 *                                     referencePersonMobileNumber
	 */
	public void setReferencePersonMobileNumber(String aReferencePersonMobileNumber) {
		referencePersonMobileNumber = aReferencePersonMobileNumber;
	}

	/**
	 * Access method for relationshipToReference.
	 *
	 * @return the current value of relationshipToReference
	 */
	public String getRelationshipToReference() {
		return relationshipToReference;
	}

	/**
	 * Setter method for relationshipToReference.
	 *
	 * @param aRelationshipToReference the new value for relationshipToReference
	 */
	public void setRelationshipToReference(String aRelationshipToReference) {
		relationshipToReference = aRelationshipToReference;
	}

	/**
	 * Access method for speakingLanguageId.
	 *
	 * @return the current value of speakingLanguageId
	 */
	public String getSpeakingLanguageId() {
		return speakingLanguageId;
	}

	/**
	 * Setter method for speakingLanguageId.
	 *
	 * @param aSpeakingLanguageId the new value for speakingLanguageId
	 */
	public void setSpeakingLanguageId(String aSpeakingLanguageId) {
		speakingLanguageId = aSpeakingLanguageId;
	}

	/**
	 * Access method for villageId.
	 *
	 * @return the current value of villageId
	 */
	public Integer getVillageId() {
		return villageId;
	}

	/**
	 * Setter method for villageId.
	 *
	 * @param aVillageId the new value for villageId
	 */
	public void setVillageId(Integer aVillageId) {
		villageId = aVillageId;
	}

	/**
	 * Access method for willingnessForCdt.
	 *
	 * @return the current value of willingnessForCdt
	 */
	public Integer getWillingnessForCdt() {
		return willingnessForCdt;
	}

	/**
	 * Setter method for willingnessForCdt.
	 *
	 * @param aWillingnessForCdt the new value for willingnessForCdt
	 */
	public void setWillingnessForCdt(Integer aWillingnessForCdt) {
		willingnessForCdt = aWillingnessForCdt;
	}

	/**
	 * Access method for hasOutstandingLoan.
	 *
	 * @return true if and only if hasOutstandingLoan is currently true
	 */
	public Boolean getHasOutstandingLoan() {
		return hasOutstandingLoan;
	}

	/**
	 * Setter method for hasOutstandingLoan.
	 *
	 * @param aHasOutstandingLoan the new value for hasOutstandingLoan
	 */
	public void setHasOutstandingLoan(Boolean aHasOutstandingLoan) {
		hasOutstandingLoan = aHasOutstandingLoan;
	}

	/**
	 * Access method for hasLifeInsurance.
	 *
	 * @return true if and only if hasLifeInsurance is currently true
	 */
	public Boolean getHasLifeInsurance() {
		return hasLifeInsurance;
	}

	/**
	 * Setter method for hasLifeInsurance.
	 *
	 * @param aHasLifeInsurance the new value for hasLifeInsurance
	 */
	public void setHasLifeInsurance(Boolean aHasLifeInsurance) {
		hasLifeInsurance = aHasLifeInsurance;
	}

	/**
	 * Access method for hasHealthInsurance.
	 *
	 * @return true if and only if hasHealthInsurance is currently true
	 */
	public Boolean getHasHealthInsurance() {
		return hasHealthInsurance;
	}

	/**
	 * Setter method for hasHealthInsurance.
	 *
	 * @param aHasHealthInsurance the new value for hasHealthInsurance
	 */
	public void setHasHealthInsurance(Boolean aHasHealthInsurance) {
		hasHealthInsurance = aHasHealthInsurance;
	}

	/**
	 * Access method for hasCropInsurance.
	 *
	 * @return true if and only if hasCropInsurance is currently true
	 */
	public Boolean getHasCropInsurance() {
		return hasCropInsurance;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreateDate() {
		return createdDate;
	}


	public Farmer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void setCreateDate(Date createDate) {
		this.createdDate = createDate;
	}
	

	public void setCreateDate(String createDate) {
		try {
			this.createdDate = sd.parse(createDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	

	public Integer getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public java.util.Date  getModifiedDate() {
		return modifiedDate;
	}


	public void setIsVip(Integer isVip) {
		this.isVip = isVip;
	}

	/**
	 * Setter method for hasCropInsurance.
	 *
	 * @param aHasCropInsurance the new value for hasCropInsurance
	 */
	public void setHasCropInsurance(Boolean aHasCropInsurance) {
		hasCropInsurance = aHasCropInsurance;
	}

	/**
	 * Access method for drkCust.
	 *
	 * @return true if and only if drkCust is currently true
	 */
	public Boolean getDrkCust() {
		return drkCust;
	}

	/**
	 * Setter method for drkCust.
	 *
	 * @param aDrkCust the new value for drkCust
	 */
	public void setDrkCust(Boolean aDrkCust) {
		drkCust = aDrkCust;
	}

	/**
	 * Access method for agriotaCust.
	 *
	 * @return true if and only if agriotaCust is currently true
	 */
	public Boolean getAgriotaCust() {
		return agriotaCust;
	}

	/**
	 * Setter method for agriotaCust.
	 *
	 * @param aAgriotaCust the new value for agriotaCust
	 */
	public void setAgriotaCust(Boolean aAgriotaCust) {
		agriotaCust = aAgriotaCust;
	}

	/**
	 * Access method for dueAmount.
	 *
	 * @return the current value of dueAmount
	 */
	public Float getDueAmount() {
		return dueAmount;
	}

	/**
	 * Setter method for dueAmount.
	 *
	 * @param aDueAmount the new value for dueAmount
	 */
	public void setDueAmount(Float aDueAmount) {
		dueAmount = aDueAmount;
	}

	/**
	 * Compares the key for this instance with another Farmer.
	 *
	 * @param other The object to compare to
	 * @return True if other object is instance of class Farmer and the key objects
	 *         are equal
	 */
	private boolean equalKeys(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Farmer)) {
			return false;
		}
		Farmer that = (Farmer) other;
		if (this.getFarmerId() != that.getFarmerId()) {
			return false;
		}
		return true;
	}

	/**
	 * Compares this instance with another Farmer.
	 *
	 * @param other The object to compare to
	 * @return True if the objects are the same
	 */
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Farmer))
			return false;
		return this.equalKeys(other) && ((Farmer) other).equalKeys(this);
	}

	/**
	 * Returns a debug-friendly String representation of this instance.
	 *
	 * @return String representation of this instance
	 */


	@Override
	public String toString() {
		return "Farmer [farmerId=" + farmerId + ", alternativeMobNumber=" + alternativeMobNumber + ", cropArea="
				+ cropArea + ", farmSize=" + farmSize + ", farmerFatherHusbandName=" + farmerFatherHusbandName
				+ ", farmerName=" + farmerName + ", hasGovernmentIdProof=" + hasGovernmentIdProof
				+ ", hasIrrigationLand=" + hasIrrigationLand + ", hasOwnLand=" + hasOwnLand + ", isVip=" + isVip
				+ ", landOwnershipImages=" + landOwnershipImages + ", majorCrop=" + majorCrop + ", mobileTypeId="
				+ mobileTypeId + ", primaryMobNumber=" + primaryMobNumber + ", referencePerson=" + referencePerson
				+ ", referencePersonMobileNumber=" + referencePersonMobileNumber + ", relationshipToReference="
				+ relationshipToReference + ", speakingLanguageId=" + speakingLanguageId + ", villageId=" + villageId
				+ ", willingnessForCdt=" + willingnessForCdt + ", hasOutstandingLoan=" + hasOutstandingLoan
				+ ", hasLifeInsurance=" + hasLifeInsurance + ", hasHealthInsurance=" + hasHealthInsurance
				+ ", hasCropInsurance=" + hasCropInsurance + ", drkCust=" + drkCust + ", agriotaCust=" + agriotaCust
				+ ", dueAmount=" + dueAmount + "]";
	}

	

	public void setModifiedDate(String modifiedDate) {
		try {
			this.modifiedDate = sd.parse(modifiedDate);
		} catch (ParseException e) {
			
		}
	}

}
