package com.krishi.fls.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="case_crop_info")
public class CaseCropInfo implements Serializable {

    /** Primary key. */
    protected static final String PK = "id";

    @Id
    @Column(name="id")
    private String id;
    @Column(name="case_id")
    private String caseId;
    @Column(name="seed_source_id")
    private int seedSourceId;
    @Column(name="variety_id")
    private int varietyId;
    @Column(name="crop_area")
    private float cropArea;
    @Column(name="seeds_sample_received")
    private Integer seedsSampleReceived;
    @Column(name="seeds_rates")
    private float seedsRates;
    
	/** after acz introduce- CDT-Ujwal */
    @Column(name="uom_id")
    private int uomId;
    @Column(name="spacing_row")
    private float spacingRow;
    @Column(name="spacing_plant")
    private float spacingPlant;
    @Column(name="corrected_sowing_week")
    private int correctedSowingWeek;
    @Column(name="corrected_sowing_year")
    private int correctedSowingYear;
    /** added acz id - CDT Rinkesh KM*/
    @Column(name="acz_id")
    private Integer aczId;

    @Column(name = "expected_harvest_start_date")
    private Date expectedHarvestStartDate;

    @Column(name = "expected_harvest_end_date")
    private Date expectedHarvestEndDate;

    @Column(name = "confirmed_harvest_date")
    private Date confirmedHarvestDate;

    @Column(name = "farmer_given_sowing_date")
    private Date farmerGivenSowingDate;

    @Column(name = "corrected_sowing_date")
    private Date correctedSowingDate;

