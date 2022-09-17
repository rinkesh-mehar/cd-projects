package com.krishi.fls.entity;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "task_history")
public class TaskHistory {

    private static final SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	@Id
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

	@JsonProperty("entityTypeId")
	@Column(name = "entity_type_id")
	private Integer caseTypeId;

	@JsonProperty("entityId")
	@Column(name = "entity_id")
	private String caseId;

	@Column(name = "comment")
	private String comment;


	@Column(name = "farmer_crop_info_id")
	private String farmerCropInfoId;

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


	public void setStartTime(String  startTime) {
		try {
			java.util.Date date = sd.parse(startTime);
			this.startTime = new Time(date.getTime());
		} catch (ParseException e) {
			this.startTime = null;
		}
	}

	public Time getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		try {
			java.util.Date date = sd.parse(endTime);
			this.endTime = new Time(date.getTime());
		} catch (ParseException e) {
			this.endTime = null;
		}
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
	@JsonProperty("entityTypeId")
	public Integer getCaseTypeId() {
		return caseTypeId;
	}
	@JsonProperty("entityTypeId")
	public void setCaseTypeId(Integer caseTypeId) {
		this.caseTypeId = caseTypeId;
	}
	@JsonProperty("entityId")
	public String getCaseId() {
		return caseId;
	}
	@JsonProperty("entityId")
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getFarmerCropInfoId() {
		return farmerCropInfoId;
	}

	public void setFarmerCropInfoId(String farmerCropInfoId) {
		this.farmerCropInfoId = farmerCropInfoId;
	}
}
