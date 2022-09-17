package com.krishi.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="case_ndvi")
public class CaseNdvi {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name="case_id")
	private String caseId;
    
    @Column(name="name")
	private String name;
    
    @Column(name="status")
	private String status;

	@JsonProperty("ndvi")
    @Column(name="expected_yield")
	private Double expectedYield;
    
    @Column(name="month")
	private Integer week;
    
    @Column(name="year")
	private Integer year;
    
   /* @Column(name="ndvi")
   	private Double ndvi;*/

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Double getExpectedYield() {
		return expectedYield;
	}

	public void setExpectedYield(Double expectedYield) {
		this.expectedYield = expectedYield;
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

	/*public Double getNdvi() {
		return ndvi;
	}

	public void setNdvi(Double ndvi) {
		this.ndvi = ndvi;
	}*/
}
