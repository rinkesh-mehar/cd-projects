package com.drkrishi.prm.model;

public class DrKrishiRole {

	private int roleId;
	
	private String ecosystem;

	private String roleName;
	
	private String roleDescription;
	
	private String roleFlag;
	
	public String getEcosystem() {
		return ecosystem;
	}

	public void setEcosystem(String ecosystem) {
		this.ecosystem = ecosystem;
	}

	public String getRoleFlag() {
		return roleFlag;
	}

	public void setRoleFlag(String roleFlag) {
		this.roleFlag = roleFlag;
	}

	
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}


	public String getRoleDescription() {
		return roleDescription;
	}

	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public DrKrishiRole() {}
	
	public DrKrishiRole(int roleId, String ecosystem, String roleName, String roleDescription, String roleFlag) {
		this.roleId = roleId;
		this.ecosystem = ecosystem;
		this.roleName = roleName;
		this.roleDescription = roleDescription;
		this.roleFlag = roleFlag;
	}
	
	
}

