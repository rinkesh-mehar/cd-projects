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
import in.cropdata.cdtmasterdata.website.model.Engines;
import in.cropdata.cdtmasterdata.website.model.vo.EnginesVO;
import in.cropdata.cdtmasterdata.website.repository.EnginesRepository;

@Service
public class EnginesService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EnginesService.class);

	@Autowired
	private EnginesRepository enginesRepository;

	private static final String SERVER_ERROR_MSG = "Server Error : ";
	
	@Autowired
	ResponseMessageUtil responseMessageUtil;

	public List<EnginesVO> getEnginesList() {
		try {
			LOGGER.info("getting all engines info...");
			return enginesRepository.getEnginesList();
		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Engines Data Found!");
		}
	}
	
	public Page<EnginesVO> getEnginesListByPagenation(Integer page, Integer size, String searchText){
		try {
			LOGGER.info("getting all engines from service....");
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());
			return enginesRepository.getEnginesListByPagenation(sortedByIdDesc, searchText);
		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Engines Data Found For Searched Text -> " + searchText);
		}
	}
	
	public ResponseMessage addEngines(Engines engines) {
		try {
			LOGGER.info("Add Engine From Service...");
//			position.setStatus(APIConstants.STATUS_INACTIVE);
			
			Integer alreadyExistEngineCount = enginesRepository.findAlreadyExistEnginesForAddMode(engines.getName(),engines.getPlatformID());
			LOGGER.info("alreadyExistEngineCount " + alreadyExistEngineCount);
			if(alreadyExistEngineCount > 0) {
				LOGGER.info("Engine already exist...{} ");
				throw new AlreadyExistException("'" + engines.getName() + "' Engine Is Already Exist");
		}else {
				LOGGER.info("Engine already not exist...{} ");
				enginesRepository.save(engines);
		}
			return responseMessageUtil.sendResponse(true, "Engines" + APIConstants.RESPONSE_ADD_SUCCESS, "");
		}catch (InvalidDataException e) {
			return responseMessageUtil.sendResponse(false, "",e.getMessage());
		}catch (AlreadyExistException e) {
			return responseMessageUtil.sendResponse(false, "",e.getMessage());
		} catch (Exception e) {
			LOGGER.error(SERVER_ERROR_MSG, e);
			return responseMessageUtil.sendResponse(false, "", "Server Error: " + e.getMessage());
		}
	}
	
	public Engines getEnginesById(Integer id) {

		try {

			LOGGER.info("getting engines by Id...");
			return enginesRepository.findById(id).orElse(null);

		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("Engines Does" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
		}

	}

	public ResponseMessage updateEngines(Integer id, Engines engines) {
		try {
			
			Optional<Engines> optionalEngines = enginesRepository.findById(id);

			if (optionalEngines.isPresent()) {
				
				Integer alreadyExistEngineCount = enginesRepository.findAlreadyExistEnginesForEditMode(id,engines.getName(),engines.getPlatformID());
				LOGGER.info("alreadyExistEngineCount " + alreadyExistEngineCount);
				if(alreadyExistEngineCount > 0) {
					LOGGER.info("Engine already exist...{} ");
					throw new AlreadyExistException("'" + engines.getName() + "' Engine Is Already Exist");
					
			}else {
					LOGGER.info("Engine already not exist...{} ");
					LOGGER.info("\"Updating Engine From Service for ID -> {}\", id");
					engines.setId(id);
					enginesRepository.save(engines);
			}
				
				return responseMessageUtil.sendResponse(true, "Engine" + APIConstants.RESPONSE_UPDATE_SUCCESS + id,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Engines Does" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		}catch (InvalidDataException e) {
			return responseMessageUtil.sendResponse(false, "",e.getMessage());
		}catch (AlreadyExistException e) {
			return responseMessageUtil.sendResponse(false, "",e.getMessage());
		} catch (Exception ex) {
			return responseMessageUtil.sendResponse(false, "", SERVER_ERROR_MSG + ex.getMessage());
		}
	}

	
	public ResponseMessage activeEngines(int id) {
		try {
			Optional<Engines> optionalEngines = enginesRepository.findById(id);
			if (optionalEngines.isPresent()) {

				enginesRepository.activeEngines(id);
				return responseMessageUtil.sendResponse(true, "Engine" + APIConstants.RESPONSE_ACTIVE_SUCCESS + id,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Engine" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}
	
	public ResponseMessage deactiveEngines(int id) {
		try {
			Optional<Engines> optionalEngines = enginesRepository.findById(id);
			if (optionalEngines.isPresent()) {

				enginesRepository.deactiveEngines(id);
				return responseMessageUtil.sendResponse(true, "Engine" + APIConstants.RESPONSE_DEACTIVE_SUCCESS + id,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Engines" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}
	
	public ResponseMessage deleteEngines(int id) {
		try {
			Optional<Engines> optionalEngines = enginesRepository.findById(id);
			if (optionalEngines.isPresent()) {

				enginesRepository.deleteEngines(id);
				return responseMessageUtil.sendResponse(true, "Engine" + APIConstants.RESPONSE_DELETE_SUCCESS + id,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Engine" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}
}
