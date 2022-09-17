package com.drkrishi.rlt.model;

import java.util.List;

public class RecommendationModel {
	
	private Integer userId;
	private String taskId;
	private Double severityValue;
	private String severityType;
	private String label;
	private List<RecommendationStress> stresses;
	private String comment;
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Double getSeverityValue() {
		return severityValue;
	}
	public void setSeverityValue(Double severityValue) {
		this.severityValue = severityValue;
	}
	public String getSeverityType() {
		return severityType;
	}
	public void setSeverityType(String severityType) {
		this.severityType = severityType;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public List<RecommendationStress> getStresses() {
		return stresses;
	}
	public void setStresses(List<RecommendationStress> stresses) {
		this.stresses = stresses;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
}
