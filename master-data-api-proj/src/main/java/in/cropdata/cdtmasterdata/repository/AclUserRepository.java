package in.cropdata.cdtmasterdata.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.cdtmasterdata.acl.dto.Menu;
import in.cropdata.cdtmasterdata.acl.model.AclUser;
import in.cropdata.cdtmasterdata.dto.interfaces.User;

public interface AclUserRepository extends JpaRepository<AclUser, Integer> {

//    Optional<User> findByEmail(String username);

	@Query(value = "SELECT u.ID,u.RoleID,u.Name,u.Email,u.Password,u.Status,u.CreatedAt,u.UpdatedAt,r.Name as Role FROM acl_users u \n"
			+ "	INNER JOIN acl_roles r ON r.ID = u.RoleID where u.Email = ?1", nativeQuery = true)
	User getUserByEmail(String email);

//    @Query(value = "SELECT name FROM acl_roles WHERE id = ?1",nativeQuery = true)
//    String getRoleByRoleId(int roleId);

	@Query(value = "SELECT u.ID,u.RoleID,u.Name,u.Email,u.Password,u.Status,u.CreatedAt,u.UpdatedAt,r.Name as Role FROM acl_users u \n"
			+ "	INNER JOIN acl_roles r ON r.ID = u.RoleID", nativeQuery = true)
	List<User> getAllUserData();

	@Query(value = "SELECT u.ID,u.RoleID,u.Name,u.Email,u.Password,u.Status,u.CreatedAt,u.UpdatedAt,r.Name as Role FROM acl_users u \n"
			+ "	INNER JOIN acl_roles r ON r.ID = u.RoleID where u.ID = ?1", nativeQuery = true)
	User getUserById(int id);

	@Query(value = "SELECT  " + "    res.ResourceURL, " + "    res.ResourceName, " + "    resg.ResourceGroupName, "
			+ "    rtcn.ID AS RestrictedResourceID " + "FROM " + "    acl_resource AS res " + "        INNER JOIN "
			+ "    acl_resource_groups AS resg ON resg.ID = res.ResourceGroupID " + "        LEFT JOIN "
			+ "    acl_restrictions AS rtcn ON (rtcn.ResourceID = res.ID "
			+ "        OR rtcn.ResourceGroupID = resg.ID " + "        OR rtcn.SubResourceID = res.ID) "
			+ "        AND (rtcn.RoleID = ?1)\n" + "WHERE " + "    res.ResourceGroupID IS NOT NULL and res.Status = 'Active'"
			+ "HAVING RestrictedResourceID IS NULL " + "ORDER BY resg.ResourceGroupName ASC", nativeQuery = true)
	List<Menu> getMenusByRole(int roleID);
	
	@Query(value = "SELECT    res.ResourceURL,     res.ResourceName,    resg.ResourceGroupName, \n" + 
			"		    rtcn.ID AS RestrictedResourceID FROM    acl_resource AS res         INNER JOIN \n" + 
			"		    acl_resource_groups AS resg ON resg.ID = res.ResourceGroupID         LEFT JOIN\n" + 
			"		    acl_restrictions AS rtcn ON (rtcn.ResourceID = res.ID \n" + 
			"		        OR rtcn.ResourceGroupID = resg.ID        OR rtcn.SubResourceID = res.ID) \n" + 
			"		        AND (rtcn.RoleID = :roleId) WHERE    res.ResourceGroupID IS NOT NULL and res.Status = 'Active'\n" + 
			"                AND (res.ResourceName like :searchText OR resg.ResourceGroupName like :searchText)\n" + 
			"		HAVING RestrictedResourceID IS NULL ORDER BY resg.ResourceGroupName ASC", nativeQuery = true)
	List<Menu> getMenusByRoleAndSearchText(Integer roleId, String searchText);

}
