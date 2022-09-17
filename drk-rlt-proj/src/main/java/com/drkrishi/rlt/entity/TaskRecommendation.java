package com.drkrishi.rlt.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "task_recommendation")
public class TaskRecommendation {

	@Id
	@Column( name = "id")
	private String id;
	
	@Column( name = "recommendation_id")
	private Integer recommendationId;
	
	@Column( name = "task_id")
	private String taskId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getRecommendationId() {
		return recommendationId;
	}

	public void setRecommendationId(Integer recommendationId) {
		this.recommendationId = recommendationId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
}
