package in.cropdata.cdtmasterdata.acl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.cdtmasterdata.acl.model.AclRestriction;
import in.cropdata.cdtmasterdata.dto.interfaces.Restriction;

public interface AclRestrictionsRepository extends JpaRepository<AclRestriction, Integer> {

	@Query(value = "SELECT ResourceURL FROM acl_resource WHERE ID=?1", nativeQuery = true)
	String getResourceURIByResourceId(int resourceId);

	@Query(value = "SELECT Name FROM acl_groups WHERE ID=?1", nativeQuery = true)
	String getGroupNameByGroupId(int groupId);

	List<AclRestriction> findAllByRoleId(int roleId);

	@Query(value = "SELECT acl_restrictions.ID,acl_restrictions.RoleID,acl_restrictions.ResourceID,acl_restrictions.GroupID,\n"
			+ "acl_roles.Name as RoleName,acl_resource.ResourceName,acl_resource.ResourceURL,\n"
			+ "acl_groups.Name as GroupName\n" + "FROM acl_restrictions\n"
			+ "JOIN acl_roles ON (acl_roles.ID = acl_restrictions.RoleID)\n"
			+ "JOIN acl_resource ON (acl_resource.ID = acl_restrictions.ResourceID)\n"
			+ "JOIN acl_groups ON (acl_groups.ID = acl_restrictions.GroupID)", nativeQuery = true)
	List<Restriction> getAllData();

	@Query(value = "SELECT acl_restrictions.ID,acl_restrictions.RoleID,acl_restrictions.ResourceID,acl_restrictions.GroupID, "
			+ "acl_roles.Name as RoleName,acl_resource.ResourceName,acl_resource.ResourceURL, "
			+ "acl_groups.Name as GroupName,resgrp.ResourceGroupName,res.ResourceName as SubResources,res.ResourceURL as SubResourceURL "
			+ ",acl_restrictions.ResourceGroupID,acl_restrictions.SubResourceID  FROM acl_restrictions "
			+ "LEFT JOIN acl_roles ON (acl_roles.ID = acl_restrictions.RoleID) "
			+ "LEFT JOIN acl_resource ON (acl_resource.ID = acl_restrictions.ResourceID) "
			+ "LEFT JOIN acl_groups ON (acl_groups.ID = acl_restrictions.GroupID) "
			+ "left join acl_resource_groups resgrp on resgrp.ID = acl_restrictions.ResourceGroupID "
			+ "left join acl_resource res on res.ID = acl_restrictions.SubResourceID "
			+ "where acl_restrictions.RoleID = ?1", nativeQuery = true)
	List<Restriction> getAllDataByRoleId(int roleId);
}
