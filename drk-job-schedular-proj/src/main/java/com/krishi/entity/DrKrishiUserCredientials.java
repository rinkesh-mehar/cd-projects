package com.krishi.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_credentials")
public class DrKrishiUserCredientials {

    @Id
    private Integer id;
    
    @Column(name="created_by")
    private String createdBy;
    
    @Column(name="created_date_time")
    private Timestamp createdDateTime;
    
    @Column(name="modified_date_time")
    private Timestamp modifiedDateTime;
    
    @Column(name="modified_by")
    private String modifiedBy;
    
    @Column(name="password_reset_date")
    private Timestamp passwordResetDate;
    
    @Column(name="is_forced_pwd_change")
    private Integer isForcedPwdChange;
    
    @Column(name="user_password")
    private String userPassword;
    
    @Column(name="user_id")
    private Integer userId;
    
    @Column(name="invalid_attempts")
    private Integer invalidAttempts;
    
    private Integer transactionType;

	public DrKrishiUserCredientials() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Timestamp getModifiedDateTime() {
		return modifiedDateTime;
	}

	public void setModifiedDateTime(Timestamp modifiedDateTime) {
		this.modifiedDateTime = modifiedDateTime;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Timestamp getPasswordResetDate() {
		return passwordResetDate;
	}

	public void setPasswordResetDate(Timestamp passwordResetDate) {
		this.passwordResetDate = passwordResetDate;
	}

	public Integer getIsForcedPwdChange() {
		return isForcedPwdChange;
	}

	public void setIsForcedPwdChange(Integer isForcedPwdChange) {
		this.isForcedPwdChange = isForcedPwdChange;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getInvalidAttempts() {
		return invalidAttempts;
	}

	public void setInvalidAttempts(Integer invalidAttempts) {
		this.invalidAttempts = invalidAttempts;
	}

	public Integer getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(Integer transactionType) {
		this.transactionType = transactionType;
	}

}