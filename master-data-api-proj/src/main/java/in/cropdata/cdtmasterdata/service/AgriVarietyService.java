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
import org.springframework.web.bind.annotation.PathVariable;

import in.cropdata.cdtmasterdata.constants.APIConstants;
import in.cropdata.cdtmasterdata.constants.DBConstants;
import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.dto.interfaces.AgriVarietyInfDto;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.model.AgriVariety;
import in.cropdata.cdtmasterdata.model.AgriVarietyMissing;
import in.cropdata.cdtmasterdata.repository.AgriVarietyMissingRepository;
import in.cropdata.cdtmasterdata.repository.AgriVarietyRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class AgriVarietyService {

	@Autowired
	private AgriVarietyRepository agriVarietyRepository;
	
	@Autowired
	private AgriVarietyMissingRepository agriVarietyMissingRepository;

	@Autowired
	private AclHistoryUtil approvalUtil;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	public List<AgriVarietyInfDto> getAllAgriVariety() {

		try {
			List<AgriVarietyInfDto> list = agriVarietyRepository.getAgriVariety();

			return list;
		} catch (Exception e) {
			throw e;
		}
	}// getAllAgriVariety

	public Page<AgriVarietyInfDto> getAllAgriVarietyPaginated(int page, int size, String searchText,String missing, int isValid,String commodityId,String hsCodeId,String domesticRestrictions,String internationalRestrictions,String filter) {

		try {
			searchText = "%" + searchText + "%";

//		System.out.println("searchText--> " + searchText);

			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());
			
			Page<AgriVarietyInfDto> varietyList = null;
			if("1".equals(filter)) {
				if("".equals(commodityId)) {
					commodityId = null;
				}
				if("".equals(hsCodeId)) {
					hsCodeId = null;
				}
				if("".equals(domesticRestrictions)) {
					domesticRestrictions = null;
				}
				if("".equals(internationalRestrictions)) {
					internationalRestrictions = null;
				}
				varietyList = agriVarietyRepository.getAgriVarietyByMultiSearchFilter(sortedByIdDesc, commodityId,hsCodeId,domesticRestrictions,internationalRestrictions);
				
			}else if("0".equals(missing)) {
				if (isValid == 0) {
					varietyList = agriVarietyRepository.getAgriVarietyInvalidated(sortedByIdDesc, searchText);
				} else {
					varietyList = agriVarietyRepository.getAgriVariety(sortedByIdDesc, searchText);
				}
			}else {
				if (isValid == 0) {
					varietyList = agriVarietyRepository.getAgriVarietyMissingInvalidated(sortedByIdDesc, searchText);
				} else {
					varietyList = agriVarietyRepository.getAgriVarietyMissing(sortedByIdDesc, searchText);
				}
			}

			return varietyList;
		} catch (Exception e) {
			throw e;
		}
	}// getAllAgriVarietyPaginated

	public ResponseMessage addAgriVariety(AgriVariety agriVariety) {

		try {
			agriVariety = agriVarietyRepository.save(agriVariety);

			approvalUtil.addRecord(DBConstants.TBL_AGRI_VARIETY, agriVariety.getId());

			return responseMessageUtil.sendResponse(true, "Agri-Variety" + APIConstants.RESPONSE_ADD_SUCCESS, "");

		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// addAllAgriVariety

	public ResponseMessage updateAgriVarietyById(int id, AgriVariety agriVariety) {

		try {
			Optional<AgriVariety> foundVareity = agriVarietyRepository.findById(id);

			if (foundVareity.isPresent()) {

				agriVariety.setId(id);
				agriVariety = agriVarietyRepository.save(agriVariety);

				approvalUtil.updateRecord(DBConstants.TBL_AGRI_VARIETY, agriVariety.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Variety" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Variety" + APIConstants.RESPONSE_UPDATE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// updateAgriVarietyById

	public ResponseMessage primaryApproveById(int id) {
		try {
			Optional<AgriVariety> founVariety = agriVarietyRepository.findById(id);

			if (founVariety.isPresent()) {

				AgriVariety variety = founVariety.get();
				variety.setStatus(APIConstants.STATUS_APPROVED);

				variety = agriVarietyRepository.save(variety);

				approvalUtil.primaryApprove(DBConstants.TBL_AGRI_VARIETY, variety.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Variety" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Variety" + APIConstants.RESPONSE_PRIMARY_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage finalApproveById(int id) {
		try {
			Optional<AgriVariety> founVariety = agriVarietyRepository.findById(id);

			if (founVariety.isPresent()) {

				AgriVariety variety = founVariety.get();
				variety.setStatus(APIConstants.STATUS_ACTIVE);

				variety = agriVarietyRepository.save(variety);

				approvalUtil.finalApprove(DBConstants.TBL_AGRI_VARIETY, variety.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Variety" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Variety" + APIConstants.RESPONSE_FINAL_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproveById

	public ResponseMessage deleteAgriVarietyById(int id) {
		try {
			Optional<AgriVariety> founVariety = agriVarietyRepository.findById(id);

			if (founVariety.isPresent()) {

				AgriVariety variety = founVariety.get();
				variety.setStatus(APIConstants.STATUS_DELETED);

				variety = agriVarietyRepository.save(variety);

				approvalUtil.delete(DBConstants.TBL_AGRI_VARIETY, variety.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Variety" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Variety" + APIConstants.RESPONSE_DELETE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// deleteAgriVarietyById

	public ResponseMessage rejectById(int id) {
		try {
			Optional<AgriVariety> founVariety = agriVarietyRepository.findById(id);

			if (founVariety.isPresent()) {

				AgriVariety variety = founVariety.get();
				variety.setStatus(APIConstants.STATUS_REJECTED);

				variety = agriVarietyRepository.save(variety);

				approvalUtil.delete(DBConstants.TBL_AGRI_VARIETY, variety.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Variety" + APIConstants.RESPONSE_REJECT_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Variety" + APIConstants.RESPONSE_REJECT_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// rejectById

	public AgriVariety findAgriVarietyById(int id) {
		try {
			Optional<AgriVariety> foundVariety = agriVarietyRepository.findById(id);
			if (foundVariety.isPresent()) {
				return foundVariety.get();
			} else {
				throw new DoesNotExistException("Agri-Variety" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findAgriVarietyById

	public List<AgriVarietyInfDto> getAllAgriVarietyByCommodityId(int commodityId) {
		try {
			List<AgriVarietyInfDto> list = agriVarietyRepository.findAllByCommodityId(commodityId);

			if (!list.isEmpty()) {
				return list;
			} else {
				throw new DoesNotExistException(
						"Agri-Variety : " + APIConstants.RESPONSE_NO_RECORD_FOUND);
			}
		} catch (Exception e) {
			throw e;
		}
	}

	public List<AgriVarietyInfDto> getAllAgriVarietyByStateAndCommodity(int stateCode, int commodityId) {
		try {
			List<AgriVarietyInfDto> varietyList = agriVarietyRepository.getAllAgriVarietyByStateAndCommodity(stateCode,
					commodityId);

			if (!varietyList.isEmpty()) {
				return varietyList;
			} else {
				throw new DoesNotExistException("Agri-Variety : " + APIConstants.RESPONSE_NO_RECORD_FOUND
						+ "getAllAgriVarietyByStateAndCommodity");
			}
		} catch (Exception ex) {
			throw ex;
		}
	}
	
	public List<AgriVarietyInfDto> getVarietyListByStateCodeDiscrictCodeSeasonIdAndCommodityId(int stateCode, int districtCode,
			 int seasonId, int commodityId) {
		try {
			List<AgriVarietyInfDto> list = agriVarietyRepository.getVarietyListByStateCodeDiscrictCodeSeasonIdAndCommodityId(stateCode,districtCode,seasonId,commodityId);

			if (!list.isEmpty()) {
				return list;
			} else {
//				throw new DoesNotExistException(
//						"Agri-Variety : " + APIConstants.RESPONSE_NO_RECORD_FOUND + "getAllAgriVarietyByCommodityId");
				list = null;
				return list;
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Transactional
	public ResponseMessage moveToMaster(int id) {
		try {
			Optional<AgriVarietyMissing> foundAgriVarietyMissing = agriVarietyMissingRepository.findById(id);

			if (foundAgriVarietyMissing.isPresent()) {
				AgriVariety agriVariety = new AgriVariety();
				AgriVarietyMissing regionalAgriVarietyMissing = foundAgriVarietyMissing.get();
				
				agriVariety.setCommodityId(regionalAgriVarietyMissing.getCommodityId());
				agriVariety.setName(regionalAgriVarietyMissing.getName());
				agriVariety.setVarietyCode(regionalAgriVarietyMissing.getVarietyCode());
				agriVariety.setHsCodeId(regionalAgriVarietyMissing.getHsCodeId());
				agriVariety.setDomesticRestrictions(regionalAgriVarietyMissing.getDomesticRestrictions());
				agriVariety.setInternationalRestrictions(regionalAgriVarietyMissing.getInternationalRestrictions());
				agriVariety.setStatus(regionalAgriVarietyMissing.getStatus());
				
				AgriVariety savedRegionalCommodity = agriVarietyRepository.save(agriVariety);
				
				agriVarietyMissingRepository.deleteById(id);


				approvalUtil.addRecord(DBConstants.TBL_AGRI_VARIETY, savedRegionalCommodity.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Variety-Missing" + APIConstants.RESPONSE_MOVE_TO_MASTER_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Variety-Missing" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}

}// AgriVarietyService
