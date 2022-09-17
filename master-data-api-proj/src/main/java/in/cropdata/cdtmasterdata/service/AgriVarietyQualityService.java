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
import in.cropdata.cdtmasterdata.dto.interfaces.AgriVarietyQualityInfDto;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.model.AgriVarietyQuality;
import in.cropdata.cdtmasterdata.model.AgriVarietyQualityMissing;
import in.cropdata.cdtmasterdata.repository.AgriVarietyQualityMissingRepository;
import in.cropdata.cdtmasterdata.repository.AgriVarietyQualityRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class AgriVarietyQualityService {

	@Autowired
	private AgriVarietyQualityRepository agriVarietyQualityRepository;
	
	@Autowired
	private AgriVarietyQualityMissingRepository agriVarietyQualityMissingRepository;

	@Autowired
	private AclHistoryUtil approvalUtil;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	public List<AgriVarietyQualityInfDto> getAllAgriVarietyQuality() {
		try {

			List<AgriVarietyQualityInfDto> list = agriVarietyQualityRepository.getAgriVarietyQualityList();

			return list;
		} catch (Exception e) {
			throw e;
		}
	}// getAllagriVarietyQuality

	public Page<AgriVarietyQualityInfDto> getAgriVarietyQualityPagination(int page, int size, int isValid, String searchText,String missing) {

		try {
			searchText = "%" + searchText + "%";

			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());
			Page<AgriVarietyQualityInfDto> agriVarietyQualityInfDtos;
			if("0".equals(missing)) {
			if (isValid == 0) {
				agriVarietyQualityInfDtos = agriVarietyQualityRepository.getAgriVarietyQualityPaginationWithInvalidated(sortedByIdDesc, searchText);
			} else {
				agriVarietyQualityInfDtos = agriVarietyQualityRepository.getAgriVarietyQualityPagination(sortedByIdDesc, searchText);
			}
			}else {
				if (isValid == 0) {
					agriVarietyQualityInfDtos = agriVarietyQualityRepository.getAgriVarietyQualityMissingPaginationWithInvalidated(sortedByIdDesc, searchText);
				} else {
					agriVarietyQualityInfDtos = agriVarietyQualityRepository.getAgriVarietyQualityMissingPagination(sortedByIdDesc, searchText);
				}	
			}

			return agriVarietyQualityInfDtos;

		} catch (Exception e) {
			throw e;
		}
	}// getAgriVarietyQualityPagination

	public ResponseMessage addAgriVarietyQuality(AgriVarietyQuality agriVarietyQuality) {

		try {
			agriVarietyQuality = agriVarietyQualityRepository.save(agriVarietyQuality);

			approvalUtil.addRecord(DBConstants.TBL_AGRI_Variety_QUALITY, agriVarietyQuality.getId());

			return responseMessageUtil.sendResponse(true, "Agri-VarietyQuality" + APIConstants.RESPONSE_ADD_SUCCESS,
					"");

		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}// addagriVarietyQuality

	public ResponseMessage updateAgriVarietyQualityById(int id, AgriVarietyQuality agriVarietyQuality) {
		try {
			Optional<AgriVarietyQuality> foundVarietyQuality = agriVarietyQualityRepository.findById(id);

			if (foundVarietyQuality.isPresent()) {

				agriVarietyQuality.setId(id);
				agriVarietyQuality = agriVarietyQualityRepository.save(agriVarietyQuality);

				approvalUtil.updateRecord(DBConstants.TBL_AGRI_Variety_QUALITY, agriVarietyQuality.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-VarietyQuality" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-VarietyQuality" + APIConstants.RESPONSE_UPDATE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// updateagriVarietyQualityById

	public ResponseMessage primaryApproveById(int id) {
		try {
			Optional<AgriVarietyQuality> foundVarietyQuality = agriVarietyQualityRepository.findById(id);

			if (foundVarietyQuality.isPresent()) {

				AgriVarietyQuality agriVarietyQuality = foundVarietyQuality.get();
				agriVarietyQuality.setStatus(APIConstants.STATUS_APPROVED);

				agriVarietyQuality = agriVarietyQualityRepository.save(agriVarietyQuality);

				approvalUtil.primaryApprove(DBConstants.TBL_AGRI_Variety_QUALITY, agriVarietyQuality.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-VarietyQuality" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-VarietyQuality" + APIConstants.RESPONSE_PRIMARY_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage finalApproveById(int id) {
		try {
			Optional<AgriVarietyQuality> foundVarietyQuality = agriVarietyQualityRepository.findById(id);
			if (foundVarietyQuality.isPresent()) {

				AgriVarietyQuality agriVarietyQuality = foundVarietyQuality.get();
				agriVarietyQuality.setStatus(APIConstants.STATUS_ACTIVE);

				agriVarietyQuality = agriVarietyQualityRepository.save(agriVarietyQuality);

				approvalUtil.finalApprove(DBConstants.TBL_AGRI_Variety_QUALITY, agriVarietyQuality.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-VarietyQuality" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-VarietyQuality" + APIConstants.RESPONSE_FINAL_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproveById

	public ResponseMessage deleteAgriVarietyQualityById(int id) {

		try {
			Optional<AgriVarietyQuality> foundVarietyQuality = agriVarietyQualityRepository.findById(id);

			if (foundVarietyQuality.isPresent()) {

				AgriVarietyQuality agriVarietyQuality = foundVarietyQuality.get();
				agriVarietyQuality.setStatus(APIConstants.STATUS_DELETED);

				agriVarietyQuality = agriVarietyQualityRepository.save(agriVarietyQuality);

				approvalUtil.delete(DBConstants.TBL_AGRI_Variety_QUALITY, agriVarietyQuality.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-VarietyQuality" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-VarietyQuality" + APIConstants.RESPONSE_DELETE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// deleteagriVarietyQualityById

	public ResponseMessage rejectById(int id) {

		try {
			Optional<AgriVarietyQuality> foundVarietyQuality = agriVarietyQualityRepository.findById(id);

			if (foundVarietyQuality.isPresent()) {

				AgriVarietyQuality agriVarietyQuality = foundVarietyQuality.get();
				agriVarietyQuality.setStatus(APIConstants.STATUS_REJECTED);

				agriVarietyQuality = agriVarietyQualityRepository.save(agriVarietyQuality);

				approvalUtil.delete(DBConstants.TBL_AGRI_Variety_QUALITY, agriVarietyQuality.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-VarietyQuality" + APIConstants.RESPONSE_REJECT_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-VarietyQuality" + APIConstants.RESPONSE_REJECT_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// rejectById

	public AgriVarietyQuality findAgriVarietyQualityById(int id) {

		try {
			Optional<AgriVarietyQuality> foundVarietyQuality = agriVarietyQualityRepository.findById(id);

			if (foundVarietyQuality.isPresent()) {
				return foundVarietyQuality.get();
			} else {
				throw new DoesNotExistException("Agri-VarietyQuality" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findagriVarietyQualityById
	
	@Transactional
	public ResponseMessage moveToMaster(int id) {
		try {
			Optional<AgriVarietyQualityMissing> foundAgriVarietyQualityMissing = agriVarietyQualityMissingRepository.findById(id);

			if (foundAgriVarietyQualityMissing.isPresent()) {
				AgriVarietyQuality agriVarietyQuality = new AgriVarietyQuality();
				AgriVarietyQualityMissing agriVarietyQualityMissing = foundAgriVarietyQualityMissing.get();
				
				agriVarietyQuality.setStateCode(agriVarietyQualityMissing.getStateCode());
				agriVarietyQuality.setRegionId(agriVarietyQualityMissing.getRegionId());
				agriVarietyQuality.setCommodityId(agriVarietyQualityMissing.getCommodityId());
				agriVarietyQuality.setVarietyId(agriVarietyQualityMissing.getVarietyId());
				agriVarietyQuality.setCurrentQuality(agriVarietyQualityMissing.getCurrentQuality());
				agriVarietyQuality.setEstimatedQuality(agriVarietyQualityMissing.getEstimatedQuality());
				agriVarietyQuality.setAllowableVarianceInQuality(agriVarietyQualityMissing.getAllowableVarianceInQuality());
				agriVarietyQuality.setStatus(agriVarietyQualityMissing.getStatus());

				AgriVarietyQuality savedAgriVarietyQuality = agriVarietyQualityRepository.save(agriVarietyQuality);
				
				agriVarietyQualityMissingRepository.deleteById(id);


				approvalUtil.addRecord(DBConstants.TBL_AGRI_Variety_QUALITY, savedAgriVarietyQuality.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-VarietyQuality-Missing" + APIConstants.RESPONSE_MOVE_TO_MASTER_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-VarietyQuality-Missing" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}
	
}// agriVarietyQualityService
