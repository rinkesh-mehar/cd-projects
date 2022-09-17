package com.krishi.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.krishi.entity.Region;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RegionModel {

	private static final long serialVersionUID = 1L;
	@JsonProperty
	private int RegionID;
	@JsonProperty
	private int TileID;
	@JsonProperty
	private int StateCode;
	@JsonProperty
	private BigDecimal Latitude;
	@JsonProperty
	private BigDecimal Longitude;
	@JsonProperty
	private String Name;
	@JsonProperty
	private String Description;
	@JsonProperty
	private String Status;

	@JsonProperty
	private Integer WorkingHours;
	@JsonProperty
	private Double PercentageOfAbsence;
	@JsonProperty
	private Double PercentageOfInefficiency;
	public RegionModel() {
		// TODO Auto-generated constructor stub
	}

	public RegionModel(int regionID, int tileID, int stateCode, BigDecimal latitude, BigDecimal longitude, String name,
			String description, String status) {
		super();
		RegionID = regionID;
		TileID = tileID;
		StateCode = stateCode;
		Latitude = latitude;
		Longitude = longitude;
		Name = name;
		Description = description;
		Status = status;
	}

	public int getRegionID() {
		return RegionID;
	}

	public void setRegionID(int regionID) {
		RegionID = regionID;
	}

	public int getTileID() {
		return TileID;
	}

	public void setTileID(int tileID) {
		TileID = tileID;
	}

	public int getStateCode() {
		return StateCode;
	}

	public void setStateCode(int stateCode) {
		StateCode = stateCode;
	}

	public BigDecimal getLatitude() {
		return Latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		Latitude = latitude;
	}

	public BigDecimal getLongitude() {
		return Longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		Longitude = longitude;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public Integer getWorkingHours() {
		return WorkingHours;
	}

	public void setWorkingHours(Integer workingHours) {
		WorkingHours = workingHours;
	}

	public Double getPercentageOfAbsence() {
		return PercentageOfAbsence;
	}

	public void setPercentageOfAbsence(Double percentageOfAbsence) {
		PercentageOfAbsence = percentageOfAbsence;
	}

	public Double getPercentageOfInefficiency() {
		return PercentageOfInefficiency;
	}

	public void setPercentageOfInefficiency(Double percentageOfInefficiency) {
		PercentageOfInefficiency = percentageOfInefficiency;
	}

	@Override
	public String toString() {
		return "RegionModel{" +
				"RegionID=" + RegionID +
				", TileID=" + TileID +
				", StateCode=" + StateCode +
				", Latitude=" + Latitude +
				", Longitude=" + Longitude +
				", Name='" + Name + '\'' +
				", Description='" + Description + '\'' +
				", Status='" + Status + '\'' +
				", WorkingHours=" + WorkingHours +
				", PercentageOfAbsence=" + PercentageOfAbsence +
				", PercentageOfInefficiency=" + PercentageOfInefficiency +
				'}';
	}

	public Region getEntity() {
		
		Region regionEntity = new Region();
		regionEntity.setRegionId(RegionID);
		regionEntity.setWorkingHours(WorkingHours);
		regionEntity.setPercentageOfAbsence(PercentageOfAbsence);
		regionEntity.setPercentageOfInefficiency(PercentageOfInefficiency);
		//regionEntity.setTileId(TileID);
		regionEntity.setStateId(StateCode);
		regionEntity.setLatitude(Latitude);
		regionEntity.setLongitude(Longitude);
		regionEntity.setRegionName(Name);
		regionEntity.setDescription(Description);
		regionEntity.setStatus(Status != null && Status.equalsIgnoreCase("Active") ? 1 : 0);
		
		return regionEntity;
		

	}
}
