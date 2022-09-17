package com.krishi.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author CDT - Pranay
 */

@Entity
@Table(name = "calling_status")
public class CallingStatus implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	private Integer callingStatusId;

	@Column(length = 45)
	private String name;

	@Column(name = "status")
	private Integer status;

	public CallingStatus() {
		super();
	}

	public CallingStatus(Integer callingStatusId, String name, Integer status) {
		super();
		this.callingStatusId = callingStatusId;
		this.name = name;
		this.status = status;
	}

	public Integer getCallingStatusId() {
		return callingStatusId;
	}

	public void setCallingStatusId(Integer callingStatusId) {
		this.callingStatusId = callingStatusId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setStatus(String status) {
		this.status = (status != null && status.equalsIgnoreCase("Active")) ? 1 : 0;
	}

	@Override
	public String toString() {
		return "CallingStatus [callingStatusId=" + callingStatusId + ", name=" + name + ", status=" + status + "]";
	}

}
