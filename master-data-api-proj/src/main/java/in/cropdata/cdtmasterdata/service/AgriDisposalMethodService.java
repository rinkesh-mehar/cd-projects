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
import in.cropdata.cdtmasterdata.model.AgriDisposalMethod;
import in.cropdata.cdtmasterdata.repository.AgriDisposalMethodRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class AgriDisposalMethodService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AgriDisposalMethodService.class);
	private static final String SERVER_ERROR_MSG = "Server Error : ";

	@Autowired
	private AgriDisposalMethodRepository agriDisposalMethodRepository;

	@Autowired
	private AclHistoryUtil approvalUtil;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	public List<AgriDisposalMethod> getAllAgriDisposalMethod() {
		try {
			List<AgriDisposalMethod> list = agriDisposalMethodRepository.findAll();

			return list;

		} catch (Exception e) {
			throw e;
		}
	}// getAllAgriDisposalMethod
	
	public Page<AgriDisposalMethod> getDisposalMethodListByPagenation(Integer page, Integer size, String searchText){
		try {
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());
			return agriDisposalMethodRepository.getDisposalMethodListByPagenation(sortedByIdDesc, searchText);
		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Agri-DisposalMethod Data Found For Searched Text -> " + searchText);
		}
	}

	public ResponseMessage addAgriDisposalMethod(AgriDisposalMethod agriDisposalMethod) {
		try {
			agriDisposalMethod = agriDisposalMethodRepository.save(agriDisposalMethod);

			approvalUtil.addRecord(DBConstants.TBL_AGRI_DISPOSAL_METHOD, agriDisposalMethod.getId());

			return responseMessageUtil.sendResponse(true, "Agri-DisposalMethod" + APIConstants.RESPONSE_ADD_SUCCESS,
					"");

		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// addAgriDisposalMethod

	public ResponseMessage updateAgriDisposalMethodById(int id, AgriDisposalMethod agriDisposalMethod) {
		try {
			Optional<AgriDisposalMethod> foundVareity = agriDisposalMethodRepository.findById(id);
			if (foundVareity.isPresent()) {

				agriDisposalMethod.setId(id);
				agriDisposalMethodRepository.save(agriDisposalMethod);

				approvalUtil.updateRecord(DBConstants.TBL_AGRI_DISPOSAL_METHOD, agriDisposalMethod.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-DisposalMethod" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");

			} else {

				return responseMessageUtil.sendResponse(false, "",
						"Agri-DisposalMethod" + APIConstants.RESPONSE_UPDATE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// updateAgriDisposalMethodById

	public ResponseMessage primaryApproveById(int id) {
		try {
			Optional<AgriDisposalMethod> foundDisposalMethod = agriDisposalMethodRepository.findById(id);
			if (foundDisposalMethod.isPresent()) {

				AgriDisposalMethod disposalMethod = foundDisposalMethod.get();
				disposalMethod.setStatus(APIConstants.STATUS_APPROVED);

				disposalMethod = agriDisposalMethodRepository.save(disposalMethod);

				approvalUtil.primaryApprove(DBConstants.TBL_AGRI_DISPOSAL_METHOD, disposalMethod.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-DisposalMethod" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");

			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-DisposalMethod" + APIConstants.RESPONSE_PRIMARY_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage finalApproveById(int id) {
		try {
			Optional<AgriDisposalMethod> foundDisposalMethod = agriDisposalMethodRepository.findById(id);
			if (foundDisposalMethod.isPresent()) {

				AgriDisposalMethod disposalMethod = foundDisposalMethod.get();
				disposalMethod.setStatus(APIConstants.STATUS_ACTIVE);

				disposalMethod = agriDisposalMethodRepository.save(disposalMethod);

				approvalUtil.finalApprove(DBConstants.TBL_AGRI_DISPOSAL_METHOD, disposalMethod.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-DisposalMethod" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");

			} else {

				return responseMessageUtil.sendResponse(false, "",
						"Agri-DisposalMethod" + APIConstants.RESPONSE_FINAL_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproveById

	public ResponseMessage deleteAgriDisposalMethodById(int id) {
		try {
			Optional<AgriDisposalMethod> foundDisposalMethod = agriDisposalMethodRepository.findById(id);
			if (foundDisposalMethod.isPresent()) {

				AgriDisposalMethod disposalMethod = foundDisposalMethod.get();

				disposalMethod.setStatus(APIConstants.STATUS_DELETED);

				disposalMethod = agriDisposalMethodRepository.save(disposalMethod);

				return responseMessageUtil.sendResponse(true,
						"Agri-DisposalMethod" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");

			} else {

				return responseMessageUtil.sendResponse(false, "",
						"Agri-DisposalMethod" + APIConstants.RESPONSE_DELETE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}// deleteAgriDisposalMethodById

	public ResponseMessage rejectById(int id) {
		try {
			Optional<AgriDisposalMethod> foundDisposalMethod = agriDisposalMethodRepository.findById(id);
			if (foundDisposalMethod.isPresent()) {

				AgriDisposalMethod disposalMethod = foundDisposalMethod.get();

				disposalMethod.setStatus(APIConstants.STATUS_REJECTED);

				disposalMethod = agriDisposalMethodRepository.save(disposalMethod);

				return responseMessageUtil.sendResponse(true,
						"Agri-DisposalMethod" + APIConstants.RESPONSE_REJECT_SUCCESS + id, "");

			} else {

				return responseMessageUtil.sendResponse(false, "",
						"Agri-DisposalMethod" + APIConstants.RESPONSE_REJECT_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}// rejectById

	public AgriDisposalMethod findAgriDisposalMethodById(int id) {
		try {
			Optional<AgriDisposalMethod> foundDisposalMethod = agriDisposalMethodRepository.findById(id);
			if (foundDisposalMethod.isPresent()) {
				return foundDisposalMethod.get();
			} else {
				throw new DoesNotExistException("Agri-DisposalMethod" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findAgriDisposalMethodById
}// AgriDisposalMethodService
