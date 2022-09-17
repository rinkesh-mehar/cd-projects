package in.cropdata.cdtmasterdata.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import in.cropdata.cdtmasterdata.model.AgriActivity;
import in.cropdata.cdtmasterdata.repository.AgriActivityRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class AgriActivityService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AgriActivityService.class);
	private static final String SERVER_ERROR_MSG = "Server Error : ";

	@Autowired
	private AgriActivityRepository agriActivityRepository;

	@Autowired
	private AclHistoryUtil approvalUtil;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	public List<AgriActivity> getAllAgriActivity() {
		return agriActivityRepository.findAll();
	}// getAllAgriActivity

	public Page<AgriActivity> getActivityListByPagenation(Integer page, Integer size, String searchText){
		try {
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());
			return agriActivityRepository.getActivityListByPagenation(sortedByIdDesc, searchText);
		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Agri-Activity Data Found For Searched Text -> " + searchText);
		}
	}
	
	public ResponseMessage addAgriActivity(AgriActivity agriActivity) {

		try {

			agriActivity = agriActivityRepository.save(agriActivity);

			approvalUtil.addRecord(DBConstants.TBL_AGRI_ACTIVITY, agriActivity.getId());

			return responseMessageUtil.sendResponse(true, "Agri-Activity" + APIConstants.RESPONSE_ADD_SUCCESS, "");

		} catch (Exception e) {

			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}// addAllAgriActivity

	public ResponseMessage updateAgriActivityById(int id, AgriActivity agriActivity) {

		try {
			Optional<AgriActivity> foundVareity = agriActivityRepository.findById(id);

			if (foundVareity.isPresent()) {
				agriActivity.setId(id);

				agriActivity = agriActivityRepository.save(agriActivity);

				approvalUtil.updateRecord(DBConstants.TBL_AGRI_ACTIVITY, agriActivity.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Activity" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Activity" + APIConstants.RESPONSE_UPDATE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// updateAgriActivityById

	public ResponseMessage primaryApproveById(int id) {

		try {
			Optional<AgriActivity> foundAgriActivity = agriActivityRepository.findById(id);

			if (foundAgriActivity.isPresent()) {

				AgriActivity agriActivity = foundAgriActivity.get();
				agriActivity.setStatus(APIConstants.STATUS_APPROVED);

				agriActivity = agriActivityRepository.save(agriActivity);

				approvalUtil.primaryApprove(DBConstants.TBL_AGRI_ACTIVITY, agriActivity.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Activity" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");

			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Activity" + APIConstants.RESPONSE_PRIMARY_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage finalApproveById(int id) {

		try {
			Optional<AgriActivity> foundAgriActivity = agriActivityRepository.findById(id);

			if (foundAgriActivity.isPresent()) {

				AgriActivity agriActivity = foundAgriActivity.get();
				agriActivity.setStatus(APIConstants.STATUS_ACTIVE);

				agriActivity = agriActivityRepository.save(agriActivity);

				approvalUtil.finalApprove(DBConstants.TBL_AGRI_ACTIVITY, agriActivity.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Activity" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Activity" + APIConstants.RESPONSE_FINAL_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproverById

	public ResponseMessage deleteAgriActivityById(int id) {
		try {
			Optional<AgriActivity> found = agriActivityRepository.findById(id);
			if (found.isPresent()) {

				AgriActivity agriActivity = found.get();
				agriActivity.setStatus(APIConstants.STATUS_DELETED);

				agriActivity = agriActivityRepository.save(agriActivity);

				approvalUtil.delete(DBConstants.TBL_AGRI_ACTIVITY, agriActivity.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Activity" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");
			} else {

				return responseMessageUtil.sendResponse(false, "",
						"Agri-Activity" + APIConstants.RESPONSE_DELETE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// deleteAgriActivityById

	public AgriActivity findAgriActivityById(int id) {
		try {
			Optional<AgriActivity> foundActivity = agriActivityRepository.findById(id);
			if (foundActivity.isPresent()) {
				return foundActivity.get();
			} else {
				throw new DoesNotExistException("Agri-Activity" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findAgriActivityById

	public ResponseMessage rejectById(int id) {
		try {
			Optional<AgriActivity> foundAgriActivity = agriActivityRepository.findById(id);

			if (foundAgriActivity.isPresent()) {

				AgriActivity agriActivity = foundAgriActivity.get();
				agriActivity.setStatus(APIConstants.STATUS_REJECTED);
				agriActivity = agriActivityRepository.save(agriActivity);

				approvalUtil.finalApprove(DBConstants.TBL_AGRI_ACTIVITY, agriActivity.getId());

				return responseMessageUtil.sendResponse(true, "Agri-Activity" + APIConstants.RESPONSE_REJECT_SUCCESS,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Activity" + APIConstants.RESPONSE_REJECT_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// rejectById

}// AgriActivityService
