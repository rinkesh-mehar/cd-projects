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
import in.cropdata.cdtmasterdata.model.AgriIrrigationMethod;
import in.cropdata.cdtmasterdata.repository.AgriIrrigationMethodRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class AgriIrrigationMethodService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AgriDisposalMethodService.class);
	private static final String SERVER_ERROR_MSG = "Server Error : ";

	@Autowired
	private AgriIrrigationMethodRepository agriIrrigationMethodRepository;

	@Autowired
	private AclHistoryUtil approvalUtil;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	public List<AgriIrrigationMethod> getAllAgriIrrigationMethod() {
		try {
			List<AgriIrrigationMethod> list = agriIrrigationMethodRepository.findAll();

			return list;
		} catch (Exception e) {
			throw e;
		}
	}// getAllAgriIrrigationMethod
	

	public Page<AgriIrrigationMethod> getIrrigationMethodListByPagenation(Integer page, Integer size, String searchText){
		try {
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());
			return agriIrrigationMethodRepository.getIrrigationMethodListByPagenation(sortedByIdDesc, searchText);
		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Agri-IrrigationMethod Data Found For Searched Text -> " + searchText);
		}
	}

	public ResponseMessage addAgriIrrigationMethod(AgriIrrigationMethod agriIrrigationMethod)
	{

		try
		{
			if (agriIrrigationMethodRepository.findByName(agriIrrigationMethod.getName()).isEmpty())
			{
				agriIrrigationMethod = agriIrrigationMethodRepository.save(agriIrrigationMethod);

				approvalUtil.addRecord(DBConstants.TBL_AGRI_IRRIGATION_METHOD, agriIrrigationMethod.getId());

				return responseMessageUtil.sendResponse(true, "Agri-IrrigationMethod" +
						APIConstants.RESPONSE_ADD_SUCCESS, "");
			} else
			{
				return responseMessageUtil.sendResponse(false, "", "Agri-IrrigationMethod" +
						APIConstants.RESPONSE_ALREADY_EXIST);
			}
		} catch (Exception e)
		{
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// addAgriIrrigationMethod

	public ResponseMessage updateAgriIrrigationMethodById(int id, AgriIrrigationMethod agriIrrigationMethod)
	{
		try
		{
			Optional<AgriIrrigationMethod> foundVareity = agriIrrigationMethodRepository.findById(id);
			if (foundVareity.isPresent())
			{
				if ((agriIrrigationMethodRepository.findByName(agriIrrigationMethod.getName())).isEmpty())
				{
					agriIrrigationMethod.setId(id);
					agriIrrigationMethod = agriIrrigationMethodRepository.save(agriIrrigationMethod);

					approvalUtil.updateRecord(DBConstants.TBL_AGRI_IRRIGATION_METHOD, agriIrrigationMethod.getId());

					return responseMessageUtil.sendResponse(true,
							"Agri-IrrigationMethod" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
				} else
				{
					return responseMessageUtil.sendResponse(false, "",
							"Agri-IrrigationMethod" + APIConstants.RESPONSE_ALREADY_EXIST);
				}
			} else
			{
				return responseMessageUtil.sendResponse(false, "",
						"Agri-IrrigationMethod" + APIConstants.RESPONSE_UPDATE_ERROR + id);
			}
		} catch (Exception e)
		{
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// updateAgriIrrigationMethodById

	public ResponseMessage primaryApproveById(int id) {
		try {
			Optional<AgriIrrigationMethod> foundIrrigationMethod = agriIrrigationMethodRepository.findById(id);

			if (foundIrrigationMethod.isPresent()) {

				AgriIrrigationMethod agriIrrigationMethod = foundIrrigationMethod.get();
				agriIrrigationMethod.setStatus(APIConstants.STATUS_APPROVED);
				agriIrrigationMethod = agriIrrigationMethodRepository.save(agriIrrigationMethod);

				approvalUtil.primaryApprove(DBConstants.TBL_AGRI_IRRIGATION_METHOD, agriIrrigationMethod.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-IrrigationMethod" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-IrrigationMethod" + APIConstants.RESPONSE_PRIMARY_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage finalApproveById(int id) {
		try {
			Optional<AgriIrrigationMethod> foundIrrigationMethod = agriIrrigationMethodRepository.findById(id);

			if (foundIrrigationMethod.isPresent()) {

				AgriIrrigationMethod agriIrrigationMethod = foundIrrigationMethod.get();
				agriIrrigationMethod.setStatus(APIConstants.STATUS_ACTIVE);
				agriIrrigationMethod = agriIrrigationMethodRepository.save(agriIrrigationMethod);

				approvalUtil.delete(DBConstants.TBL_AGRI_IRRIGATION_METHOD, agriIrrigationMethod.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-IrrigationMethod" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-IrrigationMethod" + APIConstants.RESPONSE_FINAL_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproveById

	public ResponseMessage deleteAgriIrrigationMethodById(int id) {
		try {
			Optional<AgriIrrigationMethod> foundIrrigationMethod = agriIrrigationMethodRepository.findById(id);
			if (foundIrrigationMethod.isPresent()) {

				AgriIrrigationMethod agriIrrigationMethod = foundIrrigationMethod.get();
				agriIrrigationMethod.setStatus(APIConstants.STATUS_DELETED);
				agriIrrigationMethod = agriIrrigationMethodRepository.save(agriIrrigationMethod);

				approvalUtil.primaryApprove(DBConstants.TBL_AGRI_IRRIGATION_METHOD, agriIrrigationMethod.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-IrrigationMethod" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-IrrigationMethod" + APIConstants.RESPONSE_DELETE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}

	public ResponseMessage rejectById(int id) {
		try {
			Optional<AgriIrrigationMethod> foundIrrigationMethod = agriIrrigationMethodRepository.findById(id);
			if (foundIrrigationMethod.isPresent()) {

				AgriIrrigationMethod agriIrrigationMethod = foundIrrigationMethod.get();
				agriIrrigationMethod.setStatus(APIConstants.STATUS_REJECTED);
				agriIrrigationMethod = agriIrrigationMethodRepository.save(agriIrrigationMethod);

				approvalUtil.primaryApprove(DBConstants.TBL_AGRI_IRRIGATION_METHOD, agriIrrigationMethod.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-IrrigationMethod" + APIConstants.RESPONSE_REJECT_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-IrrigationMethod" + APIConstants.RESPONSE_REJECT_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}

	public AgriIrrigationMethod findAgriIrrigationMethodById(int id) {
		try {
			Optional<AgriIrrigationMethod> foundIrrigationMethod = agriIrrigationMethodRepository.findById(id);

			if (foundIrrigationMethod.isPresent()) {
				return foundIrrigationMethod.get();
			} else {

				throw new DoesNotExistException("Agri-IrrigationMethod" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findAgriIrrigationMethodById

}// AgriIrrigationMethodService
