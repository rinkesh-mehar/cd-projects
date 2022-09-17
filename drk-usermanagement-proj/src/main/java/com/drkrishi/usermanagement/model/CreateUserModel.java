package com.drkrishi.usermanagement.model;

public class CreateUserModel {
	
	private String firstName;
	private String middleName;
	private String lastName;
	private String mobileNumber;
	private String emailAddress;
	private int roleId;
	private int state;
	private int region;
	private int reportingTo;
	private String createdby;
	
	
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
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
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
	public int getReportingTo() {
		return reportingTo;
	}
	public void setReportingTo(int reportingTo) {
		this.reportingTo = reportingTo;
	}
	public String getCreatedby() {
		return createdby;
	}
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
}
