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
import in.cropdata.cdtmasterdata.model.AgriStressSeverity;
import in.cropdata.cdtmasterdata.repository.AgriStressSeverityRepositoy;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class AgriStressSeverityService {

	@Autowired
	private AgriStressSeverityRepositoy agriStressSeverityRepositoy;

	@Autowired
	private AclHistoryUtil approvalUtil;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	public List<AgriStressSeverity> getAllAgriStressServerity() {
		try {
			List<AgriStressSeverity> list = agriStressSeverityRepositoy.findAll(Sort.by("name"));

			return list;

		} catch (Exception e) {
			throw e;
		}
	}// getAllAgriStressServerity
	
	public Page<AgriStressSeverity> getPeginatedStressSeverityList(int page, int size, String searchText) {

		try {
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());

			Page<AgriStressSeverity> stressSeverityList = agriStressSeverityRepositoy.getPeginatedStressSeverityList(sortedByIdDesc,
					searchText);

			return stressSeverityList;

		} catch (Exception e) {
			throw e;
		}
	}

	public ResponseMessage addAllAgriStressServerity(AgriStressSeverity agriStressServerity) {
		try {
			agriStressServerity = agriStressSeverityRepositoy.save(agriStressServerity);

			approvalUtil.addRecord(DBConstants.TBL_AGRI_STRESS_SEVERITY, agriStressServerity.getId());

			return responseMessageUtil.sendResponse(true, "Agri-Stress-Serverity" + APIConstants.RESPONSE_ADD_SUCCESS,"");

		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// addAllAgriStressServerity

	public ResponseMessage updateAgriStressSeverityById(int id, AgriStressSeverity agriStressServerity) {
		try {
			Optional<AgriStressSeverity> foundStressServerity = agriStressSeverityRepositoy.findById(id);

			if (foundStressServerity.isPresent()) {

				agriStressServerity.setId(id);
				agriStressServerity = agriStressSeverityRepositoy.save(agriStressServerity);

				approvalUtil.updateRecord(DBConstants.TBL_AGRI_STRESS_SEVERITY, agriStressServerity.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Stress-Serverity" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Stress-Serverity" + APIConstants.RESPONSE_UPDATE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// updateAgriStressSeverityById

	public ResponseMessage primaryApproveById(int id) {
		try {
			Optional<AgriStressSeverity> foundStressServerity = agriStressSeverityRepositoy.findById(id);

			if (foundStressServerity.isPresent()) {

				AgriStressSeverity stressServerity = foundStressServerity.get();

				stressServerity.setStatus(APIConstants.STATUS_APPROVED);

				stressServerity = agriStressSeverityRepositoy.save(stressServerity);

				approvalUtil.primaryApprove(DBConstants.TBL_AGRI_STRESS_SEVERITY, stressServerity.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Stress-Serverity" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Stress-Serverity" + APIConstants.RESPONSE_PRIMARY_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage finalApproveById(int id) {
		try {
			Optional<AgriStressSeverity> foundStressServerity = agriStressSeverityRepositoy.findById(id);

			if (foundStressServerity.isPresent()) {

				AgriStressSeverity stressServerity = foundStressServerity.get();

				stressServerity.setStatus(APIConstants.STATUS_ACTIVE);

				stressServerity = agriStressSeverityRepositoy.save(stressServerity);

				approvalUtil.finalApprove(DBConstants.TBL_AGRI_STRESS_SEVERITY, stressServerity.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Stress-Serverity" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Stress-Serverity" + APIConstants.RESPONSE_FINAL_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproveById

	public ResponseMessage deleteAgriStressServerityById(int id) {
		try {
			Optional<AgriStressSeverity> foundStressServerity = agriStressSeverityRepositoy.findById(id);
			if (foundStressServerity.isPresent()) {

				AgriStressSeverity stressServerity = foundStressServerity.get();
				stressServerity.setStatus(APIConstants.STATUS_DELETED);

				stressServerity = agriStressSeverityRepositoy.save(stressServerity);

				approvalUtil.delete(DBConstants.TBL_AGRI_STRESS_SEVERITY, stressServerity.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Stress-Serverity" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Stress-Serverity" + APIConstants.RESPONSE_DELETE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// deleteAgriStressServerityById

	public ResponseMessage rejectById(int id) {
		try {
			Optional<AgriStressSeverity> foundStressServerity = agriStressSeverityRepositoy.findById(id);
			if (foundStressServerity.isPresent()) {

				AgriStressSeverity stressServerity = foundStressServerity.get();
				stressServerity.setStatus(APIConstants.STATUS_REJECTED);

				stressServerity = agriStressSeverityRepositoy.save(stressServerity);

				approvalUtil.delete(DBConstants.TBL_AGRI_STRESS_SEVERITY, stressServerity.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Stress-Serverity" + APIConstants.RESPONSE_REJECT_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Stress-Serverity" + APIConstants.RESPONSE_REJECT_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// rejectById

	public AgriStressSeverity findAgriStressSeverityById(int id) {
		try {
			Optional<AgriStressSeverity> foundStressServerity = agriStressSeverityRepositoy.findById(id);
			if (foundStressServerity.isPresent()) {
				return foundStressServerity.get();
			} else {
				throw new DoesNotExistException("Agri-Stress-Severity" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findAgriStressSeverityById
}
