package com.drkrishi.iqa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "view_kycqa_task")
public class ViewKycqaTask {
	@Id
	@Column(name="task_id")
	private String taskId;
	
	@Column(name = "farmer_id")
	private String farmerId;
	
	@Column(name="farmer_name")
	private String farmerName;

	@Column(name = "primary_mobile_number")
	private String primaryMobileNumber;

	@Column(name = "village_name")
	private String villageName;

	@Column(name = "region_name")
	private String regionName;

	@Column(name = "state_name")
	private String stateName;

	@Column(name = "task_type_id")
	private Integer taskTypeId;

	@Column(name = "assignee_id")
	private Integer assigneeId;

	@Column(name = "status")
	private Integer status;

	@Column(name = "entity_type_id")
	private Integer entityTypeId;

	@Column(name = "entity_id")
	private String entityId;

	@Column(name = "is_agriota_cust")
	private Integer isAgriotaCust;

	@Column(name = "is_drk_cust")
	private Integer isDrkCust;

	@Column(name = "willingness_for_cdt")
	private Integer willingnessForCdt;


	@Column(name = "kyc_is_verified")
	private int kycIsVerified;

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	public String getFarmerId() {
		return farmerId;
	}

	public void setFarmerId(String farmerId) {
		this.farmerId = farmerId;
	}

	public String getFarmerName() {
		return farmerName;
	}

	public void setFarmerName(String farmerName) {
		this.farmerName = farmerName;
	}

	public String getPrimaryMobileNumber() {
		return primaryMobileNumber;
	}

	public void setPrimaryMobileNumber(String primaryMobileNumber) {
		this.primaryMobileNumber = primaryMobileNumber;
	}

	public String getVillageName() {
		return villageName;
	}

	public void setVillageName(String villageName) {
		this.villageName = villageName;
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

	public Integer getTaskTypeId() {
		return taskTypeId;
	}

	public void setTaskTypeId(Integer taskTypeId) {
		this.taskTypeId = taskTypeId;
	}

	public Integer getAssigneeId() {
		return assigneeId;
	}

	public void setAssigneeId(Integer assigneeId) {
		this.assigneeId = assigneeId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getEntityTypeId() {
		return entityTypeId;
	}

	public void setEntityTypeId(Integer entityTypeId) {
		this.entityTypeId = entityTypeId;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public Integer getIsAgriotaCust() {
		return isAgriotaCust;
	}

	public void setIsAgriotaCust(Integer isAgriotaCust) {
		this.isAgriotaCust = isAgriotaCust;
	}

	public Integer getIsDrkCust() {
		return isDrkCust;
	}

	public void setIsDrkCust(Integer isDrkCust) {
		this.isDrkCust = isDrkCust;
	}

	public Integer getWillingnessForCdt() {
		return willingnessForCdt;
	}

	public void setWillingnessForCdt(Integer willingnessForCdt) {
		this.willingnessForCdt = willingnessForCdt;
	}


	public int getKycIsVerified() {
		return kycIsVerified;
	}

	public void setKycIsVerified(int kycIsVerified) {
		this.kycIsVerified = kycIsVerified;
	}
}
