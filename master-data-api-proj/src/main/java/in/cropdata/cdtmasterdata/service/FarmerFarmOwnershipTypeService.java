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
import in.cropdata.cdtmasterdata.model.FarmerFarmOwnershipType;
import in.cropdata.cdtmasterdata.repository.FarmerFarmOwnershipTypeRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class FarmerFarmOwnershipTypeService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FarmerFarmOwnershipTypeService.class);
	private static final String SERVER_ERROR_MSG = "Server Error : ";

	@Autowired
	private FarmerFarmOwnershipTypeRepository farmerFarmOwnershipTypeRepository;

	@Autowired
	private AclHistoryUtil approvalUtil;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	public List<FarmerFarmOwnershipType> getAllFarmerFarmOwnershipType() {
		try {
			List<FarmerFarmOwnershipType> list = farmerFarmOwnershipTypeRepository.findAll();

			return list;
		} catch (Exception e) {
			throw e;
		}
	}// getAllFarmerFarmOwnershipType
	
	public Page<FarmerFarmOwnershipType> getFarmerFarmOwnershipTypeListByPagenation(Integer page, Integer size, String searchText){
		try {
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());
			return farmerFarmOwnershipTypeRepository.getFarmerFarmOwnershipTypeListByPagenation(sortedByIdDesc, searchText);
		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Farmer-FarmOwnershipType Data Found For Searched Text -> " + searchText);
		}
	}


	public ResponseMessage addFarmerFarmOwnershipType(FarmerFarmOwnershipType farmerFarmOwnershipType) {

		try {
			farmerFarmOwnershipType = farmerFarmOwnershipTypeRepository.save(farmerFarmOwnershipType);

			approvalUtil.addRecord(DBConstants.TBL_FARMER_FARM_OWNERSHIP_TYPE, farmerFarmOwnershipType.getId());

			return responseMessageUtil.sendResponse(true,
					"Farmer-FarmOwnershipType" + APIConstants.RESPONSE_ADD_SUCCESS, "");

		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// addFarmerFarmOwnershipType

	public ResponseMessage updateFarmerFarmOwnershipTypeById(int id, FarmerFarmOwnershipType farmerFarmOwnershipType) {
		try {
			Optional<FarmerFarmOwnershipType> foundFarmOwnershipType = farmerFarmOwnershipTypeRepository.findById(id);

			if (foundFarmOwnershipType.isPresent()) {

				farmerFarmOwnershipType.setId(id);
				farmerFarmOwnershipType = farmerFarmOwnershipTypeRepository.save(farmerFarmOwnershipType);

				approvalUtil.updateRecord(DBConstants.TBL_FARMER_FARM_OWNERSHIP_TYPE, farmerFarmOwnershipType.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-FarmOwnershipType" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-FarmOwnershipType" + APIConstants.RESPONSE_UPDATE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// updateFarmerFarmOwnershipTypeById

	public ResponseMessage primaryApproveById(int id) {
		try {
			Optional<FarmerFarmOwnershipType> foundFarmOwnership = farmerFarmOwnershipTypeRepository.findById(id);

			if (foundFarmOwnership.isPresent()) {

				FarmerFarmOwnershipType farmOwnership = foundFarmOwnership.get();
				farmOwnership.setStatus(APIConstants.STATUS_APPROVED);

				farmOwnership = farmerFarmOwnershipTypeRepository.save(farmOwnership);

				approvalUtil.primaryApprove(DBConstants.TBL_FARMER_FARM_OWNERSHIP_TYPE, farmOwnership.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-FarmOwnershipType" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-FarmOwnershipType" + APIConstants.RESPONSE_PRIMARY_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage finalApproveById(int id) {
		try {
			Optional<FarmerFarmOwnershipType> foundFarmOwnership = farmerFarmOwnershipTypeRepository.findById(id);

			if (foundFarmOwnership.isPresent()) {

				FarmerFarmOwnershipType farmOwnership = foundFarmOwnership.get();
				farmOwnership.setStatus(APIConstants.STATUS_ACTIVE);

				farmOwnership = farmerFarmOwnershipTypeRepository.save(farmOwnership);

				approvalUtil.finalApprove(DBConstants.TBL_FARMER_FARM_OWNERSHIP_TYPE, farmOwnership.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-FarmOwnershipType" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-FarmOwnershipType" + APIConstants.RESPONSE_FINAL_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproveById

	public ResponseMessage deleteFarmerFarmOwnershipTypeById(int id) {
		try {
			Optional<FarmerFarmOwnershipType> foundFarmOwnership = farmerFarmOwnershipTypeRepository.findById(id);

			if (foundFarmOwnership.isPresent()) {

				FarmerFarmOwnershipType farmOwnership = foundFarmOwnership.get();
				farmOwnership.setStatus(APIConstants.STATUS_DELETED);

				farmOwnership = farmerFarmOwnershipTypeRepository.save(farmOwnership);

				approvalUtil.delete(DBConstants.TBL_FARMER_FARM_OWNERSHIP_TYPE, farmOwnership.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-FarmOwnershipType" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-FarmOwnershipType" + APIConstants.RESPONSE_DELETE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// deleteFarmerFarmOwnershipTypeById

	public FarmerFarmOwnershipType findFarmerFarmOwnershipTypeById(int id) {
		try {
			Optional<FarmerFarmOwnershipType> foundFarmOwnershipType = farmerFarmOwnershipTypeRepository.findById(id);
			if (foundFarmOwnershipType.isPresent()) {
				return foundFarmOwnershipType.get();
			} else {
				throw new DoesNotExistException("Farmer-FarmOwnershipType" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findFarmerFarmOwnershipTypeById

	public ResponseMessage rejectById(int id) {
		try {
			Optional<FarmerFarmOwnershipType> foundFarmerFarmOwnershipType = farmerFarmOwnershipTypeRepository
					.findById(id);

			if (foundFarmerFarmOwnershipType.isPresent()) {

				FarmerFarmOwnershipType farmerFarmOwnershipType = foundFarmerFarmOwnershipType.get();
				farmerFarmOwnershipType.setStatus(APIConstants.STATUS_REJECTED);
				farmerFarmOwnershipType = farmerFarmOwnershipTypeRepository.save(farmerFarmOwnershipType);

				approvalUtil.finalApprove(DBConstants.TBL_FARMER_FARM_OWNERSHIP_TYPE, farmerFarmOwnershipType.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-FarmOwnershipType" + APIConstants.RESPONSE_REJECT_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-FarmOwnershipType" + APIConstants.RESPONSE_REJECT_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// rejectById

}// addAllFarmerFarmOwnershipType
