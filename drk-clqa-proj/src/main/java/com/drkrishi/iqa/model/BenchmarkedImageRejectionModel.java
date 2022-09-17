package com.drkrishi.iqa.model;

public class BenchmarkedImageRejectionModel {

	private Integer userId;
	private String benchmarkedImageId;
	private String comment;
	private String status;
	
	public String getBenchmarkedImageId() {
		return benchmarkedImageId;
	}
	public void setBenchmarkedImageId(String benchmarkedImageId) {
		this.benchmarkedImageId = benchmarkedImageId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
