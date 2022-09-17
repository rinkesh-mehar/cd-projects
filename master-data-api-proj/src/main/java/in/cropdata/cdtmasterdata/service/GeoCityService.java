package in.cropdata.cdtmasterdata.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import in.cropdata.cdtmasterdata.constants.APIConstants;
import in.cropdata.cdtmasterdata.constants.DBConstants;
import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.dto.interfaces.AgriCommodityInfo;
import in.cropdata.cdtmasterdata.dto.interfaces.GeoCityInfDto;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.model.GeoCity;
import in.cropdata.cdtmasterdata.repository.GeoCityRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class GeoCityService {

	@Autowired
	private GeoCityRepository geoCityRepository;

	@Autowired
	private AclHistoryUtil approvalUtil;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	public List<GeoCity> getAllGeoCity() {
		try {
			List<GeoCity> list = geoCityRepository.findAll();

			return list;
		} catch (Exception e) {
			throw e;
		}
	}// getAllGeoCity

	public Page<GeoCityInfDto> getAllGeoCityPaginated(int page, int size, String searchText) {

		try {
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").ascending());


			Page<GeoCityInfDto> cityList = geoCityRepository
					.getGeoCityList(sortedByIdDesc, searchText);

			return cityList;

		} catch (Exception e) {
			throw e;
		}
	}//getAllGeoCityPaginated

	public ResponseMessage addGeoCity(GeoCity geoCity) {

		try {
			geoCity = geoCityRepository.save(geoCity);
			approvalUtil.addRecord(DBConstants.TBL_GEO_CITY, geoCity.getId());

			return responseMessageUtil.sendResponse(true, "Geo-City" + APIConstants.RESPONSE_ADD_SUCCESS, "");

		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// addAllGeoCity

	public ResponseMessage updateGeoCityById(int id, GeoCity geoCity) {
		try {
			Optional<GeoCity> foundCity = geoCityRepository.findById(id);

			if (foundCity.isPresent()) {

				geoCity.setId(id);
				geoCity = geoCityRepository.save(geoCity);

				approvalUtil.updateRecord(DBConstants.TBL_GEO_CITY, geoCity.getId());

				return responseMessageUtil.sendResponse(true, "Geo-City" + APIConstants.RESPONSE_UPDATE_SUCCESS + id,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Geo-City" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// updateGeoCityById

	public ResponseMessage primaryApproveById(int id) {
		try {
			Optional<GeoCity> foundCity = geoCityRepository.findById(id);

			if (foundCity.isPresent()) {

				GeoCity geoCity = foundCity.get();
				geoCity.setStatus(APIConstants.STATUS_APPROVED);
				geoCity = geoCityRepository.save(geoCity);

				approvalUtil.primaryApprove(DBConstants.TBL_GEO_CITY, geoCity.getId());

				return responseMessageUtil.sendResponse(true,
						"Geo-City" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "", "Geo-City" + APIConstants.RESPONSE_NO_RECORD_FOUND);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage finalApproveById(int id) {
		try {
			Optional<GeoCity> foundCity = geoCityRepository.findById(id);
			if (foundCity.isPresent()) {

				GeoCity geoCity = foundCity.get();
				geoCity.setStatus(APIConstants.STATUS_ACTIVE);
				geoCity = geoCityRepository.save(geoCity);

				approvalUtil.finalApprove(DBConstants.TBL_GEO_CITY, geoCity.getId());

				return responseMessageUtil.sendResponse(true, "Geo-City" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "", "Geo-City" + APIConstants.RESPONSE_NO_RECORD_FOUND);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproveById

	public ResponseMessage deleteGeoCityById(int id) {
		try {
			Optional<GeoCity> foundCity = geoCityRepository.findById(id);
			if (foundCity.isPresent()) {

				GeoCity geoCity = foundCity.get();
				geoCity.setStatus(APIConstants.STATUS_DELETED);
				geoCity = geoCityRepository.save(geoCity);

				approvalUtil.delete(DBConstants.TBL_GEO_CITY, geoCity.getId());

				return responseMessageUtil.sendResponse(true, "Geo-City" + APIConstants.RESPONSE_DELETE_SUCCESS + id,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Geo-City" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// deleteGeoCityById

	public GeoCity findGeoCityById(int id) {
		try {
			Optional<GeoCity> foundCity = geoCityRepository.findById(id);
			if (foundCity.isPresent()) {
				return foundCity.get();
			} else {
				throw new DoesNotExistException("Geo-City" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findGeoCityById

	public ResponseMessage rejectById(int id) {
		try {
			Optional<GeoCity> foundCity = geoCityRepository.findById(id);

			if (foundCity.isPresent()) {

				GeoCity geoCity = foundCity.get();
				geoCity.setStatus(APIConstants.STATUS_REJECTED);
				geoCity = geoCityRepository.save(geoCity);

				approvalUtil.finalApprove(DBConstants.TBL_GEO_CITY, geoCity.getId());

				return responseMessageUtil.sendResponse(true, "Geo-City " + APIConstants.RESPONSE_REJECT_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "", "Geo-City " + APIConstants.RESPONSE_REJECT_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// rejectById
}
