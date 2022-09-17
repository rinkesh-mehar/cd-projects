package com.drkrishi.iqa.service;

import java.io.IOException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.drkrishi.iqa.dao.IqaDao;
import com.drkrishi.iqa.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.drkrishi.iqa.dao.KycqaDao;
import com.drkrishi.iqa.model.CommentModel;
import com.drkrishi.iqa.model.KycDetailsModel;
import com.drkrishi.iqa.model.KycqaTaskListModel;
import com.drkrishi.iqa.model.ResponseMessage;
import com.drkrishi.iqa.utility.ErrorMessage;
import com.google.common.collect.Lists;

import in.cropdata.toolsuite.sdk.FileManagerUtil;
import in.cropdata.toolsuite.sdk.dto.FileUploadResponseDTO;

@Service
public class KycqaServiceImpl implements KycqaService {

	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");


	@Autowired
	KycqaDao kycqaDao;
	
	ErrorMessage errorMessage = ErrorMessage.GENERIC;
	
	@Autowired
	private FileManagerUtil fileManagerUtil;

	@Autowired
	private IqaDao iqaDao;
	
	@Override
	public ResponseMessage getKycqaList(int userId) {

		//StaticData staticData = kycqaDao.getStaticData();
//		List<StaticData> staticData = kycqaDao.getStaticData(List.of("qaTaskCutoff"));
//		String qaTaskCutoffStr = staticData.stream().map(s -> s.getDataValue()).findFirst()
//				.orElseThrow(() -> new RuntimeException("qaTaskCutoff not found"));
//		int qaTaskCutoff = Integer.parseInt(qaTaskCutoffStr);//staticData.getQaTaskCutoff();

		List<KycqaTaskListModel> kycqaTaskListModels = new ArrayList<>();
//
//		List<Object> checkTaskAssignedCounts = kycqaDao.checkTaskAssigned(userId);
//		if (checkTaskAssignedCounts.size() > 0 && ((long) checkTaskAssignedCounts.get(0) > 0)) {
//			// Task already assigned
//		} else {
//
//			List<Object> kucqaTaskCounts = kycqaDao.getQaTaskCount();
////			List<Object> kycUserCounts = kycqaDao.getQaUserCount();
//
//			if (kucqaTaskCounts.size() > 0 && Long.parseLong(kucqaTaskCounts.get(0).toString()) > 0) {
//
////				float kycUserCount = Float.parseFloat( kycUserCounts.get(0).toString());
//				float kycqaTaskCount = Float.parseFloat(kucqaTaskCounts.get(0).toString());
////				int assignTaskPerQa = (int) Math.ceil(kycqaTaskCount / kycUserCount);
//
//				long assignTaskPerQa = qaTaskCutoff < Long.parseLong(kucqaTaskCounts.get(0).toString()) ? qaTaskCutoff
//						: Long.parseLong(kucqaTaskCounts.get(0).toString());
//				
//				List<String> assignedTask = new ArrayList<>();
//				List<ViewKycqaTask> agriotaCustTasks = kycqaDao.getAgriotaCust();
//
//				if (agriotaCustTasks.size() > 0) {
//					if (agriotaCustTasks.size() <= assignTaskPerQa) {
//						for (ViewKycqaTask agriotaCustTask : agriotaCustTasks) {
//							assignedTask.add(agriotaCustTask.getTaskId());
//							assignTaskPerQa--;
//						}
//					} else {
//						for (int i = 0; i < assignTaskPerQa; i++) {
//							assignedTask.add(agriotaCustTasks.get(i).getTaskId());
//						}
//						assignTaskPerQa = 0;
//					}
//				}
//
//				if (assignTaskPerQa > 0) {
//					List<ViewKycqaTask> drkCustTasks = kycqaDao.getDrkCust();
//					if (drkCustTasks.size() > 0) {
//						if (drkCustTasks.size() <= assignTaskPerQa) {
//							for (ViewKycqaTask drkCustTask : drkCustTasks) {
//								assignedTask.add(drkCustTask.getTaskId());
//								assignTaskPerQa--;
//							}
//						} else {
//							for (int i = 0; i < assignTaskPerQa; i++) {
//								assignedTask.add(drkCustTasks.get(i).getTaskId());
//							}
//							assignTaskPerQa = 0;
//						}
//					}
//				}
//
//				if (assignTaskPerQa > 0) {
//					List<ViewKycqaTask> willingnessForCdtTasks = kycqaDao.getWillingnessForCdt();
//					if (willingnessForCdtTasks.size() > 0) {
//						if (willingnessForCdtTasks.size() <= assignTaskPerQa) {
//							for (ViewKycqaTask willingnessForCdtTask : willingnessForCdtTasks) {
//								assignedTask.add(willingnessForCdtTask.getTaskId());
//								assignTaskPerQa--;
//							}
//						} else {
//							for (int i = 0; i < assignTaskPerQa; i++) {
//								assignedTask.add(willingnessForCdtTasks.get(i).getTaskId());
//							}
//							assignTaskPerQa = 0;
//						}
//					}
//				}
//
//				if (assignedTask.size() > 0) {
//					int assignedTaskCount = kycqaDao.assignTask(assignedTask, userId);
//					if (assignedTaskCount > 0) {
//						// task assigned successfully
//					}
//				} 
//
//			} else {
//				// no KML task (or) no KML user
//			}
//		}
		
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<ViewKycqaTask> kycqaAssignedTasks = kycqaDao.getAllKycTask();
			for (ViewKycqaTask kycqaAssignedTask : kycqaAssignedTasks) {
				KycqaTaskListModel qaTaskListModel = new KycqaTaskListModel();

				// removed commodity name -- 02/10/21
				/*
				List<String> farmerMajorCrops = new ArrayList<String>(
						Arrays.asList(kycqaAssignedTask.getFarmerMajorCrop().split(",")));
				List<String> farmerMajorCropsTrimmed = farmerMajorCrops.stream().map(String::trim)
						.collect(Collectors.toList());

				List<Integer> majorCrops = new ArrayList<Integer>();
				majorCrops = farmerMajorCropsTrimmed.stream().map(Integer::parseInt).collect(Collectors.toList());

				List<Object> majorCropName = kycqaDao.getCommodityByIds(majorCrops);
				if (majorCropName.size() > 0) {
					qaTaskListModel.setCommodity(majorCropName.toString().replace("[", "").replace("]", ""));
				}
*/
				qaTaskListModel.setFarmerId(kycqaAssignedTask.getFarmerId());
				qaTaskListModel.setFarmerName(kycqaAssignedTask.getFarmerName());
				qaTaskListModel.setMobileNumber(kycqaAssignedTask.getPrimaryMobileNumber());
				qaTaskListModel.setRegion(kycqaAssignedTask.getRegionName());
				qaTaskListModel.setState(kycqaAssignedTask.getStateName());
				qaTaskListModel.setTaskId(kycqaAssignedTask.getTaskId());
//				qaTaskListModel.setVariety(kycqaAssignedTask.getVarietyName());
				qaTaskListModel.setVillage(kycqaAssignedTask.getVillageName());
				kycqaTaskListModels.add(qaTaskListModel);
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseMessage.setStatusCode("error");
			responseMessage.setMessage(errorMessage.getMessage());
			return responseMessage;
		}
		responseMessage.setStatusCode("success");
		responseMessage.setData(kycqaTaskListModels);
		return responseMessage;
	}

