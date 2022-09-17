package com.drkrishi.rlt.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "case_details")
public class CaseDetails {

	@Id
	@Column(name = "case_id")
	private Integer caseId;
	
	@Column(name = "crop_area")
	private Double cropArea;
	
	@Column(name = "sowing_week")
	private Integer sowingWeek; 
	
	@Column(name = "sowing_year")
	private Integer sowingYear;
	
	@Column(name = "seeds_rates")
	private Integer seedsRates;
	
	@Column(name = "seeds_sample_received")
	private Integer seedsSampleReceived; 
	
	@Column(name = "season_name")
	private String seasonName;
	
	@Column(name = "seedSourceName")
	private String seed_source_name; 
	
	@Column(name = "commodity_name")
	private String commodityName; 
	
	@Column(name = "variety_name")
	private String varietyName;
	
	@Column(name = "phenophase_name")
	private String phenophaseName;

	public Integer getCaseId() {
		return caseId;
	}

	public void setCaseId(Integer caseId) {
		this.caseId = caseId;
	}

	public Double getCropArea() {
		return cropArea;
	}

	public void setCropArea(Double cropArea) {
		this.cropArea = cropArea;
	}

	public Integer getSowingWeek() {
		return sowingWeek;
	}

	public void setSowingWeek(Integer sowingWeek) {
		this.sowingWeek = sowingWeek;
	}

	public Integer getSowingYear() {
		return sowingYear;
	}

	public void setSowingYear(Integer sowingYear) {
		this.sowingYear = sowingYear;
	}

	public Integer getSeedsRates() {
		return seedsRates;
	}

	public void setSeedsRates(Integer seedsRates) {
		this.seedsRates = seedsRates;
	}

	public Integer getSeedsSampleReceived() {
		return seedsSampleReceived;
	}

	public void setSeedsSampleReceived(Integer seedsSampleReceived) {
		this.seedsSampleReceived = seedsSampleReceived;
	}

	public String getSeasonName() {
		return seasonName;
	}

	public void setSeasonName(String seasonName) {
		this.seasonName = seasonName;
	}

	public String getSeed_source_name() {
		return seed_source_name;
	}

	public void setSeed_source_name(String seed_source_name) {
		this.seed_source_name = seed_source_name;
	}

	public String getCommodityName() {
		return commodityName;
	}

	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}

	public String getVarietyName() {
		return varietyName;
	}

	public void setVarietyName(String varietyName) {
		this.varietyName = varietyName;
	} 
	
}
