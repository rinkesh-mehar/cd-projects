package com.drkrishi.prm.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class DrKrishiUserCredientials implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private int credientials_id;
		
	private int userId;
	
	private int status;
	
	private String password;
	
	private String createdBy;
	
	private Timestamp createdDateTime;

	private String modifiedBy;
	
	private Timestamp modifiedDateTime;
	
	private int isOTP;
	
	private Timestamp passwordResetDate;
	
	private int systemPasswordExpired;
	
	private int invalidAttempts;
	
	private String comments;

	

	public DrKrishiUserCredientials() {}
	
	public DrKrishiUserCredientials(int userId, int status, String password, String createdBy,
			Timestamp createdDateTime, String modifiedBy, Timestamp modifiedDateTime, int isOTP,
			Timestamp passwordResetDate, int systemPasswordExpired, int invalidAttempts, String comments) {
		
		this.userId = userId;
		this.status = status;
		this.password = password;
		this.createdBy = createdBy;
		this.createdDateTime = createdDateTime;
		this.modifiedBy = modifiedBy;
		this.modifiedDateTime = modifiedDateTime;
		this.isOTP = isOTP;
		this.passwordResetDate = passwordResetDate;
		this.systemPasswordExpired = systemPasswordExpired;
		this.invalidAttempts = invalidAttempts;
		this.comments = comments;
	}


	public int getCredientials_id() {
		return credientials_id;
	}

	public void setCredientials_id(int credientials_id) {
		this.credientials_id = credientials_id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(Timestamp createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Timestamp getModifiedDateTime() {
		return modifiedDateTime;
	}

	public void setModifiedDateTime(Timestamp modifiedDateTime) {
		this.modifiedDateTime = modifiedDateTime;
	}

	public int getIsOTP() {
		return isOTP;
	}

	public void setIsOTP(int isOTP) {
		this.isOTP = isOTP;
	}

	public Timestamp getPasswordResetDate() {
		return passwordResetDate;
	}

	public void setPasswordResetDate(Timestamp passwordResetDate) {
		this.passwordResetDate = passwordResetDate;
	}

	public int getSystemPasswordExpired() {
		return systemPasswordExpired;
	}

	public void setSystemPasswordExpired(int systemPasswordExpired) {
		this.systemPasswordExpired = systemPasswordExpired;
	}

	public int getInvalidAttempts() {
		return invalidAttempts;
	}

	public void setInvalidAttempts(int invalidAttempts) {
		this.invalidAttempts = invalidAttempts;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	
	
	
	
}
