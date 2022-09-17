package com.drkrishi.rlt.model;

public class Fertilizer {
	private String applicationType;
	private String fertilizerName;
	private String fertilizerUom;
	private Integer fertilizerDose;
	private String splitDose;
	private String applicationWeek;
	
	public String getApplicationType() {
		return applicationType;
	}
	public void setApplicationType(String applicationType) {
		this.applicationType = applicationType;
	}
	public String getFertilizerName() {
		return fertilizerName;
	}
	public void setFertilizerName(String fertilizerName) {
		this.fertilizerName = fertilizerName;
	}
	public String getFertilizerUom() {
		return fertilizerUom;
	}
	public void setFertilizerUom(String fertilizerUom) {
		this.fertilizerUom = fertilizerUom;
	}
	public Integer getFertilizerDose() {
		return fertilizerDose;
	}
	public void setFertilizerDose(Integer fertilizerDose) {
		this.fertilizerDose = fertilizerDose;
	}
	public String getSplitDose() {
		return splitDose;
	}
	public void setSplitDose(String splitDose) {
		this.splitDose = splitDose;
	}
	public String getApplicationWeek() {
		return applicationWeek;
	}
	public void setApplicationWeek(String applicationWeek) {
		this.applicationWeek = applicationWeek;
	}
	@Override
	public String toString() {
		return "Fertilizer [applicationType=" + applicationType + ", fertilizerName=" + fertilizerName
				+ ", fertilizerUom=" + fertilizerUom + ", fertilizerDose=" + fertilizerDose + ", splitDose=" + splitDose
				+ ", applicationWeek=" + applicationWeek + "]";
	}

}