	@Override
	public ResponseMessage getKycDetails(String farmerId, String taskId, int userId) {
		KycDetailsModel kycDetailsModel = new KycDetailsModel();
		List<Object[]> kycDetails = kycqaDao.getFarmerKycDetails(farmerId);
		ResponseMessage responseMessage = new ResponseMessage();
		//StaticData staticData = kycqaDao.getStaticData();
		List<StaticData> staticData = kycqaDao.getStaticData(List.of("basePath", "kycImageBasePath"));
		Optional<String> pathOp = staticData.stream().filter(s -> s.getDataKey().equals("basePath")).map(s -> s.getDataValue()).findFirst();
		Optional<String> kycPathOp = staticData.stream().filter(s -> s.getDataKey().equals("kycImageBasePath")).map(s -> s.getDataValue()).findFirst();
		String basePath = pathOp.orElse("")+kycPathOp.orElse("");//staticData.getBaseImagePath();

		if (kycDetails.size() > 0) {

			Object[] data = kycDetails.get(0);
			kycDetailsModel.setUserId(userId);
//			kycDetailsModel.setBasePath(basePath);
			kycDetailsModel.setTaskId(taskId);
			kycDetailsModel.setFarmerId(data[0].toString());
			kycDetailsModel.setFarmerName(data[1].toString());
			kycDetailsModel.setFarmerHusbandName(data[2].toString());
			kycDetailsModel.setMobileNumber(data[3].toString());
			kycDetailsModel.setAlternateMobileNumber(data[4].toString());
			kycDetailsModel.setDocumentType(data[5].toString());
			String[] splitData = data[6].toString().trim().split(",");
			kycDetailsModel.setIdProofFront(splitData[0]);
			kycDetailsModel.setIdProofBack(splitData[1].trim());
			kycDetailsModel.setGender(Integer.parseInt(data[7].toString()));
			
			String date = null;
			try {
				if(data[8] != null) {
					date = sdf.format(new SimpleDateFormat("yyyy-MM-dd").parse(data[8].toString())).toString();
				}
			} catch (ParseException e) {
				e.printStackTrace();
				responseMessage.setStatusCode("error");
				responseMessage.setMessage(errorMessage.getMessage());
				return responseMessage;
			}
			kycDetailsModel.setDateOfBirth(date);
			kycDetailsModel.setAddress(data[9].toString());

		}
		List<Object[]> history = kycqaDao.getTaskHistoryByTaskId(taskId);
		List<CommentModel> list = new ArrayList<>();
		history.forEach(h -> {
			CommentModel model = new CommentModel();
			model.setName((String) h[0]);
			model.setComment((String) h[1]);
			model.setDesignation((String) h[2]);
			list.add(model);
			
		});
		kycDetailsModel.setHistory(list);
		responseMessage.setStatusCode("success");
		responseMessage.setData(kycDetailsModel);
		return responseMessage;
	}

