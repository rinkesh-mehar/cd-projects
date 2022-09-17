package com.drkrishi.iqa.dao;

import java.util.List;
import java.util.Set;

import com.drkrishi.iqa.entity.*;

import com.drkrishi.iqa.model.BenchmarkedImageRejectionModel;
import com.drkrishi.iqa.model.IqaTaskListModel;

public interface IqaDao {

	public List<Object> checkTaskAssigned(int qaId);

	//public StaticData getStaticData();
	
	public List<StaticData> getStaticData(List<String> keys);

	public List<Object> getQaUserCount();

	public List<Object[]> getGroupBenchmarkedImageByCommodityStateRegion();

	public List<ViewBenchmarkedImage> getGroupedBenchmarkedImageDetails(String commodityName, String stateName,
			String regionName);

	public int assignTask(List<String> caseIds, int qaId);

	public List<Object[]> getAssignedTask(int qaId);
	
	public List<Object[]> getAllIqaTask();

	public List<Object[]> getStressDetails(String spotId);

	public List<Object[]> getStressImageDetails(String spotId, Integer stressId, Integer commodityId);

	public List<Object[]> getHealthImageDetails(String spotId);
	
	public List<Task> getTask(String taskId);
	
	public void benchmarkedImageRejection(BenchmarkedImageRejectionModel benchmarkedImageRejectionModel, Task task, TaskHistory taskHistory);
	
	public void uploadEditedBenchmarkedImage(String comment, Task task, TaskHistory taskHistory,SubTask subTask, boolean isVerified);

	public List<SubTask> getSubTask(String taskId);

	public List<Object[]> getHealthImageDetails(Integer stateId,
												Integer regionId, Integer commodityId);

	public List<String> getSportIds(Integer stateId,
								   Integer regionId, Integer commodityId, boolean isStress);

	public void updateSpotStressSymptomImages(String imageUrl,String spotStressSymptomImageId,String status);

	public void updateSpotHealthImages(String imageUrl, String spotHealthImageId, String status);

}
