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
import in.cropdata.cdtmasterdata.model.FarmerEnrollmentPlace;
import in.cropdata.cdtmasterdata.repository.FarmerEnrollmentPlaceRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class FarmerEnrollmentPlaceService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FarmerEnrollmentPlaceService.class);
	private static final String SERVER_ERROR_MSG = "Server Error : ";

	@Autowired
	private FarmerEnrollmentPlaceRepository farmerEnrollmentPlaceRepository;

	@Autowired
	private AclHistoryUtil approvalUtil;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	public List<FarmerEnrollmentPlace> getAllFarmerEnrollmentPlace() {
		try {
			List<FarmerEnrollmentPlace> list = farmerEnrollmentPlaceRepository.findAll();

			return list;

		} catch (Exception e) {
			throw e;
		}
	}// getAllFarmerEnrollmentPlace
	
	public Page<FarmerEnrollmentPlace> getFarmarEnrollmentPlaceListByPagenation(Integer page, Integer size, String searchText){
		try {
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());
			return farmerEnrollmentPlaceRepository.getFarmarEnrollmentPlaceListByPagenation(sortedByIdDesc, searchText);
		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Farmer-EnrollmentPlace Data Found For Searched Text -> " + searchText);
		}
	}

	public ResponseMessage addFarmerEnrollmentPlace(FarmerEnrollmentPlace farmerEnrollmentPlace) {

		try {
			farmerEnrollmentPlace = farmerEnrollmentPlaceRepository.save(farmerEnrollmentPlace);

			approvalUtil.addRecord(DBConstants.TBL_FARMER_ENROLLMENT_PLACE, farmerEnrollmentPlace.getId());

			return responseMessageUtil.sendResponse(true, "Farmer-EnrollmentPlace" + APIConstants.RESPONSE_ADD_SUCCESS,
					"");

		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// addAllFarmerEnrollmentPlace

	public ResponseMessage updateFarmerEnrollmentPlaceById(int id, FarmerEnrollmentPlace farmerEnrollmentPlace) {
		try {
			Optional<FarmerEnrollmentPlace> foundEnrollmentPlace = farmerEnrollmentPlaceRepository.findById(id);

			if (foundEnrollmentPlace.isPresent()) {

				farmerEnrollmentPlace.setId(id);
				farmerEnrollmentPlace = farmerEnrollmentPlaceRepository.save(farmerEnrollmentPlace);

				approvalUtil.updateRecord(DBConstants.TBL_FARMER_ENROLLMENT_PLACE, farmerEnrollmentPlace.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-EnrollmentPlace" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-EnrollmentPlace" + APIConstants.RESPONSE_UPDATE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// updateFarmerEnrollmentPlaceById

	public ResponseMessage primaryApproveById(int id) {
		try {
			Optional<FarmerEnrollmentPlace> foundEnrollmentPlace = farmerEnrollmentPlaceRepository.findById(id);

			if (foundEnrollmentPlace.isPresent()) {

				FarmerEnrollmentPlace enrollmentPlace = foundEnrollmentPlace.get();
				enrollmentPlace.setStatus(APIConstants.STATUS_APPROVED);

				enrollmentPlace = farmerEnrollmentPlaceRepository.save(enrollmentPlace);

				approvalUtil.primaryApprove(DBConstants.TBL_FARMER_ENROLLMENT_PLACE, enrollmentPlace.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-EnrollmentPlace" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-EnrollmentPlace" + APIConstants.RESPONSE_PRIMARY_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage finalApproveById(int id) {
		try {
			Optional<FarmerEnrollmentPlace> foundEnrollmentPlace = farmerEnrollmentPlaceRepository.findById(id);

			if (foundEnrollmentPlace.isPresent()) {

				FarmerEnrollmentPlace enrollmentPlace = foundEnrollmentPlace.get();
				enrollmentPlace.setStatus(APIConstants.STATUS_ACTIVE);

				enrollmentPlace = farmerEnrollmentPlaceRepository.save(enrollmentPlace);

				approvalUtil.finalApprove(DBConstants.TBL_FARMER_ENROLLMENT_PLACE, enrollmentPlace.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-EnrollmentPlace" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-EnrollmentPlace" + APIConstants.RESPONSE_PRIMARY_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproveById

	public ResponseMessage deleteFarmerEnrollmentPlaceById(int id) {
		try {
			Optional<FarmerEnrollmentPlace> foundEnrollmentPlace = farmerEnrollmentPlaceRepository.findById(id);

			if (foundEnrollmentPlace.isPresent()) {

				FarmerEnrollmentPlace enrollmentPlace = foundEnrollmentPlace.get();
				enrollmentPlace.setStatus(APIConstants.STATUS_DELETED);

				enrollmentPlace = farmerEnrollmentPlaceRepository.save(enrollmentPlace);

				approvalUtil.delete(DBConstants.TBL_FARMER_ENROLLMENT_PLACE, enrollmentPlace.getId());

				return responseMessageUtil.sendResponse(true,
						"Farmer-EnrollmentPlace" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-EnrollmentPlace" + APIConstants.RESPONSE_DELETE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// deleteFarmerEnrollmentPlaceById

	public FarmerEnrollmentPlace findFarmerEnrollmentPlaceById(int id) {
		try {
			Optional<FarmerEnrollmentPlace> foundEnrollmentPlace = farmerEnrollmentPlaceRepository.findById(id);

			if (foundEnrollmentPlace.isPresent()) {
				return foundEnrollmentPlace.get();
			} else {
				throw new DoesNotExistException("Farmer-EnrollmentPlace" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findFarmerEnrollmentPlaceById
	
    public ResponseMessage rejectById(int id) {
 try {
     Optional<FarmerEnrollmentPlace> foundFarmerEnrollmentPlace = farmerEnrollmentPlaceRepository.findById(id);

     if (foundFarmerEnrollmentPlace.isPresent()) {

   FarmerEnrollmentPlace farmerEnrollmentPlace = foundFarmerEnrollmentPlace.get();
   farmerEnrollmentPlace.setStatus(APIConstants.STATUS_REJECTED);
   farmerEnrollmentPlace = farmerEnrollmentPlaceRepository.save(farmerEnrollmentPlace);

   approvalUtil.finalApprove(DBConstants.TBL_FARMER_ENROLLMENT_PLACE, farmerEnrollmentPlace.getId());

   return responseMessageUtil.sendResponse(true, "Farmer-EnrollmentPlace" + APIConstants.RESPONSE_REJECT_SUCCESS, "");
     } else {
   return responseMessageUtil.sendResponse(false, "", "Farmer-EnrollmentPlace" + APIConstants.RESPONSE_REJECT_ERROR);
     }
 } catch (Exception e) {
     return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
 }
   }//rejectById 

}// addAllFarmerEnrollmentPlace
