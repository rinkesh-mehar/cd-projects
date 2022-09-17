package com.drkrishi.prm.model;

import java.util.List;

public class PrsName_ListResponseModel {
	private String statusCode;
	private String message;
	private List<String> data;

	public PrsName_ListResponseModel() {
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
		return "PrsName_ListResponseModel [statusCode=" + statusCode + ", message=" + message + ", data=" + data + "]";
	}

}
