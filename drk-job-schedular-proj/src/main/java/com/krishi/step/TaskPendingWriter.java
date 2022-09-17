package com.krishi.step;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.krishi.entity.FarmerCropInfo;
import com.krishi.repository.FarmerCropInfoRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;

import com.krishi.batch.BatchApplication;
import com.krishi.entity.Task;
import com.krishi.entity.TaskFutureDates;
import com.krishi.entity.TaskHistory;
import com.krishi.repository.FlsTaskRepository;
import com.krishi.repository.TaskRepository;

public class TaskPendingWriter implements ItemWriter<List<Task>> {

	private static final Logger LOGGER = LogManager.getLogger(BatchApplication.class);

	@PersistenceContext
	EntityManager em;

	@Autowired
	private TaskRepository taskRepo;

	@Autowired
	private FarmerCropInfoRepository farmerCropInfoRepository;
	/*process and send the pending task to cctc non-technical user*/
	@Override
	public void write(List<? extends List<Task>> item) throws Exception {

		for (Task task : item.get(0)) {
			if (task.getStatus() != 4) {
				taskHistoryUpdate(task);
			}
			updateTask(task);
		}
		LOGGER.info("INFO:Task Pending Writer successfully");
	}

	private void taskHistoryUpdate(Task task) {

		TaskHistory taskHistory = new TaskHistory();

		String generatedTaskHistoryId = generateKey("TASK_HISTORY");

		taskHistory.setId(generatedTaskHistoryId);
		taskHistory.setTaskId(task.getId());
		taskHistory.setTaskDate(task.getTaskDate());
		taskHistory.setStartTime(task.getStartTime());
		taskHistory.setEndTime(task.getEndTime());
		taskHistory.setTaskTypeId(task.getTaskTypeId());
		taskHistory.setAssigneeId(task.getAssigneeId());
		// status drop
		taskHistory.setStatus(4);
		taskHistory.setEntityTypeId(task.getEntityTypeId());
		taskHistory.setEntityId(task.getEntityId());
		// in task there is no comment
		taskHistory.setComment(null);

		em.persist(taskHistory);
		em.close();

	}

	private void updateTask(Task task) {

		if (task.getStatus() != 4) {
			Date schedule = scheduleDate(task.getId());

			/** update pushback value : Ujwal - Start */
			Integer pushBackCount = taskRepo.getPushback(task.getId());
			if (pushBackCount != null) {
				task.setPushback(--pushBackCount);
			}
			/** update pushback value : Ujwal - End */
			if (schedule != null) {
				task.setTaskDate(schedule);
			} else {
				task.setTaskTypeId(10);
				/*
				 * if (task.getTaskTypeId() == 4 || task.getTaskTypeId() == 5) { // assign for
				 * CCTC Technical task.setTaskTypeId(11); } else { // assign for CCTC
				 * non-Technical task.setTaskTypeId(10); }
				 */
				updateFarmerCropInfo(task.getFarmerCropInfoId());

			}
		}
		/** update pushback value : Ujwal - Start */
		/*
		 * else { Integer pushBackCount = taskRepo.getPushback(task.getId()); if
		 * (pushBackCount != null) { task.setPushback(--pushBackCount); } }
		 *//** update pushback value : Ujwal - End */

		task.setStatus(0);
		task.setAssigneeId(0);
		task.setFarmerCropInfoId(task.getFarmerCropInfoId());
		em.persist(task);
		em.close();
	}

	private Date scheduleDate(String taskId) {

		try {

			String hql = "SELECT taskDate FROM TaskFutureDates t WHERE t.taskId= : taskId";
			List<Date> result = em.createQuery(hql).setParameter("taskId", taskId).getResultList();

			java.util.Date dateInstance = new java.util.Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(dateInstance);
			cal.add(Calendar.DATE, -1);
			java.util.Date currentDate = cal.getTime();

			if (result.size() != 0) {
				for (Date date : result) {
					if (date.compareTo(currentDate) >= 0) {
						return (Date) date;
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error(e);
		}
		return null;
	}

	private String generateKey(String entityName) {
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
		String userId = properties.getProperty("USER_ID");
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

	public boolean updateFarmerCropInfo(String farmerCropInfoId){
		boolean isUpdate  = false;
		FarmerCropInfo farmerCropInfo = null;
		try {
			if (farmerCropInfoId != null){
				farmerCropInfo = farmerCropInfoRepository.getFarmerCropInfoById(farmerCropInfoId);
				farmerCropInfo.setLeadCallingStatus(0);
				isUpdate = true;
			}
		}catch (Exception e){
			LOGGER.info("ERROR M ----> {}", e.getMessage());
		}
		em.persist(farmerCropInfo);
		em.close();
		return isUpdate;
	}

}
