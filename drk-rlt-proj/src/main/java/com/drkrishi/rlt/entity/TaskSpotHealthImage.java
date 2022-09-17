package com.drkrishi.rlt.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "task_spot_health_image")
public class TaskSpotHealthImage {
	
	@Id
	@Column(name = "id")
	private String id;
	
	@Column(name = "task_spot_id")
	private String taskSpotId;
	
	@Column(name = "side")
	private String side;
	
	@Column(name = "image_url")
	private String imageUrl;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTaskSpotId() {
		return taskSpotId;
	}

	public void setTaskSpotId(String taskSpotId) {
		this.taskSpotId = taskSpotId;
	}

	public String getSide() {
		return side;
	}

	public void setSide(String side) {
		this.side = side;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}
