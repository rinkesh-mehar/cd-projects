package com.krishi.entity;

import java.io.Serializable;

public class RightsId implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String id;
	
	private Integer versionNumber;

	public RightsId() {
		super();
	}

	public RightsId(String id, Integer versionNumber) {
		super();
		this.id = id;
		this.versionNumber = versionNumber;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(Integer versionNumber) {
		this.versionNumber = versionNumber;
	}
	
	

}
