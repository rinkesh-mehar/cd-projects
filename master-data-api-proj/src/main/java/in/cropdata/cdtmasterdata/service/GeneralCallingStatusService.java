package in.cropdata.cdtmasterdata.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import in.cropdata.cdtmasterdata.constants.APIConstants;
import in.cropdata.cdtmasterdata.constants.DBConstants;
import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.model.GeneralCallingStatus;
import in.cropdata.cdtmasterdata.repository.GeneralCallingStatusRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class GeneralCallingStatusService {
	@Autowired
	private GeneralCallingStatusRepository generalCallingStatusRepository;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	@Autowired
	private AclHistoryUtil approvalUtil;

	@GetMapping("/all-general-calling-status")
	public List<GeneralCallingStatus> getAllGeneralCallingStatus() {
		try {
			List<GeneralCallingStatus> list = generalCallingStatusRepository.findAll();

			return list;
		} catch (Exception e) {
			throw e;
		}
	}// getAllgeneralCallingStatuss

	public Page<GeneralCallingStatus> getAllGeneralCallingStatusPaginated(int page, int size) {
		try {
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());
			Page<GeneralCallingStatus> CallingStatusList = generalCallingStatusRepository.findAll(sortedByIdDesc);
			return CallingStatusList;
		} catch (Exception e) {
			throw e;
		}
	}// getAllgeneralCallingStatusPaginated

	public ResponseMessage addGeneralCallingStatus(GeneralCallingStatus generalCallingStatus) {

		try
		{
			/**
			 * Checking the Calling Status already exist or not by calling status
			 * Removing the white space before saving Calling status
			 */
			generalCallingStatus.setCallingStatus(generalCallingStatus.getCallingStatus().strip());

			if (generalCallingStatusRepository.findByCallingStatus(generalCallingStatus.getCallingStatus()).isEmpty())
			{
				generalCallingStatus = generalCallingStatusRepository.save(generalCallingStatus);

				approvalUtil.addRecord(DBConstants.TBL_GENERAL_CALLING_STATUS, generalCallingStatus.getId());

				return responseMessageUtil.sendResponse(true, "general-Calling Status" + APIConstants.RESPONSE_ADD_SUCCESS,
						"");
			} else
			{
				return responseMessageUtil.sendResponse(false, "",
						"general-Calling Status".concat(APIConstants.RESPONSE_ALREADY_EXIST).concat(generalCallingStatus.getCallingStatus()));
			}
		} catch (Exception e)
		{
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// addAllgeneralCallingStatus

	public ResponseMessage updateGeneralCallingStatusById(int id, GeneralCallingStatus generalCallingStatus) {
		try {
			Optional<GeneralCallingStatus> foundCallingStatus = generalCallingStatusRepository.findById(id);

			if (foundCallingStatus.isPresent()) {

				generalCallingStatus.setId(id);
				generalCallingStatus = generalCallingStatusRepository.save(generalCallingStatus);

				approvalUtil.updateRecord(DBConstants.TBL_GENERAL_CALLING_STATUS, generalCallingStatus.getId());

				return responseMessageUtil.sendResponse(true,
						"general-CallingStatus" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"general-CallingStatus" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}// updategeneralCallingStatusById

	public ResponseMessage finalApproveById(int id) {
		try {
			Optional<GeneralCallingStatus> foundCallingStatus = generalCallingStatusRepository.findById(id);

			if (foundCallingStatus.isPresent()) {

				GeneralCallingStatus CallingStatus = foundCallingStatus.get();
				CallingStatus.setStatus(APIConstants.STATUS_ACTIVE);
				CallingStatus = generalCallingStatusRepository.save(CallingStatus);

				approvalUtil.finalApprove(DBConstants.TBL_GENERAL_CALLING_STATUS, CallingStatus.getId());

				return responseMessageUtil.sendResponse(true,
						"general-CallingStatus" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"general-CallingStatus" + APIConstants.RESPONSE_NO_RECORD_FOUND);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproveById

	public ResponseMessage primaryApproveById(int id) {
		try {
			Optional<GeneralCallingStatus> foundGeneralCallingStatus = generalCallingStatusRepository.findById(id);

			if (foundGeneralCallingStatus.isPresent()) {

				GeneralCallingStatus CallingStatus = foundGeneralCallingStatus.get();
				CallingStatus.setStatus(APIConstants.STATUS_APPROVED);
				CallingStatus = generalCallingStatusRepository.save(CallingStatus);

				approvalUtil.primaryApprove(DBConstants.TBL_GENERAL_CALLING_STATUS, CallingStatus.getId());

				return responseMessageUtil.sendResponse(true,
						"General-CallingStatus" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-IncomeSource" + APIConstants.RESPONSE_NO_RECORD_FOUND);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage deleteGeneralCallingStatusById(int id) {
		try {
			Optional<GeneralCallingStatus> foundCallingStatus = generalCallingStatusRepository.findById(id);

			if (foundCallingStatus.isPresent()) {

				GeneralCallingStatus CallingStatus = foundCallingStatus.get();
				CallingStatus.setStatus(APIConstants.STATUS_DELETED);
				CallingStatus = generalCallingStatusRepository.save(CallingStatus);

				approvalUtil.delete(DBConstants.TBL_GENERAL_CALLING_STATUS, CallingStatus.getId());

				return responseMessageUtil.sendResponse(true,
						"general-CallingStatus" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"general-CallingStatus" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// deletegeneralCallingStatusById

	public ResponseMessage rejectById(int id) {
		try {
			Optional<GeneralCallingStatus> foundCallingStatus = generalCallingStatusRepository.findById(id);

			if (foundCallingStatus.isPresent()) {

				GeneralCallingStatus CallingStatus = foundCallingStatus.get();
				CallingStatus.setStatus(APIConstants.STATUS_REJECTED);
				CallingStatus = generalCallingStatusRepository.save(CallingStatus);

				approvalUtil.delete(DBConstants.TBL_GENERAL_CALLING_STATUS, CallingStatus.getId());

				return responseMessageUtil.sendResponse(true,
						"general-CallingStatus" + APIConstants.RESPONSE_REJECT_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"general-CallingStatus" + APIConstants.RESPONSE_REJECT_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// rejectById

	public GeneralCallingStatus findGeneralCallingStatusById(int id) {
		try {
			Optional<GeneralCallingStatus> foundCallingStatus = generalCallingStatusRepository.findById(id);
			if (foundCallingStatus.isPresent()) {

				return foundCallingStatus.get();
			} else {
				throw new DoesNotExistException("general-CallingStatus" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findgeneralCallingStatusById

}
