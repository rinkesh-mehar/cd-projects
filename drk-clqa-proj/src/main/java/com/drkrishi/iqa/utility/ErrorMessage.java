package com.drkrishi.iqa.utility;

public enum ErrorMessage {
	GENERIC("error","There is a system error. Please contact your system administrator");
	
	private String status;
	private String message;
	
	private ErrorMessage(String status, String message) {
		this.status = status;
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}
	
}
