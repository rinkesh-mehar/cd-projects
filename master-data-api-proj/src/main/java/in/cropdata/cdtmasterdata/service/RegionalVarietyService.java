package in.cropdata.cdtmasterdata.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
import in.cropdata.cdtmasterdata.dto.interfaces.RegionalVarietyInfDto;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.model.RegionalVariety;
import in.cropdata.cdtmasterdata.model.RegionalVarietyMissing;
import in.cropdata.cdtmasterdata.repository.RegionalVarietyMissingRepository;
import in.cropdata.cdtmasterdata.repository.RegionalVarietyRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class RegionalVarietyService {

	@Autowired
	private RegionalVarietyRepository regionVarietyRepository;
	
	@Autowired
	private RegionalVarietyMissingRepository regionalVarietyMissingRepository;

	@Autowired
	private AclHistoryUtil approvalUtil;

	
	private ResponseMessageUtil responseMessageUtil;
	
	@PersistenceContext
	private EntityManager entityManager;

	public List<RegionalVarietyInfDto> getAllRegionVariety() {
		try {
			List<RegionalVarietyInfDto> list = regionVarietyRepository.getRegionvarietyList();

			return list;
		} catch (Exception e) {
			throw e;
		}

	}// getAllRegionVariety

//	public Page<RegionalVarietyInfDto> getAllRegionalVarietyPaginated(int page, int size, String searchText) {
//		try {
//			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").ascending());
//
//			Page<RegionalVarietyInfDto> varietyList = regionVarietyRepository.getRegionvarietyList(sortedByIdDesc, searchText);
//
//			return varietyList;
//		} catch (Exception e) {
//			throw e;
//		}
//	}// getAllRegionalVarietyPaginated
	

	@SuppressWarnings("unchecked")
	public Page<RegionalVarietyInfDto> getAllRegionalVarietyPaginated(int page, int size,String missing, String searchText, int isValid,String stateCode,String seasonId,String commodityId,String varietyId,String filter) {


		try {
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());


			Page<RegionalVarietyInfDto> varietyList = null;
	
			if("1".equals(filter)) {
				if("".equals(stateCode)) {
					stateCode = null;
				}
				if("".equals(seasonId)) {
					seasonId = null;
				}
				if("".equals(commodityId)) {
					commodityId = null;
				}
				if("".equals(varietyId)) {
					varietyId = null;
				}
				varietyList = regionVarietyRepository.getRegionvarietyListByMultiFilters(sortedByIdDesc, stateCode,seasonId,commodityId,varietyId);
				
			}else if("0".equals(missing)) {

				if (isValid == 0) {
					varietyList = regionVarietyRepository.getRegionvarietyListInvalidated(sortedByIdDesc, searchText);
				} else {
					varietyList = regionVarietyRepository.getRegionvarietyList(sortedByIdDesc, searchText);
				}

			}else {
				if (isValid == 0) {
					varietyList = regionVarietyRepository.getRegionvarietyMissingListInvalidated(sortedByIdDesc, searchText);
				} else {
					varietyList = regionVarietyRepository.getRegionvarietyMissingList(sortedByIdDesc, searchText);
				}
			}

			return varietyList;

		} catch (Exception e) {
			throw e;
		}
	}//getAllRegionalVarietyPaginated

	public ResponseMessage addRegionVariety(RegionalVariety regionVariety) {

		try {
			regionVariety = regionVarietyRepository.save(regionVariety);

			approvalUtil.addRecord(DBConstants.TBL_REGIONAL_VARIETY, regionVariety.getId());

			return responseMessageUtil.sendResponse(true, "Region-Variety" + APIConstants.RESPONSE_ADD_SUCCESS, "");

		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// addAllRegionVariety

	public ResponseMessage updateRegionVarietyById(int id, RegionalVariety regionVariety) {
		try {
			Optional<RegionalVariety> foundVariety = regionVarietyRepository.findById(id);

			if (foundVariety.isPresent()) {

				regionVariety.setId(id);
				regionVariety = regionVarietyRepository.save(regionVariety);

				approvalUtil.updateRecord(DBConstants.TBL_REGIONAL_VARIETY, regionVariety.getId());

				return responseMessageUtil.sendResponse(true,
						"Region-Variety" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Region-Variety" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// updateRegionVarietyById

	public ResponseMessage primaryApproveById(int id) {
		try {
			Optional<RegionalVariety> foundVariety = regionVarietyRepository.findById(id);

			if (foundVariety.isPresent()) {

				RegionalVariety regionalVariety = foundVariety.get();
				regionalVariety.setStatus(APIConstants.STATUS_APPROVED);

				regionalVariety = regionVarietyRepository.save(regionalVariety);

				approvalUtil.primaryApprove(DBConstants.TBL_REGIONAL_VARIETY, regionalVariety.getId());

				return responseMessageUtil.sendResponse(true,
						"Region-Variety" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Region-Variety" + APIConstants.RESPONSE_NO_RECORD_FOUND);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage finalApproveById(int id) {
		try {
			Optional<RegionalVariety> foundVariety = regionVarietyRepository.findById(id);

			if (foundVariety.isPresent()) {

				RegionalVariety regionalVariety = foundVariety.get();
				regionalVariety.setStatus(APIConstants.STATUS_ACTIVE);

				regionalVariety = regionVarietyRepository.save(regionalVariety);

				approvalUtil.finalApprove(DBConstants.TBL_REGIONAL_VARIETY, regionalVariety.getId());

				return responseMessageUtil.sendResponse(true,
						"Region-Variety" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Region-Variety" + APIConstants.RESPONSE_NO_RECORD_FOUND);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproveById

	public ResponseMessage deleteRegionVerietyById(int id) {
		try {
			Optional<RegionalVariety> foundVariety = regionVarietyRepository.findById(id);

			if (foundVariety.isPresent()) {

				RegionalVariety regionalVariety = foundVariety.get();
				regionalVariety.setStatus(APIConstants.STATUS_DELETED);

				regionalVariety = regionVarietyRepository.save(regionalVariety);

				approvalUtil.delete(DBConstants.TBL_REGIONAL_VARIETY, regionalVariety.getId());

				return responseMessageUtil.sendResponse(true,
						"Region-Variety" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Region-Variety" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// deleteRegionVerietyById

	public RegionalVariety findregioVarietyById(int id) {
		try {
			Optional<RegionalVariety> foundVariety = regionVarietyRepository.findById(id);
			if (foundVariety.isPresent()) {
				return foundVariety.get();
			} else {
				throw new DoesNotExistException("Region-Variety" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findregioVarietyById
	
	public ResponseMessage rejectById(int id) {
		try {
			Optional<RegionalVariety> foundVariety = regionVarietyRepository.findById(id);

			if (foundVariety.isPresent()) {

				RegionalVariety regionalVariety = foundVariety.get();
				regionalVariety.setStatus(APIConstants.STATUS_REJECTED);
				regionalVariety = regionVarietyRepository.save(regionalVariety);

				approvalUtil.finalApprove(DBConstants.TBL_REGIONAL_VARIETY, regionalVariety.getId());

				return responseMessageUtil.sendResponse(true, "Regional-Variety " + APIConstants.RESPONSE_REJECT_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "", "Regional-Variety " + APIConstants.RESPONSE_REJECT_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// rejectById
	
	@Transactional
	public ResponseMessage moveToMaster(int id) {
		try {
			Optional<RegionalVarietyMissing> foundMissingVariety = regionalVarietyMissingRepository.findById(id);

			if (foundMissingVariety.isPresent()) {
				RegionalVariety regionalVariety = new RegionalVariety();
				RegionalVarietyMissing regionalVarietyMissing = foundMissingVariety.get();
				
				regionalVariety.setStateCode(regionalVarietyMissing.getStateCode());
				regionalVariety.setSeasonId(regionalVarietyMissing.getSeasonId());
				regionalVariety.setCommodityId(regionalVarietyMissing.getCommodityId());
				regionalVariety.setVarietyId(regionalVarietyMissing.getVarietyId());
				regionalVariety.setSowingWeekStart(regionalVarietyMissing.getSowingWeekStart());
				regionalVariety.setSowingWeekEnd(regionalVarietyMissing.getSowingWeekEnd());
				regionalVariety.setHarvestWeekStart(regionalVarietyMissing.getHarvestWeekStart());
				regionalVariety.setHarvestWeekEnd(regionalVarietyMissing.getHarvestWeekEnd());
				regionalVariety.setStatus(regionalVarietyMissing.getStatus());				
				
				RegionalVariety savedRegionalCommodity = regionVarietyRepository.save(regionalVariety);
				
				regionalVarietyMissingRepository.deleteById(id);

				approvalUtil.addRecord(DBConstants.TBL_REGIONAL_VARIETY, savedRegionalCommodity.getId());

				return responseMessageUtil.sendResponse(true,
						"Region-Variety-Missing" + APIConstants.RESPONSE_MOVE_TO_MASTER_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Region-Variety-Missing" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}
	
}// RegionVarietyService
