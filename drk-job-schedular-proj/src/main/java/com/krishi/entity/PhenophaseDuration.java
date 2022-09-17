package com.krishi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "phenophase_duration")
public class PhenophaseDuration {

	@Id
	@Column(unique = true, nullable = false, precision = 10)
	private Integer id;

	@Column(name = "state_id", precision = 10)
	private Integer stateId;

//	@Column(name = "season_id", precision = 10)
//	private Integer seasonId;
	@Column(name = "commodity_id")
	private Integer commodityId;

	@Column(name = "variety_id", precision = 10)
	private Integer varietyId;

	@Column(name = "phenophase_id")
	private Integer phenophaseId;

	@Column(name = "start_das", precision = 10)
	private Integer startDas;

	@Column(name = "end_das", precision = 10)
	private Integer endDas;

	@Column(name = "status", precision = 10)
	private Integer status;

	@Column(name= "acz_id")
	private Integer aczId;
	
	@Column(name = "sowing_start_week")
	private Integer sowingWeekStart;
	
	@Column(name = "sowing_end_week")
	private Integer sowingWeekend;
	
	@Column(name = "harvest_start_week")
	private Integer harvestWeekStart;
	
	@Column(name = "harvest_end_week ")
	private Integer harvestWeekEnd;

	@Column(name = "phenophase_order")
	private Integer phenophaseOrder;

	@Column(name = "no_days_for_harvest_monitoring")
	private Integer noOfDaysForHarvestMonitoring;

	public Integer getPhenophaseOrder() {
		return phenophaseOrder;
	}

	public void setPhenophaseOrder(Integer phenophaseOrder) {
		this.phenophaseOrder = phenophaseOrder;
	}

	public Integer getNoOfDaysForHarvestMonitoring() {
		return noOfDaysForHarvestMonitoring;
	}

	public void setNoOfDaysForHarvestMonitoring(Integer noOfDaysForHarvestMonitoring) {
		this.noOfDaysForHarvestMonitoring = noOfDaysForHarvestMonitoring;
	}

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

//	public Integer getSeasonId() {
//		return seasonId;
//	}
//
//	public void setSeasonId(Integer seasonId) {
//		this.seasonId = seasonId;
//	}

	public Integer getVarietyId() {
		return varietyId;
	}

	public Integer getCommodityId() {
		return commodityId;
	}

	public void setCommodityId(Integer commodityId) {
		this.commodityId = commodityId;
	}

	public void setVarietyId(Integer varietyId) {
		this.varietyId = varietyId;
	}

	public Integer getPhenophaseId() {
		return phenophaseId;
	}

	public void setPhenophaseId(Integer phenophaseId) {
		this.phenophaseId = phenophaseId;
	}


	public Integer getStartDas() {
		return startDas;
	}

	public void setStartDas(Integer startDas) {
		this.startDas = startDas;
	}

	public Integer getEndDas() {
		return endDas;
	}

	public void setEndDas(Integer endDas) {
		this.endDas = endDas;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getAczId() {
		return aczId;
	}

	public void setAczId(Integer aczId) {
		this.aczId = aczId;
	}

	public Integer getSowingWeekStart() {
		return sowingWeekStart;
	}

	public void setSowingWeekStart(Integer sowingWeekStart) {
		this.sowingWeekStart = sowingWeekStart;
	}

	public Integer getSowingWeekend() {
		return sowingWeekend;
	}

	public void setSowingWeekend(Integer sowingWeekend) {
		this.sowingWeekend = sowingWeekend;
	}

	public Integer getHarvestWeekStart() {
		return harvestWeekStart;
	}

	public void setHarvestWeekStart(Integer harvestWeekStart) {
		this.harvestWeekStart = harvestWeekStart;
	}

	public Integer getHarvestWeekEnd() {
		return harvestWeekEnd;
	}

	public void setHarvestWeekEnd(Integer harvestWeekEnd) {
		this.harvestWeekEnd = harvestWeekEnd;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aczId == null) ? 0 : aczId.hashCode());
		result = prime * result + ((endDas == null) ? 0 : endDas.hashCode());
		result = prime * result + ((harvestWeekEnd == null) ? 0 : harvestWeekEnd.hashCode());
		result = prime * result + ((harvestWeekStart == null) ? 0 : harvestWeekStart.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((phenophaseId == null) ? 0 : phenophaseId.hashCode());
		result = prime * result + ((sowingWeekStart == null) ? 0 : sowingWeekStart.hashCode());
		result = prime * result + ((sowingWeekend == null) ? 0 : sowingWeekend.hashCode());
		result = prime * result + ((startDas == null) ? 0 : startDas.hashCode());
		result = prime * result + ((stateId == null) ? 0 : stateId.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((varietyId == null) ? 0 : varietyId.hashCode());
		result = prime * result + ((commodityId == null) ? 0 : commodityId.hashCode());
		result = prime * result + ((phenophaseOrder == null) ? 0 : phenophaseOrder.hashCode());
		result = prime * result + ((noOfDaysForHarvestMonitoring == null) ? 0 : noOfDaysForHarvestMonitoring.hashCode());
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
		PhenophaseDuration other = (PhenophaseDuration) obj;
		if (aczId == null) {
			if (other.aczId != null)
				return false;
		} else if (!aczId.equals(other.aczId))
			return false;
		if (endDas == null) {
			if (other.endDas != null)
				return false;
		} else if (!endDas.equals(other.endDas))
			return false;
		if (harvestWeekEnd == null) {
			if (other.harvestWeekEnd != null)
				return false;
		} else if (!harvestWeekEnd.equals(other.harvestWeekEnd))
			return false;
		if (harvestWeekStart == null) {
			if (other.harvestWeekStart != null)
				return false;
		} else if (!harvestWeekStart.equals(other.harvestWeekStart))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (phenophaseId == null) {
			if (other.phenophaseId != null)
				return false;
		} else if (!phenophaseId.equals(other.phenophaseId))
			return false;
		if (sowingWeekStart == null) {
			if (other.sowingWeekStart != null)
				return false;
		} else if (!sowingWeekStart.equals(other.sowingWeekStart))
			return false;
		if (sowingWeekend == null) {
			if (other.sowingWeekend != null)
				return false;
		} else if (!sowingWeekend.equals(other.sowingWeekend))
			return false;
		if (startDas == null) {
			if (other.startDas != null)
				return false;
		} else if (!startDas.equals(other.startDas))
			return false;
		if (stateId == null) {
			if (other.stateId != null)
				return false;
		} else if (!stateId.equals(other.stateId))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (varietyId == null) {
			if (other.varietyId != null)
				return false;
		} else if (!varietyId.equals(other.varietyId))
			return false;
		if (commodityId == null) {
			if (other.commodityId != null)
				return false;
		} else if (!commodityId.equals(other.commodityId))
			return false;
		if (phenophaseOrder == null) {
			if (other.phenophaseOrder != null)
				return false;
		} else if (!phenophaseOrder.equals(other.phenophaseOrder))
			return false;
		if (noOfDaysForHarvestMonitoring == null) {
			if (other.noOfDaysForHarvestMonitoring != null)
				return false;
		} else if (!noOfDaysForHarvestMonitoring.equals(other.noOfDaysForHarvestMonitoring))
			return false;
		return true;
	}



}
