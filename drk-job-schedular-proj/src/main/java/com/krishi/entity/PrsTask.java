package com.krishi.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name="prs_task")
public class PrsTask {
	private static final SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
	@Id
	@Column(name="id")
	private Integer taskId;
	
	@Column(name="village_id")
	private Integer villagesId;
	
	@Column(name="prs_assignment_id")
	private Integer assignedId;
	
	@Column(name="is_completed")
	private Integer isCompleted;
	
	@Column(name="completed_date")
	private Date completedDate;
	
	@Column(name = "scheduled_date")
	private Date scheduledDate;
	
	public Date getScheduledDate() {
		return scheduledDate;
	}

	public void setScheduledDate(String scheduledDate) {
		try {
			this.scheduledDate = sd.parse(scheduledDate);
		} catch (ParseException e) {
			
		}
	}

	public PrsTask() {
		super();
	}

	public PrsTask(Integer taskId, Integer villagesId, Integer assignedId, Integer isCompleted, Date completedDate) {
		super();
		this.taskId = taskId;
		this.villagesId = villagesId;
		this.assignedId = assignedId;
		this.isCompleted = isCompleted;
		this.completedDate = completedDate;
	}

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public Integer getVillagesId() {
		return villagesId;
	}

	public void setVillagesId(Integer villagesId) {
		this.villagesId = villagesId;
	}

	public Integer getAssignedId() {
		return assignedId;
	}

	public void setAssignedId(Integer assignedId) {
		this.assignedId = assignedId;
	}

	public Integer getIsCompleted() {
		return isCompleted;
	}

	public void setIsCompleted(Integer isCompleted) {
		this.isCompleted = isCompleted;
	}

	public Date getCompletedDate() {
		return completedDate;
	}

	public void setCompletedDate(Date completedDate) {
		this.completedDate = completedDate;
	}
	
	public void setCompletedDate(String completedDate) {
		try {
			this.completedDate = sd.parse(completedDate);
		} catch (ParseException e) {
			
		}
	}
	
	
	
}
