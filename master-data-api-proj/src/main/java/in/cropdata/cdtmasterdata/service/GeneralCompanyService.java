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
import in.cropdata.cdtmasterdata.model.GeneralCompany;
import in.cropdata.cdtmasterdata.repository.GeneralCompanyRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

//public class GeneralCompanyService {

@Service
public class GeneralCompanyService {

	@Autowired
	private GeneralCompanyRepository generalCompanyRepository;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	@Autowired
	private AclHistoryUtil approvalUtil;

	@GetMapping("/all-general-drop-reason")
	public List<GeneralCompany> getAllGeneralCompany() {
		try {
			List<GeneralCompany> list = generalCompanyRepository.findAll(Sort.by("name"));

			return list;
		} catch (Exception e) {
			throw e;
		}
	}// getAllgeneralCompanys

	public Page<GeneralCompany> getAllGeneralCompanyPaginated(int page, int size) {
		try {
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());
			Page<GeneralCompany> CompanyList = generalCompanyRepository.findAll(sortedByIdDesc);
			return CompanyList;
		} catch (Exception e) {
			throw e;
		}
	}// getAllgeneralCompanyPaginated

	public ResponseMessage addGeneralCompany(GeneralCompany generalCompany) {

		try {
			generalCompany = generalCompanyRepository.save(generalCompany);

			approvalUtil.addRecord(DBConstants.TBL_GENERAL_COMPANY, generalCompany.getId());

			return responseMessageUtil.sendResponse(true, "general-Company" + APIConstants.RESPONSE_ADD_SUCCESS, "");

		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// addAllgeneralCompany

	public ResponseMessage updateGeneralCompanyById(int id, GeneralCompany generalCompany) {
		try {
			Optional<GeneralCompany> foundCompany = generalCompanyRepository.findById(id);

			if (foundCompany.isPresent()) {

				generalCompany.setId(id);
				generalCompany = generalCompanyRepository.save(generalCompany);

				approvalUtil.updateRecord(DBConstants.TBL_GENERAL_COMPANY, generalCompany.getId());

				return responseMessageUtil.sendResponse(true,
						"general-Company" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"general-Company" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}// updategeneralCompanyById

	public ResponseMessage finalApproveById(int id) {
		try {
			Optional<GeneralCompany> foundCompany = generalCompanyRepository.findById(id);

			if (foundCompany.isPresent()) {

				GeneralCompany Company = foundCompany.get();
				Company.setStatus(APIConstants.STATUS_ACTIVE);
				Company = generalCompanyRepository.save(Company);

				approvalUtil.finalApprove(DBConstants.TBL_GENERAL_COMPANY, Company.getId());

				return responseMessageUtil.sendResponse(true,
						"general-Company" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"general-Company" + APIConstants.RESPONSE_NO_RECORD_FOUND);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproveById

	public ResponseMessage primaryApproveById(int id) {
		try {
			Optional<GeneralCompany> foundGeneralCompany = generalCompanyRepository.findById(id);

			if (foundGeneralCompany.isPresent()) {

				GeneralCompany Company = foundGeneralCompany.get();
				Company.setStatus(APIConstants.STATUS_APPROVED);
				Company = generalCompanyRepository.save(Company);

				approvalUtil.primaryApprove(DBConstants.TBL_GENERAL_COMPANY, Company.getId());

				return responseMessageUtil.sendResponse(true,
						"General-Company" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-IncomeSource" + APIConstants.RESPONSE_NO_RECORD_FOUND);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage deleteGeneralCompanyById(int id) {
		try {
			Optional<GeneralCompany> foundCompany = generalCompanyRepository.findById(id);

			if (foundCompany.isPresent()) {

				GeneralCompany Company = foundCompany.get();
				Company.setStatus(APIConstants.STATUS_DELETED);
				Company = generalCompanyRepository.save(Company);

				approvalUtil.delete(DBConstants.TBL_GENERAL_COMPANY, Company.getId());

				return responseMessageUtil.sendResponse(true,
						"general-Company" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"general-Company" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// deletegeneralCompanyById

	public ResponseMessage rejectById(int id) {
		try {
			Optional<GeneralCompany> foundCompany = generalCompanyRepository.findById(id);

			if (foundCompany.isPresent()) {

				GeneralCompany Company = foundCompany.get();
				Company.setStatus(APIConstants.STATUS_REJECTED);
				Company = generalCompanyRepository.save(Company);

				approvalUtil.delete(DBConstants.TBL_GENERAL_COMPANY, Company.getId());

				return responseMessageUtil.sendResponse(true,
						"general-Company" + APIConstants.RESPONSE_REJECT_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"general-Company" + APIConstants.RESPONSE_REJECT_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// rejectById

	public GeneralCompany findGeneralCompanyById(int id) {
		try {
			Optional<GeneralCompany> foundCompany = generalCompanyRepository.findById(id);
			if (foundCompany.isPresent()) {

				return foundCompany.get();
			} else {
				throw new DoesNotExistException("general-Company" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findgeneralCompanyById

}
