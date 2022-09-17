package in.cropdata.cdtmasterdata.service;

import java.util.List;
import java.util.Optional;

import in.cropdata.cdtmasterdata.dto.interfaces.FarmerGovtOfficialInfDto;
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
import in.cropdata.cdtmasterdata.model.FarmerGovtDepartment;
import in.cropdata.cdtmasterdata.model.FarmerGovtOfficialDesignation;
import in.cropdata.cdtmasterdata.repository.FarmerGovtDepartmentRepository;
import in.cropdata.cdtmasterdata.repository.FarmerGovtOfficialDesignationRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class FarmerGovtOfficialDesignationService {

	@Autowired
	private FarmerGovtOfficialDesignationRepository farmerGovtOfficialDesignationRepository;

	@Autowired
	private FarmerGovtDepartmentRepository farmerGovtDepartmentRepository;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;


	@Autowired
	private AclHistoryUtil approvalUtil;

	public List<FarmerGovtOfficialDesignation> getAllFarmerGovtOfficialDesignation() {
		try {
			List<FarmerGovtOfficialDesignation> list = farmerGovtOfficialDesignationRepository.findAll();
			
			for (FarmerGovtOfficialDesignation farmerGovtOfficialDesignation : list) {
				farmerGovtOfficialDesignation = getData(farmerGovtOfficialDesignation);
			}

			return list;
		} catch (Exception e) {
			throw e;
		}
	}// getAllFarmerGovtOfficialDesignations

	public Page<FarmerGovtOfficialInfDto> getAllFarmerGovtOfficialDesignationPaginated(int page, int size, String searchText, int isValid)
	{
		try
		{
				searchText = "%" + searchText + "%";
			Pageable sortByIdASC = PageRequest.of(page, size, Sort.by("id").descending());
			if (isValid == 0) {
				return farmerGovtOfficialDesignationRepository.findAllWithSearchInvalidated(sortByIdASC, searchText);
			} else{
				return farmerGovtOfficialDesignationRepository.findAllWithSearch(sortByIdASC, searchText);
			}

		} catch (Exception e)
		{
			throw e;
		}
	}


	public ResponseMessage addFarmerGovtOfficialDesignation(FarmerGovtOfficialDesignation farmerGovtOfficialDesignation) {

		try {
			farmerGovtOfficialDesignation = farmerGovtOfficialDesignationRepository.save(farmerGovtOfficialDesignation);

			approvalUtil.addRecord(DBConstants.TBL_FARMER_GOVT_OFFICIAL_DESIGNATION, farmerGovtOfficialDesignation.getId());

			return responseMessageUtil.sendResponse(true, "Farmer-GovtOfficialDesignation" + APIConstants.RESPONSE_ADD_SUCCESS, "");

		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// addAllFarmerGovtOfficialDesignation
	
	private FarmerGovtOfficialDesignation getData(FarmerGovtOfficialDesignation farmerGovtOfficialDesignation) {
		try {
			Optional<FarmerGovtDepartment> foundDepart = farmerGovtDepartmentRepository
					.findById(farmerGovtOfficialDesignation.getDepartmentId());
			if (foundDepart.isPresent()) {
				FarmerGovtDepartment farmerGovtDepartment = foundDepart.get();
				farmerGovtOfficialDesignation.setDepartment(farmerGovtDepartment.getDepartmentName());
			}
			return farmerGovtOfficialDesignation;

		} catch (Exception e) {
			throw e;
		}
	}
	
	
	public ResponseMessage updateFarmerGovtOfficialDesignationById(int id, FarmerGovtOfficialDesignation farmerGovtOfficialDesignation) {

		try {
			Optional<FarmerGovtOfficialDesignation> foundFarmerGovtOfficialDesignation = farmerGovtOfficialDesignationRepository.findById(id);

			if (foundFarmerGovtOfficialDesignation.isPresent()) {

				FarmerGovtOfficialDesignation update = foundFarmerGovtOfficialDesignation.get();

				if (farmerGovtOfficialDesignation.getDepartmentId() > 0) {
					update.setDepartmentId(farmerGovtOfficialDesignation.getDepartmentId());
				}
				
				if(farmerGovtOfficialDesignation.getName() != null) {
					update.setName(farmerGovtOfficialDesignation.getName());
				}
				
				if (farmerGovtOfficialDesignation.getStatus() != null) {
					update.setStatus(farmerGovtOfficialDesignation.getStatus());
				}
				
				farmerGovtOfficialDesignation = farmerGovtOfficialDesignationRepository.save(update);

				approvalUtil.updateRecord(DBConstants.TBL_FARMER_GOVT_OFFICIAL_DESIGNATION, farmerGovtOfficialDesignation.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-GovtOfficialDesignation" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-GovtOfficialDesignation" + APIConstants.RESPONSE_UPDATE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}// updateFarmerGovtOfficialDesignationById

	public ResponseMessage finalApproveById(int id) {
		try {
			Optional<FarmerGovtOfficialDesignation> foundBankName = farmerGovtOfficialDesignationRepository.findById(id);

			if (foundBankName.isPresent()) {

				FarmerGovtOfficialDesignation BankName = foundBankName.get();
				BankName.setStatus(APIConstants.STATUS_ACTIVE);
				BankName = farmerGovtOfficialDesignationRepository.save(BankName);

				approvalUtil.finalApprove(DBConstants.TBL_FARMER_GOVT_OFFICIAL_DESIGNATION, BankName.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-GovtOfficialDesignation" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-GovtOfficialDesignation" + APIConstants.RESPONSE_NO_RECORD_FOUND);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproveById

	public ResponseMessage primaryApproveById(int id) {
		try {
			Optional<FarmerGovtOfficialDesignation> foundFarmerGovtOfficialDesignation = farmerGovtOfficialDesignationRepository.findById(id);

			if (foundFarmerGovtOfficialDesignation.isPresent()) {

				FarmerGovtOfficialDesignation BankName = foundFarmerGovtOfficialDesignation.get();
				BankName.setStatus(APIConstants.STATUS_APPROVED);
				BankName = farmerGovtOfficialDesignationRepository.save(BankName);

				approvalUtil.primaryApprove(DBConstants.TBL_FARMER_GOVT_OFFICIAL_DESIGNATION, BankName.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-GovtOfficialDesignation" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-GovtOfficialDesignation" + APIConstants.RESPONSE_NO_RECORD_FOUND);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage deleteFarmerGovtOfficialDesignationById(int id) {
		try {
			Optional<FarmerGovtOfficialDesignation> foundBankName = farmerGovtOfficialDesignationRepository.findById(id);

			if (foundBankName.isPresent()) {

				FarmerGovtOfficialDesignation BankName = foundBankName.get();
				BankName.setStatus(APIConstants.STATUS_REJECTED);
				BankName = farmerGovtOfficialDesignationRepository.save(BankName);

				approvalUtil.delete(DBConstants.TBL_FARMER_GOVT_OFFICIAL_DESIGNATION, BankName.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-GovtOfficialDesignation" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-GovtOfficialDesignation" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// deleteFarmerGovtOfficialDesignationById

	public ResponseMessage rejectById(int id) {
		try {
			Optional<FarmerGovtOfficialDesignation> foundBankName = farmerGovtOfficialDesignationRepository.findById(id);

			if (foundBankName.isPresent()) {

				FarmerGovtOfficialDesignation BankName = foundBankName.get();
				BankName.setStatus(APIConstants.STATUS_REJECTED);
				BankName = farmerGovtOfficialDesignationRepository.save(BankName);

				approvalUtil.delete(DBConstants.TBL_FARMER_GOVT_OFFICIAL_DESIGNATION, BankName.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-GovtOfficialDesignation" + APIConstants.RESPONSE_REJECT_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-GovtOfficialDesignation" + APIConstants.RESPONSE_REJECT_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// rejectById

	public FarmerGovtOfficialDesignation findFarmerGovtOfficialDesignationById(int id) {
		try {
			Optional<FarmerGovtOfficialDesignation> foundBankName = farmerGovtOfficialDesignationRepository.findById(id);
			if (foundBankName.isPresent()) {

				return foundBankName.get();
			} else {
				throw new DoesNotExistException("Farmer-GovtOfficialDesignation" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findFarmerGovtOfficialDesignationById

}
