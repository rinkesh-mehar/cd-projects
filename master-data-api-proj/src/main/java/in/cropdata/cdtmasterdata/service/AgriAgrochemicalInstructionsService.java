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
import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.model.AgriAgrochemicalInstructions;
import in.cropdata.cdtmasterdata.model.vo.AgriAgrochemicalInstructionsVo;
import in.cropdata.cdtmasterdata.repository.AgriAgrochemicalInstructionsRepository;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class AgriAgrochemicalInstructionsService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AgriAgrochemicalInstructionsService.class);
	private static final String SERVER_ERROR_MSG = "Server Error : ";
	
	@Autowired
	private AgriAgrochemicalInstructionsRepository agriAgrochemicalInstructionsRepository;
	
	@Autowired
	private ResponseMessageUtil responseMessageUtil;
	
	public List<AgriAgrochemicalInstructions> getAgrochemicalInstructionsList() {
		try {
			LOGGER.info("getting agrochemical instructions list info...");

			return agriAgrochemicalInstructionsRepository.findAll(Sort.by("name"));

		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Agrochemical Instructions Data Found!");
		}
	}
	
	public Page<AgriAgrochemicalInstructionsVo> getAgrochemicalInstructionsPaginatedList(Integer page,Integer size,String searchText){
		
		try {
			LOGGER.info("Getting Agrochemical Instructions Paginated List..!!");
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());
			return agriAgrochemicalInstructionsRepository.getAgrochemicalInstructionsPaginatedList(sortedByIdDesc, searchText);	
			
		} catch (Exception e) {
			LOGGER.error(SERVER_ERROR_MSG,e);
			throw new DoesNotExistException("No Agrochemical Instructions Data Found By Searched Text -> " + searchText);
		}
		
		
	}
	
	public ResponseMessage addAgrochemicalInstructions(AgriAgrochemicalInstructions agriAgrochemicalInstructions) {
		try {
			agriAgrochemicalInstructionsRepository.save(agriAgrochemicalInstructions);
			return responseMessageUtil.sendResponse(true, "Agrochemical Instructions " + APIConstants.RESPONSE_ADD_SUCCESS, "");
		} catch (Exception e) {
			LOGGER.error(SERVER_ERROR_MSG,e);
			return responseMessageUtil.sendResponse(false, "", SERVER_ERROR_MSG + e.getMessage());
		}
	}
	
	public AgriAgrochemicalInstructions getAgrochemicalInstructionsById(Integer id) {
		try {
			return agriAgrochemicalInstructionsRepository.findById(id).orElse(null);
		} catch (Exception e) {
			LOGGER.error(SERVER_ERROR_MSG,e);
			throw new DoesNotExistException("Agrochemical Instructions " + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
		}
	}
	
	public ResponseMessage updateAgrochemicalInstructions(AgriAgrochemicalInstructions agriAgrochemicalInstructions,Integer id) {
		try {
			Optional<AgriAgrochemicalInstructions> optionalAgrochemicalInstructions = agriAgrochemicalInstructionsRepository.findById(id);
			if(optionalAgrochemicalInstructions.isPresent()) {
				agriAgrochemicalInstructions.setId(id);
				agriAgrochemicalInstructionsRepository.save(agriAgrochemicalInstructions);
			}
			return responseMessageUtil.sendResponse(true, "Agrochemical Instructions " + APIConstants.RESPONSE_REJECT_SUCCESS, "");
		} catch (Exception e) {
			LOGGER.error(SERVER_ERROR_MSG,e);
			return responseMessageUtil.sendResponse(false, "", SERVER_ERROR_MSG + e.getMessage());
		}
	}
	
	public ResponseMessage approveAgrochemicalInstructions(int id) {
		try {
			Optional<AgriAgrochemicalInstructions> optionalAgrochemicalInstructions = agriAgrochemicalInstructionsRepository.findById(id);
			if (optionalAgrochemicalInstructions.isPresent()) {

				agriAgrochemicalInstructionsRepository.approveCommodityGroup(id);

				return responseMessageUtil.sendResponse(true, "Agrochemical Instructions" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agrochemical Instructions" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}
	
	public ResponseMessage finalizeAgrochemicalInstructions(int id) {
		try {
			Optional<AgriAgrochemicalInstructions> optionalAgrochemicalInstructions = agriAgrochemicalInstructionsRepository.findById(id);
			if (optionalAgrochemicalInstructions.isPresent()) {

				agriAgrochemicalInstructionsRepository.finalizeCommodityGroup(id);

				return responseMessageUtil.sendResponse(true, "Agrochemical Instructions" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agrochemical Instructions" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}
	
	public ResponseMessage rejectAgrochemicalInstructions(int id) {
		try {
			Optional<AgriAgrochemicalInstructions> optionalAgrochemicalInstructions = agriAgrochemicalInstructionsRepository.findById(id);
			if (optionalAgrochemicalInstructions.isPresent()) {

				agriAgrochemicalInstructionsRepository.rejectCommodityGroup(id);

				return responseMessageUtil.sendResponse(true, "Agrochemical Instructions" + APIConstants.RESPONSE_REJECT_SUCCESS,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agrochemical Instructions" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}
}
