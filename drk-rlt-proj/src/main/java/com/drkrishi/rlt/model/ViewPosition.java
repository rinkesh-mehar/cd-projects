package com.drkrishi.rlt.model;

public class ViewPosition {
	private Integer incidence;
	private Integer severity;
	private String image;
	private Boolean benchmark;
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getIncidence() {
		return incidence;
	}
	public void setIncidence(Integer incidence) {
		this.incidence = incidence;
	}
	public Integer getSeverity() {
		return severity;
	}
	public void setSeverity(Integer severity) {
		this.severity = severity;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Boolean getBenchmark() {
		return benchmark;
	}
	public void setBenchmark(Boolean benchmark) {
		this.benchmark = benchmark;
	}
	
}
