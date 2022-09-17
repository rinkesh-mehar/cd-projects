package com.krishi.entity;

import com.krishi.model.EntityModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "task_spot_stress")
public class TaskSpotStress implements Cloneable, EntityModel {

	@Id
	@Column(name = "id")
	private String id;
	
	@Column(name = "task_spot_id")
	private String taskSpotId;

	@Column(name = "stress_id")
	private Integer stressId;

	public TaskSpotStress(TaskSpotStress taskSpotStress) {
	}

	public TaskSpotStress() {

	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public String getTaskSpotId() {
		return taskSpotId;
	}

	public void setTaskSpotId(String taskSpotId) {
		this.taskSpotId = taskSpotId;
	}

	public Integer getStressId() {
		return stressId;
	}

	public void setStressId(Integer stressId) {
		this.stressId = stressId;
	}
}
