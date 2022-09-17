package in.cropdata.cdtmasterdata.service;

import java.sql.Timestamp;
import java.time.Instant;
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
import in.cropdata.cdtmasterdata.dto.RegionalConnectivityDTO;
import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.model.RegionalConnectivity;
import in.cropdata.cdtmasterdata.repository.RegionalConnectivityRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class RegionalConnectivityService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RegionalConnectivityService.class);
	private static final String SERVER_ERROR_MSG = "Server Error : ";

	@Autowired
	private RegionalConnectivityRepository regionalConnectivityRepository;
	
	@Autowired
	private AclHistoryUtil approvalUtil;
	
	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	public List<RegionalConnectivityDTO> getRegionalConnectivityList() {

		return regionalConnectivityRepository.getListOfRegionalConnectivity();
	}
	
	public Page<RegionalConnectivityDTO> getRegionalConnectivityListByPagenation(Integer page, Integer size, String searchText){
		try {
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());
			return regionalConnectivityRepository.getRegionalConnectivityListByPagenation(sortedByIdDesc, searchText);
		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Department Data Found For Searched Text -> " + searchText);
		}
	}

	public ResponseMessage saveRegionalConnectivityData(RegionalConnectivity regionalConnectivity) {

		ResponseMessage message = new ResponseMessage();
		try {
			if (regionalConnectivity != null) {
				regionalConnectivityRepository.save(regionalConnectivity);
				message.setSuccess(true);
				message.setMessage("Data saved successfully!!");
			}
		} catch (Exception e) {
			message.setSuccess(false);
			message.setError(e.getMessage());
			e.printStackTrace();
		}

		return message;
	}

	public RegionalConnectivity getRegionalConnectById(int id) {

		try {
			Optional<RegionalConnectivity> regionalConnect = regionalConnectivityRepository.findById(id);
			if (regionalConnect.isPresent()) {
				return regionalConnect.get();

			} else {
				throw new DoesNotExistException("Regional-Connectivity" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}

	}
	
	public ResponseMessage updateRegionalConnect(int id, RegionalConnectivity regionalConnect) {
		
		ResponseMessage message = new ResponseMessage();
		try {
			regionalConnect.setId(id);
			regionalConnect.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
			regionalConnectivityRepository.save(regionalConnect);
			message.setSuccess(true);
			message.setMessage("Record Updated Successfully !!");
		 }catch(Exception e) {
			  message.setSuccess(false);
			  message.setError(e.getMessage());
			 e.printStackTrace();
		 }
		
		return message;
	}

	public ResponseMessage primaryApproveById(int id) {
		try {
			Optional<RegionalConnectivity> foundRegion = regionalConnectivityRepository.findById(id);

			if (foundRegion.isPresent()) {

				RegionalConnectivity regionalConnect = foundRegion.get();
				regionalConnect.setStatus(APIConstants.STATUS_APPROVED);

				regionalConnect = regionalConnectivityRepository.save(regionalConnect);

				approvalUtil.primaryApprove(DBConstants.TBL_REGIONAL_SEASON, regionalConnect.getId());

				return responseMessageUtil.sendResponse(true,
						"Regional-Connectivity" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Regional-Connectivity" + APIConstants.RESPONSE_NO_RECORD_FOUND);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
		
	}
	
	public ResponseMessage finalApproveById(int id) {
		try {
			Optional<RegionalConnectivity> foundRegion = regionalConnectivityRepository.findById(id);

			if (foundRegion.isPresent()) {

				RegionalConnectivity regionalConnectivity = foundRegion.get();
				regionalConnectivity.setStatus(APIConstants.STATUS_ACTIVE);

				regionalConnectivity = regionalConnectivityRepository.save(regionalConnectivity);

				approvalUtil.finalApprove(DBConstants.TBL_REGIONAL_SEASON, regionalConnectivity.getId());

				return responseMessageUtil.sendResponse(true,
						"Regional-Connectivity" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Regional-Connectivity" + APIConstants.RESPONSE_NO_RECORD_FOUND);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproveById
	
	public ResponseMessage rejectById(int id) {
		try {
			Optional<RegionalConnectivity> foundRegion = regionalConnectivityRepository.findById(id);

			if (foundRegion.isPresent()) {

				RegionalConnectivity regionalConnectivity = foundRegion.get();
				regionalConnectivity.setStatus(APIConstants.STATUS_REJECTED);
				regionalConnectivity = regionalConnectivityRepository.save(regionalConnectivity);

				approvalUtil.finalApprove(DBConstants.TBL_REGIONAL_SEASON, regionalConnectivity.getId());

				return responseMessageUtil.sendResponse(true, "Regional-Connectivity " + APIConstants.RESPONSE_REJECT_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "", "Regional-Connectivity" + APIConstants.RESPONSE_REJECT_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// rejectById
	
	
	public ResponseMessage deleteRegionalConnectivityById(int id) {
		try {
			Optional<RegionalConnectivity> foundRegion = regionalConnectivityRepository.findById(id);

			if (foundRegion.isPresent()) {

				RegionalConnectivity regionConnect = foundRegion.get();
				regionConnect.setStatus(APIConstants.STATUS_DELETED);

				regionConnect = regionalConnectivityRepository.save(regionConnect);

				approvalUtil.delete(DBConstants.TBL_REGIONAL_SEASON, regionConnect.getId());

				return responseMessageUtil.sendResponse(true,
						"Regional-Connectivity" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Regional-Connectivity" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// deleteRegionSeasonById

}
