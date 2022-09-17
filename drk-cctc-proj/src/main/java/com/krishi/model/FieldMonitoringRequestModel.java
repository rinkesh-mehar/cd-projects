package com.krishi.model;

import java.util.ArrayList;

public class FieldMonitoringRequestModel {
	
	private Integer userid;
	
	private String taskid;
	
	private Integer callingstatus;
	
	private ArrayList<String> dateschedule;
	
	private String comments;
	
	private Boolean visitrequired;
	
	private String type;

	private Double harvestedQuantity;

	private Double deliverableQuantity;

	private String rightId;

	public String getRightId() {
		return rightId;
	}

	public void setRightId(String rightId) {
		this.rightId = rightId;
	}

	public Double getHarvestedQuantity() {
		return harvestedQuantity;
	}

	public void setHarvestedQuantity(Double harvestedQuantity) {
		this.harvestedQuantity = harvestedQuantity;
	}

	public Double getDeliverableQuantity() {
		return deliverableQuantity;
	}

	public void setDeliverableQuantity(Double deliverableQuantity) {
		this.deliverableQuantity = deliverableQuantity;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getTaskid() {
		return taskid;
	}

	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}

	public Integer getCallingstatus() {
		return callingstatus;
	}

	public void setCallingstatus(Integer callingstatus) {
		this.callingstatus = callingstatus;
	}

	public ArrayList<String> getDateschedule() {
		return dateschedule;
	}

	public void setDateschedule(ArrayList<String> dateschedule) {
		this.dateschedule = dateschedule;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Boolean getVisitrequired() {
		return visitrequired;
	}

	public void setVisitrequired(Boolean visitrequired) {
		this.visitrequired = visitrequired;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
