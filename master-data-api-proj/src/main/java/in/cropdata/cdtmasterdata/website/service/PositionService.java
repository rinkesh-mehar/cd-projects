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
import in.cropdata.cdtmasterdata.website.dto.PositionDto;
import in.cropdata.cdtmasterdata.website.model.Position;
import in.cropdata.cdtmasterdata.website.repository.PositionRepository;

@Service
public class PositionService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PositionService.class);

	@Autowired
	private PositionRepository positionRepository;

	private static final String SERVER_ERROR_MSG = "Server Error : ";
	
	@Autowired
	ResponseMessageUtil responseMessageUtil;

	public List<PositionDto> getPositionList() {
		try {
			LOGGER.info("getting all position info...");
			return positionRepository.getPositionList();
		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Position Data Found!");
		}
	}
	
	public Page<PositionDto> getPositionListByPagenation(Integer page, Integer size, String searchText){
		try {
			LOGGER.info("getting all position from service....");
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());
			return positionRepository.getPositionListByPagenation(sortedByIdDesc, searchText);
		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Position Data Found For Searched Text -> " + searchText);
		}
	}
	
	public ResponseMessage addPosition(Position position) {
		try {
			LOGGER.info("Add Position From Service...");
//			position.setStatus(APIConstants.STATUS_INACTIVE);
			
			LOGGER.info("Length of Position : " + position.getName().length());
			
			if(position.getName().length() > 100) {
				throw new InvalidDataException("Position Length Should Not Be Greater Than 100");
			}
			
			PositionDto alreadyExistPosition = positionRepository.findAlreadyExistPositionForAddMode(position.getName());
			LOGGER.info("alreadyExistPosition " + alreadyExistPosition);
			if(alreadyExistPosition == null) {
				LOGGER.info("Position already not exist...{} ");
				positionRepository.save(position);
		}else {
			LOGGER.info("Position already exist...{} ");
			throw new AlreadyExistException("'" + position.getName() + "' Positions Is Already Exist");
		}
			return responseMessageUtil.sendResponse(true, "Position" + APIConstants.RESPONSE_ADD_SUCCESS, "");
		}catch (InvalidDataException e) {
			return responseMessageUtil.sendResponse(false, "",e.getMessage());
		}catch (AlreadyExistException e) {
			return responseMessageUtil.sendResponse(false, "",e.getMessage());
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error: " + e.getMessage());
		}
	}
	
	public Position getPositionById(Integer id) {

		try {

			LOGGER.info("getting position by Id...");
			return positionRepository.findById(id).orElse(null);

		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("Position Does" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
		}

	}

	public ResponseMessage updatePosition(Integer id, Position position) {
		try {
			
			LOGGER.info("Length of Position : " + position.getName().length());
			
			if(position.getName().length() > 100) {
				throw new InvalidDataException("Position Length Should Not Be Greater Than 100");
			}
			
			Optional<Position> positionDetail = positionRepository.findById(id);

			if (positionDetail.isPresent()) {
				
				PositionDto alreadyExistPosition = positionRepository.findAlreadyExistPositionForEditMode(id,position.getName());
				LOGGER.info("alreadyExistPosition " + alreadyExistPosition);
				if(alreadyExistPosition == null) {
					LOGGER.info("Position already not exist...{} ");
					LOGGER.info("\"Updating Position From Service for ID -> {}\", id");
					position.setId(id);
					positionRepository.save(position);
			}else {
				LOGGER.info("Position already exist...{} ");
				throw new AlreadyExistException("'" + position.getName() + "' Positions Is Already Exist");
			}
				
				return responseMessageUtil.sendResponse(true, "Position" + APIConstants.RESPONSE_UPDATE_SUCCESS + id,
						"");
			} else {
				return responseMessageUtil.sendResponse(false,
						"Position Does" + APIConstants.RESPONSE_DOES_NOT_EXIST + id, "");
			}
		}catch (InvalidDataException e) {
			return responseMessageUtil.sendResponse(false, "",e.getMessage());
		}catch (AlreadyExistException e) {
			return responseMessageUtil.sendResponse(false, "",e.getMessage());
		} catch (Exception ex) {
			return responseMessageUtil.sendResponse(false, "", SERVER_ERROR_MSG + ex.getMessage());
		}
	}
	
	public ResponseMessage deactivePosition(int id) {
		try {
			Optional<Position> optionalPosition = positionRepository.findById(id);
			if (optionalPosition.isPresent()) {

				positionRepository.deactivePosition(id);
				return responseMessageUtil.sendResponse(true, "Position" + APIConstants.RESPONSE_DEACTIVE_SUCCESS + id,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Position" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}
	
	public ResponseMessage activePosition(int id) {
		try {
			Optional<Position> optionalPosition = positionRepository.findById(id);
			if (optionalPosition.isPresent()) {

				positionRepository.activePosition(id);
				return responseMessageUtil.sendResponse(true, "Position" + APIConstants.RESPONSE_ACTIVE_SUCCESS + id,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Position" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}
	
	public Integer opportunityCountByPosition(int positionId) {
		
		return positionRepository.opportunityCountByPosition(positionId);

	}
	
	public ResponseMessage deletePosition(int id) {
		try {
			Optional<Position> optionalPosition = positionRepository.findById(id);
			if (optionalPosition.isPresent()) {

				positionRepository.deletePosition(id);
				return responseMessageUtil.sendResponse(true, "Position" + APIConstants.RESPONSE_DELETE_SUCCESS + id,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Position" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}
}
