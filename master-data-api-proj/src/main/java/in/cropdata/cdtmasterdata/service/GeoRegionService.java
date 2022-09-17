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
import in.cropdata.cdtmasterdata.dto.interfaces.GeoRegionInfDto;
import in.cropdata.cdtmasterdata.dto.interfaces.RegionalStateInf;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.model.GeoRegion;
import in.cropdata.cdtmasterdata.repository.GeoRegionRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;
import in.cropdata.cdtmasterdata.website.model.vo.GeoRegionDto;

@Service
public class GeoRegionService {

	@Autowired
	private GeoRegionRepository geoRegionRepository;

	@Autowired
	private AclHistoryUtil approvalUtil;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	public List<GeoRegionInfDto> getAllGeoRegion() {
		try {
			List<GeoRegionInfDto> list = geoRegionRepository.getGeoRegionList();

			return list;
		} catch (Exception e) {
			throw e;
		}
	}// getAllGeoRegion

	public Page<GeoRegionInfDto> getAllGeoRegionPaginated(int page, int size,String searchtext) {
		try {
			searchtext = "%"+searchtext+"%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("regionId").descending());
			Page<GeoRegionInfDto> regionList = geoRegionRepository.getGeoRegionList(sortedByIdDesc,searchtext);

			return regionList;
		} catch (Exception e) {
			throw e;
		}
	}
	
	public List<RegionalStateInf> getRegionByStateCode(int regionId){
		List<RegionalStateInf> list = geoRegionRepository.findAllRegionByStateCode(regionId);
		return list;
	}

	public ResponseMessage addGeoRegion(GeoRegion geoRegion) {

		try {
			geoRegion = geoRegionRepository.save(geoRegion);

			approvalUtil.addRecord(DBConstants.TBL_GEO_REGION, geoRegion.getRegionId());

			return responseMessageUtil.sendResponse(true, "Geo-Region" + APIConstants.RESPONSE_ADD_SUCCESS, "");

		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// addAllGeoRegion

	public ResponseMessage updateGeoRegionByRegionId(int regionId, GeoRegion geoRegion) {
		try {
			Optional<GeoRegion> foundRegion = geoRegionRepository.findByRegionId(regionId);
			if (foundRegion.isPresent()) {

				geoRegion.setRegionId(regionId);
				geoRegion = geoRegionRepository.save(geoRegion);

				approvalUtil.updateRecord(DBConstants.TBL_GEO_REGION, geoRegion.getRegionId());

				return responseMessageUtil.sendResponse(true,
						"Geo-Region" + APIConstants.RESPONSE_UPDATE_SUCCESS + regionId, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Geo-Region" + APIConstants.RESPONSE_UPDATE_ERROR + regionId);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// updateGeoRegionByRegionId

	public ResponseMessage primaryApproveById(int regionId) {
		try {
			Optional<GeoRegion> foundRegion = geoRegionRepository.findByRegionId(regionId);

			if (foundRegion.isPresent()) {

				GeoRegion region = foundRegion.get();
				region.setStatus(APIConstants.STATUS_APPROVED);

				region = geoRegionRepository.save(region);

				approvalUtil.primaryApprove(DBConstants.TBL_GEO_REGION, region.getRegionId());

				return responseMessageUtil.sendResponse(true,
						"Geo-Region" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Geo-Region" + APIConstants.RESPONSE_PRIMARY_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage finalApproveById(int regionId) {
		try {
			Optional<GeoRegion> foundRegion = geoRegionRepository.findByRegionId(regionId);

			if (foundRegion.isPresent()) {

				GeoRegion region = foundRegion.get();
				region.setStatus(APIConstants.STATUS_ACTIVE);

				region = geoRegionRepository.save(region);

				approvalUtil.finalApprove(DBConstants.TBL_GEO_REGION, region.getRegionId());

				return responseMessageUtil.sendResponse(true,
						"Geo-Region" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Geo-Region" + APIConstants.RESPONSE_FINAL_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproveById

	public ResponseMessage deleteGeoRegionByRegionId(int regionId) {
		try {
			Optional<GeoRegion> foundRegion = geoRegionRepository.findByRegionId(regionId);

			if (foundRegion.isPresent()) {

				GeoRegion region = foundRegion.get();
				region.setStatus(APIConstants.STATUS_DELETED);

				region = geoRegionRepository.save(region);

				approvalUtil.delete(DBConstants.TBL_GEO_REGION, region.getRegionId());

				return responseMessageUtil.sendResponse(true,
						"Geo-Region" + APIConstants.RESPONSE_DELETE_SUCCESS + regionId, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Geo-Region" + APIConstants.RESPONSE_DELETE_ERROR + regionId);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// deleteGeoRegionByRegionId

	public GeoRegion findGeoRegionByRegionId(int regionId) {
		try {
			Optional<GeoRegion> foundRegion = geoRegionRepository.findByRegionId(regionId);
			if (foundRegion.isPresent()) {
				return foundRegion.get();
			} else {
				throw new DoesNotExistException("Geo-Region" + APIConstants.RESPONSE_DOES_NOT_EXIST + regionId);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findGeoRegionByRegionId

	public ResponseMessage rejectById(int regionId) {
		try {
			Optional<GeoRegion> foundRegion = geoRegionRepository.findById(regionId);

			if (foundRegion.isPresent()) {

				GeoRegion geoRegion = foundRegion.get();
				geoRegion.setStatus(APIConstants.STATUS_REJECTED);
				geoRegion = geoRegionRepository.save(geoRegion);

				approvalUtil.finalApprove(DBConstants.TBL_GEO_REGION, geoRegion.getRegionId());

				return responseMessageUtil.sendResponse(true, "Geo-Region " + APIConstants.RESPONSE_REJECT_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "", "Geo-Region " + APIConstants.RESPONSE_REJECT_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// rejectById

	public List<GeoRegionDto> getAllGeoRegionbyStateCode(int stateCode) {
		try {

			return geoRegionRepository.getRegionByStateCode(stateCode);
		} catch (Exception e) {
			throw e;
		}
	}

}// GeoRegionService