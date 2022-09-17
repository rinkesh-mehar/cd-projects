/**
 * 
 */
package com.krishi.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author CDT-Rinkesh
 *
 */
@Entity
@Table(name = "regional_commodity")
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegionalCommodity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@JsonProperty("ID")
	private Integer id;

	@Column(name = "state_id")
	@JsonProperty("StateCode")
	private Integer stateId;

	@Column(name = "region_id")
	@JsonProperty("RegionID")
	private String regionId;

	@Column(name = "sowing_start_week")
	@JsonProperty("SowingWeekStart")
	private String sowingWeekStart;

	@Column(name = "sowing_end_week")
	@JsonProperty("SowingWeekEnd")
	private String sowingWeekEnd;

	@Column(name = "commodity_id")
	@JsonProperty("CommodityID")
	private String commodityId;

	@Column(name = "target_value")
	@JsonProperty("TargetValue")
	private Integer targetValue;

	@Column(name = "min_lot_size")
	@JsonProperty("MinLotSize")
	private Integer minLotSize;

	@Column(name = "max_rigts_in_lot")
	@JsonProperty("MaxRigtsInLot")
	private Integer maxRigtsInLot;

	@Column(name = "harvest_relaxation")
	@JsonProperty("HarvestRelaxation")
	private Integer harvestRelaxation;

	@Column(name = "status")
	@JsonProperty("Status")
	private Integer status;

	@Column(name = "crop_area_proportion")
	@JsonProperty("CropAreaProportion")
	private Float cropAreaProportion;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStateId() {
		return stateId;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public Integer getTargetValue() {
		return targetValue;
	}

	public void setTargetValue(Integer targetValue) {
		this.targetValue = targetValue;
	}

	public Integer getMinLotSize() {
		return minLotSize;
	}

	public void setMinLotSize(Integer minLotSize) {
		this.minLotSize = minLotSize;
	}

	public Integer getMaxRigtsInLot() {
		return maxRigtsInLot;
	}

	public void setMaxRigtsInLot(Integer maxRigtsInLot) {
		this.maxRigtsInLot = maxRigtsInLot;
	}

	public Integer getHarvestRelaxation() {
		return harvestRelaxation;
	}

	public void setHarvestRelaxation(Integer harvestRelaxation) {
		this.harvestRelaxation = harvestRelaxation;
	}

	public Float getCropAreaProportion() {
		return cropAreaProportion;
	}

	public void setCropAreaProportion(Float cropAreaProportion) {
		this.cropAreaProportion = cropAreaProportion;
	}

	public String getSowingWeekStart() {
		return sowingWeekStart;
	}

	public void setSowingWeekStart(String sowingWeekStart) {
		this.sowingWeekStart = sowingWeekStart;
	}

	public String getSowingWeekEnd() {
		return sowingWeekEnd;
	}

	public void setSowingWeekEnd(String sowingWeekEnd) {
		this.sowingWeekEnd = sowingWeekEnd;
	}

	public String getCommodityId() {
		return commodityId;
	}

	public void setCommodityId(String commodityId) {
		this.commodityId = commodityId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setStatus(String status) {
		this.status = (status != null && status.equalsIgnoreCase("Active")) ? 1 : 0;
	}

	@Override
	public String toString() {
		return "RegionalCommodity [id=" + id + ", regionId=" + regionId + ", sowingWeekStart=" + sowingWeekStart + ", sowingWeekEnd=" + sowingWeekEnd + ", stateId=" + stateId + ""
				+ ", targetValue=" + targetValue + ", commodityId=" + commodityId + ", minLotSize=" + minLotSize + ", maxRigtsInLot=" + maxRigtsInLot + ", harvestRelaxation=" + harvestRelaxation  + ", status=" + status + ", cropAreaProportion=" + cropAreaProportion + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((stateId == null) ? 0 : stateId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((targetValue == null) ? 0 : targetValue.hashCode());
		result = prime * result + ((minLotSize == null) ? 0 : minLotSize.hashCode());
		result = prime * result + ((regionId == null) ? 0 : regionId.hashCode());
		result = prime * result + ((commodityId == null) ? 0 : commodityId.hashCode());
		result = prime * result + ((sowingWeekStart == null) ? 0 : sowingWeekStart.hashCode());
		result = prime * result + ((sowingWeekEnd == null) ? 0 : sowingWeekEnd.hashCode());
		result = prime * result + ((maxRigtsInLot == null) ? 0 : maxRigtsInLot.hashCode());
		result = prime * result + ((harvestRelaxation == null) ? 0 : harvestRelaxation.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((cropAreaProportion == null) ? 0 : cropAreaProportion.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RegionalCommodity other = (RegionalCommodity) obj;
		if (stateId == null) {
			if (other.stateId != null)
				return false;
		} else if (!stateId.equals(other.stateId))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (targetValue == null) {
			if (other.targetValue != null)
				return false;
		} else if (!targetValue.equals(other.targetValue))
			return false;
		if (minLotSize == null) {
			if (other.minLotSize != null)
				return false;
		} else if (!minLotSize.equals(other.minLotSize))
			return false;
		if (regionId == null) {
			if (other.regionId != null)
				return false;
		} else if (!regionId.equals(other.regionId))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (commodityId == null) {
			if (other.commodityId != null)
				return false;
		} else if (!commodityId.equals(other.commodityId))
			return false;
		if (sowingWeekStart == null) {
			if (other.sowingWeekStart != null)
				return false;
		} else if (!sowingWeekStart.equals(other.sowingWeekStart))
			return false;
		if (sowingWeekEnd == null) {
			if (other.sowingWeekEnd != null)
				return false;
		} else if (!sowingWeekEnd.equals(other.sowingWeekEnd))
			return false;
		if (cropAreaProportion == null) {
			if (other.cropAreaProportion != null)
				return false;
		} else if (!cropAreaProportion.equals(other.cropAreaProportion))
			return false;
		return true;
	}
}
