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
import in.cropdata.cdtmasterdata.dto.interfaces.AgriCommodityInfo;
import in.cropdata.cdtmasterdata.dto.interfaces.AgriFertilizerInfDto;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.model.AgriFertilizer;
import in.cropdata.cdtmasterdata.model.AgriFertilizerMissing;
import in.cropdata.cdtmasterdata.repository.AgriFertilizerMissingRepository;
import in.cropdata.cdtmasterdata.repository.AgriFertilizerRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class AgriFertilizerService {

	@Autowired
	private AgriFertilizerRepository agriFertilizerRepository;
	
	@Autowired
	private AgriFertilizerMissingRepository agriFertilizerMissingRepository;

	@Autowired
	private AclHistoryUtil approvalUtil;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	public List<AgriFertilizerInfDto> getAllAgriFertilizer() {

		try {
			List<AgriFertilizerInfDto> list = agriFertilizerRepository.getAgriFertilizerList();
			
			return list;

		} catch (Exception e) {
			throw e;
		}
	}// getAllAgriFertilizer

//	public Page<AgriFertilizerInfDto> getAllAgriFertilizerPaginated(int page, int size) {
//
//		try {
//			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").ascending());
//
//			return agriFertilizerRepository.getAgriFertilizerList(sortedByIdDesc);
//
//		} catch (Exception e) {
//			throw e;
//		}
//	}
	
	public Page<AgriFertilizerInfDto> getAllAgriFertilizerPaginated(int page, int size, String searchText, int isValid,String missing,String stateCode,String seasonId,String doseFactorId,String commodityId,String name,String filter) {

		try {
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").ascending());
			Page<AgriFertilizerInfDto> fertilizerList;
			if("1".equals(filter)) {
				if("".equals(stateCode)) {
					stateCode = null;
				}
				if("".equals(seasonId)) {
					seasonId = null;
				}
				if("".equals(doseFactorId)) {
					doseFactorId = null;
				}
				if("".equals(commodityId)) {
					commodityId = null;
				}
				if("".equals(name)) {
					name = null;
				}
				fertilizerList = agriFertilizerRepository.getAgriFertilizerListByMultiSearchFilters(sortedByIdDesc, stateCode,seasonId,doseFactorId,commodityId,name);
				
			}else if("0".equals(missing)) {
			if (isValid == 0) {
				fertilizerList = agriFertilizerRepository.getAgriFertilizerListInvalidated(sortedByIdDesc, searchText);
			} else {
				fertilizerList = agriFertilizerRepository.getAgriFertilizerList(sortedByIdDesc, searchText);
			}
			}else {
				if (isValid == 0) {
					fertilizerList = agriFertilizerRepository.getAgriFertilizerMissingListInvalidated(sortedByIdDesc, searchText);
				} else {
					fertilizerList = agriFertilizerRepository.getAgriFertilizerMissingList(sortedByIdDesc, searchText);
				}	
			}

			return fertilizerList;

		} catch (Exception e) {
			throw e;
		}
	}//getAllAgriFertilizerPaginated
	
	public ResponseMessage addAgriFertilizer(AgriFertilizer agriFertilizer) {

		try {
			agriFertilizer = agriFertilizerRepository.save(agriFertilizer);

			approvalUtil.addRecord(DBConstants.TBL_AGRI_FERTILIZER, agriFertilizer.getId());

			return responseMessageUtil.sendResponse(true, "Agri-Fertilizer" + APIConstants.RESPONSE_ADD_SUCCESS, "");

		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// addAgriFertilizer

	public ResponseMessage updateAgriFertilizerById(int id, AgriFertilizer agriFertilizer) {
		try {
			Optional<AgriFertilizer> foundVareity = agriFertilizerRepository.findById(id);

			if (foundVareity.isPresent()) {
				agriFertilizer.setId(id);
				agriFertilizer = agriFertilizerRepository.save(agriFertilizer);

				approvalUtil.updateRecord(DBConstants.TBL_AGRI_FERTILIZER, agriFertilizer.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Fertilizer" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Fertilizer" + APIConstants.RESPONSE_UPDATE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// updateAgriFertilizerById

	public ResponseMessage primaryApproveById(int id) {
		try {
			Optional<AgriFertilizer> foundFertilizer = agriFertilizerRepository.findById(id);
			if (foundFertilizer.isPresent()) {
				AgriFertilizer fertilizer = foundFertilizer.get();
				fertilizer.setStatus(APIConstants.STATUS_APPROVED);

				fertilizer = agriFertilizerRepository.save(fertilizer);

				approvalUtil.primaryApprove(DBConstants.TBL_AGRI_FERTILIZER, fertilizer.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Fertilizer" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");
			} else {

				return responseMessageUtil.sendResponse(false, "",
						"Agri-Fertilizer" + APIConstants.RESPONSE_PRIMARY_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage finalApproveById(int id) {

		try {

			Optional<AgriFertilizer> foundFertilizer = agriFertilizerRepository.findById(id);
			if (foundFertilizer.isPresent()) {
				AgriFertilizer fertilizer = foundFertilizer.get();
				fertilizer.setStatus(APIConstants.STATUS_ACTIVE);

				fertilizer = agriFertilizerRepository.save(fertilizer);

				approvalUtil.finalApprove(DBConstants.TBL_AGRI_FERTILIZER, fertilizer.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Fertilizer" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Fertilizer" + APIConstants.RESPONSE_FINAL_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproveById

	public ResponseMessage deleteAgriFertilizerById(int id) {
		try {
			Optional<AgriFertilizer> foundFertilizer = agriFertilizerRepository.findById(id);
			if (foundFertilizer.isPresent()) {
				AgriFertilizer fertilizer = foundFertilizer.get();
				fertilizer.setStatus(APIConstants.STATUS_DELETED);

				fertilizer = agriFertilizerRepository.save(fertilizer);

				approvalUtil.delete(DBConstants.TBL_AGRI_FERTILIZER, fertilizer.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Fertilizer" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");
			} else {

				return responseMessageUtil.sendResponse(false, "",
						"Agri-Fertilizer" + APIConstants.RESPONSE_DELETE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}

	public ResponseMessage rejectById(int id) {
		try {
			Optional<AgriFertilizer> foundFertilizer = agriFertilizerRepository.findById(id);
			if (foundFertilizer.isPresent()) {
				AgriFertilizer fertilizer = foundFertilizer.get();
				fertilizer.setStatus(APIConstants.STATUS_REJECTED);

				fertilizer = agriFertilizerRepository.save(fertilizer);

				approvalUtil.delete(DBConstants.TBL_AGRI_FERTILIZER, fertilizer.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Fertilizer" + APIConstants.RESPONSE_REJECT_SUCCESS + id, "");
			} else {

				return responseMessageUtil.sendResponse(false, "",
						"Agri-Fertilizer" + APIConstants.RESPONSE_REJECT_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}// rejectById

	public AgriFertilizer findAgriFertilizerById(int id) {

		try {
			Optional<AgriFertilizer> foundFertilizer = agriFertilizerRepository.findById(id);

			if (foundFertilizer.isPresent()) {
				return foundFertilizer.get();

			} else {
				throw new DoesNotExistException("Agri-Fertilizer" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findAgriFertilizerById
	
	@Transactional
	public ResponseMessage moveToMaster(int id) {
		try {
			Optional<AgriFertilizerMissing> foundMissingAgriFertilizerMissing = agriFertilizerMissingRepository.findById(id);

			if (foundMissingAgriFertilizerMissing.isPresent()) {
				AgriFertilizer agriFertilizer = new AgriFertilizer();
				AgriFertilizerMissing agriFertilizerMissing = foundMissingAgriFertilizerMissing.get();
				
				agriFertilizer.setStateCode(agriFertilizerMissing.getStateCode());
				agriFertilizer.setSeasonId(agriFertilizerMissing.getSeasonId());
				agriFertilizer.setDoseFactorId(agriFertilizerMissing.getDoseFactorId());
				agriFertilizer.setCommodityId(agriFertilizerMissing.getCommodityId());
				agriFertilizer.setName(agriFertilizerMissing.getName());
				agriFertilizer.setUomId(agriFertilizerMissing.getUomId());
				agriFertilizer.setDose(agriFertilizerMissing.getDose());
				agriFertilizer.setNote(agriFertilizerMissing.getNote());
				agriFertilizer.setStatus(agriFertilizerMissing.getStatus());

				AgriFertilizer savedAgriFertilizer = agriFertilizerRepository.save(agriFertilizer);
				
				agriFertilizerMissingRepository.deleteById(id);


				approvalUtil.addRecord(DBConstants.TBL_AGRI_FERTILIZER, savedAgriFertilizer.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Fertilizer-Missing" + APIConstants.RESPONSE_MOVE_TO_MASTER_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Fertilizer-Missing" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}
	
}// AgriFertilizerService
