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
import in.cropdata.cdtmasterdata.model.FarmerIdProof;
import in.cropdata.cdtmasterdata.repository.FarmerIdProofRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class FarmerIdProofService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AgriDisposalMethodService.class);
	private static final String SERVER_ERROR_MSG = "Server Error : ";

	@Autowired
	private FarmerIdProofRepository farmerIdProofRepository;

	@Autowired
	private AclHistoryUtil approvalUtil;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	public List<FarmerIdProof> getAllFarmerIdProof() {
		try {
			List<FarmerIdProof> list = farmerIdProofRepository.findAll();

			return list;
		} catch (Exception e) {
			throw e;
		}
	}// getAllFarmerIdProof
	
	public Page<FarmerIdProof> getFarmerIdProofListByPagenation(Integer page, Integer size, String searchText){
		try {
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());
			return farmerIdProofRepository.getFarmerIdProofListByPagenation(sortedByIdDesc, searchText);
		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Farmer-IdProof Data Found For Searched Text -> " + searchText);
		}
	}

	public ResponseMessage addFarmerIdProof(FarmerIdProof farmerIdProof) {

		try {
			farmerIdProof = farmerIdProofRepository.save(farmerIdProof);

			approvalUtil.addRecord(DBConstants.TBL_FARMER_ID_PROOF, farmerIdProof.getId());

			return responseMessageUtil.sendResponse(true, "Farmer-IdProof" + APIConstants.RESPONSE_ADD_SUCCESS, "");

		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// addFarmerIdProof

	public ResponseMessage updateFarmerIdProofById(int id, FarmerIdProof farmerIdProof) {
		try {
			Optional<FarmerIdProof> foundIdProof = farmerIdProofRepository.findById(id);

			if (foundIdProof.isPresent()) {

				farmerIdProof.setId(id);
				farmerIdProof = farmerIdProofRepository.save(farmerIdProof);

				approvalUtil.updateRecord(DBConstants.TBL_FARMER_ID_PROOF, farmerIdProof.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-IdProof" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-IdProof" + APIConstants.RESPONSE_UPDATE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// updateFarmerIdProofById

	public ResponseMessage primaryApproveById(int id) {
		try {
			Optional<FarmerIdProof> fonudIdProof = farmerIdProofRepository.findById(id);

			if (fonudIdProof.isPresent()) {

				FarmerIdProof idProof = fonudIdProof.get();
				idProof.setStatus(APIConstants.STATUS_APPROVED);
				idProof = farmerIdProofRepository.save(idProof);

				approvalUtil.primaryApprove(DBConstants.TBL_FARMER_ID_PROOF, idProof.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-IdProof" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-IdProof" + APIConstants.RESPONSE_PRIMARY_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage finalApproveById(int id) {
		try {
			Optional<FarmerIdProof> fonudIdProof = farmerIdProofRepository.findById(id);

			if (fonudIdProof.isPresent()) {

				FarmerIdProof idProof = fonudIdProof.get();
				idProof.setStatus(APIConstants.STATUS_ACTIVE);
				idProof = farmerIdProofRepository.save(idProof);

				approvalUtil.finalApprove(DBConstants.TBL_FARMER_ID_PROOF, idProof.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-IdProof" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-IdProof" + APIConstants.RESPONSE_FINAL_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproveById

	public ResponseMessage deleteFarmerIdProofById(int id) {
		try {
			Optional<FarmerIdProof> fonudIdProof = farmerIdProofRepository.findById(id);

			if (fonudIdProof.isPresent()) {

				FarmerIdProof idProof = fonudIdProof.get();
				idProof.setStatus(APIConstants.STATUS_DELETED);
				idProof = farmerIdProofRepository.save(idProof);

				approvalUtil.delete(DBConstants.TBL_FARMER_ID_PROOF, idProof.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-IdProof" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-IdProof" + APIConstants.RESPONSE_DELETE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// deleteFarmerIdProofById

	public FarmerIdProof findFarmerIdProofById(int id) {
		try {
			Optional<FarmerIdProof> foundIdProof = farmerIdProofRepository.findById(id);

			if (foundIdProof.isPresent()) {
				return foundIdProof.get();
			} else {
				throw new DoesNotExistException("Farmer-IdProof" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findFarmerIdProofById

	public ResponseMessage rejectById(int id) {
		try {
			Optional<FarmerIdProof> foundFarmerIdProof = farmerIdProofRepository.findById(id);

			if (foundFarmerIdProof.isPresent()) {

				FarmerIdProof farmerIdProof = foundFarmerIdProof.get();
				farmerIdProof.setStatus(APIConstants.STATUS_REJECTED);
				farmerIdProof = farmerIdProofRepository.save(farmerIdProof);

				approvalUtil.finalApprove(DBConstants.TBL_FARMER_ID_PROOF, farmerIdProof.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-IdProof " + APIConstants.RESPONSE_REJECT_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-IdProof " + APIConstants.RESPONSE_REJECT_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// rejectById

}// addAllFarmerIdProof
