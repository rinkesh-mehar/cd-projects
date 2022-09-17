// Generated with g9.

package com.drkrishi.rlt.entity;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "case_crop_info")
public class CaseCropInfo implements Serializable {

	@Id
	private String id;
	@Column(name = "case_id", precision = 10)
	private String caseId;
	@Column(name = "seed_source_id", precision = 10)
	private int seedSourceId;
	@Column(name = "variety_id", precision = 10)
	private int varietyId;
	@Column(name = "crop_area", length = 12)
	private float cropArea;
	@Column(name = "seeds_sample_received", length = 3)
	private boolean seedsSampleReceived;
	@Column(name = "seeds_rates", length = 12)
	private float seedsRates;
	@Column(name = "uom_id", precision = 10)
	private int uomId;
	@Column(name = "spacing_row", length = 12)
	private float spacingRow;
	@Column(name = "spacing_plant", length = 12)
	private float spacingPlant;
	@Column(name = "corrected_sowing_week", nullable = false, precision = 10)
	private int correctedSowingWeek;
	@Column(name = "corrected_sowing_year", nullable = false, precision = 10)
	private int correctedSowingYear;
	@Column(name = "harvest_week", nullable = false, precision = 10)
	private int harvestWeek;
	@Column(name = "harvest_year", nullable = false, precision = 10)
	private int harvestYear;
	@Column(name = "acz_id")
	private int aczId;

	@Transient
	private Integer zonalCommodityId;

	@Transient
	private Integer commodityId;

	public Integer getZonalCommodityId() {
		return zonalCommodityId;
	}

	public void setZonalCommodityId(Integer zonalCommodityId) {
		this.zonalCommodityId = zonalCommodityId;
	}

	public Integer getCommodityId() {
		return commodityId;
	}

	public void setCommodityId(Integer commodityId) {
		this.commodityId = commodityId;
	}

	public CaseCropInfo() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String aId) {
		id = aId;
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String aCaseId) {
		caseId = aCaseId;
	}

	public int getSeedSourceId() {
		return seedSourceId;
	}

	public void setSeedSourceId(int aSeedSourceId) {
		seedSourceId = aSeedSourceId;
	}

	public int getVarietyId() {
		return varietyId;
	}

	public void setVarietyId(int aVarietyId) {
		varietyId = aVarietyId;
	}

	public float getCropArea() {
		return cropArea;
	}

	public void setCropArea(float aCropArea) {
		cropArea = aCropArea;
	}

	public boolean getSeedsSampleReceived() {
		return seedsSampleReceived;
	}

	public void setSeedsSampleReceived(boolean aSeedsSampleReceived) {
		seedsSampleReceived = aSeedsSampleReceived;
	}

	public float getSeedsRates() {
		return seedsRates;
	}

	public void setSeedsRates(float aSeedsRates) {
		seedsRates = aSeedsRates;
	}

	public int getUomId() {
		return uomId;
	}

	public void setUomId(int aUomId) {
		uomId = aUomId;
	}

	public float getSpacingRow() {
		return spacingRow;
	}

	public void setSpacingRow(float aSpacingRow) {
		spacingRow = aSpacingRow;
	}

	public float getSpacingPlant() {
		return spacingPlant;
	}

	public void setSpacingPlant(float aSpacingPlant) {
		spacingPlant = aSpacingPlant;
	}

	public int getCorrectedSowingWeek() {
		return correctedSowingWeek;
	}

	public void setCorrectedSowingWeek(int aSowingWeek) {
		correctedSowingWeek = aSowingWeek;
	}

	public int getCorrectedSowingYear() {
		return correctedSowingYear;
	}

	public void setCorrectedSowingYear(int aSowingYear) {
		correctedSowingYear = aSowingYear;
	}

	public int getAczId() {
		return aczId;
	}

	public void setAczId(int aczId) {
		this.aczId = aczId;
	}
}
