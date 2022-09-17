package com.drkrishi.rlt.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.drkrishi.rlt.entity.Commodity;
import com.drkrishi.rlt.entity.TaskHistory;
import com.drkrishi.rlt.entity.Users;
import com.drkrishi.rlt.entity.ViewTaskHistoryInfo;
import com.drkrishi.rlt.entity.ViewTaskInfo;

@Repository
public class DiagnosisApprovalDaoImpl implements DiagnosisApprovalDao {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	@Transactional
	public Users getUserById(Integer userId) {
		String sql = "select u from Users u where u.id = :userId";
		Query query = em.createQuery(sql, Users.class);
		query.setParameter("userId", userId);
		Users user  = (Users)  query.getSingleResult();
		em.clear();
		em.close();
		return user;
	}
	
	

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<ViewTaskInfo> getRlmPendingRecords(Integer userId, Integer regionId, Date startDate, Date endDate, String barcode) {
		String hqlQuery = "select vt from ViewTaskInfo vt where " +
				" vt.bankIsVerified=true and vt.rltSampleIsVerified=true and vt.rlmApprovalIsVerified=false and vt.taskTypeId=6 and "
				+ " ((vt.taskStatus=0 and vt.farmerRegionId = :regionId) or (vt.taskStatus = 1 and vt.assigneeId = :userId))";

		Query query;
		if (barcode != null && !barcode.isBlank()) {
			hqlQuery = "select vt from ViewTaskInfo vt where " +
					" vt.bankIsVerified=true and vt.rltSampleIsVerified=true and vt.rlmApprovalIsVerified=false and vt.taskTypeId=6 and "
					+ " ((vt.taskStatus=0 and vt.farmerRegionId = :regionId) or (vt.taskStatus = 1 and vt.assigneeId = :userId)) "
					+ " and vt.barcode=:barcode";
			query = em.createQuery(hqlQuery).setParameter("userId", userId)
					.setParameter("regionId", regionId)
					.setParameter("barcode", barcode);
		} else if (startDate != null && endDate != null) {
			hqlQuery = "select vt from ViewTaskInfo vt where " +
					" vt.bankIsVerified=true and vt.rltSampleIsVerified=true and vt.rlmApprovalIsVerified=false and vt.taskTypeId=6 and"
					+ " ((vt.taskStatus=0 and vt.farmerRegionId = :regionId) or (vt.taskStatus = 1 and vt.assigneeId = :userId)) "
					+ " and vt.taskDate between :startDate and :endDate";
			query = em.createQuery(hqlQuery).setParameter("userId", userId)
					.setParameter("regionId", regionId)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate);
		} else {
			query = em.createQuery(hqlQuery).setParameter("userId", userId).setParameter("regionId", regionId);
		}
		List<ViewTaskInfo> tasks = query.getResultList();
		em.clear();
		em.close();
		return tasks;
	}
	
	

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<ViewTaskHistoryInfo> getRlmApprovedReassignRecords(Integer regionId, Date startDate, Date endDate, Integer currentTaskTypeId) {
		String hqlQuery = "select vt from ViewTaskHistoryInfo vt where vt.taskTypeId=9 and vt.farmerRegionId = :regionId and "
				+ " vt.taskStatus=2 and vt.currentTaskTypeId = :currentTaskTypeId";

		Query query;
		if (startDate != null && endDate != null) {
			hqlQuery = "select vt from ViewTaskHistoryInfo vt where vt.taskTypeId=9 and vt.farmerRegionId = :regionId "
					+ " and vt.taskStatus=2 and vt.currentTaskTypeId = :currentTaskTypeId "
					+ " and vt.taskDate between :startDate and :endDate";
			query = em.createQuery(hqlQuery)
					.setParameter("regionId", regionId)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("currentTaskTypeId", currentTaskTypeId);
		} else {
			query = em.createQuery(hqlQuery).setParameter("currentTaskTypeId", currentTaskTypeId)
					.setParameter("regionId", regionId);
		}
		List<ViewTaskHistoryInfo> info = query.getResultList();
		Map<String, ViewTaskHistoryInfo> map = new HashMap<>();
		for(ViewTaskHistoryInfo t:info) {
			if(!map.containsKey(t.getTaskId()) || map.get(t.getTaskId()).getTaskDate().before(t.getTaskDate())) {
				map.put(t.getTaskId(), t);
			}
		}
		info = new ArrayList<>(map.values());
		em.clear();
		em.close();
		return info;
	}
	

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Commodity> getCommodityListByIds(List<Integer> commodityIdList) {
		String hqlQuery = "select c from Commodity c where c.id in (:ids)";
		Query query = em.createQuery(hqlQuery).setParameter("ids", commodityIdList);
		List<Commodity> list = query.getResultList();
		em.clear();
		em.close();
		return list;
	}

	@Override
	@Transactional
	public TaskHistory getLatestTaskHistoryByTaskId(String taskId) {
		try {
			String hqlQuery = "select t from TaskHistory t where t.taskId = :taskId and t.taskTypeId = 6 order by t.taskDate desc";
			Query query = em.createQuery(hqlQuery).setParameter("taskId", taskId);
			TaskHistory history = (TaskHistory) query.setMaxResults(1).getSingleResult();
			em.clear();
			em.close();
			return history;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	@Transactional
	public TaskHistory getFirstTaskHistoryByTaskId(String taskId) {
		try {
			String hqlQuery = "select t from TaskHistory t where t.taskId = :taskId and t.taskTypeId = 6 order by id asc";
			Query query = em.createQuery(hqlQuery).setParameter("taskId", taskId);
			TaskHistory history =  (TaskHistory) query.setMaxResults(1).getSingleResult();
			em.clear();
			em.close();
			return history;
		} catch (Exception e) {
			return null;
		}
	}



	@Override
	@Transactional
	public Integer updateAssignee(String id, Integer assigneeId) {
		String hqlQuery = "update Task t set t.assigneeId=:assigneeId, t.status = 1 where t.id=:taskId";
		Query query = em.createQuery(hqlQuery);
		query.setParameter("taskId", id)
		.setParameter("assigneeId", assigneeId);
		Integer count =  query.executeUpdate();
		em.clear();
		em.close();
		return count;
	}



	

}
