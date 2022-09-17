package com.drkrishi.usermanagement.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ViewUserModel {
    
	private int user_id;
	private String firstName;
	private String middleName;
	private String lastName;
	private String emailAddress;
	private String mobileNumber;
	private int state;
	private String stateName;
	private int region;
	private String regionName;
	private int roleId;
	private String roleName;
	private int reportingRoleId;
	private String reportingRoleName;
	private String status;
	private String createdby;
	private int reportingTo;
	private String reportingName;
	private String modifiedDate;
	private String createdDate;
	private String comments;
	
	
	

	public ViewUserModel() {}
	
	public ViewUserModel(int user_id, String firstName, String middleName, String lastName, String emailAddress,
			String mobileNumber, int state, String stateName, int region, String regionName, int roleId,
			String roleName, int reportingRoleId, String reportingRoleName, String status, String createdby,
			int reportingTo, String reportingName,  String createdDate, String modifiedDate, String comments) {
		
		this.user_id = user_id;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.emailAddress = emailAddress;
		this.mobileNumber = mobileNumber;
		this.state = state;
		this.stateName = stateName;
		this.region = region;
		this.regionName = regionName;
		this.roleId = roleId;
		this.roleName = roleName;
		this.reportingRoleId = reportingRoleId;
		this.reportingRoleName = reportingRoleName;
		this.status = status;
		this.createdby = createdby;
		this.reportingTo = reportingTo;
		this.reportingName = reportingName;
		this.createdDate=createdDate;
		this.modifiedDate=modifiedDate;
		this.comments=comments;
	}

	public String getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
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

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public int getRegion() {
		return region;
	}

	public void setRegion(int region) {
		this.region = region;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public int getReportingRoleId() {
		return reportingRoleId;
	}

	public void setReportingRoleId(int reportingRoleId) {
		this.reportingRoleId = reportingRoleId;
	}

	public String getReportingRoleName() {
		return reportingRoleName;
	}

	public void setReportingRoleName(String reportingRoleName) {
		this.reportingRoleName = reportingRoleName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	public int getReportingTo() {
		return reportingTo;
	}

	public void setReportingTo(int reportingTo) {
		this.reportingTo = reportingTo;
	}

	public String getReportingName() {
		return reportingName;
	}

	public void setReportingName(String reportingName) {
		this.reportingName = reportingName;
	}
	
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	
    
}