package com.drkrishi.iqa.dao;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import com.drkrishi.iqa.entity.*;
import org.springframework.stereotype.Repository;

@Repository
public class KmlqaDaoImpl implements KmlqaDao{

	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StaticData> getStaticDataByKey(List<String> keys) {
			String hqlQuery = "from StaticData where dataKey in (:dataKey)";
			Query query = em.createQuery(hqlQuery,StaticData.class);
			query.setParameter("dataKey", keys);
			return  query.getResultList();
	}
	
	@Override
	public StaticData getStaticData() {
		String hqlQuery = "from StaticData where id = 1";
		Query query = em.createQuery(hqlQuery, StaticData.class);
		return (StaticData) query.getResultList().get(0);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> checkTaskAssigned(int kmlqaId){
		String hqlQuery = "select count(id) from Task"
	    		+ " where taskTypeId = 13 and assigneeId = :assigneeId and status = 1 and taskDate = :taskDate";
	    Query query = em.createQuery(hqlQuery)
	    		.setParameter("taskDate", new Date(System.currentTimeMillis()))
	    		.setParameter("assigneeId", kmlqaId);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getKmlqaTaskCount() {
		
		String hqlQuery = "select count(id) from Task"
	    		+ " where taskTypeId = 13 and assigneeId = 0 and status = 0";
	    Query query = em.createQuery(hqlQuery);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getKmlUserCount() {
		
		String hqlQuery = "select count(u.id) from Users as u inner join UserRoles as ur ON ur.userId = u.id"
	    		+ " where ur.roleId = 6";
	    Query query = em.createQuery(hqlQuery);
		return query.getResultList();
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<ViewKmlqaTask> getAgriotaCust() {
		String hqlQuery = " from ViewKmlqaTask"
	    		+ " where taskTypeId = 13 and isAgriotaCust = 1 and assigneeId = 0 and status = 0";
		
	    Query query = em.createQuery(hqlQuery);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ViewKmlqaTask> getDrkCust() {
		String hqlQuery = " from ViewKmlqaTask"
	    		+ " where taskTypeId = 13 and isAgriotaCust = 0 and isDrkCust = 1 and assigneeId = 0 and status = 0";
		
	    Query query = em.createQuery(hqlQuery);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ViewKmlqaTask> getWillingnessForCdt() {
		String hqlQuery = " from ViewKmlqaTask"
	    		+ " where taskTypeId = 13 and isAgriotaCust = 0 and isDrkCust = 0 and willingnessForCdt = 1 "
	    		+ " and assigneeId = 0 and status = 0";
		
	    Query query = em.createQuery(hqlQuery);
		return query.getResultList();
	}

	@Transactional
	@Override
	public int assignTask(List<String> taskIds, int kmlqaId){
		String hqlQuery = "update Task set "
				+ " taskDate = :taskDate, taskTime = :taskTime, assigneeId = :assigneeId, status = 1"
	    		+ " where id in (:taskIds) and assigneeId = 0 and status = 0";
		
	    Query query = em.createQuery(hqlQuery)
	    		.setParameter("taskDate", new Date(System.currentTimeMillis()))
	    		.setParameter("taskTime", new Time(System.currentTimeMillis()))
	    		.setParameter("assigneeId", kmlqaId)
	    		.setParameter("taskIds", taskIds);
		return query.executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ViewKmlqaTask> getAssignedTask(int kmlqaId) {
		String hqlQuery = " from ViewKmlqaTask where assigneeId = :assigneeId and status = 1";
	    Query query = em.createQuery(hqlQuery)
	    		.setParameter("assigneeId", kmlqaId);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ViewKmlqaTask> getAllTask() {
		/*String hqlQuery = " from ViewKmlqaTask where assigneeId = 0 and status = 0 and taskTypeId = 13 ";*/
		String hqlQuery = " from ViewKmlqaTask where taskTypeId = 6 and kmlIsVerified = 0";
	    Query query = em.createQuery(hqlQuery);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getFileDetails(String taskId){
		
		String hqlQuery = " select ck.kmlFilePath"
				+ " from Task as t"
				+ " inner join CaseKml as ck on t.entityId = ck.farmCaseId"
				+ " where t.id = :taskId";
	    Query query = em.createQuery(hqlQuery)
	    		.setParameter("taskId", taskId);
	    
	    
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ViewCropInformation> getCropDetails(String taskId){
		
		String hqlQuery = " select vci from ViewCropInformation as vci"
				+ " where vci.taskId = :taskId";
	    Query query = em.createQuery(hqlQuery,ViewCropInformation.class)
	    		.setParameter("taskId", taskId);
		return query.getResultList();
	}

	@Override
	public Task getTaskById(String taskId) {
		String hqlQuery = " select t from Task as t"
				+ " where t.id = :taskId and t.taskTypeId=6";
	    Query query = em.createQuery(hqlQuery,Task.class)
	    		.setParameter("taskId", taskId);
	    Object task =query.getResultList().get(0);
		return  (Task) task;
	}

	@Override
	@Transactional
	public boolean updateCaseCropArea(String caseId, Double area) {
		String nativeQuery = "Update case_crop_info set crop_area=? where case_id =?";
		Query query =em.createNativeQuery(nativeQuery);
		query.setParameter(1, area);
		query.setParameter(2, caseId);
		int result = query.executeUpdate();
		em.clear();
		em.close();
		if(result == 1) {
			return true;
			
		}else {
			return false;	
		}
		
	}
	
	@Override
	@Transactional
	public void submitDetails(Task task, TaskHistory taskHistory, SubTask subTask, boolean isVerified) {

		if (isVerified) {
			task.setTaskTypeId(8);
		}
		subTask.setKmlIsVerified(1);
		em.persist(subTask);

//		task.setStatus(2);
		task.setEndTime(new Time(System.currentTimeMillis()));
		em.persist(task);

		taskHistory.setStatus(2);
		taskHistory.setComment("Approved");
		em.persist(taskHistory);
		/** update case kml url - CDT-Ujwal - Start */
		String hqlQuery = "update CaseKml set "
				+ " isVerified = 1"
	    		+ " where farmCaseId = :caseId";
		
	    Query query = em.createQuery(hqlQuery)
	    		.setParameter("caseId", task.getEntityId());
	    /** update case kml url - CDT-Ujwal - End */
	    query.executeUpdate();
	}

	@Override
	@Transactional
	public void updateKmlUrl(String taskId, String kmlUrl){
		String hqlQuery = "update CaseKml set "
				+ " kmlFilePath = :kmlUrl, isVerified = 1"
				+ " where farmCaseId = :caseId";

		Query query = em.createQuery(hqlQuery)
				.setParameter("kmlUrl", kmlUrl)
				.setParameter("caseId", taskId);
		/** update case kml url - CDT-Ujwal - End */
		query.executeUpdate();
	}
}