    /** Default constructor. */
    public CaseCropInfo() {
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

    public Integer getAczId() {
        return aczId;
    }

    public void setAczId(Integer aczId) {
        this.aczId = aczId;
    }

    /**
     * Access method for caseId.
     *
     * @return the current value of caseId
     */
    public String getCaseId() {
        return caseId;
    }

    /**
     * Setter method for caseId.
     *
     * @param aCaseId the new value for caseId
     */
    public void setCaseId(String aCaseId) {
        caseId = aCaseId;
    }

    /**
     * Access method for seedSourceId.
     *
     * @return the current value of seedSourceId
     */
    public int getSeedSourceId() {
        return seedSourceId;
    }

    /**
     * Setter method for seedSourceId.
     *
     * @param aSeedSourceId the new value for seedSourceId
     */
    public void setSeedSourceId(int aSeedSourceId) {
        seedSourceId = aSeedSourceId;
    }

    /**
     * Access method for varietyId.
     *
     * @return the current value of varietyId
     */
    public int getVarietyId() {
        return varietyId;
    }

    /**
     * Setter method for varietyId.
     *
     * @param aVarietyId the new value for varietyId
     */
    public void setVarietyId(int aVarietyId) {
        varietyId = aVarietyId;
    }

    /**
     * Access method for cropArea.
     *
     * @return the current value of cropArea
     */
    public float getCropArea() {
        return cropArea;
    }

    /**
     * Setter method for cropArea.
     *
     * @param aCropArea the new value for cropArea
     */
    public void setCropArea(float aCropArea) {
        cropArea = aCropArea;
    }

    /**
     * Access method for seedsSampleReceived.
     *
     * @return true if and only if seedsSampleReceived is currently true
     */
    public Integer getSeedsSampleReceived() {
        return seedsSampleReceived;
    }

    /**
     * Setter method for seedsSampleReceived.
     *
     * @param aSeedsSampleReceived the new value for seedsSampleReceived
     */
    public void setSeedsSampleReceived(Integer aSeedsSampleReceived) {
        seedsSampleReceived = aSeedsSampleReceived;
    }

    /**
     * Access method for seedsRates.
     *
     * @return the current value of seedsRates
     */
    public float getSeedsRates() {
        return seedsRates;
    }

    /**
     * Setter method for seedsRates.
     *
     * @param aSeedsRates the new value for seedsRates
     */
    public void setSeedsRates(float aSeedsRates) {
        seedsRates = aSeedsRates;
    }

    /**
     * Access method for uomId.
     *
     * @return the current value of uomId
     */
    public int getUomId() {
        return uomId;
    }

    /**
     * Setter method for uomId.
     *
     * @param aUomId the new value for uomId
     */
    public void setUomId(int aUomId) {
        uomId = aUomId;
    }

    /**
     * Access method for spacingRow.
     *
     * @return the current value of spacingRow
     */
    public float getSpacingRow() {
        return spacingRow;
    }

    /**
     * Setter method for spacingRow.
     *
     * @param aSpacingRow the new value for spacingRow
     */
    public void setSpacingRow(float aSpacingRow) {
        spacingRow = aSpacingRow;
    }

    /**
     * Access method for spacingPlant.
     *
     * @return the current value of spacingPlant
     */
    public float getSpacingPlant() {
        return spacingPlant;
    }

    /**
     * Setter method for spacingPlant.
     *
     * @param aSpacingPlant the new value for spacingPlant
     */
    public void setSpacingPlant(float aSpacingPlant) {
        spacingPlant = aSpacingPlant;
    }

    /**
     * Access method for sowingWeek.
     *
     * @return the current value of sowingWeek
     */
    public int getCorrectedSowingWeek() {
        return correctedSowingWeek;
    }

    public void setCorrectedSowingWeek(int correctedSowingWeek) {
        this.correctedSowingWeek = correctedSowingWeek;
    }

    public int getCorrectedSowingYear() {
        return correctedSowingYear;
    }

    public void setCorrectedSowingYear(int correctedSowingYear) {
        this.correctedSowingYear = correctedSowingYear;
    }

    /** added new field in fls day end sync -Start -CDT-Ujwal*/
    @Column(name="farmer_given_sowing_week")
    private Integer farmerGivenSowingWeek;
    
    @Column(name="farmer_given_sowing_year")
    private Integer farmerGivenSowingYear;

    @Column(name = "logistic_hub_id")
    private String logisticHubId;
    
    @Column(name = "warehouse_doc_url")
    private String warehouseReceiptPhoto;
    
    @Column(name = "stack_number")
    private String stackNumber;
    
    @Column(name = "packaged_in")
    private Integer packagedId;
    
    @Column(name = "no_of_bags_packs")
    private Integer noOfBagsPacks;
    
    @Column(name = "capacity_packs")
    private Double capacityPacks;
    
    @Column(name = "date_of_deposit")
    private Date dateOfDeposit;
    
    @Column(name = "storage_duration")
    private Integer storageDuration;
    
    
	public String getLogisticHubId() {
		return logisticHubId;
	}

	public void setLogisticHubId(String logisticHubId) {
		this.logisticHubId = logisticHubId;
	}

	public String getWarehouseReceiptPhoto() {
		return warehouseReceiptPhoto;
	}

	public void setWarehouseReceiptPhoto(String warehouseReceiptPhoto) {
		this.warehouseReceiptPhoto = warehouseReceiptPhoto;
	}

	public String getStackNumber() {
		return stackNumber;
	}

	public void setStackNumber(String stackNumber) {
		this.stackNumber = stackNumber;
	}

	public Integer getPackagedId() {
		return packagedId;
	}

	public void setPackagedId(Integer packagedId) {
		this.packagedId = packagedId;
	}

	public Integer getNoOfBagsPacks() {
		return noOfBagsPacks;
	}

	public void setNoOfBagsPacks(Integer noOfBagsPacks) {
		this.noOfBagsPacks = noOfBagsPacks;
	}

	public Double getCapacityPacks() {
		return capacityPacks;
	}

	public void setCapacityPacks(Double capacityPacks) {
		this.capacityPacks = capacityPacks;
	}

	public Date getDateOfDeposit() {
		return dateOfDeposit;
	}

	public void setDateOfDeposit(Date dateOfDeposit) {
		this.dateOfDeposit = dateOfDeposit;
	}

	public Integer getStorageDuration() {
		return storageDuration;
	}

	public void setStorageDuration(Integer storageDuration) {
		this.storageDuration = storageDuration;
	}

    public Integer getFarmerGivenSowingWeek() {
        return farmerGivenSowingWeek;
    }

    public void setFarmerGivenSowingWeek(Integer farmerGivenSowingWeek) {
        this.farmerGivenSowingWeek = farmerGivenSowingWeek;
    }

    public Integer getFarmerGivenSowingYear() {
        return farmerGivenSowingYear;
    }

    public void setFarmerGivenSowingYear(Integer farmerGivenSowingYear) {
        this.farmerGivenSowingYear = farmerGivenSowingYear;
    }

    /** added new field in fls day end sync -End -CDT-Ujwal*/

    public Date getExpectedHarvestStartDate() {
        return expectedHarvestStartDate;
    }

    public void setExpectedHarvestStartDate(Date expectedHarvestStartDate) {
        this.expectedHarvestStartDate = expectedHarvestStartDate;
    }

    public Date getExpectedHarvestEndDate() {
        return expectedHarvestEndDate;
    }

    public void setExpectedHarvestEndDate(Date expectedHarvestEndDate) {
        this.expectedHarvestEndDate = expectedHarvestEndDate;
    }

    public Date getConfirmedHarvestDate() {
        return confirmedHarvestDate;
    }

    public void setConfirmedHarvestDate(Date confirmedHarvestDate) {
        this.confirmedHarvestDate = confirmedHarvestDate;
    }

    public Date getFarmerGivenSowingDate() {
        return farmerGivenSowingDate;
    }

    public void setFarmerGivenSowingDate(Date farmerGivenSowingDate) {
        this.farmerGivenSowingDate = farmerGivenSowingDate;
    }

    public Date getCorrectedSowingDate() {
        return correctedSowingDate;
    }

    public void setCorrectedSowingDate(Date correctedSowingDate) {
        this.correctedSowingDate = correctedSowingDate;
    }
}
