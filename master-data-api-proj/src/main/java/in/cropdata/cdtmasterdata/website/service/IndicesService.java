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
import in.cropdata.cdtmasterdata.website.model.Indices;
import in.cropdata.cdtmasterdata.website.model.vo.IndicesVO;
import in.cropdata.cdtmasterdata.website.repository.IndicesRepository;

@Service
public class IndicesService {

	private static final Logger LOGGER = LoggerFactory.getLogger(IndicesService.class);

	@Autowired
	private IndicesRepository indicesRepository;

	private static final String SERVER_ERROR_MSG = "Server Error : ";
	
	@Autowired
	ResponseMessageUtil responseMessageUtil;

	public List<IndicesVO> getIndicesList() {
		try {
			LOGGER.info("getting all indices info...");
			return indicesRepository.getIndicesList();
		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Indices Data Found!");
		}
	}
	
	public Page<IndicesVO> getIndicesListByPagenation(Integer page, Integer size, String searchText){
		try {
			LOGGER.info("getting all indices from service....");
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());
			return indicesRepository.getIndicesListByPagenation(sortedByIdDesc, searchText);
		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Indices Data Found For Searched Text -> " + searchText);
		}
	}
	
	public ResponseMessage addIndices(Indices indices) {
		try {
			LOGGER.info("Add Indice From Service...");
//			position.setStatus(APIConstants.STATUS_INACTIVE);
			
			Integer alreadyExistEngineCount = indicesRepository.findAlreadyExistIndicesForAddMode(indices.getName(),indices.getPlatformID());
			LOGGER.info("alreadyExistEngineCount " + alreadyExistEngineCount);
			if(alreadyExistEngineCount > 0) {
				LOGGER.info("Indice already exist...{} ");
				throw new AlreadyExistException("'" + indices.getName() + "' Indice Is Already Exist");
		}else {
				LOGGER.info("Indice already not exist...{} ");
				indicesRepository.save(indices);
		}
			return responseMessageUtil.sendResponse(true, "Indices" + APIConstants.RESPONSE_ADD_SUCCESS, "");
		}catch (InvalidDataException e) {
			return responseMessageUtil.sendResponse(false, "",e.getMessage());
		}catch (AlreadyExistException e) {
			return responseMessageUtil.sendResponse(false, "",e.getMessage());
		} catch (Exception e) {
			LOGGER.error(SERVER_ERROR_MSG, e);
			return responseMessageUtil.sendResponse(false, "", "Server Error: " + e.getMessage());
		}
	}
	
	public Indices getIndicesById(Integer id) {

		try {

			LOGGER.info("getting indices by Id...");
			return indicesRepository.findById(id).orElse(null);

		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("Indices Does" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
		}

	}

	public ResponseMessage updateIndices(Integer id, Indices indices) {
		try {
			
			Optional<Indices> optionalIndices = indicesRepository.findById(id);

			if (optionalIndices.isPresent()) {
				
				Integer alreadyExistEngineCount = indicesRepository.findAlreadyExistIndicesForEditMode(id,indices.getName(),indices.getPlatformID());
				LOGGER.info("alreadyExistEngineCount " + alreadyExistEngineCount);
				if(alreadyExistEngineCount > 0) {
					LOGGER.info("Indice already exist...{} ");
					throw new AlreadyExistException("'" + indices.getName() + "' Indice Is Already Exist");
					
			}else {
					LOGGER.info("Engine already not exist...{} ");
					LOGGER.info("\"Updating Indice From Service for ID -> {}\", id");
					indices.setId(id);
					indicesRepository.save(indices);
			}
				
				return responseMessageUtil.sendResponse(true, "Indice" + APIConstants.RESPONSE_UPDATE_SUCCESS + id,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Indice Does" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		}catch (InvalidDataException e) {
			return responseMessageUtil.sendResponse(false, "",e.getMessage());
		}catch (AlreadyExistException e) {
			return responseMessageUtil.sendResponse(false, "",e.getMessage());
		} catch (Exception ex) {
			return responseMessageUtil.sendResponse(false, "", SERVER_ERROR_MSG + ex.getMessage());
		}
	}

	
	public ResponseMessage activeIndices(int id) {
		try {
			Optional<Indices> optionalIndices = indicesRepository.findById(id);
			if (optionalIndices.isPresent()) {

				indicesRepository.activeIndices(id);
				return responseMessageUtil.sendResponse(true, "Indice" + APIConstants.RESPONSE_ACTIVE_SUCCESS + id,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Indice" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}
	
	public ResponseMessage deactiveIndices(int id) {
		try {
			Optional<Indices> optionalIndices = indicesRepository.findById(id);
			if (optionalIndices.isPresent()) {

				indicesRepository.deactiveIndices(id);
				return responseMessageUtil.sendResponse(true, "Indice" + APIConstants.RESPONSE_DEACTIVE_SUCCESS + id,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Indice" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}
	
	public ResponseMessage deleteIndices(int id) {
		try {
			Optional<Indices> optionalIndices = indicesRepository.findById(id);
			if (optionalIndices.isPresent()) {

				indicesRepository.deleteIndices(id);
				return responseMessageUtil.sendResponse(true, "Indice" + APIConstants.RESPONSE_DELETE_SUCCESS + id,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Indice" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}
}
