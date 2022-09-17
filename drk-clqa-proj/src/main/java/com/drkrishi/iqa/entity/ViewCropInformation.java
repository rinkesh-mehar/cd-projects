package com.drkrishi.iqa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "view_crop_information")
public class ViewCropInformation {

	@Id
	@Column(name = "task_id")
	private String taskId;
	
	@Column(name = "commodity_name")
	private String commodityName;
	
	@Column(name = "variety_name")
	private String varietyName;
	
	@Column(name = "crop_area")
	private Float cropArea;
	
	/*@Column(name = "season_name")
	private String seasonName;*/
	
	@Column(name = "sowing_week")
	private Integer sowingWeek;
	
	@Column(name = "sowing_year")
	private Integer sowingYear;
	
	@Column(name = "seed_source_name")
	private String seedSourceName;
	
	@Column(name = "seeds_sample_received")
	private Integer seedsSampleReceived;
	
	@Column(name = "seeds_rates")
	private Float seedsRates;
	
	/*@Column(name = "unit_of_measurement_name")*/
//	private String unitOfMeasurementName;
	
	@Column(name = "spacing_row")
	private Float spacingRow;
	
	@Column(name = "spacing_plant")
	private Float spacingPlant;

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
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

	public Float getCropArea() {
		return cropArea;
	}

	public void setCropArea(Float cropArea) {
		this.cropArea = cropArea;
	}

/*	public String getSeasonName() {
		return seasonName;
	}

	public void setSeasonName(String seasonName) {
		this.seasonName = seasonName;
	}*/

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

	public String getSeedSourceName() {
		return seedSourceName;
	}

	public void setSeedSourceName(String seedSourceName) {
		this.seedSourceName = seedSourceName;
	}

	public Integer getSeedsSampleReceived() {
		return seedsSampleReceived;
	}

	public void setSeedsSampleReceived(Integer seedsSampleReceived) {
		this.seedsSampleReceived = seedsSampleReceived;
	}

	public Float getSeedsRates() {
		return seedsRates;
	}

	public void setSeedsRates(Float seedsRates) {
		this.seedsRates = seedsRates;
	}

	/*public String getUnitOfMeasurementName() {
		return unitOfMeasurementName;
	}

	public void setUnitOfMeasurementName(String unitOfMeasurementName) {
		this.unitOfMeasurementName = unitOfMeasurementName;
	}*/

	public Float getSpacingRow() {
		return spacingRow;
	}

	public void setSpacingRow(Float spacingRow) {
		this.spacingRow = spacingRow;
	}

	public Float getSpacingPlant() {
		return spacingPlant;
	}

	public void setSpacingPlant(Float spacingPlant) {
		this.spacingPlant = spacingPlant;
	}

}
