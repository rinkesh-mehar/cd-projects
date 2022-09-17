package in.cropdata.cdtmasterdata.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.cropdata.cdtmasterdata.constants.APIConstants;
import in.cropdata.cdtmasterdata.constants.DBConstants;
import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.model.AgriYieldCorrectionCriteria;
import in.cropdata.cdtmasterdata.repository.AgriYieldCorrectionCriteriaRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class AgriYieldCorrectionCriteriaService {
	
	@Autowired
	private AgriYieldCorrectionCriteriaRepository agriYieldCorrectionCriteriaRepository;
	
	@Autowired
	private AclHistoryUtil approvalUtil;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;
	
	
	public List<AgriYieldCorrectionCriteria> getAllAgriYieldCorrectionCriteria() {
		return agriYieldCorrectionCriteriaRepository.findAll();
	}// getAllAgriYieldCorrectionCriteria

	public ResponseMessage addAgriYieldCorrectionCriteria(AgriYieldCorrectionCriteria agriYieldCorrectionCriteria) {

		try {

			agriYieldCorrectionCriteria = agriYieldCorrectionCriteriaRepository.save(agriYieldCorrectionCriteria);

			approvalUtil.addRecord(DBConstants.TBL_AGRI_YIELD_CORRECTION_CRITERIA, agriYieldCorrectionCriteria.getId());

			return responseMessageUtil.sendResponse(true, "Agri-YieldCorrectionCriteria" + APIConstants.RESPONSE_ADD_SUCCESS, "");

		} catch (Exception e) {

			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}// addAllAgriYieldCorrectionCriteria

	public ResponseMessage updateAgriYieldCorrectionCriteriaById(int id, AgriYieldCorrectionCriteria agriYieldCorrectionCriteria) {

		try {
			Optional<AgriYieldCorrectionCriteria> foundVareity = agriYieldCorrectionCriteriaRepository.findById(id);

			if (foundVareity.isPresent()) {
				agriYieldCorrectionCriteria.setId(id);

				agriYieldCorrectionCriteria = agriYieldCorrectionCriteriaRepository.save(agriYieldCorrectionCriteria);

				approvalUtil.updateRecord(DBConstants.TBL_AGRI_YIELD_CORRECTION_CRITERIA, agriYieldCorrectionCriteria.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-YieldCorrectionCriteria" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-YieldCorrectionCriteria" + APIConstants.RESPONSE_UPDATE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// updateAgriYieldCorrectionCriteriaById

//	public ResponseMessage primaryApproveById(int id) {
//
//		try {
//			Optional<AgriYieldCorrectionCriteria> foundAgriYieldCorrectionCriteria = agriYieldCorrectionCriteriaRepository.findById(id);
//
//			if (foundAgriYieldCorrectionCriteria.isPresent()) {
//
//				AgriYieldCorrectionCriteria agriYieldCorrectionCriteria = foundAgriYieldCorrectionCriteria.get();
//				agriYieldCorrectionCriteria.setStatus(APIConstants.STATUS_APPROVED);
//
//				agriYieldCorrectionCriteria = agriYieldCorrectionCriteriaRepository.save(agriYieldCorrectionCriteria);
//
//				approvalUtil.primaryApprove(DBConstants.TBL_AGRI_YIELD_CORRECTION_CRITERIA, agriYieldCorrectionCriteria.getId());
//
//				return responseMessageUtil.sendResponse(true,
//						"Agri-YieldCorrectionCriteria" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");
//
//			} else {
//				return responseMessageUtil.sendResponse(false, "",
//						"Agri-YieldCorrectionCriteria" + APIConstants.RESPONSE_PRIMARY_APPROVAL_ERROR);
//			}
//		} catch (Exception e) {
//			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
//		}
//	}// primaryApproveById

//	public ResponseMessage finalApproveById(int id) {
//
//		try {
//			Optional<AgriYieldCorrectionCriteria> foundAgriYieldCorrectionCriteria = agriYieldCorrectionCriteriaRepository.findById(id);
//
//			if (foundAgriYieldCorrectionCriteria.isPresent()) {
//
//				AgriYieldCorrectionCriteria agriYieldCorrectionCriteria = foundAgriYieldCorrectionCriteria.get();
//				agriYieldCorrectionCriteria.setStatus(APIConstants.STATUS_ACTIVE);
//
//				agriYieldCorrectionCriteria = agriYieldCorrectionCriteriaRepository.save(agriYieldCorrectionCriteria);
//
//				approvalUtil.finalApprove(DBConstants.TBL_AGRI_YIELD_CORRECTION_CRITERIA, agriYieldCorrectionCriteria.getId());
//
//				return responseMessageUtil.sendResponse(true,
//						"Agri-YieldCorrectionCriteria" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
//			} else {
//				return responseMessageUtil.sendResponse(false, "",
//						"Agri-YieldCorrectionCriteria" + APIConstants.RESPONSE_FINAL_APPROVAL_ERROR);
//			}
//		} catch (Exception e) {
//			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
//		}
//	}// finalApproverById

//	public ResponseMessage deleteAgriYieldCorrectionCriteriaById(int id) {
//		try {
//			Optional<AgriYieldCorrectionCriteria> found = agriYieldCorrectionCriteriaRepository.findById(id);
//			if (found.isPresent()) {
//
//				AgriYieldCorrectionCriteria agriYieldCorrectionCriteria = found.get();
//				agriYieldCorrectionCriteria.setStatus(APIConstants.STATUS_DELETED);
//
//				agriYieldCorrectionCriteria = agriYieldCorrectionCriteriaRepository.save(agriYieldCorrectionCriteria);
//
//				approvalUtil.delete(DBConstants.TBL_AGRI_YIELD_CORRECTION_CRITERIA, agriYieldCorrectionCriteria.getId());
//
//				return responseMessageUtil.sendResponse(true,
//						"Agri-YieldCorrectionCriteria" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");
//			} else {
//
//				return responseMessageUtil.sendResponse(false, "",
//						"Agri-YieldCorrectionCriteria" + APIConstants.RESPONSE_DELETE_ERROR + id);
//			}
//		} catch (Exception e) {
//			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
//		}
//	}// deleteAgriYieldCorrectionCriteriaById

	public AgriYieldCorrectionCriteria findAgriYieldCorrectionCriteriaById(int id) {
		try {
			Optional<AgriYieldCorrectionCriteria> foundYieldCorrectionCriteria = agriYieldCorrectionCriteriaRepository.findById(id);
			if (foundYieldCorrectionCriteria.isPresent()) {
				return foundYieldCorrectionCriteria.get();
			} else {
				throw new DoesNotExistException("Agri-YieldCorrectionCriteria" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findAgriYieldCorrectionCriteriaById

//	public ResponseMessage rejectById(int id) {
//		try {
//			Optional<AgriYieldCorrectionCriteria> foundAgriYieldCorrectionCriteria = agriYieldCorrectionCriteriaRepository.findById(id);
//
//			if (foundAgriYieldCorrectionCriteria.isPresent()) {
//
//				AgriYieldCorrectionCriteria agriYieldCorrectionCriteria = foundAgriYieldCorrectionCriteria.get();
//				agriYieldCorrectionCriteria.setStatus(APIConstants.STATUS_REJECTED);
//				agriYieldCorrectionCriteria = agriYieldCorrectionCriteriaRepository.save(agriYieldCorrectionCriteria);
//
//				approvalUtil.finalApprove(DBConstants.TBL_AGRI_YIELD_CORRECTION_CRITERIA, agriYieldCorrectionCriteria.getId());
//
//				return responseMessageUtil.sendResponse(true, "Agri-YieldCorrectionCriteria" + APIConstants.RESPONSE_REJECT_SUCCESS,
//						"");
//			} else {
//				return responseMessageUtil.sendResponse(false, "",
//						"Agri-YieldCorrectionCriteria" + APIConstants.RESPONSE_REJECT_ERROR);
//			}
//		} catch (Exception e) {
//			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
//		}
//	}// rejectById
}//AgriYieldCorrectionCriteriaService
