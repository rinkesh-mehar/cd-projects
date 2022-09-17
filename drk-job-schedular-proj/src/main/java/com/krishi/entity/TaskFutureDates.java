package com.krishi.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "task_future_dates")
public class TaskFutureDates {

	@Id
	@Column(name = "id")
	private int id;

	@Column(name = "task_id")
	private String taskId;

	@Column(name = "task_date")
	private Date taskDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	@Override
	public String toString() {
		return "TaskFutureDates [id=" + id + ", taskId=" + taskId + ", taskDate=" + taskDate + "]";
	}

}