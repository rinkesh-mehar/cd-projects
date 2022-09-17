package com.drkrishi.rlt.entity;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "task_history")
public class TaskHistory {
	
	@Id
	@Column(name = "id")
	private String id;
	
	@Column(name = "task_id")
	private String taskId;
	
	@Column(name = "task_date")
	private Date taskDate;
	
	@Column(name = "start_time")
	private Time startTime;
	
	@Column(name = "end_time")
	private Time endTime;
	
	@Column(name = "task_type_id")
	private Integer taskTypeId;
	
	@Column(name = "assignee_id")
	private Integer assigneeId;
	
	@Column(name = "status")
	private Integer status;
	
	@Column(name = "entity_type_id")
	private Integer entityTypeId;
	
	@Column(name = "entity_id")
	private String entityId;
	
	@Column(name = "comment")
	private String comment;

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

	public Date getTaskDate() {
		return taskDate;
	}

	public void setTaskDate(Date taskDate) {
		this.taskDate = taskDate;
	}

	public Time getStartTime() {
		return startTime;
	}

	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}

	public Time getEndTime() {
		return endTime;
	}

	public void setEndTime(Time endTime) {
		this.endTime = endTime;
	}

	public Integer getTaskTypeId() {
		return taskTypeId;
	}

	public void setTaskTypeId(Integer taskTypeId) {
		this.taskTypeId = taskTypeId;
	}

	public Integer getAssigneeId() {
		return assigneeId;
	}

	public void setAssigneeId(Integer assigneeId) {
		this.assigneeId = assigneeId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getEntityTypeId() {
		return entityTypeId;
	}

	public void setEntityTypeId(Integer entityTypeId) {
		this.entityTypeId = entityTypeId;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
