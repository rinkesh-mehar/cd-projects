
package com.drkrishi.usermanagement.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserModel {

	private String firstName;
	private String lastName;
	private String middleName;
	private String emailAddress;
	private String mobileNumber;
	private int state;
	private int region;
	private String stateName;
	private String regionName;
	private String createdDate;	
	private String createdby;
	private String modifiedby;
	private int reportingTo;
	private String userPassword;
	private int userId;
	private String oldPassword;
	private String newPassword;
	private String roleTitle;
	private String roleDescription;
	private int roleId;
	private String status;
	private String reportingName;
	private String comments;
	private String roleName;
	private int reportingRoleId;
	private int assigningRoleId;
	private String role;
	private String reportingRoleName;
	private String modifiedDate;
	private int regionId;
	private String ecosystem;
	private int user_id;
	
	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	private List<String> array;
	
	public List<String> getArray() {
		return array;
	}

	public void setArray(List<String> array) {
		this.array = array;
	}

	public String getEcosystem() {
		return ecosystem;
	}

	public void setEcosystem(String ecosystem) {
		this.ecosystem = ecosystem;
	}

	public int getRegionId() {
		return regionId;
	}

	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}

	public String getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getReportingRoleName() {
		return reportingRoleName;
	}

	public void setReportingRoleName(String reportingRoleName) {
		this.reportingRoleName = reportingRoleName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getAssigningRoleId() {
		return assigningRoleId;
	}

	public void setAssigningRoleId(int assigningRoleId) {
		this.assigningRoleId = assigningRoleId;
	}

	public int getReportingRoleId() {
		return reportingRoleId;
	}

	public void setReportingRoleId(int reportingRoleId) {
		this.reportingRoleId = reportingRoleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReportingName() {
		return reportingName;
	}

	public void setReportingName(String reportingName) {
		this.reportingName = reportingName;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRoleTitle() {
		return roleTitle;
	}

	public void setRoleTitle(String roleTitle) {
		this.roleTitle = roleTitle;
	}

	public String getRoleDescription() {
		return roleDescription;
	}

	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}


	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
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

	public int getRegion() {
		return region;
	}

	public void setRegion(int region) {
		this.region = region;
	}

	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	public String getModifiedby() {
		return modifiedby;
	}

	public void setModifiedby(String modifiedby) {
		this.modifiedby = modifiedby;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public int getReportingTo() {
		return reportingTo;
	}

	public void setReportingTo(int reportingTo) {
		this.reportingTo = reportingTo;
	}
}
