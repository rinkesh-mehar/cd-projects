package com.krishi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.krishi.entity.MbepEntity;

public class MbepModel {

	@JsonProperty
	private Integer ID;
	
	@JsonProperty
	private Integer StateCode;
	
	@JsonProperty
	private Integer RegionID;
	
	@JsonProperty
	private Integer CommodityID;
	
	@JsonProperty
	private Integer VarietyID;
	
	@JsonProperty
	private Integer BandID;
	
	@JsonProperty
	private Double Mbep;

	@JsonProperty
	private String CropType;

	@JsonProperty
	private String Status;

	public Integer getID() {
		return ID;
	}

	public void setID(Integer iD) {
		ID = iD;
	}

	public Integer getVarietyID() {
		return VarietyID;
	}

	public void setVarietyID(Integer varietyID) {
		VarietyID = varietyID;
	}

	public Double getMbep() {
		return Mbep;
	}

	public void setMbep(Double mbep) {
		Mbep = mbep;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}
	
	public Integer getStateCode() {
		return StateCode;
	}

	public void setStateCode(Integer stateCode) {
		StateCode = stateCode;
	}

	public Integer getRegionID() {
		return RegionID;
	}

	public void setRegionID(Integer regionID) {
		RegionID = regionID;
	}

	public Integer getCommodityID() {
		return CommodityID;
	}

	public void setCommodityID(Integer commodityID) {
		CommodityID = commodityID;
	}

	public Integer getBandID() {
		return BandID;
	}

	public String getCropType() {
		return CropType;
	}

	public void setCropType(String cropType) {
		CropType = cropType;
	}

	public void setBandID(Integer bandID) {
		BandID = bandID;
	}

	public MbepEntity getEntity() {
		MbepEntity entity = new MbepEntity();
		entity.setId(ID);
		entity.setVarietyId(VarietyID);
		entity.setPrice(Mbep);
		entity.setStateCode(StateCode);
		entity.setRegionId(RegionID);
		entity.setCommodityId(CommodityID);
		entity.setBandId(BandID);
		entity.setStatus(Status != null && Status.equalsIgnoreCase("Active") ? 1 : 0);
		entity.setCropType(CropType);
		return entity;
	}

}
