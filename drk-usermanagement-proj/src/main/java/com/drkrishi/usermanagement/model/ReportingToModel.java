package com.drkrishi.usermanagement.model;

public class ReportingToModel {

	int reportingId;
	String reportingName;
	
	public int getReportingId() {
		return reportingId;
	}
	public void setReportingId(int reportingId) {
		this.reportingId = reportingId;
	}
	public String getReportingName() {
		return reportingName;
	}
	public void setReportingName(String reportingName) {
		this.reportingName = reportingName;
	}
	
	public ReportingToModel(int reportingId, String reportingName) {
		this.reportingId = reportingId;
		this.reportingName = reportingName;
	}
	
}
