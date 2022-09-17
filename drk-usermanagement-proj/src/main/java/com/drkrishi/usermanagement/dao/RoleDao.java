package com.drkrishi.usermanagement.dao;

import java.util.List;

import com.drkrishi.usermanagement.entity.AppMenu;
import com.drkrishi.usermanagement.entity.Ecosystem;
import com.drkrishi.usermanagement.entity.PrVillageAssigment;
import com.drkrishi.usermanagement.entity.Region;
import com.drkrishi.usermanagement.entity.RoleMenu;
import com.drkrishi.usermanagement.entity.Roles;
import com.drkrishi.usermanagement.entity.State;
import com.drkrishi.usermanagement.entity.TaskAllocation;
import com.drkrishi.usermanagement.entity.UserRoles;
import com.drkrishi.usermanagement.entity.VillageTask;

public interface RoleDao {

	public boolean saveRole(Roles drKrishiRole);
	
	public boolean saveUserRole(UserRoles drKrishiUserRole);
	
	public String getRoleName(int roleId);
	
//	public String getEcosystemName(int roleId);
	
//	public String getUserRoleName(int userId);
	
	public UserRoles getUserRole(int userId);
	
	public List<Roles> getAllRoles();
	
//	public Roles getJoineeRole();
	
	public List<Roles> getAllRolesWithNoAdmin();
	
	public List<State> getStates();
	
	public List<Region> getRegion(int stateId);
	
	public String getStateName(int stateId);
	
	public String getRegionName(int regionId);
	
	public List<Roles> getRolesByEcosystemName(String ecosystem);
	
	public List<Region> getRegions();
	
//	public List<Region> getTileIdFromRegions(int stateId, int regionId);
	
//	public boolean checkPrmHavingState(int stateId);
	
	public List<Object> listOfRoles();
	
	Roles getRoleDetails(int roleId);
	
	public List<Integer> getListOfUserIdByRoleId(int roleId);
	
	public UserRoles getUserIdByRoleId(int roleId);
	
	public Ecosystem getEcosystem(int ecoSystemId);
	
	public int getUserRoleId(int userId);
	
	List<Roles> getRolesByCode(String roleCode);
	
	public AppMenu getAppMenu(int appMenuId );
	
	public List<RoleMenu> getRoleMenu(int roleId );
	
//	public RoleMenu getRoleMenuDetails(int roleId);
	
	public List<TaskAllocation> getTaskAllocation( int userId ); 
	
	public List<PrVillageAssigment> getPrsAssignmentyId(int userId );
	
	public VillageTask getPrsTask(int prs_assignmentId);

	public String findByReportingTo(int reportingTo);
	
	public Region getRegionDetails(int regionId);
	
}