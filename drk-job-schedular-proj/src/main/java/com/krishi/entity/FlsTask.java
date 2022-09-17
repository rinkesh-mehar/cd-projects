package com.krishi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="fls_task")
public class FlsTask {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "task_id")
	private String taskId;
	
	@Column(name = "region_id")
	private Integer regionId;
	
	@Column(name = "assignee_id")
	private Integer assigneeId;
	
	@Column(name = "batch_number")
	private Integer batchNumber;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public Integer getRegionId() {
		return regionId;
	}

	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}

	public Integer getAssigneeId() {
		return assigneeId;
	}

	public void setAssigneeId(Integer assigneeId) {
		this.assigneeId = assigneeId;
	}

	public Integer getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(Integer batchNumber) {
		this.batchNumber = batchNumber;
	}
}
