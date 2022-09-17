package com.krishi.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.krishi.model.EntityModel;

/**
 * @author CDT-Ujwal
 *
 */
@Entity
@Table(name = "farmer_crop_info")
public class FarmerCropInfo implements EntityModel {

	@Id
	@Column(name = "id")
	private String cropInfoId;

	@Column(name = "farmer_id")
	private String farmerId;

//	@Column(name = "season_id")
//	private Integer seasonId;

	@Column(name = "commodity_id")
	private Integer commodityId;

	@Column(name = "variety_id")
	private Integer varietyId;

	@Column(name = "cropping_area")
	private double croppingArea;

	@Column(name = "yield")
	private double yield;

	@Column(name = "has_irrigation")
	private Integer hasIrrigation;

	/* after acz introduce -CDT-Ujwal*/
	@Column(name = "sowing_week")
	private Integer sowingWeek;
	
	@Column(name = "harvest_week")
	private Integer harvestWeek;
	
	@Column(name = "harvest_year")
	private Integer harvestYear;
	
	@Column(name = "sowing_year")
	private Integer sowingYear;
	
	/** added new field in sync prs - CDT - Ujwal - Start */
	@Column(name = "local_name_of_variety")
	private String alternateVarietyName;

	@Column(name = "other_variety_id")
	private Integer otherVarietyId;

	@Column(name = "village_id")
	private Integer villageId;

	@Column(name = "acz_id")
	private Integer aczId;

	@Column(name = "leadCalling_status")
	private int leadCallingStatus;

	@Column(name = "seller_given_qty_ton")
	private Double sellerGivenQuantity;

	@Column(name = "date_of_availability")
	private Date dateOfAvailability;

	@Column(name = "crop_type_id")
	private Integer cropTypeId;

	@Column(name = "sowing_date")
	private Date sowingDate;

	public Date getSowingDate() {
		return sowingDate;
	}

	public void setSowingDate(Date sowingDate) {
		this.sowingDate = sowingDate;
	}

	public Integer getOtherVarietyId() {
		return otherVarietyId;
	}

	public void setOtherVarietyId(Integer otherVarietyId) {
		this.otherVarietyId = otherVarietyId;
	}

	public Integer getAczId() {
		return aczId;
	}

	public void setAczId(Integer aczId) {
		this.aczId = aczId;
	}

	public String getAlternateVarietyName() {
		return alternateVarietyName;
	}

	public void setAlternateVarietyName(String alternateVarietyName) {
		this.alternateVarietyName = alternateVarietyName;
	}


	public Double getSellerGivenQuantity() {
		return sellerGivenQuantity;
	}

	public void setSellerGivenQuantity(Double sellerGivenQuantity) {
		this.sellerGivenQuantity = sellerGivenQuantity;
	}

	public Integer getCropTypeId() {
		return cropTypeId;
	}

	public void setCropTypeId(Integer cropTypeId) {
		this.cropTypeId = cropTypeId;
	}


	public Date getDateOfAvailability() {
		return dateOfAvailability;
	}

	public void setDateOfAvailability(Date dateOfAvailability) {
		this.dateOfAvailability = dateOfAvailability;
	}

	/** added new field in sync prs - CDT - Ujwal - End */

	public String getId() {
		return cropInfoId;
	}

	public void setId(String id) {
		this.cropInfoId = id;
	}

	public String getCropInfoId() {
		return cropInfoId;
	}

	public void setCropInfoId(String cropInfoId) {
		this.cropInfoId = cropInfoId;
	}

	public String getFarmerId() {
		return farmerId;
	}

	public void setFarmerId(String farmerId) {
		this.farmerId = farmerId;
	}

//	public Integer getSeasonId() {
//		return seasonId;
//	}
//
//	public void setSeasonId(Integer seasonId) {
//		this.seasonId = seasonId;
//	}

	
	public Integer getCommodityId() {
		return commodityId;
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

	public void setHarvetWeek(Integer harvestWeek) {
		this.harvestWeek = harvestWeek;
	}

	public void setCommodityId(Integer commodityId) {
		this.commodityId = commodityId;
	}

	public Integer getVarietyId() {
		return varietyId;
	}

	public void setVarietyId(Integer varietyId) {
		this.varietyId = varietyId;
	}

	public double getCroppingArea() {
		return croppingArea;
	}

	public void setCroppingArea(double croppingArea) {
		this.croppingArea = croppingArea;
	}

	public double getYield() {
		return yield;
	}

	public void setYield(double yield) {
		this.yield = yield;
	}

	public Integer getHasIrrigation() {
		return hasIrrigation;
	}

	public void setHasIrrigation(Integer hasIrrigation) {
		this.hasIrrigation = hasIrrigation;
	}
	
	

	public Integer getHarvestYear() {
		return harvestYear;
	}

	public void setHarvestYear(Integer harvestYear) {
		this.harvestYear = harvestYear;
	}

	public Integer getSowingYear() {
		return sowingYear;
	}

	public void setSowingYear(Integer sowingYear) {
		this.sowingYear = sowingYear;
	}

	public int getLeadCallingStatus() {
		return leadCallingStatus;
	}

	public void setLeadCallingStatus(int leadCallingStatus) {
		this.leadCallingStatus = leadCallingStatus;
	}

	public Integer getVillageId()
	{
		return villageId;
	}

	public void setVillageId(Integer villageId)
	{
		this.villageId = villageId;
	}
}
