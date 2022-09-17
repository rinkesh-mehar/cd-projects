package com.drkrishi.rlt.dao;

import java.sql.Date;
import java.util.List;

import com.drkrishi.rlt.entity.Task;
import com.drkrishi.rlt.entity.TaskHistory;
import com.drkrishi.rlt.entity.Users;
import com.drkrishi.rlt.entity.ViewBarcodeDetails;

public interface BarcodeDao {

	public ViewBarcodeDetails getBarcodeDetails(String barcodeNumber);
	public int saveBarcodeDetails(String taskId, int rlmId, Date date);
	public Users getUsersDetails(Integer userId);
//	public List<Object> getStressDesciption(int StressId);
	public List<ViewBarcodeDetails> getBarcodeSampleDetails(int barcodeNumber);
	public List<Object>  findRlmByRltId(int rltId);
	public List<Task>  getTaskReceivedDate(String taskId);
	public void setNeedMoreSample(Task task);
	public List<Task> getTask(String taskId);
	
	public void saveTaskHistory(TaskHistory taskHistory);
}
