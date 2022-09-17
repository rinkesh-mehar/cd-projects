package com.drkrishi.iqa.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import com.drkrishi.iqa.entity.*;
import org.springframework.stereotype.Repository;

import com.drkrishi.iqa.model.KycDetailsModel;
import com.drkrishi.iqa.model.ResponseMessage;

@Repository
public class KycqaDaoImpl implements KycqaDao {

	@PersistenceContext
	private EntityManager em;

//	@Override
//	public StaticData getStaticData() {
//		String hqlQuery = "from StaticData where id = 1";
//		Query query = em.createQuery(hqlQuery, StaticData.class);
//		return (StaticData) query.getResultList().get(0);
//	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StaticData> getStaticData(List<String> keys) {
		String hql = "select s from StaticData s where s.dataKey in (:key)";
		Query query = em.createQuery(hql, StaticData.class);
		query.setParameter("key", keys);
		return  query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> checkTaskAssigned(int qaId) {
		String hqlQuery = "select count(id) from Task"
				+ " where taskTypeId = 12 and assigneeId = :assigneeId and status = 1 and taskDate = :taskDate";
		Query query = em.createQuery(hqlQuery).setParameter("taskDate", new Date(System.currentTimeMillis()))
				.setParameter("assigneeId", qaId);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getQaTaskCount() {

		String hqlQuery = "select count(id) from Task" + " where taskTypeId = 12 and assigneeId = 0 and status = 0";
		Query query = em.createQuery(hqlQuery);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getQaUserCount() {

		String hqlQuery = "select count(u.id) from Users as u inner join UserRoles as ur ON ur.userId = u.id"
				+ " where ur.roleId = 23";
		Query query = em.createQuery(hqlQuery);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ViewKycqaTask> getAgriotaCust() {
		String hqlQuery = " from ViewKycqaTask"
				+ " where taskTypeId = 12 and isAgriotaCust = 1 and assigneeId = 0 and status = 0";

		Query query = em.createQuery(hqlQuery);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ViewKycqaTask> getDrkCust() {
		String hqlQuery = " from ViewKycqaTask"
				+ " where taskTypeId = 12 and isAgriotaCust = 0 and isDrkCust = 1 and assigneeId = 0 and status = 0";

		Query query = em.createQuery(hqlQuery);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ViewKycqaTask> getWillingnessForCdt() {
		String hqlQuery = " from ViewKycqaTask"
				+ " where taskTypeId = 12 and isAgriotaCust = 0 and isDrkCust = 0 and willingnessForCdt = 1 "
				+ " and assigneeId = 0 and status = 0";

		Query query = em.createQuery(hqlQuery);
		return query.getResultList();
	}

	@Transactional
	@Override
	public int assignTask(List<String> taskIds, int qaId) {
		String hqlQuery = "update Task set "
				+ " taskDate = :taskDate, taskTime = :taskTime, assigneeId = :assigneeId, status = 1"
				+ " where id in (:taskIds) and assigneeId = 0 and status = 0";

		Query query = em.createQuery(hqlQuery).setParameter("taskDate", new Date(System.currentTimeMillis()))
				.setParameter("taskTime", new Time(System.currentTimeMillis())).setParameter("assigneeId", qaId)
				.setParameter("taskIds", taskIds);
		return query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ViewKycqaTask> getAssignedTask(int kmlqaId) {
		String hqlQuery = " from ViewKycqaTask where assigneeId = :assigneeId and status = 1";
		Query query = em.createQuery(hqlQuery).setParameter("assigneeId", kmlqaId);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ViewKycqaTask> getAllKycTask() {
		/*String hqlQuery = " from ViewKycqaTask where assigneeId = 0 and status = 0 and taskTypeId = 12 ";*/
		String hqlQuery = " from ViewKycqaTask where taskTypeId = 6 And kycIsVerified = 0";
		Query query = em.createQuery(hqlQuery);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getFarmerKycDetails(String farmerId) {
		String hqlQuery = "select f.id, f.farmerName, f.farmerFatherHusbandName, f.primaryMobNumber, f.alternativeMobNumber, kdt.name,"
				+ " fk.docPhoto, fk.gender, fk.dob, fk.permanentAddress " + " from Farmer as f "
				+ " inner join FarmerKyc as fk on f.id = fk.farmerId"
				+ " inner join KycDocType as kdt on fk.docTypeId = kdt.id" + " where f.id = :farmerId";
		Query query = em.createQuery(hqlQuery).setParameter("farmerId", farmerId);
		return query.getResultList();
	}

	@Transactional
	@Override
	public ResponseMessage saveKycDetails(KycDetailsModel kycDetailsModel, Task task, TaskHistory taskHistory,
										  SubTask subTask, boolean isVerified) {
/*
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		
		String hqlQuery = "update Farmer set farmerName = :farmerName, farmerFatherHusbandName = :farmerFatherHusbandName,"
				+ " primaryMobNumber = :primaryMobNumber, alternativeMobNumber = :alternativeMobNumber "
				+ " where id = :farmerId";

		Query query = em.createQuery(hqlQuery).setParameter("farmerName", kycDetailsModel.getFarmerName())
				.setParameter("farmerFatherHusbandName", kycDetailsModel.getFarmerHusbandName())
				.setParameter("primaryMobNumber", kycDetailsModel.getMobileNumber())
				.setParameter("alternativeMobNumber", kycDetailsModel.getAlternateMobileNumber())
				.setParameter("farmerId", kycDetailsModel.getFarmerId());
		query.executeUpdate();

		String hqlQuery1 = "update FarmerKyc set isVerified = 1, gender = :gender, dob = :dob, permanentAddress = :permanentAddress"
				+ " where farmerId = :farmerId";
		
		java.util.Date date = null;
		try {
			date = sdf.parse(kycDetailsModel.getDateOfBirth());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Query query1 = em.createQuery(hqlQuery1).setParameter("gender", kycDetailsModel.getGender())
				.setParameter("dob", new Date(date.getTime()))
				.setParameter("permanentAddress", kycDetailsModel.getAddress())
				.setParameter("farmerId", kycDetailsModel.getFarmerId());
		
		*/
		String hqlQuery1 = "update FarmerKyc set isVerified = 1";
		Query query1 = em.createQuery(hqlQuery1);
		query1.executeUpdate();

		/*TODO: discussion required*/

		String hqlQuery = "update SubTask set "
				+ " kycIsVerified = :imageName"
				+ " where taskId = :id";

		Query query = em.createQuery(hqlQuery)
				.setParameter("imageName", 1)
				.setParameter("id", subTask.getTaskId());
		query.executeUpdate();
		/*subTask.setImageIsVerified(1);
		em.persist(subTask);*/
		if (isVerified) {
//			task.setStatus(2);
//			task.setAssigneeId(kycDetailsModel.getUserId());
//			task.setTaskTypeId(7);
			task.setTaskTypeId(8);
			em.persist(task);
		}

		taskHistory.setId(generateKey(kycDetailsModel.getUserId(), "TASK_HISTORY"));
		taskHistory.setComment(kycDetailsModel.getComment());
		taskHistory.setAssigneeId(kycDetailsModel.getUserId());
		taskHistory.setStatus(2);
		em.persist(taskHistory);

		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setMessage("KYC details of farmer has been verified");
		responseMessage.setStatusCode("success");
		return responseMessage;

	}
	
	@Transactional
	@Override
	public ResponseMessage kycCorrection(KycDetailsModel kycDetailsModel, Task task, TaskHistory taskHistory) {

		task.setTaskDate(new java.sql.Date(System.currentTimeMillis()));
		task.setStatus(0);
		task.setTaskTypeId(22);
		task.setAssigneeId(0);
		em.persist(task);

		taskHistory.setId(generateKey(kycDetailsModel.getUserId(), "TASK_HISTORY"));
		taskHistory.setComment(kycDetailsModel.getComment());
		taskHistory.setAssigneeId(kycDetailsModel.getUserId());
		taskHistory.setStatus(2);
		em.persist(taskHistory);

		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setMessage("Successfully Submitted");
		responseMessage.setStatusCode("success");
		return responseMessage;

	}

	private String generateKey(int userId, String entityName) {
		Properties properties = new Properties();
		try {
			InputStream resourceStream = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("entity-code.properties");
			properties.load(resourceStream);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			Thread.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		int fixLenght = Integer.parseInt(properties.getProperty("FIX_LENGHT"));
		String entityValue = properties.getProperty(entityName);
		String id = String.valueOf(userId);
		int prefixZero = fixLenght - id.length();
		StringBuffer sb = new StringBuffer(entityValue);
		for (int i = 0; i < prefixZero; i++) {
			sb.append("0");
		}
		sb.append(id);
		sb.append(System.currentTimeMillis());
		return sb.toString();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Task> getTask(String taskId) {
		Query query = em.createQuery(" from Task where id = :taskId", Task.class).setParameter("taskId", taskId);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getCommodityByIds(List<Integer> ids){
		try {
			Query query = em.createQuery("select name from Commodity where id in (:ids)").setParameter("ids", ids);
			return query.getResultList();
		}catch (Exception e) {
			
		}
		return new ArrayList<>();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getTaskHistoryByTaskId(String taskId) {
		Query query = em.createQuery("SELECT u.firstName, t.comment, r.name from TaskHistory t inner join Users u on t.assigneeId=u.id inner join UserRoles ur on u.id = ur.userId inner join Roles r on ur.roleId = r.id   where t.taskId = :taskId").setParameter("taskId", taskId);
		return query.getResultList();
	}
}
