package com.drkrishi.prm.model;

public class ResponseModel {

	private int statusCode;

	private String msg;
	
	private String prsName;

	public String getPrsName() {
		return prsName;
	}

	public void setPrsName(String prsName) {
		this.prsName = prsName;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "ResponseModel [statusCode=" + statusCode + ", msg=" + msg + ", prsName=" + prsName + ", getPrsName()="
				+ getPrsName() + ", getStatusCode()=" + getStatusCode() + ", getMsg()=" + getMsg() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

	

}
