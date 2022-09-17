package com.drkrishi.rlt.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "view_barcode_details")
public class ViewBarcodeDetails {

	@Id
	@Column(name = "task_id")
	private String taskId;
	
	@Column(name = "barcode_number")
	private String barcode;
	
	@Column(name = "entity_id")
	private String entityId;
	
	@Column(name = "village_name")
	private String villageName;
	
	@Column(name = "former_mobile")
	private String formerMobile;
	
	@Column(name = "crop_name")
	private String cropName;

	@Column(name = "crop_type")
	private Integer cropType;

	@Column(name = "seller_type")
	private Integer sellerType;

	@Column(name = "status")
	private Integer status;
	
	@Column(name = "task_type_id")
	private Integer taskTypeId;
	
	@Column(name = "taskDate")
	private Date taskDate;
	
	@Column(name = "assignee_id")
	private Integer assigneeId;
	
//	@Column(name = "case_type_id")
//	private Integer caseTypeId;
	
	@Column(name = "region_id")
	private Integer regionId;
	
	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getVillageName() {
		return villageName;
	}

	public void setVillageName(String villageName) {
		this.villageName = villageName;
	}

	public String getFormerMobile() {
		return formerMobile;
	}

	public void setFormerMobile(String formerMobile) {
		this.formerMobile = formerMobile;
	}

	public String getCropName() {
		return cropName;
	}

	public void setCropName(String cropName) {
		this.cropName = cropName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getTaskTypeId() {
		return taskTypeId;
	}

	public void setTaskTypeId(Integer taskTypeId) {
		this.taskTypeId = taskTypeId;
	}

	public Date getTaskDate() {
		return taskDate;
	}

	public void setTaskDate(Date taskDate) {
		this.taskDate = taskDate;
	}

	public Integer getAssigneeId() {
		return assigneeId;
	}

	public void setAssigneeId(Integer assigneeId) {
		this.assigneeId = assigneeId;
	}

//	public Integer getCaseTypeId() {
//		return caseTypeId;
//	}

//	public void setCaseTypeId(Integer caseTypeId) {
//		this.caseTypeId = caseTypeId;
//	}

	public Integer getRegionId() {
		return regionId;
	}

	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}

	public Integer getCropType() {
		return cropType;
	}

	public void setCropType(Integer cropType) {
		this.cropType = cropType;
	}

	public Integer getSellerType() {
		return sellerType;
	}

	public void setSellerType(Integer seller_type) {
		this.sellerType = seller_type;
	}
}
