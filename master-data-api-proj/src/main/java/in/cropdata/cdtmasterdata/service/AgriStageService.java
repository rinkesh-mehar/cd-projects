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
import in.cropdata.cdtmasterdata.model.AgriStage;
import in.cropdata.cdtmasterdata.model.vo.AgriStageVO;
import in.cropdata.cdtmasterdata.repository.AgriStageRepository;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class AgriStageService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AgriStageService.class);
	private static final String SERVER_ERROR_MSG = "Server Error : ";
	
	@Autowired
	private AgriStageRepository agriStageRepository;
	
	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	  public List<AgriStage> getStageList() {
	        try {

	        	LOGGER.info("getting stage list..!!");
	            return agriStageRepository.findAll(Sort.by("name"));

	        } catch (Exception e) {
	        	LOGGER.error(SERVER_ERROR_MSG,e);
	            throw new DoesNotExistException("No Stage Data Found..!!");
	        }

	    }
	  
	  public Page<AgriStageVO> getStagePaginatedList(Integer page,Integer size,String searchText){
		  
		  try {
			
			  LOGGER.info("getting stage paginated list..!!");
			  
			  searchText = "%" + searchText + "%";
			  
			  Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());
			  return agriStageRepository.getStagePaginatedList(sortedByIdDesc,searchText);
			  
		} catch (Exception e) {
			LOGGER.error(SERVER_ERROR_MSG, e);
			throw new DoesNotExistException("No Stage Data Found For Serach Text => " + searchText);
		}
		  
	  }
	  
	  public ResponseMessage addStage(AgriStage agriStage) {
		  
		  try {
			  LOGGER.info("Updating Stage..!!");
			  agriStageRepository.save(agriStage);
			  return responseMessageUtil.sendResponse(true, "Agri Stage" + APIConstants.RESPONSE_ADD_SUCCESS, "");
		} catch (Exception e) {
			LOGGER.error(SERVER_ERROR_MSG, e);
			return responseMessageUtil.sendResponse(false, "", e.getMessage());
		}
	  }
	  
	  public AgriStage getStageById(Integer id) {
		  try {
			  LOGGER.info("Getting Stage By Id..!!");
			  return agriStageRepository.findById(id).orElse(null);
		} catch (Exception e) {
			throw new DoesNotExistException("Stage Does" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
		}
	  }
	  
	  public ResponseMessage updateStage(AgriStage agriStage,Integer id) {
		  try {
			  LOGGER.info("Updating Stage..!!");
			  Optional<AgriStage> optionalStage = agriStageRepository.findById(id);
			  if(optionalStage.isPresent()) {
				  agriStage.setId(id);
				  agriStageRepository.save(agriStage);
			  }
			  return responseMessageUtil.sendResponse(true, "Agri Stage" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
		} catch (Exception e) {
			LOGGER.error(SERVER_ERROR_MSG, e);
			return responseMessageUtil.sendResponse(false, "", e.getMessage());
		}
	  }
	  
	  public ResponseMessage approveStage(Integer id) {
		  try {
			LOGGER.info("Approving Stage..!!");
			Optional<AgriStage> optionalStage = agriStageRepository.findById(id);
			if(optionalStage.isPresent()) {
				agriStageRepository.approveStage(id);
				return responseMessageUtil.sendResponse(true, "Agri Stage" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");
			}else {
				return responseMessageUtil.sendResponse(true,  "" , "Agri Stage" + APIConstants.RESPONSE_PRIMARY_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			LOGGER.error(SERVER_ERROR_MSG, e);
			return responseMessageUtil.sendResponse(false, "", e.getMessage());
		}
	  }
	  
	  public ResponseMessage finalizeStage(Integer id) {
		  try {
			LOGGER.info("Finalizing Stage..!!");
			Optional<AgriStage> optionalStage = agriStageRepository.findById(id);
			if(optionalStage.isPresent()) {
				agriStageRepository.finalizeStage(id);
				return responseMessageUtil.sendResponse(true, "Agri Stage" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
			}else {
				return responseMessageUtil.sendResponse(true, "", "Agri Stage" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS);
			}
		} catch (Exception e) {
			LOGGER.error(SERVER_ERROR_MSG, e);
			return responseMessageUtil.sendResponse(false, "", e.getMessage());
		}
	  }
	  
	  public ResponseMessage rejectStage(Integer id) {
		  try {
			  LOGGER.info("Rejecting Stage..!!");
			  Optional<AgriStage> optionalStage = agriStageRepository.findById(id);
			  if(optionalStage.isPresent()) {
				  agriStageRepository.rejectStage(id);
				  return responseMessageUtil.sendResponse(true, "Agri Stage" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
			  }else {
				  return responseMessageUtil.sendResponse(false, "", "Agri Stage" + APIConstants.RESPONSE_FINAL_APPROVAL_ERROR);
			  }
		} catch (Exception e) {
			LOGGER.error(SERVER_ERROR_MSG, e);
			return responseMessageUtil.sendResponse(false, "", e.getMessage());
		}
	  }
	
}
