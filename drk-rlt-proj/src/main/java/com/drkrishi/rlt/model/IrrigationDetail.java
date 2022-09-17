package com.drkrishi.rlt.model;

import java.util.List;

public class IrrigationDetail {
	
	private List<String> irrigationSource;
	private List<String> irrigationMethod;
	private Integer numberOfIrrigation;
	private String weekOfIrrigationl;
	
	public List<String> getIrrigationSource() {
		return irrigationSource;
	}
	public void setIrrigationSource(List<String> irrigationSource) {
		this.irrigationSource = irrigationSource;
	}
	public List<String> getIrrigationMethod() {
		return irrigationMethod;
	}
	public void setIrrigationMethod(List<String> irrigationMethod) {
		this.irrigationMethod = irrigationMethod;
	}
	public Integer getNumberOfIrrigation() {
		return numberOfIrrigation;
	}
	public void setNumberOfIrrigation(Integer numberOfIrrigation) {
		this.numberOfIrrigation = numberOfIrrigation;
	}
	public String getWeekOfIrrigationl() {
		return weekOfIrrigationl;
	}
	public void setWeekOfIrrigationl(String weekOfIrrigationl) {
		this.weekOfIrrigationl = weekOfIrrigationl;
	}
	
	@Override
	public String toString() {
		return "IrrigationDetail [irrigationSource=" + irrigationSource + ", irrigationMethod=" + irrigationMethod
				+ ", numberOfIrrigation=" + numberOfIrrigation + ", weekOfIrrigationl=" + weekOfIrrigationl + "]";
	}
	
}
