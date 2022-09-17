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
import in.cropdata.cdtmasterdata.model.FarmerGovtDepartment;
import in.cropdata.cdtmasterdata.repository.FarmerGovtDepartmentRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class FarmerGovtDepartmentService {

	@Autowired
	private FarmerGovtDepartmentRepository farmerGovtDepartmentRepository;

	@Autowired
	private AclHistoryUtil approvalUtil;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	public List<FarmerGovtDepartment> GetAllFarmerGovtDepartment() {
		try {
			List<FarmerGovtDepartment> list = farmerGovtDepartmentRepository.findAll(Sort.by("departmentName"));

			return list;

		} catch (Exception e) {
			throw e;
		}
	}

	public Page<FarmerGovtDepartment> getAllFarmerGovtDepartmentPaginated(int page, int size) {
		try {
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());
			Page<FarmerGovtDepartment> farmerGovtDepartmentList = farmerGovtDepartmentRepository
					.findAll(sortedByIdDesc);
			return farmerGovtDepartmentList;
		} catch (Exception e) {
			throw e;
		}
	}// getAllFarmerGovtDepartmentPaginated

	public ResponseMessage addFarmerGovtDepartment(FarmerGovtDepartment FarmerGovtDepartment) {

		try {
			FarmerGovtDepartment = farmerGovtDepartmentRepository.save(FarmerGovtDepartment);

			approvalUtil.addRecord(DBConstants.TBL_FARMER_GOVT_DEPARTMENT, FarmerGovtDepartment.getId());

			return responseMessageUtil.sendResponse(true, "Farmer-GovtDepartment" + APIConstants.RESPONSE_ADD_SUCCESS,
					"");

		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// addAllFarmerGovtDepartment

	public ResponseMessage updateFarmerGovtDepartmentById(int id, FarmerGovtDepartment FarmerGovtDepartment) {
		try {
			Optional<FarmerGovtDepartment> foundGovtDepartment = farmerGovtDepartmentRepository.findById(id);

			if (foundGovtDepartment.isPresent()) {

				FarmerGovtDepartment.setId(id);
				FarmerGovtDepartment = farmerGovtDepartmentRepository.save(FarmerGovtDepartment);

				approvalUtil.updateRecord(DBConstants.TBL_FARMER_GOVT_DEPARTMENT, FarmerGovtDepartment.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-GovtDepartment" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-GovtDepartment" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}// updateFarmerGovtDepartmentById

	public ResponseMessage primaryApproveById(int id) {
		try {
			Optional<FarmerGovtDepartment> foundGovtDepartment = farmerGovtDepartmentRepository.findById(id);

			if (foundGovtDepartment.isPresent()) {

				FarmerGovtDepartment GovtDepartment = foundGovtDepartment.get();
				GovtDepartment.setStatus(APIConstants.STATUS_APPROVED);
				GovtDepartment = farmerGovtDepartmentRepository.save(GovtDepartment);

				approvalUtil.primaryApprove(DBConstants.TBL_FARMER_GOVT_DEPARTMENT, GovtDepartment.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-GovtDepartment" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-GovtDepartment" + APIConstants.RESPONSE_NO_RECORD_FOUND);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage finalApproveById(int id) {
		try {
			Optional<FarmerGovtDepartment> foundGovtDepartment = farmerGovtDepartmentRepository.findById(id);

			if (foundGovtDepartment.isPresent()) {

				FarmerGovtDepartment GovtDepartment = foundGovtDepartment.get();
				GovtDepartment.setStatus(APIConstants.STATUS_ACTIVE);
				GovtDepartment = farmerGovtDepartmentRepository.save(GovtDepartment);

				approvalUtil.finalApprove(DBConstants.TBL_FARMER_GOVT_DEPARTMENT, GovtDepartment.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-GovtDepartment" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-GovtDepartment" + APIConstants.RESPONSE_NO_RECORD_FOUND);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproveById

	public ResponseMessage deleteFarmerGovtDepartmentById(int id) {
		try {
			Optional<FarmerGovtDepartment> foundGovtDepartment = farmerGovtDepartmentRepository.findById(id);

			if (foundGovtDepartment.isPresent()) {

				FarmerGovtDepartment GovtDepartment = foundGovtDepartment.get();
				GovtDepartment.setStatus(APIConstants.STATUS_DELETED);
				GovtDepartment = farmerGovtDepartmentRepository.save(GovtDepartment);

				approvalUtil.delete(DBConstants.TBL_FARMER_GOVT_DEPARTMENT, GovtDepartment.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-GovtDepartment" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-GovtDepartment" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// deleteFarmerGovtDepartmentById

	public FarmerGovtDepartment findFarmerGovtDepartmentById(int id) {
		try {
			Optional<FarmerGovtDepartment> foundGovtDepartment = farmerGovtDepartmentRepository.findById(id);
			if (foundGovtDepartment.isPresent()) {

				return foundGovtDepartment.get();
			} else {
				throw new DoesNotExistException("Farmer-GovtDepartment" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findFarmerGovtDepartmentById

	public ResponseMessage rejectById(int id) {
		try {
			Optional<FarmerGovtDepartment> foundFarmerGovtDepartment = farmerGovtDepartmentRepository.findById(id);

			if (foundFarmerGovtDepartment.isPresent()) {

				FarmerGovtDepartment farmerGovtDepartment = foundFarmerGovtDepartment.get();
				farmerGovtDepartment.setStatus(APIConstants.STATUS_REJECTED);
				farmerGovtDepartment = farmerGovtDepartmentRepository.save(farmerGovtDepartment);

				approvalUtil.finalApprove(DBConstants.TBL_FARMER_GOVT_DEPARTMENT, farmerGovtDepartment.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-GovtDepartment" + APIConstants.RESPONSE_REJECT_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-GovtDepartment" + APIConstants.RESPONSE_REJECT_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// rejectById

}
