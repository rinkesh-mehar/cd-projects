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
import in.cropdata.cdtmasterdata.model.AgriEcosystem;
import in.cropdata.cdtmasterdata.repository.AgriEcosystemRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class AgriEcosystemService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AgriEcosystemService.class);
	private static final String SERVER_ERROR_MSG = "Server Error : ";


	@Autowired
	private AgriEcosystemRepository agriEcosystemRepository;

	@Autowired
	private AclHistoryUtil approvalUtil;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	public List<AgriEcosystem> getAllAgriEcosystem() {
		try {
			List<AgriEcosystem> list = agriEcosystemRepository.findAll();

			return list;
		} catch (Exception e) {
			throw e;
		}
	}// getAllAgriEcosystem
	
	public Page<AgriEcosystem> getAgriEcosystemListByPagenation(Integer page, Integer size, String searchText){
		try {
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());
			return agriEcosystemRepository.getAgriEcosystemListByPagenation(sortedByIdDesc, searchText);
		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Agri-Ecosystem Data Found For Searched Text -> " + searchText);
		}
	}

	public ResponseMessage addAgriEcosystem(AgriEcosystem agriEcosystem) {

		try {
			agriEcosystem = agriEcosystemRepository.save(agriEcosystem);

			approvalUtil.addRecord(DBConstants.TBL_AGRI_ECOSYSTEM, agriEcosystem.getId());

			return responseMessageUtil.sendResponse(true, "Agri-Ecosystem" + APIConstants.RESPONSE_ADD_SUCCESS, "");

		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}// addAgriEcosystem

	public ResponseMessage updateAgriEcosystemById(int id, AgriEcosystem agriEcosystem) {
		try {
			Optional<AgriEcosystem> foundEco = agriEcosystemRepository.findById(id);

			if (foundEco.isPresent()) {

				agriEcosystem.setId(id);
				agriEcosystemRepository.save(agriEcosystem);

				approvalUtil.updateRecord(DBConstants.TBL_AGRI_ECOSYSTEM, agriEcosystem.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Ecosystem" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
			} else {

				return responseMessageUtil.sendResponse(false, "",
						"Agri-Ecosystem" + APIConstants.RESPONSE_UPDATE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// updateAgriEcosystemById

	public ResponseMessage primaryApproveById(int id) {
		try {
			Optional<AgriEcosystem> foundEco = agriEcosystemRepository.findById(id);

			if (foundEco.isPresent()) {

				AgriEcosystem agriEcosystem = foundEco.get();
				agriEcosystem.setStatus(APIConstants.STATUS_APPROVED);

				agriEcosystem = agriEcosystemRepository.save(agriEcosystem);

				approvalUtil.primaryApprove(DBConstants.TBL_AGRI_ECOSYSTEM, agriEcosystem.getId());
				return responseMessageUtil.sendResponse(true,
						"Agri-Ecosystem" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Ecosystem" + APIConstants.RESPONSE_PRIMARY_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage finalApproveById(int id) {
		try {
			Optional<AgriEcosystem> foundEco = agriEcosystemRepository.findById(id);

			if (foundEco.isPresent()) {

				AgriEcosystem agriEcosystem = foundEco.get();
				agriEcosystem.setStatus(APIConstants.STATUS_ACTIVE);

				agriEcosystem = agriEcosystemRepository.save(agriEcosystem);

				return responseMessageUtil.sendResponse(true,
						"Agri-Ecosystem" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Ecosystem" + APIConstants.RESPONSE_FINAL_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproveById

	public ResponseMessage deleteAgriEcosystemById(int id) {
		try {
			Optional<AgriEcosystem> foundEco = agriEcosystemRepository.findById(id);
			if (foundEco.isPresent()) {

				AgriEcosystem ecosystem = foundEco.get();
				ecosystem.setStatus(APIConstants.STATUS_DELETED);
				ecosystem = agriEcosystemRepository.save(ecosystem);

				approvalUtil.delete(DBConstants.TBL_AGRI_ECOSYSTEM, ecosystem.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Ecosystem" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");

			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Ecosystem" + APIConstants.RESPONSE_DELETE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}// deleteAgriEcosystemById

	public ResponseMessage rejectById(int id) {
		try {
			Optional<AgriEcosystem> foundEco = agriEcosystemRepository.findById(id);
			if (foundEco.isPresent()) {

				AgriEcosystem ecosystem = foundEco.get();
				ecosystem.setStatus(APIConstants.STATUS_REJECTED);
				ecosystem = agriEcosystemRepository.save(ecosystem);

				approvalUtil.delete(DBConstants.TBL_AGRI_ECOSYSTEM, ecosystem.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Ecosystem" + APIConstants.RESPONSE_REJECT_SUCCESS + id, "");

			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Ecosystem" + APIConstants.RESPONSE_REJECT_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}// rejectById

	public AgriEcosystem findAgriEcosystemById(int id) {
		try {
			Optional<AgriEcosystem> foundEcosystem = agriEcosystemRepository.findById(id);
			if (foundEcosystem.isPresent()) {
				return foundEcosystem.get();

			} else {

				throw new DoesNotExistException("Agri-Ecosystem" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findAgriEcosystemById
}// AgriEcosystemService
