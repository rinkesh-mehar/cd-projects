package com.drkrishi.rlt.entity;

import java.io.Serializable;
import java.sql.Date;

public class ViewTaskHistoryInfoId implements Serializable {
 
private static final long serialVersionUID = 1L;
private String taskId;
private Date taskDate;

public String getTaskId() {
	return taskId;
}
public void setTaskId(String taskId) {
	this.taskId = taskId;
}
public Date getTaskDate() {
	return taskDate;
}
public void setTaskDate(Date taskDate) {
	this.taskDate = taskDate;
}
  
  
}

