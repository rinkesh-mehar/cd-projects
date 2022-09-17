package com.drkrishi.rlt.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "benchmarked_images")
public class BenchmarkedImages {

	@Id
	@Column(name = "id")
	private String id;
	
	@Column(name = "image_name")
	private String imageName;
	
	@Column(name = "variety_id")
	private Integer varietyId;
	
	@Column(name = "region_id")
	private Integer regionId;
	
	@Column(name = "stress_symptom_id")
	private Integer stressSymptomId;
	
	@Column(name = "created_date")
	private Date createdDate;
	
	@Column(name = "created_by")
	private Integer createdBy;
	
	@Column(name = "status")
	private Integer status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public Integer getVarietyId() {
		return varietyId;
	}

	public void setVarietyId(Integer varietyId) {
		this.varietyId = varietyId;
	}

	public Integer getRegionId() {
		return regionId;
	}

	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}

	public Integer getStressSymptomId() {
		return stressSymptomId;
	}

	public void setStressSymptomId(Integer stressSymptomId) {
		this.stressSymptomId = stressSymptomId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
