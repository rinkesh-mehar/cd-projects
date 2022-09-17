package com.drkrishi.rlt.model;

public class ReassignModel {
	
	private Integer rlmUserId;
	private String taskId;
	private Integer rltUserId;
	private String comment;
	
	
	public Integer getRlmUserId() {
		return rlmUserId;
	}
	public void setRlmUserId(Integer rlmUserId) {
		this.rlmUserId = rlmUserId;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public Integer getRltUserId() {
		return rltUserId;
	}
	public void setRltUserId(Integer rltUserId) {
		this.rltUserId = rltUserId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
}
