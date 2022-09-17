// Generated with g9.

package com.drkrishi.rlt.entity;

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
@Table(name="farmer")
public class Farmer implements Serializable {

    /** Primary key. */
    protected static final String PK = "farmerId";

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id", unique=true, nullable=false, precision=10)
    private String farmerId;
    @Column(name="alternative_mob_number", precision=19)
    private String alternativeMobNumber;
    @Column(name="crop_area", nullable=false, length=22)
    private double cropArea;
    @Column(name="farm_size", length=22)
    private double farmSize;
    @Column(name="farmer_father_husband_name", length=255)
    private String farmerFatherHusbandName;
    @Column(name="farmer_name", length=255)
    private String farmerName;
    @Column(name="has_government_id_proof", precision=3)
    private short hasGovernmentIdProof;
    @Column(name="has_irrigation_land", precision=3)
    private short hasIrrigationLand;
    @Column(name="has_own_land", precision=3)
    private short hasOwnLand;
    @Column(name="is_vip", precision=3)
    private short isVip;
    @Column(name="land_ownership_images", length=255)
    private String landOwnershipImages;
    @Column(name="major_crop")
    private String majorCrop;
    @Column(name="mobile_type_id", precision=10)
    private int mobileTypeId;
    @Column(name="primary_mob_number", precision=19)
    private String primaryMobNumber;
    @Column(name="reference_person", length=255)
    private String referencePerson;
    @Column(name="reference_person_mobile_number", precision=19)
    private String referencePersonMobileNumber;
    @Column(name="relationship_to_reference", length=255)
    private String relationshipToReference;
    @Column(name="speaking_language_id", precision=10)
    private int speakingLanguageId;
    @Column(name="village_id", precision=10)
    private int villageId;
    @Column(name="willingness_for_cdt", precision=3)
    private short willingnessForCdt;
    @Column(name="has_outstanding_loan", length=3)
    private boolean hasOutstandingLoan;
    @Column(name="has_life_insurance", length=3)
    private boolean hasLifeInsurance;
    @Column(name="has_health_insurance", length=3)
    private boolean hasHealthInsurance;
    @Column(name="has_crop_insurance", length=3)
    private boolean hasCropInsurance;
    @Column(name="is_drk_cust", length=3)
    private boolean isDrkCust;
    @Column(name="is_agriota_cust", length=3)
    private boolean isAgriotaCust;
    @Column(name="due_amount", nullable=false, precision=8, scale=2)
    private float dueAmount;

