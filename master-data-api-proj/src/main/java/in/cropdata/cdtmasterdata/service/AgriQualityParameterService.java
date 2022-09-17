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
import in.cropdata.cdtmasterdata.model.AgriQualityParameter;
import in.cropdata.cdtmasterdata.model.vo.AgriQualityParameterVo;
import in.cropdata.cdtmasterdata.repository.AgriQualityParameterRepository;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;
@Service
public class AgriQualityParameterService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AgriQualityParameterService.class);
	private static final String SERVER_ERROR_MSG = "Server Error : ";
	
	@Autowired
	private AgriQualityParameterRepository agriQualityParameterRepository;
	
	@Autowired
	ResponseMessageUtil responseMessageUtil;
	
	public List<AgriQualityParameter> getQualityParameterList() {
		try {
			LOGGER.info("getting quality parameter list info...");

			return agriQualityParameterRepository.findAll(Sort.by("name"));

		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Quality Parameter Data Found!");
		}
	}
		
	public Page<AgriQualityParameterVo> getQualityParameterPagenatedList(Integer page, Integer size,
			String searchText) {
		try {
			LOGGER.info("getting all quality parameter info - paginated...");
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());

			return agriQualityParameterRepository.getQualityParameterPagenatedList(sortedByIdDesc, searchText);

		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Quality Parameter Data Found For Searched Text -> " + searchText);
		}
	}
	
	public AgriQualityParameter getQualityParameterById(Integer id) {
		try {
			LOGGER.info("getting quality parameter by id...");
			return agriQualityParameterRepository.findById(id).orElse(null);
		} catch (Exception e) {
			
			throw new DoesNotExistException("Quality Parameter Does" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
		}
	}
	
	public ResponseMessage addQualityParameter(AgriQualityParameter agriQualityParameter) {
		LOGGER.info("Adding Quality Parameter...");
		try {
			agriQualityParameterRepository.save(agriQualityParameter);
			return responseMessageUtil.sendResponse(true, "Quality Parameter " + APIConstants.RESPONSE_ADD_SUCCESS, "");
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", SERVER_ERROR_MSG + e.getMessage());
		}
	}
	
	public ResponseMessage updateQualityParameter(AgriQualityParameter agriQualityParameter, Integer id) {
		LOGGER.info("Updation Quality Parameter...");
		try {
			Optional<AgriQualityParameter> optionalAgriQualityParameter = agriQualityParameterRepository.findById(id);
			if(optionalAgriQualityParameter.isPresent()) {
				agriQualityParameter.setId(id);
				agriQualityParameterRepository.save(agriQualityParameter);
			}
			return responseMessageUtil.sendResponse(true, "Quality Parameter" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", SERVER_ERROR_MSG + e.getMessage());
		}
		
	}
	
	public ResponseMessage approveQualityParameter(int id) {
		try {
			Optional<AgriQualityParameter> optionalAgriQualityParameter = agriQualityParameterRepository.findById(id);
			if (optionalAgriQualityParameter.isPresent()) {

				agriQualityParameterRepository.approveQualityParameter(id);

				return responseMessageUtil.sendResponse(true, "Quality Parameter" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Quality Parameter" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}
	
	public ResponseMessage finalizeQualityParameter(int id) {
		try {
			Optional<AgriQualityParameter> optionalAgriQualityParameter = agriQualityParameterRepository.findById(id);
			if (optionalAgriQualityParameter.isPresent()) {

				agriQualityParameterRepository.finalizeQualityParameter(id);

				return responseMessageUtil.sendResponse(true, "Quality Parameter" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Quality Parameter" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}
	
	public ResponseMessage rejectQualityParameter(int id) {
		try {
			Optional<AgriQualityParameter> optionalAgriQualityParameter = agriQualityParameterRepository.findById(id);
			if (optionalAgriQualityParameter.isPresent()) {

				agriQualityParameterRepository.rejectQualityParameter(id);

				return responseMessageUtil.sendResponse(true, "Quality Parameter" + APIConstants.RESPONSE_REJECT_SUCCESS,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Quality Parameter" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}
	
}
