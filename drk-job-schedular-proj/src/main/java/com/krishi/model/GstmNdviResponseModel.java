package com.krishi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.krishi.entity.CaseNdvi;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GstmNdviResponseModel {
	
	@JsonProperty
	private String CaseId;
	
	@JsonProperty
	private String url;
	
	@JsonProperty
	private Integer Year;
	
	@JsonProperty
	private Integer Week;
	
	@JsonProperty
	private Double ndvi;

	public String getCaseId() {
		return CaseId;
	}

	public void setCaseId(String caseId) {
		CaseId = caseId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getYear() {
		return Year;
	}

	public void setYear(Integer year) {
		Year = year;
	}

	public Integer getWeek() {
		return Week;
	}

	public void setWeek(Integer week) {
		Week = week;
	}
	
	public Double getNdvi() {
		return ndvi;
	}

	public void setNdvi(Double ndvi) {
		this.ndvi = ndvi;
	}

	public CaseNdvi getCaseNdvi() {
		CaseNdvi caseNdvi = new CaseNdvi();
		caseNdvi.setCaseId(this.CaseId);
		caseNdvi.setName(this.url);
		caseNdvi.setWeek(this.Week);
		caseNdvi.setYear(this.Year);
//		caseNdvi.setNdvi(ndvi);
		return caseNdvi;
	}
	
}
