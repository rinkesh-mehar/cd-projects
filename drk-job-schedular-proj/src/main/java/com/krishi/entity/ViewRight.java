package com.krishi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="view_right")
public class ViewRight {

	@Id
	@Column(name = "case_id")
	private String caseId;
	
//	@Column(name = "expected_yield")
//	private Double expectedYield; 
//    
//    @Column(name = "field_status")
//	private String fieldStatus; 
//    
//    @Column(name = "ndvi_images")
//	private String ndviImages; 
    
    @Column(name = "season_id")
	private Integer seasonId; 
    
    @Column(name = "variety_id")
   	private Integer varietyId; 
    
    @Column(name = "sowing_week")
   	private Integer sowingWeek; 
    
    @Column(name = "harvest_week")
   	private Integer harvestWeek; 
    
    @Column(name = "crop_area")
   	private Double cropArea; 
    
    @Column(name = "variety_name")
   	private String variety_name;
    
    @Column(name = "commodity_id")
   	private Integer commodityId;
    
    @Column(name = "hscode")
   	private String hscode;

    @Column(name = "commodity_name")
   	private String commodityName;
    
    @Column(name = "season_name")
   	private String season_name;
    
    @Column(name = "farmer_id")
   	private String farmerId;
    
    @Column(name = "is_pennydropped")
   	private Boolean isPennydropped;
    
    @Column(name = "is_kyc_verified")
   	private Boolean isKycVerified;

    @Column(name = "is_kml_verified")
   	private Boolean isKmlVerified;

    @Column(name = "farmer_due_amount")
   	private Double farmerDueAmount;
    
    @Column(name = "village_id")
   	private Integer villageId;
    
    @Column(name = "village_name")
   	private String villageName;
    
    @Column(name = "subregion_id")
   	private Integer subregionId;
    
    @Column(name = "region_id")
   	private Integer regionId;
    
    @Column(name = "region_name")
   	private String regionName;
    
    @Column(name = "state_code")
   	private Integer stateCode;
    
    @Column(name = "state_name")
   	private String stateName;
    
    @Column(name = "district_id")
   	private Integer districtId;
    
    @Column(name = "district_name")
   	private String districtName;
    
    @Column(name = "farmer_email")
   	private String farmerEmail;
    
    @Column(name = "farmer_account_number")
   	private String farmerAccountNumber;
    
    @Column(name = "domestic_restrictions")
   	private String domesticRestrictions;
    
    @Column(name = "international_restrictions")
   	private String internationalRestrictions;

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

//	public Double getExpectedYield() {
//		return expectedYield;
//	}
//
//	public void setExpectedYield(Double expectedYield) {
//		this.expectedYield = expectedYield;
//	}
//
//	public String getFieldStatus() {
//		return fieldStatus;
//	}
//
//	public void setFieldStatus(String fieldStatus) {
//		this.fieldStatus = fieldStatus;
//	}
//
//	public String getNdviImages() {
//		return ndviImages;
//	}
//
//	public void setNdviImages(String ndviImages) {
//		this.ndviImages = ndviImages;
//	}

	public Integer getSeasonId() {
		return seasonId;
	}

	public void setSeasonId(Integer seasonId) {
		this.seasonId = seasonId;
	}

	public Integer getVarietyId() {
		return varietyId;
	}

	public void setVarietyId(Integer varietyId) {
		this.varietyId = varietyId;
	}

	public Integer getSowingWeek() {
		return sowingWeek;
	}

	public void setSowingWeek(Integer sowingWeek) {
		this.sowingWeek = sowingWeek;
	}

	public Integer getHarvestWeek() {
		return harvestWeek;
	}

	public void setHarvestWeek(Integer harvestWeek) {
		this.harvestWeek = harvestWeek;
	}

	public Double getCropArea() {
		return cropArea;
	}

	public void setCropArea(Double cropArea) {
		this.cropArea = cropArea;
	}

	public String getVariety_name() {
		return variety_name;
	}

	public void setVariety_name(String variety_name) {
		this.variety_name = variety_name;
	}

	public Integer getCommodityId() {
		return commodityId;
	}

	public void setCommodityId(Integer commodityId) {
		this.commodityId = commodityId;
	}

	public String getHscode() {
		return hscode;
	}

	public void setHscode(String hscode) {
		this.hscode = hscode;
	}

	public String getCommodityName() {
		return commodityName;
	}

	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}

	public String getSeason_name() {
		return season_name;
	}

	public void setSeason_name(String season_name) {
		this.season_name = season_name;
	}

	public String getFarmerId() {
		return farmerId;
	}

	public void setFarmerId(String farmerId) {
		this.farmerId = farmerId;
	}

	public Double getFarmerDueAmount() {
		return farmerDueAmount;
	}

	public void setFarmerDueAmount(Double farmerDueAmount) {
		this.farmerDueAmount = farmerDueAmount;
	}

	public Integer getVillageId() {
		return villageId;
	}

	public void setVillageId(Integer villageId) {
		this.villageId = villageId;
	}

	public String getVillageName() {
		return villageName;
	}

	public void setVillageName(String villageName) {
		this.villageName = villageName;
	}

	public Integer getSubregionId() {
		return subregionId;
	}

	public void setSubregionId(Integer subregionId) {
		this.subregionId = subregionId;
	}

	public Integer getRegionId() {
		return regionId;
	}

	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public Integer getStateCode() {
		return stateCode;
	}

	public void setStateCode(Integer stateCode) {
		this.stateCode = stateCode;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public Integer getDistrictId() {
		return districtId;
	}

	public void setDistrictId(Integer districtId) {
		this.districtId = districtId;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getFarmerEmail() {
		return farmerEmail;
	}

	public void setFarmerEmail(String farmerEmail) {
		this.farmerEmail = farmerEmail;
	}

	public String getFarmerAccountNumber() {
		return farmerAccountNumber;
	}

	public void setFarmerAccountNumber(String farmerAccountNumber) {
		this.farmerAccountNumber = farmerAccountNumber;
	}

	public Boolean getIsPennydropped() {
		return isPennydropped;
	}

	public void setIsPennydropped(Boolean isPennydropped) {
		this.isPennydropped = isPennydropped;
	}

	public Boolean getIsKycVerified() {
		return isKycVerified;
	}

	public void setIsKycVerified(Boolean isKycVerified) {
		this.isKycVerified = isKycVerified;
	}

	public Boolean getIsKmlVerified() {
		return isKmlVerified;
	}

	public void setIsKmlVerified(Boolean isKmlVerified) {
		this.isKmlVerified = isKmlVerified;
	}

	public String getDomesticRestrictions() {
		return domesticRestrictions;
	}

	public void setDomesticRestrictions(String domesticRestrictions) {
		this.domesticRestrictions = domesticRestrictions;
	}

	public String getInternationalRestrictions() {
		return internationalRestrictions;
	}

	public void setInternationalRestrictions(String internationalRestrictions) {
		this.internationalRestrictions = internationalRestrictions;
	}
    
}
