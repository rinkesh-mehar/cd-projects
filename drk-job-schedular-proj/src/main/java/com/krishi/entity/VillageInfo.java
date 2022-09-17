package com.krishi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.krishi.model.EntityModel;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

@Entity(name = "village_info")
public class VillageInfo implements Serializable, EntityModel {

	public VillageInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	/** Primary key. */
	protected static final String PK = "villageInfoId";

	@Id
	@Column(name = "id")
	private String villageInfoId;

	@Column(name = "village_id", precision = 10)
	private int villageId;

	@Column(name = "commodity_id", nullable = false, precision = 10)
	private int commodityId;

	@Column(name = "sowing_area", length = 12)
	private float sowingArea;

	@Column(name = "sowing_week", precision = 10)
	private int sowingWeek;

	@Column(name = "sowing_year", precision = 10)
	private int sowingYear;

	@Column(name = "harvest_week", precision = 10)
	private int harvestWeek;

	@Column(name = "harvest_year", precision = 10)
	private int harvestYear;

	@Column(name = "average_yield", length = 22)
	private double averageYeild;

	@Column(name = "common_varities", precision = 10)
	private String commonVarities;

	/** added new field in sync prs - CDT - Ujwal - Start */

//  @Column(name = "season_id")
//  private int seasonId;

	@Column(name = "alternate_variety_name")
	private String alternateVarietyName;

	@Column(name = "variety_id")
	private Integer varietyId;

	@Column(name = "other_variety_name")
	public String otherVarietyName;

	@Column(name = "other_variety_id")
	public Integer otherVarietyId;

	public String getOtherVarietyName() {
		return otherVarietyName;
	}

	public void setOtherVarietyName(String otherVarietyName) {
		this.otherVarietyName = otherVarietyName;
	}

	public Integer getOtherVarietyId() {
		return otherVarietyId;
	}

	public void setOtherVarietyId(Integer otherVarietyId) {
		this.otherVarietyId = otherVarietyId;
	}

	public Integer getVarietyId() {
		return varietyId;
	}

	public void setVarietyId(Integer varietyId) {
		this.varietyId = varietyId;
	}

	public String getAlternateVarietyName() {
		return alternateVarietyName;
	}

	public void setAlternateVarietyName(String alternateVarietyName) {
		this.alternateVarietyName = alternateVarietyName;
	}

//  public int getSeasonId() {
//      return seasonId;
//  }
//
//  public void setSeasonId(int seasonId) {
//      this.seasonId = seasonId;
//  }

	/** added new field in sync prs - CDT - Ujwal - End */

	/**
	 * Returns a debug-friendly String representation of this instance.
     *
     * @return String representation of this instance
     */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("[VillageInfo |");
		sb.append(" villageInfoId=").append(getVillageInfoId());
		sb.append("]");
		return sb.toString();
	}

	/**
	 * Return all elements of the primary key.
	 *
	 * @return Map of key names to values
	 */
	public Map<String, Object> getPrimaryKey() {
		Map<String, Object> ret = new LinkedHashMap<String, Object>(6);
		ret.put("villageInfoId", getVillageInfoId());
		return ret;
	}

	public String getVillageInfoId() {
		return villageInfoId;
	}

	public void setVillageInfoId(String villageInfoId) {
		this.villageInfoId = villageInfoId;
	}

	public int getVillageId() {
		return villageId;
	}

	public void setVillageId(int villageId) {
		this.villageId = villageId;
	}

	public int getCommodityId() {
		return commodityId;
	}

	public void setCommodityId(int commodityId) {
		this.commodityId = commodityId;
	}

	public float getSowingArea() {
		return sowingArea;
	}

	public void setSowingArea(float sowingArea) {
		this.sowingArea = sowingArea;
	}

	public int getSowingWeek() {
		return sowingWeek;
	}

	public void setSowingWeek(int sowingWeek) {
		this.sowingWeek = sowingWeek;
	}

	public int getSowingYear() {
		return sowingYear;
	}

	public void setSowingYear(int sowingYear) {
		this.sowingYear = sowingYear;
	}

	public int getHarvestWeek() {
		return harvestWeek;
	}

	public void setHarvestWeek(int harvestWeek) {
		this.harvestWeek = harvestWeek;
	}

	public int getHarvestYear() {
		return harvestYear;
	}

	public void setHarvestYear(int harvestYear) {
		this.harvestYear = harvestYear;
	}

	public double getAverageYeild() {
		return averageYeild;
	}

	public void setAverageYeild(double averageYeild) {
		this.averageYeild = averageYeild;
	}

	public String getCommonVarities() {
		return commonVarities;
	}

	public void setCommonVarities(String commonVarities) {
		this.commonVarities = commonVarities;
	}

	public VillageInfo(String villageInfoId, int villageId, int commodityId, float sowingArea, int sowingWeek, int sowingYear, int harvestWeek, int harvestYear, double averageYeild, String commonVarities, String alternateVarietyName, Integer varietyId, String otherVarietyName, Integer otherVarietyId) {
		this.villageInfoId = villageInfoId;
		this.villageId = villageId;
		this.commodityId = commodityId;
		this.sowingArea = sowingArea;
		this.sowingWeek = sowingWeek;
		this.sowingYear = sowingYear;
		this.harvestWeek = harvestWeek;
		this.harvestYear = harvestYear;
		this.averageYeild = averageYeild;
		this.commonVarities = commonVarities;
		this.alternateVarietyName = alternateVarietyName;
		this.varietyId = varietyId;
		this.otherVarietyName = otherVarietyName;
		this.otherVarietyId = otherVarietyId;
	}

	@Override
	public String getId() {

		return villageInfoId;
	}

	@Override
	public void setId(String id) {
		this.villageInfoId = id;

	}

}
