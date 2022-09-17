package com.drkrishi.usermanagement.model;

public class ErrorMessage {

	private String errorId;
	private String errorMessage;
	
	
	public String getErrorId() {
		return errorId;
	}
	public void setErrorId(String errorId) {
		this.errorId = errorId;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public ErrorMessage(String errorId, String errorMessage) {
		super();
		this.errorId = errorId;
		this.errorMessage = errorMessage;
	}
	
}
