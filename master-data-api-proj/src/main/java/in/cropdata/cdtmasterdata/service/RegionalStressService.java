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
import in.cropdata.cdtmasterdata.dto.interfaces.RegionalStressInfDto;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.model.RegionalStress;
import in.cropdata.cdtmasterdata.model.RegionalStressMissing;
import in.cropdata.cdtmasterdata.repository.RegionalStressMissingRepository;
import in.cropdata.cdtmasterdata.repository.RegionalStressRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class RegionalStressService {

	@Autowired
	private RegionalStressRepository regionalStressRepository;
	
	@Autowired
	private RegionalStressMissingRepository regionalStressMissingRepository;

	@Autowired
	private AclHistoryUtil approvalUtil;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	public List<RegionalStressInfDto> getAllRegionalStress() {

		try {
			List<RegionalStressInfDto> list = regionalStressRepository.getRegionalStressList();

			return list;
		} catch (Exception e) {
			throw e;
		}
	}// getAllRegionalStress


	public Page<RegionalStressInfDto> getAllRegionalStressPaginated(int page, int size,String missing, String  searchText, int isValid) {

		try {
			searchText = "%"+searchText+"%";
			Page<RegionalStressInfDto> stressList;

			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());
			

		
			if("0".equals(missing)) {
				if (isValid == 0) {
					 stressList = regionalStressRepository.getRegionalStressInvalidated(sortedByIdDesc,searchText);
					} else {
						stressList = regionalStressRepository.getRegionalStress(sortedByIdDesc,searchText);
					}
			}else {
				if (isValid == 0) {
					 stressList = regionalStressRepository.getRegionalStressMissingInvalidated(sortedByIdDesc,searchText);
					} else {
						stressList = regionalStressRepository.getRegionalStressMissing(sortedByIdDesc,searchText);
					}
			}


			return stressList;
		} catch (Exception e) {
			throw e;
		}
	}// getAllRegionalStressPaginated

	public ResponseMessage addRegionalStress(RegionalStress regionalStress) {

		try {
			regionalStress = regionalStressRepository.save(regionalStress);

			approvalUtil.addRecord(DBConstants.TBL_REGIONAL_STRESS, regionalStress.getId());

			return responseMessageUtil.sendResponse(true, "RegionalStress" + APIConstants.RESPONSE_ADD_SUCCESS, "");

		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// addAllRegionalStress

	public ResponseMessage updateRegionalStressById(int id, RegionalStress regionalStress) {
		try {
			Optional<RegionalStress> foundStress = regionalStressRepository.findById(id);
			if (foundStress.isPresent()) {

				regionalStress.setId(id);
				regionalStress = regionalStressRepository.save(regionalStress);

				approvalUtil.updateRecord(DBConstants.TBL_REGIONAL_STRESS, regionalStress.getId());

				return responseMessageUtil.sendResponse(true,
						"RegionalStress" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"RegionalStress" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// updateRegionalStressById

	public ResponseMessage primaryApproveById(int id) {
		try {
			Optional<RegionalStress> foundStress = regionalStressRepository.findById(id);

			if (foundStress.isPresent()) {

				RegionalStress regionalStress = foundStress.get();
				regionalStress.setStatus(APIConstants.STATUS_APPROVED);

				regionalStress = regionalStressRepository.save(regionalStress);

				approvalUtil.primaryApprove(DBConstants.TBL_REGIONAL_STRESS, regionalStress.getId());

				return responseMessageUtil.sendResponse(true,
						"RegionalStress" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"RegionalStress" + APIConstants.RESPONSE_NO_RECORD_FOUND);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage finalApproveById(int id) {
		try {
			Optional<RegionalStress> foundStress = regionalStressRepository.findById(id);

			if (foundStress.isPresent()) {

				RegionalStress regionalStress = foundStress.get();
				regionalStress.setStatus(APIConstants.STATUS_ACTIVE);

				regionalStress = regionalStressRepository.save(regionalStress);

				approvalUtil.finalApprove(DBConstants.TBL_REGIONAL_STRESS, regionalStress.getId());

				return responseMessageUtil.sendResponse(true,
						"RegionalStress" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"RegionalStress" + APIConstants.RESPONSE_NO_RECORD_FOUND);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproveById

	public ResponseMessage deleteRegionalStressById(int id) {
		try {
			Optional<RegionalStress> foundStress = regionalStressRepository.findById(id);

			if (foundStress.isPresent()) {

				RegionalStress regionalStress = foundStress.get();
				regionalStress.setStatus(APIConstants.STATUS_DELETED);

				regionalStress = regionalStressRepository.save(regionalStress);

				approvalUtil.delete(DBConstants.TBL_REGIONAL_STRESS, regionalStress.getId());

				return responseMessageUtil.sendResponse(true,
						"RegionalStress" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"RegionalStress" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// deleteRegionalStressById

	public RegionalStress findRegionalStressById(int id) {
		try {
			Optional<RegionalStress> foundStress = regionalStressRepository.findById(id);
			if (foundStress.isPresent()) {
				return foundStress.get();
			} else {
				throw new DoesNotExistException("RegionalStress" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findRegionalStressById
	
	public ResponseMessage rejectById(int id) {
		try {
			Optional<RegionalStress> foundStress = regionalStressRepository.findById(id);

			if (foundStress.isPresent()) {

				RegionalStress regionalStress = foundStress.get();
				regionalStress.setStatus(APIConstants.STATUS_REJECTED);
				regionalStress = regionalStressRepository.save(regionalStress);

				approvalUtil.finalApprove(DBConstants.TBL_REGIONAL_STRESS, regionalStress.getId());

				return responseMessageUtil.sendResponse(true, "Regional-Stress " + APIConstants.RESPONSE_REJECT_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "", "Regional-Stress " + APIConstants.RESPONSE_REJECT_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// rejectById
	

	@Transactional
	public ResponseMessage moveToMaster(int id) {
		try {
			Optional<RegionalStressMissing> foundMissingStress = regionalStressMissingRepository.findById(id);

			if (foundMissingStress.isPresent()) {
				RegionalStress regionalStress = new RegionalStress();
				RegionalStressMissing regionalCommodityMissing = foundMissingStress.get();
				
				regionalStress.setStateCode(regionalCommodityMissing.getStateCode());
//				regionalStress.setRegionId(regionalCommodityMissing.getRegionId());
				regionalStress.setStressId(regionalCommodityMissing.getStressId());
				regionalStress.setStatus(regionalCommodityMissing.getStatus());
				
				RegionalStress savedRegionalStress = regionalStressRepository.save(regionalStress);
				
				regionalStressMissingRepository.deleteById(id);

				approvalUtil.addRecord(DBConstants.TBL_REGIONAL_STRESS, savedRegionalStress.getId());

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
	
}// RegionalStressService
