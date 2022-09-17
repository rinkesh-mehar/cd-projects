// Generated with g9.

package com.drkrishi.rlt.entity;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="task")
public class Task implements Serializable {

   
    @Id
    @Column(name="id")
    private String id;
    @Column(name="task_date")
    private Date taskDate;
    @Column(name="start_time")
    private Time taskTime;
    
    @Column(name="end_time")
    private Time endTime;
    
    @Column(name="task_type_id", nullable=false, precision=10)
    private int taskTypeId;
    @Column(name="assignee_id", precision=10)
    private int assigneeId;
    @Column(length=3)
    private int status;
    @Column(name="entity_type_id")
    private int entityTypeId;
    @Column(name="entity_id")
    private String entityId;
    @Column(name="barcode")
    private String barcode;
    
	public Time getEndTime() {
		return endTime;
	}
	public void setEndTime(Time endTime) {
		this.endTime = endTime;
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
	public Time getTaskTime() {
		return taskTime;
	}
	public void setTaskTime(Time taskTime) {
		this.taskTime = taskTime;
	}
	public int getTaskTypeId() {
		return taskTypeId;
	}
	public void setTaskTypeId(int taskTypeId) {
		this.taskTypeId = taskTypeId;
	}
	public int getAssigneeId() {
		return assigneeId;
	}
	public void setAssigneeId(int assigneeId) {
		this.assigneeId = assigneeId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	public int getEntityTypeId() {
		return entityTypeId;
	}
	public void setEntityTypeId(int entityTypeId) {
		this.entityTypeId = entityTypeId;
	}
	public String getEntityId() {
		return entityId;
	}
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
}
