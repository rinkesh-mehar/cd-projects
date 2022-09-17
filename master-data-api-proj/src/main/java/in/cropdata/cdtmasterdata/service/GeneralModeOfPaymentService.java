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
import in.cropdata.cdtmasterdata.model.GeneralModeOfPayment;
import in.cropdata.cdtmasterdata.repository.GeneralModeOfPaymentRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class GeneralModeOfPaymentService {

	@Autowired
	private GeneralModeOfPaymentRepository generalModeOfPaymentRepository;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	@Autowired
	private AclHistoryUtil approvalUtil;

	@GetMapping("/all-general-mode-of-Payment")
	public List<GeneralModeOfPayment> getAllGeneralModeOfPayment() {
		try {
			List<GeneralModeOfPayment> list = generalModeOfPaymentRepository.findAll();

			return list;
		} catch (Exception e) {
			throw e;
		}
	}// getAllGeneralModeOfPayments

	public Page<GeneralModeOfPayment> getAllGeneralModeOfPaymentPaginated(int page, int size) {
		try {
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());
			Page<GeneralModeOfPayment> ModeOfPaymentList = generalModeOfPaymentRepository.findAll(sortedByIdDesc);
			return ModeOfPaymentList;
		} catch (Exception e) {
			throw e;
		}
	}// getAllGeneralModeOfPaymentPaginated

    public ResponseMessage addGeneralModeOfPayment(GeneralModeOfPayment generalModeOfPayment)
    {
        try
        {
            final String modeOfPayment = generalModeOfPayment.getModeOfPayment().strip();
            generalModeOfPayment.setModeOfPayment(modeOfPayment);

            if (generalModeOfPaymentRepository.findByModeOfPayment(modeOfPayment).isEmpty())
            {
                generalModeOfPayment = generalModeOfPaymentRepository.save(generalModeOfPayment);

                approvalUtil.addRecord(DBConstants.TBL_GENERAL_MODE_OF_PAYMENT, generalModeOfPayment.getId());

                return responseMessageUtil.sendResponse(true, "general-ModeOfPayment" + APIConstants.RESPONSE_ADD_SUCCESS,
                        "");
            } else
            {
                return responseMessageUtil.sendResponse(
                        false, "",
                        "general-ModeOfPayment".concat(APIConstants.RESPONSE_ALREADY_EXIST).concat(modeOfPayment));
            }
        } catch (Exception e)
        {
            return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
        }
    }// addAllGeneralModeOfPayment

	public ResponseMessage updateGeneralModeOfPaymentById(int id, GeneralModeOfPayment GeneralModeOfPayment) {
		try {
			Optional<GeneralModeOfPayment> foundModeOfPayment = generalModeOfPaymentRepository.findById(id);

			if (foundModeOfPayment.isPresent()) {

				GeneralModeOfPayment.setId(id);
				GeneralModeOfPayment = generalModeOfPaymentRepository.save(GeneralModeOfPayment);

				approvalUtil.updateRecord(DBConstants.TBL_GENERAL_MODE_OF_PAYMENT, GeneralModeOfPayment.getId());

				return responseMessageUtil.sendResponse(true,
						"general-ModeOfPayment" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"general-ModeOfPayment" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}// updateGeneralModeOfPaymentById

	public ResponseMessage finalApproveById(int id) {
		try {
			Optional<GeneralModeOfPayment> foundModeOfPayment = generalModeOfPaymentRepository.findById(id);

			if (foundModeOfPayment.isPresent()) {

				GeneralModeOfPayment ModeOfPayment = foundModeOfPayment.get();
				ModeOfPayment.setStatus(APIConstants.STATUS_ACTIVE);
				ModeOfPayment = generalModeOfPaymentRepository.save(ModeOfPayment);

				approvalUtil.finalApprove(DBConstants.TBL_GENERAL_MODE_OF_PAYMENT, ModeOfPayment.getId());

				return responseMessageUtil.sendResponse(true,
						"general-ModeOfPayment" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"general-ModeOfPayment" + APIConstants.RESPONSE_NO_RECORD_FOUND);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproveById

	public ResponseMessage primaryApproveById(int id) {
		try {
			Optional<GeneralModeOfPayment> foundGeneralModeOfPayment = generalModeOfPaymentRepository.findById(id);

			if (foundGeneralModeOfPayment.isPresent()) {

				GeneralModeOfPayment ModeOfPayment = foundGeneralModeOfPayment.get();
				ModeOfPayment.setStatus(APIConstants.STATUS_APPROVED);
				ModeOfPayment = generalModeOfPaymentRepository.save(ModeOfPayment);

				approvalUtil.primaryApprove(DBConstants.TBL_GENERAL_MODE_OF_PAYMENT, ModeOfPayment.getId());

				return responseMessageUtil.sendResponse(true,
						"General-ModeOfPayment" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-IncomeSource" + APIConstants.RESPONSE_NO_RECORD_FOUND);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage deleteGeneralModeOfPaymentById(int id) {
		try {
			Optional<GeneralModeOfPayment> foundModeOfPayment = generalModeOfPaymentRepository.findById(id);

			if (foundModeOfPayment.isPresent()) {

				GeneralModeOfPayment ModeOfPayment = foundModeOfPayment.get();
				ModeOfPayment.setStatus(APIConstants.STATUS_DELETED);
				ModeOfPayment = generalModeOfPaymentRepository.save(ModeOfPayment);

				approvalUtil.delete(DBConstants.TBL_GENERAL_MODE_OF_PAYMENT, ModeOfPayment.getId());

				return responseMessageUtil.sendResponse(true,
						"general-ModeOfPayment" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"general-ModeOfPayment" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// deleteGeneralModeOfPaymentById

	public ResponseMessage rejectById(int id) {
		try {
			Optional<GeneralModeOfPayment> foundModeOfPayment = generalModeOfPaymentRepository.findById(id);

			if (foundModeOfPayment.isPresent()) {

				GeneralModeOfPayment ModeOfPayment = foundModeOfPayment.get();
				ModeOfPayment.setStatus(APIConstants.STATUS_REJECTED);
				ModeOfPayment = generalModeOfPaymentRepository.save(ModeOfPayment);

				approvalUtil.delete(DBConstants.TBL_GENERAL_MODE_OF_PAYMENT, ModeOfPayment.getId());

				return responseMessageUtil.sendResponse(true,
						"general-ModeOfPayment" + APIConstants.RESPONSE_REJECT_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"general-ModeOfPayment" + APIConstants.RESPONSE_REJECT_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// rejectById

	public GeneralModeOfPayment findGeneralModeOfPaymentById(int id) {
		try {
			Optional<GeneralModeOfPayment> foundModeOfPayment = generalModeOfPaymentRepository.findById(id);
			if (foundModeOfPayment.isPresent()) {

				return foundModeOfPayment.get();
			} else {
				throw new DoesNotExistException("general-ModeOfPayment" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findGeneralModeOfPaymentById

}
