package com.drkrishi.rlt.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

@Entity
@Table(name="view_task_history_info")
@IdClass(ViewTaskHistoryInfoId.class)
public class ViewTaskHistoryInfo {
	
	@Id
	@Column(name = "task_id")
	private String taskId;
	
	@Column(name = "entity_id")
	private String entityId;
	
	@Column(name = "farmer_id")
	private String farmerId;
	
	@Column(name = "farmer_mob_number")
	private String farmerMobNumber;
	
	//@Column(name = "farmer_major_crop")
	//private String farmerMajorCrop;
	
	@Column(name = "village_name")
	private String villageName;
	
	@Column(name = "task_type_id")
	private Integer taskTypeId;
	
	@Column(name = "task_status")
	private Integer taskStatus;
	
	@Id
	@Column(name = "task_date")
	private Date taskDate;
	
	@Column(name = "case_sample_status")
	private String caseSampleStatus;
	
	private Integer currentTaskTypeId;
	
	private Integer farmerRegionId;
	
	private String cropName;

	public String getTaskId() {
		return taskId;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getFarmerId() {
		return farmerId;
	}

	public void setFarmerId(String farmerId) {
		this.farmerId = farmerId;
	}

	public String getFarmerMobNumber() {
		return farmerMobNumber;
	}

	public void setFarmerMobNumber(String farmerMobNumber) {
		this.farmerMobNumber = farmerMobNumber;
	}

//	public String getFarmerMajorCrop() {
//		return farmerMajorCrop;
//	}
//
//	public void setFarmerMajorCrop(String farmerMajorCrop) {
//		this.farmerMajorCrop = farmerMajorCrop;
//	}

	public String getVillageName() {
		return villageName;
	}

	public void setVillageName(String villageName) {
		this.villageName = villageName;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public Integer getTaskTypeId() {
		return taskTypeId;
	}

	public void setTaskTypeId(Integer taskTypeId) {
		this.taskTypeId = taskTypeId;
	}

	public Integer getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(Integer taskStatus) {
		this.taskStatus = taskStatus;
	}

	public Date getTaskDate() {
		return taskDate;
	}

	public void setTaskDate(Date taskDate) {
		this.taskDate = taskDate;
	}

	public String getCaseSampleStatus() {
		return caseSampleStatus;
	}

	public void setCaseSampleStatus(String caseSampleStatus) {
		this.caseSampleStatus = caseSampleStatus;
	}

	public Integer getCurrentTaskTypeId() {
		return currentTaskTypeId;
	}

	public void setCurrentTaskTypeId(Integer currentTaskTypeId) {
		this.currentTaskTypeId = currentTaskTypeId;
	}

	public String getCropName() {
		return cropName;
	}

	public void setCropName(String cropName) {
		this.cropName = cropName;
	}

	public Integer getFarmerRegionId() {
		return farmerRegionId;
	}

	public void setFarmerRegionId(Integer farmerRegionId) {
		this.farmerRegionId = farmerRegionId;
	}

}
