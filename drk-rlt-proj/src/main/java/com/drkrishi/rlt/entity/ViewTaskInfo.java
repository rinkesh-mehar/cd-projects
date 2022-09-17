package com.drkrishi.rlt.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="view_task_info")
public class ViewTaskInfo {
	
	@Id
	private String taskId;
	
	private String entityId;
	
	private String farmerId;
	
	private String farmerMobNumber;
	
	//private String farmerMajorCrop;
	
	private String villageName;
	
	private Integer taskTypeId;
	
	private Integer taskStatus;
	
	private Date taskDate;
	
	private String barcode;
	
	private String caseSampleStatus;
	
	private Integer farmerRegionId;
	
	private Integer assigneeId;
	
	private String cropName;

	private Boolean kycIsVerified;

	private Boolean kmlIsVerified;

	private Boolean imageIsVerified;

	private Boolean bankIsVerified;

	private Boolean rlmApprovalIsVerified;

	private Boolean rltSampleIsVerified;

	public Boolean getRlmApprovalIsVerified() {
		return rlmApprovalIsVerified;
	}

	public void setRlmApprovalIsVerified(Boolean rlmApprovalIsVerified) {
		this.rlmApprovalIsVerified = rlmApprovalIsVerified;
	}

	public Boolean getRltSampleIsVerified() {
		return rltSampleIsVerified;
	}

	public void setRltSampleIsVerified(Boolean rltSampleIsVerified) {
		this.rltSampleIsVerified = rltSampleIsVerified;
	}

	public Boolean getKycIsVerified() {
		return kycIsVerified;
	}

	public void setKycIsVerified(Boolean kycIsVerified) {
		this.kycIsVerified = kycIsVerified;
	}

	public Boolean getKmlIsVerified() {
		return kmlIsVerified;
	}

	public void setKmlIsVerified(Boolean kmlIsVerified) {
		this.kmlIsVerified = kmlIsVerified;
	}

	public Boolean getImageIsVerified() {
		return imageIsVerified;
	}

	public void setImageIsVerified(Boolean imageIsVerified) {
		this.imageIsVerified = imageIsVerified;
	}

	public Boolean getBankIsVerified() {
		return bankIsVerified;
	}

	public void setBankIsVerified(Boolean bankIsVerified) {
		this.bankIsVerified = bankIsVerified;
	}

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

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getCaseSampleStatus() {
		return caseSampleStatus;
	}

	public void setCaseSampleStatus(String caseSampleStatus) {
		this.caseSampleStatus = caseSampleStatus;
	}

	public Integer getFarmerRegionId() {
		return farmerRegionId;
	}

	public void setFarmerRegionId(Integer farmerRegionId) {
		this.farmerRegionId = farmerRegionId;
	}

	public Integer getAssigneeId() {
		return assigneeId;
	}

	public void setAssigneeId(Integer assigneeId) {
		this.assigneeId = assigneeId;
	}

	public String getCropName() {
		return cropName;
	}

	public void setCropName(String cropName) {
		this.cropName = cropName;
	}
	
}
