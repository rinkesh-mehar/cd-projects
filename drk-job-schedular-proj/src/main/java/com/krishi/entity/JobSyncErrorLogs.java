package com.krishi.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "job_sync_error_logs")
@Entity
public class JobSyncErrorLogs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer jobSyncDetailsId;

    private String errorDetail;

    private Timestamp createdDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getJobSyncDetailsId() {
        return jobSyncDetailsId;
    }

    public void setJobSyncDetailsId(Integer jobSyncDetailsId) {
        this.jobSyncDetailsId = jobSyncDetailsId;
    }

    public String getErrorDetail() {
        return errorDetail;
    }

    public void setErrorDetail(String errorDetail) {
        this.errorDetail = errorDetail;
    }

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

    
}
