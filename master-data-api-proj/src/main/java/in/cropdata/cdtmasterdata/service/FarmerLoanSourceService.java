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
import in.cropdata.cdtmasterdata.dto.interfaces.FarmerLoanSourceInfo;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.model.FarmerLoanSource;
import in.cropdata.cdtmasterdata.repository.FarmerLoanSourceRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class FarmerLoanSourceService {

	@Autowired
	private FarmerLoanSourceRepository farmerLoanSourceRepository;

	@Autowired
	private AclHistoryUtil approvalUtil;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	@GetMapping("/all-farmer-loansource")
	public List<FarmerLoanSource> getAllFarmerLoanSources() {
		try {
			List<FarmerLoanSource> list = farmerLoanSourceRepository.findAll();

			return list;
		} catch (Exception e) {
			throw e;
		}
	}// getAllFarmerLoanSources

//	public Page<FarmerLoanSource> getAllFarmerLoanSourcePaginated(int page, int size) {
//		try {
//			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").ascending());
//			Page<FarmerLoanSource> loanSourceList = farmerLoanSourceRepository.findAll(sortedByIdDesc);
//			return loanSourceList;
//		} catch (Exception e) {
//			throw e;
//		}
//	}// getAllFarmerLoanSourcePaginated
	
	
	public Page<FarmerLoanSourceInfo> getAllFarmerLoanSourcePaginated(int page, int size, String searchText) {

		try {
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());


			Page<FarmerLoanSourceInfo> loanSourceList = farmerLoanSourceRepository
					.findAllWithSearch(sortedByIdDesc, searchText);

			return loanSourceList;

		} catch (Exception e) {
			throw e;
		}
	}//getAllFarmerLoanSourcePaginated

	public ResponseMessage addFarmerLoanSource(FarmerLoanSource farmerLoanSource) {

		try
		{
			final String sourceName = farmerLoanSource.getName().strip();

			if (farmerLoanSourceRepository.findByName(sourceName).isEmpty())
			{
				farmerLoanSource.setName(sourceName);
				farmerLoanSource = farmerLoanSourceRepository.save(farmerLoanSource);

				approvalUtil.addRecord(DBConstants.TBL_FARMER_LOAN_SOURCE, farmerLoanSource.getId());

				return responseMessageUtil.sendResponse(true, "Farmer-LoanSource".concat(APIConstants.RESPONSE_ADD_SUCCESS), "");

			} else
			{
				return responseMessageUtil.sendResponse(
						false, "", "Farmer-LoanSource".concat(APIConstants.RESPONSE_ALREADY_EXIST).concat(sourceName));
			}

		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// addAllFarmerLoanSource

	public ResponseMessage updateFarmerLoanSourceById(int id, FarmerLoanSource farmerLoanSource) {
		try {
			Optional<FarmerLoanSource> foundLoanSource = farmerLoanSourceRepository.findById(id);

			if (foundLoanSource.isPresent()) {

				farmerLoanSource.setId(id);
				farmerLoanSource = farmerLoanSourceRepository.save(farmerLoanSource);

				approvalUtil.updateRecord(DBConstants.TBL_FARMER_LOAN_SOURCE, farmerLoanSource.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-LoanSource" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-LoanSource" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}// updateFarmerLoanSourceById

	public ResponseMessage primaryApproveById(int id) {
		try {
			Optional<FarmerLoanSource> foundLoanSource = farmerLoanSourceRepository.findById(id);

			if (foundLoanSource.isPresent()) {

				FarmerLoanSource loanSource = foundLoanSource.get();
				loanSource.setStatus(APIConstants.STATUS_APPROVED);
				loanSource = farmerLoanSourceRepository.save(loanSource);

				approvalUtil.primaryApprove(DBConstants.TBL_FARMER_LOAN_SOURCE, loanSource.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-LoanSource" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-LoanSource" + APIConstants.RESPONSE_NO_RECORD_FOUND);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage finalApproveById(int id) {
		try {
			Optional<FarmerLoanSource> foundLoanSource = farmerLoanSourceRepository.findById(id);

			if (foundLoanSource.isPresent()) {

				FarmerLoanSource loanSource = foundLoanSource.get();
				loanSource.setStatus(APIConstants.STATUS_ACTIVE);
				loanSource = farmerLoanSourceRepository.save(loanSource);

				approvalUtil.finalApprove(DBConstants.TBL_FARMER_LOAN_SOURCE, loanSource.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-LoanSource" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-LoanSource" + APIConstants.RESPONSE_NO_RECORD_FOUND);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproveById

	public ResponseMessage deleteFarmerLoanSourceById(int id) {
		try {
			Optional<FarmerLoanSource> foundLoanSource = farmerLoanSourceRepository.findById(id);

			if (foundLoanSource.isPresent()) {

				FarmerLoanSource loanSource = foundLoanSource.get();
				loanSource.setStatus(APIConstants.STATUS_DELETED);
				loanSource = farmerLoanSourceRepository.save(loanSource);

				approvalUtil.delete(DBConstants.TBL_FARMER_LOAN_SOURCE, loanSource.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-LoanSource" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-LoanSource" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// deleteFarmerLoanSourceById

	public FarmerLoanSource findFarmerLoanSourceById(int id) {
		try {
			Optional<FarmerLoanSource> foundLoanSource = farmerLoanSourceRepository.findById(id);
			if (foundLoanSource.isPresent()) {

				return foundLoanSource.get();
			} else {
				throw new DoesNotExistException("Farmer-LoanSource" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findFarmerLoanSourceById

	public ResponseMessage rejectById(int id) {
		try {
			Optional<FarmerLoanSource> foundFarmerLoanSource = farmerLoanSourceRepository.findById(id);

			if (foundFarmerLoanSource.isPresent()) {

				FarmerLoanSource farmerLoanSource = foundFarmerLoanSource.get();
				farmerLoanSource.setStatus(APIConstants.STATUS_REJECTED);
				farmerLoanSource = farmerLoanSourceRepository.save(farmerLoanSource);

				approvalUtil.finalApprove(DBConstants.TBL_FARMER_LOAN_SOURCE, farmerLoanSource.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-LoanSource " + APIConstants.RESPONSE_REJECT_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-LoanSource " + APIConstants.RESPONSE_REJECT_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// rejectById

}// FarmerLoanSourceService
