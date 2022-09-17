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
import in.cropdata.cdtmasterdata.model.GeneralTypeOfService;
import in.cropdata.cdtmasterdata.repository.GeneralTypeOfServiceRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class GeneralTypeOfServiceService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AgriDisposalMethodService.class);
	private static final String SERVER_ERROR_MSG = "Server Error : ";
	
	@Autowired
	private GeneralTypeOfServiceRepository generalTypeOfServiceRepository;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	@Autowired
	private AclHistoryUtil approvalUtil;

	@GetMapping("/all-general-type-of-service")
	public List<GeneralTypeOfService> getAllGeneralTypeOfService() {
		try {
			List<GeneralTypeOfService> list = generalTypeOfServiceRepository.findAll();

			return list;
		} catch (Exception e) {
			throw e;
		}
	}// getAllgeneralTypeOfServices
	
	public Page<GeneralTypeOfService> getGeneralTypeOfServiceListByPagenation(Integer page, Integer size, String searchText){
		try {
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());
			return generalTypeOfServiceRepository.getGeneralTypeOfServiceListByPagenation(sortedByIdDesc, searchText);
		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No General-TypeOfService Data Found For Searched Text -> " + searchText);
		}
	}

	public ResponseMessage addGeneralTypeOfService(GeneralTypeOfService generalTypeOfService)
	{
		try
		{
			final String typeOfService = generalTypeOfService.getTypeOfService().strip();

			if (generalTypeOfServiceRepository.findByTypeOfService(typeOfService).isEmpty())
			{
				generalTypeOfService.setTypeOfService(typeOfService);
				generalTypeOfService = generalTypeOfServiceRepository.save(generalTypeOfService);

				approvalUtil.addRecord(DBConstants.TBL_GENERAL_TYPE_OF_SERVICE, generalTypeOfService.getId());

				return responseMessageUtil.sendResponse(true, "general-TypeOfService" + APIConstants.RESPONSE_ADD_SUCCESS,
						"");
			} else
			{
				return responseMessageUtil.sendResponse(false, "",
						"general-TypeOfService".concat(APIConstants.RESPONSE_ALREADY_EXIST).concat(typeOfService));
			}
		} catch (Exception e)
		{
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// addAllgeneralTypeOfService

	public ResponseMessage updateGeneralTypeOfServiceById(int id, GeneralTypeOfService generalTypeOfService) {
		try {
			Optional<GeneralTypeOfService> foundTypeOfService = generalTypeOfServiceRepository.findById(id);

			if (foundTypeOfService.isPresent()) {

				generalTypeOfService.setId(id);
				generalTypeOfService = generalTypeOfServiceRepository.save(generalTypeOfService);

				approvalUtil.updateRecord(DBConstants.TBL_GENERAL_TYPE_OF_SERVICE, generalTypeOfService.getId());

				return responseMessageUtil.sendResponse(true,
						"general-TypeOfService" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"general-TypeOfService" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}// updategeneralTypeOfServiceById

	public ResponseMessage finalApproveById(int id) {
		try {
			Optional<GeneralTypeOfService> foundTypeOfService = generalTypeOfServiceRepository.findById(id);

			if (foundTypeOfService.isPresent()) {

				GeneralTypeOfService TypeOfService = foundTypeOfService.get();
				TypeOfService.setStatus(APIConstants.STATUS_ACTIVE);
				TypeOfService = generalTypeOfServiceRepository.save(TypeOfService);

				approvalUtil.finalApprove(DBConstants.TBL_GENERAL_TYPE_OF_SERVICE, TypeOfService.getId());

				return responseMessageUtil.sendResponse(true,
						"general-TypeOfService" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"general-TypeOfService" + APIConstants.RESPONSE_NO_RECORD_FOUND);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproveById

	public ResponseMessage primaryApproveById(int id) {
		try {
			Optional<GeneralTypeOfService> foundGeneralTypeOfService = generalTypeOfServiceRepository.findById(id);

			if (foundGeneralTypeOfService.isPresent()) {

				GeneralTypeOfService TypeOfService = foundGeneralTypeOfService.get();
				TypeOfService.setStatus(APIConstants.STATUS_APPROVED);
				TypeOfService = generalTypeOfServiceRepository.save(TypeOfService);

				approvalUtil.primaryApprove(DBConstants.TBL_GENERAL_TYPE_OF_SERVICE, TypeOfService.getId());

				return responseMessageUtil.sendResponse(true,
						"General-TYPE_OF_SERVICE" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Farmer-IncomeSource" + APIConstants.RESPONSE_NO_RECORD_FOUND);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage deleteGeneralTypeOfServiceById(int id) {
		try {
			Optional<GeneralTypeOfService> foundTypeOfService = generalTypeOfServiceRepository.findById(id);

			if (foundTypeOfService.isPresent()) {

				GeneralTypeOfService TypeOfService = foundTypeOfService.get();
				TypeOfService.setStatus(APIConstants.STATUS_DELETED);
				TypeOfService = generalTypeOfServiceRepository.save(TypeOfService);

				approvalUtil.delete(DBConstants.TBL_GENERAL_TYPE_OF_SERVICE, TypeOfService.getId());

				return responseMessageUtil.sendResponse(true,
						"general-TypeOfService" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"general-TypeOfService" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// deletegeneralTypeOfServiceById

	public ResponseMessage rejectById(int id) {
		try {
			Optional<GeneralTypeOfService> foundTypeOfService = generalTypeOfServiceRepository.findById(id);

			if (foundTypeOfService.isPresent()) {

				GeneralTypeOfService TypeOfService = foundTypeOfService.get();
				TypeOfService.setStatus(APIConstants.STATUS_REJECTED);
				TypeOfService = generalTypeOfServiceRepository.save(TypeOfService);

				approvalUtil.delete(DBConstants.TBL_GENERAL_TYPE_OF_SERVICE, TypeOfService.getId());

				return responseMessageUtil.sendResponse(true,
						"general-TypeOfService" + APIConstants.RESPONSE_REJECT_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"general-TypeOfService" + APIConstants.RESPONSE_REJECT_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// rejectById

	public GeneralTypeOfService findGeneralTypeOfServiceById(int id) {
		try {
			Optional<GeneralTypeOfService> foundTypeOfService = generalTypeOfServiceRepository.findById(id);
			if (foundTypeOfService.isPresent()) {

				return foundTypeOfService.get();
			} else {
				throw new DoesNotExistException("general-TypeOfService" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findgeneralTypeOfServiceById

}
