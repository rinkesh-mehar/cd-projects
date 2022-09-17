package com.krishi.entity;//package com.krishi.entity;
//
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.Table;
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import com.fasterxml.jackson.annotation.JsonProperty;
//
//@Entity
//@Table(name = "regional_variety")
//@JsonIgnoreProperties(ignoreUnknown = true)
//public class RegionalVariety {
//	
//	@JsonProperty("ID")
//	@Id
//	private Integer id;
//	
//	@JsonProperty("StateCode")
//	private Integer stateCode;
//	
//	@JsonProperty("SeasonID")
//	private Integer seasonId;
//	
//	@JsonProperty("CommodityID")
//	private Integer commodityId;
//	
//	@JsonProperty("VarietyID")
//	private Integer varietyId;
//	
//	@JsonProperty("SowingWeekStart")
//	private Integer sowingWeekStart;
//	
//	@JsonProperty("SowingWeekEnd")
//	private Integer sowingWeekEnd;
//	
//	@JsonProperty("HarvestWeekStart")
//	private Integer harvestWeekStart;
//	
//	@JsonProperty("HarvestWeekEnd")
//	private Integer harvestWeekEnd;
//	
//	@JsonProperty("Status")
//	private Integer status;
//
//	public Integer getId() {
//		return id;
//	}
//
//	public void setId(Integer id) {
//		this.id = id;
//	}
//
//	public Integer getStateCode() {
//		return stateCode;
//	}
//
//	public void setStateCode(Integer stateCode) {
//		this.stateCode = stateCode;
//	}
//
//	public Integer getSeasonId() {
//		return seasonId;
//	}
//
//	public void setSeasonId(Integer seasonId) {
//		this.seasonId = seasonId;
//	}
//
//	public Integer getCommodityId() {
//		return commodityId;
//	}
//
//	public void setCommodityId(Integer commodityId) {
//		this.commodityId = commodityId;
//	}
//
//	public Integer getVarietyId() {
//		return varietyId;
//	}
//
//	public void setVarietyId(Integer varietyId) {
//		this.varietyId = varietyId;
//	}
//
//	public Integer getSowingWeekStart() {
//		return sowingWeekStart;
//	}
//
//	public void setSowingWeekStart(Integer sowingWeekStart) {
//		this.sowingWeekStart = sowingWeekStart;
//	}
//
//	public Integer getSowingWeekEnd() {
//		return sowingWeekEnd;
//	}
//
//	public void setSowingWeekEnd(Integer sowingWeekEnd) {
//		this.sowingWeekEnd = sowingWeekEnd;
//	}
//
//	public Integer getHarvestWeekStart() {
//		return harvestWeekStart;
//	}
//
//	public void setHarvestWeekStart(Integer harvestWeekStart) {
//		this.harvestWeekStart = harvestWeekStart;
//	}
//
//	public Integer getHarvestWeekEnd() {
//		return harvestWeekEnd;
//	}
//
//	public void setHarvestWeekEnd(Integer harvestWeekEnd) {
//		this.harvestWeekEnd = harvestWeekEnd;
//	}
//
//	public Integer getStatus() {
//		return status;
//	}
//
//	public void setStatus(Integer status) {
//		this.status = status;
//	}
//	
//	public void setStatus(String status) {
//		this.status = ((status != null && status.equalsIgnoreCase("Active"))? 1 : 0);
//	}
//
//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + ((commodityId == null) ? 0 : commodityId.hashCode());
//		result = prime * result + ((harvestWeekEnd == null) ? 0 : harvestWeekEnd.hashCode());
//		result = prime * result + ((harvestWeekStart == null) ? 0 : harvestWeekStart.hashCode());
//		result = prime * result + ((id == null) ? 0 : id.hashCode());
//		result = prime * result + ((seasonId == null) ? 0 : seasonId.hashCode());
//		result = prime * result + ((sowingWeekEnd == null) ? 0 : sowingWeekEnd.hashCode());
//		result = prime * result + ((sowingWeekStart == null) ? 0 : sowingWeekStart.hashCode());
//		result = prime * result + ((stateCode == null) ? 0 : stateCode.hashCode());
//		result = prime * result + ((status == null) ? 0 : status.hashCode());
//		result = prime * result + ((varietyId == null) ? 0 : varietyId.hashCode());
//		return result;
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		RegionalVariety other = (RegionalVariety) obj;
//		if (commodityId == null) {
//			if (other.commodityId != null)
//				return false;
//		} else if (!commodityId.equals(other.commodityId))
//			return false;
//		if (harvestWeekEnd == null) {
//			if (other.harvestWeekEnd != null)
//				return false;
//		} else if (!harvestWeekEnd.equals(other.harvestWeekEnd))
//			return false;
//		if (harvestWeekStart == null) {
//			if (other.harvestWeekStart != null)
//				return false;
//		} else if (!harvestWeekStart.equals(other.harvestWeekStart))
//			return false;
//		if (id == null) {
//			if (other.id != null)
//				return false;
//		} else if (!id.equals(other.id))
//			return false;
//		if (seasonId == null) {
//			if (other.seasonId != null)
//				return false;
//		} else if (!seasonId.equals(other.seasonId))
//			return false;
//		if (sowingWeekEnd == null) {
//			if (other.sowingWeekEnd != null)
//				return false;
//		} else if (!sowingWeekEnd.equals(other.sowingWeekEnd))
//			return false;
//		if (sowingWeekStart == null) {
//			if (other.sowingWeekStart != null)
//				return false;
//		} else if (!sowingWeekStart.equals(other.sowingWeekStart))
//			return false;
//		if (stateCode == null) {
//			if (other.stateCode != null)
//				return false;
//		} else if (!stateCode.equals(other.stateCode))
//			return false;
//		if (status == null) {
//			if (other.status != null)
//				return false;
//		} else if (!status.equals(other.status))
//			return false;
//		if (varietyId == null) {
//			if (other.varietyId != null)
//				return false;
//		} else if (!varietyId.equals(other.varietyId))
//			return false;
//		return true;
//	}
//
//}
