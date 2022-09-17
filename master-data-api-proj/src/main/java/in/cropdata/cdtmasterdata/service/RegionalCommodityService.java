package in.cropdata.cdtmasterdata.service;

import java.util.List;
import java.util.Optional;

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
import in.cropdata.cdtmasterdata.dto.interfaces.RegionalCommodityDtoInf;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.model.RegionalCommodity;
import in.cropdata.cdtmasterdata.model.RegionalCommodityMissing;
import in.cropdata.cdtmasterdata.repository.RegionalCommodityMissingRepository;
import in.cropdata.cdtmasterdata.repository.RegionalCommodityRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class RegionalCommodityService {
	
	@Autowired
	private RegionalCommodityRepository regionCommodityRepository;
	
	@Autowired
	private RegionalCommodityMissingRepository regionalCommodityMissingRepository;

	@Autowired
	private AclHistoryUtil approvalUtil;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	public List<RegionalCommodity> getAllAgriRegionalCommodity() {

		try {
			List<RegionalCommodity> list = regionCommodityRepository.findAll();

			return list;
		} catch (Exception e) {
			throw e;
		}

	}// getAllAgriRegionalCommodity

	public Page<RegionalCommodityDtoInf> getAllRegionalCommodityPaginated(int page, int size,String missing, String searchText, int isValid) {
		try {
			searchText = "%"+searchText+"%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());

			Page<RegionalCommodityDtoInf> commodityList;


			if("0".equals(missing)) {
				if (isValid == 0) {
					commodityList = regionCommodityRepository.getRegionalCommodityInvalidated(sortedByIdDesc, searchText);
				} else {
					commodityList = regionCommodityRepository.getRegionalCommodity(sortedByIdDesc, searchText);
				}
			}else {
				if (isValid == 0) {
					commodityList = regionCommodityRepository.getRegionalCommodityMissingInvalidated(sortedByIdDesc, searchText);
				} else {
					commodityList = regionCommodityRepository.getMissingRegionalCommodity(sortedByIdDesc, searchText);
				}
			}

			return commodityList;
		} catch (Exception e) {
			throw e;
		}
	}

	public ResponseMessage addregionCommodity(RegionalCommodity regionCommodity) {

		try {
			regionCommodity = regionCommodityRepository.save(regionCommodity);

			approvalUtil.addRecord(DBConstants.TBL_REGIONAL_COMMODITY, regionCommodity.getId());

			return responseMessageUtil.sendResponse(true, "Region-Commodity" + APIConstants.RESPONSE_ADD_SUCCESS, "");

		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// addAllregionCommodity

	public ResponseMessage updateregionCommodityById(int id, RegionalCommodity regionCommodity) {
		try {
			Optional<RegionalCommodity> foundCommodity = regionCommodityRepository.findById(id);

			if (foundCommodity.isPresent()) {

				regionCommodity.setId(id);
				regionCommodity = regionCommodityRepository.save(regionCommodity);

				approvalUtil.updateRecord(DBConstants.TBL_REGIONAL_COMMODITY, regionCommodity.getId());

				return responseMessageUtil.sendResponse(true,
						"Region-Commodity" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Region-Commodity" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// updateregionCommodityById

	public ResponseMessage primaryApproveById(int id) {
		try {
			Optional<RegionalCommodity> foundCommodity = regionCommodityRepository.findById(id);

			if (foundCommodity.isPresent()) {

				RegionalCommodity regionCommodity = foundCommodity.get();
				regionCommodity.setStatus(APIConstants.STATUS_APPROVED);

				regionCommodity = regionCommodityRepository.save(regionCommodity);

				approvalUtil.primaryApprove(DBConstants.TBL_REGIONAL_COMMODITY, regionCommodity.getId());

				return responseMessageUtil.sendResponse(true,
						"Region-Commodity" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Region-Commodity" + APIConstants.RESPONSE_NO_RECORD_FOUND);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage finalApproveById(int id) {
		try {
			Optional<RegionalCommodity> foundCommodity = regionCommodityRepository.findById(id);

			if (foundCommodity.isPresent()) {

				RegionalCommodity regionCommodity = foundCommodity.get();
				regionCommodity.setStatus(APIConstants.STATUS_ACTIVE);

				regionCommodity = regionCommodityRepository.save(regionCommodity);

				approvalUtil.finalApprove(DBConstants.TBL_REGIONAL_COMMODITY, regionCommodity.getId());

				return responseMessageUtil.sendResponse(true,
						"Region-Commodity" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Region-Commodity" + APIConstants.RESPONSE_NO_RECORD_FOUND);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproveById

	public ResponseMessage deleteregionCommodityById(int id) {
		try {
			Optional<RegionalCommodity> foundCommodity = regionCommodityRepository.findById(id);

			if (foundCommodity.isPresent()) {

				RegionalCommodity regionCommodity = foundCommodity.get();
				regionCommodity.setStatus(APIConstants.STATUS_DELETED);

				regionCommodity = regionCommodityRepository.save(regionCommodity);

				approvalUtil.delete(DBConstants.TBL_REGIONAL_COMMODITY, regionCommodity.getId());

				return responseMessageUtil.sendResponse(true,
						"Region-Commodity" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Region-Commodity" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// deleteregionCommodityById

	public RegionalCommodity findregionCommodityById(int id) {

		try {
			Optional<RegionalCommodity> foundCommodity = regionCommodityRepository.findById(id);

			if (foundCommodity.isPresent()) {
				return foundCommodity.get();
			} else {
				throw new DoesNotExistException("Region-Commodity" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findregionCommodityById
	
	public ResponseMessage rejectById(int id) {
		try {
			Optional<RegionalCommodity> foundCommodity = regionCommodityRepository.findById(id);

			if (foundCommodity.isPresent()) {

				RegionalCommodity regionalCommodity = foundCommodity.get();
				regionalCommodity.setStatus(APIConstants.STATUS_REJECTED);
				regionalCommodity = regionCommodityRepository.save(regionalCommodity);

				approvalUtil.finalApprove(DBConstants.TBL_REGIONAL_COMMODITY, regionalCommodity.getId());

				return responseMessageUtil.sendResponse(true, "Region-Commodity " + APIConstants.RESPONSE_REJECT_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "", "Region-Commodity " + APIConstants.RESPONSE_REJECT_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// rejectById
	
	@Transactional
	public ResponseMessage moveToMaster(int id) {
		try {
			Optional<RegionalCommodityMissing> foundMissingCommodity = regionalCommodityMissingRepository.findById(id);

			if (foundMissingCommodity.isPresent()) {
				RegionalCommodity regionalCommodity = new RegionalCommodity();
				RegionalCommodityMissing regionalCommodityMissing = foundMissingCommodity.get();
				regionalCommodity.setStateCode(regionalCommodityMissing.getStateCode());
				regionalCommodity.setRegionId(regionalCommodityMissing.getRegionId());
				regionalCommodity.setSeasonId(regionalCommodityMissing.getSeasonId());
				regionalCommodity.setCommodityId(regionalCommodityMissing.getCommodityId());
				regionalCommodity.setTargetValue(regionalCommodityMissing.getTargetValue());
				regionalCommodity.setMinLotSize(regionalCommodityMissing.getMinLotSize());
				regionalCommodity.setMaxRigtsInLot(regionalCommodityMissing.getMaxRigtsInLot());
				regionalCommodity.setHarvestRelaxation(regionalCommodityMissing.getHarvestRelaxation());
				regionalCommodity.setStatus(regionalCommodityMissing.getStatus());
				RegionalCommodity savedRegionalCommodity = regionCommodityRepository.save(regionalCommodity);
				
				regionalCommodityMissingRepository.deleteById(id);

				System.out.println("RCom Id : " + savedRegionalCommodity.getId());
				approvalUtil.addRecord(DBConstants.TBL_REGIONAL_COMMODITY, savedRegionalCommodity.getId());

				return responseMessageUtil.sendResponse(true,
						"Region-Commodity-Missing" + APIConstants.RESPONSE_MOVE_TO_MASTER_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Region-Commodity-Missing" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}

}
