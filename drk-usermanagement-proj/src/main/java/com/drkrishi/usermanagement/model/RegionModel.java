package com.drkrishi.usermanagement.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegionModel {

	private int stateId;
	private int tileId;
	private String tileIdDescription;
	
	public RegionModel(int stateId, int tileId, String tileIdDescription) {
		
		this.stateId = stateId;
		this.tileId = tileId;
		this.tileIdDescription = tileIdDescription;
	}
	
	public int getStateId() {
		return stateId;
	}
	public void setStateId(int stateId) {
		this.stateId = stateId;
	}
	public int getTileId() {
		return tileId;
	}
	public void setTileId(int tileId) {
		this.tileId = tileId;
	}
	public String getTileIdDescription() {
		return tileIdDescription;
	}
	public void setTileIdDescription(String tileIdDescription) {
		this.tileIdDescription = tileIdDescription;
	}
	
	
	
}
