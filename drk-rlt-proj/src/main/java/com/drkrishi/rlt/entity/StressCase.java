package com.drkrishi.rlt.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "view_stress_case")
public class StressCase {
	
	
	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "stress_id")
	private Integer stressId;
	
	@Column(name = "stress_name")
	private String stressName;
	
	@Column(name = "stress_type")
	private String stressType;

	@Column(name = "side")
	private String side;
	
	@Column(name = "left_photo")
	private String leftPhoto;
	
 	@Column(name = "benchmark")
	private Boolean benchmark;
	
	@Column(name = "incident")
	private Integer incident;
	
	@Column(name = "severity")
	private Integer severity;
	
	@Column(name = "right_photo")
	private String rightPhoto;

	@Column(name = "stress_spot_symptom_image_id")
	private String stressSpotSymptomImageId;

	@Column(name = "symptom_desc")
	private Integer symptomDesc;
	
	@Column(name = "spot_id")
	private String spotId;
	
	@Column(name = "stress_status")
	private Boolean stressStatus;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getLeftPhoto() {
		return leftPhoto;
	}

	public void setLeftPhoto(String leftPhoto) {
		this.leftPhoto = leftPhoto;
	}

	public Integer getSymptomDesc() {
		return symptomDesc;
	}

	public void setSymptomDesc(Integer symptomDesc) {
		this.symptomDesc = symptomDesc;
	}

	public String getStressName() {
		return stressName;
	}

	public void setStressName(String stressName) {
		this.stressName = stressName;
	}

	public Integer getStressId() {
		return stressId;
	}

	public void setStressId(Integer stressId) {
		this.stressId = stressId;
	}

	public String getSide() {
		return side;
	}

	public void setSide(String side) {
		this.side = side;
	}

	public Integer getSeverity() {
		return severity;
	}

	public void setSeverity(Integer severity) {
		this.severity = severity;
	}

	public Boolean getBenchmark() {
		return benchmark;
	}

	public void setBenchmark(Boolean benchmark) {
		this.benchmark = benchmark;
	}

	public Integer getIncident() {
		return incident;
	}

	public void setIncident(Integer incident) {
		this.incident = incident;
	}

	public String getStressSpotSymptomImageId() {
		return stressSpotSymptomImageId;
	}

	public void setStressSpotSymptomImageId(String stressSpotSymptomImageId) {
		this.stressSpotSymptomImageId = stressSpotSymptomImageId;
	}

	public String getStressType() {
		return stressType;
	}

	public void setStressType(String stressType) {
		this.stressType = stressType;
	}

	public String getSpotId() {
		return spotId;
	}

	public void setSpotId(String spotId) {
		this.spotId = spotId;
	}

	public Boolean getStressStatus() {
		return stressStatus;
	}

	public void setStressStatus(Boolean stressStatus) {
		this.stressStatus = stressStatus;
	}

}
