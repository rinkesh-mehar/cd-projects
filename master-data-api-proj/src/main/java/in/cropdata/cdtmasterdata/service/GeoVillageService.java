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
import in.cropdata.cdtmasterdata.dto.interfaces.GeoVillageInfDto;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.model.GeoVillage;
import in.cropdata.cdtmasterdata.repository.GeoVillageRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class GeoVillageService {

	@Autowired
	private GeoVillageRepository geoVillageRepository;

	@Autowired
	private AclHistoryUtil approvalUtil;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	public List<GeoVillageInfDto> getAllGeoVillage() {
		try {
			List<GeoVillageInfDto> list = geoVillageRepository.getGeoVillageList();

			return list;
		} catch (Exception e) {
			throw e;
		}
	}// getAllGeoVillage

	public Page<GeoVillageInfDto> getAllGeoVillagePaginated(int page, int size, String searchText) {
		try {
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());

			return geoVillageRepository.getGeoVillageList(sortedByIdDesc, searchText);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * @param geoVillage verify given data are exist or not, if data exist then it
	 *                   not store
	 * @return It return three arguments(isSuccess, message and error)
	 */
	public ResponseMessage addGeoVillage(GeoVillage geoVillage) {
		try {
			if (geoVillageRepository.findByVillageCode(geoVillage.getVillageCode()) != null) {
				return responseMessageUtil.sendResponse(false, "",
						"Geo-Village" + APIConstants.RESPONSE_ALREADY_EXIST + geoVillage.getVillageCode());
			}
			geoVillage = geoVillageRepository.save(geoVillage);
			approvalUtil.addRecord(DBConstants.TBL_GEO_VILLAGE, geoVillage.getId());

			return responseMessageUtil.sendResponse(true, "Geo-Village" + APIConstants.RESPONSE_ADD_SUCCESS, "");

		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// addAllGeoVillage

	public ResponseMessage updateGeoVillageById(int id, GeoVillage geoVillage) {
		try {
			Optional<GeoVillage> foundVillage = geoVillageRepository.findById(id);

			if (foundVillage.isPresent()) {
				if (geoVillageRepository.findByVillageCode(geoVillage.getVillageCode()) == null) {
					geoVillage.setId(id);
					geoVillage = geoVillageRepository.save(geoVillage);

					approvalUtil.updateRecord(DBConstants.TBL_GEO_VILLAGE, geoVillage.getId());

					return responseMessageUtil.sendResponse(true,
							"Geo-Village" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
				} else {
					return responseMessageUtil.sendResponse(false, "",
							"Geo-Village" + APIConstants.RESPONSE_ALREADY_EXIST + geoVillage.getVillageCode());
				}
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Geo-Village" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// updateGeoVillageById

	public ResponseMessage primaryApproveById(int id) {
		try {
			Optional<GeoVillage> foundVillage = geoVillageRepository.findById(id);

			if (foundVillage.isPresent()) {

				GeoVillage geoVillage = foundVillage.get();
				geoVillage.setStatus(APIConstants.STATUS_APPROVED);
				geoVillage = geoVillageRepository.save(geoVillage);

				approvalUtil.primaryApprove(DBConstants.TBL_GEO_VILLAGE, geoVillage.getId());

				return responseMessageUtil.sendResponse(true,
						"Geo-Village" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Geo-Village" + APIConstants.RESPONSE_NO_RECORD_FOUND);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage finalApproveById(int id) {
		try {
			Optional<GeoVillage> foundVillage = geoVillageRepository.findById(id);
			if (foundVillage.isPresent()) {

				GeoVillage geoVillage = foundVillage.get();
				geoVillage.setStatus(APIConstants.STATUS_ACTIVE);
				geoVillage = geoVillageRepository.save(geoVillage);

				approvalUtil.finalApprove(DBConstants.TBL_GEO_VILLAGE, geoVillage.getId());

				return responseMessageUtil.sendResponse(true,
						"Geo-Village" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Geo-Village" + APIConstants.RESPONSE_NO_RECORD_FOUND);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproveById

	public ResponseMessage deleteGeoVillageById(int id) {
		try {
			Optional<GeoVillage> foundVillage = geoVillageRepository.findById(id);
			if (foundVillage.isPresent()) {

				GeoVillage geoVillage = foundVillage.get();
				geoVillage.setStatus(APIConstants.STATUS_DELETED);
				geoVillage = geoVillageRepository.save(geoVillage);

				approvalUtil.delete(DBConstants.TBL_GEO_VILLAGE, geoVillage.getId());

				return responseMessageUtil.sendResponse(true, "Geo-Village" + APIConstants.RESPONSE_DELETE_SUCCESS + id,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Geo-Village" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// deleteGeoVillageById

	public GeoVillage findGeoVillageById(int id) {
		try {
			Optional<GeoVillage> foundVillage = geoVillageRepository.findById(id);
			if (foundVillage.isPresent()) {
				return foundVillage.get();
			} else {
				throw new DoesNotExistException("Geo-Village" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findGeoVillageById

	public ResponseMessage rejectById(int id) {
		try {
			Optional<GeoVillage> foundVillage = geoVillageRepository.findById(id);

			if (foundVillage.isPresent()) {

				GeoVillage geoVillage = foundVillage.get();
				geoVillage.setStatus(APIConstants.STATUS_REJECTED);
				geoVillage = geoVillageRepository.save(geoVillage);

				approvalUtil.finalApprove(DBConstants.TBL_GEO_VILLAGE, geoVillage.getId());

				return responseMessageUtil.sendResponse(true, "Geo-Village " + APIConstants.RESPONSE_REJECT_SUCCESS,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "", "Geo-Village " + APIConstants.RESPONSE_REJECT_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// rejectById

	/**
	 * @param tehsilCode the tehsil code based on which village list to be fetched
	 */
	public List<GeoVillageInfDto> getGeoVillagesByTehsilCode(final Integer tehsilCode) {
		try {

			return geoVillageRepository.getGeoVillagesByTehsilCode(tehsilCode);

		} catch (Exception ex) {
			throw ex;
		}
	}

	public List<Object> getVillageAliasWithPage(int page, int size) {
		try {
			Pageable sortedByAliasAsc = PageRequest.of(page, size, Sort.by("Alias").ascending());
			List<Object> responseList = new ArrayList<>();
			Map<String, List<GeoVillageInfDto>> objectMap = new HashMap<>();
			GeoDistrictAliasDto geoDistrictAliasDto = new GeoDistrictAliasDto();
			geoDistrictAliasDto.setAliasDtoPage(geoVillageRepository.getVillageAliasWithPage(sortedByAliasAsc));
			geoDistrictAliasDto.setVillageInfoDtoList(geoVillageRepository.getVillages());
			objectMap.put("Villages", geoDistrictAliasDto.getVillageInfoDtoList());

			responseList.add(objectMap);
			responseList.add(geoDistrictAliasDto.getAliasDtoPage());

			return responseList;

		} catch (Exception ex) {
			throw ex;
		}
	}

	public ResponseMessage tagVillageAlias(Map<String, Object> villageAlias) {
		try {
			Integer stateCode = Integer.valueOf(villageAlias.get("stateCode").toString());
			Integer districtCode = Integer.valueOf(villageAlias.get("districtCode").toString());
			Integer tehsilCode = Integer.valueOf(villageAlias.get("tehsilCode").toString());
			Integer villageCode = Integer.valueOf(villageAlias.get("villageCode").toString());
			Integer aliasId = Integer.valueOf(villageAlias.get("aliasId").toString());

			int updateCount = 0;
			if (aliasId <= 0) {
				String aliasName = villageAlias.get("aliasName").toString();

				if (geoVillageRepository.checkVillageAlias(stateCode, districtCode, tehsilCode, villageCode,
						aliasName) > 0) {

					return responseMessageUtil.sendResponse(false, "",
							"Geo-Village Alias" + APIConstants.RESPONSE_ALREADY_EXIST + aliasName);
				} else {
					updateCount = geoVillageRepository.addVillageAlias(stateCode, districtCode, tehsilCode, villageCode,
							aliasName);
				}
			} else {
				updateCount = geoVillageRepository.tagVillageAlias(stateCode, districtCode, tehsilCode, villageCode,
						aliasId);
			}

			return responseMessageUtil.sendResponse(true, "Geo-Village Alias" + APIConstants.RESPONSE_ADD_SUCCESS, "");

		} catch (Exception ex) {
			throw ex;
		}
	}

}// GeoVillageService
