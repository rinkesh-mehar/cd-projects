package com.drkrishi.usermanagement.model;

public class jwtToken {

	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public jwtToken(String token) {
		this.token = token;
	}
	
}
