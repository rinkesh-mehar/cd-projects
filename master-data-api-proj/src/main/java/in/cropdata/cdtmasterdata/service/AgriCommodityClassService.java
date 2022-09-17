package in.cropdata.cdtmasterdata.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import in.cropdata.cdtmasterdata.constants.APIConstants;
import in.cropdata.cdtmasterdata.constants.DBConstants;
import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.model.AgriCommodityClass;
import in.cropdata.cdtmasterdata.repository.AgriCommodityClassRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class AgriCommodityClassService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AgriCommodityClassService.class);
	private static final String SERVER_ERROR_MSG = "Server Error : ";

	
	@Autowired
	private AgriCommodityClassRepository agriCommodityClassRepository;

	@Autowired
	private AclHistoryUtil approvalUtil;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	public List<AgriCommodityClass> getAllAgriCommodityClass() {
		try {

			List<AgriCommodityClass> agriCommodityList = agriCommodityClassRepository.findAll(Sort.by("name"));

			return agriCommodityList;

		} catch (Exception e) {
			throw e;
		}
	}// getAllAgriCommodityClass
	
	public Page<AgriCommodityClass> getCommodityClassListByPagenation(Integer page, Integer size, String searchText){
		try {
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());
			return agriCommodityClassRepository.getCommodityClassListByPagenation(sortedByIdDesc, searchText);
		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Agri-CommodityClass Data Found For Searched Text -> " + searchText);
		}
	}

	public ResponseMessage addAgriCommodityClass(AgriCommodityClass agriCommodityClass) {

		try {
			agriCommodityClass = agriCommodityClassRepository.save(agriCommodityClass);

			approvalUtil.addRecord(DBConstants.TBL_AGRI_COMMODITY_CLASS, agriCommodityClass.getId());

			return responseMessageUtil.sendResponse(true, "Agri-CommodityClass" + APIConstants.RESPONSE_ADD_SUCCESS,
					"");
		} catch (DataIntegrityViolationException e) {
			LOGGER.error("DataIntegrityViolationException : ", e);
			return responseMessageUtil.sendResponse(false, "", "Duplicate entry for commodity class " + agriCommodityClass.getName());
		}catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}// addAllAgriCommodityClass

	public ResponseMessage updateAgriCommodityClassById(int id, AgriCommodityClass agriCommodityClass) {

		try {
			Optional<AgriCommodityClass> foundCommodityClass = agriCommodityClassRepository.findById(id);
			if (foundCommodityClass.isPresent()) {

				agriCommodityClass.setId(id);
				agriCommodityClass = agriCommodityClassRepository.save(agriCommodityClass);

				approvalUtil.updateRecord(DBConstants.TBL_AGRI_COMMODITY_CLASS, agriCommodityClass.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-CommodityClass" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-CommodityClass" + APIConstants.RESPONSE_UPDATE_ERROR + id);
			}
		} catch (DataIntegrityViolationException e) {
			LOGGER.error("DataIntegrityViolationException : ", e);
			return responseMessageUtil.sendResponse(false, "", "Duplicate entry for commodity class " + agriCommodityClass.getName());
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// updateAgriCommodityClassById

	public ResponseMessage primaryApproveById(int id) {
		try {
			Optional<AgriCommodityClass> foundCommodityClass = agriCommodityClassRepository.findById(id);

			if (foundCommodityClass.isPresent()) {

				AgriCommodityClass agriCommodityClass = foundCommodityClass.get();

				agriCommodityClass.setStatus(APIConstants.STATUS_APPROVED);
				agriCommodityClass = agriCommodityClassRepository.save(agriCommodityClass);

				approvalUtil.primaryApprove(DBConstants.TBL_AGRI_COMMODITY_CLASS, agriCommodityClass.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-CommodityClass" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");

			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-CommodityClass" + APIConstants.RESPONSE_PRIMARY_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage finalApproveById(int id) {

		try {
			Optional<AgriCommodityClass> foundCommodityClass = agriCommodityClassRepository.findById(id);

			if (foundCommodityClass.isPresent()) {

				AgriCommodityClass commodityClass = foundCommodityClass.get();

				commodityClass.setStatus(APIConstants.STATUS_ACTIVE);

				commodityClass = agriCommodityClassRepository.save(commodityClass);

				approvalUtil.finalApprove(DBConstants.TBL_AGRI_COMMODITY_CLASS, commodityClass.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-CommodityClass" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");

			} else {

				return responseMessageUtil.sendResponse(false, "",
						"Agri-CommodityClass" + APIConstants.RESPONSE_FINAL_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproveById

	public ResponseMessage deleteAgriCommodityClassById(int id) {

		try {
			Optional<AgriCommodityClass> foundCommodityClass = agriCommodityClassRepository.findById(id);

			if (foundCommodityClass.isPresent()) {

				AgriCommodityClass commodityClass = foundCommodityClass.get();
				commodityClass.setStatus(APIConstants.STATUS_DELETED);

				commodityClass = agriCommodityClassRepository.save(commodityClass);

				approvalUtil.addRecord(DBConstants.TBL_AGRI_COMMODITY_CLASS, commodityClass.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-CommodityClass" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-CommodityClass" + APIConstants.RESPONSE_DELETE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}// deleteAgriCommodityClassById

	public ResponseMessage rejectById(int id) {

		try {
			Optional<AgriCommodityClass> foundCommodityClass = agriCommodityClassRepository.findById(id);

			if (foundCommodityClass.isPresent()) {

				AgriCommodityClass commodityClass = foundCommodityClass.get();
				commodityClass.setStatus(APIConstants.STATUS_REJECTED);

				commodityClass = agriCommodityClassRepository.save(commodityClass);

				approvalUtil.addRecord(DBConstants.TBL_AGRI_COMMODITY_CLASS, commodityClass.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-CommodityClass" + APIConstants.RESPONSE_REJECT_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-CommodityClass" + APIConstants.RESPONSE_REJECT_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}// rejectById

	public AgriCommodityClass findAgriCommodityClassById(int id) {
		try {
			Optional<AgriCommodityClass> foundCommodityClass = agriCommodityClassRepository.findById(id);
			if (foundCommodityClass.isPresent()) {
				return foundCommodityClass.get();
			} else {
				throw new DoesNotExistException("Agri-CommodityClass" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw new DoesNotExistException("Agri-Activity" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
		}
	}// findAgriCommodityClassById

}
