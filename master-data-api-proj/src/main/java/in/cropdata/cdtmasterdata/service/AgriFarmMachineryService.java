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
import in.cropdata.cdtmasterdata.model.AgriFarmMachinery;
import in.cropdata.cdtmasterdata.repository.AgriFarmMachineryRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class AgriFarmMachineryService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AgriDisposalMethodService.class);
	private static final String SERVER_ERROR_MSG = "Server Error : ";

	@Autowired
	private AgriFarmMachineryRepository agriFarmMachineryRepository;

	@Autowired
	private AclHistoryUtil approveUtil;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	public List<AgriFarmMachinery> getAllAgriFarmMachinery() {
		try {
			List<AgriFarmMachinery> list = agriFarmMachineryRepository.findAll();

			return list;
		} catch (Exception e) {
			throw e;
		}
	}// getAllAgriFarmMachinery
	
	public Page<AgriFarmMachinery> getFarmMachineryListByPagenation(Integer page, Integer size, String searchText){
		try {
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());
			return agriFarmMachineryRepository.getFarmMachineryListByPagenation(sortedByIdDesc, searchText);
		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Agri-FarmMachinery Data Found For Searched Text -> " + searchText);
		}
	}

	public ResponseMessage addAgriFarmMachinery(AgriFarmMachinery agriFarmMachinery) {

		try {
			agriFarmMachinery = agriFarmMachineryRepository.save(agriFarmMachinery);

			approveUtil.addRecord(DBConstants.TBL_AGRI_FARM_MACHINERY, agriFarmMachinery.getId());

			return responseMessageUtil.sendResponse(true, "Agri-FarmMachinery" + APIConstants.RESPONSE_ADD_SUCCESS, "");

		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// addAgriFarmMachinery

	public ResponseMessage updateAgriFarmMachineryById(int id, AgriFarmMachinery agriFarmMachinery) {
		try {
			Optional<AgriFarmMachinery> foundfarmMaschinary = agriFarmMachineryRepository.findById(id);
			if (foundfarmMaschinary.isPresent()) {

				agriFarmMachinery.setId(id);
				agriFarmMachineryRepository.save(agriFarmMachinery);

				approveUtil.updateRecord(DBConstants.TBL_AGRI_FARM_MACHINERY, agriFarmMachinery.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-FarmMachinery" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");

			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-FarmMachinery" + APIConstants.RESPONSE_UPDATE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// updateAgriFarmMachineryById

	public ResponseMessage primaryApproveById(int id) {
		try {
			Optional<AgriFarmMachinery> foundFarmMachin = agriFarmMachineryRepository.findById(id);
			if (foundFarmMachin.isPresent()) {

				AgriFarmMachinery farmMachinary = foundFarmMachin.get();
				farmMachinary.setStatus(APIConstants.STATUS_APPROVED);

				farmMachinary = agriFarmMachineryRepository.save(farmMachinary);

				approveUtil.primaryApprove(DBConstants.TBL_AGRI_FARM_MACHINERY, farmMachinary.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-FarmMachinery" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");

			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-FarmMachinery" + APIConstants.RESPONSE_PRIMARY_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage finalApproveById(int id) {
		try {
			Optional<AgriFarmMachinery> foundFarmMachin = agriFarmMachineryRepository.findById(id);

			if (foundFarmMachin.isPresent()) {

				AgriFarmMachinery farmMachin = foundFarmMachin.get();
				farmMachin.setStatus(APIConstants.STATUS_ACTIVE);

				farmMachin = agriFarmMachineryRepository.save(farmMachin);

				return responseMessageUtil.sendResponse(true,
						"Agri-FarmMachinery" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
			} else {

				return responseMessageUtil.sendResponse(false, "",
						"Agri-FarmMachinery" + APIConstants.RESPONSE_FINAL_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproveById

	public ResponseMessage deleteAgriFarmMachineryById(int id) {
		try {
			Optional<AgriFarmMachinery> foundFarmMachin = agriFarmMachineryRepository.findById(id);
			if (foundFarmMachin.isPresent()) {

				AgriFarmMachinery farmMachin = foundFarmMachin.get();
				farmMachin.setStatus(APIConstants.STATUS_DELETED);
				farmMachin = agriFarmMachineryRepository.save(farmMachin);

				approveUtil.delete(DBConstants.TBL_AGRI_FARM_MACHINERY, farmMachin.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-FarmMachinery" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");

			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-FarmMachinery" + APIConstants.RESPONSE_DELETE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}// deleteAgriFarmMachineryById

	public ResponseMessage rejectById(int id) {
		try {
			Optional<AgriFarmMachinery> foundFarmMachin = agriFarmMachineryRepository.findById(id);
			if (foundFarmMachin.isPresent()) {

				AgriFarmMachinery farmMachin = foundFarmMachin.get();
				farmMachin.setStatus(APIConstants.STATUS_REJECTED);
				farmMachin = agriFarmMachineryRepository.save(farmMachin);

				approveUtil.delete(DBConstants.TBL_AGRI_FARM_MACHINERY, farmMachin.getId());

				return responseMessageUtil.sendResponse(true,
						"Agri-FarmMachinery" + APIConstants.RESPONSE_REJECT_SUCCESS + id, "");

			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Agri-FarmMachinery" + APIConstants.RESPONSE_REJECT_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}// rejectById

	public AgriFarmMachinery findAgriFarmMachineryById(int id) {
		try {
			Optional<AgriFarmMachinery> foundFarmMachinery = agriFarmMachineryRepository.findById(id);
			if (foundFarmMachinery.isPresent()) {
				return foundFarmMachinery.get();

			} else {
				throw new DoesNotExistException("Agri-FarmMachinery" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findAgriFarmMachineryById
}// AgriFarmMachineryService
