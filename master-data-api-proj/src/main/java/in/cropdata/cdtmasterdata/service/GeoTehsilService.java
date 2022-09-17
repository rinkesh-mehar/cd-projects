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
import in.cropdata.cdtmasterdata.dto.interfaces.GeoTehsilInfDto;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.model.GeoTehsil;
import in.cropdata.cdtmasterdata.repository.GeoTehsilRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class GeoTehsilService {

	@Autowired
	private GeoTehsilRepository geoTehsilRepository;

	@Autowired
	private AclHistoryUtil approvalUtil;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	public List<GeoTehsilInfDto> getAllGeoTehsil() {
		try {
			List<GeoTehsilInfDto> list = geoTehsilRepository.getGeoTehsilList();

			return list;
		} catch (Exception e) {
			throw e;
		}
	}// getAllGeoTehsil

	public Page<GeoTehsilInfDto> getAllGeoTehsilPaginated(int page, int size, String searchText) {

		searchText = "%" + searchText + "%";
		Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").ascending());

		Page<GeoTehsilInfDto> tehsilList = geoTehsilRepository.getGeoTehsilList(sortedByIdDesc, searchText);

		return tehsilList;
	}

	public List<GeoTehsilInfDto> getAllGeoTehsilByDistrictCode(int districtCode) {
		try {
			List<GeoTehsilInfDto> list = geoTehsilRepository.findAllByDistrictCode(districtCode);

			return list;

		} catch (Exception e) {
			throw e;
		}
	}// getAllGeoTehsilByDistrictCode

	/**
	 * @param geoTehsil verify given data are exist or not, if data exist then it
	 *                  not store
	 * @return It return three arguments(isSuccess, message and error)
	 */
	public ResponseMessage addGeoTehsil(GeoTehsil geoTehsil) {

		try {
			if (geoTehsilRepository.findByTehsilCode(geoTehsil.getTehsilCode()) == null) {
				geoTehsil = geoTehsilRepository.save(geoTehsil);

				approvalUtil.addRecord(DBConstants.TBL_GEO_TEHSIL, geoTehsil.getId());

				return responseMessageUtil.sendResponse(true, "Geo-Tehsil" + APIConstants.RESPONSE_ADD_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Geo-Tehsil" + APIConstants.RESPONSE_ALREADY_EXIST + geoTehsil.getTehsilCode());
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// addAllGeoTehsil

	public ResponseMessage updateGeoTehsilById(int id, GeoTehsil geoTehsil) {
		try {
			Optional<GeoTehsil> foundTehsil = geoTehsilRepository.findById(id);
			if (foundTehsil.isPresent()) {
				if (geoTehsilRepository.findByNameAndDistrictCode(geoTehsil.getName(),
						geoTehsil.getDistrictCode()) == null) {

					geoTehsil.setId(id);
					geoTehsil = geoTehsilRepository.save(geoTehsil);

					approvalUtil.updateRecord(DBConstants.TBL_GEO_TEHSIL, geoTehsil.getId());

					return responseMessageUtil.sendResponse(true,
							"Geo-Tehsil" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
				} else {
					return responseMessageUtil.sendResponse(false, "",
							"Geo-Tehsil" + APIConstants.RESPONSE_ALREADY_EXIST);
				}
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Geo-Tehsil" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// updateGeoTehsilById

	public ResponseMessage primaryApproveById(int id) {
		try {
			Optional<GeoTehsil> foundTehsil = geoTehsilRepository.findById(id);
			if (foundTehsil.isPresent()) {

				GeoTehsil geoTehsil = foundTehsil.get();
				geoTehsil.setStatus(APIConstants.STATUS_APPROVED);
				geoTehsil = geoTehsilRepository.save(geoTehsil);

				approvalUtil.primaryApprove(DBConstants.TBL_GEO_TEHSIL, geoTehsil.getId());

				return responseMessageUtil.sendResponse(true,
						"Geo-Tehsil" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Geo-Tehsil" + APIConstants.RESPONSE_NO_RECORD_FOUND);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage finalApproveById(int id) {
		try {
			Optional<GeoTehsil> foundTehsil = geoTehsilRepository.findById(id);
			if (foundTehsil.isPresent()) {

				GeoTehsil geoTehsil = foundTehsil.get();
				geoTehsil.setStatus(APIConstants.STATUS_ACTIVE);
				geoTehsil = geoTehsilRepository.save(geoTehsil);

				approvalUtil.finalApprove(DBConstants.TBL_GEO_TEHSIL, geoTehsil.getId());

				return responseMessageUtil.sendResponse(true,
						"Geo-Tehsil" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Geo-Tehsil" + APIConstants.RESPONSE_NO_RECORD_FOUND);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproveById

	public ResponseMessage deleteGeoTehsilById(int id) {
		try {
			Optional<GeoTehsil> foundTehsil = geoTehsilRepository.findById(id);
			if (foundTehsil.isPresent()) {

				GeoTehsil geoTehsil = foundTehsil.get();
				geoTehsil.setStatus(APIConstants.STATUS_DELETED);
				geoTehsil = geoTehsilRepository.save(geoTehsil);

				approvalUtil.delete(DBConstants.TBL_GEO_TEHSIL, geoTehsil.getId());

				return responseMessageUtil.sendResponse(true, "Geo-Tehsil" + APIConstants.RESPONSE_DELETE_SUCCESS + id,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Geo-Tehsil" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// deleteGeoTehsilById

	public GeoTehsil findGeoTehsilById(int id) {
		try {
			Optional<GeoTehsil> foundTehsil = geoTehsilRepository.findById(id);

			if (foundTehsil.isPresent()) {
				return foundTehsil.get();
			} else {
				throw new DoesNotExistException("Geo-Tehsil" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findGeoTehsilById

	public ResponseMessage rejectById(int id) {
		try {
			Optional<GeoTehsil> foundTehsil = geoTehsilRepository.findById(id);

			if (foundTehsil.isPresent()) {

				GeoTehsil geoTehsil = foundTehsil.get();
				geoTehsil.setStatus(APIConstants.STATUS_REJECTED);
				geoTehsil = geoTehsilRepository.save(geoTehsil);

				approvalUtil.finalApprove(DBConstants.TBL_GEO_TEHSIL, geoTehsil.getId());

				return responseMessageUtil.sendResponse(true, "Geo-Tehsil " + APIConstants.RESPONSE_REJECT_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "", "Geo-Tehsil " + APIConstants.RESPONSE_REJECT_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// rejectById

	public List<Object> getTehsilAliasWithPage(int page, int size) {
		try {
			Pageable sortedByAliasAsc = PageRequest.of(page, size, Sort.by("Alias").ascending());
			List<Object> responseList = new ArrayList<>();
			Map<String, List<GeoTehsilInfDto>> objectMap = new HashMap<>();
			GeoDistrictAliasDto geoDistrictAliasDto = new GeoDistrictAliasDto();
			geoDistrictAliasDto.setAliasDtoPage(geoTehsilRepository.getTehsilAliasWithPage(sortedByAliasAsc));
			geoDistrictAliasDto.setTehsilInfoDtoList(geoTehsilRepository.getTehsils());
			objectMap.put("Tehsils", geoDistrictAliasDto.getTehsilInfoDtoList());

			responseList.add(objectMap);
			responseList.add(geoDistrictAliasDto.getAliasDtoPage());

			return responseList;

		} catch (Exception ex) {
			throw ex;
		}
	}

	public ResponseMessage tagTehsilAlias(Map<String, Object> tehsilAlias) {
		try {
			Integer stateCode = Integer.valueOf(tehsilAlias.get("stateCode").toString());
			Integer districtCode = Integer.valueOf(tehsilAlias.get("districtCode").toString());
			Integer tehsilCode = Integer.valueOf(tehsilAlias.get("tehsilCode").toString());
			Integer aliasId = Integer.valueOf(tehsilAlias.get("aliasId").toString());

			int updateCount = 0;
			if (aliasId <= 0) {
				String aliasName = tehsilAlias.get("aliasName").toString();

				if (geoTehsilRepository.checkTehsilAlias(stateCode, districtCode, tehsilCode, aliasName) > 0) {

					return responseMessageUtil.sendResponse(false, "",
							"Geo-Tehsil Alias" + APIConstants.RESPONSE_ALREADY_EXIST + aliasName);
				} else {
					updateCount = geoTehsilRepository.addTehsilAlias(stateCode, districtCode, tehsilCode, aliasName);
				}
			} else {
				updateCount = geoTehsilRepository.tagTehsilAlias(stateCode, districtCode, tehsilCode, aliasId);
			}

			return responseMessageUtil.sendResponse(true, "Geo-Tehsil Alias" + APIConstants.RESPONSE_ADD_SUCCESS, "");

		} catch (Exception ex) {
			throw ex;
		}
	}

}// GeoTehsilService
