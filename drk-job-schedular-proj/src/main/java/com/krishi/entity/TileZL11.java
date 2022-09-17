package com.krishi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.criteria.CriteriaBuilder.In;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author CDT-RinkeshKM
 *
 */

@Entity
@Table(name = "tile_zl11")
public class TileZL11 {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", unique = true, nullable = false, precision = 10)
	@JsonProperty("TileID")
	private Long id;

	@Column(name = "status")
	@JsonProperty("Status")
	private Integer status;

	@Column(name = "acz_id")
	@JsonProperty("AczID")
	private Integer aczId;

	@Column(name = "latitude")
	@JsonProperty("Latitude")
	private Double latitude;
	
	@Column(name = "longitude")
	@JsonProperty("Longitude")
	private Double longitude;
	
	@Column(name = "region_id")
	@JsonProperty("RegionID")
	private Integer regionId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getAczId() {
		return aczId;
	}

	public void setAczId(Integer aczID) {
		this.aczId = aczID;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Integer getRegionId() {
		return regionId;
	}

	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((aczId == null) ? 0 : aczId.hashCode());
		result = prime * result + ((latitude == null) ? 0 : latitude.hashCode());
		result = prime * result + ((longitude == null) ? 0 : longitude.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((regionId == null) ? 0 : regionId.hashCode());
		return result;
	}
	
	public TileZL11() {
		super();
	}
	
	public TileZL11(Long id, Integer status, Integer aczId, Double latitude, Double longitude, Integer regionId) {
		super();
		this.id = id;
		this.status = status;
		this.aczId = aczId;
		this.latitude = latitude;
		this.longitude = longitude;
		this.regionId = regionId;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TileZL11 other = (TileZL11) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (aczId == null) {
			if (other.aczId != null)
				return false;
		} else if (!aczId.equals(other.aczId))
			return false;
		if (latitude == null) {
			if (other.latitude != null)
				return false;
		} else if (!latitude.equals(other.latitude))
			return false;
		if (aczId == null) {
			if (other.aczId != null)
				return false;
		} else if (!aczId.equals(other.aczId))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (longitude == null) {
			if (other.longitude != null)
				return false;
		} else if (!longitude.equals(other.longitude))
			return false;
		if (regionId == null) {
			if (other.regionId != null)
				return false;
		} else if (!regionId.equals(other.regionId))
			return false;
		return true;
	}
	
	

}