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
import in.cropdata.cdtmasterdata.model.AgriRecommendation;
import in.cropdata.cdtmasterdata.model.AgriStressStage;
import in.cropdata.cdtmasterdata.model.vo.AgriRecommendationVo;
import in.cropdata.cdtmasterdata.model.vo.AgriStressStageVO;
import in.cropdata.cdtmasterdata.repository.AgriRecommendationRepository;
import in.cropdata.cdtmasterdata.repository.AgriStressStageRepository;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;
@Service
public class AgriStressStageService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AgriStressStageService.class);
	private static final String SERVER_ERROR_MSG = "Server Error : ";
	
	@Autowired
	private AgriStressStageRepository agriStressStageRepository;
	
	@Autowired
	ResponseMessageUtil responseMessageUtil;
	
	public Page<AgriStressStageVO> getStressStagePagenatedList(Integer page, Integer size,
			String searchText) {
		try {
			LOGGER.info("getting all stress stage info - paginated...");
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());

			return agriStressStageRepository.getStressStagePagenatedList(sortedByIdDesc, searchText);

		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Stress Stage Data Found For Searched Text -> " + searchText);
		}
	}
	
	public AgriStressStage getRecommendationById(Integer id) {
		try {
			LOGGER.info("getting stress stage by id...");
			return agriStressStageRepository.findById(id).orElse(null);
		} catch (Exception e) {
			
			throw new DoesNotExistException("Stress Stage Does" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
		}
	}
	
	public ResponseMessage addStressStage(AgriStressStage agriStressStage) {
		LOGGER.info("Adding Stress Stage...");
		try {
			agriStressStageRepository.save(agriStressStage);
			return responseMessageUtil.sendResponse(true, "Stress Stage " + APIConstants.RESPONSE_ADD_SUCCESS, "");
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", SERVER_ERROR_MSG + e.getMessage());
		}
	}
	
	public ResponseMessage updateStressStage(AgriStressStage agriStressStage, Integer id) {
		LOGGER.info("Updation Stress Stage...");
		try {
			Optional<AgriStressStage> optionalStressStage = agriStressStageRepository.findById(id);
			if(optionalStressStage.isPresent()) {
				agriStressStage.setId(id);
				agriStressStageRepository.save(agriStressStage);
			}
			return responseMessageUtil.sendResponse(true, "Stress Stage" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", SERVER_ERROR_MSG + e.getMessage());
		}
		
	}
	
	public ResponseMessage approveStressStage(int id) {
		try {
			Optional<AgriStressStage> optionalStressStage = agriStressStageRepository.findById(id);
			if (optionalStressStage.isPresent()) {

				agriStressStageRepository.approveStressStage(id);

				return responseMessageUtil.sendResponse(true, "Stress Stage" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Stress Stage" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}
	
	public ResponseMessage finalizeStressStage(int id) {
		try {
			Optional<AgriStressStage> optionalDepartment = agriStressStageRepository.findById(id);
			if (optionalDepartment.isPresent()) {

				agriStressStageRepository.finalizeStressStage(id);

				return responseMessageUtil.sendResponse(true, "Stress Stage" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Stress Stage" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}
	
	public ResponseMessage rejectStressStage(int id) {
		try {
			Optional<AgriStressStage> optionalStressStage = agriStressStageRepository.findById(id);
			if (optionalStressStage.isPresent()) {

				agriStressStageRepository.rejectStressStage(id);

				return responseMessageUtil.sendResponse(true, "Stress Stage" + APIConstants.RESPONSE_REJECT_SUCCESS,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Stress Stage" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}
	
}
