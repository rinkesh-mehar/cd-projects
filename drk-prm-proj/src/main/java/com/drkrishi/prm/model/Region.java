package com.drkrishi.prm.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Region{

	private int regionID;
	
	private int tileId;
	
	 private int stateId;
	
	private float latitude;
	
	private float longitude;
	
	private String regionName;
	
	private String description;
	
	private String comment;
	
	private int status;
	
	private int gstmRegionid;

	public int getRegionID() {
		return regionID;
	}

	public void setRegionID(int regionID) {
		this.regionID = regionID;
	}

	public int getTileId() {
		return tileId;
	}

	public void setTileId(int tileId) {
		this.tileId = tileId;
	}

	public int getStateId() {
		return stateId;
	}

	public void setStateId(int stateId) {
		this.stateId = stateId;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public String getRegionname() {
		return regionName;
	}

	public void setRegionname(String regionName) {
		this.regionName = regionName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getGstmRegionid() {
		return gstmRegionid;
	}

	public void setGstmRegionid(int gstmRegionid) {
		this.gstmRegionid = gstmRegionid;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((comment == null) ? 0 : comment.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + Float.floatToIntBits(latitude);
		result = prime * result + Float.floatToIntBits(longitude);
		result = prime * result + regionID;
		result = prime * result + ((regionName == null) ? 0 : regionName.hashCode());
		result = prime * result + stateId;
		result = prime * result + status;
		result = prime * result + tileId;
		result = prime * result + gstmRegionid;
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
		if (Float.floatToIntBits(latitude) != Float.floatToIntBits(other.latitude))
			return false;
		if (Float.floatToIntBits(longitude) != Float.floatToIntBits(other.longitude))
			return false;
		if (regionID != other.regionID)
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
		if (gstmRegionid != other.gstmRegionid)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Region [regionID=" + regionID + ", tileId=" + tileId + ", stateId=" + stateId + ", latitude=" + latitude
				+ ", longitude=" + longitude + ", regionName=" + regionName + ", description=" + description
				+ ", comment=" + comment + ", status=" + status +"gstmRegionid="+gstmRegionid+ "]";
	}

	public Region(int regionID, int tileId, int stateId, float latitude, float longitude, String regionName,
			String description, String comment, int status, int gstmRegionid) {
		super();
		this.regionID = regionID;
		this.tileId = tileId;
		this.stateId = stateId;
		this.latitude = latitude;
		this.longitude = longitude;
		this.regionName = regionName;
		this.description = description;
		this.comment = comment;
		this.status = status;
		this.gstmRegionid = gstmRegionid;
	}

	public Region() {
		
	}
	
	

}
