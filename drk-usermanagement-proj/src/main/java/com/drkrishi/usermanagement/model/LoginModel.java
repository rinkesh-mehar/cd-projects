package com.drkrishi.usermanagement.model;

import com.fasterxml.jackson.annotation.JsonInclude;



@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginModel {
	
	private String mobileNumber;
	private String userPassword;
	private String channel;
	
	
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	
	
}
