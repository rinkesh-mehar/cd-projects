package com.krishi.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="job_sync_details")
@Entity
public class JobSyncDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private Integer userId;
	
	private String fileName;
	
	private Timestamp processedDate;
	
	private Byte processedStatus;
	
	private Timestamp createdDate;
	
	private Timestamp modifiedDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Timestamp getProcessedDate() {
		return processedDate;
	}

	public void setProcessedDate(Timestamp processedDate) {
		this.processedDate = processedDate;
	}

	public Byte getProcessedStatus() {
		return processedStatus;
	}

	public void setProcessedStatus(Byte processedStatus) {
		this.processedStatus = processedStatus;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Timestamp getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	

}

	
