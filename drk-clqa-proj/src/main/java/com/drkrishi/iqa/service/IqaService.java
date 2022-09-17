package com.drkrishi.iqa.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.drkrishi.iqa.model.BenchmarkedImageDetailsModel;
import com.drkrishi.iqa.model.BenchmarkedImageRejectionModel;
import com.drkrishi.iqa.model.IqaTaskListModel;
import com.drkrishi.iqa.model.ResponseMessage;

public interface IqaService {

	public ResponseMessage getIqaTaskList(int userId);
	public ResponseMessage getStressImageDetails(IqaTaskListModel iqaTaskListModel, boolean status);
	public ResponseMessage uploadEditedBenchmarkedImage(String benchmarkedImageName, String benchmarkedImage, String benchmarkedImageId, /*String comment*/ String status, Integer userId) throws Exception;
	public ResponseMessage imageRejection(BenchmarkedImageRejectionModel imageRejectionModel);
	public ResponseMessage getHealthImageDetails(String spotId);
	public ResponseMessage getSpotStressDetails(String spotId, Integer commodityId);
	public ResponseMessage getTaskSpotIds(IqaTaskListModel iqaTaskListModel);
	public ResponseMessage completedTask(String taskId, Integer userId);

}
