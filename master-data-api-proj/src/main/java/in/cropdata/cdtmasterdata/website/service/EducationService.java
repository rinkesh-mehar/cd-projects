package in.cropdata.cdtmasterdata.website.service;

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
import in.cropdata.cdtmasterdata.exceptions.AlreadyExistException;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.exceptions.InvalidDataException;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;
import in.cropdata.cdtmasterdata.website.dto.EducationDto;
import in.cropdata.cdtmasterdata.website.model.Education;
import in.cropdata.cdtmasterdata.website.repository.EducationRepository;

@Service
public class EducationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EducationService.class);

	@Autowired
	private EducationRepository educationRepository;

	private static final String SERVER_ERROR_MSG = "Server Error : ";
	
	@Autowired
	ResponseMessageUtil responseMessageUtil;

	public List<EducationDto> getEducationList() {
		try {
			LOGGER.info("getting all education info...");

			return educationRepository.getEducationList();

		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Education Data Found!");
		}
	}
	
	public Page<EducationDto> getEducationListByPagenation(Integer page, Integer size, String searchText){
		try {
			LOGGER.info("getting education list from service....");
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());
			return educationRepository.getEducationListByPagenation(sortedByIdDesc, searchText);
		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Education Data Found For Searched Text -> " + searchText);
		}
	}
	
	public ResponseMessage addEducation(Education education) {
		try {
			LOGGER.info("Add Education From Service...");
//			education.setStatus(APIConstants.STATUS_INACTIVE);
			
			LOGGER.info("Length of Education : " + education.getName().length());
			
			if(education.getName().length() > 100) {
				throw new InvalidDataException("Education Length Should Not Be Greater Than 100");
			}
			
			EducationDto alreadyExistEducation = educationRepository.findAlreadyExistEducationForAddMode(education.getName());
			LOGGER.info("alreadyExistEducation " + alreadyExistEducation);
			if(alreadyExistEducation == null) {
				LOGGER.info("Education already not exist...{} ");
				educationRepository.save(education);
			}else {
			LOGGER.info("Education already exist...{} ");
			throw new AlreadyExistException("'" + education.getName() + "' Education Is Already Exist");
			}
			return responseMessageUtil.sendResponse(true, "Education" + APIConstants.RESPONSE_ADD_SUCCESS, "");
		}catch (InvalidDataException e) {
			return responseMessageUtil.sendResponse(false, "",e.getMessage());
		} catch (AlreadyExistException e) {
			return responseMessageUtil.sendResponse(false, "",e.getMessage());
		}catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error: " + e.getMessage());
		}
	}
	
	public Education getEducationById(Integer id) {

		try {

			LOGGER.info("getting education by Id...");
			return educationRepository.findById(id).orElse(null);

		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("Education Does" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
		}

	}

	public ResponseMessage updateEducation(Integer id, Education education) {
		try {
			
			LOGGER.info("Length of Education : " + education.getName().length());
			
			if(education.getName().length() > 100) {
				throw new InvalidDataException("Education Length Should Not Be Greater Than 100");
			}
			
			Optional<Education> educationDetail = educationRepository.findById(id);

			if (educationDetail.isPresent()) {
				EducationDto alreadyExistEducation = educationRepository.findAlreadyExistEducationForEditMode(id,education.getName());
				LOGGER.info("alreadyExistEducation " + alreadyExistEducation);
				if(alreadyExistEducation == null) {
					LOGGER.info("Education already not exist...{} ");
					LOGGER.info("\"Updating Education From Service for ID -> {}\", id");
					education.setId(id);
					educationRepository.save(education);
				}else {
				LOGGER.info("Education already exist...{} ");
				throw new AlreadyExistException("'" + education.getName() + "' Education Is Already Exist");
				}
				
				return responseMessageUtil.sendResponse(true, "Education" + APIConstants.RESPONSE_UPDATE_SUCCESS + id,
						"");
			} else {
				return responseMessageUtil.sendResponse(false,
						"Education Does" + APIConstants.RESPONSE_DOES_NOT_EXIST + id, "");
			}
		} catch (InvalidDataException e) {
			return responseMessageUtil.sendResponse(false, "",e.getMessage());
		}catch (AlreadyExistException e) {
			return responseMessageUtil.sendResponse(false, "",e.getMessage());
		}catch (Exception ex) {
			return responseMessageUtil.sendResponse(false, "", SERVER_ERROR_MSG + ex.getMessage());
		}
	}
	
	public ResponseMessage deactiveEducation(int id) {
		try {
			Optional<Education> optionalEducation = educationRepository.findById(id);
			if (optionalEducation.isPresent()) {

				
				educationRepository.deactiveEducation(id);

				return responseMessageUtil.sendResponse(true, "Education" + APIConstants.RESPONSE_DEACTIVE_SUCCESS + id,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Department" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}
	
	public ResponseMessage activeEducation(int id) {
		try {
			Optional<Education> optionalEducation = educationRepository.findById(id);
			if (optionalEducation.isPresent()) {

				
				educationRepository.activeEducation(id);

				return responseMessageUtil.sendResponse(true, "Education" + APIConstants.RESPONSE_ACTIVE_SUCCESS + id,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Department" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}
	
	public Integer opportunityCountByEducation(int educationId) {
		
		return educationRepository.opportunityCountByEducation(educationId);

	}

	public ResponseMessage deleteEducation(int id) {
	try {
		Optional<Education> optionalEducation = educationRepository.findById(id);
		if (optionalEducation.isPresent()) {

			
			educationRepository.deleteEducation(id);

			return responseMessageUtil.sendResponse(true, "Education" + APIConstants.RESPONSE_DELETE_SUCCESS + id,
					"");
		} else {
			return responseMessageUtil.sendResponse(false, "",
					"Department" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
		}
	} catch (Exception e) {
		return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
	}
}

}
