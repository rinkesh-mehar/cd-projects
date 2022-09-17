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
import in.cropdata.cdtmasterdata.model.vo.AgriRecommendationVo;
import in.cropdata.cdtmasterdata.repository.AgriRecommendationRepository;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;
@Service
public class AgriRecommendationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AgriRecommendationService.class);
	private static final String SERVER_ERROR_MSG = "Server Error : ";
	
	@Autowired
	private AgriRecommendationRepository agriRecommendationRepository;
	
	@Autowired
	ResponseMessageUtil responseMessageUtil;
	
	public List<AgriRecommendation> getRecommendationList() {
		try {
			LOGGER.info("getting recommendation list info...");

			return agriRecommendationRepository.findAll(Sort.by("name"));

		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Recommendation Data Found!");
		}
	}
	
//	public Page<AgriRecommendationVo> getRecommendationPagenatedList(Integer page,Integer size,String searchText){
//		searchText = "%" + searchText + "%";
//		Pageable sortedByDesc = PageRequest.of(page, size, Sort.by("id").descending());
//		return agriRecommendationRepository.getRecommendationPagenatedList(sortedByDesc, searchText);
//	}
	
	public Page<AgriRecommendationVo> getRecommendationPagenatedList(Integer page, Integer size,
			String searchText) {
		try {
			LOGGER.info("getting all recommenation info - paginated...");
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());

			return agriRecommendationRepository.getRecommendationPagenatedList(sortedByIdDesc, searchText);

		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Recommendation Data Found For Searched Text -> " + searchText);
		}
	}
	
	public AgriRecommendation getRecommendationById(Integer id) {
		try {
			LOGGER.info("getting recommendation by id...");
			return agriRecommendationRepository.findById(id).orElse(null);
		} catch (Exception e) {
			
			throw new DoesNotExistException("Recommendation Does" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
		}
	}
	
	public ResponseMessage addRecommendation(AgriRecommendation agriRecommendation) {
		LOGGER.info("Adding Recommendation...");
		try {
			agriRecommendationRepository.save(agriRecommendation);
			return responseMessageUtil.sendResponse(true, "Recommendation " + APIConstants.RESPONSE_ADD_SUCCESS, "");
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", SERVER_ERROR_MSG + e.getMessage());
		}
	}
	
	public ResponseMessage updateRecommendation(AgriRecommendation agriRecommendation, Integer id) {
		LOGGER.info("Updation Recommendation...");
		try {
			Optional<AgriRecommendation> optionalRecommendation = agriRecommendationRepository.findById(id);
			if(optionalRecommendation.isPresent()) {
				agriRecommendation.setId(id);
				agriRecommendationRepository.save(agriRecommendation);
			}
			return responseMessageUtil.sendResponse(true, "Recommendation" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", SERVER_ERROR_MSG + e.getMessage());
		}
		
	}
	
	public ResponseMessage approveRecommendation(int id) {
		try {
			Optional<AgriRecommendation> optionalDepartment = agriRecommendationRepository.findById(id);
			if (optionalDepartment.isPresent()) {

				agriRecommendationRepository.approveRecommendation(id);

				return responseMessageUtil.sendResponse(true, "Recommendation" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Recommendation" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}
	
	public ResponseMessage finalizeRecommendation(int id) {
		try {
			Optional<AgriRecommendation> optionalDepartment = agriRecommendationRepository.findById(id);
			if (optionalDepartment.isPresent()) {

				agriRecommendationRepository.finalizeRecommendation(id);

				return responseMessageUtil.sendResponse(true, "Recommendation" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Recommendation" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}
	
	public ResponseMessage rejectRecommendation(int id) {
		try {
			Optional<AgriRecommendation> optionalDepartment = agriRecommendationRepository.findById(id);
			if (optionalDepartment.isPresent()) {

				agriRecommendationRepository.rejectRecommendation(id);

				return responseMessageUtil.sendResponse(true, "Recommendation" + APIConstants.RESPONSE_REJECT_SUCCESS,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Recommendation" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}
	
}
