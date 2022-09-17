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
import in.cropdata.cdtmasterdata.model.AgriSeason;
import in.cropdata.cdtmasterdata.model.vo.AgriSeasonVO;
import in.cropdata.cdtmasterdata.repository.AgriSeasonRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class AgriSeasonService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AgriSeasonService.class);
	private static final String SERVER_ERROR_MSG = "Server Error : ";


	@Autowired
	private AgriSeasonRepository agriSeasonRepository;

	@Autowired
	private AclHistoryUtil approvalUtil;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	public List<AgriSeason> getAllAgriSeason() {
		try {
			List<AgriSeason> list = agriSeasonRepository.findAll(Sort.by("name"));

			return list;
		} catch (Exception e) {
			throw e;
		}
	}// getAllAgriSeason
	
	public List<AgriSeasonVO> getSeasonList() {
		try {
			List<AgriSeasonVO> list = agriSeasonRepository.getSeasonList();

			return list;
		} catch (Exception e) {
			throw e;
		}
	}
	
	public Page<AgriSeason> getSeasonListByPagenation(Integer page, Integer size, String searchText){
		try {
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());
			return agriSeasonRepository.getSeasonListByPagenation(sortedByIdDesc, searchText);
		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Agri-Season Data Found For Searched Text -> " + searchText);
		}
	}


	public ResponseMessage addAgriSeason(AgriSeason agriSeason) {
		try {
			Optional<AgriSeason> foundSeason = agriSeasonRepository.findByName(agriSeason.getName());

			if (foundSeason.isPresent()) {

				return responseMessageUtil.sendResponse(false, "",
						APIConstants.RESPONSE_ALREADY_EXIST + agriSeason.getName());
			} else {
				agriSeason = agriSeasonRepository.save(agriSeason);
				approvalUtil.addRecord(DBConstants.TBL_AGRI_SEASON, agriSeason.getId());
				return responseMessageUtil.sendResponse(true, "Agri-Season" + APIConstants.RESPONSE_ADD_SUCCESS, "");
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// addAgriSeason

	public ResponseMessage updateAgriSeasonById(int id, AgriSeason agriSeason) {
		try {
			Optional<AgriSeason> foundSeason = agriSeasonRepository.findById(id);

			if (foundSeason.isPresent()) {

				agriSeason.setId(id);
				agriSeason = agriSeasonRepository.save(agriSeason);

				approvalUtil.updateRecord(DBConstants.TBL_AGRI_SEASON, agriSeason.getId());

				return responseMessageUtil.sendResponse(true, "Agri-Season" + APIConstants.RESPONSE_UPDATE_SUCCESS + id,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Season" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// updateAgriSeasonById

	public ResponseMessage primaryApproveById(int id) {
		try {
			Optional<AgriSeason> foundSeason = agriSeasonRepository.findById(id);

			if (foundSeason.isPresent()) {

				AgriSeason agriSeason = foundSeason.get();
				agriSeason.setStatus(APIConstants.STATUS_APPROVED);
				agriSeason = agriSeasonRepository.save(agriSeason);

				approvalUtil.primaryApprove(DBConstants.TBL_AGRI_SEASON, agriSeason.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Season" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Season" + APIConstants.RESPONSE_DOES_NOT_EXIST);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage finalApproveById(int id) {
		try {
			Optional<AgriSeason> foundSeason = agriSeasonRepository.findById(id);

			if (foundSeason.isPresent()) {

				AgriSeason agriSeason = foundSeason.get();
				agriSeason.setStatus(APIConstants.STATUS_ACTIVE);
				agriSeason = agriSeasonRepository.save(agriSeason);

				approvalUtil.finalApprove(DBConstants.TBL_AGRI_SEASON, agriSeason.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Season" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Season" + APIConstants.RESPONSE_DOES_NOT_EXIST);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproveById

	public ResponseMessage deleteAgriSeasonById(int id) {
		try {
			Optional<AgriSeason> foundSeason = agriSeasonRepository.findById(id);

			if (foundSeason.isPresent()) {

				AgriSeason agriSeason = foundSeason.get();
				agriSeason.setStatus(APIConstants.STATUS_DELETED);
				agriSeason = agriSeasonRepository.save(agriSeason);

				approvalUtil.delete(DBConstants.TBL_AGRI_SEASON, agriSeason.getId());

				return responseMessageUtil.sendResponse(true, "Agri-Season" + APIConstants.RESPONSE_DELETE_SUCCESS + id,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Season" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// deleteAgriSeasonById
	
	public ResponseMessage rejectById(int id) {
		try {
			Optional<AgriSeason> foundSeason = agriSeasonRepository.findById(id);
			
			if (foundSeason.isPresent()) {
				
				AgriSeason agriSeason = foundSeason.get();
				agriSeason.setStatus(APIConstants.STATUS_REJECTED);
				agriSeason = agriSeasonRepository.save(agriSeason);
				
				approvalUtil.delete(DBConstants.TBL_AGRI_SEASON, agriSeason.getId());
				
				return responseMessageUtil.sendResponse(true, "Agri-Season" + APIConstants.RESPONSE_REJECT_SUCCESS + id,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Season" + APIConstants.RESPONSE_REJECT_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// rejectById

	public AgriSeason findAgriSeasonById(int id) {
		try {
			Optional<AgriSeason> foundSeason = agriSeasonRepository.findById(id);
			if (foundSeason.isPresent()) {
				return foundSeason.get();
			} else {
				throw new DoesNotExistException("Agri-Season" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findAgriSeasonById
	
	public List<AgriSeason> getSeasonByStateCode(int stateCode) {

		try {
			List<AgriSeason> seasonList = agriSeasonRepository.getSeasonByStateCode(stateCode);

			return seasonList;

		} catch (Exception e) {
			throw e;
		}
	}
}// AgriSeasonService
