package com.krishi.model;

public class CropdataSecurity {
	private String password;
	private String userName;
	private long securityMode;

	public String getPassword() {
		return password;
	}

	public void setPassword(String value) {
		this.password = value;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String value) {
		this.userName = value;
	}

	public long getSecurityMode() {
		return securityMode;
	}

	public void setSecurityMode(long value) {
		this.securityMode = value;
	}
}
