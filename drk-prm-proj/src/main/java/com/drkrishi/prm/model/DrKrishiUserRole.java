package com.drkrishi.prm.model;


import javax.persistence.Column;


public class DrKrishiUserRole{
	
	private int userRoleId;
	
	private int userId;
	
	private int roleId;

	private int stateId;
	
	public int getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(int userRoleId) {
		this.userRoleId = userRoleId;
	}

	public DrKrishiUserRole() {}
	
	public DrKrishiUserRole(int userRoleId, int userId, int roleId, int stateId, String roleName) {
		this.userRoleId = userRoleId;
		this.userId = userId;
		this.roleId = roleId;
		this.stateId = stateId;
		this.roleName = roleName;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getStateId() {
		return stateId;
	}

	public void setStateId(int stateId) {
		this.stateId = stateId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Column(name="roleName")
	private String roleName;
	
	
}