    /** Default constructor. */
    public Farmer() {
        super();
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
    public double getCropArea() {
        return cropArea;
    }

    /**
     * Setter method for cropArea.
     *
     * @param aCropArea the new value for cropArea
     */
    public void setCropArea(double aCropArea) {
        cropArea = aCropArea;
    }

    /**
     * Access method for farmSize.
     *
     * @return the current value of farmSize
     */
    public double getFarmSize() {
        return farmSize;
    }

    /**
     * Setter method for farmSize.
     *
     * @param aFarmSize the new value for farmSize
     */
    public void setFarmSize(double aFarmSize) {
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
    public short getHasGovernmentIdProof() {
        return hasGovernmentIdProof;
    }

    /**
     * Setter method for hasGovernmentIdProof.
     *
     * @param aHasGovernmentIdProof the new value for hasGovernmentIdProof
     */
    public void setHasGovernmentIdProof(short aHasGovernmentIdProof) {
        hasGovernmentIdProof = aHasGovernmentIdProof;
    }

    /**
     * Access method for hasIrrigationLand.
     *
     * @return the current value of hasIrrigationLand
     */
    public short getHasIrrigationLand() {
        return hasIrrigationLand;
    }

    /**
     * Setter method for hasIrrigationLand.
     *
     * @param aHasIrrigationLand the new value for hasIrrigationLand
     */
    public void setHasIrrigationLand(short aHasIrrigationLand) {
        hasIrrigationLand = aHasIrrigationLand;
    }

    /**
     * Access method for hasOwnLand.
     *
     * @return the current value of hasOwnLand
     */
    public short getHasOwnLand() {
        return hasOwnLand;
    }

    /**
     * Setter method for hasOwnLand.
     *
     * @param aHasOwnLand the new value for hasOwnLand
     */
    public void setHasOwnLand(short aHasOwnLand) {
        hasOwnLand = aHasOwnLand;
    }

    /**
     * Access method for isVip.
     *
     * @return the current value of isVip
     */
    public short getIsVip() {
        return isVip;
    }

    /**
     * Setter method for isVip.
     *
     * @param aIsVip the new value for isVip
     */
    public void setIsVip(short aIsVip) {
        isVip = aIsVip;
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
    public int getMobileTypeId() {
        return mobileTypeId;
    }

    /**
     * Setter method for mobileTypeId.
     *
     * @param aMobileTypeId the new value for mobileTypeId
     */
    public void setMobileTypeId(int aMobileTypeId) {
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
     * @param aReferencePersonMobileNumber the new value for referencePersonMobileNumber
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
    public int getSpeakingLanguageId() {
        return speakingLanguageId;
    }

    /**
     * Setter method for speakingLanguageId.
     *
     * @param aSpeakingLanguageId the new value for speakingLanguageId
     */
    public void setSpeakingLanguageId(int aSpeakingLanguageId) {
        speakingLanguageId = aSpeakingLanguageId;
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
     * Access method for willingnessForCdt.
     *
     * @return the current value of willingnessForCdt
     */
    public short getWillingnessForCdt() {
        return willingnessForCdt;
    }

    /**
     * Setter method for willingnessForCdt.
     *
     * @param aWillingnessForCdt the new value for willingnessForCdt
     */
    public void setWillingnessForCdt(short aWillingnessForCdt) {
        willingnessForCdt = aWillingnessForCdt;
    }

    /**
     * Access method for hasOutstandingLoan.
     *
     * @return true if and only if hasOutstandingLoan is currently true
     */
    public boolean getHasOutstandingLoan() {
        return hasOutstandingLoan;
    }

    /**
     * Setter method for hasOutstandingLoan.
     *
     * @param aHasOutstandingLoan the new value for hasOutstandingLoan
     */
    public void setHasOutstandingLoan(boolean aHasOutstandingLoan) {
        hasOutstandingLoan = aHasOutstandingLoan;
    }

    /**
     * Access method for hasLifeInsurance.
     *
     * @return true if and only if hasLifeInsurance is currently true
     */
    public boolean getHasLifeInsurance() {
        return hasLifeInsurance;
    }

    /**
     * Setter method for hasLifeInsurance.
     *
     * @param aHasLifeInsurance the new value for hasLifeInsurance
     */
    public void setHasLifeInsurance(boolean aHasLifeInsurance) {
        hasLifeInsurance = aHasLifeInsurance;
    }

    /**
     * Access method for hasHealthInsurance.
     *
     * @return true if and only if hasHealthInsurance is currently true
     */
    public boolean getHasHealthInsurance() {
        return hasHealthInsurance;
    }

    /**
     * Setter method for hasHealthInsurance.
     *
     * @param aHasHealthInsurance the new value for hasHealthInsurance
     */
    public void setHasHealthInsurance(boolean aHasHealthInsurance) {
        hasHealthInsurance = aHasHealthInsurance;
    }

    /**
     * Access method for hasCropInsurance.
     *
     * @return true if and only if hasCropInsurance is currently true
     */
    public boolean getHasCropInsurance() {
        return hasCropInsurance;
    }

    /**
     * Setter method for hasCropInsurance.
     *
     * @param aHasCropInsurance the new value for hasCropInsurance
     */
    public void setHasCropInsurance(boolean aHasCropInsurance) {
        hasCropInsurance = aHasCropInsurance;
    }

    /**
     * Access method for drkCust.
     *
     * @return true if and only if drkCust is currently true
     */
    public boolean getDrkCust() {
        return isDrkCust;
    }

    /**
     * Setter method for drkCust.
     *
     * @param aDrkCust the new value for drkCust
     */
    public void setDrkCust(boolean aDrkCust) {
        isDrkCust = aDrkCust;
    }

    /**
     * Access method for agriotaCust.
     *
     * @return true if and only if agriotaCust is currently true
     */
    public boolean getAgriotaCust() {
        return isAgriotaCust;
    }

    /**
     * Setter method for agriotaCust.
     *
     * @param aAgriotaCust the new value for agriotaCust
     */
    public void setAgriotaCust(boolean aAgriotaCust) {
        isAgriotaCust = aAgriotaCust;
    }

    /**
     * Access method for dueAmount.
     *
     * @return the current value of dueAmount
     */
    public float getDueAmount() {
        return dueAmount;
    }

    /**
     * Setter method for dueAmount.
     *
     * @param aDueAmount the new value for dueAmount
     */
    public void setDueAmount(float aDueAmount) {
        dueAmount = aDueAmount;
    }

    /**
     * Compares the key for this instance with another Farmer.
     *
     * @param other The object to compare to
     * @return True if other object is instance of class Farmer and the key objects are equal
     */
    private boolean equalKeys(Object other) {
        if (this==other) {
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
        if (!(other instanceof Farmer)) return false;
        return this.equalKeys(other) && ((Farmer)other).equalKeys(this);
    }


    /**
     * Returns a debug-friendly String representation of this instance.
     *
     * @return String representation of this instance
     */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("[Farmer |");
        sb.append(" farmerId=").append(getFarmerId());
        sb.append("]");
        return sb.toString();
    }

    /**
     * Return all elements of the primary key.
     *
     * @return Map of key names to values
     */
    public Map<String, Object> getPrimaryKey() {
        Map<String, Object> ret = new LinkedHashMap<String, Object>(6);
        ret.put("farmerId", Integer.valueOf(getFarmerId()));
        return ret;
    }

}
