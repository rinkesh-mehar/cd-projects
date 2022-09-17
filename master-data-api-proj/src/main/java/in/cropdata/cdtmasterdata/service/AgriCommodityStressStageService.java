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
import org.springframework.transaction.annotation.Transactional;

import in.cropdata.cdtmasterdata.constants.APIConstants;
import in.cropdata.cdtmasterdata.constants.DBConstants;
import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.dto.interfaces.AgriCommodityStressStageInfDto;
import in.cropdata.cdtmasterdata.dto.interfaces.AgriDistrictCommodityStressInfDto;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.model.AgriDistrictCommodityStress;
import in.cropdata.cdtmasterdata.model.AgriCommodityStressStage;
import in.cropdata.cdtmasterdata.model.AgriStressStageMissing;
import in.cropdata.cdtmasterdata.model.vo.AgriStageVO;
import in.cropdata.cdtmasterdata.repository.AgriDistrictCommodityStressRepository;
import in.cropdata.cdtmasterdata.repository.AgriStressStageMissingRepository;
import in.cropdata.cdtmasterdata.repository.AgriCommodityStressStageRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class AgriCommodityStressStageService {

	@Autowired
	private AgriCommodityStressStageRepository agriStressStageRepository;
	
	@Autowired
	private AgriStressStageMissingRepository agriStressStageMissingRepository;

	@Autowired
	private AgriDistrictCommodityStressRepository agriCommodityStressRepository;

	@Autowired
	private AclHistoryUtil approvalUtil;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AgriCommodityStressStageService.class);
	
	private static final String SERVER_ERROR_MSG = "Server Error : ";

	public List<AgriCommodityStressStageInfDto> getAgriCommodityStressStageList() {
		try {
			List<AgriCommodityStressStageInfDto> list = agriStressStageRepository.getAgriCommodityStressStageList();

			return list;

		} catch (Exception e) {
			throw e;
		}
	}// getAllAgriStressStage
	
	public Page<AgriCommodityStressStageInfDto> getAllAgriCommodityStressStagePaginated(int page, int size, String  searchText, int isValid, String missing) {

		try {
			searchText = "%"+searchText+"%";
			
//		System.out.println("searchText--> " + searchText);
		
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());
			Page<AgriCommodityStressStageInfDto> stressStageList;

			if("0".equals(missing)) {
				
			if (isValid == 0) {
				stressStageList = agriStressStageRepository.getAgriStressStageInvalidated(sortedByIdDesc, searchText);
			} else {
				stressStageList = agriStressStageRepository.getAgriCommodityStressStage(sortedByIdDesc, searchText);
			}
			}else {
				
				if (isValid == 0) {
					System.err.println("inside if...");
					stressStageList = agriStressStageRepository.getAgriStressStageMissingInvalidated(sortedByIdDesc, searchText);
				} else {
					System.err.println("inside else...");
					stressStageList = agriStressStageRepository.getAgriStressStageMissing(sortedByIdDesc, searchText);
				}
			}

			return stressStageList;
		} catch (Exception e) {
			throw e;
		}
	}// getAllAgriStressStagePaginated


	public ResponseMessage addAgriCommodityStressStage(AgriCommodityStressStage agriStressStage) {

		try {

			String phenophase = agriCommodityStressRepository.getPhenophaseByCommodityStartEndPhenophase(agriStressStage.getCommodityId(),
					agriStressStage.getStartPhenophaseId(), agriStressStage.getEndPhenophaseId(), agriStressStage.getStressId());

			if (phenophase == null){
				return responseMessageUtil.sendResponse(false, "", "Unable to fetch phenophases...");
			}

			agriStressStage.setPhenophase(phenophase);
			agriStressStage = agriStressStageRepository.save(agriStressStage);

			approvalUtil.addRecord(DBConstants.TBL_AGRI_COMMODITY_STRESS_STAG, agriStressStage.getId());

			return responseMessageUtil.sendResponse(true, "Agri Commodity Stress Stage" + APIConstants.RESPONSE_ADD_SUCCESS, "");

		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// addAgriStressStage

	public ResponseMessage updateAgriCommodityStressStageById(int id, AgriCommodityStressStage agriStressStage) {
		try {
			Optional<AgriCommodityStressStage> foundVareity = agriStressStageRepository.findById(id);

			if (foundVareity.isPresent()) {

				agriStressStage.setId(id);
				agriStressStage = agriStressStageRepository.save(agriStressStage);

				approvalUtil.updateRecord(DBConstants.TBL_AGRI_COMMODITY_STRESS_STAG, agriStressStage.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-StressStage" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");

			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri Commodity Stress Stage" + APIConstants.RESPONSE_UPDATE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// updateAgriStressStageById

	public ResponseMessage primaryApproveById(int id) {
		try {
			Optional<AgriCommodityStressStage> foundStressStage = agriStressStageRepository.findById(id);

			if (foundStressStage.isPresent()) {

				AgriCommodityStressStage stressStage = foundStressStage.get();
				stressStage.setStatus(APIConstants.STATUS_APPROVED);

				stressStage = agriStressStageRepository.save(stressStage);

				approvalUtil.primaryApprove(DBConstants.TBL_AGRI_COMMODITY_STRESS_STAG, stressStage.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-StressStage" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri Commodity Stress Stage" + APIConstants.RESPONSE_PRIMARY_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage finalApproveById(int id) {
		try {
			Optional<AgriCommodityStressStage> foundStressStage = agriStressStageRepository.findById(id);
			if (foundStressStage.isPresent()) {

				AgriCommodityStressStage stressStage = foundStressStage.get();
				stressStage.setStatus(APIConstants.STATUS_ACTIVE);

				stressStage = agriStressStageRepository.save(stressStage);

				approvalUtil.finalApprove(DBConstants.TBL_AGRI_COMMODITY_STRESS_STAG, stressStage.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri Commodity Stress Stage" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri Commodity Stress Stage" + APIConstants.RESPONSE_FINAL_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproveById

	public ResponseMessage deleteAgriCommodityStressStageById(int id) {
		try {
			Optional<AgriCommodityStressStage> foundStressStage = agriStressStageRepository.findById(id);
			if (foundStressStage.isPresent()) {

				AgriCommodityStressStage stressStage = foundStressStage.get();
				stressStage.setStatus(APIConstants.STATUS_DELETED);
				stressStage = agriStressStageRepository.save(stressStage);

				approvalUtil.delete(DBConstants.TBL_AGRI_COMMODITY_STRESS_STAG, stressStage.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri Commodity Stress Stage" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri Commodity Stress Stage" + APIConstants.RESPONSE_DELETE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// deleteAgriStressStageById

	public ResponseMessage rejectById(int id) {
		try {
			Optional<AgriCommodityStressStage> foundStressStage = agriStressStageRepository.findById(id);
			if (foundStressStage.isPresent()) {

				AgriCommodityStressStage stressStage = foundStressStage.get();
				stressStage.setStatus(APIConstants.STATUS_REJECTED);
				stressStage = agriStressStageRepository.save(stressStage);

				approvalUtil.delete(DBConstants.TBL_AGRI_COMMODITY_STRESS_STAG, stressStage.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri Commodity Stress Stage" + APIConstants.RESPONSE_REJECT_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri Commodity Stress Stage" + APIConstants.RESPONSE_REJECT_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// rejectById

	public AgriCommodityStressStage findAgriCommodityStressStageById(int id) {
		try {
			Optional<AgriCommodityStressStage> foundStressStage = agriStressStageRepository.findById(id);
			if (foundStressStage.isPresent()) {
				return foundStressStage.get();
			} else {
				throw new DoesNotExistException("Agri Commodity Stress Stage" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findAgriStressStageById

	public List<AgriDistrictCommodityStressInfDto> getByCommodityIdAndStressTypeId(int commodityId, int stressTypeId) {
		try {
//			List<AgriCommodityStress> stressList = agriCommodityStressRepository.findByCommodityIdAndStressTypeId(commodityId,
//					stressTypeId);
			
			List<AgriDistrictCommodityStressInfDto> stressList = agriCommodityStressRepository.getStressByCommodityIdAndStressTypeId(commodityId,
					stressTypeId);

			return stressList;

		} catch (Exception e) {
			throw e;
		}
	}//getByCommodityIdAndStressTypeId
	
	@Transactional
	public ResponseMessage moveToMaster(int id) {
		try {
			Optional<AgriStressStageMissing> foundMissingAgriStressStageMissing = agriStressStageMissingRepository.findById(id);

			if (foundMissingAgriStressStageMissing.isPresent()) {
				AgriCommodityStressStage agriStressStage = new AgriCommodityStressStage();
				AgriStressStageMissing agriStressStageMissing = foundMissingAgriStressStageMissing.get();
				
				agriStressStage.setCommodityId(agriStressStageMissing.getCommodityId());
				agriStressStage.setStartPhenophaseId(agriStressStageMissing.getStartPhenophaseId());
				agriStressStage.setEndPhenophaseId(agriStressStageMissing.getEndPhenophaseId());
//				agriStressStage.setStressTypeId(agriStressStageMissing.getStressTypeId());
				agriStressStage.setStressId(agriStressStageMissing.getStressId());
//				agriStressStage.setName(agriStressStageMissing.getName());
				agriStressStage.setDescription(agriStressStageMissing.getDescription());
				agriStressStage.setStatus(agriStressStageMissing.getStatus());
				
				AgriCommodityStressStage savedRegionalCommodity = agriStressStageRepository.save(agriStressStage);
				
				agriStressStageMissingRepository.deleteById(id);


				approvalUtil.addRecord(DBConstants.TBL_AGRI_COMMODITY_STRESS_STAG, savedRegionalCommodity.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri Commodity Stress Stage" + APIConstants.RESPONSE_MOVE_TO_MASTER_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri Commodity Stress Stage" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}
	
	public List<AgriStageVO> getStageListByStressId(int stressId){
	try {

		LOGGER.info("getting stage list by stress id...");
		return agriStressStageRepository.getStageListByStressId(stressId);

	} catch (Exception ex) {
		LOGGER.error(SERVER_ERROR_MSG, ex);
		throw new DoesNotExistException("Stress List Does" + APIConstants.RESPONSE_DOES_NOT_EXIST + stressId);
	}
	}
}// AgriStressStageService
