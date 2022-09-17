/**
 * 
 */
package com.krishi.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author CDT-Ujwal
 *
 */
@Entity
@Table(name = "general_terrain_type")
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeneralTerrainType implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique = true)
	@JsonProperty("ID")
	private Integer id;

	@Column(name = "region_id")
	@JsonProperty("RegionID")
	private Integer regionID;

	@Column(name = "terrain_type")
	@JsonProperty("TerrainType")
	private String terrainType;

	@Column(name = "min_per_km")
	@JsonProperty("MinPerKm")
	private Double minPerKm;

	@Column(name = "status")
	@JsonProperty("Status")
	private Integer status;

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

	public String getTerrainType() {
		return terrainType;
	}

	public void setTerrainType(String terrainType) {
		this.terrainType = terrainType;
	}

	public Double getMinPerKm() {
		return minPerKm;
	}

	public void setMinPerKm(Double minPerKm) {
		this.minPerKm = minPerKm;
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

	@Override
	public String toString() {
		return "GeneralTerrainType [id=" + id + ", regionID=" + regionID + ", terrainType=" + terrainType
				+ ", minPerKm=" + minPerKm + ", status=" + status + "]";
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((minPerKm == null) ? 0 : minPerKm.hashCode());
		result = prime * result + ((regionID == null) ? 0 : regionID.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((terrainType == null) ? 0 : terrainType.hashCode());
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
		GeneralTerrainType other = (GeneralTerrainType) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (minPerKm == null) {
			if (other.minPerKm != null)
				return false;
		} else if (!minPerKm.equals(other.minPerKm))
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
		if (terrainType == null) {
			if (other.terrainType != null)
				return false;
		} else if (!terrainType.equals(other.terrainType))
			return false;
		return true;
	}

}
