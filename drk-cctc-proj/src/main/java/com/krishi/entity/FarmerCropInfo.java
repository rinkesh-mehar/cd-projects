package com.krishi.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author CDT-Ujwal
 *
 */
@Entity
@Table(name = "farmer_crop_info")
public class FarmerCropInfo implements Serializable {

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
	
	@Column(name = "sowing_week")
	private Integer sowingWeek;
	
	@Column(name = "harvest_week")
	private Integer harvestWeek;
	
	@Column(name = "harvest_year")
	private Integer harvestYear;
	
	@Column(name = "sowing_year")
	private Integer sowingYear;

	@Column(name = "crop_type_id")
	private Integer cropType;

	@Column(name = "lead_calling_status")
	private Integer leadCallingStatus;

	@Column(name = "lead_response")
	private Integer leadResponse;

	@Column(name = "date_of_availability")
	private Date dateOfAvailability;

	@Column(name = "seller_given_qty_ton")
	private Double sellerGivenQtyTon;

	@Column(name = "local_name_of_variety")
	private String alternateVariety;

	@Column(name = "sowing_date")
	private Date sowingDate;

	public Date getSowingDate() {
		return sowingDate;
	}

	public void setSowingDate(Date sowingDate) {
		this.sowingDate = sowingDate;
	}

	@Transient
	private Date sowing;

	@Transient
	private Date harvest;

	@Transient
	private Integer userId;

	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getAlternateVariety() {
		return alternateVariety;
	}
	public void setAlternateVariety(String alternateVariety) {
		this.alternateVariety = alternateVariety;
	}

	public Double getSellerGivenQtyTon() {
		return sellerGivenQtyTon;
	}

	public void setSellerGivenQtyTon(Double sellerGivenQtyTon) {
		this.sellerGivenQtyTon = sellerGivenQtyTon;
	}

	public Date getDateOfAvailability() {
		return dateOfAvailability;
	}

	public void setDateOfAvailability(Date dateOfAvailability) {
		this.dateOfAvailability = dateOfAvailability;
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
	
	public String getCropInfoId() {
		return cropInfoId;
	}

	public void setCropInfoId(String cropInfoId) {
		this.cropInfoId = cropInfoId;
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

	public Date getSowing() {
		return sowing;
	}

	public void setSowing(Date sowing) {
		this.sowing = sowing;
	}

	public Date getHarvest() {
		return harvest;
	}

	public void setHarvest(Date harvest) {
		this.harvest = harvest;
	}

	public Integer getCropType() {
		return cropType;
	}

	public void setCropType(Integer cropType) {
		this.cropType = cropType;
	}

	public Integer getLeadCallingStatus() {
		return leadCallingStatus;
	}

	public void setLeadCallingStatus(Integer leadCallingStatus) {
		this.leadCallingStatus = leadCallingStatus;
	}

	public Integer getLeadResponse() {
		return leadResponse;
	}

	public void setLeadResponse(Integer leadResponse) {
		this.leadResponse = leadResponse;
	}
}
