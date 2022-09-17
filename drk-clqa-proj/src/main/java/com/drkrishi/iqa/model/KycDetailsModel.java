package com.drkrishi.iqa.model;

import java.util.List;

public class KycDetailsModel {

	private Integer userId;
	private String taskId;
	private String farmerId;
	private String farmerName;
	private String farmerHusbandName;
	private String mobileNumber;
	private String alternateMobileNumber;
	private String documentType;
	private String basePath;
	private String idProofFront;
	private String idProofBack;
	private Integer gender;
	private String dateOfBirth;
	private String address;
	private String comment;
	private List<CommentModel> history;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

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

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getBasePath() {
		return basePath;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	public String getIdProofFront() {
		return idProofFront;
	}

	public void setIdProofFront(String idProofFront) {
		this.idProofFront = idProofFront;
	}

	public String getIdProofBack() {
		return idProofBack;
	}

	public void setIdProofBack(String idProofBack) {
		this.idProofBack = idProofBack;
	}

	public String getFarmerName() {
		return farmerName;
	}

	public void setFarmerName(String farmerName) {
		this.farmerName = farmerName;
	}

	public String getFarmerHusbandName() {
		return farmerHusbandName;
	}

	public void setFarmerHusbandName(String farmerHusbandName) {
		this.farmerHusbandName = farmerHusbandName;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getAlternateMobileNumber() {
		return alternateMobileNumber;
	}

	public void setAlternateMobileNumber(String alternateMobileNumber) {
		this.alternateMobileNumber = alternateMobileNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public List<CommentModel> getHistory() {
		return history;
	}

	public void setHistory(List<CommentModel> history) {
		this.history = history;
	}
}
