package in.cropdata.cdtmasterdata.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import in.cropdata.cdtmasterdata.constants.APIConstants;
import in.cropdata.cdtmasterdata.constants.DBConstants;
import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.model.FarmerLoanPurpose;
import in.cropdata.cdtmasterdata.repository.FarmerLoanPurposeRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class FarmerLoanPurposeService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FarmerLoanPurposeService.class);
	private static final String SERVER_ERROR_MSG = "Server Error : ";


	@Autowired
	private FarmerLoanPurposeRepository farmerLoanPurposeRepository;

	@Autowired
	private AclHistoryUtil approvalUtil;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	public List<FarmerLoanPurpose> getAllFarmerLoanPurpose() {
		try {
			List<FarmerLoanPurpose> list = farmerLoanPurposeRepository.findAll();

			return list;
		} catch (Exception e) {
			throw e;
		}
	}// getAllFarmerLoanPurpose
	
	public Page<FarmerLoanPurpose> getFarmerLoanPurposeListByPagenation(Integer page, Integer size, String searchText){
		try {
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());
			return farmerLoanPurposeRepository.getFarmerLoanPurposeListByPagenation(sortedByIdDesc, searchText);
		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Farmer-LoanPurpose Data Found For Searched Text -> " + searchText);
		}
	}

	public ResponseMessage addFarmerLoanPurpose(FarmerLoanPurpose farmerLoanPurpose) {

		try {
			farmerLoanPurpose = farmerLoanPurposeRepository.save(farmerLoanPurpose);

			approvalUtil.addRecord(DBConstants.TBL_FARMER_LOAN_PURPOSE, farmerLoanPurpose.getId());

			return responseMessageUtil.sendResponse(true, "Farmer-LoanPurpose" + APIConstants.RESPONSE_ADD_SUCCESS, "");

		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// addAllFarmerLoanPurpose

	public ResponseMessage updateFarmerLoanPurposeById(int id, FarmerLoanPurpose farmerLoanPurpose) {
		try {
			Optional<FarmerLoanPurpose> foundFarmerLoanPurpose = farmerLoanPurposeRepository.findById(id);
			if (foundFarmerLoanPurpose.isPresent()) {

				farmerLoanPurpose.setId(id);
				farmerLoanPurpose = farmerLoanPurposeRepository.save(farmerLoanPurpose);

				approvalUtil.updateRecord(DBConstants.TBL_FARMER_LOAN_PURPOSE, farmerLoanPurpose.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-LoanPurpose" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-LoanPurpose" + APIConstants.RESPONSE_UPDATE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// updateFarmerLoanPurposeById

	public ResponseMessage primaryApproveById(int id) {
		try {
			Optional<FarmerLoanPurpose> foundLoanPurpose = farmerLoanPurposeRepository.findById(id);

			if (foundLoanPurpose.isPresent()) {

				FarmerLoanPurpose loanPurpose = foundLoanPurpose.get();
				loanPurpose.setStatus(APIConstants.STATUS_APPROVED);

				loanPurpose = farmerLoanPurposeRepository.save(loanPurpose);

				approvalUtil.primaryApprove(DBConstants.TBL_FARMER_LOAN_PURPOSE, loanPurpose.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-LoanPurpose" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-LoanPurpose" + APIConstants.RESPONSE_PRIMARY_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage finalApproveById(int id) {
		try {
			Optional<FarmerLoanPurpose> foundLoanPurpose = farmerLoanPurposeRepository.findById(id);

			if (foundLoanPurpose.isPresent()) {

				FarmerLoanPurpose loanPurpose = foundLoanPurpose.get();
				loanPurpose.setStatus(APIConstants.STATUS_ACTIVE);

				loanPurpose = farmerLoanPurposeRepository.save(loanPurpose);

				approvalUtil.finalApprove(DBConstants.TBL_FARMER_LOAN_PURPOSE, loanPurpose.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-LoanPurpose" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-LoanPurpose" + APIConstants.RESPONSE_FINAL_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproveById

	public ResponseMessage deleteFarmerLoanPurposeById(int id) {
		try {
			Optional<FarmerLoanPurpose> foundLoanPurpose = farmerLoanPurposeRepository.findById(id);

			if (foundLoanPurpose.isPresent()) {

				FarmerLoanPurpose loanPurpose = foundLoanPurpose.get();
				loanPurpose.setStatus(APIConstants.STATUS_DELETED);

				loanPurpose = farmerLoanPurposeRepository.save(loanPurpose);

				approvalUtil.delete(DBConstants.TBL_FARMER_LOAN_PURPOSE, loanPurpose.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-LoanPurpose" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-LoanPurpose" + APIConstants.RESPONSE_DELETE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// deleteFarmerLoanPurposeById

	public FarmerLoanPurpose findFarmerLoanPurposeById(int id) {
		try {
			Optional<FarmerLoanPurpose> foundLoanPurpose = farmerLoanPurposeRepository.findById(id);
			if (foundLoanPurpose.isPresent()) {
				return foundLoanPurpose.get();
			} else {
				throw new DoesNotExistException("Farmer-LoanPurpose" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findFarmerLoanPurposeById

	public ResponseMessage rejectById(int id) {
		try {
			Optional<FarmerLoanPurpose> foundFarmerLoanPurpose = farmerLoanPurposeRepository.findById(id);

			if (foundFarmerLoanPurpose.isPresent()) {

				FarmerLoanPurpose farmerLoanPurpose = foundFarmerLoanPurpose.get();
				farmerLoanPurpose.setStatus(APIConstants.STATUS_REJECTED);
				farmerLoanPurpose = farmerLoanPurposeRepository.save(farmerLoanPurpose);

				approvalUtil.finalApprove(DBConstants.TBL_FARMER_LOAN_PURPOSE, farmerLoanPurpose.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-LoanPurpose " + APIConstants.RESPONSE_REJECT_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-LoanPurpose " + APIConstants.RESPONSE_REJECT_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// rejectById

}// FarmerLoanPurposeService
