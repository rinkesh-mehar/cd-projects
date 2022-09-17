package in.cropdata.cdtmasterdata.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import in.cropdata.cdtmasterdata.model.FarmerInsuranceType;
import in.cropdata.cdtmasterdata.repository.FarmerInsuranceTypeRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class FarmerInsuranceTypeService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FarmerInsuranceTypeService.class);
	private static final String SERVER_ERROR_MSG = "Server Error : ";

	@Autowired
	private FarmerInsuranceTypeRepository farmerInsuranceTypeRepository;

	@Autowired
	private AclHistoryUtil approvalUtil;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	public List<FarmerInsuranceType> getAllFarmerInsurance() {
		try {
			List<FarmerInsuranceType> list = farmerInsuranceTypeRepository.findAll(Sort.by("name"));

			return list;

		} catch (Exception e) {
			throw e;
		}
	}// getAllFarmerInsurance
	
	public Page<FarmerInsuranceType> getFarmerInsuranceTypeListByPagenation(Integer page, Integer size, String searchText){
		try {
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());
			return farmerInsuranceTypeRepository.getFarmerInsuranceTypeListByPagenation(sortedByIdDesc, searchText);
		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Farmer-Insurance Data Found For Searched Text -> " + searchText);
		}
	}

	public ResponseMessage addFarmerInsurance(FarmerInsuranceType farmerInsuranceType) {

		try {
			farmerInsuranceType = farmerInsuranceTypeRepository.save(farmerInsuranceType);

			approvalUtil.addRecord(DBConstants.TBL_FARMER_INSURANCE_TYPE, farmerInsuranceType.getId());

			return responseMessageUtil.sendResponse(true, "Farmer-Insurance" + APIConstants.RESPONSE_ADD_SUCCESS, "");

		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// addAllFarmerInsurance

	public ResponseMessage updateFarmerInsuranceById(int id, FarmerInsuranceType farmerInsuranceType) {
		try {
			Optional<FarmerInsuranceType> foundInsurance = farmerInsuranceTypeRepository.findById(id);

			if (foundInsurance.isPresent()) {

				farmerInsuranceType.setId(id);
				farmerInsuranceType = farmerInsuranceTypeRepository.save(farmerInsuranceType);

				approvalUtil.updateRecord(DBConstants.TBL_FARMER_INSURANCE_TYPE, farmerInsuranceType.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-Insurance-Type" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-Insurance-Type" + APIConstants.RESPONSE_UPDATE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// updateFarmerInsuranceById

	public ResponseMessage primaryApproveById(int id) {
		try {
			Optional<FarmerInsuranceType> foundInsurance = farmerInsuranceTypeRepository.findById(id);

			if (foundInsurance.isPresent()) {

				FarmerInsuranceType insurance = foundInsurance.get();
				insurance.setStatus(APIConstants.STATUS_APPROVED);

				insurance = farmerInsuranceTypeRepository.save(insurance);

				approvalUtil.primaryApprove(DBConstants.TBL_FARMER_INSURANCE_TYPE, insurance.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-Insurance-Type" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-Insurance-Type" + APIConstants.RESPONSE_PRIMARY_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage finalApproveById(int id) {
		try {
			Optional<FarmerInsuranceType> foundInsurance = farmerInsuranceTypeRepository.findById(id);

			if (foundInsurance.isPresent()) {

				FarmerInsuranceType insurance = foundInsurance.get();
				insurance.setStatus(APIConstants.STATUS_ACTIVE);

				insurance = farmerInsuranceTypeRepository.save(insurance);

				approvalUtil.finalApprove(DBConstants.TBL_FARMER_INSURANCE_TYPE, insurance.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-Insurance-Type" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-Insurance-Type" + APIConstants.RESPONSE_FINAL_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproveById

	public ResponseMessage deleteFarmerInsuranceById(int id) {
		try {
			Optional<FarmerInsuranceType> foundInsurance = farmerInsuranceTypeRepository.findById(id);

			if (foundInsurance.isPresent()) {

				FarmerInsuranceType insurance = foundInsurance.get();
				insurance.setStatus(APIConstants.STATUS_DELETED);

				insurance = farmerInsuranceTypeRepository.save(insurance);

				approvalUtil.delete(DBConstants.TBL_FARMER_INSURANCE_TYPE, insurance.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-Insurance-Type" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-Insurance-Type" + APIConstants.RESPONSE_DELETE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// deleteFarmerInsuranceById

	public FarmerInsuranceType findFarmerInsuranceById(int id) {
		try {
			Optional<FarmerInsuranceType> foundInsurance = farmerInsuranceTypeRepository.findById(id);
			if (foundInsurance.isPresent()) {
				return foundInsurance.get();
			} else {
				throw new DoesNotExistException("Farmer-Insurance-Type" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findFarmerInsuranceById

	public ResponseMessage rejectById(int id) {
		try {
			Optional<FarmerInsuranceType> foundFarmerInsurance = farmerInsuranceTypeRepository.findById(id);

			if (foundFarmerInsurance.isPresent()) {

				FarmerInsuranceType farmerInsuranceType = foundFarmerInsurance.get();
				farmerInsuranceType.setStatus(APIConstants.STATUS_REJECTED);
				farmerInsuranceType = farmerInsuranceTypeRepository.save(farmerInsuranceType);

				approvalUtil.finalApprove(DBConstants.TBL_FARMER_INSURANCE_TYPE, farmerInsuranceType.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-Insurance-Type" + APIConstants.RESPONSE_REJECT_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-Insurance-Type" + APIConstants.RESPONSE_REJECT_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// rejectById

}// addAllFarmerInsurance
