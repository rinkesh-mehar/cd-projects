package com.drkrishi.usermanagement.model;

import com.fasterxml.jackson.annotation.JsonInclude;



@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserListModel {

	private int user_id;
	private String name;
	private String  mobileNumber;
	private String state;
	private String status;
	private String reportingTo;
	private java.sql.Timestamp loginTime;
	private int reportingRoleId;
	private int stateId;
	private int regionId;
	private String regionName;
	private String role;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public int getRegionId() {
		return regionId;
	}

	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}

	public int getReportingRoleId() {
		return reportingRoleId;
	}

	public int getStateId() {
		return stateId;
	}

	public void setStateId(int stateId) {
		this.stateId = stateId;
	}

	public void setReportingRoleId(int reportingRoleId) {
		this.reportingRoleId = reportingRoleId;
	}

	public UserListModel(){}
	
	public UserListModel(int user_id, String name, String mobileNumber, String state, String status,
			String reportingTo,java.sql.Timestamp loginTime) {
		this.user_id = user_id;
		this.name = name;
		this.mobileNumber = mobileNumber;
		this.state = state;
		this.status = status;
		this.reportingTo = reportingTo;
		this.loginTime=loginTime;
	}

	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getReportingTo() {
		return reportingTo;
	}
	public void setReportingTo(String reportingTo) {
		this.reportingTo = reportingTo;
	}
	public java.sql.Timestamp getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(java.sql.Timestamp loginTime) {
		this.loginTime = loginTime;
	}
	
	
	public UserListModel(int stateId, String state) {
		this.stateId = stateId;
		this.state = state;
	}
	
	
}
