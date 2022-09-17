package com.drkrishi.usermanagement.model;

import com.fasterxml.jackson.annotation.JsonInclude;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseMessage {
	private jwtToken header;
	private StatusMessage status;
	private ErrorMessage error;
	private Object data;
	private Config config;
	public Object downloadList;
	
	
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public void setHeader(jwtToken header) {
		this.header = header;
	}
	public StatusMessage getStatus() {
		return status;
	}
	public void setStatus(StatusMessage status) {
		this.status = status;
	}
	public ErrorMessage getError() {
		return error;
	}
	public void setError(ErrorMessage error) {
		this.error = error;
	}
	public jwtToken getHeader() {
		return header;
	}
	
	public Config getConfig() {
		return config;
	}
	public void setConfig(Config config) {
		this.config = config;
	}
	public ResponseMessage(){}

	public Object getDownloadList() {
		return downloadList;
	}

	public void setDownloadList(Object downloadList) {
		this.downloadList = downloadList;
	}
}
