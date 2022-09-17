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
import in.cropdata.cdtmasterdata.model.FarmerProxyRelationType;
import in.cropdata.cdtmasterdata.repository.FarmerProxyRelationTypeRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class FarmerProxyRelationTypeService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FarmerProxyRelationTypeService.class);
	private static final String SERVER_ERROR_MSG = "Server Error : ";
	
	@Autowired
	private FarmerProxyRelationTypeRepository farmerProxyRelationTypeRepository;
	
	@Autowired
	private AclHistoryUtil approvalUtil;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	public List<FarmerProxyRelationType> getAllFarmerProxyRelationType() {
		try {
			List<FarmerProxyRelationType> list = farmerProxyRelationTypeRepository.findAll();

			return list;

		} catch (Exception e) {
			throw e;
		}
	}// getAllFarmerProxyRelationType

	public Page<FarmerProxyRelationType> getFarmerProxyRelationTypeListByPagenation(Integer page, Integer size, String searchText){
		try {
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());
			return farmerProxyRelationTypeRepository.getFarmerProxyRelationTypeListByPagenation(sortedByIdDesc, searchText);
		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Farmer-ProxyRelationType Data Found For Searched Text -> " + searchText);
		}
	}

	public ResponseMessage addFarmerProxyRelationType(FarmerProxyRelationType farmerProxyRelationType) {

		try {
			farmerProxyRelationType = farmerProxyRelationTypeRepository.save(farmerProxyRelationType);

			approvalUtil.addRecord(DBConstants.TBL_FARMER_PROXY_RELATION_TYPE, farmerProxyRelationType.getId());
			
			return responseMessageUtil.sendResponse(true, "Farmer-ProxyRelationType" + APIConstants.RESPONSE_ADD_SUCCESS, "");

		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// addAllFarmerProxyRelationType

	public ResponseMessage updateFarmerProxyRelationTypeById(int id, FarmerProxyRelationType farmerProxyRelationType) {
		try {
			Optional<FarmerProxyRelationType> foundProxyRelationType = farmerProxyRelationTypeRepository.findById(id);

			if (foundProxyRelationType.isPresent()) {

				farmerProxyRelationType.setId(id);
				farmerProxyRelationType = farmerProxyRelationTypeRepository.save(farmerProxyRelationType);

				approvalUtil.updateRecord(DBConstants.TBL_FARMER_PROXY_RELATION_TYPE, farmerProxyRelationType.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-ProxyRelationType" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-ProxyRelationType" + APIConstants.RESPONSE_UPDATE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// updateFarmerProxyRelationTypeById

	public ResponseMessage primaryApproveById(int id) {
		try {
			Optional<FarmerProxyRelationType> foundProxyRelationType = farmerProxyRelationTypeRepository.findById(id);

			if (foundProxyRelationType.isPresent()) {

				FarmerProxyRelationType ProxyRelationType = foundProxyRelationType.get();
				ProxyRelationType.setStatus(APIConstants.STATUS_APPROVED);

				ProxyRelationType = farmerProxyRelationTypeRepository.save(ProxyRelationType);

				approvalUtil.primaryApprove(DBConstants.TBL_FARMER_PROXY_RELATION_TYPE, ProxyRelationType.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-ProxyRelationType" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-ProxyRelationType" + APIConstants.RESPONSE_PRIMARY_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage finalApproveById(int id) {
		try {
			Optional<FarmerProxyRelationType> foundProxyRelationType = farmerProxyRelationTypeRepository.findById(id);

			if (foundProxyRelationType.isPresent()) {

				FarmerProxyRelationType ProxyRelationType = foundProxyRelationType.get();
				ProxyRelationType.setStatus(APIConstants.STATUS_ACTIVE);

				ProxyRelationType = farmerProxyRelationTypeRepository.save(ProxyRelationType);

				approvalUtil.finalApprove(DBConstants.TBL_FARMER_PROXY_RELATION_TYPE, ProxyRelationType.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-ProxyRelationType" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-ProxyRelationType" + APIConstants.RESPONSE_FINAL_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproveById

	public ResponseMessage deleteFarmerProxyRelationTypeById(int id) {
		try {
			Optional<FarmerProxyRelationType> foundProxyRelationType = farmerProxyRelationTypeRepository.findById(id);

			if (foundProxyRelationType.isPresent()) {

				FarmerProxyRelationType ProxyRelationType = foundProxyRelationType.get();
				ProxyRelationType.setStatus(APIConstants.STATUS_DELETED);

				ProxyRelationType = farmerProxyRelationTypeRepository.save(ProxyRelationType);

				approvalUtil.delete(DBConstants.TBL_FARMER_PROXY_RELATION_TYPE, ProxyRelationType.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-ProxyRelationType" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-ProxyRelationType" + APIConstants.RESPONSE_DELETE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// deleteFarmerProxyRelationTypeById

	public FarmerProxyRelationType findFarmerProxyRelationTypeById(int id) {
		try {
			Optional<FarmerProxyRelationType> foundProxyRelationType = farmerProxyRelationTypeRepository.findById(id);

			if (foundProxyRelationType.isPresent()) {
				return foundProxyRelationType.get();
			} else {
				throw new DoesNotExistException("Farmer-ProxyRelationType" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findFarmerProxyRelationTypeById
	
    public ResponseMessage rejectById(int id) {
  try {
      Optional<FarmerProxyRelationType> foundFarmerProxyRelationType = farmerProxyRelationTypeRepository.findById(id);

      if (foundFarmerProxyRelationType.isPresent()) {

    FarmerProxyRelationType farmerProxyRelationType = foundFarmerProxyRelationType.get();
    farmerProxyRelationType.setStatus(APIConstants.STATUS_REJECTED);
    farmerProxyRelationType = farmerProxyRelationTypeRepository.save(farmerProxyRelationType);

    approvalUtil.finalApprove(DBConstants.TBL_FARMER_PROXY_RELATION_TYPE, farmerProxyRelationType.getId());

    return responseMessageUtil.sendResponse(true, "Farmer-ProxyRelationType" + APIConstants.RESPONSE_REJECT_SUCCESS, "");
      } else {
    return responseMessageUtil.sendResponse(false, "", "Farmer-ProxyRelationType" + APIConstants.RESPONSE_REJECT_ERROR);
      }
  } catch (Exception e) {
      return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
  }
    }//rejectById 

}
