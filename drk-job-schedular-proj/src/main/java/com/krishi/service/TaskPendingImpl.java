package com.krishi.service;

import com.krishi.entity.FarmerCropInfo;
import com.krishi.entity.Task;
import com.krishi.entity.TaskHistory;
import com.krishi.repository.FarmerCropInfoRepository;
import com.krishi.repository.TaskHistoryRepository;
import com.krishi.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

@Service
public class TaskPendingImpl implements TaskPending{
    private static final Logger logger = LoggerFactory.getLogger(TaskGeneratorImpl.class);

    @Autowired
    TaskRepository taskRepository;

    @PersistenceContext
    EntityManager em;

    @Autowired
    TaskHistoryRepository taskHistoryRepository;

    @Autowired
    private TaskRepository taskRepo;

    @Autowired
    private FarmerCropInfoRepository farmerCropInfoRepository;

    @Override
    public boolean updateExistingTask() throws Exception {
      return this.write(this.read());
    }

    @Transactional
    List<Task> read() throws UnexpectedInputException, ParseException, NonTransientResourceException {

        List<Task> taskList = taskRepository.taskPending();
        return taskList.size() > 0 ? taskList : null;
    }

    private boolean write(List<Task> item) throws Exception {

        if(item != null)
        {
            for (Task task : item)
            {
                logger.info("check task type ids is {} and taskId is {}", task.getTaskTypeId(), task.getId());
                if (task.getStatus() != 4)
                {
                    taskHistoryUpdate(task);
                }
                updateTask(task);
            }
            logger.info("INFO:Task Pending Writer successfully");
            return true;
        } else {
            return false;
        }
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    void taskHistoryUpdate(Task task) {

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

        taskHistoryRepository.save(taskHistory);
//        em.close();

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
        taskRepository.save(task);
//        em.close();
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
            logger.error(String.valueOf(e));
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

    private void updateFarmerCropInfo(String farmerCropInfoId){
        boolean isUpdate  = false;
        FarmerCropInfo farmerCropInfo = null;
        try {
            if (farmerCropInfoId != null){
                farmerCropInfo = farmerCropInfoRepository.getFarmerCropInfoById(farmerCropInfoId);
                if (farmerCropInfo != null)
                {
                    farmerCropInfo.setLeadCallingStatus(0);
                    isUpdate = true;
                }
            }
        }catch (Exception e){
            logger.info("ERROR M ----> {}", e.getMessage());
        }
        farmerCropInfoRepository.save(farmerCropInfo);
//        em.close();
    }

}
