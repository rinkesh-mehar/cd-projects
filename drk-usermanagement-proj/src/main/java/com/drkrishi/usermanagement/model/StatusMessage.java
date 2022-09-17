package com.drkrishi.usermanagement.model;

public class StatusMessage {

	private String id;
	private String detailMessage;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDetailMessage() {
		return detailMessage;
	}
	public void setDetailMessage(String detailMessage) {
		this.detailMessage = detailMessage;
	}
	
	public StatusMessage(String id, String detailMessage) {
		this.id = id;
		this.detailMessage = detailMessage;
	}
	
}
