package com.drkrishi.prm.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.sql.Timestamp;

/**
 * The persistent class for the DRKrishi_Users database table.
 * 
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DRKrishiUser {

	private static final long serialVersionUID = 1L;

	private int userId;

	private String firstName;

	private String middleName;

	private String lastName;

	private String emailAddress;

	private long mobileNumber;

	private int state;

	private int region;

//	private String createdBy;

//	private Timestamp createdDateTime;

//	private String modifiedBy;

//	private Timestamp modifiedDateTime;

//	private int reportingTo;

	public DRKrishiUser() {
	}

	public DRKrishiUser(int userId, String firstName, String middleName, String lastName, String emailAddress,
			long mobileNumber, int state, int region, String createdBy, Timestamp createdDateTime, String modifiedBy,
			Timestamp modifiedDateTime, int reportingTo) {

		this.userId = userId;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.emailAddress = emailAddress;
		this.mobileNumber = mobileNumber;
		this.state = state;
		this.region = region;
//		this.createdBy = createdBy;
//		this.createdDateTime = createdDateTime;
//		this.modifiedBy = modifiedBy;
//		this.modifiedDateTime = modifiedDateTime;
//		this.reportingTo = reportingTo;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public long getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getRegion() {
		return region;
	}

	public void setRegion(int region) {
		this.region = region;
	}

	/*public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}*/
/*
	public Timestamp getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(Timestamp createdDateTime) {
		this.createdDateTime = createdDateTime;
	}
*/
	/*public String getModifiedBy() {
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

	public int getReportingTo() {
		return reportingTo;
	}

	public void setReportingTo(int reportingTo) {
		this.reportingTo = reportingTo;
	} */

}