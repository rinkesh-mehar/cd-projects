package com.drkrishi.rlt.dao;

import java.sql.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import com.drkrishi.rlt.entity.ViewBarcodeDetails;
import org.springframework.stereotype.Repository;

import com.drkrishi.rlt.entity.Task;
import com.drkrishi.rlt.entity.TaskHistory;
import com.drkrishi.rlt.entity.Users;

@Repository
public class BarcodeDaoImpl implements BarcodeDao{

	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	public ViewBarcodeDetails getBarcodeDetails(String barcodeNumber) {
		
//		String hqlQuery = "select t.barcode as barcodeNumber, t.caseId as caseId, vi.name as village, "
//				+ " f.primaryMobNumber as formerMobile, c.name as crop, t.status as status, t.taskTypeId as taskTypeId,"
//				+ " t.taskDate as taskDate, t.assigneeId as assigneeId, t.id as id " + 	    		
//
//				"from Task as t inner join CaseCropInfo as cci on t.caseId = cci.caseId " + 
//	    		"inner join Variety as v on cci.varietyId = v.id " + 
//	    		"inner join Commodity as c on v.commodityId = c.id " + 
////	    		"inner join Stress as s on c.id = s.commodityId " +
//	    		"inner join FarmCase as fc on fc.id = t.caseId " + 
//	    		"inner join FarmerFarm as ff on fc.farmId = ff.id " + 
//	    		"inner join Farmer as f on ff.farmerId = f.farmerId " + 
//	    		"inner join Village as vi on f.villageId = vi.id " + 

		String hqlQuery = "from ViewBarcodeDetails "
				+ " where taskTypeId = 6 and barcode = :barcodeNumber ";

		Query query = em.createQuery(hqlQuery, ViewBarcodeDetails.class).setParameter("barcodeNumber", barcodeNumber);
		em.close();
		return (ViewBarcodeDetails) query.getResultList().get(0);
	}
	
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<Object>  getStressDesciption(int stressId) {
//		
//		StringBuffer sb = new StringBuffer("select ");
//	    sb.append(" distinct ss.symptomDesc from StressSymptoms as ss where ss.stressId = :stressId");
//	    sb.toString();
//	    Query query = em.createQuery(sb.toString()).setParameter("stressId", stressId);
//		return query.getResultList();
//	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ViewBarcodeDetails>  getBarcodeSampleDetails(int rlt) {
		
//		String hqlQuery = "select t.barcode as barcodeNumber, t.caseId as caseId, vi.name as village, "
//				+ " f.primaryMobNumber as formerMobile, c.name as crop, t.status as status, t.taskTypeId as taskTypeId,"
//				+ " t.taskDate as taskDate, t.assigneeId as assigneeId, t.id as id " + 	   
//				
//				"from Task as t inner join CaseCropInfo as cci on t.caseId = cci.caseId " + 
//	    		"inner join Variety as v on cci.varietyId = v.id " + 
//	    		"inner join Commodity as c on v.commodityId = c.id " + 
//	    		"inner join FarmCase as fc on fc.id = t.caseId " + 
//	    		"inner join FarmerFarm as ff on fc.farmId = ff.id " + 
//	    		"inner join Farmer as f on ff.farmerId = f.farmerId " + 
//	    		"inner join Village as vi on f.villageId = vi.id " + 
		String hqlQuery = "from ViewBarcodeDetails"
				+ " where assigneeId = :rlt and taskTypeId = 6 ";

		Query query = em.createQuery(hqlQuery, ViewBarcodeDetails.class)
	    		.setParameter("rlt", rlt);
	    em.close();		
		return query.getResultList();
	}

	@Transactional
	@Override
	public int saveBarcodeDetails(String taskId, int rltId, Date date) {
		try {
			Query query = em.createQuery("update Task as t set t.taskDate = :date, t.status = 1, t.assigneeId = :rltId where t.id = :taskId");
					query.setParameter("date", date);
					query.setParameter("rltId", rltId);
					query.setParameter("taskId", taskId);
			em.close();
			return query.executeUpdate();
		}
		finally {
		
		}
	}

	@Override
	public Users getUsersDetails(Integer userId) {
		Query query = em.createQuery("from Users where id = :id", Users.class);
			query.setParameter("id", userId);
		em.close();
		return (Users) query.getResultList().get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> findRlmByRltId(int rltId) {
		String hqlQuery = "select u.id from Users as u inner join UserRoles as ur on u.id = ur.userId "
				+ "inner join Roles as r on ur.roleId = r.id where u.regionId = "
				+ "(select rg.id from Users as u inner join Region as rg on u.regionId = rg.regionId where u.id = :rltId) "
				+ "and r.code = 'RLM' ";
		
		Query query = em.createQuery(hqlQuery).setParameter("rltId", rltId);
		em.close();
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Task> getTaskReceivedDate(String id) {
		Query query = em.createQuery(" from Task where id = :id and taskTypeId = 6 order by taskDate",Task.class)
				.setParameter("id", id);
		em.close();
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Task> getTask(String taskId) {
		Query query = em.createQuery(" from Task where id = :taskId",Task.class)
				.setParameter("taskId", taskId);
		em.close();
		return query.getResultList();
	}
	
	@Transactional
	@Override
	public void setNeedMoreSample(Task task) {
		em.persist(task);
		em.close();
	}
	
	@Transactional
	@Override
	public void saveTaskHistory(TaskHistory taskHistory) {
		em.persist(taskHistory);
		em.close();
	}
}
