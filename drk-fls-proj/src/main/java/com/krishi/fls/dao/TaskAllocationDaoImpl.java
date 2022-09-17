package com.krishi.fls.dao;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.krishi.fls.entity.Farmer;
import com.krishi.fls.entity.TaskAllocation;
import com.krishi.fls.entity.TaskType;
import com.krishi.fls.repository.FarmerRepository;
import com.krishi.fls.repository.TaskAllocationRepositry;
import com.krishi.fls.repository.TasktypeRepository;
@Repository
@Component
public class TaskAllocationDaoImpl implements TaskAllocationDao {

 private static final Logger LOGGER = LogManager.getLogger(TaskAllocationDaoImpl.class);
	
 @Autowired
 private FarmerRepository farmerRepo;

 @Autowired
 private TaskAllocationRepositry taskAllocationRepo;
 
 @Autowired
 private TasktypeRepository taskTypeRepo;

 /* (non-Javadoc)
  * @see com.krishi.fls.dao.TaskAllocationDao#setTask()
  */
 @Override
 public void setTask() {
  List<Farmer> drkFarmers = farmerRepo.findnewUsers();
  List<Farmer> agrigotaFarmers = farmerRepo.findrkUsers();
  //List<Farmer> willingFarmers = farmerRepo.findrkCDTUsers();
 
  
  
  for (Farmer farmerLis : drkFarmers) {
   List<TaskType> taskTypes=taskTypeRepo.getTaskList();
   
   setNewFarmerTask(farmerLis, taskTypes);
  }
  for (Farmer farmerLis : agrigotaFarmers) {
   List<TaskType> taskTypes=taskTypeRepo.getAgrigotaList();
   
   setNewFarmerTask(farmerLis, taskTypes);
  }
  
 }

 /**
  * @param farmerLis
  * @param taskTypes
  */
 @Transactional
 private void setNewFarmerTask(Farmer farmerLis,
  List<TaskType> taskTypes) {
 // for(TaskType tasktypes:taskTypes) {
  
  TaskAllocation taskAllocation = new TaskAllocation();
  taskAllocation.setId( generateKey("TASK"));
  taskAllocation.setEntityId(farmerLis.getFarmerId());
  taskAllocation.setTaskDate(getNextDate(new Date()));
  //taskAllocation.setTaskTime(new Time(new Date().getTime()));
  taskAllocation.setTaskTypeId(1);//default 0 for the Lead collection for the new farmer
  //taskAllocation.setAssigneeId(null);
  taskAllocation.setStatus(0);
  taskAllocation.setEntityTypeId(4);//Always Farmer registration
  taskAllocation= taskAllocationRepo.save(taskAllocation);
  //}
 }
 
 private String generateKey(String entityName) {
		
		FileReader reader = null;
		Properties properties = new Properties(); 
		try {
			InputStream resourceStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("entity-code.properties");
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
		String id = String.valueOf(properties.getProperty("USER_ID"));
		int prefixZero = fixLenght - id.length();
		
		StringBuffer sb = new StringBuffer(entityValue);
		
		for( int i=0; i<prefixZero; i++) {
			sb.append("0");
		}
		sb.append(id);
		sb.append(System.currentTimeMillis());
		return sb.toString();
	}

 /**
  * @param curDate
  * @return
  */
 public static Date getNextDate(Date curDate) {
  final SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
  Date date;
  try {
   date = format.parse(format.format(curDate));
   final Calendar calendar = Calendar.getInstance();
   calendar.setTime(date);
   calendar.add(Calendar.DAY_OF_YEAR, 1);
   return new Date(format.format(calendar.getTime()));
  } catch (ParseException e) {
   LOGGER.error("Exception occured while getting date : "+e.toString());
   return null;
  }
 }
}
