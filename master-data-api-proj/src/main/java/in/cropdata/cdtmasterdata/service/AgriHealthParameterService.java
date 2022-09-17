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
import in.cropdata.cdtmasterdata.constants.DBConstants;
import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.model.AgriHealthParameter;
import in.cropdata.cdtmasterdata.repository.AgriHealthParameterRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class AgriHealthParameterService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AgriHealthParameterService.class);
	private static final String SERVER_ERROR_MSG = "Server Error : ";

	@Autowired
	private AgriHealthParameterRepository agriHealthParameterRepository;

//	@Autowired
//	private AgriCommodityRepositoy commodityRepositoy;
//
//	@Autowired
//	private AgriPhenophaseRepository agriPhenophaseRepository;

	@Autowired
	private AclHistoryUtil approvalUtil;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	public List<AgriHealthParameter> getAllAgriAgriHealthParameter() {

		try {
			List<AgriHealthParameter> healthParameterList = agriHealthParameterRepository.findAll(Sort.by("name"));

//			for (AgriHealthParameter healthParameter : healthParameterList) {
//
//				healthParameter = getData(healthParameter);
//
//			} // for
			return healthParameterList;
		} catch (Exception e) {
			throw e;
		}
	}
	
	public Page<AgriHealthParameter> getHealthParameterListByPagenation(Integer page, Integer size, String searchText){
		try {
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());
			return agriHealthParameterRepository.getHealthParameterListByPagenation(sortedByIdDesc, searchText);
		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Agri-Health_Parameter Data Found For Searched Text -> " + searchText);
		}
	}
	
//	private AgriHealthParameter getData(AgriHealthParameter healthParameter) {
//		try {
//			Optional<AgriCommodity> foundCommodity = commodityRepositoy.findById(healthParameter.getCommodityId());
//
//			if (foundCommodity.isPresent()) {
//				AgriCommodity commodity = foundCommodity.get();
//				healthParameter.setCommodity(commodity.getName());
//			}
//
//			Optional<AgriPhenophase> foundPhenophase = agriPhenophaseRepository
//					.findById(healthParameter.getPhenophaseId());
//
//			if (foundPhenophase.isPresent()) {
//				AgriPhenophase phenophase = foundPhenophase.get();
//				healthParameter.setPhenophase(phenophase.getName());
//			}
//
//			return healthParameter;
//
//		} catch (Exception e) {
//			throw e;
//		}
//
//	}

	public ResponseMessage addAgriHealthParameter(AgriHealthParameter agriHealthParameter) {

		try {

			agriHealthParameter = agriHealthParameterRepository.save(agriHealthParameter);

			approvalUtil.addRecord(DBConstants.TBL_AGRI_HEALTH_PARAMETER, agriHealthParameter.getId());

			return responseMessageUtil.sendResponse(true, "Agri-Health_Parameter" + APIConstants.RESPONSE_ADD_SUCCESS,
					"");

		} catch (Exception e) {

			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// addAgriHealthParameter

	public ResponseMessage updateAgriHealthParameterById(int id, AgriHealthParameter agriHealthParameter) {

		try {
			Optional<AgriHealthParameter> foundHealthParameter = agriHealthParameterRepository.findById(id);

			if (foundHealthParameter.isPresent()) {

				AgriHealthParameter update = foundHealthParameter.get();

//				if (agriHealthParameter.getCommodityId() > 0) {
//					update.setCommodityId(agriHealthParameter.getCommodityId());
//				}
//
//				if (agriHealthParameter.getPhenophaseId() > 0) {
//					update.setPhenophaseId(agriHealthParameter.getPhenophaseId());
//				}
				
				if (agriHealthParameter.getName() != null) {
					update.setName(agriHealthParameter.getName());
				}
				
				if (agriHealthParameter.getStatus() != null) {
					update.setStatus(agriHealthParameter.getStatus());
				}
				
				agriHealthParameter = agriHealthParameterRepository.save(update);

				approvalUtil.updateRecord(DBConstants.TBL_AGRI_HEALTH_PARAMETER, agriHealthParameter.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Health_Parameter" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Health_Parameter" + APIConstants.RESPONSE_UPDATE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}// updateAgriHealthParameterById

	public ResponseMessage primaryApproveById(int id) {

		try {
			Optional<AgriHealthParameter> foundHealthParameter = agriHealthParameterRepository.findById(id);

			if (foundHealthParameter.isPresent()) {

				AgriHealthParameter agriHealthParameter = foundHealthParameter.get();
				agriHealthParameter.setStatus(APIConstants.STATUS_APPROVED);

				agriHealthParameter = agriHealthParameterRepository.save(agriHealthParameter);

				approvalUtil.primaryApprove(DBConstants.TBL_AGRI_HEALTH_PARAMETER, agriHealthParameter.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Health-Parameter" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");

			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Health-Parameter" + APIConstants.RESPONSE_PRIMARY_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage finalApproveById(int id) {

		try {
			Optional<AgriHealthParameter> foundHealthParameter = agriHealthParameterRepository.findById(id);

			if (foundHealthParameter.isPresent()) {

				AgriHealthParameter agriHealthParameter = foundHealthParameter.get();
				agriHealthParameter.setStatus(APIConstants.STATUS_ACTIVE);

				agriHealthParameter = agriHealthParameterRepository.save(agriHealthParameter);

				approvalUtil.finalApprove(DBConstants.TBL_AGRI_HEALTH_PARAMETER, agriHealthParameter.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Health-Parameter" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Health-Parameter" + APIConstants.RESPONSE_FINAL_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproverById

	public ResponseMessage deleteAgriHealthParameterById(int id) {
		try {
			Optional<AgriHealthParameter> foundHealthParameter = agriHealthParameterRepository.findById(id);
			if (foundHealthParameter.isPresent()) {

				AgriHealthParameter agriHealthParameter = foundHealthParameter.get();
				agriHealthParameter.setStatus(APIConstants.STATUS_DELETED);

				agriHealthParameter = agriHealthParameterRepository.save(agriHealthParameter);

				approvalUtil.delete(DBConstants.TBL_AGRI_HEALTH_PARAMETER, agriHealthParameter.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Health-Parameter" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");
			} else {

				return responseMessageUtil.sendResponse(false, "",
						"Agri-Health-Parameter" + APIConstants.RESPONSE_DELETE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// deleteAgriHealthParameterById

	public AgriHealthParameter findAgriHealthParameterById(int id) {
		try {
			Optional<AgriHealthParameter> foundHealthParameter = agriHealthParameterRepository.findById(id);
			if (foundHealthParameter.isPresent()) {
				return foundHealthParameter.get();
			} else {
				throw new DoesNotExistException("Agri-Health-Parameter" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findAgriHealthParameterById
	
	public ResponseMessage rejectAgriHealthParameterById(int id) {

		try {
			Optional<AgriHealthParameter> foundHealthParameter = agriHealthParameterRepository.findById(id);

			if (foundHealthParameter.isPresent()) {

				AgriHealthParameter agriHealthParameter = foundHealthParameter.get();
				agriHealthParameter.setStatus(APIConstants.STATUS_REJECTED);

				agriHealthParameter = agriHealthParameterRepository.save(agriHealthParameter);

				approvalUtil.finalApprove(DBConstants.TBL_AGRI_HEALTH_PARAMETER, agriHealthParameter.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Health-Parameter" + APIConstants.RESPONSE_REJECT_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Health-Parameter" + APIConstants.RESPONSE_REJECT_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}
}
