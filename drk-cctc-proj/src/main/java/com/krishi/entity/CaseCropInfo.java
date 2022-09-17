// Generated with g9.

package com.krishi.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "case_crop_info")
public class CaseCropInfo {

	@Id
	private Integer id;

	private Integer caseId;

	private Integer seedSourceId;

	private Integer varietyId;

	private Double cropArea;

	private Boolean seedsSampleReceived;

	private Double seedsRates;

	private Integer seasonId;

	private Integer uomId;

	private Double spacingRow;

	private Double spacingPlant;

	private Integer correctedSowingWeek;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCaseId() {
		return caseId;
	}

	public void setCaseId(Integer caseId) {
		this.caseId = caseId;
	}

	public Integer getSeedSourceId() {
		return seedSourceId;
	}

	public void setSeedSourceId(Integer seedSourceId) {
		this.seedSourceId = seedSourceId;
	}

	public Integer getVarietyId() {
		return varietyId;
	}

	public void setVarietyId(Integer varietyId) {
		this.varietyId = varietyId;
	}

	public Double getCropArea() {
		return cropArea;
	}

	public void setCropArea(Double cropArea) {
		this.cropArea = cropArea;
	}

	public Boolean getSeedsSampleReceived() {
		return seedsSampleReceived;
	}

	public void setSeedsSampleReceived(Boolean seedsSampleReceived) {
		this.seedsSampleReceived = seedsSampleReceived;
	}

	public Double getSeedsRates() {
		return seedsRates;
	}

	public void setSeedsRates(Double seedsRates) {
		this.seedsRates = seedsRates;
	}

	public Integer getSeasonId() {
		return seasonId;
	}

	public void setSeasonId(Integer seasonId) {
		this.seasonId = seasonId;
	}

	public Integer getUomId() {
		return uomId;
	}

	public void setUomId(Integer uomId) {
		this.uomId = uomId;
	}

	public Double getSpacingRow() {
		return spacingRow;
	}

	public void setSpacingRow(Double spacingRow) {
		this.spacingRow = spacingRow;
	}

	public Double getSpacingPlant() {
		return spacingPlant;
	}

	public void setSpacingPlant(Double spacingPlant) {
		this.spacingPlant = spacingPlant;
	}

	public Integer getCorrectedSowingWeek() {
		return correctedSowingWeek;
	}

	public void setCorrectedSowingWeek(Integer correctedSowingWeek) {
		this.correctedSowingWeek = correctedSowingWeek;
	}
}
