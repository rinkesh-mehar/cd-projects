package com.krishi.fls.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="phenophase_duration")
public class PhenophaseDuration {
	
	@Id
    private Integer id;
	
	private Integer phenophaseId;
	
	private Integer stateId;
	
	/** ACZ Introduced changes - CDT: Rinkesh*/
//	private Integer seasonId;
	
	private Integer aczId;
	
	private Integer startDas;
	
	private Integer endDas;
	
	private Integer sowingStartWeek;
	
	private Integer sowingEndWeek;

	private Integer harvestStartWeek;
	
	private Integer harvestEndWeek;
	
	private Integer varietyId;
	
	private Integer commodityId;
	
//	private Integer startDay;
	
//	private Integer endDay;
	
	private Integer status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPhenophaseId() {
		return phenophaseId;
	}

	public void setPhenophaseId(Integer phenophaseId) {
		this.phenophaseId = phenophaseId;
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

//	public void setSeasonId(Integer seasonId) {
//		this.seasonId = seasonId;
//	}
	
	public Integer getAczId() {
		return aczId;
	}

	public void setAczId(Integer aczId) {
		this.aczId = aczId;
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

	public Integer getSowingStartWeek() {
		return sowingStartWeek;
	}

	public void setSowingStartWeek(Integer sowingWeekStart) {
		this.sowingStartWeek = sowingWeekStart;
	}

	public Integer getSowingEndWeek() {
		return sowingEndWeek;
	}

	public void setSowingEndWeek(Integer sowingWeekEnd) {
		this.sowingEndWeek = sowingWeekEnd;
	}

	public Integer getHarvestStartWeek() {
		return harvestStartWeek;
	}

	public void setHarvestWeekStart(Integer harvestWeekStart) {
		this.harvestStartWeek = harvestWeekStart;
	}

	public Integer getHarvestEndWeek() {
		return harvestEndWeek;
	}

	public void setHarvestEndWeek(Integer harvestWeekEnd) {
		this.harvestEndWeek = harvestWeekEnd;
	}
	
	public Integer getVarietyId() {
		return varietyId;
	}
	
	public void setVarietyId(Integer varietyId) {
		this.varietyId = varietyId;
	}

//	public Integer getStartDay() {
//		return startDay;
//	}

//	public void setStartDay(Integer startDay) {
//		this.startDay = startDay;
//	}

//	public Integer getEndDay() {
//		return endDay;
//	}

//	public void setEndDay(Integer endDay) {
//		this.endDay = endDay;
//	}

	
	public Integer getStatus() {
		return status;
	}

	public Integer getCommodityId() {
		return commodityId;
	}

	public void setCommodityId(Integer commodityId) {
		this.commodityId = commodityId;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	

}
