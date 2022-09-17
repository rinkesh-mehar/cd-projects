package com.drkrishi.usermanagement.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
import com.drkrishi.usermanagement.model.RolesModel;

@Repository
@Component
public class RoleDaoImpl implements RoleDao {

	private static final Logger LOGGER = LogManager.getLogger(RoleDaoImpl.class);

	@PersistenceContext
	private EntityManager em;

	@Transactional
	@Override
	public boolean saveRole(Roles drKrishiRole) {
		try {
			em.persist(drKrishiRole);
			em.close();
			return true;
		} catch (Exception e) {
			LOGGER.error("", e.fillInStackTrace());
			return false;
		}
	}

	@Transactional
	@Override
	public boolean saveUserRole(UserRoles drKrishiUserRole) {
		try {
			em.persist(drKrishiUserRole);
			em.close();
			return true;
		} catch (Exception e) {
			LOGGER.error("", e.fillInStackTrace());
			return false;
		}
	}

	@Override
	public int getUserRoleId(int userId) {

		UserRoles userrole = null;
		try {
//			Query query = em.createQuery(" from UserRoles user where user.userId= '" + userId + "' ");
			String hql = "from UserRoles user where user.userId= :userId";
			Query query = em.createQuery(hql).setParameter("userId", userId);
			
			if (query.getResultList().size() != 0) {
				userrole = (UserRoles) query.getResultList().get(0);
				return userrole.getRoleId();
			}
			return 0;
		} catch (Exception e) {
			LOGGER.error("", e.fillInStackTrace());
			return 0;
		}
	}

	@Override
	public UserRoles getUserRole(int userId) {

		UserRoles userrole = null;
		try {
//			Query query = em.createQuery("from UserRoles user where user.userId= '" + userId + "' ");

			String hql = "from UserRoles user where user.userId= :userId";
			Query query = em.createQuery(hql).setParameter("userId", userId);
			if (query.getResultList().size() != 0) {
				userrole = (UserRoles) query.getResultList().get(0);
				return userrole;
			}
			return userrole;
		} catch (Exception e) {
			LOGGER.error("", e.fillInStackTrace());
			return userrole;
		}
	}

