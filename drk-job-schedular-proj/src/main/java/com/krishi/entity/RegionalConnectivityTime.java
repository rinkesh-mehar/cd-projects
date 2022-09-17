/**
 * 
 */
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
@Table(name = "regional_connectivity_time")
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegionalConnectivityTime implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique = true)
	@JsonProperty("ID")
	private Integer id;

	@Column(name = "region_id")
	@JsonProperty("RegionID")
	private Integer regionID;

	@Column(name = "surfaced_proportion")
	@JsonProperty("SurfacedProportion")
	private Double surfacedProportion;

	@Column(name = "unsurfacedProportion")
	@JsonProperty("UnsurfacedProportion")
	private Double unsurfacedProportion;

	@Column(name = "surfaced_time_min_per_km")
	@JsonProperty("SurfacedTimeMinPerkm")
	private Double surfacedTimeMinPerKm;

	@Column(name = "unsurfaced_time_min_per_km")
	@JsonProperty("UnsurfacedTimeMinPerKm")
	private Double unsurfacedTimeMinPerKm;

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

	public Double getSurfacedProportion() {
		return surfacedProportion;
	}

	public void setSurfacedProportion(Double surfacedProportion) {
		this.surfacedProportion = surfacedProportion;
	}

	public Double getUnsurfacedProportion() {
		return unsurfacedProportion;
	}

	public void setUnsurfacedProportion(Double unsurfacedProportion) {
		this.unsurfacedProportion = unsurfacedProportion;
	}

	public Double getSurfacedTimeMinPerKm() {
		return surfacedTimeMinPerKm;
	}

	public void setSurfacedTimeMinPerKm(Double surfacedTimeMinPerKm) {
		this.surfacedTimeMinPerKm = surfacedTimeMinPerKm;
	}

	public Double getUnsurfacedTimeMinPerKm() {
		return unsurfacedTimeMinPerKm;
	}

	public void setUnsurfacedTimeMinPerKm(Double unsurfacedTimeMinPerKm) {
		this.unsurfacedTimeMinPerKm = unsurfacedTimeMinPerKm;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setStatus(String status) {
		this.status = ((status != null && status.equalsIgnoreCase("Active")) ? 1 : 0);
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
	public String toString() {
		return "RegionalConnectivityTime [id=" + id + ", regionID=" + regionID + ", surfacedProportion="
				+ surfacedProportion + ", unsurfacedProportion=" + unsurfacedProportion + ", surfacedTimeMinPerKm="
				+ surfacedTimeMinPerKm + ", unsurfacedTimeMinPerKm=" + unsurfacedTimeMinPerKm + ", status=" + status
				+ ", updatedAt=" + updatedAt + ", createdAt=" + createdAt + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((regionID == null) ? 0 : regionID.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((surfacedProportion == null) ? 0 : surfacedProportion.hashCode());
		result = prime * result + ((surfacedTimeMinPerKm == null) ? 0 : surfacedTimeMinPerKm.hashCode());
		result = prime * result + ((unsurfacedProportion == null) ? 0 : unsurfacedProportion.hashCode());
		result = prime * result + ((unsurfacedTimeMinPerKm == null) ? 0 : unsurfacedTimeMinPerKm.hashCode());
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
		RegionalConnectivityTime other = (RegionalConnectivityTime) obj;
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
		if (surfacedProportion == null) {
			if (other.surfacedProportion != null)
				return false;
		} else if (!surfacedProportion.equals(other.surfacedProportion))
			return false;
		if (surfacedTimeMinPerKm == null) {
			if (other.surfacedTimeMinPerKm != null)
				return false;
		} else if (!surfacedTimeMinPerKm.equals(other.surfacedTimeMinPerKm))
			return false;
		if (unsurfacedProportion == null) {
			if (other.unsurfacedProportion != null)
				return false;
		} else if (!unsurfacedProportion.equals(other.unsurfacedProportion))
			return false;
		if (unsurfacedTimeMinPerKm == null) {
			if (other.unsurfacedTimeMinPerKm != null)
				return false;
		} else if (!unsurfacedTimeMinPerKm.equals(other.unsurfacedTimeMinPerKm))
			return false;
		if (updatedAt == null) {
			if (other.updatedAt != null)
				return false;
		} else if (!updatedAt.equals(other.updatedAt))
			return false;
		return true;
	}

	
}
