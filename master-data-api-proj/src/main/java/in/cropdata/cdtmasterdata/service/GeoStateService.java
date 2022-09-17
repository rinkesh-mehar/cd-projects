package in.cropdata.cdtmasterdata.service;

import in.cropdata.cdtmasterdata.constants.APIConstants;
import in.cropdata.cdtmasterdata.constants.DBConstants;
import in.cropdata.cdtmasterdata.dto.GeoStateAliasDto;
import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.dto.interfaces.GeoStateInfDto;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.model.GeoState;
import in.cropdata.cdtmasterdata.repository.GeoStateRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GeoStateService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GeoStateService.class);
	private static final String SERVER_ERROR_MSG = "Server Error : ";

	@Autowired
	private GeoStateRepository geoStateRepository;

	@Autowired
	private AclHistoryUtil approvalUtil;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	/**
	 * @return return all state's
	 */
	public List<GeoStateInfDto> getAllGeoState() {
		try {

			List<GeoStateInfDto> list = geoStateRepository.getGeoStateList();
			return list;
		} catch (Exception e) {
			throw e;
		}
	}
	
	public Page<GeoStateInfDto> getStateListByPagenation(Integer page, Integer size, String searchText){
		try {
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());
			return geoStateRepository.getStateListByPagenation(sortedByIdDesc, searchText);
		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Geo-State Data Found For Searched Text -> " + searchText);
		}
	}

	/**
	 * @return only return approved state's
	 */
	public List<GeoStateInfDto> getActiveGeoState() {
		try {
			List<GeoStateInfDto> list = geoStateRepository.getActiveGeoStateList();

			return list;
		} catch (Exception e) {
			throw e;
		}
	}// getAllGeoState

	/**
	 * @param geoState verify given data are exist or not, if data exist then it not
	 *                 store
	 * @return It return three arguments(isSuccess, message and error)
	 */
	public ResponseMessage addGeoState(GeoState geoState) {

		try {
			if (!((geoStateRepository.existState(geoState.getStateCode(), geoState.getName())).size() == 0)) {
				return responseMessageUtil.sendResponse(false, "",
						"Geo-State" + APIConstants.RESPONSE_ALREADY_EXIST + geoState.getStateCode());
			}
			geoState = geoStateRepository.save(geoState);

			approvalUtil.addRecord(DBConstants.TBL_GEO_STATE, geoState.getId());

			return responseMessageUtil.sendResponse(true, "Geo-State" + APIConstants.RESPONSE_ADD_SUCCESS, "");

		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// addAllGeoState

	public ResponseMessage updateGeoStateById(int id, GeoState geoState) {
		try {
			Optional<GeoState> foundState = geoStateRepository.findById(id);
			if (foundState.isPresent()) {
				if (geoStateRepository.findByStateCode(geoState.getStateCode()) == null) {
					if ((geoStateRepository.findByName(geoState.getName())) == null) {

						geoState.setId(id);
						geoState = geoStateRepository.save(geoState);

						approvalUtil.updateRecord(DBConstants.TBL_GEO_STATE, geoState.getId());

						return responseMessageUtil.sendResponse(true,
								"Geo-State" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
					} else {
						return responseMessageUtil.sendResponse(false, "",
								"Geo-State" + APIConstants.RESPONSE_ALREADY_EXIST);
					}
				} else {
					return responseMessageUtil.sendResponse(false, "",
							"Geo-State" + APIConstants.RESPONSE_ALREADY_EXIST);
				}
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Geo-State" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// updateGeoStateById

	public ResponseMessage primaryApproveById(int id) {
		try {
			Optional<GeoState> foundState = geoStateRepository.findById(id);
			if (foundState.isPresent()) {

				GeoState geoState = foundState.get();
				geoState.setStatus(APIConstants.STATUS_APPROVED);
				geoState = geoStateRepository.save(geoState);

				approvalUtil.primaryApprove(DBConstants.TBL_GEO_STATE, geoState.getId());

				return responseMessageUtil.sendResponse(true,
						"Geo-State" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "", "Geo-State" + APIConstants.RESPONSE_NO_RECORD_FOUND);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage finalApproveById(int id) {
		try {
			Optional<GeoState> foundState = geoStateRepository.findById(id);
			if (foundState.isPresent()) {

				GeoState geoState = foundState.get();
				geoState.setStatus(APIConstants.STATUS_ACTIVE);
				geoState = geoStateRepository.save(geoState);

				approvalUtil.finalApprove(DBConstants.TBL_GEO_STATE, geoState.getId());

				return responseMessageUtil.sendResponse(true,
						"Geo-State" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");

			} else {
				return responseMessageUtil.sendResponse(false, "", "Geo-State" + APIConstants.RESPONSE_NO_RECORD_FOUND);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproveById

	public ResponseMessage deleteGeoStateById(int id) {
		try {
			Optional<GeoState> foundState = geoStateRepository.findById(id);
			if (foundState.isPresent()) {

				GeoState geoState = foundState.get();
				geoState.setStatus(APIConstants.STATUS_DELETED);
				geoState = geoStateRepository.save(geoState);

				approvalUtil.delete(DBConstants.TBL_GEO_STATE, geoState.getId());

				return responseMessageUtil.sendResponse(true, "Geo-State" + APIConstants.RESPONSE_DELETE_SUCCESS + id,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Geo-State" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}// deleteGeoStateById

	public GeoState findGeoStateById(int id) {
		try {
			Optional<GeoState> foundState = geoStateRepository.findById(id);
			if (foundState.isPresent()) {
				return foundState.get();
			} else {
				throw new DoesNotExistException("Geo-State" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findGeoStateById

	public ResponseMessage rejectById(int id) {
		try {
			Optional<GeoState> foundState = geoStateRepository.findById(id);

			if (foundState.isPresent()) {

				GeoState geoState = foundState.get();
				geoState.setStatus(APIConstants.STATUS_REJECTED);
				geoState = geoStateRepository.save(geoState);

				approvalUtil.finalApprove(DBConstants.TBL_GEO_STATE, geoState.getId());

				return responseMessageUtil.sendResponse(true, "Geo-State " + APIConstants.RESPONSE_REJECT_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "", "Geo-State " + APIConstants.RESPONSE_REJECT_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// rejectById

	public Map<String, Object> getStateAlias() {
		try {
			Map<String, Object> responseMap = new HashMap<>();

			responseMap.put("States", geoStateRepository.getStates());
			responseMap.put("StateAlias", geoStateRepository.getStateAlias());

			return responseMap;

		} catch (Exception ex) {
			throw ex;
		}
	}

	public List<Object> getCommodityAliasWithPage(int page, int size) {
		try {

			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("Alias").ascending());
			List<Object> responseMap = new ArrayList<>();
			Map<String, List<GeoStateInfDto>> objectMap = new HashMap<>();
			GeoStateAliasDto geoStateAliasDto = new GeoStateAliasDto();
			geoStateAliasDto.setAliasList(geoStateRepository.getStateAliasWithPage(sortedByIdDesc));
			geoStateAliasDto.setStateList(geoStateRepository.getStateList());
			objectMap.put("States", geoStateAliasDto.getStateList());

			responseMap.add(objectMap);
			responseMap.add(geoStateAliasDto.getAliasList());

			return responseMap;

		} catch (Exception ex) {
			throw ex;
		}
	}

	public ResponseMessage tagStateAlias(Map<String, Object> stateAlias) {
		try {
			Integer stateCode = Integer.valueOf(stateAlias.get("stateCode").toString());
			Integer aliasId = Integer.valueOf(stateAlias.get("aliasId").toString());

			int updateCount = 0;
			if (aliasId <= 0) {
				String aliasName = stateAlias.get("aliasName").toString();

				if (geoStateRepository.checkStateAlias(stateCode, aliasName) > 0) {

					return responseMessageUtil.sendResponse(false, "",
							"Geo-State Alias" + APIConstants.RESPONSE_ALREADY_EXIST + aliasName);
				} else {
					updateCount = geoStateRepository.addStateAlias(stateCode, aliasName);
				}
			} else {
				updateCount = geoStateRepository.tagStateAlias(stateCode, aliasId);
			}

			return responseMessageUtil.sendResponse(true, "Geo-State Alias" + APIConstants.RESPONSE_ADD_SUCCESS, "");

		} catch (Exception ex) {
			throw ex;
		}
	}

	public List<GeoState> getStatesByCountryCode(Integer countryCode){
		try
		{
			if (countryCode != null){
				return geoStateRepository.findByCountryCode(countryCode);
			}else {
				throw new DoesNotExistException("Geo-State" + APIConstants.RESPONSE_DOES_NOT_EXIST + countryCode);
			}

		} catch (Exception ex){
			throw ex;
		}
	}

}// GeoStateService
