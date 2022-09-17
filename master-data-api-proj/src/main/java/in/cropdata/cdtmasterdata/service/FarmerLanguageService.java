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
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.model.FarmerLanguage;
import in.cropdata.cdtmasterdata.repository.FarmerLanguageRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class FarmerLanguageService {

	@Autowired
	private FarmerLanguageRepository farmerLanguageRepository;

	@Autowired
	private AclHistoryUtil approvalUtil;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	public List<FarmerLanguage> getAllFarmerLanguage() {
		try {
			List<FarmerLanguage> list = farmerLanguageRepository.findAll(Sort.by("language"));

			return list;
		} catch (Exception e) {
			throw e;
		}
	}// getAllFarmerLanguage

	public Page<FarmerLanguage> getAllFarmerLanguagePaginated(int page, int size) {
		try {
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());
			Page<FarmerLanguage> farmerLanguageList = farmerLanguageRepository.findAll(sortedByIdDesc);
			return farmerLanguageList;
		} catch (Exception e) {
			throw e;
		}
	}// getAllFarmerLanguagePaginated

	public ResponseMessage addFarmerLanguage(FarmerLanguage farmerLanguage) {

		try {
			farmerLanguage = farmerLanguageRepository.save(farmerLanguage);

			approvalUtil.addRecord(DBConstants.TBL_FARMER_LANGUAGE, farmerLanguage.getId());

			return responseMessageUtil.sendResponse(true, "Farmer-Language" + APIConstants.RESPONSE_ADD_SUCCESS, "");

		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// addFarmerLanguage

	public ResponseMessage updateFarmerLanguageById(int id, FarmerLanguage farmerLanguage) {
		try {
			Optional<FarmerLanguage> foundLanguage = farmerLanguageRepository.findById(id);

			if (foundLanguage.isPresent()) {

				farmerLanguage.setId(id);
				farmerLanguage = farmerLanguageRepository.save(farmerLanguage);

				approvalUtil.updateRecord(DBConstants.TBL_FARMER_LANGUAGE, farmerLanguage.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-Language" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-Language" + APIConstants.RESPONSE_UPDATE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// updateFarmerLanguageById

	public ResponseMessage primaryApproveById(int id) {
		try {
			Optional<FarmerLanguage> foundLanguage = farmerLanguageRepository.findById(id);

			if (foundLanguage.isPresent()) {

				FarmerLanguage language = foundLanguage.get();
				language.setStatus(APIConstants.STATUS_APPROVED);

				language = farmerLanguageRepository.save(language);

				approvalUtil.primaryApprove(DBConstants.TBL_FARMER_LANGUAGE, language.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-Language" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-Language" + APIConstants.RESPONSE_PRIMARY_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage finalApproveById(int id) {
		try {
			Optional<FarmerLanguage> foundLanguage = farmerLanguageRepository.findById(id);

			if (foundLanguage.isPresent()) {

				FarmerLanguage language = foundLanguage.get();
				language.setStatus(APIConstants.STATUS_ACTIVE);

				language = farmerLanguageRepository.save(language);

				approvalUtil.finalApprove(DBConstants.TBL_FARMER_LANGUAGE, language.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-Language" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-Language" + APIConstants.RESPONSE_FINAL_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproveById

	public ResponseMessage deleteFarmerLanguageById(int id) {
		try {
			Optional<FarmerLanguage> foundLanguage = farmerLanguageRepository.findById(id);

			if (foundLanguage.isPresent()) {

				FarmerLanguage language = foundLanguage.get();
				language.setStatus(APIConstants.STATUS_DELETED);

				language = farmerLanguageRepository.save(language);

				approvalUtil.delete(DBConstants.TBL_FARMER_LANGUAGE, language.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-Language" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-Language" + APIConstants.RESPONSE_DELETE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// deleteFarmerLanguageById

	public FarmerLanguage findFarmerLanguageById(int id) {
		try {
			Optional<FarmerLanguage> foundLanguage = farmerLanguageRepository.findById(id);
			if (foundLanguage.isPresent()) {
				return foundLanguage.get();
			} else {
				throw new DoesNotExistException("Farmer-Language" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findFarmerLanguageById

	public ResponseMessage rejectById(int id) {
		try {
			Optional<FarmerLanguage> foundFarmerLanguage = farmerLanguageRepository.findById(id);

			if (foundFarmerLanguage.isPresent()) {

				FarmerLanguage farmerLanguage = foundFarmerLanguage.get();
				farmerLanguage.setStatus(APIConstants.STATUS_REJECTED);
				farmerLanguage = farmerLanguageRepository.save(farmerLanguage);

				approvalUtil.finalApprove(DBConstants.TBL_FARMER_LANGUAGE, farmerLanguage.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-Language " + APIConstants.RESPONSE_REJECT_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-Language " + APIConstants.RESPONSE_REJECT_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// rejectById

}// addAllFarmerLanguage
