package com.krishi.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author CDT - Pranay
 */

@Entity
@Table(name = "calling_status")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CallingStatus implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@JsonProperty("ID")
	private Integer callingStatusId;

	@Column(length = 45)
	@JsonProperty("CallingStatus")
	private String name;

	@Column(name = "status")
	@JsonProperty("Status")
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((callingStatusId == null) ? 0 : callingStatusId.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CallingStatus other = (CallingStatus) obj;
		if (callingStatusId == null) {
			if (other.callingStatusId != null)
				return false;
		} else if (!callingStatusId.equals(other.callingStatusId))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}

}
