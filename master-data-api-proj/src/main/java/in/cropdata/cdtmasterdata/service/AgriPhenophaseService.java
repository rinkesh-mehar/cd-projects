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
import in.cropdata.cdtmasterdata.model.AgriPhenophase;
import in.cropdata.cdtmasterdata.model.AgriPlantPart;
import in.cropdata.cdtmasterdata.repository.AgriPhenophaseRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class AgriPhenophaseService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AgriPhenophaseService.class);
	private static final String SERVER_ERROR_MSG = "Server Error : ";

	@Autowired
	private AgriPhenophaseRepository agriPhenophaseRepository;

	@Autowired
	private AclHistoryUtil approvalUtil;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	public List<AgriPhenophase> getAllAgriPheonphase() {
		try {
			List<AgriPhenophase> list = agriPhenophaseRepository.findAll(Sort.by("name"));

			return list;
		} catch (Exception e) {
			throw e;
		}
	}// getAllAgriPheonphase
	
	
	public Page<AgriPhenophase> getAllAgriPhenophasePeginated(int page, int size , String searchText) {
		try {
//			searchText = "%"+searchText+"% ";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());
			Page<AgriPhenophase> phenophaseList = agriPhenophaseRepository.findAllByNameIgnoreCaseContaining(sortedByIdDesc,searchText);

			return phenophaseList;
		} catch (Exception e) {
			throw e;
		}
	}// getAllAgriPhenophasePeginated
	
	public Page<AgriPhenophase> getPhenophaseListByPagenation(Integer page, Integer size, String searchText){
		try {
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());
			return agriPhenophaseRepository.getPhenophaseListByPagenation(sortedByIdDesc, searchText);
		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Agri-Phenophase Data Found For Searched Text -> " + searchText);
		}
	}

	public ResponseMessage addAgriPhenophase(AgriPhenophase agriPhenophase) {

		try {
			agriPhenophase = agriPhenophaseRepository.save(agriPhenophase);

			approvalUtil.addRecord(DBConstants.TBL_AGRI_PHENOPHASE, agriPhenophase.getId());

			return responseMessageUtil.sendResponse(true, "Agri-Phenophase" + APIConstants.RESPONSE_ADD_SUCCESS, "");

		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// addAgriPhenophase

	public ResponseMessage updateAgriPhenophaseById(int id, AgriPhenophase agriPhenophase) {
		try {
			Optional<AgriPhenophase> foundAgriPhenophase = agriPhenophaseRepository.findById(id);

			if (foundAgriPhenophase.isPresent()) {

				agriPhenophase.setId(id);
				agriPhenophaseRepository.save(agriPhenophase);

				approvalUtil.updateRecord(DBConstants.TBL_AGRI_PHENOPHASE, agriPhenophase.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Phenophase" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Phenophase" + APIConstants.RESPONSE_UPDATE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// updateAgriPhenophaseById

	public ResponseMessage primaryApproveById(int id) {
		try {
			Optional<AgriPhenophase> foundAgriPhenophase = agriPhenophaseRepository.findById(id);

			if (foundAgriPhenophase.isPresent()) {

				AgriPhenophase agriPhenophase = foundAgriPhenophase.get();
				agriPhenophase.setStatus(APIConstants.STATUS_APPROVED);
				agriPhenophase = agriPhenophaseRepository.save(agriPhenophase);

				approvalUtil.primaryApprove(DBConstants.TBL_AGRI_PHENOPHASE, agriPhenophase.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Phenophase" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Phenophase" + APIConstants.RESPONSE_PRIMARY_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage finalApproveById(int id) {
		try {
			Optional<AgriPhenophase> foundAgriPhenophase = agriPhenophaseRepository.findById(id);

			if (foundAgriPhenophase.isPresent()) {

				AgriPhenophase phenophase = foundAgriPhenophase.get();
				phenophase.setStatus(APIConstants.STATUS_ACTIVE);

				phenophase = agriPhenophaseRepository.save(phenophase);

				approvalUtil.updateRecord(DBConstants.TBL_AGRI_PHENOPHASE, phenophase.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Phenophase" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Phenophase" + APIConstants.RESPONSE_FINAL_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproveById

	public ResponseMessage deleteAgriPhenophaseById(int id) {
		try {
			Optional<AgriPhenophase> foundAgriPhenophase = agriPhenophaseRepository.findById(id);
			if (foundAgriPhenophase.isPresent()) {

				AgriPhenophase phenophase = foundAgriPhenophase.get();
				phenophase.setStatus(APIConstants.STATUS_DELETED);

				phenophase = agriPhenophaseRepository.save(phenophase);

				approvalUtil.delete(DBConstants.TBL_AGRI_PHENOPHASE, phenophase.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Phenophase" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Phenophase" + APIConstants.RESPONSE_DELETE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// deleteAgriPhenophaseById

	public ResponseMessage rejectById(int id) {
		try {
			Optional<AgriPhenophase> foundAgriPhenophase = agriPhenophaseRepository.findById(id);
			if (foundAgriPhenophase.isPresent()) {

				AgriPhenophase phenophase = foundAgriPhenophase.get();
				phenophase.setStatus(APIConstants.STATUS_REJECTED);

				phenophase = agriPhenophaseRepository.save(phenophase);

				approvalUtil.delete(DBConstants.TBL_AGRI_PHENOPHASE, phenophase.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Phenophase" + APIConstants.RESPONSE_REJECT_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Phenophase" + APIConstants.RESPONSE_REJECT_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// rejectById

	public AgriPhenophase findAgriPhenophaseById(int id) {
		try {
			Optional<AgriPhenophase> foundPhenophase = agriPhenophaseRepository.findById(id);

			if (foundPhenophase.isPresent()) {
				return foundPhenophase.get();
			} else {

				throw new DoesNotExistException("Agri-Phenophase" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findAgriPhenophaseById

}// AgriPhenophaseService