	public ResponseMessage saveKycDetails(KycDetailsModel kycDetailsModel) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<Task> oldTask = kycqaDao.getTask(kycDetailsModel.getTaskId());
			boolean isVerified = false;
			if (oldTask.size() > 0) {
				TaskHistory taskHistory = makeTaskHistory(oldTask.get(0), kycDetailsModel.getUserId());

				List<SubTask> subTask = iqaDao.getSubTask(oldTask.get(0).getId());
				if (subTask.get(0) != null){
					if ((subTask.get(0).isKmlIsVerified() ==1) && (subTask.get(0).isBankIsVerified() == 1)
							&& (subTask.get(0).getRlmApprovalVerified() == 1) &&
							(subTask.get(0).getRltSampleVerified() == 1)){
						isVerified = true;
					}
				}
				responseMessage = kycqaDao.saveKycDetails(kycDetailsModel, oldTask.get(0), taskHistory, subTask.get(0), isVerified);
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseMessage.setStatusCode("error");
			responseMessage.setMessage(errorMessage.getMessage());
			return responseMessage;
		}
		return responseMessage;
	}
	
	public ResponseMessage kycCorrection(KycDetailsModel kycDetailsModel) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<Task> oldTask = kycqaDao.getTask(kycDetailsModel.getTaskId());
			if (oldTask.size() > 0) {
				TaskHistory taskHistory = makeTaskHistory(oldTask.get(0), kycDetailsModel.getUserId());
				responseMessage = kycqaDao.kycCorrection(kycDetailsModel, oldTask.get(0), taskHistory);
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseMessage.setStatusCode("error");
			responseMessage.setMessage(errorMessage.getMessage());
			return responseMessage;
		}
		return responseMessage;
	}
	
	

	private TaskHistory makeTaskHistory(Task oldTask, int userId) {
		TaskHistory taskHistory = new TaskHistory();

		taskHistory.setTaskId(oldTask.getId());
		taskHistory.setTaskDate(oldTask.getTaskDate());
		taskHistory.setStartTime(oldTask.getTaskTime());
		taskHistory.setEndTime(new Time(System.currentTimeMillis()));
		taskHistory.setTaskTypeId(oldTask.getTaskTypeId());
		taskHistory.setAssigneeId(oldTask.getAssigneeId());
		taskHistory.setStatus(oldTask.getStatus());
		taskHistory.setEntityTypeId(oldTask.getEntityTypeId());
		taskHistory.setEntityId(oldTask.getEntityId());
		return taskHistory;
	}

	@Override
	public ResponseMessage uploadFile(MultipartFile file) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			FileUploadResponseDTO _fileUploadResp = this.fileManagerUtil.uploadFile("case_kml", false,
					file.getName(), true, file, null);
			responseMessage.setData(_fileUploadResp.getPublicUrl());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return responseMessage;
	}
}
