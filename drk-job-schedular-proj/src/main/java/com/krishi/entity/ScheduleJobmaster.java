package com.krishi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name="scheduled_job2")
public class ScheduleJobmaster {

	@Id
	@Column(name="id")
	private Integer id;
	
	private String name;
	
	private Boolean status;
	
	private String scheduleTime;
	
	private Date createdDate;

	public ScheduleJobmaster() {
		super();
	}



	public ScheduleJobmaster(Integer id, String name, Boolean status, String scheduleTime,
			Date createdDate) {
		super();
		this.id = id;
		this.name = name;
		this.status = status;
		this.scheduleTime = scheduleTime;
		this.createdDate = createdDate;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getScheduleTime() {
		return scheduleTime;
	}

	public void setScheduleTime(String scheduleTime) {
		this.scheduleTime = scheduleTime;
	}
	
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
}
