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
import in.cropdata.cdtmasterdata.model.FarmerVipStatus;
import in.cropdata.cdtmasterdata.repository.FarmerVipStatusRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class FarmerVipStatusService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AgriDisposalMethodService.class);
	private static final String SERVER_ERROR_MSG = "Server Error : ";
	
	@Autowired
	private FarmerVipStatusRepository farmerVipStatusRepository;

	@Autowired
	private AclHistoryUtil approvalUtil;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	public List<FarmerVipStatus> getAllFarmerVipStatus() {
		try {
			List<FarmerVipStatus> list = farmerVipStatusRepository.findAll();

			return list;

		} catch (Exception e) {
			throw e;
		}
	}// getAllFarmerVipStatus
	
	public Page<FarmerVipStatus> getFarmerVipStatusListByPagenation(Integer page, Integer size, String searchText){
		try {
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());
			return farmerVipStatusRepository.getFarmerVipStatusListByPagenation(sortedByIdDesc, searchText);
		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Farmer-VipStatus Data Found For Searched Text -> " + searchText);
		}
	}

	public ResponseMessage addFarmerVipStatus(FarmerVipStatus farmerVipStatus) {

		try {
			farmerVipStatus = farmerVipStatusRepository.save(farmerVipStatus);

			approvalUtil.addRecord(DBConstants.TBL_FARMER_VIP_STATUS, farmerVipStatus.getId());

			return responseMessageUtil.sendResponse(true, "Farmer-VipStatus" + APIConstants.RESPONSE_ADD_SUCCESS, "");

		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// addAllFarmerVipStatus

	public ResponseMessage updateFarmerVipStatusById(int id, FarmerVipStatus farmerVipStatus) {
		try {
			Optional<FarmerVipStatus> foundVipStatus = farmerVipStatusRepository.findById(id);

			if (foundVipStatus.isPresent()) {

				farmerVipStatus.setId(id);
				farmerVipStatus = farmerVipStatusRepository.save(farmerVipStatus);

				approvalUtil.updateRecord(DBConstants.TBL_FARMER_VIP_STATUS, farmerVipStatus.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-VipStatus" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-VipStatus" + APIConstants.RESPONSE_UPDATE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// updateFarmerVipStatusById

	public ResponseMessage primaryApproveById(int id) {
		try {
			Optional<FarmerVipStatus> foundVipStatus = farmerVipStatusRepository.findById(id);

			if (foundVipStatus.isPresent()) {

				FarmerVipStatus VipStatus = foundVipStatus.get();
				VipStatus.setStatus(APIConstants.STATUS_APPROVED);

				VipStatus = farmerVipStatusRepository.save(VipStatus);

				approvalUtil.primaryApprove(DBConstants.TBL_FARMER_VIP_STATUS, VipStatus.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-VipStatus" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-VipStatus" + APIConstants.RESPONSE_PRIMARY_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage finalApproveById(int id) {
		try {
			Optional<FarmerVipStatus> foundVipStatus = farmerVipStatusRepository.findById(id);

			if (foundVipStatus.isPresent()) {

				FarmerVipStatus VipStatus = foundVipStatus.get();
				VipStatus.setStatus(APIConstants.STATUS_ACTIVE);

				VipStatus = farmerVipStatusRepository.save(VipStatus);

				approvalUtil.finalApprove(DBConstants.TBL_FARMER_VIP_STATUS, VipStatus.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-VipStatus" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-VipStatus" + APIConstants.RESPONSE_FINAL_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproveById

	public ResponseMessage deleteFarmerVipStatusById(int id) {
		try {
			Optional<FarmerVipStatus> foundVipStatus = farmerVipStatusRepository.findById(id);

			if (foundVipStatus.isPresent()) {

				FarmerVipStatus VipStatus = foundVipStatus.get();
				VipStatus.setStatus(APIConstants.STATUS_DELETED);

				VipStatus = farmerVipStatusRepository.save(VipStatus);

				approvalUtil.delete(DBConstants.TBL_FARMER_VIP_STATUS, VipStatus.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-VipStatus" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-VipStatus" + APIConstants.RESPONSE_DELETE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// deleteFarmerVipStatusById

	public FarmerVipStatus findFarmerVipStatusById(int id) {
		try {
			Optional<FarmerVipStatus> foundVipStatus = farmerVipStatusRepository.findById(id);

			if (foundVipStatus.isPresent()) {
				return foundVipStatus.get();
			} else {
				throw new DoesNotExistException("Farmer-VipStatus" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findFarmerVipStatusById
	
    public ResponseMessage rejectById(int id) {
  try {
      Optional<FarmerVipStatus> foundFarmerVipStatus = farmerVipStatusRepository.findById(id);

      if (foundFarmerVipStatus.isPresent()) {

    FarmerVipStatus farmerVipStatus = foundFarmerVipStatus.get();
    farmerVipStatus.setStatus(APIConstants.STATUS_REJECTED);
    farmerVipStatus = farmerVipStatusRepository.save(farmerVipStatus);

    approvalUtil.finalApprove(DBConstants.TBL_FARMER_VIP_STATUS, farmerVipStatus.getId());

    return responseMessageUtil.sendResponse(true, "Farmer-VipStatus" + APIConstants.RESPONSE_REJECT_SUCCESS, "");
      } else {
    return responseMessageUtil.sendResponse(false, "", "Farmer-VipStatus" + APIConstants.RESPONSE_REJECT_ERROR);
      }
  } catch (Exception e) {
      return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
  }
    }//rejectById 
}
