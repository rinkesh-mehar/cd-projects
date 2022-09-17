package com.drkrishi.rlt.model;

import java.util.List;

import com.drkrishi.rlt.entity.StressSeverity;
import com.drkrishi.rlt.entity.StressSymptoms;

public class MasterModel {
	private List<MasterStressModel> stressDetails;
	private List<StressSeverity> stressSeverity;
	private List<Symptom> stressSymptomsModel;
	
	public MasterModel() {
	}

	public MasterModel(List<MasterStressModel> stressDetails, List<StressSeverity> stressSeverity,
			List<Symptom> stressSymptomsModel) {
		super();
		this.stressDetails = stressDetails;
		this.stressSeverity = stressSeverity;
		this.stressSymptomsModel = stressSymptomsModel;
	}

	public List<MasterStressModel> getStressDetails() {
		return stressDetails;
	}
	public void setStressDetails(List<MasterStressModel> stressDetails) {
		this.stressDetails = stressDetails;
	}
	public List<StressSeverity> getStressSeverity() {
		return stressSeverity;
	}
	public void setStressSeverity(List<StressSeverity> stressSeverity) {
		this.stressSeverity = stressSeverity;
	}

	public List<Symptom> getStressSymptomsModel() {
		return stressSymptomsModel;
	}

	public void setStressSymptomsModel(List<Symptom> stressSymptomsModel) {
		this.stressSymptomsModel = stressSymptomsModel;
	}

}
