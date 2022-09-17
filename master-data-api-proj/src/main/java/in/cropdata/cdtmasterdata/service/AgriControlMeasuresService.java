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
import in.cropdata.cdtmasterdata.model.AgriControlMeasures;
import in.cropdata.cdtmasterdata.model.GeneralUom;
import in.cropdata.cdtmasterdata.repository.AgriControlMeasuresRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class AgriControlMeasuresService {

	@Autowired
	private AgriControlMeasuresRepository agriStressControlMeasuresRepository;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;
	
	@Autowired
	private AclHistoryUtil approvalUtil;

	public ResponseMessage addAgriStressControlMeasures(AgriControlMeasures agriStressControlMeasures) {
		try {
			agriStressControlMeasuresRepository.save(agriStressControlMeasures);

			return responseMessageUtil.sendResponse(true, "Control Measures" + APIConstants.RESPONSE_ADD_SUCCESS,
					"");

		} catch (Exception e) {
			e.printStackTrace();
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// addGroup
	
	public Page<AgriControlMeasures> getPeginatedControlMeasuresList(int page, int size, String searchText) {

		try {
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());

			Page<AgriControlMeasures> controlMeasuresList = agriStressControlMeasuresRepository.getPeginatedControlMeasuresList(sortedByIdDesc,
					searchText);

			return controlMeasuresList;

		} catch (Exception e) {
			throw e;
		}
	}

	public List<AgriControlMeasures> getAllAgriStressControlMeasures() {
		return agriStressControlMeasuresRepository.findAll(Sort.by("name"));
	}// getAllGroup

	public ResponseMessage updateAgriStressControlMeasuresById(int id,
			AgriControlMeasures agriStressControlMeasures) {

		try {

			Optional<AgriControlMeasures> foundGroup = agriStressControlMeasuresRepository.findById(id);

			if (foundGroup.isPresent()) {

				agriStressControlMeasures.setId(id);
				agriStressControlMeasuresRepository.save(agriStressControlMeasures);

				return responseMessageUtil.sendResponse(true,
						"Control Measures" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");

			} else {

				return responseMessageUtil.sendResponse(false, "",
						"Control Measures" + APIConstants.RESPONSE_UPDATE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// updateGroupById

	public ResponseMessage deleteAgriStressControlMeasuresById(int id) {

		try {
			agriStressControlMeasuresRepository.deleteById(id);

			return responseMessageUtil.sendResponse(true,
					"Control Measures" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");

		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "",
					"Control Measures" + APIConstants.RESPONSE_DELETE_ERROR + id);
		}
	}// deleteGroupById

	public AgriControlMeasures findAgriStressControlMeasuresById(int id) {
		try {
			Optional<AgriControlMeasures> foundStressControlMeasures = agriStressControlMeasuresRepository
					.findById(id);
			if (foundStressControlMeasures.isPresent()) {
				return foundStressControlMeasures.get();
			} else {
				throw new DoesNotExistException("Control Measures" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findGroupById

	public ResponseMessage primaryApproveById(int id) {

		try {
			Optional<AgriControlMeasures> foundAgriStressControlMeasures = agriStressControlMeasuresRepository.findById(id);

			if (foundAgriStressControlMeasures.isPresent()) {

				AgriControlMeasures agriStressControlMeasures = foundAgriStressControlMeasures.get();
				agriStressControlMeasures.setStatus(APIConstants.STATUS_APPROVED);

				agriStressControlMeasures = agriStressControlMeasuresRepository.save(agriStressControlMeasures);

				approvalUtil.primaryApprove(DBConstants.TBL_AGRI__CONTROL_MEASURES, agriStressControlMeasures.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Control-Measures" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");

			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Control-Measures" + APIConstants.RESPONSE_PRIMARY_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage finalApproveById(int id) {

		try {
			Optional<AgriControlMeasures> foundAgriStressControlMeasures = agriStressControlMeasuresRepository.findById(id);

			if (foundAgriStressControlMeasures.isPresent()) {

				AgriControlMeasures agriStressControlMeasures = foundAgriStressControlMeasures.get();
				agriStressControlMeasures.setStatus(APIConstants.STATUS_ACTIVE);

				agriStressControlMeasures = agriStressControlMeasuresRepository.save(agriStressControlMeasures);

				approvalUtil.finalApprove(DBConstants.TBL_AGRI__CONTROL_MEASURES, agriStressControlMeasures.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Control-Measures" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Control-Measures" + APIConstants.RESPONSE_FINAL_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproverById
	
	public ResponseMessage rejectById(int id) {
		try {
			Optional<AgriControlMeasures> foundAgriStressControlMeasures = agriStressControlMeasuresRepository.findById(id);

			if (foundAgriStressControlMeasures.isPresent()) {

				AgriControlMeasures agriStressControlMeasures = foundAgriStressControlMeasures.get();
				agriStressControlMeasures.setStatus(APIConstants.STATUS_REJECTED);
				agriStressControlMeasures = agriStressControlMeasuresRepository.save(agriStressControlMeasures);

				approvalUtil.finalApprove(DBConstants.TBL_AGRI__CONTROL_MEASURES, agriStressControlMeasures.getId());

				return responseMessageUtil.sendResponse(true, "Agri-Control-Measures " + APIConstants.RESPONSE_REJECT_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "", "Agri-Control-Measures " + APIConstants.RESPONSE_REJECT_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// rejectById

}
