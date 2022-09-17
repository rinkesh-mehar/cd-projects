package com.drkrishi.rlt.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.*;

import com.drkrishi.rlt.entity.*;
import com.drkrishi.rlt.repository.SubtaskRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.drkrishi.rlt.dao.BarcodeDao;
import com.drkrishi.rlt.model.BarcodeModel;
import com.drkrishi.rlt.model.BarcodeRequestModel;
import com.drkrishi.rlt.model.ResponseMessage;

@Service
public class BarcodeServiceImpl implements BarcodeService {

	private static final Logger LOGGER = LogManager.getLogger(BarcodeServiceImpl.class);

	@Autowired
	BarcodeDao barcodeDao;

	@Autowired
	SubtaskRepository subtaskRepository;

	@Override
	public ResponseMessage saveBarcodeDetails(BarcodeRequestModel barcodeRequestModel) {

		// get RLM id based on RLT id --one region one RLM
//		int rlmId = (int) barcodeDao.findRlmByRltId(barcodeRequestModel.getUserId()).get(0);
		ResponseMessage responseMessage = new ResponseMessage();
		if( barcodeRequestModel.getUserId() == 0 || barcodeRequestModel.getUserId() == null) {
			responseMessage.setStatusCode(500);
			responseMessage.setMessage("There is an error. Please logout, login and try again");
		}
		else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			int flag = barcodeDao.saveBarcodeDetails(barcodeRequestModel.getTaskId(), barcodeRequestModel.getUserId(),barcodeRequestModel.getDate());
			if(flag > 0) {
				responseMessage.setStatusCode(200);
				responseMessage.setMessage("Sample has been received");

				List<Task> tasks = barcodeDao.getTask(barcodeRequestModel.getTaskId());
				if(tasks.size() > 0) {
					TaskHistory taskHistory = convertToTaskHistory(tasks.get(0), barcodeRequestModel.getUserId());
					barcodeDao.saveTaskHistory(taskHistory);
				}
	//			responseMessage.setData(getSampleList(barcodeRequestModel.getUserId()));
			} else {
				responseMessage.setStatusCode(500);
				responseMessage.setMessage("Failed to save sample");
			}

		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getBarcodeDetails(String barcodeNumber, int loggedinUserId) {
		ViewBarcodeDetails queryResult = barcodeDao.getBarcodeDetails(barcodeNumber);

		Users users = barcodeDao.getUsersDetails(loggedinUserId);
		ResponseMessage responseMessage = new ResponseMessage();
		BarcodeModel barcodeModel = new BarcodeModel();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		if(queryResult != null) {

			Optional<Subtask> fetchedSubtask = subtaskRepository.findFirstByTaskIdOrderByCreatedAtDesc(queryResult.getTaskId());

			if (fetchedSubtask.isPresent()) {
				Subtask subtask = fetchedSubtask.get();

//				if (subtask.getKycIsVerified() && subtask.getKmlIsVerified() && subtask.getBankIsVerified()
//						/*&& subtask.getImageIsVerified()*/) {

					responseMessage.setStatusCode(200);
					responseMessage.setMessage("Data fetched successfully");

					int status = queryResult.getStatus();
					int taskTypeId = (int) queryResult.getTaskTypeId();
					int regionId = queryResult.getRegionId() == null ? 0 : queryResult.getRegionId();
					int userId = queryResult.getAssigneeId() == null ? 0 : queryResult.getAssigneeId();
					if (taskTypeId == 6 && status == 1 && userId == loggedinUserId) {
						responseMessage.setStatusCode(403);
						responseMessage.setMessage("Sample already assigned to you");
					} else if (taskTypeId == 6 && status == 1 && userId != 0 && userId != loggedinUserId) {
						responseMessage.setStatusCode(403);
						responseMessage.setMessage("Sample already assigned to other");
					} else if (users.getRegionId() != regionId) {
						responseMessage.setStatusCode(403);
						responseMessage.setMessage("The sample barcode is belong to other region");
					} else if (taskTypeId == 6 /*&& status == 0*/ && userId == 0) {
						barcodeModel.setBarcodeNumber(queryResult.getBarcode());
						barcodeModel.setEntityId(queryResult.getEntityId());
						barcodeModel.setVillage(queryResult.getVillageName());
						barcodeModel.setFormerMobile(queryResult.getFormerMobile());
						barcodeModel.setCrop(queryResult.getCropName());
						barcodeModel.setTaskId((String) queryResult.getTaskId());
						barcodeModel.setCropType((Integer) queryResult.getCropType());
						barcodeModel.setSellerType((Integer) queryResult.getSellerType());
//						if (queryResult.getAssigneeId() == null || ((int) queryResult.getAssigneeId()) == 0) {
//							barcodeModel.setReceivedDate(sdf.format(new Date()));
//						} else {
							barcodeModel.setReceivedDate(sdf.format(getReceivedDate((String) queryResult.getTaskId())));
//						}
						//			barcodeModel.setCausedBy(barcodeDao.getStressDesciption((int) queryResult.get(0)[5]).toString());
						responseMessage.setData(barcodeModel);
					} else {
						responseMessage.setStatusCode(403);
						responseMessage.setMessage("Invalid sample");
					}
//				} else {
//					responseMessage.setStatusCode(500);
//					responseMessage.setMessage("Please Verify KML KYC and Bank First");
//				}
			}
		} else {
			responseMessage.setStatusCode(500);
			responseMessage.setMessage("Data not found For Barcode");
		}
		return responseMessage;
	}

	@Override
	public List<BarcodeModel> getSampleList(int rltId) {
		
			List<ViewBarcodeDetails> queryResult = barcodeDao.getBarcodeSampleDetails(rltId);
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			List<BarcodeModel> barcodeModels = new ArrayList<>();
			if(queryResult.size() > 0) {
				for(int i=0; i<queryResult.size(); i++) {
					BarcodeModel barcodeModel = new BarcodeModel();
					barcodeModel.setBarcodeNumber(queryResult.get(i).getBarcode());
					barcodeModel.setEntityId((queryResult.get(i).getEntityId()));
					barcodeModel.setVillage(queryResult.get(i).getVillageName());
					barcodeModel.setFormerMobile(queryResult.get(i).getFormerMobile());
					barcodeModel.setCrop(queryResult.get(i).getCropName());
					Date date = getReceivedDate(queryResult.get(i).getTaskId());
					barcodeModel.setTaskId(queryResult.get(i).getTaskId());
					barcodeModel.setCropType(queryResult.get(i).getCropType());
					barcodeModel.setSellerType(queryResult.get(i).getSellerType());
					if(date != null) {
						barcodeModel.setReceivedDate(sdf.format(date));
					} else {
						barcodeModel.setReceivedDate(sdf.format(queryResult.get(i).getTaskDate()));
					}

					barcodeModels.add(barcodeModel);
				}

				// barcodeModel.setCausedBy(barcodeDao.getStressDesciption((int)

			}

		return barcodeModels;
	}

	private java.sql.Date getReceivedDate(String taskId) {

		List<Task> dateList =  barcodeDao.getTaskReceivedDate(taskId);
		if(dateList.size() > 0) {
			return (java.sql.Date) dateList.get(0).getTaskDate();
		}
		return null;
	}

	public ResponseMessage setNeedMoreSample(String taskId, int userId) {
		ResponseMessage responseMessage = new ResponseMessage();
		List<Task> oldTasks = barcodeDao.getTask(taskId);
		if(oldTasks.size() > 0) {

			Task oldTask = oldTasks.get(0);
			if(oldTask.getTaskTypeId() == 8) {
				responseMessage.setStatusCode(403);
				responseMessage.setMessage("Alreay sent to collect more samples");
			} else {
				responseMessage.setStatusCode(200);
				responseMessage.setMessage("updated successfully");
				Task task = new Task();
				task.setId(generateKey(userId, "TASK"));
				task.setEntityTypeId(oldTask.getEntityTypeId());
				task.setEntityId(oldTask.getEntityId());
				task.setTaskTypeId(7);
				barcodeDao.setNeedMoreSample(task);

				TaskHistory taskHistory = new TaskHistory();
				taskHistory.setId(generateKey(userId, "TASK_HISTORY"));
				taskHistory.setTaskId(oldTask.getId());
				taskHistory.setTaskDate(oldTask.getTaskDate());
				taskHistory.setStartTime(oldTask.getTaskTime());
				taskHistory.setEndTime(new Time(System.currentTimeMillis()));
				taskHistory.setTaskTypeId(oldTask.getTaskTypeId());
				taskHistory.setAssigneeId(oldTask.getAssigneeId());
				taskHistory.setStatus(oldTask.getStatus());
				taskHistory.setEntityTypeId(oldTask.getEntityTypeId());
				taskHistory.setEntityId(oldTask.getEntityId());
				taskHistory.setComment("Need More Sample");
				barcodeDao.saveTaskHistory(taskHistory);

				oldTask.setStatus(5);
				barcodeDao.setNeedMoreSample(oldTask);
			}
		} else {
			responseMessage.setStatusCode(500);
			responseMessage.setMessage("failed to update record");
		}

		return responseMessage;
	}

	private String generateKey(int userId, String entityName) {

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
		String id = String.valueOf(userId);
		int prefixZero = fixLenght - id.length();

		StringBuffer sb = new StringBuffer(entityValue);

		for( int i=0; i<prefixZero; i++) {
			sb.append("0");
		}
		sb.append(id);
		sb.append(System.currentTimeMillis());
		return sb.toString();
	}

	private TaskHistory convertToTaskHistory(Task oldTask, Integer userId) {
		TaskHistory taskHistory = new TaskHistory();
		taskHistory.setId(generateKey(userId, "TASK_HISTORY"));
		taskHistory.setTaskId(oldTask.getId());
		taskHistory.setTaskDate(new java.sql.Date(System.currentTimeMillis()));
		taskHistory.setStartTime(new Time(System.currentTimeMillis()));
		taskHistory.setEndTime(oldTask.getEndTime());
		taskHistory.setTaskTypeId(oldTask.getTaskTypeId());
		taskHistory.setAssigneeId(oldTask.getAssigneeId());
		taskHistory.setStatus(oldTask.getStatus());
		taskHistory.setEntityTypeId(oldTask.getEntityTypeId());
		taskHistory.setEntityId(oldTask.getEntityId());
		taskHistory.setComment("Saved RLT assigneed task date for RLM dashboard reference");
		return taskHistory;
	}
}
