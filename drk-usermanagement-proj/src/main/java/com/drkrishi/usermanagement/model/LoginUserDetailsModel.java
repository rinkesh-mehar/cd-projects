package com.drkrishi.usermanagement.model;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginUserDetailsModel {

	public int userId;
	public Date lastLoginTime;
	public String mobileNumber;
	public String name;
	public String roleName;
	public String roleCode;
	public List<MenuModel> menuUrl;
	public int is_forced_pwd_change;	
	public int stateId;
	public LocalDate date;
	public int weekNumber;
	public String regionCode;
	public String passwordChangeMessage;
	public int regionId;
	public String cropDataApiKey;

	public String getCropDataApiKey() {
		return cropDataApiKey;
	}


	public void setCropDataApiKey(String cropDataApiKey) {
		this.cropDataApiKey = cropDataApiKey;
	}


	public int getRegionId() {
		return regionId;
	}


	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}


	public LoginUserDetailsModel(int userId, Date lastLoginTime, String mobileNumber, String name, String roleName,String roleCode, List<MenuModel> menuUrl, int is_forced_pwd_change, int stateId, LocalDate date, int weekNumber, String regionCode,int regionId,String cropDataApiKey) {
		this.userId = userId;
		this.lastLoginTime = lastLoginTime;
		this.mobileNumber = mobileNumber;
		this.name = name;
		this.roleName = roleName;
		this.roleCode = roleCode;
		this.menuUrl = menuUrl;
		this.is_forced_pwd_change=is_forced_pwd_change;
		this.stateId = stateId;
		this.date=date;
		this.weekNumber=weekNumber;
		this.regionCode = regionCode;
		this.regionId=regionId;
		this.cropDataApiKey=cropDataApiKey;
	}
	

	public String getRegionCode() {
		return regionCode;
	}


	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public String getPasswordChangeMessage() {
		return passwordChangeMessage;
	}


	public void setPasswordChangeMessage(String passwordChangeMessage) {
		this.passwordChangeMessage = passwordChangeMessage;
	}


	public int getWeekNumber() {
		return weekNumber;
	}

	public void setWeekNumber(int weekNumber) {
		this.weekNumber = weekNumber;
	}
	
	public List<MenuModel> getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(List<MenuModel> menuUrl) {
		this.menuUrl = menuUrl;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public int getIs_forced_pwd_change() {
		return is_forced_pwd_change;
	}


	public void setIs_forced_pwd_change(int is_forced_pwd_change) {
		this.is_forced_pwd_change = is_forced_pwd_change;
	}


	
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public int getStateId() {
		return stateId;
	}

	public void setStateId(int stateId) {
		this.stateId = stateId;
	}

	
}



