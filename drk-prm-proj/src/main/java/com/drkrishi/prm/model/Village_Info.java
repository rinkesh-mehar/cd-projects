package com.drkrishi.prm.model;

public class Village_Info {

	private int village_info_Id;

	private double average_yeild__Crop;

	private int commodity_Id;

	private int common_varities;

	private int harvest_week;

	private float sowing_area_commodity;

	private int sowing_week;

	private int varitiy_Id;

	private Village village;

	public Village_Info() {
	}

	public int getVillage_info_Id() {
		return this.village_info_Id;
	}

	public void setVillage_info_Id(int village_info_Id) {
		this.village_info_Id = village_info_Id;
	}

	public double getAverage_yeild__Crop() {
		return this.average_yeild__Crop;
	}

	public void setAverage_yeild__Crop(double average_yeild__Crop) {
		this.average_yeild__Crop = average_yeild__Crop;
	}

	public int getCommodity_Id() {
		return this.commodity_Id;
	}

	public int getCommon_varities() {
		return this.common_varities;
	}

	public void setCommon_varities(int common_varities) {
		this.common_varities = common_varities;
	}

	public int getHarvest_week() {
		return this.harvest_week;
	}

	public void setHarvest_week(int harvest_week) {
		this.harvest_week = harvest_week;
	}

	public float getSowing_area_commodity() {
		return this.sowing_area_commodity;
	}

	public void setSowing_area_commodity(float sowing_area_commodity) {
		this.sowing_area_commodity = sowing_area_commodity;
	}

	public int getSowing_week() {
		return this.sowing_week;
	}

	public void setSowing_week(int sowing_week) {
		this.sowing_week = sowing_week;
	}

	public int getVaritiy_Id() {
		return this.varitiy_Id;
	}

	public void setVaritiy_Id(int varitiy_Id) {
		this.varitiy_Id = varitiy_Id;
	}

	public Village getVillage() {
		return this.village;
	}

	public void setVillage(Village village) {
		this.village = village;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(average_yeild__Crop);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + commodity_Id;
		result = prime * result + common_varities;
		result = prime * result + harvest_week;
		result = prime * result + Float.floatToIntBits(sowing_area_commodity);
		result = prime * result + sowing_week;
		result = prime * result + varitiy_Id;
		result = prime * result + ((village == null) ? 0 : village.hashCode());
		result = prime * result + village_info_Id;
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
		Village_Info other = (Village_Info) obj;
		if (Double.doubleToLongBits(average_yeild__Crop) != Double.doubleToLongBits(other.average_yeild__Crop))
			return false;
		if (commodity_Id != other.commodity_Id)
			return false;
		if (common_varities != other.common_varities)
			return false;
		if (harvest_week != other.harvest_week)
			return false;
		if (Float.floatToIntBits(sowing_area_commodity) != Float.floatToIntBits(other.sowing_area_commodity))
			return false;
		if (sowing_week != other.sowing_week)
			return false;
		if (varitiy_Id != other.varitiy_Id)
			return false;
		if (village == null) {
			if (other.village != null)
				return false;
		} else if (!village.equals(other.village))
			return false;
		if (village_info_Id != other.village_info_Id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Village_Info [village_info_Id=" + village_info_Id + ", average_yeild__Crop=" + average_yeild__Crop
				+ ", commodity_Id=" + commodity_Id + ", common_varities=" + common_varities + ", harvest_week="
				+ harvest_week + ", sowing_area_commodity=" + sowing_area_commodity + ", sowing_week=" + sowing_week
				+ ", varitiy_Id=" + varitiy_Id + ", village=" + village + "]";
	}

	public Village_Info(int village_info_Id, double average_yeild__Crop, int commodity_Id, int common_varities,
			int harvest_week, float sowing_area_commodity, int sowing_week, int varitiy_Id, Village village) {
		super();
		this.village_info_Id = village_info_Id;
		this.average_yeild__Crop = average_yeild__Crop;
		this.commodity_Id = commodity_Id;
		this.common_varities = common_varities;
		this.harvest_week = harvest_week;
		this.sowing_area_commodity = sowing_area_commodity;
		this.sowing_week = sowing_week;
		this.varitiy_Id = varitiy_Id;
		this.village = village;
	}

}