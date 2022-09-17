package in.cropdata.cdtmasterdata.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import in.cropdata.cdtmasterdata.constants.APIConstants;
import in.cropdata.cdtmasterdata.constants.DBConstants;
import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.model.AgriStressType;
import in.cropdata.cdtmasterdata.repository.AgriStressTypeRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class AgriStressTypeService {

	@Autowired
	private AgriStressTypeRepository agriStressTypeRepository;

	@Autowired
	private AclHistoryUtil approvalUtil;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	public List<AgriStressType> getAllAgriStressType() {
		try {
			List<AgriStressType> list = agriStressTypeRepository.findAll(Sort.by("name"));

			return list;

		} catch (Exception e) {
			throw e;
		}
	}// getAllAgriStressType
	
	public Page<AgriStressType> getPeginatedStressTypeList(int page, int size, String searchText) {

		try {
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());

			Page<AgriStressType> stressTypeList = agriStressTypeRepository.getPeginatedStressTypeList(sortedByIdDesc,
					searchText);

			return stressTypeList;

		} catch (Exception e) {
			throw e;
		}
	}

	public ResponseMessage addAgriStressType(AgriStressType agriStressType) {

		try {
			agriStressType = agriStressTypeRepository.save(agriStressType);

			approvalUtil.addRecord(DBConstants.TBL_AGRI_STRESS_TYPE, agriStressType.getId());

			return responseMessageUtil.sendResponse(true, "Agri-Stress-Type" + APIConstants.RESPONSE_ADD_SUCCESS, "");

		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// addAllAgriStressType

	public ResponseMessage updateAgriStressTypeById(int id, AgriStressType agriStressType) {
		try {
			Optional<AgriStressType> foundVareity = agriStressTypeRepository.findById(id);

			if (foundVareity.isPresent()) {

				agriStressType.setId(id);
				agriStressType = agriStressTypeRepository.save(agriStressType);

				approvalUtil.updateRecord(DBConstants.TBL_AGRI_STRESS_TYPE, agriStressType.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Stress-Type" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Stress-Type" + APIConstants.RESPONSE_UPDATE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// updateAgriStressTypeById

	public ResponseMessage primaryApproveById(int id) {
		try {
			Optional<AgriStressType> foundStressType = agriStressTypeRepository.findById(id);

			if (foundStressType.isPresent()) {

				AgriStressType stressType = foundStressType.get();
				stressType.setStatus(APIConstants.STATUS_APPROVED);

				stressType = agriStressTypeRepository.save(stressType);

				approvalUtil.primaryApprove(DBConstants.TBL_AGRI_STRESS_TYPE, stressType.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Stress-Type" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Stress-Type" + APIConstants.RESPONSE_PRIMARY_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage finalApproveById(int id) {
		try {
			Optional<AgriStressType> foundStressType = agriStressTypeRepository.findById(id);

			if (foundStressType.isPresent()) {

				AgriStressType stressType = foundStressType.get();
				stressType.setStatus(APIConstants.STATUS_ACTIVE);

				stressType = agriStressTypeRepository.save(stressType);

				approvalUtil.finalApprove(DBConstants.TBL_AGRI_STRESS_TYPE, stressType.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Stress-Type" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Stress-Type" + APIConstants.RESPONSE_FINAL_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage deleteAgriStressTypeById(int id) {
		try {
			Optional<AgriStressType> foundStressType = agriStressTypeRepository.findById(id);

			if (foundStressType.isPresent()) {

				AgriStressType stressType = foundStressType.get();
				stressType.setStatus(APIConstants.STATUS_DELETED);

				stressType = agriStressTypeRepository.save(stressType);

				approvalUtil.delete(DBConstants.TBL_AGRI_STRESS_TYPE, stressType.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Stress-Type" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Stress-Type" + APIConstants.RESPONSE_DELETE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// deleteAgriStressTypeById

	public ResponseMessage rejectById(int id) {
		try {
			Optional<AgriStressType> foundStressType = agriStressTypeRepository.findById(id);

			if (foundStressType.isPresent()) {

				AgriStressType stressType = foundStressType.get();
				stressType.setStatus(APIConstants.STATUS_REJECTED);

				stressType = agriStressTypeRepository.save(stressType);

				approvalUtil.delete(DBConstants.TBL_AGRI_STRESS_TYPE, stressType.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Stress-Type" + APIConstants.RESPONSE_REJECT_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Stress-Type" + APIConstants.RESPONSE_REJECT_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// rejectById

	public AgriStressType findAgriStressTypeById(int id) {
		try {
			Optional<AgriStressType> foundStressType = agriStressTypeRepository.findById(id);
			if (foundStressType.isPresent()) {
				return foundStressType.get();
			} else {
				throw new DoesNotExistException("Agri-Stress-Type" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findAgriStressTypeById
}// AgriStressTypeService
