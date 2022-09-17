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
import in.cropdata.cdtmasterdata.model.GeneralBankCategory;
import in.cropdata.cdtmasterdata.repository.GeneralBankCategoryRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

//public class GeneralBankCategoryService {

@Service
public class GeneralBankCategoryService {

	@Autowired
	private GeneralBankCategoryRepository generalBankCategoryRepository;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	@Autowired
	private AclHistoryUtil approvalUtil;

	public List<GeneralBankCategory> getAllGeneralBankCategory() {
		try {
			List<GeneralBankCategory> list = generalBankCategoryRepository.findAll(Sort.by("name").descending());

			return list;
		} catch (Exception e) {
			throw e;
		}
	}// getAllgeneralBankCategorys

	public Page<GeneralBankCategory> getAllGeneralBankCategoryPaginated(int page, int size) {
		try {
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());
			Page<GeneralBankCategory> BankCategoryList = generalBankCategoryRepository.findAll(sortedByIdDesc);
			return BankCategoryList;
		} catch (Exception e) {
			throw e;
		}
	}// getAllgeneralBankCategoryPaginated

	public ResponseMessage addGeneralBankCategory(GeneralBankCategory generalBankCategory)
	{
		try
		{
			final String categoryName = generalBankCategory.getName().strip();

			if (generalBankCategoryRepository.findByName(categoryName).isEmpty())
			{
				generalBankCategory.setName(categoryName);

				generalBankCategory = generalBankCategoryRepository.save(generalBankCategory);

				approvalUtil.addRecord(DBConstants.TBL_GENERAl_BANK_CATEGORY, generalBankCategory.getId());

				return responseMessageUtil.sendResponse(true, "general-BankCategory" + APIConstants.RESPONSE_ADD_SUCCESS, "");
			} else
			{
				return responseMessageUtil.sendResponse(false, "",
						"general-BankCategory".concat(APIConstants.RESPONSE_ALREADY_EXIST).concat(categoryName));
			}
		} catch (Exception e)
		{
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// addAllgeneralBankCategory

	public ResponseMessage updateGeneralBankCategoryById(int id, GeneralBankCategory generalBankCategory) {
		try {
			Optional<GeneralBankCategory> foundBankCategory = generalBankCategoryRepository.findById(id);

			if (foundBankCategory.isPresent()) {

				generalBankCategory.setId(id);
				generalBankCategory = generalBankCategoryRepository.save(generalBankCategory);

				approvalUtil.updateRecord(DBConstants.TBL_GENERAl_BANK_CATEGORY, generalBankCategory.getId());

				return responseMessageUtil.sendResponse(true,
						"general-BankCategory" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"general-BankCategory" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}// updategeneralBankCategoryById

	public ResponseMessage finalApproveById(int id) {
		try {
			Optional<GeneralBankCategory> foundBankCategory = generalBankCategoryRepository.findById(id);

			if (foundBankCategory.isPresent()) {

				GeneralBankCategory BankCategory = foundBankCategory.get();
				BankCategory.setStatus(APIConstants.STATUS_ACTIVE);
				BankCategory = generalBankCategoryRepository.save(BankCategory);

				approvalUtil.finalApprove(DBConstants.TBL_GENERAl_BANK_CATEGORY, BankCategory.getId());

				return responseMessageUtil.sendResponse(true,
						"general-BankCategory" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"general-BankCategory" + APIConstants.RESPONSE_NO_RECORD_FOUND);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproveById

	public ResponseMessage primaryApproveById(int id) {
		try {
			Optional<GeneralBankCategory> foundGeneralBankCategory = generalBankCategoryRepository.findById(id);

			if (foundGeneralBankCategory.isPresent()) {

				GeneralBankCategory BankCategory = foundGeneralBankCategory.get();
				BankCategory.setStatus(APIConstants.STATUS_APPROVED);
				BankCategory = generalBankCategoryRepository.save(BankCategory);

				approvalUtil.primaryApprove(DBConstants.TBL_GENERAl_BANK_CATEGORY, BankCategory.getId());

				return responseMessageUtil.sendResponse(true,
						"General-BankCategory" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-IncomeSource" + APIConstants.RESPONSE_NO_RECORD_FOUND);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage deleteGeneralBankCategoryById(int id) {
		try {
			Optional<GeneralBankCategory> foundBankCategory = generalBankCategoryRepository.findById(id);

			if (foundBankCategory.isPresent()) {

				GeneralBankCategory BankCategory = foundBankCategory.get();
				BankCategory.setStatus(APIConstants.STATUS_DELETED);
				BankCategory = generalBankCategoryRepository.save(BankCategory);

				approvalUtil.delete(DBConstants.TBL_GENERAl_BANK_CATEGORY, BankCategory.getId());

				return responseMessageUtil.sendResponse(true,
						"general-BankCategory" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"general-BankCategory" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// deletegeneralBankCategoryById

	public ResponseMessage rejectById(int id) {
		try {
			Optional<GeneralBankCategory> foundBankCategory = generalBankCategoryRepository.findById(id);

			if (foundBankCategory.isPresent()) {

				GeneralBankCategory BankCategory = foundBankCategory.get();
				BankCategory.setStatus(APIConstants.STATUS_REJECTED);
				BankCategory = generalBankCategoryRepository.save(BankCategory);

				approvalUtil.delete(DBConstants.TBL_GENERAl_BANK_CATEGORY, BankCategory.getId());

				return responseMessageUtil.sendResponse(true,
						"general-BankCategory" + APIConstants.RESPONSE_REJECT_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"general-BankCategory" + APIConstants.RESPONSE_REJECT_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// rejectById

	public GeneralBankCategory findGeneralBankCategoryById(int id) {
		try {
			Optional<GeneralBankCategory> foundBankCategory = generalBankCategoryRepository.findById(id);
			if (foundBankCategory.isPresent()) {

				return foundBankCategory.get();
			} else {
				throw new DoesNotExistException("general-BankCategory" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findgeneralBankCategoryById

}
