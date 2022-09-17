package in.cropdata.cdtmasterdata.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import in.cropdata.cdtmasterdata.constants.APIConstants;
import in.cropdata.cdtmasterdata.constants.DBConstants;
import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.model.AgriGeneralCommodity;
import in.cropdata.cdtmasterdata.repository.AgriGeneralCommodityRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class AgriGeneralCommodityService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AgriGeneralCommodityService.class);
	private static final String SERVER_ERROR_MSG = "Server Error : ";


	@Autowired
	private AgriGeneralCommodityRepository agriGeneralCommodityRepository;

	@Autowired
	private AclHistoryUtil approvalUtil;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	public List<AgriGeneralCommodity> getAllAgriGeneralCommodity() {

		try {
			List<AgriGeneralCommodity> list = agriGeneralCommodityRepository.findAll(Sort.by("name"));

			return list;
		} catch (Exception e) {
			throw e;
		}

	}// getAllAgriGeneralCommodity
	
	public Page<AgriGeneralCommodity> getGeneralCommodityListByPagenation(Integer page, Integer size, String searchText){
		try {
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());
			return agriGeneralCommodityRepository.getGeneralCommodityListByPagenation(sortedByIdDesc, searchText);
		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Agri-GeneralCommodity Data Found For Searched Text -> " + searchText);
		}
	}

	public ResponseMessage addAgriGeneralCommodity(AgriGeneralCommodity agriGeneralCommodity) {

		try {
			agriGeneralCommodity = agriGeneralCommodityRepository.save(agriGeneralCommodity);

			approvalUtil.addRecord(DBConstants.TBL_AGRI_GENERAL_COMMODITY, agriGeneralCommodity.getId());

			return responseMessageUtil.sendResponse(true, "Agri-GeneralCommodity" + APIConstants.RESPONSE_ADD_SUCCESS,
					"");

		} catch (DataIntegrityViolationException e) {
			LOGGER.error("DataIntegrityViolationException : ", e);
			return responseMessageUtil.sendResponse(false, "", "Duplicate entry for Agri-GeneralCommodity " + agriGeneralCommodity.getName());
		}catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// addAgriGeneralCommodity

	public ResponseMessage updateAgriGeneralCommodityById(int id, AgriGeneralCommodity agriGeneralCommodity) {
		try {
			Optional<AgriGeneralCommodity> foundGeneralCommodity = agriGeneralCommodityRepository.findById(id);

			if (foundGeneralCommodity.isPresent()) {

				agriGeneralCommodity.setId(id);
				agriGeneralCommodity = agriGeneralCommodityRepository.save(agriGeneralCommodity);

				approvalUtil.updateRecord(DBConstants.TBL_AGRI_GENERAL_COMMODITY, agriGeneralCommodity.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-GeneralCommodity" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");

			} else {

				return responseMessageUtil.sendResponse(false, "",
						"Agri-GeneralCommodity" + APIConstants.RESPONSE_UPDATE_ERROR + id);
			}
		} catch (DataIntegrityViolationException e) {
			LOGGER.error("DataIntegrityViolationException : ", e);
			return responseMessageUtil.sendResponse(false, "", "Duplicate entry for Agri-GeneralCommodity " + agriGeneralCommodity.getName());
		}catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// updateAgriGeneralCommodityById

	public ResponseMessage primrayApproveById(int id) {
		try {
			Optional<AgriGeneralCommodity> foundGeneralCommodity = agriGeneralCommodityRepository.findById(id);

			if (foundGeneralCommodity.isPresent()) {

				AgriGeneralCommodity generalCommodity = foundGeneralCommodity.get();
				generalCommodity.setStatus(APIConstants.STATUS_APPROVED);

				generalCommodity = agriGeneralCommodityRepository.save(generalCommodity);

				approvalUtil.primaryApprove(DBConstants.TBL_AGRI_GENERAL_COMMODITY, generalCommodity.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-GeneralCommodity" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");

			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-GeneralCommodity" + APIConstants.RESPONSE_PRIMARY_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primrayApproveById

	public ResponseMessage finalApproveById(int id) {
		try {
			Optional<AgriGeneralCommodity> foundGeneralCommodity = agriGeneralCommodityRepository.findById(id);
			if (foundGeneralCommodity.isPresent()) {

				AgriGeneralCommodity generalCommodity = foundGeneralCommodity.get();
				generalCommodity.setStatus(APIConstants.STATUS_ACTIVE);

				generalCommodity = agriGeneralCommodityRepository.save(generalCommodity);

				approvalUtil.finalApprove(DBConstants.TBL_AGRI_GENERAL_COMMODITY, generalCommodity.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-GeneralCommodity" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");

			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-GeneralCommodity" + APIConstants.RESPONSE_FINAL_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproveById

	public ResponseMessage deleteAgriGeneralCommodityById(int id) {
		try {
			Optional<AgriGeneralCommodity> foundGeneralCommodity = agriGeneralCommodityRepository.findById(id);
			if (foundGeneralCommodity.isPresent()) {

				AgriGeneralCommodity generalCommodity = foundGeneralCommodity.get();

				generalCommodity.setStatus(APIConstants.STATUS_DELETED);

				generalCommodity = agriGeneralCommodityRepository.save(generalCommodity);

				approvalUtil.delete(DBConstants.TBL_AGRI_GENERAL_COMMODITY, generalCommodity.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-GeneralCommodity" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-GeneralCommodity" + APIConstants.RESPONSE_DELETE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// deleteAgriGeneralCommodityById

	public ResponseMessage rejectById(int id) {
		try {
			Optional<AgriGeneralCommodity> foundGeneralCommodity = agriGeneralCommodityRepository.findById(id);
			if (foundGeneralCommodity.isPresent()) {

				AgriGeneralCommodity generalCommodity = foundGeneralCommodity.get();

				generalCommodity.setStatus(APIConstants.STATUS_REJECTED);

				generalCommodity = agriGeneralCommodityRepository.save(generalCommodity);

				approvalUtil.delete(DBConstants.TBL_AGRI_GENERAL_COMMODITY, generalCommodity.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-GeneralCommodity" + APIConstants.RESPONSE_REJECT_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-GeneralCommodity" + APIConstants.RESPONSE_REJECT_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// rejectById

	public AgriGeneralCommodity findAgriGeneralCommodityById(int id) {
		try {
			Optional<AgriGeneralCommodity> foundGeneralCommodity = agriGeneralCommodityRepository.findById(id);
			if (foundGeneralCommodity.isPresent()) {
				return foundGeneralCommodity.get();

			} else {
				throw new DoesNotExistException("Agri-GeneralCommodity" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findAgriGeneralCommodityById
}
