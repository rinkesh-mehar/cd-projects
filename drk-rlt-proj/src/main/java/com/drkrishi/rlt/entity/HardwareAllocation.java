package com.drkrishi.rlt.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="hardware_allocation")
public class HardwareAllocation {
	
	@Id
	private Integer id;
	
	private Integer userId;
	
	private String boxBarcode;
	
	private String vanBarcode;
	
	private Date assignedDate;
	
	private Date receivedDate;
	
	private Integer receivedBy;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getBoxBarcode() {
		return boxBarcode;
	}

	public void setBoxBarcode(String boxBarcode) {
		this.boxBarcode = boxBarcode;
	}

	public String getVanBarcode() {
		return vanBarcode;
	}

	public void setVanBarcode(String vanBarcode) {
		this.vanBarcode = vanBarcode;
	}

	public Date getAssignedDate() {
		return assignedDate;
	}

	public void setAssignedDate(Date assignedDate) {
		this.assignedDate = assignedDate;
	}

	public Date getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}

	public Integer getReceivedBy() {
		return receivedBy;
	}

	public void setReceivedBy(Integer receivedBy) {
		this.receivedBy = receivedBy;
	}

	
}
