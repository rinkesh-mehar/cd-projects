package com.drkrishi.usermanagement.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StateModel {

	public int stateId;
	public String stateName;
	
	public int getStateId() {
		return stateId;
	}
	public void setStateId(int stateId) {
		this.stateId = stateId;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	
	public StateModel(int stateId, String stateName) {
		this.stateId = stateId;
		this.stateName = stateName;
	}
	
}
