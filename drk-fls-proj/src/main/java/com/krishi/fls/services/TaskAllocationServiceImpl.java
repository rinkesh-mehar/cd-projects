package com.krishi.fls.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.krishi.fls.dao.TaskAllocationDao;

@Service
@Component
public class TaskAllocationServiceImpl implements TaskAllocationService {

 @Autowired
 private TaskAllocationDao taskAllocationDao;
 
 @Override
 public void setTask() {
  taskAllocationDao.setTask();
  
 }
}
