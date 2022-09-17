package com.drkrishi.rlt.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.drkrishi.rlt.entity.Users;

@Repository
public class ManageHardwareDaoImpl implements ManageHardwareDao {
	
	@PersistenceContext
	private EntityManager em;

	
	@Override
	@Transactional
	public Users getUserById(Integer userId) {
		Query query = em.createQuery("select u from Users u where u.id = :id", Users.class);
		query.setParameter("id", userId);
		return (Users) query.getSingleResult();
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> getAssignedHardwardList(Integer regionId, String searchKeyward) {
		Query query;
		if(searchKeyward != null) {
			String hqlQuery = "select ha.id, ha.boxBarcode, ha.vanBarcode, ha.assignedDate,"
					+ " CONCAT(u.firstName, ' ', COALESCE(u.middleName, ''), ' ',u.lastName), u.mobileNumber, r.name from HardwareAllocation ha"
					+ " inner join Users u on ha.userId = u.id inner join UserRoles ur on u.id = ur.userId inner join Roles r on ur.roleId = r.id"
					+ " where ha.receivedDate = null and u.regionId = :regionId and (u.mobileNumber = :searchKeyward or"
					+ " CONCAT(u.firstName, ' ', COALESCE(u.middleName, ''), ' ',u.lastName) like CONCAT('%',:searchKeyward,'%') "
					+ " or CONCAT(u.firstName,' ',u.lastName) like CONCAT('%',:searchKeyward,'%'))";
			query = em.createQuery(hqlQuery).setParameter("searchKeyward", searchKeyward)
					.setParameter("regionId", regionId);
		} else {
			String hqlQuery = "select ha.id, ha.boxBarcode, ha.vanBarcode, ha.assignedDate,"
					+ " CONCAT(u.firstName, ' ', COALESCE(u.middleName, ''), ' ',u.lastName), u.mobileNumber, r.name from HardwareAllocation ha"
					+ " inner join Users u on ha.userId = u.id inner join UserRoles ur on u.id = ur.userId inner join Roles r on ur.roleId = r.id"
					+ " where ha.receivedDate = null and u.regionId = :regionId";
			query = em.createQuery(hqlQuery).setParameter("regionId", regionId);
		}
		return query.getResultList();
	}


	@Override
	@Transactional
	public int unTagHardware(Integer id, Integer userId) {
		Query query = em.createQuery("update HardwareAllocation set receivedDate = CURRENT_DATE, receivedBy = :userId where id = :id");
		query.setParameter("userId", userId)
		.setParameter("id", id);
		return query.executeUpdate();
	}

}
