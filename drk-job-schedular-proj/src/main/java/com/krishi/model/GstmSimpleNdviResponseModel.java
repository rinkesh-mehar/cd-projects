package com.krishi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.krishi.entity.SimpleNdvi;

public class GstmSimpleNdviResponseModel {

	@JsonProperty
	private String url;
	
	@JsonProperty
	private Integer Year;
	
	@JsonProperty
	private Integer Week;
	
	@JsonProperty
	private Double ndvi;

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

	public SimpleNdvi getSimpleNdvi() {
		SimpleNdvi simpleNdvi = new SimpleNdvi();
		simpleNdvi.setUrl(this.url);
		simpleNdvi.setWeek(this.Week);
		simpleNdvi.setYear(this.Year);
		simpleNdvi.setNdvi(ndvi);
		return simpleNdvi;
	}
}
