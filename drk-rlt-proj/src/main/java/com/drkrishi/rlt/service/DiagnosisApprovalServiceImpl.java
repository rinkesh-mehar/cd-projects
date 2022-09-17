package com.drkrishi.rlt.service;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import com.drkrishi.rlt.entity.*;
import com.drkrishi.rlt.repository.SubtaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.drkrishi.rlt.dao.DiagnosisApprovalDao;
import com.drkrishi.rlt.model.DiagnosisApprovalModel;
import com.drkrishi.rlt.model.ResponseMessage;

@Service
public class DiagnosisApprovalServiceImpl implements DiagnosisApprovalService {

	@Autowired
	private DiagnosisApprovalDao diagnosisApprovalDao;

	@Autowired
	SubtaskRepository subtaskRepository;

	private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("dd/MM/yyyy");

	@Override
	public ResponseMessage getDiagnosisApprovalList(Integer userId, String taskStatus, String startDateStr, String endDateStr,
			String barcode) {
		ResponseMessage response = new ResponseMessage();
		try {
			final List<DiagnosisApprovalModel> modelList = new ArrayList<>();

			Date startDate = (startDateStr != null && !startDateStr.isBlank())
					? new Date(DATE_FORMATTER.parse(startDateStr).getTime())
					: null;
			Date endDate = (endDateStr != null && !endDateStr.isBlank())
					? new Date(DATE_FORMATTER.parse(endDateStr).getTime())
					: null;
					
			Users user = diagnosisApprovalDao.getUserById(userId);
			Integer regionId = user.getRegionId();

			if (!taskStatus.equalsIgnoreCase("pending")) {
				List<ViewTaskHistoryInfo> taskHistoryInfo;
				if(taskStatus.equalsIgnoreCase("approved")) {
					taskHistoryInfo = diagnosisApprovalDao.getRlmApprovedReassignRecords(regionId, startDate, endDate, 15);
					List<ViewTaskHistoryInfo> badSampleList = diagnosisApprovalDao.getRlmApprovedReassignRecords(regionId, startDate, endDate, 9);
					taskHistoryInfo.addAll(badSampleList);
				} else {
					taskHistoryInfo = diagnosisApprovalDao.getRlmApprovedReassignRecords(regionId, startDate, endDate, 6);
				}
				taskHistoryInfo.forEach(t -> {
					DiagnosisApprovalModel model = new DiagnosisApprovalModel();
//					List<Integer> commodityIds = Arrays.asList(t.getFarmerMajorCrop().split(",")).stream()
//							.map(s -> Integer.parseInt(s)).collect(Collectors.toList());
//					List<Commodity> commodityList = diagnosisApprovalDao.getCommodityListByIds(commodityIds);
					TaskHistory lastTaskHistory = diagnosisApprovalDao.getLatestTaskHistoryByTaskId(t.getTaskId());
					TaskHistory firstTaskHistory = diagnosisApprovalDao.getFirstTaskHistoryByTaskId(t.getTaskId());
					model.setCaseid(t.getEntityId());
					model.setFarmerid(t.getFarmerId());
					model.setVillage(t.getVillageName());
					model.setFarmermobilenumber(t.getFarmerMobNumber());
					model.setTaskstatus(taskStatus.equalsIgnoreCase("approved")? "approved" : "reassigned");
					model.setStatus(t.getCaseSampleStatus());
					model.setTaskId(t.getTaskId());
//					String crop = commodityList.stream().map(c -> c.getName()).collect(Collectors.joining(","));
//					model.setCrop(crop);
					model.setCrop(t.getCropName());
					if (lastTaskHistory != null) {
						model.setLevelonedate(DATE_FORMATTER.format(lastTaskHistory.getTaskDate()));
					}
					if (firstTaskHistory != null) {
						model.setReceiveddate(DATE_FORMATTER.format(firstTaskHistory.getTaskDate()));
					}
					modelList.add(model);
				});
			} else {
				List<ViewTaskInfo> taskInfo = diagnosisApprovalDao.getRlmPendingRecords(userId, regionId, startDate, endDate, barcode);
				final List<DiagnosisApprovalModel> tempList = new ArrayList<>();
				taskInfo.forEach(t -> {
					DiagnosisApprovalModel model = new DiagnosisApprovalModel();
					//model.setTaskstatus("pending");
					model.setCaseid(t.getEntityId());
					model.setFarmerid(t.getFarmerId());
					model.setVillage(t.getVillageName());
					model.setFarmermobilenumber(t.getFarmerMobNumber());
					model.setStatus(t.getCaseSampleStatus());
					model.setTaskId(t.getTaskId());
//					List<Integer> commodityIds = Arrays.asList(t.getFarmerMajorCrop().split(",")).stream()
//							.map(s -> Integer.parseInt(s)).collect(Collectors.toList());
//					List<Commodity> commodityList = diagnosisApprovalDao.getCommodityListByIds(commodityIds);
//					String crop = commodityList.stream().map(c -> c.getName()).collect(Collectors.joining(","));
//					model.setCrop(crop);
					model.setCrop(t.getCropName());
					TaskHistory lastTaskHistory = diagnosisApprovalDao.getLatestTaskHistoryByTaskId(t.getTaskId());
					TaskHistory firstTaskHistory = diagnosisApprovalDao.getFirstTaskHistoryByTaskId(t.getTaskId());
					if (lastTaskHistory != null) {
						model.setLevelonedate(DATE_FORMATTER.format(lastTaskHistory.getTaskDate()));
						model.setReceiveddate(DATE_FORMATTER.format(lastTaskHistory.getTaskDate()));
					}
//					if (firstTaskHistory != null) {
//						model.setReceiveddate(DATE_FORMATTER.format(firstTaskHistory.getTaskDate()));
//					}
					if(t.getAssigneeId() != null && t.getAssigneeId() != 0) {
						model.setTaskstatus("assigned");
						modelList.add(model);
					} else {
						model.setTaskstatus("pending");
						tempList.add(model);
					}
				});
				modelList.addAll(tempList);
			}
			if(modelList.isEmpty()) {
				response.setMessage("Record not found!");
				response.setStatusCode(204);
			} else {
				response.setMessage("Success");
				response.setStatusCode(200);
			}
			Collections.sort(modelList, new Comparator<DiagnosisApprovalModel>() {
				@Override
				public int compare(DiagnosisApprovalModel o1, DiagnosisApprovalModel o2) {
					try {
						return DATE_FORMATTER.parse(o1.getLevelonedate()).compareTo(DATE_FORMATTER.parse(o2.getLevelonedate()));
					} catch (ParseException e) {
						return 0;
					}
				}
			});
			response.setData(modelList);
		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage("Internal Server Error");
			response.setStatusCode(500);
		}

		return response;
	}

	@Override
	public ResponseMessage updateAssignee(Task task) {
		ResponseMessage response = new ResponseMessage();
		Integer count = diagnosisApprovalDao.updateAssignee(task.getId(), task.getAssigneeId());
		if(count == 1) {
			response.setStatusCode(200);
			response.setMessage("Successfully updated");
		} else {
			response.setStatusCode(400);
			response.setMessage("Invalid data");
		}
		return response;
	}

}
