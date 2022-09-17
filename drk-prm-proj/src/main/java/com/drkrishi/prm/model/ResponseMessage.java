package com.drkrishi.prm.model;

import java.util.List;

public class ResponseMessage {
	private String statusCode;
	private String message;
	private List<String> data;

	public ResponseMessage() {
		super();
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getData() {
		return data;
	}

	public void setData(List<String> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ResponseMessage [statusCode=" + statusCode + ", message=" + message + ", data=" + data + "]";
	}

}