	@Override
	public UserRoles getUserIdByRoleId(int roleId) {

		UserRoles userrole = null;
		try {
//			Query query = em.createQuery("from UserRoles user where user.roleId= '" + roleId + "' ");

			String hql = "from UserRoles user where user.roleId= :roleId";
			Query query = em.createQuery(hql).setParameter("roleId", roleId);
			
			if (query.getResultList().size() != 0) {
				userrole = (UserRoles) query.getResultList().get(0);
				return userrole;
			}
			return userrole;
		} catch (Exception e) {
			LOGGER.error("", e.fillInStackTrace());
			return userrole;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Roles> getAllRolesWithNoAdmin() {
		
		List<Roles> roles = new ArrayList<Roles>();
		try {
			Query query = em.createQuery(
					" from Roles role where role.roleFlag = 'YES' and role.roleName !='System Admin' and role.roleName != 'Joinee' ");
			return query.getResultList();

		} catch (Exception e) {
			LOGGER.error("", e.fillInStackTrace());
			return roles;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Roles> getRolesByEcosystemName(String ecosystem) {

		List<Roles> ecosystemRoles = new ArrayList<Roles>();
		try {
			Query query = em.createQuery(
					" from Roles role where role.ecosystem = '" + ecosystem + "' and role.roleFlag != 'YES' ");
			return query.getResultList();

		} catch (Exception e) {
			LOGGER.error("", e.fillInStackTrace());
			return ecosystemRoles;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<State> getStates() {

		List<State> assigingRoles = new ArrayList<State>();
		try {
			Query query = em.createQuery("from State WHERE status=1");
			return query.getResultList();

		} catch (Exception e) {
			LOGGER.error("", e.fillInStackTrace());
			return assigingRoles;
		}
	}

	@Override
	public List<Region> getRegion(int stateId) {

		List<Region> listOfRegions = new ArrayList<Region>();
		try {

			String hql = "from Region where state_id = :stateId";
			return em.createQuery(hql).setParameter("stateId", stateId).getResultList();

		} catch (Exception e) {
			LOGGER.error("", e.fillInStackTrace());
		}
		return listOfRegions;
	}

	@Override
	public String getStateName(int stateId) {

		try {

			String hql = "from State s where s.stateId = :stateId";
			List result = em.createQuery(hql).setParameter("stateId", stateId).getResultList();

			if (result.size() != 0) {
				State state = (State) result.get(0);
				return state.getStateName();
			}

		} catch (Exception e) {
			LOGGER.error("", e.fillInStackTrace());
		}
		return null;
	}

	@Override
	public String getRegionName(int regionId) {

		try {
//			Query query = em.createQuery("from Region where regionId = '" + regionId + "' ");
			
			String hql = "from Region where regionId = :regionId";
			Query query = em.createQuery(hql).setParameter("regionId", regionId);
			
			if (query.getResultList().size() != 0) {
				Region state = (Region) query.getResultList().get(0);
				return state.getRegionName();
			}

		} catch (Exception e) {
			LOGGER.error("", e.fillInStackTrace());
		}
		return null;
	}

	
	@Override
	public Region getRegionDetails(int regionId) {

		try {
			
			String hql = "from Region where regionId = :regionId";
			Query query = em.createQuery(hql).setParameter("regionId", regionId);
			
			if (query.getResultList().size() != 0) {
				return (Region) query.getResultList().get(0);
			}

		} catch (Exception e) {
			LOGGER.error("", e.fillInStackTrace());
		}
		return null;
	}
	
	
	@Override
	public String getRoleName(int roleId) {

		try {

//			Query query = em.createQuery("from Roles where id = '" + roleId + "' ");
			
			String hql = "from Roles where id = :roleId";
			Query query = em.createQuery(hql).setParameter("roleId", roleId);
			
			if (query.getResultList().size() != 0) {
				Roles role = (Roles) query.getResultList().get(0);
				return role.getName();
			}
			return "";
		} catch (Exception e) {
			LOGGER.error("", e.fillInStackTrace());
			return "";
		}
	}

	/*@Override
	public String getEcosystemName(int roleId) {

		try {
			Query query = em.createQuery(" from Roles where roleId ='" + roleId + "' ");
			if (query.getResultList().size() != 0) {
				Roles state = (Roles) query.getResultList().get(0);
				return String.valueOf(state.getEcosystemId());
			}
			return "";

		} catch (Exception e) {
			LOGGER.error("", e.fillInStackTrace());
			return "";
		}
	} */

	@Override
	public List<Region> getRegions() {
		List<Region> listOfRegions = new ArrayList<Region>();

		try {
			return em.createQuery(" from Region ").getResultList();
		} catch (Exception e) {
			LOGGER.error("", e.fillInStackTrace());
		}
		return listOfRegions;
	}

	/*@Override
	public List<Region> getTileIdFromRegions(int stateId, int regionId) {

		try {
			Query query = em.createQuery("from Region reg where reg.state_id = '" + stateId + "'   ");
			return query.getResultList();

		} catch (Exception e) {
			LOGGER.error("", e.fillInStackTrace());
			return null;
		}
	}*/

	@Override
	public List<Object> listOfRoles() {

		List<Object> list = new ArrayList<Object>();

		Set<String> ecosystem = new HashSet<String>();
		try {

			Query query = em.createQuery(" from Roles role where role.reportingTo != 0 ");

			if (query.getResultList().size() != 0) {

				List<Roles> roles = query.getResultList();

				roles.forEach(role -> {

					if (ecosystem.add(role.getName())) {

						RolesModel rolesModel = new RolesModel(role.getId(), role.getName());
						list.add(rolesModel);

					}
				});
			}

		} catch (Exception e) {
			LOGGER.error("", e.fillInStackTrace());
		}		
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Roles> getAllRoles() {

		List<Roles> roles = new ArrayList<Roles>();
		try {
			Query query = em.createQuery(" from Roles role ");
			return query.getResultList();

		} catch (Exception e) {
			LOGGER.error("", e.fillInStackTrace());
		}
		return roles;
	}

	@Override
	public Roles getRoleDetails(int roleId) {

		try {

			String hql = "from Roles role where role.id = :roleId";
			Query query = em.createQuery(hql).setParameter("roleId", roleId);
			
			return (query.getResultList().size() != 0) ? (Roles) query.getResultList().get(0) : null;
		} catch (Exception e) {
			LOGGER.error("", e.fillInStackTrace());
		}
		return null;
	}

	@Override
	public List<Integer> getListOfUserIdByRoleId(int roleId) {

		List<Integer> inte = new ArrayList<Integer>();

		try {
//			Query query = em.createQuery(" from UserRoles user where user.roleId = " + roleId + " ");

			String hql = "from UserRoles user where user.roleId = :roleId";
			Query query = em.createQuery(hql).setParameter("roleId", roleId);
			
			if (query.getResultList().size() != 0) {

				List<UserRoles> list = query.getResultList();

				list.forEach(user -> {
					inte.add(user.getUserId());
				});
			}

			return inte;

		} catch (Exception e) {
			LOGGER.error("", e.fillInStackTrace());
		}
		return inte;
	}

	@Override
	public Ecosystem getEcosystem(int ecoSystemId) {

		List<Integer> inte = new ArrayList<Integer>();

		try {
//			Query query = em.createQuery(" from Ecosystem eco where eco.id = " + ecoSystemId + " ");
//			return (query.getResultList().size() != 0) ? (Ecosystem) query.getResultList().get(0) : null;
			
			String hql = "from Ecosystem eco where eco.id = :ecoSystemId";
			Query query = em.createQuery(hql).setParameter("ecoSystemId", ecoSystemId);
			return (query.getResultList().size() != 0) ? (Ecosystem) query.getResultList().get(0) : null;
		} catch (Exception e) {
			LOGGER.error("", e.fillInStackTrace());
		}
		return null;
	}

	@Override
	public List<Roles> getRolesByCode(String roleCode) {

		try {
//			Query query = em.createQuery(" from Roles role where role.code = '" + roleCode + "' ");
//			return (query.getResultList().size() != 0) ? query.getResultList() : new ArrayList<Roles>();

			String hql = "from Roles role where role.code =  :roleCode";
			Query query = em.createQuery(hql).setParameter("roleCode", roleCode);
			return (query.getResultList().size() != 0) ? query.getResultList() : new ArrayList<Roles>();

		} catch (Exception e) {
			LOGGER.error("", e.fillInStackTrace());
		}
		return null;
	}

	@Override
	public List<RoleMenu> getRoleMenu(int roleId) {
		try {

//			Query query = em.createQuery(" from RoleMenu rolemenu where rolemenu.role_id = '" + roleId + "' ");
//			return (query.getResultList().size() != 0) ? query.getResultList() : new ArrayList<RoleMenu>();

			String hql = "from RoleMenu rolemenu where rolemenu.role_id = :roleId";
			Query query = em.createQuery(hql).setParameter("roleId", roleId);
			return (query.getResultList().size() != 0) ? query.getResultList() : new ArrayList<RoleMenu>();
			
		} catch (Exception e) {
			LOGGER.error("", e.fillInStackTrace());
		}
		return null;
	}

	@Override
	public AppMenu getAppMenu(int appMenuId) {

		try {

//			Query query = em.createQuery(" from AppMenu appmenu where appmenu.id = '" + appMenuId + "' ");
//			return (query.getResultList().size() != 0) ? (AppMenu) query.getResultList().get(0) : null;

			String hql = "from AppMenu appmenu where appmenu.id = :appMenuId";
			Query query = em.createQuery(hql).setParameter("appMenuId", appMenuId);
			return (query.getResultList().size() != 0) ? (AppMenu) query.getResultList().get(0) : null;			
			
		} catch (Exception e) {
			LOGGER.error("", e.fillInStackTrace());
		}
		return null;
	}

	/*
	 * @Override public RoleMenu getRoleMenuDetails(int roleId) { try {
	 * 
	 * Query query =
	 * em.createQuery(" from RoleMenu rolemenu where rolemenu.role_id = '" + roleId
	 * + "' "); return (query.getResultList().size() != 0) ? (RoleMenu)
	 * query.getResultList().get(0) : null;
	 * 
	 * } catch (Exception e) { LOGGER.error("", e.fillInStackTrace()); } return
	 * null; }
	 */

	@Override
	public List<TaskAllocation> getTaskAllocation(int userId) {
		try {

//			Query query = em.createQuery(" from TaskAllocation task  where task.assigneeId = '" + userId + "'");
//			return (query.getResultList().size() != 0) ? query.getResultList() : new ArrayList<TaskAllocation>();

			String hql = "from TaskAllocation task  where task.assigneeId = :userId";
			Query query = em.createQuery(hql).setParameter("userId", userId);
			return (query.getResultList().size() != 0) ? query.getResultList() : new ArrayList<TaskAllocation>();

		} catch (Exception e) {
			LOGGER.error("", e.fillInStackTrace());
		}

		return null;
	}

	@Override
	public List<PrVillageAssigment> getPrsAssignmentyId(int userId) {

		try {

			String hql = "from PrVillageAssigment task  where task.prsId = :userId";
			Query query = em.createQuery(hql).setParameter("userId", userId);

			return (query.getResultList().size() != 0) ? query.getResultList() : new ArrayList<PrVillageAssigment>();

		} catch (Exception e) {
			LOGGER.error("", e.fillInStackTrace());
		}

		return null;
	}

	@Override
	public VillageTask getPrsTask(int prs_assignmentId) {

		try {

			String hql = "from VillageTask task  where task.taskId = :assignedId";
			Query query = em.createQuery(hql).setParameter("assignedId", prs_assignmentId);
			return (query.getResultList().size() != 0) ? (VillageTask) query.getResultList().get(0) : null;

		} catch (Exception e) {
			LOGGER.error("", e.fillInStackTrace());
		}

		return null;
	}

	@Override
	public String findByReportingTo(int reporting_to) {
		try {
			//SELECT r.name FROM user_roles as ur INNER JOIN roles as r on ur.role_id = r.id WHERE ur.user_id = :userId"
//user_roles.user_id, roles.name FROM user_roles INNER JOIN roles ON user_roles.role_id=roles. '" + reporting_to + "' 
			Query query = em.createNativeQuery("SELECT r.name FROM roles as r INNER JOIN user_roles as ur on ur.role_id = r.id WHERE ur.user_id = :userId").setParameter("userId", reporting_to);
			if (query.getResultList().size() != 0) {
				String name = (String) query.getResultList().get(0);
				return name;
			}
			return "";
		} catch (Exception e) {
			LOGGER.error("", e.fillInStackTrace());
			return "";
		}
	}}
