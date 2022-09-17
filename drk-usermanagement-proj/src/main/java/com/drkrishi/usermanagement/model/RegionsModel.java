package com.drkrishi.usermanagement.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegionsModel {

	public int regionId;
	public String regionName;
	
	public int getRegionId() {
		return regionId;
	}
	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	
	public RegionsModel(int regionId, String regionName) {
		
		this.regionId = regionId;
		this.regionName = regionName;
	}
	
	
}
