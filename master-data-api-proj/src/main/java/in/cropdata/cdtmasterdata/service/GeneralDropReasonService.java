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
import in.cropdata.cdtmasterdata.model.GeneralDropReason;
import in.cropdata.cdtmasterdata.repository.GeneralDropReasonRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class GeneralDropReasonService {

	@Autowired
	private GeneralDropReasonRepository generalDropReasonRepository;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	@Autowired
	private AclHistoryUtil approvalUtil;

	@GetMapping("/all-general-drop-reason")
	public List<GeneralDropReason> getAllGeneralDropReason() {
		try {
			List<GeneralDropReason> list = generalDropReasonRepository.findAll();

			return list;
		} catch (Exception e) {
			throw e;
		}
	}// getAllgeneralDropReasons

	public Page<GeneralDropReason> getAllGeneralDropReasonPaginated(int page, int size) {
		try {
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());
			Page<GeneralDropReason> DropReasonList = generalDropReasonRepository.findAll(sortedByIdDesc);
			return DropReasonList;
		} catch (Exception e) {
			throw e;
		}
	}// getAllgeneralDropReasonPaginated

	public ResponseMessage addGeneralDropReason(GeneralDropReason generalDropReason) {

		try {

			final String dropReason = generalDropReason.getDropReason().strip();
			generalDropReason.setDropReason(dropReason);

			if (generalDropReasonRepository.findByDropReason(dropReason).isEmpty())
			{
				generalDropReason = generalDropReasonRepository.save(generalDropReason);

				approvalUtil.addRecord(DBConstants.TBL_GENERAL_DROP_REASON, generalDropReason.getId());

				return responseMessageUtil.sendResponse(true, "general-DropReason" + APIConstants.RESPONSE_ADD_SUCCESS, "");
			} else
			{
				return responseMessageUtil.sendResponse(false, "",
						"general-DropReason".concat(APIConstants.RESPONSE_ALREADY_EXIST).concat(dropReason));
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// addAllgeneralDropReason

	public ResponseMessage updateGeneralDropReasonById(int id, GeneralDropReason generalDropReason) {
		try {
			Optional<GeneralDropReason> foundDropReason = generalDropReasonRepository.findById(id);

			if (foundDropReason.isPresent()) {

				generalDropReason.setId(id);
				generalDropReason = generalDropReasonRepository.save(generalDropReason);

				approvalUtil.updateRecord(DBConstants.TBL_GENERAL_DROP_REASON, generalDropReason.getId());

				return responseMessageUtil.sendResponse(true,
						"general-DropReason" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"general-DropReason" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}// updategeneralDropReasonById

	public ResponseMessage finalApproveById(int id) {
		try {
			Optional<GeneralDropReason> foundDropReason = generalDropReasonRepository.findById(id);

			if (foundDropReason.isPresent()) {

				GeneralDropReason DropReason = foundDropReason.get();
				DropReason.setStatus(APIConstants.STATUS_ACTIVE);
				DropReason = generalDropReasonRepository.save(DropReason);

				approvalUtil.finalApprove(DBConstants.TBL_GENERAL_DROP_REASON, DropReason.getId());

				return responseMessageUtil.sendResponse(true,
						"general-DropReason" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"general-DropReason" + APIConstants.RESPONSE_NO_RECORD_FOUND);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproveById

	public ResponseMessage primaryApproveById(int id) {
		try {
			Optional<GeneralDropReason> foundGeneralDropReason = generalDropReasonRepository.findById(id);

			if (foundGeneralDropReason.isPresent()) {

				GeneralDropReason DropReason = foundGeneralDropReason.get();
				DropReason.setStatus(APIConstants.STATUS_APPROVED);
				DropReason = generalDropReasonRepository.save(DropReason);

				approvalUtil.primaryApprove(DBConstants.TBL_GENERAL_DROP_REASON, DropReason.getId());

				return responseMessageUtil.sendResponse(true,
						"General-DropReason" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-IncomeSource" + APIConstants.RESPONSE_NO_RECORD_FOUND);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage deleteGeneralDropReasonById(int id) {
		try {
			Optional<GeneralDropReason> foundDropReason = generalDropReasonRepository.findById(id);

			if (foundDropReason.isPresent()) {

				GeneralDropReason DropReason = foundDropReason.get();
				DropReason.setStatus(APIConstants.STATUS_DELETED);
				DropReason = generalDropReasonRepository.save(DropReason);

				approvalUtil.delete(DBConstants.TBL_GENERAL_DROP_REASON, DropReason.getId());

				return responseMessageUtil.sendResponse(true,
						"general-DropReason" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"general-DropReason" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// deletegeneralDropReasonById

	public ResponseMessage rejectById(int id) {
		try {
			Optional<GeneralDropReason> foundDropReason = generalDropReasonRepository.findById(id);

			if (foundDropReason.isPresent()) {

				GeneralDropReason DropReason = foundDropReason.get();
				DropReason.setStatus(APIConstants.STATUS_REJECTED);
				DropReason = generalDropReasonRepository.save(DropReason);

				approvalUtil.delete(DBConstants.TBL_GENERAL_DROP_REASON, DropReason.getId());

				return responseMessageUtil.sendResponse(true,
						"general-DropReason" + APIConstants.RESPONSE_REJECT_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"general-DropReason" + APIConstants.RESPONSE_REJECT_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// rejectById

	public GeneralDropReason findGeneralDropReasonById(int id) {
		try {
			Optional<GeneralDropReason> foundDropReason = generalDropReasonRepository.findById(id);
			if (foundDropReason.isPresent()) {

				return foundDropReason.get();
			} else {
				throw new DoesNotExistException("general-DropReason" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findgeneralDropReasonById

}
