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
import in.cropdata.cdtmasterdata.model.FarmerMobileType;
import in.cropdata.cdtmasterdata.repository.FarmerMobileTypeRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class FarmerMobileTypeService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FarmerMobileTypeService.class);
	private static final String SERVER_ERROR_MSG = "Server Error : ";
	
	@Autowired
	private FarmerMobileTypeRepository farmerMobileTypeRepository;
	
	@Autowired
	private AclHistoryUtil approvalUtil;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	public List<FarmerMobileType> getAllFarmerMobileType() {
		try {
			List<FarmerMobileType> list = farmerMobileTypeRepository.findAll();

			return list;

		} catch (Exception e) {
			throw e;
		}
	}// getAllFarmerMobileType
	
	public Page<FarmerMobileType> getFarmerMobileTypeListByPagenation(Integer page, Integer size, String searchText){
		try {
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());
			return farmerMobileTypeRepository.getFarmerMobileTypeListByPagenation(sortedByIdDesc, searchText);
		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Farmer-MobileType Data Found For Searched Text -> " + searchText);
		}
	}

	public ResponseMessage addFarmerMobileType(FarmerMobileType farmerMobileType) {

		try {
			farmerMobileType = farmerMobileTypeRepository.save(farmerMobileType);

			approvalUtil.addRecord(DBConstants.TBL_FARMER_MOBILE_TYPE, farmerMobileType.getId());

			return responseMessageUtil.sendResponse(true, "Farmer-MobileType" + APIConstants.RESPONSE_ADD_SUCCESS, "");

		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// addAllFarmerMobileType

	public ResponseMessage updateFarmerMobileTypeById(int id, FarmerMobileType farmerMobileType) {
		try {
			Optional<FarmerMobileType> foundMobileType = farmerMobileTypeRepository.findById(id);

			if (foundMobileType.isPresent()) {

				farmerMobileType.setId(id);
				farmerMobileType = farmerMobileTypeRepository.save(farmerMobileType);

				approvalUtil.updateRecord(DBConstants.TBL_FARMER_MOBILE_TYPE, farmerMobileType.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-MobileType" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-MobileType" + APIConstants.RESPONSE_UPDATE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// updateFarmerMobileTypeById

	public ResponseMessage primaryApproveById(int id) {
		try {
			Optional<FarmerMobileType> foundMobileType = farmerMobileTypeRepository.findById(id);

			if (foundMobileType.isPresent()) {

				FarmerMobileType MobileType = foundMobileType.get();
				MobileType.setStatus(APIConstants.STATUS_APPROVED);

				MobileType = farmerMobileTypeRepository.save(MobileType);

				approvalUtil.primaryApprove(DBConstants.TBL_FARMER_MOBILE_TYPE, MobileType.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-MobileType" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-MobileType" + APIConstants.RESPONSE_PRIMARY_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage finalApproveById(int id) {
		try {
			Optional<FarmerMobileType> foundMobileType = farmerMobileTypeRepository.findById(id);

			if (foundMobileType.isPresent()) {

				FarmerMobileType MobileType = foundMobileType.get();
				MobileType.setStatus(APIConstants.STATUS_ACTIVE);

				MobileType = farmerMobileTypeRepository.save(MobileType);

				approvalUtil.finalApprove(DBConstants.TBL_FARMER_MOBILE_TYPE, MobileType.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-MobileType" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-MobileType" + APIConstants.RESPONSE_FINAL_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproveById

	public ResponseMessage deleteFarmerMobileTypeById(int id) {
		try {
			Optional<FarmerMobileType> foundMobileType = farmerMobileTypeRepository.findById(id);

			if (foundMobileType.isPresent()) {

				FarmerMobileType MobileType = foundMobileType.get();
				MobileType.setStatus(APIConstants.STATUS_DELETED);

				MobileType = farmerMobileTypeRepository.save(MobileType);

				approvalUtil.delete(DBConstants.TBL_FARMER_MOBILE_TYPE, MobileType.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-MobileType" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-MobileType" + APIConstants.RESPONSE_DELETE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// deleteFarmerMobileTypeById

	public FarmerMobileType findFarmerMobileTypeById(int id) {
		try {
			Optional<FarmerMobileType> foundMobileType = farmerMobileTypeRepository.findById(id);

			if (foundMobileType.isPresent()) {
				return foundMobileType.get();
			} else {
				throw new DoesNotExistException("Farmer-MobileType" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findFarmerMobileTypeById
	
    public ResponseMessage rejectById(int id) {
  try {
      Optional<FarmerMobileType> foundFarmerMobileType = farmerMobileTypeRepository.findById(id);

      if (foundFarmerMobileType.isPresent()) {

    FarmerMobileType farmerMobileType = foundFarmerMobileType.get();
    farmerMobileType.setStatus(APIConstants.STATUS_REJECTED);
    farmerMobileType = farmerMobileTypeRepository.save(farmerMobileType);

    approvalUtil.finalApprove(DBConstants.TBL_FARMER_MOBILE_TYPE, farmerMobileType.getId());

    return responseMessageUtil.sendResponse(true, "Farmer-MobileType" + APIConstants.RESPONSE_REJECT_SUCCESS, "");
      } else {
    return responseMessageUtil.sendResponse(false, "", "Farmer-MobileType" + APIConstants.RESPONSE_REJECT_ERROR);
      }
  } catch (Exception e) {
      return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
  }
    }//rejectById 

}
