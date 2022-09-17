package com.drkrishi.rlt.model;

import java.util.List;

public class SeedTreatment {
	private String seedTreatment;
	private List<String> seedTreatmentAgent;
	private String seedTreatmentUom;
	private String seedTreatmentAgentDose;
	
	public String getSeedTreatment() {
		return seedTreatment;
	}
	public void setSeedTreatment(String seedTreatment) {
		this.seedTreatment = seedTreatment;
	}
	public List<String> getSeedTreatmentAgent() {
		return seedTreatmentAgent;
	}
	public void setSeedTreatmentAgent(List<String> seedTreatmentAgent) {
		this.seedTreatmentAgent = seedTreatmentAgent;
	}
	public String getSeedTreatmentUom() {
		return seedTreatmentUom;
	}
	public void setSeedTreatmentUom(String seedTreatmentUom) {
		this.seedTreatmentUom = seedTreatmentUom;
	}
	public String getSeedTreatmentAgentDose() {
		return seedTreatmentAgentDose;
	}
	public void setSeedTreatmentAgentDose(String seedTreatmentAgentDose) {
		this.seedTreatmentAgentDose = seedTreatmentAgentDose;
	}
}
