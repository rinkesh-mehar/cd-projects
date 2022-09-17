package in.cropdata.cdtmasterdata.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.cropdata.cdtmasterdata.constants.APIConstants;
import in.cropdata.cdtmasterdata.constants.DBConstants;
import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.dto.interfaces.TaskTypeSpecificationsInfDto;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.model.TaskTypeSpecifications;
import in.cropdata.cdtmasterdata.model.TaskTypeSpecificationsMissing;
import in.cropdata.cdtmasterdata.repository.TaskTypeSpecificationsMissingRepository;
import in.cropdata.cdtmasterdata.repository.TaskTypeSpecificationsRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class RegionalTaskTypeSpecificationsService {

	@Autowired
	private TaskTypeSpecificationsRepository taskTypeSpecificationsRepository;
	
	@Autowired
	private TaskTypeSpecificationsMissingRepository taskTypeSpecificationsMissingRepository;

	@Autowired
	private AclHistoryUtil approvalUtil;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	public List<Map<String, Object>> getAllRegionTaskTypeSpecification() {
		try {
			List<Map<String, Object>> list = taskTypeSpecificationsRepository.getRegionalTaskTypeSpecificationList();

			return list;
		} catch (Exception e) {
			throw e;
		}
	}// get All Region Task Type Specification List

	public Page<TaskTypeSpecificationsInfDto> getAllRegionalTaskTypeSpecificationPaginated(int page, int size,
			String searchText, int isValid,String missing) {
		try {
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").ascending());

			Page<TaskTypeSpecificationsInfDto> seasonList = null;

			if("0".equals(missing)) {
				
			if (isValid == 0) {
				seasonList = taskTypeSpecificationsRepository.getRegionalTaskTypeSpecificationListInvalidated(sortedByIdDesc, searchText);
			} else {
				seasonList = taskTypeSpecificationsRepository.getRegionalTaskTypeSpecificationList(sortedByIdDesc, searchText);
			}
			}else {
				
				if (isValid == 0) {
					seasonList = taskTypeSpecificationsRepository.getRegionalTaskTypeSpecificationMissingListInvalidated(sortedByIdDesc, searchText);
				} else {
					seasonList = taskTypeSpecificationsRepository.getRegionalTaskTypeSpecificationMissingList(sortedByIdDesc, searchText);
				}	
			}

			return seasonList;
		} catch (Exception e) {
			throw e;
		}
	}// get All Regional Task Type Specification Paginated

	public ResponseMessage addTaskTypeSpecifications(TaskTypeSpecifications taskTypeSpecifications) {
		try {
			taskTypeSpecifications = taskTypeSpecificationsRepository.save(taskTypeSpecifications);
			approvalUtil.addRecord(DBConstants.TBL__TASK_TYPE_SPECIFICATION, taskTypeSpecifications.getId());
			return responseMessageUtil.sendResponse(true,
					"Region-TaskTypeSpecifications" + APIConstants.RESPONSE_ADD_SUCCESS, "");
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// addAlltaskTypeSpecifications

	public ResponseMessage updateTaskTypeSpecificationsById(int id, TaskTypeSpecifications taskTypeSpecifications) {
		try {
			Optional<TaskTypeSpecifications> foundTaskTypeSpecification = taskTypeSpecificationsRepository.findById(id);
			if (foundTaskTypeSpecification.isPresent()) {
				taskTypeSpecifications.setId(id);
				taskTypeSpecifications = taskTypeSpecificationsRepository.save(taskTypeSpecifications);
				approvalUtil.updateRecord(DBConstants.TBL__TASK_TYPE_SPECIFICATION, taskTypeSpecifications.getId());
				return responseMessageUtil.sendResponse(true,
						"Region-TaskTypeSpecifications" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Region-TaskTypeSpecifications" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// updatetaskTypeSpecificationsById

	public ResponseMessage primaryApproveById(int id) {
		try {
			Optional<TaskTypeSpecifications> foundTaskTypeSpecification = taskTypeSpecificationsRepository.findById(id);
			if (foundTaskTypeSpecification.isPresent()) {
				TaskTypeSpecifications taskTypeSpecifications = foundTaskTypeSpecification.get();
				taskTypeSpecifications.setStatus(APIConstants.STATUS_APPROVED);
				taskTypeSpecifications = taskTypeSpecificationsRepository.save(taskTypeSpecifications);
				approvalUtil.primaryApprove(DBConstants.TBL__TASK_TYPE_SPECIFICATION, taskTypeSpecifications.getId());
				return responseMessageUtil.sendResponse(true,
						"Region-TaskTypeSpecifications" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Region-TaskTypeSpecifications" + APIConstants.RESPONSE_NO_RECORD_FOUND);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage finalApproveById(int id) {
		try {
			Optional<TaskTypeSpecifications> foundTaskTypeSpecification = taskTypeSpecificationsRepository.findById(id);
			if (foundTaskTypeSpecification.isPresent()) {
				TaskTypeSpecifications taskTypeSpecifications = foundTaskTypeSpecification.get();
				taskTypeSpecifications.setStatus(APIConstants.STATUS_ACTIVE);
				taskTypeSpecifications = taskTypeSpecificationsRepository.save(taskTypeSpecifications);
				approvalUtil.finalApprove(DBConstants.TBL__TASK_TYPE_SPECIFICATION, taskTypeSpecifications.getId());
				return responseMessageUtil.sendResponse(true,
						"Region-TaskTypeSpecifications" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Region-TaskTypeSpecifications" + APIConstants.RESPONSE_NO_RECORD_FOUND);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproveById

	public ResponseMessage deleteTaskTypeSpecificationsById(int id) {
		try {
			Optional<TaskTypeSpecifications> foundTaskTypeSpecification = taskTypeSpecificationsRepository.findById(id);
			if (foundTaskTypeSpecification.isPresent()) {
				TaskTypeSpecifications taskTypeSpecifications = foundTaskTypeSpecification.get();
				taskTypeSpecifications.setStatus(APIConstants.STATUS_DELETED);
				taskTypeSpecifications = taskTypeSpecificationsRepository.save(taskTypeSpecifications);
				approvalUtil.delete(DBConstants.TBL__TASK_TYPE_SPECIFICATION, taskTypeSpecifications.getId());
				return responseMessageUtil.sendResponse(true,
						"Region-TaskTypeSpecifications" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Region-TaskTypeSpecifications" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// deletetaskTypeSpecificationsById

	public TaskTypeSpecifications findTaskTypeSpecificationsById(int id) {
		try {
			Optional<TaskTypeSpecifications> foundTaskTypeSpecification = taskTypeSpecificationsRepository.findById(id);
			if (foundTaskTypeSpecification.isPresent()) {
				return foundTaskTypeSpecification.get();
			} else {
				throw new DoesNotExistException(
						"Region-TaskTypeSpecifications " + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findRegionTaskTypeSpecificationsById

	public ResponseMessage rejectById(int id) {
		try {
			Optional<TaskTypeSpecifications> foundTaskTypeSpecification = taskTypeSpecificationsRepository.findById(id);
			if (foundTaskTypeSpecification.isPresent()) {
				TaskTypeSpecifications TaskTypeSpecifications = foundTaskTypeSpecification.get();
				TaskTypeSpecifications.setStatus(APIConstants.STATUS_REJECTED);
				TaskTypeSpecifications = taskTypeSpecificationsRepository.save(TaskTypeSpecifications);
				approvalUtil.finalApprove(DBConstants.TBL__TASK_TYPE_SPECIFICATION, TaskTypeSpecifications.getId());
				return responseMessageUtil.sendResponse(true,
						"Region-TaskTypeSpecifications " + APIConstants.RESPONSE_REJECT_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Region-TaskTypeSpecifications " + APIConstants.RESPONSE_REJECT_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// rejectById

	public List<Map<String, Object>> getStateList() {
		return taskTypeSpecificationsRepository.getStateList();
	}

	public List<Map<String, Object>> getSeasonList() {
		return taskTypeSpecificationsRepository.getSeasonList();
	}

	public List<Map<String, Object>> getCommodityList() {
		return taskTypeSpecificationsRepository.getCommodityList();
	}

	public List<Map<String, Object>> getVarietyList(Integer commodityID) {
		return taskTypeSpecificationsRepository.getVarietyList(commodityID);
	}

	public List<Map<String, Object>> getPhenophaseList(Integer commodityID, Integer varietyID) {
		return taskTypeSpecificationsRepository.getPhenophaseList(commodityID, varietyID);
	}
	
	@Transactional
	public ResponseMessage moveToMaster(int id) {
		try {
			Optional<TaskTypeSpecificationsMissing> foundMissingCommodity = taskTypeSpecificationsMissingRepository.findById(id);

			if (foundMissingCommodity.isPresent()) {
				TaskTypeSpecifications regionalCommodity = new TaskTypeSpecifications();
				TaskTypeSpecificationsMissing taskTypeSpecificationsMissing = foundMissingCommodity.get();
				
				regionalCommodity.setStateID(taskTypeSpecificationsMissing.getStateID());
				regionalCommodity.setSeasonID(taskTypeSpecificationsMissing.getSeasonID());
				regionalCommodity.setCommodityID(taskTypeSpecificationsMissing.getCommodityID());
				regionalCommodity.setVarietyID(taskTypeSpecificationsMissing.getVarietyID());
				regionalCommodity.setPhenophaseID(taskTypeSpecificationsMissing.getPhenophaseID());
				regionalCommodity.setPushBackLimit(taskTypeSpecificationsMissing.getPushBackLimit());
				regionalCommodity.setPriority(taskTypeSpecificationsMissing.getPriority());
				regionalCommodity.setTaskTime(taskTypeSpecificationsMissing.getTaskTime());
				regionalCommodity.setTaskTypeID(taskTypeSpecificationsMissing.getTaskTypeID());
				regionalCommodity.setSpotDependency(taskTypeSpecificationsMissing.getSpotDependency());
				regionalCommodity.setStatus(taskTypeSpecificationsMissing.getStatus());
				
				TaskTypeSpecifications savedTaskTypeSpecifications = taskTypeSpecificationsRepository.save(regionalCommodity);
				
				taskTypeSpecificationsMissingRepository.deleteById(id);


				approvalUtil.addRecord(DBConstants.TBL__TASK_TYPE_SPECIFICATION, savedTaskTypeSpecifications.getId());

				return responseMessageUtil.sendResponse(true,
						"Region-TaskTypeSpecifications-Missing" + APIConstants.RESPONSE_MOVE_TO_MASTER_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Region-TaskTypeSpecifications-Missing" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}

}
