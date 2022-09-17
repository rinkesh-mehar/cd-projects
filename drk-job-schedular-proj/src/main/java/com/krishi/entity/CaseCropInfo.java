package com.krishi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.krishi.model.EntityModel;

import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name="case_crop_info")
public class CaseCropInfo implements Serializable, EntityModel {

    /** Primary key. */
    protected static final String PK = "id";

    @Id
    @Column(name="id")
    private String id;
    @Column(name="case_id")
    private String caseId;
    @Column(name="seed_source_id")
    private Integer seedSourceId;
    @Column(name="variety_id")
    private Integer varietyId;
    @Column(name="crop_area")
    private Float cropArea;
    @Column(name="seeds_sample_received")
    private Boolean seedsSampleReceived;
    @Column(name="seeds_rates")
    private Float seedsRates;
	/** after acz introduce -CDT-Ujwal*/
//    @Column(name="season_id")
//    private Integer seasonId;
    @Column(name="uom_id")
    private Integer uomId;
    @Column(name="spacing_row")
    private Float spacingRow;
    @Column(name="spacing_plant")
    private Float spacingPlant;
    @Column(name="corrected_sowing_week")
    private Integer correctedSowingWeek;
    @Column(name="corrected_sowing_year")
    private Integer correctedSowingYear;
    @Column(name = "harvest_week")
    private Integer harvestWeek;
    @Column(name = "harvest_year")
    private Integer harvestYear;
    
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

    /** changes added - CDT Rinkesh KM*/
    @Column(name = "acz_id")
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

//    added new field -- 11/10/21
    @Column(name = "seller_given_qty_ton")
    private Double sellerGivenQtyTon;

    @Column(name = "village_id")
    private Integer villageId;

    @Column(name = "current_phenophase_id")
    private Integer currentPhenophaseID;

    public Integer getVillageId()
    {
        return villageId;
    }

    public void setVillageId(Integer villageId)
    {
        this.villageId = villageId;
    }

    public Integer getAczId() {
        return aczId;
    }

    public void setAczId(Integer aczId) {
        this.aczId = aczId;
    }

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

	/** added new field in fls day end sync -End -CDT-Ujwal*/
    
    public CaseCropInfo() {
        super();
    }

    public static String getPK() {
        return PK;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public Integer getSeedSourceId() {
        return seedSourceId;
    }

    public void setSeedSourceId(Integer seedSourceId) {
        this.seedSourceId = seedSourceId;
    }

    public Integer getVarietyId() {
        return varietyId;
    }

    public void setVarietyId(Integer varietyId) {
        this.varietyId = varietyId;
    }

    public Float getCropArea() {
        return cropArea;
    }

    public void setCropArea(Float cropArea) {
        this.cropArea = cropArea;
    }

    public Boolean getSeedsSampleReceived() {
        return seedsSampleReceived;
    }

    public void setSeedsSampleReceived(Boolean seedsSampleReceived) {
        this.seedsSampleReceived = seedsSampleReceived;
    }

    public Float getSeedsRates() {
        return seedsRates;
    }

    public void setSeedsRates(Float seedsRates) {
        this.seedsRates = seedsRates;
    }

//    public Integer getSeasonId() {
//        return seasonId;
//    }
//
//    public void setSeasonId(Integer seasonId) {
//        this.seasonId = seasonId;
//    }

    public Integer getUomId() {
        return uomId;
    }

    public void setUomId(Integer uomId) {
        this.uomId = uomId;
    }

    public Float getSpacingRow() {
        return spacingRow;
    }

    public void setSpacingRow(Float spacingRow) {
        this.spacingRow = spacingRow;
    }

    public Float getSpacingPlant() {
        return spacingPlant;
    }

    public void setSpacingPlant(Float spacingPlant) {
        this.spacingPlant = spacingPlant;
    }

    public Integer getCorrectedSowingWeek() {
        return correctedSowingWeek;
    }

    public void setCorrectedSowingWeek(Integer correctedSowingWeek) {
        this.correctedSowingWeek = correctedSowingWeek;
    }

    public Integer getCorrectedSowingYear() {
        return correctedSowingYear;
    }

    public void setCorrectedSowingYear(Integer correctedSowingYear) {
        this.correctedSowingYear = correctedSowingYear;
    }

    public Integer getHarvestWeek() {
        return harvestWeek;
    }

    public void setHarvestWeek(Integer harvestWeek) {
        this.harvestWeek = harvestWeek;
    }

    public Integer getHarvestYear() {
        return harvestYear;
    }

    public void setHarvestYear(Integer harvestYear) {
        this.harvestYear = harvestYear;
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

    public Double getSellerGivenQtyTon()
    {
        return sellerGivenQtyTon;
    }

    public void setSellerGivenQtyTon(Double sellerGivenQtyTon)
    {
        this.sellerGivenQtyTon = sellerGivenQtyTon;
    }

    public Integer getCurrentPhenophaseID()
    {
        return currentPhenophaseID;
    }

    public void setCurrentPhenophaseID(Integer currentPhenophaseID)
    {
        this.currentPhenophaseID = currentPhenophaseID;
    }
}
