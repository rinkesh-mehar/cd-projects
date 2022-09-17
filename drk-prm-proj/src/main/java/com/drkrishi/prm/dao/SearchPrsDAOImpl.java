package com.drkrishi.prm.dao;

import com.drkrishi.prm.entity.DRKrishiUserEntity;
import com.drkrishi.prm.newentity.NEW_Village_AssigmentEntity;
import com.drkrishi.prm.newentity.NEW_VillagetaskEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;


@Repository
@Component
public class SearchPrsDAOImpl implements SearchPrsDAO{

	
	@PersistenceContext
	private EntityManager em;
	
	// get prs user list based on state id, region id, prs name
	@Override
	public List<DRKrishiUserEntity> getPrsListByName(int stateId, int regionId, String prsname, Integer userId) {
		
		System.out.println(" PRS DAO....");
		
		List<DRKrishiUserEntity> list = new ArrayList<DRKrishiUserEntity>();
		
		try {
			String hql ="";
			Query query = null;
			System.out.println("prsname "+prsname);
			if(prsname != null && !prsname.replace(" ", "").equals("")) {
				hql = "from DRKrishiUserEntity where regionId = :regionId and stateId = :stateId and reportingTo = :reportingTo and "
						+ " (CONCAT(firstName,' ', middleName ,' ', lastName) like :prsname or CONCAT(firstName,' ', lastName) like :prsname)";
				query = em.createQuery(hql).
						setParameter("regionId", regionId).
						setParameter("stateId", stateId).
						setParameter("prsname", "%"+prsname+"%").
						setParameter("reportingTo", userId);
				
			}else {
				hql = "from DRKrishiUserEntity where regionId = :regionId and stateId = :stateId  and reportingTo = :reportingTo";
				query = em.createQuery(hql).
						setParameter("regionId", regionId).
						setParameter("stateId", stateId).
						setParameter("reportingTo", userId);
				
			}
					/*setParameter("firstNamesearch", prsname+"%").
					setParameter("middleNamesearch", prsname+"%").
					setParameter("lastNamesearch", prsname+"%");*/

			if (query.getResultList().size() != 0) {
				return query.getResultList();				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	// get prs task details based on prs id, week and year
	@Override
	public List<NEW_Village_AssigmentEntity> getPRS_AssigmentByPRSId(int prsId, int weekvalue, int yearvalue) {

		List<NEW_Village_AssigmentEntity> list = new ArrayList<NEW_Village_AssigmentEntity>();
		
		try {  
			String hql = "from NEW_Village_AssigmentEntity where drkrishiUser2.id = :prsId and weekNumber=: weekNumber and year = :year and status = 1";			
			Query query = em.createQuery(hql).setParameter("prsId", prsId).setParameter("weekNumber", weekvalue).setParameter("year", yearvalue);

			if (query.getResultList().size() != 0) {
				return query.getResultList();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	// get prs task based on prs id
	@Override
	public List<NEW_VillagetaskEntity> getPRS_VillageTaskEntity(int assignmentId) {

		List<NEW_VillagetaskEntity> list = new ArrayList<NEW_VillagetaskEntity>();
		
		try {  
			String hql = "from NEW_VillagetaskEntity where prVillageAssigment.assigment_Id = :assigment_Id and status = 1 ";			
			Query query = em.createQuery(hql).setParameter("assigment_Id", assignmentId);

			if (query.getResultList().size() != 0) {
				return query.getResultList();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

	
	@Override
	public List<DRKrishiUserEntity> getScoutNamesList(int userId,int stateId, int regionId) {

		List<DRKrishiUserEntity> entity = null;
		try {

			// String hql = "from DRKrishiUserEntity where regionId = :regionId and stateId
			// = :stateId";
			// String hql="select dr.firstName,dr.middleName,dr.lastName from
			// DRKrishiUserEntity dr join DrKrishiUserRoleEntity ur on dr.id=ur.userId where
			// dr.stateId = :stateId and dr.regionId = :regionId and ur.roleId=12";
			String hql = "select dr from DRKrishiUserEntity dr join DrKrishiUserRoleEntity ur on dr.id=ur.userId where dr.stateId = :stateId and dr.regionId = :regionId and dr.status=1 and dr.reportingTo = :userId and ur.roleId=12";
			Query query = em.createQuery(hql).setParameter("regionId", regionId).setParameter("stateId", stateId).setParameter("userId", userId);
			if (query.getResultList().size() != 0) {
				entity = query.getResultList();

			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		return entity;

	}

}
