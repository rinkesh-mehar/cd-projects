package com.drkrishi.iqa.dao;

import java.util.List;

import com.drkrishi.iqa.entity.*;
import com.drkrishi.iqa.model.KycDetailsModel;
import com.drkrishi.iqa.model.ResponseMessage;

public interface KycqaDao {

	//public StaticData getStaticData();
	public List<StaticData> getStaticData(List<String> keys);
	public List<Object> checkTaskAssigned(int qaId);
	public List<Object> getQaTaskCount();
	public List<Object> getQaUserCount();
	public List<ViewKycqaTask> getAgriotaCust();
	public List<ViewKycqaTask> getDrkCust();
	public List<ViewKycqaTask> getWillingnessForCdt();
	public int assignTask(List<String> taskIds, int qaId);
	public List<ViewKycqaTask> getAssignedTask(int qaId);
	public List<ViewKycqaTask> getAllKycTask();
	
	public List<Object[]> getFarmerKycDetails(String farmerId);
	public ResponseMessage saveKycDetails(KycDetailsModel kycDetailsModel, Task task, TaskHistory taskHistory, SubTask subTask, boolean isVerified);
	public List<Task> getTask(String taskId);
	
	public List<Object> getCommodityByIds(List<Integer> ids);
	
	public List<Object[]> getTaskHistoryByTaskId(String taskId);
	public ResponseMessage kycCorrection(KycDetailsModel kycDetailsModel,Task task, TaskHistory taskHistory);
}
