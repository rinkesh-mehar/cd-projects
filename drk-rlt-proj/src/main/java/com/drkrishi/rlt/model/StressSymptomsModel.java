package com.drkrishi.rlt.model;

import java.util.List;

public class StressSymptomsModel {
	private Integer stressId;
	private List<Symptom> symptoms;
	
	public Integer getStressId() {
		return stressId;
	}
	public void setStressId(Integer stressId) {
		this.stressId = stressId;
	}
	public List<Symptom> getSymptoms() {
		return symptoms;
	}
	public void setSymptoms(List<Symptom> symptoms) {
		this.symptoms = symptoms;
	}
	
	
}
