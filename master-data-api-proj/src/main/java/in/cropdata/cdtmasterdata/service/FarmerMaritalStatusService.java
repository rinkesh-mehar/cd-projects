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
import in.cropdata.cdtmasterdata.model.FarmerMaritalStatus;
import in.cropdata.cdtmasterdata.repository.FarmerMaritalStatusRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class FarmerMaritalStatusService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AgriDisposalMethodService.class);
	private static final String SERVER_ERROR_MSG = "Server Error : ";
	
	@Autowired
	private FarmerMaritalStatusRepository farmerMaritalStatusRepository;
	
	@Autowired
	private AclHistoryUtil approvalUtil;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	public List<FarmerMaritalStatus> getAllFarmerMaritalStatus() {
		try {
			List<FarmerMaritalStatus> list = farmerMaritalStatusRepository.findAll();

			return list;

		} catch (Exception e) {
			throw e;
		}
	}// getAllFarmerMaritalStatus
	
	public Page<FarmerMaritalStatus> getFarmerMaritalStatusListByPagenation(Integer page, Integer size, String searchText){
		try {
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());
			return farmerMaritalStatusRepository.getFarmerMaritalStatusListByPagenation(sortedByIdDesc, searchText);
		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Farmer-Marital-Status Data Found For Searched Text -> " + searchText);
		}
	}

	public ResponseMessage addFarmerMaritalStatus(FarmerMaritalStatus farmerMaritalStatus) {

		try {
			farmerMaritalStatus = farmerMaritalStatusRepository.save(farmerMaritalStatus);

			approvalUtil.addRecord(DBConstants.TBL_FARMER_MARITAL_STATUS, farmerMaritalStatus.getId());

			return responseMessageUtil.sendResponse(true, "Farmer-Marital-Status" + APIConstants.RESPONSE_ADD_SUCCESS, "");

		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// addAllFarmerMaritalStatus

	public ResponseMessage updateFarmerMaritalStatusById(int id, FarmerMaritalStatus farmerMaritalStatus) {
		try {
			Optional<FarmerMaritalStatus> foundMaritalStatus = farmerMaritalStatusRepository.findById(id);

			if (foundMaritalStatus.isPresent()) {

				farmerMaritalStatus.setId(id);
				farmerMaritalStatus = farmerMaritalStatusRepository.save(farmerMaritalStatus);

				approvalUtil.updateRecord(DBConstants.TBL_FARMER_MARITAL_STATUS, farmerMaritalStatus.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-Marital-Status" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-Marital-Status" + APIConstants.RESPONSE_UPDATE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// updateFarmerMaritalStatusById

	public ResponseMessage primaryApproveById(int id) {
		try {
			Optional<FarmerMaritalStatus> foundMaritalStatus = farmerMaritalStatusRepository.findById(id);

			if (foundMaritalStatus.isPresent()) {

				FarmerMaritalStatus MaritalStatus = foundMaritalStatus.get();
				MaritalStatus.setStatus(APIConstants.STATUS_APPROVED);

				MaritalStatus = farmerMaritalStatusRepository.save(MaritalStatus);

				approvalUtil.primaryApprove(DBConstants.TBL_FARMER_MARITAL_STATUS, MaritalStatus.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-Marital-Status" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-Marital-Status" + APIConstants.RESPONSE_PRIMARY_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage finalApproveById(int id) {
		try {
			Optional<FarmerMaritalStatus> foundMaritalStatus = farmerMaritalStatusRepository.findById(id);

			if (foundMaritalStatus.isPresent()) {

				FarmerMaritalStatus MaritalStatus = foundMaritalStatus.get();
				MaritalStatus.setStatus(APIConstants.STATUS_ACTIVE);

				MaritalStatus = farmerMaritalStatusRepository.save(MaritalStatus);

				approvalUtil.finalApprove(DBConstants.TBL_FARMER_MARITAL_STATUS, MaritalStatus.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-Marital-Status" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-Marital-Status" + APIConstants.RESPONSE_FINAL_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproveById

	public ResponseMessage deleteFarmerMaritalStatusById(int id) {
		try {
			Optional<FarmerMaritalStatus> foundMaritalStatus = farmerMaritalStatusRepository.findById(id);

			if (foundMaritalStatus.isPresent()) {

				FarmerMaritalStatus MaritalStatus = foundMaritalStatus.get();
				MaritalStatus.setStatus(APIConstants.STATUS_DELETED);

				MaritalStatus = farmerMaritalStatusRepository.save(MaritalStatus);

				approvalUtil.delete(DBConstants.TBL_FARMER_MARITAL_STATUS, MaritalStatus.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-Marital-Status" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-Marital-Status" + APIConstants.RESPONSE_DELETE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// deleteFarmerMaritalStatusById

	public FarmerMaritalStatus findFarmerMaritalStatusById(int id) {
		try {
			Optional<FarmerMaritalStatus> foundMaritalStatus = farmerMaritalStatusRepository.findById(id);

			if (foundMaritalStatus.isPresent()) {
				return foundMaritalStatus.get();
			} else {
				throw new DoesNotExistException("Farmer-Marital-Status" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findFarmerMaritalStatusById
	
    public ResponseMessage rejectById(int id) {
  try {
      Optional<FarmerMaritalStatus> foundFarmerMaritalStatus = farmerMaritalStatusRepository.findById(id);

      if (foundFarmerMaritalStatus.isPresent()) {

    FarmerMaritalStatus farmerMaritalStatus = foundFarmerMaritalStatus.get();
    farmerMaritalStatus.setStatus(APIConstants.STATUS_REJECTED);
    farmerMaritalStatus = farmerMaritalStatusRepository.save(farmerMaritalStatus);

    approvalUtil.finalApprove(DBConstants.TBL_FARMER_MARITAL_STATUS, farmerMaritalStatus.getId());

    return responseMessageUtil.sendResponse(true, "Farmer-Marital-Status" + APIConstants.RESPONSE_REJECT_SUCCESS, "");
      } else {
    return responseMessageUtil.sendResponse(false, "", "Farmer-Marital-Status" + APIConstants.RESPONSE_REJECT_ERROR);
      }
  } catch (Exception e) {
      return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
  }
    }//rejectById 

}
