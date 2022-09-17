package com.krishi.entity;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.krishi.model.EntityModel;

@Entity
@Table(name="task")
public class Task implements EntityModel {
 
    @Id
    private String id;
    
    @Column(name="task_date")
    private Date taskDate;
    
    @Column(name="task_type_id")
    private Integer taskTypeId;
    
    @Column(name="assignee_id")
    private Integer assigneeId;

    private Integer status;
    
    @Column(name="entity_type_id")
    private Integer entityTypeId;
    
    @Column(name="entity_id")
    private String entityId;
    
	@Column(name = "start_time")
	private Time startTime;

	@Column(name = "end_time")
	private Time endTime;

	@Column(name = "barcode")
	private String barcode;

	@Column(name = "farmer_crop_info_id")
	private String farmerCropInfoId;
	/** pushback variable: Ujwal- Start */
	
	@Column(name = "pushback")
	private Integer pushback;

	@Column(name = "ordering")
	private Integer ordering;
	
    
	public Integer getPushback() {
		return pushback;
	}

	public void setPushback(Integer pushback) {
		this.pushback = pushback;
	}
	/** pushback variable: Ujwal- End */

	public Task() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public Date getTaskDate() {
		return taskDate;
	}

	public void setTaskDate(Date taskDate) {
		this.taskDate = taskDate;
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

	public Time getStartTime() {
		return startTime;
	}

	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}

	public void setStartTime(String startTime) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
			this.startTime = new Time(format.parse(startTime).getTime());
		} catch (Exception e){}
	}


	public Time getEndTime() {
		return endTime;
	}

	public void setEndTime(Time endTime) {
		this.endTime = endTime;
	}

	public void setEndTime(String endTime) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
			this.endTime = new Time(format.parse(endTime).getTime());
		} catch (Exception e){}
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public void setBarCode(String barcode) {
		this.barcode = barcode;
	}

	public String getFarmerCropInfoId() {
		return farmerCropInfoId;
	}

	public void setFarmerCropInfoId(String farmerCropInfoId) {
		this.farmerCropInfoId = farmerCropInfoId;
	}

	public Integer getOrdering() {
		return ordering;
	}

	public void setOrdering(Integer ordering) {
		this.ordering = ordering;
	}
}
