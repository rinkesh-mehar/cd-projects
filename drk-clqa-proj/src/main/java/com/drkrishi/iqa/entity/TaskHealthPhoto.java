package com.drkrishi.iqa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "task_health_photos")
public class TaskHealthPhoto {
	
	@Id
	@Column(name = "id")
	private String id;
	
	@Column(name = "task_id")
	private String taskId;
	
	@Column(name = "left_photo")
	private String leftPhoto;
	
	@Column(name = "right_photo")
	private String rightPhoto;
	
	@Column(name = "front_photo")
	private String frontPhoto;
	
	@Column(name = "spot_id")
	private String spotId;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getLeftPhoto() {
		return leftPhoto;
	}
	public void setLeftPhoto(String leftPhoto) {
		this.leftPhoto = leftPhoto;
	}
	public String getRightPhoto() {
		return rightPhoto;
	}
	public void setRightPhoto(String rightPhoto) {
		this.rightPhoto = rightPhoto;
	}
	public String getFrontPhoto() {
		return frontPhoto;
	}
	public void setFrontPhoto(String frontPhoto) {
		this.frontPhoto = frontPhoto;
	}
	public String getSpotId() {
		return spotId;
	}
	public void setSpotId(String spotId) {
		this.spotId = spotId;
	}
	
}
