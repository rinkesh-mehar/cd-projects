package com.drkrishi.iqa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "view_benchmarked_image")
public class ViewBenchmarkedImage {

	@Id
	@Column(name = "benchmarked_image_id")
	private String benchmarkedImageId;
	
	@Column(name = "benchmarked_image_name")
	private String benchmarkedImageName;
	
	@Column(name="variety_name")
	private String varietyName;
	
	@Column(name="commodity_name")
	private String commodityName;
	
	@Column(name="region_name")
	private String regionName;
	
	@Column(name="state_name")
	private String stateName;
	
	@Column(name="stress_symptom_name")
	private String stressSymptomName;
	
	@Column(name="stress_name")
	private String stressName;
	
	@Column(name="benchmarked_image_status")
	private Integer benchmarkedImageStatus;
	
	@Column(name="task_id")
	private String taskId;
	
	@Column(name="task_status")
	private Integer taskStatus;
	
	@Column(name="task_type_id")
	private Integer taskTypeId;
	
	@Column(name="entity_type_id")
	private String entityTypeId;
	
	@Column(name="assignee_id")
	private Integer assigneeId;

	@Column(name = "image_is_verified")
	private int imageIsVerified;
	
	public String getBenchmarkedImageId() {
		return benchmarkedImageId;
	}

	public void setBenchmarkedImageId(String benchmarkedImageId) {
		this.benchmarkedImageId = benchmarkedImageId;
	}

	public String getBenchmarkedImageName() {
		return benchmarkedImageName;
	}

	public void setBenchmarkedImageName(String benchmarkedImageName) {
		this.benchmarkedImageName = benchmarkedImageName;
	}

	public String getVarietyName() {
		return varietyName;
	}

	public void setVarietyName(String varietyName) {
		this.varietyName = varietyName;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getStressSymptomName() {
		return stressSymptomName;
	}

	public void setStressSymptomName(String stressSymptomName) {
		this.stressSymptomName = stressSymptomName;
	}

	public String getStressName() {
		return stressName;
	}

	public void setStressName(String stressName) {
		this.stressName = stressName;
	}

	public String getCommodityName() {
		return commodityName;
	}

	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}

	public Integer getBenchmarkedImageStatus() {
		return benchmarkedImageStatus;
	}

	public void setBenchmarkedImageStatus(Integer benchmarkedImageStatus) {
		this.benchmarkedImageStatus = benchmarkedImageStatus;
	}

	public Integer getAssigneeId() {
		return assigneeId;
	}

	public void setAssigneeId(Integer assigneeId) {
		this.assigneeId = assigneeId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public Integer getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(Integer taskStatus) {
		this.taskStatus = taskStatus;
	}

	public Integer getTaskTypeId() {
		return taskTypeId;
	}

	public void setTaskTypeId(Integer taskTypeId) {
		this.taskTypeId = taskTypeId;
	}

	public String getEntityTypeId() {
		return entityTypeId;
	}

	public void setEntityTypeId(String entityTypeId) {
		this.entityTypeId = entityTypeId;
	}

	public int getImageIsVerified() {
		return imageIsVerified;
	}

	public void setImageIsVerified(int imageIsVerified) {
		this.imageIsVerified = imageIsVerified;
	}
}
