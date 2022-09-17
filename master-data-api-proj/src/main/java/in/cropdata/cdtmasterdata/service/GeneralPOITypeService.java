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
import org.springframework.web.bind.annotation.GetMapping;

import in.cropdata.cdtmasterdata.constants.APIConstants;
import in.cropdata.cdtmasterdata.constants.DBConstants;
import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.model.GeneralPOIType;
import in.cropdata.cdtmasterdata.repository.GeneralPOITypeRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class GeneralPOITypeService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GeneralPOITypeService.class);
	private static final String SERVER_ERROR_MSG = "Server Error : ";
	
	@Autowired
	private GeneralPOITypeRepository generalPOITypeRepository;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	@Autowired
	private AclHistoryUtil approvalUtil;

	@GetMapping("/all-general-poi_type")
	public List<GeneralPOIType> getAllGeneralPOIType() {
		try {
			List<GeneralPOIType> list = generalPOITypeRepository.findAll();

			return list;

		} catch (Exception e) {
			throw e;
		}
	}// getAllgeneralPOITypes

	public Page<GeneralPOIType> getGeneralPOITypeListByPagenation(Integer page, Integer size, String searchText){
		try {
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());
			return generalPOITypeRepository.getGeneralPOITypeListByPagenation(sortedByIdDesc, searchText);
		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No General-POIType Data Found For Searched Text -> " + searchText);
		}
	}


    public ResponseMessage addGeneralPOIType(GeneralPOIType generalPOIType)
    {
        try
        {
            final String POIName = generalPOIType.getName().strip();
            if (generalPOITypeRepository.findByName(POIName).isEmpty())
            {
                generalPOIType.setName(POIName);
                generalPOIType = generalPOITypeRepository.save(generalPOIType);

                approvalUtil.addRecord(DBConstants.TBL_GENERAL_POI_TYPE, generalPOIType.getId());

                return responseMessageUtil.sendResponse(true, "General-POIType" + APIConstants.RESPONSE_ADD_SUCCESS, "");
            } else
            {
                return responseMessageUtil.sendResponse(false, "",
                        "general-POIType".concat(APIConstants.RESPONSE_ALREADY_EXIST).concat(POIName));
            }
        } catch (Exception e)
        {
            return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
        }
    }// addAllgeneralPOIType

	public ResponseMessage updateGeneralPOITypeById(int id, GeneralPOIType generalPOIType) {
		try {
			Optional<GeneralPOIType> foundPOIType = generalPOITypeRepository.findById(id);

			if (foundPOIType.isPresent()) {

				generalPOIType.setId(id);
				generalPOIType = generalPOITypeRepository.save(generalPOIType);

				approvalUtil.updateRecord(DBConstants.TBL_GENERAL_POI_TYPE, generalPOIType.getId());

				return responseMessageUtil.sendResponse(true,
						"general-POIType" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"general-POIType" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}// updategeneralPOITypeById

	public ResponseMessage finalApproveById(int id) {
		try {
			Optional<GeneralPOIType> foundPOIType = generalPOITypeRepository.findById(id);

			if (foundPOIType.isPresent()) {

				GeneralPOIType poiType = foundPOIType.get();
				poiType.setStatus(APIConstants.STATUS_ACTIVE);
				poiType = generalPOITypeRepository.save(poiType);

				approvalUtil.finalApprove(DBConstants.TBL_GENERAL_POI_TYPE, poiType.getId());

				return responseMessageUtil.sendResponse(true,
						"general-POIType" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"general-POIType" + APIConstants.RESPONSE_NO_RECORD_FOUND);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproveById

	public ResponseMessage primaryApproveById(int id) {
		try {
			Optional<GeneralPOIType> foundGeneralPOIType = generalPOITypeRepository.findById(id);

			if (foundGeneralPOIType.isPresent()) {

				GeneralPOIType poiType = foundGeneralPOIType.get();
				poiType.setStatus(APIConstants.STATUS_APPROVED);
				poiType = generalPOITypeRepository.save(poiType);

				approvalUtil.primaryApprove(DBConstants.TBL_GENERAL_POI_TYPE, poiType.getId());

				return responseMessageUtil.sendResponse(true,
						"General-POI" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-IncomeSource" + APIConstants.RESPONSE_NO_RECORD_FOUND);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage deleteGeneralPOITypeById(int id) {
		try {
			Optional<GeneralPOIType> foundPOIType = generalPOITypeRepository.findById(id);

			if (foundPOIType.isPresent()) {

				GeneralPOIType poiType = foundPOIType.get();
				poiType.setStatus(APIConstants.STATUS_DELETED);
				poiType = generalPOITypeRepository.save(poiType);

				approvalUtil.delete(DBConstants.TBL_GENERAL_POI_TYPE, poiType.getId());

				return responseMessageUtil.sendResponse(true,
						"general-POIType" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"general-POIType" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// deletegeneralPOITypeById

	public ResponseMessage rejectById(int id) {
		try {
			Optional<GeneralPOIType> foundPOIType = generalPOITypeRepository.findById(id);

			if (foundPOIType.isPresent()) {

				GeneralPOIType poiType = foundPOIType.get();
				poiType.setStatus(APIConstants.STATUS_REJECTED);
				poiType = generalPOITypeRepository.save(poiType);

				approvalUtil.delete(DBConstants.TBL_GENERAL_POI_TYPE, poiType.getId());

				return responseMessageUtil.sendResponse(true,
						"general-POIType" + APIConstants.RESPONSE_REJECT_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"general-POIType" + APIConstants.RESPONSE_REJECT_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// rejectById

	public GeneralPOIType findGeneralPOITypeById(int id) {
		try {
			Optional<GeneralPOIType> foundPOIType = generalPOITypeRepository.findById(id);
			if (foundPOIType.isPresent()) {

				return foundPOIType.get();
			} else {
				throw new DoesNotExistException("general-POIType" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findgeneralPOITypeById

}
