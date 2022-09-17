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
import in.cropdata.cdtmasterdata.model.GeoCountry;
import in.cropdata.cdtmasterdata.repository.GeoCountryRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class GeoCountryService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GeoCountryService.class);
	private static final String SERVER_ERROR_MSG = "Server Error : ";


	@Autowired
	private GeoCountryRepository geoCountryRepository;

	@Autowired
	private AclHistoryUtil approvalUtil;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	public List<GeoCountry> getAllGeoCountry() {
		try {
			List<GeoCountry> list = geoCountryRepository.findAll(Sort.by("name"));

				return list;
		} catch (Exception e) {
			throw e;
		}
	}// getAllGeoCountry
	
	public Page<GeoCountry> getCountryListByPagenation(Integer page, Integer size, String searchText){
		try {
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());
			return geoCountryRepository.getCountryListByPagenation(sortedByIdDesc, searchText);
		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Geo-Country Data Found For Searched Text -> " + searchText);
		}
	}

	/**
	 * @return It return only Active geo country list
	 */
	public List<GeoCountry> getActiveGeoCountryList()
	{
		try
		{

			List<GeoCountry> list = geoCountryRepository.getActiveCountry();
			return list;
		} catch (Exception e)
		{
			throw e;
		}
	}

	/**
	 * @param geoCountry verify given data are exist or not,
	 *                   if data exist then it not store
	 * @return It return three arguments(isSuccess, message and error)
	 */
	public ResponseMessage addGeoCountry(GeoCountry geoCountry)
	{

		try
		{
			if (!((geoCountryRepository.existCountry(geoCountry.getCountryCode(), geoCountry.getName())) == null))
			{
				return responseMessageUtil.sendResponse(false, "", "Geo-Country" +
						APIConstants.RESPONSE_ALREADY_EXIST + geoCountry.getCountryCode());
			}
			geoCountry = geoCountryRepository.save(geoCountry);

			approvalUtil.addRecord(DBConstants.TBL_GEO_COUNTRY, geoCountry.getId());

			return responseMessageUtil.sendResponse(true, "Geo-Country" + APIConstants.RESPONSE_ADD_SUCCESS, "");

		} catch (Exception e)
		{
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// addAllGeoCountry

	public ResponseMessage updateGeoCountryById(int id, GeoCountry geoCountry) {
		try {
			Optional<GeoCountry> foundCountry = geoCountryRepository.findById(id);

			if (foundCountry.isPresent()) {

				geoCountry.setId(id);
				geoCountry = geoCountryRepository.save(geoCountry);

				approvalUtil.updateRecord(DBConstants.TBL_GEO_COUNTRY, geoCountry.getId());

				return responseMessageUtil.sendResponse(true, "Geo-Country" + APIConstants.RESPONSE_UPDATE_SUCCESS + id,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Geo-Country" + APIConstants.RESPONSE_UPDATE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// updateGeoCountryById

	public ResponseMessage primaryApproveById(int id) {
		try {
			Optional<GeoCountry> foundCountry = geoCountryRepository.findById(id);

			if (foundCountry.isPresent()) {

				GeoCountry country = foundCountry.get();
				country.setStatus(APIConstants.STATUS_APPROVED);

				country = geoCountryRepository.save(country);

				approvalUtil.primaryApprove(DBConstants.TBL_GEO_COUNTRY, country.getId());

				return responseMessageUtil.sendResponse(true,
						"Geo-Country" + APIConstants.RESPONSE_PRIMARY_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Geo-Country" + APIConstants.RESPONSE_PRIMARY_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// primaryApproveById

	public ResponseMessage finalApproveById(int id) {
		try {
			Optional<GeoCountry> foundCountry = geoCountryRepository.findById(id);

			if (foundCountry.isPresent()) {

				GeoCountry country = foundCountry.get();
				country.setStatus(APIConstants.STATUS_ACTIVE);

				country = geoCountryRepository.save(country);

				approvalUtil.finalApprove(DBConstants.TBL_GEO_COUNTRY, country.getId());

				return responseMessageUtil.sendResponse(true,
						"Geo-Country" + APIConstants.RESPONSE_FINAL_APPROVAL_SUCCESS, "");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Geo-Country" + APIConstants.RESPONSE_FINAL_APPROVAL_ERROR);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// finalApproveById

	public ResponseMessage deleteGeoCountryById(int id) {
		try {
			Optional<GeoCountry> foundCountry = geoCountryRepository.findById(id);

			if (foundCountry.isPresent()) {

				GeoCountry country = foundCountry.get();
				country.setStatus(APIConstants.STATUS_DELETED);

				country = geoCountryRepository.save(country);

				approvalUtil.delete(DBConstants.TBL_GEO_COUNTRY, country.getId());

				return responseMessageUtil.sendResponse(true, "Geo-Country" + APIConstants.RESPONSE_DELETE_SUCCESS + id,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Geo-Country" + APIConstants.RESPONSE_DELETE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// deleteGeoCountryById

	public GeoCountry findGeoCountryById(int id) {
		try {
			Optional<GeoCountry> foundCountry = geoCountryRepository.findById(id);
			if (foundCountry.isPresent()) {
				return foundCountry.get();
			} else {
				throw new DoesNotExistException("Geo-Country" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findGeoCountryById
	
	   public ResponseMessage rejectById(int id) {
		   try {
		       Optional<GeoCountry> foundGeoCountry = geoCountryRepository.findById(id);

		       if (foundGeoCountry.isPresent()) {

		    	   GeoCountry geoCountry = foundGeoCountry.get();
		    	   geoCountry.setStatus(APIConstants.STATUS_REJECTED);
		    	   geoCountry  = geoCountryRepository.save(geoCountry );

		     approvalUtil.finalApprove(DBConstants.TBL_GEO_COUNTRY, geoCountry.getId());

		     return responseMessageUtil.sendResponse(true, "Geo-Country " + APIConstants.RESPONSE_REJECT_SUCCESS, "");
		       } else {
		     return responseMessageUtil.sendResponse(false, "", "Geo-Country " + APIConstants.RESPONSE_REJECT_ERROR);
		       }
		   } catch (Exception e) {
		       return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		   }
		     }//rejectById 

}// GeoCountryService
