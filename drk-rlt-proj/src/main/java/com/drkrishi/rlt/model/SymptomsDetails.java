package com.drkrishi.rlt.model;

import java.util.List;

public class SymptomsDetails {
	private Integer stressId;
	private List<SymptomsModel> symptoms;
	
	public Integer getStressId() {
		return stressId;
	}
	public void setStressId(Integer stressId) {
		this.stressId = stressId;
	}
	public List<SymptomsModel> getSymptoms() {
		return symptoms;
	}
	public void setSymptoms(List<SymptomsModel> symptoms) {
		this.symptoms = symptoms;
	}
}
