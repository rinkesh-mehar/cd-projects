package com.drkrishi.usermanagement.model;



public class RolesModel {

	public int roleId;
	public String roleName;
	
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	public RolesModel(int roleId, String roleName) {
		this.roleId = roleId;
		this.roleName = roleName;
	}
	
}
