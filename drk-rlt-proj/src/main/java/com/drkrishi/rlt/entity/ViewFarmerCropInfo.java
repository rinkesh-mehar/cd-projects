package com.drkrishi.rlt.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author RinkeshKM
 * @Date 05/08/21
 */

@Entity
@Table(name = "farmer_crop_detail_info")
public class ViewFarmerCropInfo {

	@Id
	private String cropInfoId;
	
	private String taskId;
	
	private String farmerId;
	
//	private Integer seasonId;

	private String sowing;

	private String harvest;

	private Integer  commodityId;
	
	private String commodityName;
	
	private Integer varietyId;
	
	private Double croppingArea;
	
	private Double yield;
	
	private Integer hasIrrigation;
	
	private String alternateVariety;
	
	private Double sellerGivenQtyTon ;
	
	private String dateOfAvailability;
	
//	private String seasonName;
	
	private String varietyName;

	private Integer cropTypeId;

	private Integer leadResponse;

	@Transient
	private Integer zonalCommodityId;

	public Integer getZonalCommodityId() {
		return zonalCommodityId;
	}

	public void setZonalCommodityId(Integer zonalCommodityId) {
		this.zonalCommodityId = zonalCommodityId;
	}

	public Integer getCropTypeId() {
		return cropTypeId;
	}

	public void setCropTypeId(Integer cropTypeId) {
		this.cropTypeId = cropTypeId;
	}

	/** added fields - CDT- RinkeshKM*/
	private String cropType;

	public String getCropType() {
		return cropType;
	}

	public void setCropType(String cropType) {
		this.cropType = cropType;
	}

	public String getCropInfoId() {
		return cropInfoId;
	}

	public void setCropInfoId(String cropInfoId) {
		this.cropInfoId = cropInfoId;
	}
	
	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getFarmerId() {
		return farmerId;
	}

	public void setFarmerId(String farmerId) {
		this.farmerId = farmerId;
	}

	/** after acz introduce- CDT-Ujwal*/
//	public Integer getSeasonId() {
//		return seasonId;
//	}
//
//	public void setSeasonId(Integer seasonId) {
//		this.seasonId = seasonId;
//	}



	public String getSowing() {
		return sowing;
	}

	public String getHarvest() {
		return harvest;
	}

	public void setHarvest(String harvest) {
		this.harvest = harvest;
	}

	public void setSowing(String sowing) {
		this.sowing = sowing;
	}

	public Integer getCommodityId() {
		return commodityId;
	}

	public void setCommodityId(Integer commodityId) {
		this.commodityId = commodityId;
	}
	
	public String getCommodityName() {
		return commodityName;
	}

	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}

	public Integer getVarietyId() {
		return varietyId;
	}

	public void setVarietyId(Integer varietyId) {
		this.varietyId = varietyId;
	}

	public Double getCroppingArea() {
		return croppingArea;
	}

	public void setCroppingArea(Double croppingArea) {
		this.croppingArea = croppingArea;
	}

	public Double getYield() {
		return yield;
	}

	public void setYield(Double yield) {
		this.yield = yield;
	}


	public Integer getHasIrrigation() {
		return hasIrrigation;
	}

	public void setHasIrrigation(Integer hasIrrigation) {
		this.hasIrrigation = hasIrrigation;
	}

	public String getAlternateVariety() {
		return alternateVariety;
	}

	public void setAlternateVarietyName(String alternateVariety) {
		this.alternateVariety = alternateVariety;
	}

	public Double getSellerGivenQtyTon() {
		return sellerGivenQtyTon;
	}

	public void setSellerGivenQtyTon(Double sellerGivenQtyTon) {
		this.sellerGivenQtyTon = sellerGivenQtyTon;
	}

	public String getDateOfAvailability() {
		return dateOfAvailability;
	}

	public void setDateOfAvailability(String dateOfAvailability) {
		this.dateOfAvailability = dateOfAvailability;
	}

	public String getVarietyName() {
		return varietyName;
	}

	public void setVarietyName(String varietyName) {
		this.varietyName = varietyName;
	}

	public Integer getLeadResponse() {
		return leadResponse;
	}

	public void setLeadResponse(Integer leadResponse) {
		this.leadResponse = leadResponse;
	}
}
