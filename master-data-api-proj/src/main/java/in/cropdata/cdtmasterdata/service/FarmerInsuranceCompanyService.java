package in.cropdata.cdtmasterdata.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import in.cropdata.cdtmasterdata.constants.APIConstants;
import in.cropdata.cdtmasterdata.constants.DBConstants;
import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.dto.interfaces.FarmerInsuranceCompanyInfDto;
import in.cropdata.cdtmasterdata.dto.interfaces.FarmerLoanSourceInfo;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.model.FarmerInsuranceCompany;
import in.cropdata.cdtmasterdata.model.FarmerInsuranceType;
import in.cropdata.cdtmasterdata.repository.FarmerInsuranceCompanyRepository;
import in.cropdata.cdtmasterdata.repository.FarmerInsuranceTypeRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class FarmerInsuranceCompanyService {

	@Autowired
	private FarmerInsuranceCompanyRepository farmerInsuranceCompanyRepository;

	@Autowired
	private AclHistoryUtil approvalUtil;

	@Autowired
	private FarmerInsuranceTypeRepository farmerInsuranceTypeRepository;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	public List<FarmerInsuranceCompany> getAllFarmerInsuranceCompany() {
		try {
			List<FarmerInsuranceCompany> list = farmerInsuranceCompanyRepository.findAll();

//			for (FarmerInsuranceCompany insuranceCompany : list) {
//				insuranceCompany = getData(insuranceCompany);
//			}

			return list;
		} catch (Exception e) {
			throw e;
		}
	}// getAllFarmerInsuranceCompany

//	public Page<FarmerInsuranceCompanyInfDto> getAllFarmerInsuranceCompany(int page, int size) {
//		try {
//			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").ascending());
//			Page<FarmerInsuranceCompanyInfDto> list = farmerInsuranceCompanyRepository.findAll(sortedByIdDesc);
//
//			for (FarmerInsuranceCompany insuranceCompany : list) {
//				insuranceCompany = getData(insuranceCompany);
//				System.out.println(insuranceCompany.getInsuranceType());
//			}
//
//			return list;
//		} catch (Exception e) {
//			throw e;
//		}
//	}// getAllFarmerInsuranceCompany
	
	public Page<FarmerInsuranceCompanyInfDto> getAllFarmerLoanSourcePaginated(int page, int size, String searchText, int isValid) {

		try {
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());
			Page<FarmerInsuranceCompanyInfDto> insuranceCompanyList;
			if (isValid == 0) {
				insuranceCompanyList = farmerInsuranceCompanyRepository.findAllWithSearchInvalidated(sortedByIdDesc, searchText);
			} else {
				insuranceCompanyList = farmerInsuranceCompanyRepository.findAllWithSearch(sortedByIdDesc, searchText);
			}

			return insuranceCompanyList;

		} catch (Exception e) {
			throw e;
		}
	}//getAllFarmerLoanSourcePaginated

	public ResponseMessage addFarmerInsuranceCompany(FarmerInsuranceCompany farmerInsuranceCompany) {

		try {
			farmerInsuranceCompany = farmerInsuranceCompanyRepository.save(farmerInsuranceCompany);

			approvalUtil.addRecord(DBConstants.TBL_FARMER_INSURANCECOMPANY, farmerInsuranceCompany.getId());

			return responseMessageUtil.sendResponse(true, "Farmer-InsuranceCompany" + APIConstants.RESPONSE_ADD_SUCCESS,
					"");

		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// addFarmerInsuranceCompany

	private FarmerInsuranceCompany getData(FarmerInsuranceCompany insuranceCompany) {
		try {
			Optional<FarmerInsuranceType> foundInsurance = farmerInsuranceTypeRepository
					.findById(insuranceCompany.getInsuranceTypeId());
			if (foundInsurance.isPresent()) {
				FarmerInsuranceType farmerInsuranceType = foundInsurance.get();
				insuranceCompany.setInsuranceType(farmerInsuranceType.getName());
			}
			return insuranceCompany;

		} catch (Exception e) {
			throw e;
		}
	}
	
	public ResponseMessage updateFarmerInsuranceCompanyById(int id, FarmerInsuranceCompany insuranceCompany) {

		try {
			Optional<FarmerInsuranceCompany> foundInsuranceCompany = farmerInsuranceCompanyRepository.findById(id);

			if (foundInsuranceCompany.isPresent()) {

				FarmerInsuranceCompany update = foundInsuranceCompany.get();

				if (insuranceCompany.getInsuranceTypeId() > 0) {
					update.setInsuranceTypeId(insuranceCompany.getInsuranceTypeId());
				}
				
				if(insuranceCompany.getName() != null) {
					update.setName(insuranceCompany.getName());
				}
				
				if (insuranceCompany.getStatus() != null) {
					update.setStatus(insuranceCompany.getStatus());
				}
				
				insuranceCompany = farmerInsuranceCompanyRepository.save(update);

				approvalUtil.updateRecord(DBConstants.TBL_FARMER_INSURANCECOMPANY, insuranceCompany.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-InsuranceCompany" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-InsuranceCompany" + APIConstants.RESPONSE_UPDATE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}// updateFarmerInsuranceCompanyById

//	public ResponseMessage updateFarmerInsuranceCompanyById(int id, FarmerInsuranceCompany farmerInsuranceCompany) {
//		try {
//			Optional<FarmerInsuranceCompany> foundInsuranceCompany = farmerInsuranceCompanyRepository.findById(id);
//			
//			if (farmerInsuranceCompany.getInsuranceTypeId() > 0) {
//				FarmerInsuranceCompany update = foundInsuranceCompany.get();
//				
//				update.setInsuranceTypeId(farmerInsuranceCompany.getInsuranceTypeId());
//			}
//
//			if (foundInsuranceCompany.isPresent()) {
//
//				farmerInsuranceCompany.setId(id);
//				farmerInsuranceCompany = farmerInsuranceCompanyRepository.save(farmerInsuranceCompany);
//
//				approvalUtil.updateRecord(DBConstants.TBL_FARMER_INSURANCECOMPANY, farmerInsuranceCompany.getId());
//
//				return responseMessageUtil.sendResponse(true,
//						"Farmer-InsuranceCompany" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
//			} else {
//				return responseMessageUtil.sendResponse(false, "",
//						"Farmer-InsuranceCompany" + APIConstants.RESPONSE_UPDATE_ERROR + id);
//			}
//		} catch (Exception e) {
//			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
//		}
//	}// updateFarmerInsuranceCompanyById

	public ResponseMessage primaryApproveById(int id) {
		try {
			Optional<FarmerInsuranceCompany> foundInsuranceCompany = farmerInsuranceCompanyRepository.findById(id);

			if (foundInsuranceCompany.isPresent()) {

				FarmerInsuranceCompany insuranceCompany = foundInsuranceCompany.get();
				insuranceCompany.setStatus(APIConstants.STATUS_APPROVED);

				insuranceCompany = farmerInsuranceCompanyRepository.save(insuranceCompany);

				approvalUtil.primaryApprove(DBConstants.TBL_FARMER_INSURANCECOMPANY, insuranceCompany.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-InsuranceCompany" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-InsuranceCompany" + APIConstants.RESPONSE_PRIMARY_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage finalApproveById(int id) {
		try {
			Optional<FarmerInsuranceCompany> foundInsuranceCompany = farmerInsuranceCompanyRepository.findById(id);

			if (foundInsuranceCompany.isPresent()) {

				FarmerInsuranceCompany insuranceCompany = foundInsuranceCompany.get();
				insuranceCompany.setStatus(APIConstants.STATUS_ACTIVE);

				insuranceCompany = farmerInsuranceCompanyRepository.save(insuranceCompany);

				approvalUtil.finalApprove(DBConstants.TBL_FARMER_INSURANCECOMPANY, insuranceCompany.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-InsuranceCompany" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-InsuranceCompany" + APIConstants.RESPONSE_FINAL_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproveById

	public ResponseMessage deleteFarmerInsuranceCompanyById(int id) {
		try {
			Optional<FarmerInsuranceCompany> foundInsuranceCompany = farmerInsuranceCompanyRepository.findById(id);

			if (foundInsuranceCompany.isPresent()) {

				FarmerInsuranceCompany insuranceCompany = foundInsuranceCompany.get();
				insuranceCompany.setStatus(APIConstants.STATUS_DELETED);

				insuranceCompany = farmerInsuranceCompanyRepository.save(insuranceCompany);

				approvalUtil.delete(DBConstants.TBL_FARMER_INSURANCECOMPANY, insuranceCompany.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-InsuranceCompany" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-InsuranceCompany" + APIConstants.RESPONSE_DELETE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// deleteFarmerInsuranceCompanyById

	public FarmerInsuranceCompany findFarmerInsuranceCompanyById(int id) {
		try {
			Optional<FarmerInsuranceCompany> foundInsuranceCompany = farmerInsuranceCompanyRepository.findById(id);
			if (foundInsuranceCompany.isPresent()) {
				return foundInsuranceCompany.get();
			} else {
				throw new DoesNotExistException("Farmer-InsuranceCompany" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findFarmerInsuranceCompanyById

	public ResponseMessage rejectById(int id) {
		try {
			Optional<FarmerInsuranceCompany> foundFarmerInsuranceCompany = farmerInsuranceCompanyRepository
					.findById(id);

			if (foundFarmerInsuranceCompany.isPresent()) {

				FarmerInsuranceCompany farmerInsuranceCompany = foundFarmerInsuranceCompany.get();
				farmerInsuranceCompany.setStatus(APIConstants.STATUS_REJECTED);
				farmerInsuranceCompany = farmerInsuranceCompanyRepository.save(farmerInsuranceCompany);

				approvalUtil.finalApprove(DBConstants.TBL_FARMER_INSURANCECOMPANY, farmerInsuranceCompany.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-InsuranceCompany " + APIConstants.RESPONSE_REJECT_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-InsuranceCompany " + APIConstants.RESPONSE_REJECT_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// rejectById

}// addAllFarmerInsuranceCompany
