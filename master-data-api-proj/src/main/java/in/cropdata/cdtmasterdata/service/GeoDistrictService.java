package in.cropdata.cdtmasterdata.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import in.cropdata.cdtmasterdata.constants.APIConstants;
import in.cropdata.cdtmasterdata.constants.DBConstants;
import in.cropdata.cdtmasterdata.dto.GeoDistrictAliasDto;
import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.dto.interfaces.GeoDistrictInfDto;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.model.GeoDistrict;
import in.cropdata.cdtmasterdata.repository.GeoDistrictRepository;
import in.cropdata.cdtmasterdata.repository.GeoStateRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class GeoDistrictService {

	@Autowired
	private GeoDistrictRepository geoDistrictRepository;

	@Autowired
	private AclHistoryUtil approvalUtil;

	@Autowired
	private GeoStateRepository geoStateRepository;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	public List<GeoDistrictInfDto> getAllGeoDistrict() {

		try {
			List<GeoDistrictInfDto> list = geoDistrictRepository.getGeoDistrictList();

			return list;

		} catch (Exception e) {
			throw e;
		}
	}// getAllGeoDistrict

//	public Page<GeoDistrictInfDto> getAllGeoDistrictPaginated(int page, int size, String searchText) {
//		try {
////			searchText = "%"+searchText+"%";
//			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").ascending());
//
//			Page<GeoDistrictInfDto> districtList = geoDistrictRepository.getGeoDistrictList(sortedByIdDesc,searchText);
//
//			for (GeoDistrict geoDistrict : districtList) {
//				GeoState state = geoStateRepository.findByStateCode(geoDistrict.getStateCode());
//				if (state != null) {
//
//					geoDistrict.setState(state.getName());
//				}
//			}
//			return districtList;
//		} catch (Exception e) {
//			throw e;
//		}
//	}

	public Page<GeoDistrictInfDto> getAllGeoDistrictPaginated(int page, int size, String searchText) {

		try {
			searchText = "%" + searchText + "%";

//		System.out.println("searchText--> " + searchText);

			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());

			Page<GeoDistrictInfDto> districtList = geoDistrictRepository.getGeoDistrictList(sortedByIdDesc, searchText);

			return districtList;
		} catch (Exception e) {
			throw e;
		}
	}// getAllAgriPhenophaseDurationPaginated

	public List<GeoDistrict> getAllGeoDistrictByStateCode(int stateCode) {
		try {
			List<GeoDistrict> list = geoDistrictRepository.findAllByStateCode(stateCode);

			return list;

		} catch (Exception e) {
			throw e;
		}
	}// getAllGeoDistrictByStateCode

	/**
	 * @param geoDistrict verify given data are exist or not, if data exist then it
	 *                    not store
	 * @return It return three arguments(isSuccess, message and error)
	 */
	public ResponseMessage addGeoDistrict(GeoDistrict geoDistrict) {

		try {
			if (geoDistrictRepository.findByDistrictCode(geoDistrict.getDistrictCode()) == null) {
				if (!(geoDistrictRepository.findByNameAndStateCode(geoDistrict.getName(),
						geoDistrict.getStateCode()) == null)) {
					return responseMessageUtil.sendResponse(false, "",
							"Geo-District Already Exist" );
				}
				geoDistrict = geoDistrictRepository.save(geoDistrict);

				approvalUtil.addRecord(DBConstants.TBL_GEO_DISTRICT, geoDistrict.getId());

				return responseMessageUtil.sendResponse(true, "Geo-District" + APIConstants.RESPONSE_ADD_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Geo-District Already Exist");
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// addGeoDistrict

	public ResponseMessage updateGeoDistrictById(int id, GeoDistrict geoDistrict) {
		try {
			Optional<GeoDistrict> foundDistrict = geoDistrictRepository.findById(id);

			if (foundDistrict.isPresent()) {
				if (geoDistrictRepository.findByNameAndStateCode(geoDistrict.getName(),
						geoDistrict.getStateCode()) == null) {

					geoDistrict.setId(id);
					geoDistrict = geoDistrictRepository.save(geoDistrict);

					approvalUtil.updateRecord(DBConstants.TBL_GEO_DISTRICT, geoDistrict.getId());

					return responseMessageUtil.sendResponse(true,
							"Geo-District" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
				} else {
					return responseMessageUtil.sendResponse(false, "",
							"Geo-District" + APIConstants.RESPONSE_ALREADY_EXIST + id);
				}
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Geo-District" + APIConstants.RESPONSE_UPDATE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// updateGeoDistrictById

	public ResponseMessage primaryApproveById(int id) {

		Optional<GeoDistrict> foundDistrict = geoDistrictRepository.findById(id);

		if (foundDistrict.isPresent()) {

			try {
				GeoDistrict district = foundDistrict.get();
				district.setStatus(APIConstants.STATUS_APPROVED);

				district = geoDistrictRepository.save(district);

				approvalUtil.primaryApprove(DBConstants.TBL_GEO_DISTRICT, district.getId());

				return responseMessageUtil.sendResponse(true,
						"Geo-District" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");

			} catch (Exception e) {
				return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
			}
		} else {
			return responseMessageUtil.sendResponse(false, "",
					"Geo-District" + APIConstants.RESPONSE_PRIMARY_APPROVAL_ERROR);
		}
	}// primaryApproveById

	public ResponseMessage finalApproveById(int id) {
		try {
			Optional<GeoDistrict> foundDistrict = geoDistrictRepository.findById(id);

			if (foundDistrict.isPresent()) {

				GeoDistrict district = foundDistrict.get();
				district.setStatus(APIConstants.STATUS_ACTIVE);

				district = geoDistrictRepository.save(district);

				approvalUtil.finalApprove(DBConstants.TBL_GEO_DISTRICT, district.getId());

				return responseMessageUtil.sendResponse(true,
						"Geo-District" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Geo-District" + APIConstants.RESPONSE_FINAL_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproveById

	public ResponseMessage deleteGeoDistrictById(int id) {
		try {
			Optional<GeoDistrict> foundDistrict = geoDistrictRepository.findById(id);

			if (foundDistrict.isPresent()) {

				GeoDistrict district = foundDistrict.get();
				district.setStatus(APIConstants.STATUS_DELETED);

				district = geoDistrictRepository.save(district);

				approvalUtil.delete(DBConstants.TBL_GEO_DISTRICT, district.getId());

				return responseMessageUtil.sendResponse(true,
						"Geo-District" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Geo-District" + APIConstants.RESPONSE_DELETE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// deleteGeoDistrictById

	public GeoDistrict findGeoDistrictById(int id) {
		try {
			Optional<GeoDistrict> foundDistrict = geoDistrictRepository.findById(id);
			if (foundDistrict.isPresent()) {
				return foundDistrict.get();
			} else {
				throw new DoesNotExistException("Geo-District" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findGeoDistrictById

	public ResponseMessage rejectById(int id) {
		try {
			Optional<GeoDistrict> foundGeoDistrict = geoDistrictRepository.findById(id);

			if (foundGeoDistrict.isPresent()) {

				GeoDistrict geoDistrict = foundGeoDistrict.get();
				geoDistrict.setStatus(APIConstants.STATUS_REJECTED);
				geoDistrict = geoDistrictRepository.save(geoDistrict);

				approvalUtil.finalApprove(DBConstants.TBL_GEO_DISTRICT, geoDistrict.getId());

				return responseMessageUtil.sendResponse(true, "Geo-District " + APIConstants.RESPONSE_REJECT_SUCCESS,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Geo-District " + APIConstants.RESPONSE_REJECT_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// rejectById

	/*
	 * public Map<String, Object> getDistrictAlias() { try { Map<String, Object>
	 * responseMap = new HashMap<>();
	 *
	 * responseMap.put("DistrictAlias", geoDistrictRepository.getDistrictAlias());
	 * responseMap.put("Districts", geoDistrictRepository.getDistricts());
	 *
	 * return responseMap;
	 *
	 * } catch (Exception ex) { throw ex; } }
	 */ // pranay

	public List<Object> getGeoDistrictAliasWithPage(int page, int size) {
		try {
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("Alias").ascending());
			List<Object> responseList = new ArrayList<>();
			Map<String, List<GeoDistrictInfDto>> objectMap = new HashMap<>();
			GeoDistrictAliasDto geoDistrictAliasDto = new GeoDistrictAliasDto();
			geoDistrictAliasDto.setAliasDtoPage(geoDistrictRepository.getDistrictAliasWithPage(sortedByIdDesc));
			geoDistrictAliasDto.setDistrictInfoDtoList(geoDistrictRepository.getDistrictsWithPage());
			objectMap.put("Districts", geoDistrictAliasDto.getDistrictInfoDtoList());

			responseList.add(objectMap);
			responseList.add(geoDistrictAliasDto.getAliasDtoPage());

			return responseList;

		} catch (Exception ex) {
			throw ex;
		}
	}

	public Map<String, Object> getStates() {
		try {
			Map<String, Object> responseMap = new HashMap<>();

			responseMap.put("States", geoDistrictRepository.getStates());
//			responseMap.put("DistrictAlias", geoDistrictRepository.getDistrictAlias());

			return responseMap;

		} catch (Exception ex) {
			throw ex;
		}
	}

	public Map<String, Object> getDistrictsByStateCode(Integer stateCode) {
		try {
			Map<String, Object> responseMap = new HashMap<>();

			responseMap.put("Districts", geoDistrictRepository.getDistrictsByStateCode(stateCode));

			return responseMap;

		} catch (Exception ex) {
			throw ex;
		}
	}

	public ResponseMessage tagDistrictWithAlias(Map<String, Object> districtAlias) {
		try {
			Integer stateCode = Integer.valueOf(districtAlias.get("stateCode").toString());
			Integer districtCode = Integer.valueOf(districtAlias.get("districtCode").toString());
			Integer aliasId = Integer.valueOf(districtAlias.get("aliasId").toString());

			int updateCount = 0;
			if (aliasId <= 0) {
				String aliasName = districtAlias.get("aliasName").toString();

				if (geoDistrictRepository.checkDistrictAlias(stateCode, districtCode, aliasName) > 0) {

					return responseMessageUtil.sendResponse(false, "",
							"Geo-District Alias" + APIConstants.RESPONSE_ALREADY_EXIST + aliasName);
				} else {
					updateCount = geoDistrictRepository.addDistrictAlias(stateCode, districtCode, aliasName);
				}
			} else {
				updateCount = geoDistrictRepository.tagDistrictAlias(stateCode, districtCode, aliasId);
			}

			return responseMessageUtil.sendResponse(true, "Geo-District Alias" + APIConstants.RESPONSE_ADD_SUCCESS, "");

		} catch (Exception ex) {
			throw ex;
		}
	}

}// GeoDistrictService
