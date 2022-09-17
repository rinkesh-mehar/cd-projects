package com.krishi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "simple_ndvi")
public class SimpleNdvi {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "case_id")
	private String caseId;
	
	@Column(name = "url")
	private String url;

	@Column(name = "week")
	private Integer week;

	@Column(name = "year")
	private Integer year;
	
	@Column(name = "ndvi")
	private Double ndvi;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getWeek() {
		return week;
	}

	public void setWeek(Integer week) {
		this.week = week;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Double getNdvi() {
		return ndvi;
	}

	public void setNdvi(Double ndvi) {
		this.ndvi = ndvi;
	}
}
