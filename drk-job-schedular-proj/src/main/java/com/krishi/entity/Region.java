package com.krishi.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "region")
public class Region implements Serializable {

	/** Primary key. */
	protected static final String PK = "regionId";

	@Id
	@Column(name = "id", unique = true, nullable = false, precision = 10)
	private int regionId;
	@Column(name = "tile_id", nullable = false, precision = 10)
	private int tileId;
	@Column(name = "state_id", precision = 10)
	private int stateId;
	@Column(length = 12)
	private BigDecimal latitude;
	@Column(length = 12)
	private BigDecimal longitude;
	@Column(name = "name", length = 100)
	private String regionName;
	private String description;
	@Column(length = 255)
	private String comment;
	@Column(nullable = false, precision = 10)
	private int status;
	@Column(name = "working_hours")
	private Integer workingHours;
	@Column(name = "percentage_of_absence")
	private Double percentageOfAbsence;
	@Column(name = "percentage_of_inefficiency")
	private Double percentageOfInefficiency;

	/** Default constructor. */
	public Region() {
		super();
	}

	/**
	 * Access method for regionId.
	 *
	 * @return the current value of regionId
	 */
	public int getRegionId() {
		return regionId;
	}

	/**
	 * Setter method for regionId.
	 *
	 * @param aRegionId the new value for regionId
	 */
	public void setRegionId(int aRegionId) {
		regionId = aRegionId;
	}

	/**
	 * Access method for tileId.
	 *
	 * @return the current value of tileId
	 */
	public int getTileId() {
		return tileId;
	}

	/**
	 * Setter method for tileId.
	 *
	 * @param aTileId the new value for tileId
	 */
	public void setTileId(int aTileId) {
		tileId = aTileId;
	}

	/**
	 * Access method for stateId.
	 *
	 * @return the current value of stateId
	 */
	public int getStateId() {
		return stateId;
	}

	/**
	 * Setter method for stateId.
	 *
	 * @param aStateId the new value for stateId
	 */
	public void setStateId(int aStateId) {
		stateId = aStateId;
	}

	/**
	 * Access method for latitude.
	 *
	 * @return the current value of latitude
	 */
	public BigDecimal getLatitude() {
		return latitude;
	}

	/**
	 * Setter method for latitude.
	 *
	 * @param aLatitude the new value for latitude
	 */
	public void setLatitude(BigDecimal aLatitude) {
		latitude = aLatitude;
	}

	/**
	 * Access method for longitude.
	 *
	 * @return the current value of longitude
	 */
	public BigDecimal getLongitude() {
		return longitude;
	}

	/**
	 * Setter method for longitude.
	 *
	 * @param aLongitude the new value for longitude
	 */
	public void setLongitude(BigDecimal aLongitude) {
		longitude = aLongitude;
	}

	/**
	 * Access method for regionName.
	 *
	 * @return the current value of regionName
	 */
	public String getRegionName() {
		return regionName;
	}

	/**
	 * Setter method for regionName.
	 *
	 * @param aRegionName the new value for regionName
	 */
	public void setRegionName(String aRegionName) {
		regionName = aRegionName;
	}

	/**
	 * Access method for description.
	 *
	 * @return the current value of description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Setter method for description.
	 *
	 * @param aDescription the new value for description
	 */
	public void setDescription(String aDescription) {
		description = aDescription;
	}

	/**
	 * Access method for comment.
	 *
	 * @return the current value of comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * Setter method for comment.
	 *
	 * @param aComment the new value for comment
	 */
	public void setComment(String aComment) {
		comment = aComment;
	}

	/**
	 * Access method for status.
	 *
	 * @return the current value of status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * Setter method for status.
	 *
	 * @param aStatus the new value for status
	 */
	public void setStatus(int aStatus) {
		status = aStatus;
	}

	public Integer getWorkingHours() {
		return workingHours;
	}

	public void setWorkingHours(Integer workingHours) {
		this.workingHours = workingHours;
	}

	public Double getPercentageOfAbsence() {
		return percentageOfAbsence;
	}

	public void setPercentageOfAbsence(Double percentageOfAbsence) {
		this.percentageOfAbsence = percentageOfAbsence;
	}

	public Double getPercentageOfInefficiency() {
		return percentageOfInefficiency;
	}

	public void setPercentageOfInefficiency(Double percentageOfInefficiency) {
		this.percentageOfInefficiency = percentageOfInefficiency;
	}

	/**
	 * Returns a debug-friendly String representation of this instance.
	 *
	 * @return String representation of this instance
	 */
	@Override
	public String toString() {
		return "Region{" +
				"regionId=" + regionId +
				", tileId=" + tileId +
				", stateId=" + stateId +
				", latitude=" + latitude +
				", longitude=" + longitude +
				", regionName='" + regionName + '\'' +
				", description='" + description + '\'' +
				", comment='" + comment + '\'' +
				", status=" + status +
				", workingHours=" + workingHours +
				", percentageOfAbsence=" + percentageOfAbsence +
				", percentageOfInefficiency=" + percentageOfInefficiency +
				'}';
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((comment == null) ? 0 : comment.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((latitude == null) ? 0 : latitude.hashCode());
		result = prime * result + ((longitude == null) ? 0 : longitude.hashCode());
		result = prime * result + regionId;
		result = prime * result + ((regionName == null) ? 0 : regionName.hashCode());
		result = prime * result + stateId;
		result = prime * result + status;
		result = prime * result + tileId;
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
		Region other = (Region) obj;
		if (comment == null) {
			if (other.comment != null)
				return false;
		} else if (!comment.equals(other.comment))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (latitude == null) {
			if (other.latitude != null)
				return false;
		} else if (!latitude.equals(other.latitude))
			return false;
		if (longitude == null) {
			if (other.longitude != null)
				return false;
		} else if (!longitude.equals(other.longitude))
			return false;
		if (regionId != other.regionId)
			return false;
		if (regionName == null) {
			if (other.regionName != null)
				return false;
		} else if (!regionName.equals(other.regionName))
			return false;
		if (stateId != other.stateId)
			return false;
		if (status != other.status)
			return false;
		if (tileId != other.tileId)
			return false;
		return true;
	}

	/**
	 * Return all elements of the primary key.
	 *
	 * @return Map of key names to values
	 */
	public Map<String, Object> getPrimaryKey() {
		Map<String, Object> ret = new LinkedHashMap<String, Object>(6);
		ret.put("regionId", Integer.valueOf(getRegionId()));
		return ret;
	}

}
