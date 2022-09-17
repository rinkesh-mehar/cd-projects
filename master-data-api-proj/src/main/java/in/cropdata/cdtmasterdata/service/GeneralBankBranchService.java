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
import org.springframework.web.bind.annotation.GetMapping;

import in.cropdata.cdtmasterdata.constants.APIConstants;
import in.cropdata.cdtmasterdata.constants.DBConstants;
import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.dto.interfaces.GeneralBankBranchInf;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.model.AgriVarietyStress;
import in.cropdata.cdtmasterdata.model.AgriVarietyStressMissing;
import in.cropdata.cdtmasterdata.model.GeneralBank;
import in.cropdata.cdtmasterdata.model.GeneralBankBranch;
import in.cropdata.cdtmasterdata.model.GeneralBankBranchMissing;
import in.cropdata.cdtmasterdata.repository.GeneralBankBranchMissingRepository;
import in.cropdata.cdtmasterdata.repository.GeneralBankBranchRepository;
import in.cropdata.cdtmasterdata.repository.GeneralBankRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class GeneralBankBranchService {
	@Autowired
	private GeneralBankBranchRepository generalBankBranchRepository;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;
	
	@Autowired
	private GeneralBankRepository generalBankRepository;

	@Autowired
	private AclHistoryUtil approvalUtil;
	
	@Autowired
	private GeneralBankBranchMissingRepository generalBankBranchMissingRepository;

	@GetMapping("/all-general-bank")
	public List<GeneralBankBranch> getAllGeneralBankBranch() {
		try {
			List<GeneralBankBranch> list = generalBankBranchRepository.findAll(Sort.by("name"));
			
			for (GeneralBankBranch generalBankBranch : list) {
				generalBankBranch = getData(generalBankBranch);
			}

			return list;
		} catch (Exception e) {
			throw e;
		}
	}// getAllGeneralBankBranchs

//	public Page<GeneralBankBranch> getAllGeneralBankBranchPaginated(int page, int size, String searchText) {
//		try {
//			searchText = "%"searchText"%";
//			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").ascending());
//			Page<GeneralBankBranch> list = generalBankBranchRepository.findAllByBranchNameIgnoreCaseContaining(sortedByIdDesc , searchText);
//			for (GeneralBankBranch generalBankBranch : list) {
//				generalBankBranch = getData(generalBankBranch);
//			}
//			return list;
//		} catch (Exception e) {
//			throw e;
//		}
//	}// getAllGeneralBankBranchPaginated
	
	public Page<GeneralBankBranchInf> getAllGeneralBankBranchPaginated(int page, int size, String searchText,
			int isValid, String missing) {

		try {
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());

			Page<GeneralBankBranchInf> list;
			if ("0".equals(missing)) {
				if (isValid == 0) {
					list = generalBankBranchRepository.findAllWithSearchInvalidated(sortedByIdDesc, searchText);
				} else {
					list = generalBankBranchRepository.findAllWithSearch(sortedByIdDesc, searchText);
				}
			} else {
				if (isValid == 0) {
					list = generalBankBranchRepository.findAllWithSearchMissingInvalidated(sortedByIdDesc, searchText);
				} else {

					list = generalBankBranchRepository.findAllWithMissingSearch(sortedByIdDesc, searchText);
				}
			}
			return list;

		} catch (Exception e) {
			throw e;
		}
	}// getAllGeneralBankBranchPaginated

	public ResponseMessage addGeneralBankBranch(GeneralBankBranch generalBankBranch) {

		try
		{
			/**
			 * Checking the ifsc Code is already present or not.
			 * Removing the White Space before saving data.
			 */
			final String ifscCode = generalBankBranch.getIfscCode().strip();

			if (!ifscCode.matches("[a-zA-Z0-9]+"))
			{
				return responseMessageUtil.sendResponse(false, "", "Please Enter Alphanumeric IFSC Code");
			}

			if (generalBankBranchRepository.findByIfscCode(ifscCode).isEmpty())
			{
				generalBankBranch.setIfscCode(ifscCode);
				generalBankBranch.setName(generalBankBranch.getName().strip());

				generalBankBranch = generalBankBranchRepository.save(generalBankBranch);

				approvalUtil.addRecord(DBConstants.TBL_GENERAL_BANK_BRANCH, generalBankBranch.getId());

				return responseMessageUtil.sendResponse(true, "general-BankBranch" + APIConstants.RESPONSE_ADD_SUCCESS, "");
			} else
			{
				return responseMessageUtil.sendResponse(
						false, "",
						"general-BankBranch".concat(APIConstants.RESPONSE_ALREADY_EXIST).concat("ifscCode :" + ifscCode));
			}
		} catch (Exception e)
		{
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// addAllGeneralBankBranch
	
	private GeneralBankBranch getData(GeneralBankBranch generalBankBranch) {
		try {
			Optional<GeneralBank> foundBank = generalBankRepository
					.findById(generalBankBranch.getBankId());
			if (foundBank.isPresent()) {
				GeneralBank generalBank = foundBank.get();
				generalBankBranch.setBank(generalBank.getName());
			}
			return generalBankBranch;

		} catch (Exception e) {
			throw e;
		}
	}
	
//	private FarmerInsuranceCompany getData(FarmerInsuranceCompany insuranceCompany) {
//		try {
//			Optional<FarmerInsuranceType> foundInsurance = farmerInsuranceTypeRepository
//					.findById(insuranceCompany.getInsuranceTypeId());
//			if (foundInsurance.isPresent()) {
//				FarmerInsuranceType farmerInsuranceType = foundInsurance.get();
//				insuranceCompany.setInsuranceType(farmerInsuranceType.getName());
//			}
//			return insuranceCompany;
//
//		} catch (Exception e) {
//			throw e;
//		}
//	}

	public ResponseMessage updateGeneralBankBranchById(final int id, GeneralBankBranch generalBankBranch)
	{
		try
		{
			final String ifscCode = generalBankBranch.getIfscCode().strip();

			if (!ifscCode.matches("[a-zA-Z0-9]+"))
			{
				return responseMessageUtil.sendResponse(false, "", "Please Enter Alphanumeric IFSC Code");
			}

			final Optional<GeneralBankBranch> foundGeneralBankByIFSCCode = generalBankBranchRepository.findByIfscCode(ifscCode);

			if (foundGeneralBankByIFSCCode.isPresent() && id != foundGeneralBankByIFSCCode.get().getId())
			{
				return responseMessageUtil.sendResponse(
						false, "",
						"general-BankBranch".concat(APIConstants.RESPONSE_ALREADY_EXIST).concat("IFSC Code :" + ifscCode));
			}

			Optional<GeneralBankBranch> foundBankBranch = generalBankBranchRepository.findById(id);

			if (foundBankBranch.isPresent())
			{
				GeneralBankBranch update = foundBankBranch.get();

				if (generalBankBranch.getBankId() > 0)
				{
					update.setBankId(generalBankBranch.getBankId());
				}
				if (generalBankBranch.getName() != null)
				{
					update.setName(generalBankBranch.getName());
				}
				if (generalBankBranch.getIfscCode() != null)
				{
					update.setIfscCode(generalBankBranch.getIfscCode());
				}
				if (generalBankBranch.getStatus() != null)
				{
					update.setStatus(generalBankBranch.getStatus());
				}

				generalBankBranch = generalBankBranchRepository.save(update);

				approvalUtil.updateRecord(DBConstants.TBL_GENERAL_BANK_BRANCH, generalBankBranch.getId());

				return responseMessageUtil.sendResponse(true,
						"General-BankBranch" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
			} else
			{
				return responseMessageUtil.sendResponse(false, "",
						"General-BankBranch" + APIConstants.RESPONSE_UPDATE_ERROR + id);
			}
		} catch (Exception e)
		{
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}// updateGeneralBankBranchById

//	public ResponseMessage updateGeneralBankBranchById(int id, GeneralBankBranch GeneralBankBranch) {
//		try {
//			Optional<GeneralBankBranch> foundBankName = generalBankBranchRepository.findById(id);
//
//			if (foundBankName.isPresent()) {
//
//				GeneralBankBranch.setId(id);
//				GeneralBankBranch = generalBankBranchRepository.save(GeneralBankBranch);
//
//				approvalUtil.updateRecord(DBConstants.TBL_GENERAL_BANK_BRANCH, GeneralBankBranch.getId());
//
//				return responseMessageUtil.sendResponse(true,
//						"general-BankBranch" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
//			} else {
//				return responseMessageUtil.sendResponse(false, "",
//						"general-BankBranch" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
//			}
//		} catch (Exception e) {
//			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
//		}
//
//	}// updateGeneralBankBranchById

	public ResponseMessage finalApproveById(int id) {
		try {
			Optional<GeneralBankBranch> foundBankName = generalBankBranchRepository.findById(id);

			if (foundBankName.isPresent()) {

				GeneralBankBranch BankName = foundBankName.get();
				BankName.setStatus(APIConstants.STATUS_ACTIVE);
				BankName = generalBankBranchRepository.save(BankName);

				approvalUtil.finalApprove(DBConstants.TBL_GENERAL_BANK_BRANCH, BankName.getId());

				return responseMessageUtil.sendResponse(true,
						"general-BankBranch" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"general-BankBranch" + APIConstants.RESPONSE_NO_RECORD_FOUND);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproveById

	public ResponseMessage primaryApproveById(int id) {
		try {
			Optional<GeneralBankBranch> foundGeneralBankBranch = generalBankBranchRepository.findById(id);

			if (foundGeneralBankBranch.isPresent()) {

				GeneralBankBranch BankName = foundGeneralBankBranch.get();
				BankName.setStatus(APIConstants.STATUS_APPROVED);
				BankName = generalBankBranchRepository.save(BankName);

				approvalUtil.primaryApprove(DBConstants.TBL_GENERAL_BANK_BRANCH, BankName.getId());

				return responseMessageUtil.sendResponse(true,
						"General-BankBranch" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"General-BankBranch" + APIConstants.RESPONSE_NO_RECORD_FOUND);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage deleteGeneralBankBranchById(int id) {
		try {
			Optional<GeneralBankBranch> foundBankName = generalBankBranchRepository.findById(id);

			if (foundBankName.isPresent()) {

				GeneralBankBranch BankName = foundBankName.get();
				BankName.setStatus(APIConstants.STATUS_DELETED);
				BankName = generalBankBranchRepository.save(BankName);

				approvalUtil.delete(DBConstants.TBL_GENERAL_BANK_BRANCH, BankName.getId());

				return responseMessageUtil.sendResponse(true,
						"general-BankBranch" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"general-BankBranch" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// deleteGeneralBankBranchById

	public ResponseMessage rejectById(int id) {
		try {
			Optional<GeneralBankBranch> foundBankName = generalBankBranchRepository.findById(id);

			if (foundBankName.isPresent()) {

				GeneralBankBranch BankName = foundBankName.get();
				BankName.setStatus(APIConstants.STATUS_REJECTED);
				BankName = generalBankBranchRepository.save(BankName);

				approvalUtil.delete(DBConstants.TBL_GENERAL_BANK_BRANCH, BankName.getId());

				return responseMessageUtil.sendResponse(true,
						"general-BankBranch" + APIConstants.RESPONSE_REJECT_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"general-BankBranch" + APIConstants.RESPONSE_REJECT_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// rejectById

	public GeneralBankBranch findGeneralBankBranchById(int id) {
		try {
			Optional<GeneralBankBranch> foundBankName = generalBankBranchRepository.findById(id);
			if (foundBankName.isPresent()) {

				return foundBankName.get();
			} else {
				throw new DoesNotExistException("general-BankBranch" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findGeneralBankBranchById

	@Transactional
	public ResponseMessage moveToMaster(int id) {

		try {
			Optional<GeneralBankBranchMissing> foundGeneralBankBranchMissing = generalBankBranchMissingRepository.findById(id);

			if (foundGeneralBankBranchMissing.isPresent()) {
				GeneralBankBranch generalBankBranch = new GeneralBankBranch();
				GeneralBankBranchMissing generalBankBranchMissing = foundGeneralBankBranchMissing.get();
				
				generalBankBranch.setId(generalBankBranchMissing.getId());
				generalBankBranch.setBankId(generalBankBranchMissing.getBankId());
				generalBankBranch.setName(generalBankBranchMissing.getName());
				generalBankBranch.setIfscCode(generalBankBranchMissing.getIfscCode());
				generalBankBranch.setStatus(generalBankBranchMissing.getStatus());
				generalBankBranch.setDistrictCode(generalBankBranchMissing.getDistrictCode());
	
				GeneralBankBranch generalBnkBrnch = generalBankBranchRepository.save(generalBankBranch);
				
				generalBankBranchMissingRepository.deleteById(id);


				approvalUtil.addRecord(DBConstants.TBL_GENERAL_BANK_BRANCH, generalBnkBrnch.getId());

				return responseMessageUtil.sendResponse(true,
						"General-BankBranch-Missing" + APIConstants.RESPONSE_MOVE_TO_MASTER_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"General-BankBranch-Missing" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
		
	}

}
