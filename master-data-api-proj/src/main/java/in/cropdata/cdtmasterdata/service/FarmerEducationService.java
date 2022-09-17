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
import in.cropdata.cdtmasterdata.model.FarmerEducation;
import in.cropdata.cdtmasterdata.repository.FarmerEducationRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class FarmerEducationService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FarmerEducationService.class);
	private static final String SERVER_ERROR_MSG = "Server Error : ";

	@Autowired
	private FarmerEducationRepository farmerEducationRepository;

	@Autowired
	private AclHistoryUtil approvalUtil;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	public List<FarmerEducation> getAllFarmerEducation() {
		try {
			List<FarmerEducation> list = farmerEducationRepository.findAll();

			return list;

		} catch (Exception e) {
			throw e;
		}
	}// getAllFarmerEducation
	
	public Page<FarmerEducation> getFarmarEducationListByPagenation(Integer page, Integer size, String searchText){
		try {
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());
			return farmerEducationRepository.getFarmarEducationListByPagenation(sortedByIdDesc, searchText);
		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Farmer-Education Data Found For Searched Text -> " + searchText);
		}
	}

	public ResponseMessage addFarmerEducation(FarmerEducation farmerEducation) {

		try {
			farmerEducation = farmerEducationRepository.save(farmerEducation);

			approvalUtil.addRecord(DBConstants.TBL_FARMER_EDUCATION, farmerEducation.getId());

			return responseMessageUtil.sendResponse(true, "Farmer-Education" + APIConstants.RESPONSE_ADD_SUCCESS, "");

		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// addAllFarmerEducation

	public ResponseMessage updateFarmerEducationById(int id, FarmerEducation farmerEducation) {
		try {
			Optional<FarmerEducation> foundEducation = farmerEducationRepository.findById(id);

			if (foundEducation.isPresent()) {

				farmerEducation.setId(id);
				farmerEducation = farmerEducationRepository.save(farmerEducation);

				approvalUtil.updateRecord(DBConstants.TBL_FARMER_EDUCATION, farmerEducation.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-Education" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-Education" + APIConstants.RESPONSE_UPDATE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// updateFarmerEducationById

	public ResponseMessage primaryApproveById(int id) {
		try {
			Optional<FarmerEducation> foundEducation = farmerEducationRepository.findById(id);

			if (foundEducation.isPresent()) {

				FarmerEducation education = foundEducation.get();
				education.setStatus(APIConstants.STATUS_APPROVED);

				education = farmerEducationRepository.save(education);

				approvalUtil.primaryApprove(DBConstants.TBL_FARMER_EDUCATION, education.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-Education" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-Education" + APIConstants.RESPONSE_PRIMARY_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage finalApproveById(int id) {
		try {
			Optional<FarmerEducation> foundEducation = farmerEducationRepository.findById(id);

			if (foundEducation.isPresent()) {

				FarmerEducation education = foundEducation.get();
				education.setStatus(APIConstants.STATUS_ACTIVE);

				education = farmerEducationRepository.save(education);

				approvalUtil.finalApprove(DBConstants.TBL_FARMER_EDUCATION, education.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-Education" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-Education" + APIConstants.RESPONSE_FINAL_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproveById

	public ResponseMessage deleteFarmerEducationById(int id) {
		try {
			Optional<FarmerEducation> foundEducation = farmerEducationRepository.findById(id);

			if (foundEducation.isPresent()) {

				FarmerEducation education = foundEducation.get();
				education.setStatus(APIConstants.STATUS_DELETED);

				education = farmerEducationRepository.save(education);

				approvalUtil.delete(DBConstants.TBL_FARMER_EDUCATION, education.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-Education" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-Education" + APIConstants.RESPONSE_DELETE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// deleteFarmerEducationById

	public FarmerEducation findFarmerEducationById(int id) {
		try {
			Optional<FarmerEducation> foundEducation = farmerEducationRepository.findById(id);

			if (foundEducation.isPresent()) {
				return foundEducation.get();
			} else {
				throw new DoesNotExistException("Farmer-Education" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findFarmerEducationById
	
    public ResponseMessage rejectById(int id) {
  try {
      Optional<FarmerEducation> foundFarmerEducation = farmerEducationRepository.findById(id);

      if (foundFarmerEducation.isPresent()) {

    FarmerEducation farmerEducation = foundFarmerEducation.get();
    farmerEducation.setStatus(APIConstants.STATUS_REJECTED);
    farmerEducation = farmerEducationRepository.save(farmerEducation);

    approvalUtil.finalApprove(DBConstants.TBL_FARMER_EDUCATION, farmerEducation.getId());

    return responseMessageUtil.sendResponse(true, "Farmer-Education" + APIConstants.RESPONSE_REJECT_SUCCESS, "");
      } else {
    return responseMessageUtil.sendResponse(false, "", "Farmer-Education" + APIConstants.RESPONSE_REJECT_ERROR);
      }
  } catch (Exception e) {
      return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
  }
    }//rejectById 

}// addAllFarmerEducation
