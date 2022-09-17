// Generated with g9.

package com.krishi.entity;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="task")
public class Task implements Serializable {
 
    @Id
    private String id;
    
    @Column(name="task_date")
    private Date taskDate;
    @Column(name="start_time")
    private Time startTime;
    
    @Column(name="end_time")
    private Time endTime;
    
    @Column(name="task_type_id")
    private Integer taskTypeId;
    @Column(name="assignee_id")
    private Integer assigneeId;

    private Integer status;
    @Column(name="entity_type_id")
    private Integer entityTypeId;
    
    @Column(name="entity_id")
    private String entityId;
    
    @Column(name="barcode")
    private String barcode;

    @Column(name = "farmer_crop_info_id")
	private String farmerCropInfoId;

	public String getFarmerCropInfoId() {
		return farmerCropInfoId;
	}

	public void setFarmerCropInfoId(String farmerCropInfoId) {
		this.farmerCropInfoId = farmerCropInfoId;
	}

	public Task() {
		super();
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
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEntityId() {
		return entityId;
	}
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}
}
