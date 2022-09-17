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
import in.cropdata.cdtmasterdata.model.AgriCommodityGroup;
import in.cropdata.cdtmasterdata.model.vo.AgriCommodityGroupVo;
import in.cropdata.cdtmasterdata.repository.AgriCommodityGroupRepository;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;
@Service
public class AgriCommodityGroupService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AgriCommodityGroupService.class);
	private static final String SERVER_ERROR_MSG = "Server Error : ";
	
	@Autowired
	private AgriCommodityGroupRepository agriCommodityGroupRepository;
	
	@Autowired
	ResponseMessageUtil responseMessageUtil;
	
	public List<AgriCommodityGroup> getCommodityGroupList() {
		try {
			LOGGER.info("getting Commodity Group list info...");

			return agriCommodityGroupRepository.findAll(Sort.by("name"));

		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Commodity Group Data Found!");
		}
	}
	
	
	public Page<AgriCommodityGroupVo> getCommodityGroupPagenatedList(Integer page, Integer size,
			String searchText) {
		try {
			LOGGER.info("getting all Commodity Group info - paginated...");
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());

			return agriCommodityGroupRepository.getCommodityGroupPagenatedList(sortedByIdDesc, searchText);

		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Commodity Group Data Found For Searched Text -> " + searchText);
		}
	}
	
	public AgriCommodityGroup getCommodityGroupById(Integer id) {
		try {
			LOGGER.info("getting Commodity Group by id...");
			return agriCommodityGroupRepository.findById(id).orElse(null);
		} catch (Exception e) {
			
			throw new DoesNotExistException("Commodity Group Does" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
		}
	}
	
	public ResponseMessage addCommodityGroup(AgriCommodityGroup agriCommodityGroup) {
		LOGGER.info("Adding Commodity Group...");
		try {
			agriCommodityGroupRepository.save(agriCommodityGroup);
			return responseMessageUtil.sendResponse(true, "Commodity Group " + APIConstants.RESPONSE_ADD_SUCCESS, "");
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", SERVER_ERROR_MSG + e.getMessage());
		}
	}
	
	public ResponseMessage updateCommodityGroup(AgriCommodityGroup agriCommodityGroup, Integer id) {
		LOGGER.info("Updation Commodity Group...");
		try {
			Optional<AgriCommodityGroup> optionalAgriCommodityGroup = agriCommodityGroupRepository.findById(id);
			if(optionalAgriCommodityGroup.isPresent()) {
				agriCommodityGroup.setId(id);
				agriCommodityGroupRepository.save(agriCommodityGroup);
			}
			return responseMessageUtil.sendResponse(true, "Commodity Group" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", SERVER_ERROR_MSG + e.getMessage());
		}
		
	}
	
	public ResponseMessage approveCommodityGroup(int id) {
		try {
			Optional<AgriCommodityGroup> optionalAgriCommodityGroup = agriCommodityGroupRepository.findById(id);
			if (optionalAgriCommodityGroup.isPresent()) {

				agriCommodityGroupRepository.approveCommodityGroup(id);

				return responseMessageUtil.sendResponse(true, "Commodity Group" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Commodity Group" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}
	
	public ResponseMessage finalizeCommodityGroup(int id) {
		try {
			Optional<AgriCommodityGroup> optionalAgriCommodityGroup = agriCommodityGroupRepository.findById(id);
			if (optionalAgriCommodityGroup.isPresent()) {

				agriCommodityGroupRepository.finalizeCommodityGroup(id);

				return responseMessageUtil.sendResponse(true, "Commodity Group" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Commodity Group" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}
	
	public ResponseMessage rejectCommodityGroup(int id) {
		try {
			Optional<AgriCommodityGroup> optionalAgriCommodityGroup = agriCommodityGroupRepository.findById(id);
			if (optionalAgriCommodityGroup.isPresent()) {

				agriCommodityGroupRepository.rejectCommodityGroup(id);

				return responseMessageUtil.sendResponse(true, "Commodity Group" + APIConstants.RESPONSE_REJECT_SUCCESS,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Commodity Group" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}
	
}
