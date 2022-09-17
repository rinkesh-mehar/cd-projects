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
import in.cropdata.cdtmasterdata.dto.interfaces.AgriHsCodeInfDto;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.model.AgriHsCode;
import in.cropdata.cdtmasterdata.model.AgriHsCodeMissing;
import in.cropdata.cdtmasterdata.repository.AgriHsCodeMissingRepository;
import in.cropdata.cdtmasterdata.repository.AgriHsCodeRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class AgriHsCodeService {

	@Autowired
	private AgriHsCodeRepository agriHsCodeRepository;
	
	@Autowired
	private AgriHsCodeMissingRepository agriHsCodeMissingRepository;


	@Autowired
	private AclHistoryUtil approvalUtil;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	public List<AgriHsCodeInfDto> getAllAgriHsCode() {

		try {
			List<AgriHsCodeInfDto> list = agriHsCodeRepository.getAgriHsCodeList();

			return list;
		} catch (Exception e) {
			throw e;
		}

	}// getAllAgriHsCode

	public Page<AgriHsCodeInfDto> getAllAgriHsCodePaginated(int page, int size, String searchText, int isValid,String missing) {

		try {
			searchText = "%"+searchText+"%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());
			Page<AgriHsCodeInfDto> hsCodeList;

			if("0".equals(missing)) {
			if (isValid == 0) {
				hsCodeList = agriHsCodeRepository.getPageAgriHsCodeListInvalidated(sortedByIdDesc, searchText);
			} else {
				hsCodeList = agriHsCodeRepository.getPageAgriHsCodeList(sortedByIdDesc,searchText);
			}
			}else {
				if (isValid == 0) {
					hsCodeList = agriHsCodeRepository.getPageAgriHsCodeMissingListInvalidated(sortedByIdDesc, searchText);
				} else {
					hsCodeList = agriHsCodeRepository.getPageAgriHsCodeMissingList(sortedByIdDesc,searchText);
				}
			}
			

			return hsCodeList;

		} catch (Exception e) {
			throw e;
		}
	}// getAllAgriHsCodePaginated

	public ResponseMessage addAgriHsCode(AgriHsCode agriHsCode) {

		try {
			agriHsCode = agriHsCodeRepository.save(agriHsCode);

			approvalUtil.addRecord(DBConstants.TBL_AGRI_HS_CODE, agriHsCode.getId());

			return responseMessageUtil.sendResponse(true, "AgriHsCode" + APIConstants.RESPONSE_ADD_SUCCESS, "");

		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// addAgriHsCode

	public ResponseMessage updateAgriHsCodeById(int id, AgriHsCode agriHsCode) {
		try {
			Optional<AgriHsCode> foundHsCode = agriHsCodeRepository.findById(id);

			if (foundHsCode.isPresent()) {

				agriHsCode.setId(id);
				agriHsCodeRepository.save(agriHsCode);

				approvalUtil.updateRecord(DBConstants.TBL_AGRI_HS_CODE, agriHsCode.getId());

				return responseMessageUtil.sendResponse(true, "AgriHsCode" + APIConstants.RESPONSE_UPDATE_SUCCESS + id,
						"");
			} else {

				return responseMessageUtil.sendResponse(false, "",
						"AgriHsCode" + APIConstants.RESPONSE_UPDATE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// updateAgriHsCodeById

	public ResponseMessage primaryApproveById(int id) {
		try {
			Optional<AgriHsCode> foundHsCode = agriHsCodeRepository.findById(id);

			if (foundHsCode.isPresent()) {

				AgriHsCode hsCode = foundHsCode.get();
				hsCode.setStatus(APIConstants.STATUS_APPROVED);

				hsCode = agriHsCodeRepository.save(hsCode);

				approvalUtil.primaryApprove(DBConstants.TBL_AGRI_HS_CODE, hsCode.getId());

				return responseMessageUtil.sendResponse(true,
						"AgriHsCode" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");

			} else {
				return responseMessageUtil.sendResponse(false, "",
						"AgriHsCode" + APIConstants.RESPONSE_PRIMARY_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage finalyApproveById(int id) {
		try {
			Optional<AgriHsCode> foundHsCode = agriHsCodeRepository.findById(id);

			if (foundHsCode.isPresent()) {

				AgriHsCode hsCode = foundHsCode.get();
				hsCode.setStatus(APIConstants.STATUS_ACTIVE);

				hsCode = agriHsCodeRepository.save(hsCode);

				approvalUtil.finalApprove(DBConstants.TBL_AGRI_HS_CODE, hsCode.getId());

				return responseMessageUtil.sendResponse(true,
						"AgriHsCode" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"AgriHsCode" + APIConstants.RESPONSE_FINAL_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage deleteAgriHsCodeById(int id) {
		try {
			Optional<AgriHsCode> foundHsCode = agriHsCodeRepository.findById(id);

			if (foundHsCode.isPresent()) {

				AgriHsCode hsCode = foundHsCode.get();
				hsCode.setStatus(APIConstants.STATUS_DELETED);

				hsCode = agriHsCodeRepository.save(hsCode);

				approvalUtil.delete(DBConstants.TBL_AGRI_HS_CODE, hsCode.getId());

				return responseMessageUtil.sendResponse(true, "AgriHsCode" + APIConstants.RESPONSE_DELETE_SUCCESS + id,
						"AgriHsCode" + APIConstants.RESPONSE_DELETE_ERROR + id);
			} else {
				return responseMessageUtil.sendResponse(false, "AgriHsCode" + APIConstants.RESPONSE_DELETE_SUCCESS + id,
						"AgriHsCode" + APIConstants.RESPONSE_DELETE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// deleteAgriHsCodeById

	public ResponseMessage rejectById(int id) {
		try {
			Optional<AgriHsCode> foundHsCode = agriHsCodeRepository.findById(id);

			if (foundHsCode.isPresent()) {

				AgriHsCode hsCode = foundHsCode.get();
				hsCode.setStatus(APIConstants.STATUS_REJECTED);

				hsCode = agriHsCodeRepository.save(hsCode);

				approvalUtil.delete(DBConstants.TBL_AGRI_HS_CODE, hsCode.getId());

				return responseMessageUtil.sendResponse(true, "AgriHsCode" + APIConstants.RESPONSE_REJECT_SUCCESS + id,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"AgriHsCode" + APIConstants.RESPONSE_REJECT_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// rejectById

	public AgriHsCode findAgriHsCodeById(int id) {
		try {
			Optional<AgriHsCode> foundHsCode = agriHsCodeRepository.findById(id);
			if (foundHsCode.isPresent()) {
				return foundHsCode.get();

			} else {

				throw new DoesNotExistException("AgriHsCode" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findAgriHsCodeById

	public List<AgriHsCode> getAllAgriHsCodeByCommodity(int commodityId) {
		return agriHsCodeRepository.findByCommodityId(commodityId);
	}//getAllAgriHsCodeByCommodity
	
	@Transactional
	public ResponseMessage moveToMaster(int id) {
		try {
			Optional<AgriHsCodeMissing> foundAgriHsCodeMissing = agriHsCodeMissingRepository.findById(id);

			if (foundAgriHsCodeMissing.isPresent()) {
				AgriHsCode agriHsCode = new AgriHsCode();
				AgriHsCodeMissing regionalAgriHsCodeMissing = foundAgriHsCodeMissing.get();
				
				agriHsCode.setCommodityId(regionalAgriHsCodeMissing.getCommodityId());
				agriHsCode.setGeneralCommodityId(regionalAgriHsCodeMissing.getGeneralCommodityId());
				agriHsCode.setCommodityClassId(regionalAgriHsCodeMissing.getCommodityClassId());
				agriHsCode.setHsCode(regionalAgriHsCodeMissing.getHsCode());
				agriHsCode.setUomId(regionalAgriHsCodeMissing.getUomId());
				agriHsCode.setDescription(regionalAgriHsCodeMissing.getDescription());
				agriHsCode.setStatus(regionalAgriHsCodeMissing.getStatus());
				
				AgriHsCode savedAgriHsCode = agriHsCodeRepository.save(agriHsCode);
				
				agriHsCodeMissingRepository.deleteById(id);


				approvalUtil.addRecord(DBConstants.TBL_AGRI_HS_CODE, savedAgriHsCode.getId());

				return responseMessageUtil.sendResponse(true,
						"AgriHsCode-Missing" + APIConstants.RESPONSE_MOVE_TO_MASTER_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"AgriHsCode-Missing" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}

}//AgriHsCodeService
