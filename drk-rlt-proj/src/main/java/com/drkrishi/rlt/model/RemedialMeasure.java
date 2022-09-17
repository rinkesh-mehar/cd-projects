package com.drkrishi.rlt.model;

public class RemedialMeasure {
	private String agrochemicalName;
	private String remedialBrand;
	private String remedialUom;
	private String remedialWeek;
	private Integer remedialDose;
	
	public String getAgrochemicalName() {
		return agrochemicalName;
	}
	public void setAgrochemicalName(String agrochemicalName) {
		this.agrochemicalName = agrochemicalName;
	}
	public String getRemedialBrand() {
		return remedialBrand;
	}
	public void setRemedialBrand(String remedialBrand) {
		this.remedialBrand = remedialBrand;
	}
	public String getRemedialUom() {
		return remedialUom;
	}
	public void setRemedialUom(String remedialUom) {
		this.remedialUom = remedialUom;
	}
	public String getRemedialWeek() {
		return remedialWeek;
	}
	public void setRemedialWeek(String remedialWeek) {
		this.remedialWeek = remedialWeek;
	}
	
	public Integer getRemedialDose() {
		return remedialDose;
	}
	public void setRemedialDose(Integer remedialDose) {
		this.remedialDose = remedialDose;
	}
	@Override
	public String toString() {
		return "RemedialMeasure [agrochemicalName=" + agrochemicalName + ", remedialBrand=" + remedialBrand
				+ ", remedialtUom=" + remedialUom + ", remedialWeek=" + remedialWeek + "]";
	}
	
}
