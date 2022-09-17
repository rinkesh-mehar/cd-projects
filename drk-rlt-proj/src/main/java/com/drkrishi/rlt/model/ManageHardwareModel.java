package com.drkrishi.rlt.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ManageHardwareModel {
	
	private Integer id;
	
	private String name;
	
	private String mobilenumber;
	
	private String role;
	
	private String vanid;
	
	private String boxid;
	
	private String date;
	
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Integer userId;
	
	public ManageHardwareModel() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobilenumber() {
		return mobilenumber;
	}

	public void setMobilenumber(String mobilenumber) {
		this.mobilenumber = mobilenumber;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getVanid() {
		return vanid;
	}

	public void setVanid(String vanid) {
		this.vanid = vanid;
	}

	public String getBoxid() {
		return boxid;
	}

	public void setBoxid(String boxid) {
		this.boxid = boxid;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	


}
