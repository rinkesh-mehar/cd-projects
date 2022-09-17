package com.krishi.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author CDT-Ujwal
 *
 */

@Entity
@Table(name = "regional_monthly_weather_based_travel_time")
@JsonIgnoreProperties(ignoreUnknown = true)
public class MonthlyWeatherBasedTravelTime implements Serializable  {


	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique = true)
	@JsonProperty("ID")
	private Integer id;
	
	@Column(name= "region_id")
	@JsonProperty("RegionID")
	private Integer regionID;
	
	@Column(name= "month")
	@JsonProperty("Month")
	private String month;
	
	@Column(name= "unit_type")
	@JsonProperty("UnitType")
	private Integer unitType;
	
	@Column(name = "status")
	@JsonProperty("Status")
	private Integer status;

	@Transient
	private Date updatedAt;

	@Transient
	private Date createdAt;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getRegionID() {
		return regionID;
	}

	public void setRegionID(Integer regionID) {
		this.regionID = regionID;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public Integer getUnitType() {
		return unitType;
	}

	public void setUnitType(Integer unitType) {
		this.unitType = unitType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	public void setStatus(String status) {
		this.status = ((status != null && status.equalsIgnoreCase("Active"))? 1 : 0);
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((month == null) ? 0 : month.hashCode());
		result = prime * result + ((regionID == null) ? 0 : regionID.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((unitType == null) ? 0 : unitType.hashCode());
		result = prime * result + ((updatedAt == null) ? 0 : updatedAt.hashCode());
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
		MonthlyWeatherBasedTravelTime other = (MonthlyWeatherBasedTravelTime) obj;
		if (createdAt == null) {
			if (other.createdAt != null)
				return false;
		} else if (!createdAt.equals(other.createdAt))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (month == null) {
			if (other.month != null)
				return false;
		} else if (!month.equals(other.month))
			return false;
		if (regionID == null) {
			if (other.regionID != null)
				return false;
		} else if (!regionID.equals(other.regionID))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (unitType == null) {
			if (other.unitType != null)
				return false;
		} else if (!unitType.equals(other.unitType))
			return false;
		if (updatedAt == null) {
			if (other.updatedAt != null)
				return false;
		} else if (!updatedAt.equals(other.updatedAt))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MonthlyWeatherBasedTravelTime [id=" + id + ", regionID=" + regionID + ", month=" + month + ", unitType="
				+ unitType + ", status=" + status + ", updatedAt=" + updatedAt + ", createdAt=" + createdAt + "]";
	}
	
	
}
