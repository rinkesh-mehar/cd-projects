package com.drkrishi.rlt.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "barcode_details")
public class BarcodeDetails {
	
	@Id
	@Column(name = "task_id")
	private Integer taskId;
	
	@Column(name = "barcode_number")
	private String barcodeNumber;
	
	@Column(name = "case_id")
	private Integer caseId;
	
	@Column(name = "assignee_id")
	private Integer assigneeId;
	
	@Column(name = "status")
	private Integer status;
	
	@Column(name = "case_type_id")
	private Integer caseTypeId;
	
	@Column(name = "village_name")
	private String villageName;
	
	@Column(name = "former_mobile")
	private String formerMobile;
	
	@Column(name = "crop_name")
	private String cropName;
	
	@Column(name = "stress_id")
	private Integer stressId;
	
	@Column(name = "task_date")
	private Date taskDate;

	public String getBarcodeNumber() {
		return barcodeNumber;
	}

	public void setBarcodeNumber(String barcodeNumber) {
		this.barcodeNumber = barcodeNumber;
	}

	public Integer getCaseId() {
		return caseId;
	}

	public void setCaseId(Integer caseId) {
		this.caseId = caseId;
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

	public Integer getStressId() {
		return stressId;
	}

	public void setStressId(Integer stressId) {
		this.stressId = stressId;
	}

	public Date getTaskDate() {
		return taskDate;
	}

	public void setTaskDate(Date taskDate) {
		this.taskDate = taskDate;
	}

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
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

	public Integer getCaseTypeId() {
		return caseTypeId;
	}

	public void setCaseTypeId(Integer caseTypeId) {
		this.caseTypeId = caseTypeId;
	}
	
}
