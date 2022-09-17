package com.krishi.fls.entity;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="village_info")
public class VillageInfo implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	public VillageInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	/** Primary key. */
    protected static final String PK = "villageInfoId";

    @Id
    @Column(name="id")
    private String villageInfoId;

	@Column(name="village_id")
    private int villageId;
    
    @Column(name="commodity_id")
    private int commodityId;
    
    @Column(name="sowing_area")
    private float sowingArea;
    
    @Column(name="sowing_week")
    private int sowingWeek;
    
    @Column(name="sowing_year")
    private int sowingYear;
    
    @Column(name="harvest_week")
    private int harvestWeek;
    
    @Column(name="harvest_year")
    private int harvestYear;
    
    @Column(name="average_yield")
    private double averageYeild;
    
    @Column(name="common_varities")
    private String commonVarities;
    
    
    
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

	public VillageInfo(String villageInfoId, int villageId, int commodityId, float sowingArea, int sowingWeek,
			int sowingYear, int harvestWeek, int harvestYear, double averageYeild, String commonVarities) {
	
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
	}
	
	

}
