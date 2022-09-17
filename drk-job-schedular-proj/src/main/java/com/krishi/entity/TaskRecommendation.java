package com.krishi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.krishi.model.EntityModel;

@Entity
@Table(name="task_recommendation")
public class TaskRecommendation implements EntityModel {
	
	@Id
	@Column(name = "id")
	private String id;
	

	@Column(name = "task_id")
	private String taskId;
	
	
	@Column(name = "recommendation_id")
	private Integer recommendationId;

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

	public Integer getRecommendationId() {
		return recommendationId;
	}

	public void setRecommendationId(Integer recommendationId) {
		this.recommendationId = recommendationId;
	}

}
