package com.drkrishi.rlt.model;

import com.fasterxml.jackson.annotation.JsonInclude;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseMessage {
	private int statusCode;
	private String message;
	private Object data;
	
	public Object getData() {
		return data;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	public ResponseMessage(){}
	
}
