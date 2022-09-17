package com.drkrishi.rlt.model;

public class CropInformation {

	private Integer taskId;
	private String commodityName;
	private String varietyName;
	private Float cropArea;
	private String seasonName;
	private String sowingWeek;
	private Integer sowingYear;
	private String seedSourceName;
	private Integer seedsSampleReceived;
	private Float seedsRates;	
	private String unitOFMeasurement;	
	private Float spacingRowToRow;	
	private Float spacingPlantToPlant;	
	private String cropTypeName;
	private float sellerGivenQtyTon;

	public String getCropTypeName() {
		return cropTypeName;
	}

	public void setCropTypeName(String cropTypeName) {
		this.cropTypeName = cropTypeName;
	}

	public float getSellerGivenQtyTon() {
		return sellerGivenQtyTon;
	}

	public void setSellerGivenQtyTon(float sellerGivenQtyTon) {
		this.sellerGivenQtyTon = sellerGivenQtyTon;
	}

	public Float getCropArea() {
		return cropArea;
	}

	public void setCropArea(Float cropArea) {
		this.cropArea = cropArea;
	}

	public String getSowingWeek() {
		return sowingWeek;
	}

	public void setSowingWeek(String sowingWeek) {
		this.sowingWeek = sowingWeek;
	}

	public Integer getSowingYear() {
		return sowingYear;
	}

	public void setSowingYear(Integer sowingYear) {
		this.sowingYear = sowingYear;
	}

	public Float getSeedsRates() {
		return seedsRates;
	}

	public void setSeedsRates(Float seedsRates) {
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

	public String getSeedSourceName() {
		return seedSourceName;
	}

	public void setSeedSourceName(String seedSourceName) {
		this.seedSourceName = seedSourceName;
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

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public Float getSpacingRowToRow() {
		return spacingRowToRow;
	}

	public void setSpacingRowToRow(Float spacingRowToRow) {
		this.spacingRowToRow = spacingRowToRow;
	}

	public Float getSpacingPlantToPlant() {
		return spacingPlantToPlant;
	}

	public void setSpacingPlantToPlant(Float spacingPlantToPlant) {
		this.spacingPlantToPlant = spacingPlantToPlant;
	}

	public String getUnitOFMeasurement() {
		return unitOFMeasurement;
	}

	public void setUnitOFMeasurement(String unitOFMeasurement) {
		this.unitOFMeasurement = unitOFMeasurement;
	}

	@Override
	public String toString() {
		return "CropInformation [taskId=" + taskId + ", commodityName=" + commodityName + ", varietyName=" + varietyName
				+ ", cropArea=" + cropArea + ", seasonName=" + seasonName + ", sowingWeek=" + sowingWeek
				+ ", sowingYear=" + sowingYear + ", seedSourceName=" + seedSourceName + ", seedsSampleReceived="
				+ seedsSampleReceived + ", seedsRates=" + seedsRates + ", unitOFMeasurement=" + unitOFMeasurement
				+ ", spacingRowToRow=" + spacingRowToRow + ", spacingPlantToPlant=" + spacingPlantToPlant + "]";
	}

}
