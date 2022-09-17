package com.krishi.fls.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author CDT-Ujwal
 *
 */
@Entity
@Table(name = "farmer_crop_info", schema = "dev")
public class FarmerCropInfo implements Serializable {

	@Id
	@Column(name = "id")
	private String cropInfoId;

	@Column(name = "farmer_id")
	private String farmerId;

	@Column(name = "season_id")
	private Integer seasonId;

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

	/** added new field in sync prs - CDT - Ujwal - Start */
	@Column(name = "alternate_variety_name")
	private String alternateVarietyName;

	public String getAlternateVarietyName() {
		return alternateVarietyName;
	}

	public void setAlternateVarietyName(String alternateVarietyName) {
		this.alternateVarietyName = alternateVarietyName;
	}

	@Column(name = "seller_given_qty_ton")
	private Double sellerGivenQuantity;
	
	
	public Double getSellerGivenQuantity() {
		return sellerGivenQuantity;
	}

	public void setSellerGivenQuantity(Double sellerGivenQuantity) {
		this.sellerGivenQuantity = sellerGivenQuantity;
	}
	
	@Column(name = "date_of_availability")
	private Date dateOfAvailability;
	
	public Date getDateOfAvailability() {
		return dateOfAvailability;
	}

	public void setDateOfAvailability(Date dateOfAvailability) {
		this.dateOfAvailability = dateOfAvailability;
	}
	/** added new field in sync prs - CDT - Ujwal - End */

	public String getFarmerId() {
		return farmerId;
	}

	public void setFarmerId(String farmerId) {
		this.farmerId = farmerId;
	}

	public Integer getSeasonId() {
		return seasonId;
	}

	public void setSeasonId(Integer seasonId) {
		this.seasonId = seasonId;
	}

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

	
	
	public FarmerCropInfo(String cropInfoId, String farmerId, Integer seasonId, Integer commodityId, Integer varietyId,
			double croppingArea, double yield, Integer hasIrrigation, String alternateVarietyName,
			Double sellerGivenQuantity, Date dateOfAvailability) {
		this.cropInfoId = cropInfoId;
		this.farmerId = farmerId;
		this.seasonId = seasonId;
		this.commodityId = commodityId;
		this.varietyId = varietyId;
		this.croppingArea = croppingArea;
		this.yield = yield;
		this.hasIrrigation = hasIrrigation;
		this.alternateVarietyName = alternateVarietyName;
		this.sellerGivenQuantity = sellerGivenQuantity;
		this.dateOfAvailability = dateOfAvailability;
	}

	@Override
	public String toString() {
		return "FarmerCropInfo [cropInfoId=" + cropInfoId + ", farmerId=" + farmerId + ", seasonId=" + seasonId
				+ ", commodityId=" + commodityId + ", varietyId=" + varietyId + ", croppingArea=" + croppingArea
				+ ", yield=" + yield + ", hasIrrigation=" + hasIrrigation + ", alternateVarietyName="
				+ alternateVarietyName + ", sellerGivenQuantity=" + sellerGivenQuantity + ", dateOfAvailability="
				+ dateOfAvailability + "]";
	}

	

	

}
