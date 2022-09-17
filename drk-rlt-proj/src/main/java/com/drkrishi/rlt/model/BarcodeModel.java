package com.drkrishi.rlt.model;

import org.springframework.stereotype.Component;

@Component
public class BarcodeModel {

	private String barcodeNumber;
	private String taskId;
	private String entityId;
	private String village;
    private String formerMobile;
    private String crop;
//    private String causedBy;
//    private String status;
    private String receivedDate;
    private Integer cropType;
	private Integer sellerType;
    
  
	public String getBarcodeNumber() {
		return barcodeNumber;
	}
	public void setBarcodeNumber(String barcodeNumber) {
		this.barcodeNumber = barcodeNumber;
	}
	public String getFormerMobile() {
		return formerMobile;
	}
	public void setFormerMobile(String formerMobile) {
		this.formerMobile = formerMobile;
	}
	public String getVillage() {
		return village;
	}
	public void setVillage(String village) {
		this.village = village;
	}
	public String getCrop() {
		return crop;
	}
	public void setCrop(String crop) {
		this.crop = crop;
	}
//	public String getCausedBy() {
//		return causedBy;
//	}
//	public void setCausedBy(String causedBy) {
//		this.causedBy = causedBy;
//	}
//	public String getStatus() {
//		return status;
//	}
//	public void setStatus(String status) {
//		this.status = status;
//	}
	public String getReceivedDate() {
		return receivedDate;
	}
	public void setReceivedDate(String receivedDate) {
		this.receivedDate = receivedDate;
	}
	
	public String getEntityId() {
		return entityId;
	}
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
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

	public void setSellerType(Integer sellerType) {
		this.sellerType = sellerType;
	}
}
