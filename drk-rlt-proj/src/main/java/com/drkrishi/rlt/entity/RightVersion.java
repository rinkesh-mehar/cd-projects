package com.drkrishi.rlt.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="right_version")
public class RightVersion {
	
	@Id
	@Column(name="id")
	private Integer id; 
	
	@Column(name="right_id")
	private String rightId;
	
	@Column(name="latest_version_number")
	private Integer latestVersionNumber;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRightId() {
		return rightId;
	}

	public void setRightId(String rightId) {
		this.rightId = rightId;
	}

	public Integer getLatestVersionNumber() {
		return latestVersionNumber;
	}

	public void setLatestVersionNumber(Integer latestVersionNumber) {
		this.latestVersionNumber = latestVersionNumber;
	}
	
}
