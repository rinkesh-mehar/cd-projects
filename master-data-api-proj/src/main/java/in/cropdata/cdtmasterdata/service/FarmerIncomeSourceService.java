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
import in.cropdata.cdtmasterdata.model.FarmerIncomeSource;
import in.cropdata.cdtmasterdata.repository.FarmerIncomeSourceRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class FarmerIncomeSourceService {
	@Autowired
	private FarmerIncomeSourceRepository farmerIncomeSourceRepository;

	@Autowired
	private AclHistoryUtil approvalUtil;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	@GetMapping("/all-farmer-IncomeSource")
	public List<FarmerIncomeSource> getAllFarmerIncomeSource() {
		try {
			List<FarmerIncomeSource> list = farmerIncomeSourceRepository.findAll();

			return list;
		} catch (Exception e) {
			throw e;
		}
	}// getAllFarmerIncomeSources

	public Page<FarmerIncomeSource> getAllFarmerIncomeSourcePaginated(int page, int size) {
		try {
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());
			Page<FarmerIncomeSource> IncomeSourceList = farmerIncomeSourceRepository.findAll(sortedByIdDesc);
			return IncomeSourceList;
		} catch (Exception e) {
			throw e;
		}
	}// getAllFarmerIncomeSourcePaginated

	public ResponseMessage addFarmerIncomeSource(FarmerIncomeSource FarmerIncomeSource) {

		try {
			FarmerIncomeSource = farmerIncomeSourceRepository.save(FarmerIncomeSource);

			approvalUtil.addRecord(DBConstants.TBL_FARMER_INCOME_SOURCE, FarmerIncomeSource.getId());

			return responseMessageUtil.sendResponse(true, "Farmer-IncomeSource" + APIConstants.RESPONSE_ADD_SUCCESS,
					"");

		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// addAllFarmerIncomeSource

	public ResponseMessage updateFarmerIncomeSourceById(int id, FarmerIncomeSource FarmerIncomeSource) {
		try {
			Optional<FarmerIncomeSource> foundIncomeSource = farmerIncomeSourceRepository.findById(id);

			if (foundIncomeSource.isPresent()) {

				FarmerIncomeSource.setId(id);
				FarmerIncomeSource = farmerIncomeSourceRepository.save(FarmerIncomeSource);

				approvalUtil.updateRecord(DBConstants.TBL_FARMER_INCOME_SOURCE, FarmerIncomeSource.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-IncomeSource" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-IncomeSource" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}// updateFarmerIncomeSourceById

	public ResponseMessage primaryApproveById(int id) {
		try {
			Optional<FarmerIncomeSource> foundIncomeSource = farmerIncomeSourceRepository.findById(id);

			if (foundIncomeSource.isPresent()) {

				FarmerIncomeSource IncomeSource = foundIncomeSource.get();
				IncomeSource.setStatus(APIConstants.STATUS_APPROVED);
				IncomeSource = farmerIncomeSourceRepository.save(IncomeSource);

				approvalUtil.primaryApprove(DBConstants.TBL_FARMER_INCOME_SOURCE, IncomeSource.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-IncomeSource" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-IncomeSource" + APIConstants.RESPONSE_NO_RECORD_FOUND);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage finalApproveById(int id) {
		try {
			Optional<FarmerIncomeSource> foundIncomeSource = farmerIncomeSourceRepository.findById(id);

			if (foundIncomeSource.isPresent()) {

				FarmerIncomeSource IncomeSource = foundIncomeSource.get();
				IncomeSource.setStatus(APIConstants.STATUS_ACTIVE);
				IncomeSource = farmerIncomeSourceRepository.save(IncomeSource);

				approvalUtil.finalApprove(DBConstants.TBL_FARMER_INCOME_SOURCE, IncomeSource.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-IncomeSource" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-IncomeSource" + APIConstants.RESPONSE_NO_RECORD_FOUND);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproveById

	public ResponseMessage deleteFarmerIncomeSourceById(int id) {
		try {
			Optional<FarmerIncomeSource> foundIncomeSource = farmerIncomeSourceRepository.findById(id);

			if (foundIncomeSource.isPresent()) {

				FarmerIncomeSource IncomeSource = foundIncomeSource.get();
				IncomeSource.setStatus(APIConstants.STATUS_DELETED);
				IncomeSource = farmerIncomeSourceRepository.save(IncomeSource);

				approvalUtil.delete(DBConstants.TBL_FARMER_INCOME_SOURCE, IncomeSource.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-IncomeSource" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-IncomeSource" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// deleteFarmerIncomeSourceById

	public FarmerIncomeSource findFarmerIncomeSourceById(int id) {
		try {
			Optional<FarmerIncomeSource> foundIncomeSource = farmerIncomeSourceRepository.findById(id);
			if (foundIncomeSource.isPresent()) {

				return foundIncomeSource.get();
			} else {
				throw new DoesNotExistException("Farmer-IncomeSource" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findFarmerIncomeSourceById

	public ResponseMessage rejectById(int id) {
		try {
			Optional<FarmerIncomeSource> foundFarmerIncomeSource = farmerIncomeSourceRepository.findById(id);

			if (foundFarmerIncomeSource.isPresent()) {

				FarmerIncomeSource farmerIncomeSource = foundFarmerIncomeSource.get();
				farmerIncomeSource.setStatus(APIConstants.STATUS_REJECTED);
				farmerIncomeSource = farmerIncomeSourceRepository.save(farmerIncomeSource);

				approvalUtil.finalApprove(DBConstants.TBL_FARMER_INCOME_SOURCE, farmerIncomeSource.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-IncomeSource " + APIConstants.RESPONSE_REJECT_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-IncomeSource " + APIConstants.RESPONSE_REJECT_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// rejectById

}
