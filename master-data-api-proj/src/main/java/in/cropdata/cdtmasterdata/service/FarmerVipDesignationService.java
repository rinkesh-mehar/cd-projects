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
import in.cropdata.cdtmasterdata.model.FarmerVipDesignation;
import in.cropdata.cdtmasterdata.repository.FarmerVipDesignationRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class FarmerVipDesignationService {

	@Autowired
	private FarmerVipDesignationRepository farmerVipDesignationRepository;

	@Autowired
	private AclHistoryUtil approvalUtil;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	@GetMapping("/all-farmer-vipDesignation")
	public List<FarmerVipDesignation> getAllFarmerVipDesignations() {
		try {
			List<FarmerVipDesignation> list = farmerVipDesignationRepository.findAll();

			return list;

		} catch (Exception e) {
			throw e;
		}
	}// getAllFarmerVipDesignations

	public Page<FarmerVipDesignation> getAllFarmerVipDesignationPaginated(int page, int size) {
		try {
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());
			Page<FarmerVipDesignation> VipDesignationList = farmerVipDesignationRepository.findAll(sortedByIdDesc);
			return VipDesignationList;
		} catch (Exception e) {
			throw e;
		}
	}// getAllFarmerVipDesignationPaginated

	public ResponseMessage addFarmerVipDesignation(FarmerVipDesignation farmerVipDesignation) {

		try {
			farmerVipDesignation = farmerVipDesignationRepository.save(farmerVipDesignation);

			approvalUtil.addRecord(DBConstants.TBL_FARMER_VIP_DESIGNATION, farmerVipDesignation.getId());

			return responseMessageUtil.sendResponse(true, "Farmer-VipDesignation" + APIConstants.RESPONSE_ADD_SUCCESS,
					"");

		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// addAllFarmerVipDesignation

	public ResponseMessage updateFarmerVipDesignationById(int id, FarmerVipDesignation farmerVipDesignation) {
		try {
			Optional<FarmerVipDesignation> foundVipDesignation = farmerVipDesignationRepository.findById(id);

			if (foundVipDesignation.isPresent()) {

				farmerVipDesignation.setId(id);
				farmerVipDesignation = farmerVipDesignationRepository.save(farmerVipDesignation);

				approvalUtil.updateRecord(DBConstants.TBL_FARMER_VIP_DESIGNATION, farmerVipDesignation.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-VipDesignation" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-VipDesignation" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}// updateFarmerVipDesignationById

	public ResponseMessage primaryApproveById(int id) {
		try {
			Optional<FarmerVipDesignation> foundVipDesignation = farmerVipDesignationRepository.findById(id);

			if (foundVipDesignation.isPresent()) {

				FarmerVipDesignation VipDesignation = foundVipDesignation.get();
				VipDesignation.setStatus(APIConstants.STATUS_APPROVED);
				VipDesignation = farmerVipDesignationRepository.save(VipDesignation);

				approvalUtil.primaryApprove(DBConstants.TBL_FARMER_VIP_DESIGNATION, VipDesignation.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-VipDesignation" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-VipDesignation" + APIConstants.RESPONSE_NO_RECORD_FOUND);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage finalApproveById(int id) {
		try {
			Optional<FarmerVipDesignation> foundVipDesignation = farmerVipDesignationRepository.findById(id);

			if (foundVipDesignation.isPresent()) {

				FarmerVipDesignation VipDesignation = foundVipDesignation.get();
				VipDesignation.setStatus(APIConstants.STATUS_ACTIVE);
				VipDesignation = farmerVipDesignationRepository.save(VipDesignation);

				approvalUtil.finalApprove(DBConstants.TBL_FARMER_VIP_DESIGNATION, VipDesignation.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-VipDesignation" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-VipDesignation" + APIConstants.RESPONSE_NO_RECORD_FOUND);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproveById

	public ResponseMessage deleteFarmerVipDesignationById(int id) {
		try {
			Optional<FarmerVipDesignation> foundVipDesignation = farmerVipDesignationRepository.findById(id);

			if (foundVipDesignation.isPresent()) {

				FarmerVipDesignation VipDesignation = foundVipDesignation.get();
				VipDesignation.setStatus(APIConstants.STATUS_DELETED);
				VipDesignation = farmerVipDesignationRepository.save(VipDesignation);

				approvalUtil.delete(DBConstants.TBL_FARMER_VIP_DESIGNATION, VipDesignation.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-VipDesignation" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-VipDesignation" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// deleteFarmerVipDesignationById

	public FarmerVipDesignation findFarmerVipDesignationById(int id) {
		try {
			Optional<FarmerVipDesignation> foundVipDesignation = farmerVipDesignationRepository.findById(id);
			if (foundVipDesignation.isPresent()) {

				return foundVipDesignation.get();
			} else {
				throw new DoesNotExistException("Farmer-VipDesignation" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findFarmerVipDesignationById
	
    public ResponseMessage rejectById(int id) {
 try {
     Optional<FarmerVipDesignation> foundFarmerVipDesignation = farmerVipDesignationRepository.findById(id);

     if (foundFarmerVipDesignation.isPresent()) {

   FarmerVipDesignation farmerVipDesignation = foundFarmerVipDesignation.get();
   farmerVipDesignation.setStatus(APIConstants.STATUS_REJECTED);
   farmerVipDesignation = farmerVipDesignationRepository.save(farmerVipDesignation );

   approvalUtil.finalApprove(DBConstants.TBL_FARMER_VIP_DESIGNATION, farmerVipDesignation.getId());

   return responseMessageUtil.sendResponse(true, "Farmer-VipDesignation " + APIConstants.RESPONSE_REJECT_SUCCESS, "");
     } else {
   return responseMessageUtil.sendResponse(false, "", "Farmer-VipDesignation " + APIConstants.RESPONSE_REJECT_ERROR);
     }
 } catch (Exception e) {
     return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
 }
   }//rejectById 
}
