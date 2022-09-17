package com.drkrishi.iqa.dao;

import java.util.List;

import com.drkrishi.iqa.entity.*;

public interface KmlqaDao {
	public List<StaticData> getStaticDataByKey(List<String> keys);
	public StaticData getStaticData();
	public List<Object> checkTaskAssigned(int kmlqaId);
	public List<Object> getKmlqaTaskCount();
	public List<Object> getKmlUserCount();
	public List<ViewKmlqaTask> getAgriotaCust();
	public List<ViewKmlqaTask> getDrkCust();
	public List<ViewKmlqaTask> getWillingnessForCdt();
	public int assignTask(List<String> taskIds, int kmlqaId);
	public List<ViewKmlqaTask> getAssignedTask(int kmlqaId);
	public List<ViewKmlqaTask> getAllTask();
	public List<Object> getFileDetails(String TaskId);
	public List<ViewCropInformation> getCropDetails(String taskId);
	
	public Task getTaskById(String TaskId);
	
	public boolean updateCaseCropArea(String caseId,Double area);
	public void submitDetails(Task task, TaskHistory taskHistory, SubTask subTask, boolean isVerified);
	public void updateKmlUrl(String taskId, String kmlUrl);
	
}
