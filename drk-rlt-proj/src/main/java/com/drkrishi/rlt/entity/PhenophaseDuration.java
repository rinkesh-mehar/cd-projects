package com.drkrishi.rlt.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="phenophase_duration")
public class PhenophaseDuration {
	
	@Id
    @Column(unique=true, nullable=false, precision=10)
    private Integer id;
	
	private Integer phenophaseId;
	
	private Integer stateId;
	
	private Integer seasonId;
	
	private Integer varietyId;
	
	private Integer startDay;
	
	private Integer endDay;

	public PhenophaseDuration() {
		super();
	}

	public PhenophaseDuration(Integer id, Integer phenophaseId, Integer stateId, Integer seasonId, Integer varietyId,
			Integer startDay, Integer endDay) {
		super();
		this.id = id;
		this.phenophaseId = phenophaseId;
		this.stateId = stateId;
		this.seasonId = seasonId;
		this.varietyId = varietyId;
		this.startDay = startDay;
		this.endDay = endDay;
	}

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

	public Integer getStartDay() {
		return startDay;
	}

	public void setStartDay(Integer startDay) {
		this.startDay = startDay;
	}

	public Integer getEndDay() {
		return endDay;
	}

	public void setEndDay(Integer endDay) {
		this.endDay = endDay;
	}
}
