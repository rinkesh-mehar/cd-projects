package com.krishi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.krishi.entity.TileZL11;
import com.krishi.entity.VarietyQuality;

/**
 * @author CDT-RinkeshKM
 *
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class TileZL11Model {

	@JsonProperty
	private String TileID; 
	
	@JsonProperty
	private String Status;
	
	@JsonProperty
	private Integer AczID;
	
	@JsonProperty
	private Double Latitude;
	
	@JsonProperty 
	private Double Longitude;
	
	@JsonProperty
	private Integer RegionID;

	public String getTileID() {
		return TileID;
	}

	public void setTileID(String tileID) {
		TileID = tileID;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public Integer getAczID() {
		return AczID;
	}

	public void setAczID(Integer aczID) {
		AczID = aczID;
	}

	public Double getLatitude() {
		return Latitude;
	}

	public void setLatitude(Double latitude) {
		Latitude = latitude;
	}

	public Double getLongitude() {
		return Longitude;
	}

	public void setLongitude(Double longitude) {
		Longitude = longitude;
	}

	public Integer getRegionID() {
		return RegionID;
	}

	public void setRegionID(Integer regionID) {
		RegionID = regionID;
	}

	public TileZL11 getEntity() {

		TileZL11 entity = new TileZL11();
		entity.setId(Long.parseLong(TileID));
//		entity.setId(TileID);
		entity.setAczId(AczID);
		entity.setLatitude(Latitude);
		entity.setLongitude(Longitude);
		entity.setRegionId(RegionID);
		entity.setStatus(Status != null && Status.equalsIgnoreCase("Active") ? 1 : 0);
		return entity;

	}
	
	
	
}
