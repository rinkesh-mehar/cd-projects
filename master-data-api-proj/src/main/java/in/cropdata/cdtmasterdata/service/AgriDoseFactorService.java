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
import in.cropdata.cdtmasterdata.model.AgriDoseFactor;
import in.cropdata.cdtmasterdata.model.GeneralUom;
import in.cropdata.cdtmasterdata.repository.AgriDoseFactorRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class AgriDoseFactorService {

	@Autowired
	private AgriDoseFactorRepository agriDoseFactorRepository;

	@Autowired
	private AclHistoryUtil approvalUtil;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	public List<AgriDoseFactor> getAllAgriDoseFactor() {
		try {
			List<AgriDoseFactor> list = agriDoseFactorRepository.findAll(Sort.by("doseFactor"));

			return list;
		} catch (Exception e) {
			throw e;
		}
	}// getAllAgriDoseFactor
	
	public Page<AgriDoseFactor> getPeginatedAgriDoseFactorList(int page, int size, String searchText) {

		try {
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());

			Page<AgriDoseFactor> doseFactorList = agriDoseFactorRepository.getPeginatedAgriDoseFactorList(sortedByIdDesc,
					searchText);

			return doseFactorList;

		} catch (Exception e) {
			throw e;
		}
	}

	public ResponseMessage addAgriDoseFactor(AgriDoseFactor agriDoseFactor) {

		try {
			agriDoseFactor = agriDoseFactorRepository.save(agriDoseFactor);

			approvalUtil.addRecord(DBConstants.TBL_AGRI_DOSE_FACTORS, agriDoseFactor.getId());

			return responseMessageUtil.sendResponse(true, "Agri-Dose-Factor" + APIConstants.RESPONSE_ADD_SUCCESS, "");

		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}// addAgriDoseFactor

	public ResponseMessage updateAgriDoseFactorById(int id, AgriDoseFactor agriDoseFactor) {
		try {
			Optional<AgriDoseFactor> foundDoseFact = agriDoseFactorRepository.findById(id);

			if (foundDoseFact.isPresent()) {

				agriDoseFactor.setId(id);
				agriDoseFactorRepository.save(agriDoseFactor);

				approvalUtil.updateRecord(DBConstants.TBL_AGRI_DOSE_FACTORS, agriDoseFactor.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Dose-Factor" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
			} else {

				return responseMessageUtil.sendResponse(false, "",
						"Agri-Dose-Factor" + APIConstants.RESPONSE_UPDATE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// updateAgriDoseFactorById

	public ResponseMessage primaryApproveById(int id) {
		try {
			Optional<AgriDoseFactor> foundDoseFact = agriDoseFactorRepository.findById(id);

			if (foundDoseFact.isPresent()) {

				AgriDoseFactor agriDoseFactor = foundDoseFact.get();
				agriDoseFactor.setStatus(APIConstants.STATUS_APPROVED);

				agriDoseFactor = agriDoseFactorRepository.save(agriDoseFactor);

				approvalUtil.primaryApprove(DBConstants.TBL_AGRI_DOSE_FACTORS, agriDoseFactor.getId());
				return responseMessageUtil.sendResponse(true,
						"Agri-Dose-Factor" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Dose-Factor" + APIConstants.RESPONSE_PRIMARY_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage finalApproveById(int id) {
		try {
			Optional<AgriDoseFactor> foundDoseFact = agriDoseFactorRepository.findById(id);

			if (foundDoseFact.isPresent()) {

				AgriDoseFactor agriDoseFactor = foundDoseFact.get();
				agriDoseFactor.setStatus(APIConstants.STATUS_ACTIVE);

				agriDoseFactor = agriDoseFactorRepository.save(agriDoseFactor);

				return responseMessageUtil.sendResponse(true,
						"Agri-Dose-Factor" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Dose-Factor" + APIConstants.RESPONSE_FINAL_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproveById

	public ResponseMessage deleteAgriDoseFactorById(int id) {
		try {
			Optional<AgriDoseFactor> foundDoseFact = agriDoseFactorRepository.findById(id);
			if (foundDoseFact.isPresent()) {

				AgriDoseFactor agriDoseFactor = foundDoseFact.get();
				agriDoseFactor.setStatus(APIConstants.STATUS_DELETED);
				agriDoseFactor = agriDoseFactorRepository.save(agriDoseFactor);

				approvalUtil.delete(DBConstants.TBL_AGRI_DOSE_FACTORS, agriDoseFactor.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Dose-Factor" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");

			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Dose-Factor" + APIConstants.RESPONSE_DELETE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}// deleteAgriDoseFactorById

	public ResponseMessage rejectById(int id) {
		try {
			Optional<AgriDoseFactor> foundDoseFact = agriDoseFactorRepository.findById(id);
			if (foundDoseFact.isPresent()) {

				AgriDoseFactor agriDoseFactor = foundDoseFact.get();
				agriDoseFactor.setStatus(APIConstants.STATUS_REJECTED);
				agriDoseFactor = agriDoseFactorRepository.save(agriDoseFactor);

				approvalUtil.delete(DBConstants.TBL_AGRI_DOSE_FACTORS, agriDoseFactor.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-Dose-Factor" + APIConstants.RESPONSE_REJECT_SUCCESS + id, "");

			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-Dose-Factor" + APIConstants.RESPONSE_REJECT_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}// rejectById

	public AgriDoseFactor findAgriDoseFactorById(int id) {
		try {
			Optional<AgriDoseFactor> foundDoseFact = agriDoseFactorRepository.findById(id);
			if (foundDoseFact.isPresent()) {
				return foundDoseFact.get();

			} else {

				throw new DoesNotExistException("Agri-Dose-Factor" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findAgriDoseFactorById
}// AgriDoseFactorService
