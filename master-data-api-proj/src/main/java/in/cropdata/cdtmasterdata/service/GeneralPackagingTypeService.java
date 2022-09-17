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
import in.cropdata.cdtmasterdata.model.GeneralPackagingType;
import in.cropdata.cdtmasterdata.model.vo.GeneralPackagingTypeVo;
import in.cropdata.cdtmasterdata.repository.GeneralPackagingTypeRepository;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;
@Service
public class GeneralPackagingTypeService {

	private static final Logger LOGGER = LoggerFactory.getLogger(GeneralPackagingTypeService.class);
	private static final String SERVER_ERROR_MSG = "Server Error : ";
	
	@Autowired
	private GeneralPackagingTypeRepository generalPackagingTypeRepository;
	
	@Autowired
	ResponseMessageUtil responseMessageUtil;
	
	public List<GeneralPackagingType> getGeneralPackagingTypeList() {
		try {
			LOGGER.info("getting packaging type list info...");

			return generalPackagingTypeRepository.findAll();

		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Packaging Type Data Found!");
		}
	}
	
	public Page<GeneralPackagingTypeVo> getGeneralPackagingTypePagenatedList(Integer page, Integer size,
			String searchText) {
		try {
			LOGGER.info("getting all recommenation info - paginated...");
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());

			return generalPackagingTypeRepository.getGeneralPackagingTypePagenatedList(sortedByIdDesc, searchText);

		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Packaging Type Data Found For Searched Text -> " + searchText);
		}
	}
	
	public GeneralPackagingType getGeneralPackagingTypeById(Integer id) {
		try {
			LOGGER.info("getting packaging type by id...");
			return generalPackagingTypeRepository.findById(id).orElse(null);
		} catch (Exception e) {
			
			throw new DoesNotExistException("Packaging Type Does" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
		}
	}
	
	public ResponseMessage addGeneralPackagingType(GeneralPackagingType generalPackagingType) {
		LOGGER.info("Adding Packaging Type...");
		try {
			generalPackagingTypeRepository.save(generalPackagingType);
			return responseMessageUtil.sendResponse(true, "Packaging Type " + APIConstants.RESPONSE_ADD_SUCCESS, "");
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", SERVER_ERROR_MSG + e.getMessage());
		}
	}
	
	public ResponseMessage updateGeneralPackagingType(GeneralPackagingType generalPackagingType, Integer id) {
		LOGGER.info("Updation Packaging Type...");
		try {
			Optional<GeneralPackagingType> optionalGeneralPackagingType = generalPackagingTypeRepository.findById(id);
			if(optionalGeneralPackagingType.isPresent()) {
				generalPackagingType.setId(id);
				generalPackagingTypeRepository.save(generalPackagingType);
			}
			return responseMessageUtil.sendResponse(true, "Packaging Type" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", SERVER_ERROR_MSG + e.getMessage());
		}
		
	}
	
	public ResponseMessage approveGeneralPackagingType(int id) {
		try {
			Optional<GeneralPackagingType> optionalGeneralPackagingType = generalPackagingTypeRepository.findById(id);
			if (optionalGeneralPackagingType.isPresent()) {

				generalPackagingTypeRepository.approveGeneralPackagingType(id);

				return responseMessageUtil.sendResponse(true, "Packaging Type" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Packaging Type" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}
	
	public ResponseMessage finalizeGeneralPackagingType(int id) {
		try {
			Optional<GeneralPackagingType> optionalGeneralPackagingType = generalPackagingTypeRepository.findById(id);
			if (optionalGeneralPackagingType.isPresent()) {

				generalPackagingTypeRepository.finalizeGeneralPackagingType(id);

				return responseMessageUtil.sendResponse(true, "Packaging Type" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Packaging Type" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}
	
	public ResponseMessage rejectGeneralPackagingType(int id) {
		try {
			Optional<GeneralPackagingType> optionalGeneralPackagingType = generalPackagingTypeRepository.findById(id);
			if (optionalGeneralPackagingType.isPresent()) {

				generalPackagingTypeRepository.rejectGeneralPackagingType(id);

				return responseMessageUtil.sendResponse(true, "Packaging Type" + APIConstants.RESPONSE_REJECT_SUCCESS,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Packaging Type" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}
	
}
