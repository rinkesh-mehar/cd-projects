package com.krishi.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="job_execution_details")
public class JobExecutionDetails {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Integer id;
	
	private Integer jobId;
	
	private Timestamp executionDateTime;
	
	private Timestamp createdDate;

	public JobExecutionDetails() {
		super();
	}

	

	public JobExecutionDetails(Integer id, Integer jobId, Timestamp executionDateTime, Timestamp createdDate) {
		super();
		this.id = id;
		this.jobId = jobId;
		this.executionDateTime = executionDateTime;
		this.createdDate = createdDate;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getJobId() {
		return jobId;
	}



	public void setJobId(Integer jobId) {
		this.jobId = jobId;
	}



	public Timestamp getExecutionDateTime() {
		return executionDateTime;
	}

	public void setExecutionDateTime(Timestamp executionDateTime) {
		this.executionDateTime = executionDateTime;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	
	

}
