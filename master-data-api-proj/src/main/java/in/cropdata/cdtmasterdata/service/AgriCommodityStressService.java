package in.cropdata.cdtmasterdata.service;

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
import in.cropdata.cdtmasterdata.model.AgriCommodityStress;
import in.cropdata.cdtmasterdata.model.vo.AgriCommodityStressVO;
import in.cropdata.cdtmasterdata.repository.AgriCommodityStressRepository;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class AgriCommodityStressService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AgriCommodityStressService.class);
	
	private static final String SERVER_ERROR_MSG = "Server Error : ";
	
	@Autowired
	private ResponseMessageUtil responseMessageUtil;
	
	@Autowired
	private AgriCommodityStressRepository agriCommodityStressRepository;
	
	public Page<AgriCommodityStressVO> getCommodityStressPagenatedList(Integer page, Integer size,
			String searchText) {
		try {
			LOGGER.info("getting all Commodity Stress info - paginated...");
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());

			return agriCommodityStressRepository.getCommodityStressPagenatedLisy(sortedByIdDesc, searchText);

		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Commodity Stress Data Found For Searched Text -> " + searchText);
		}
	}
	
	public AgriCommodityStress getCommodityStressById(Integer id) {
		try {
			LOGGER.info("getting Commodity Stress by id...");
			return agriCommodityStressRepository.findById(id).orElse(null);
		} catch (Exception e) {
			
			throw new DoesNotExistException("Commodity Stress Does" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
		}
	}
	
	public ResponseMessage addCommodityStress(AgriCommodityStress agriCommodityStress) {
		LOGGER.info("Adding Commodity Stress...");
		try {
			agriCommodityStressRepository.save(agriCommodityStress);
			return responseMessageUtil.sendResponse(true, "Commodity Stress " + APIConstants.RESPONSE_ADD_SUCCESS, "");
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", SERVER_ERROR_MSG + e.getMessage());
		}
	}
	
	public ResponseMessage updateCommodityStress(AgriCommodityStress agriCommodityStress, Integer id) {
		LOGGER.info("Updation Commodity Stress...");
		try {
			Optional<AgriCommodityStress> optionalAgriCommodityGroup = agriCommodityStressRepository.findById(id);
			if(optionalAgriCommodityGroup.isPresent()) {
				agriCommodityStress.setId(id);
				agriCommodityStressRepository.save(agriCommodityStress);
			}
			return responseMessageUtil.sendResponse(true, "Commodity Stress" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", SERVER_ERROR_MSG + e.getMessage());
		}
		
	}
	
	public ResponseMessage approveCommodityStress(int id) {
		try {
			Optional<AgriCommodityStress> optionalCommodityStress = agriCommodityStressRepository.findById(id);
			if (optionalCommodityStress.isPresent()) {

				agriCommodityStressRepository.approveCommodityStress(id);

				return responseMessageUtil.sendResponse(true, "Commodity Stress" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Commodity Stress" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}
	
	public ResponseMessage finalizeCommodityStress(int id) {
		try {
			Optional<AgriCommodityStress> optionalCommodityStress = agriCommodityStressRepository.findById(id);
			if (optionalCommodityStress.isPresent()) {

				agriCommodityStressRepository.finalizeCommodityStress(id);

				return responseMessageUtil.sendResponse(true, "Commodity Stress" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Commodity Stress" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}
	
	public ResponseMessage rejectCommodityStress(int id) {
		try {
			Optional<AgriCommodityStress> optionalCommodityStress = agriCommodityStressRepository.findById(id);
			if (optionalCommodityStress.isPresent()) {

				agriCommodityStressRepository.rejectCommodityStress(id);

				return responseMessageUtil.sendResponse(true, "Commodity Stress" + APIConstants.RESPONSE_REJECT_SUCCESS,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Commodity Stress" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}
	
}
