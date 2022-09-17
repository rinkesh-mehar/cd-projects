package com.krishi.fls.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="variety_quality")
public class VarietyQuality {

	@Id
	private Integer id;

	@Column(name = "acz_id")
	private Integer aczId;

	@Column(name = "variety_id")
	private Integer varietyId;
	
	@Column(name = "current_quality_id")
	private Integer currentQualityId;
	
	@Column(name = "current_quality")
	private String currentQuality;
	
	@Column(name = "estimated_quality")
	private String estimatedQuality;
	
	@Column(name = "allowable_variance_in_quality") 
	private String allowableVarianceInQuality;
	
	@Column(name = "state_code")
	private Integer stateCode;

	@Column(name = "region_id")
	private Integer regionId;
	
	@Column(name = "commodity_id")
	private Integer commodityId;
	
	@Column(name = "sowing_start_week")
	private Integer sowingWeekStart;
	
	@Column(name = "sowing_end_week")
	private Integer sowingWeekEnd;
	
	@Column(name = "status")
	private Integer status;

	public Integer getAczId() {
		return aczId;
	}

	public void setAczId(Integer aczId) {
		this.aczId = aczId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSowingWeekStart() {
		return sowingWeekStart;
	}

	public void setSowingWeekStart(Integer sowingWeekStart) {
		this.sowingWeekStart = sowingWeekStart;
	}

	public Integer getSowingWeekEnd() {
		return sowingWeekEnd;
	}

	public void setSowingWeekEnd(Integer sowingWeekEnd) {
		this.sowingWeekEnd = sowingWeekEnd;
	}

	public Integer getVarietyId() {
		return varietyId;
	}

	public void setVarietyId(Integer varietyId) {
		this.varietyId = varietyId;
	}

	public String getCurrentQuality() {
		return currentQuality;
	}

	public void setCurrentQuality(String currentQuality) {
		this.currentQuality = currentQuality;
	}

	public String getEstimatedQuality() {
		return estimatedQuality;
	}

	public void setEstimatedQuality(String estimatedQuality) {
		this.estimatedQuality = estimatedQuality;
	}

	public String getAllowableVarianceInQuality() {
		return allowableVarianceInQuality;
	}

	public void setAllowableVarianceInQuality(String allowableVarianceInQuality) {
		this.allowableVarianceInQuality = allowableVarianceInQuality;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getCurrentQualityId() {
		return currentQualityId;
	}

	public void setCurrentQualityId(Integer currentQualityId) {
		this.currentQualityId = currentQualityId;
	}

	public Integer getStateCode() {
		return stateCode;
	}

	public void setStateCode(Integer stateCode) {
		this.stateCode = stateCode;
	}

	public Integer getRegionId() {
		return regionId;
	}

	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}

	public Integer getCommodityId() {
		return commodityId;
	}

	public void setCommodityId(Integer commodityId) {
		this.commodityId = commodityId;
	}
	
}
