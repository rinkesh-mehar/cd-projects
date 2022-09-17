package in.cropdata.cdtmasterdata.service;

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
import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.exceptions.AlreadyExistException;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.model.AgriStateCommodity;
import in.cropdata.cdtmasterdata.model.vo.AgriStateCommodityVO;
import in.cropdata.cdtmasterdata.repository.AgriStateCommodityRepository;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;
@Service
public class AgriStateCommodityService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AgriStateCommodityService.class);
	private static final String SERVER_ERROR_MSG = "Server Error : ";
	
	@Autowired
	private AgriStateCommodityRepository agriStateCommodityRepository;
	
	@Autowired
	ResponseMessageUtil responseMessageUtil;
	
	public Page<AgriStateCommodityVO> getAgriStateCommodityPagenatedList(Integer page, Integer size,
			String searchText) {
		try {
			LOGGER.info("getting all state commodity info - paginated...");
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());

			return agriStateCommodityRepository.getAgriStateCommodityPagenatedList(sortedByIdDesc, searchText);

		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No State Commodity Data Found For Searched Text -> " + searchText);
		}
	}
	
	public AgriStateCommodity getAgriStateCommodityById(Integer id) {
		try {
			LOGGER.info("getting state commodity by id...");
			return agriStateCommodityRepository.findById(id).orElse(null);
		} catch (Exception e) {
			
			throw new DoesNotExistException("State Commodity Does" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
		}
	}
	
	public ResponseMessage addStateCommodity(AgriStateCommodity agriStateCommodity) {
		LOGGER.info("Adding State Commodity...");
		try {
			Integer count = agriStateCommodityRepository.findAlreadyExistStateCommodityForAddMode(agriStateCommodity.getStateCode(),agriStateCommodity.getCommodityId());
			if(count == 0) {
				agriStateCommodityRepository.save(agriStateCommodity);
		}else if(count > 0) {
			
			throw new AlreadyExistException("Duplicate entry for State Commodity");
		}
			
			return responseMessageUtil.sendResponse(true, "State Commodity " + APIConstants.RESPONSE_ADD_SUCCESS, "");
		}catch (AlreadyExistException e) {
			return responseMessageUtil.sendResponse(false, "",e.getMessage());
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", SERVER_ERROR_MSG + e.getMessage());
		}
	}
	
	public ResponseMessage updateStateCommodity(AgriStateCommodity agriStateCommodity, Integer id) {
		LOGGER.info("Updation State Commodity...");
		try {
			Optional<AgriStateCommodity> optionalStressStage = agriStateCommodityRepository.findById(id);
			if(optionalStressStage.isPresent()) {
				Integer count = agriStateCommodityRepository.findAlreadyExistStateCommodityForEditMode(id,agriStateCommodity.getStateCode(),agriStateCommodity.getCommodityId());
				if(count == 0) {
					agriStateCommodity.setId(id);
					agriStateCommodityRepository.save(agriStateCommodity);
			}else if(count > 0) {
				
				throw new AlreadyExistException("Duplicate entry for State Commodity");
			}

			}
			return responseMessageUtil.sendResponse(true, "State Commodity" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
		}catch (AlreadyExistException e) {
			return responseMessageUtil.sendResponse(false, "",e.getMessage());
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", SERVER_ERROR_MSG + e.getMessage());
		}
		
	}
	
	public ResponseMessage approveAgriStateCommodity(int id) {
		try {
			Optional<AgriStateCommodity> optionalStressStage = agriStateCommodityRepository.findById(id);
			if (optionalStressStage.isPresent()) {

				agriStateCommodityRepository.approveAgriStateCommodity(id);

				return responseMessageUtil.sendResponse(true, "State Commodity" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"State Commodity" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}
	
	public ResponseMessage finalizeAgriStateCommodity(int id) {
		try {
			Optional<AgriStateCommodity> optionalDepartment = agriStateCommodityRepository.findById(id);
			if (optionalDepartment.isPresent()) {

				agriStateCommodityRepository.finalizeAgriStateCommodity(id);

				return responseMessageUtil.sendResponse(true, "State Commodity" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"State Commodity" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}
	
	public ResponseMessage rejectAgriStateCommodity(int id) {
		try {
			Optional<AgriStateCommodity> optionalStressStage = agriStateCommodityRepository.findById(id);
			if (optionalStressStage.isPresent()) {

				agriStateCommodityRepository.rejectAgriStateCommodity(id);

				return responseMessageUtil.sendResponse(true, "State Commodity" + APIConstants.RESPONSE_REJECT_SUCCESS,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"State Commodity" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}
	
}
