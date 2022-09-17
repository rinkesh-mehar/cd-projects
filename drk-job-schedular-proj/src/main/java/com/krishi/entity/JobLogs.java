package com.krishi.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "job_logs")
public class JobLogs {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Integer id;
	
	private String errorDetails;
	
	private Date createdDate;

	public JobLogs() {
		super();
	}

	public JobLogs(Integer id, String errorDetails, Date createdDate) {
		super();
		this.id = id;
		this.errorDetails = errorDetails;
		this.createdDate = createdDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getErrorDetails() {
		return errorDetails;
	}

	public void setErrorDetails(String errorDetails) {
		this.errorDetails = errorDetails;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

}
