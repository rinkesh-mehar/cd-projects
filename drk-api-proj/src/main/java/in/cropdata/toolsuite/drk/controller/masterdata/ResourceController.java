package in.cropdata.toolsuite.drk.controller.masterdata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.cropdata.toolsuite.drk.dao.masterdata.ResourceDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.cropdata.toolsuite.drk.DrkApplication;
import in.cropdata.toolsuite.drk.constants.APIConstants;
import in.cropdata.toolsuite.drk.constants.ErrorConstants;
import in.cropdata.toolsuite.drk.controller.util.DrkUtils;
import in.cropdata.toolsuite.drk.exceptions.tileassignment.InvalidApiKeyException;
import in.cropdata.toolsuite.drk.properties.AppProperties;
import in.cropdata.toolsuite.drk.service.masterdata.ResourceService;

@RestController
@RequestMapping("/" + DrkApplication.apiVersion)
public class ResourceController {
	Logger logger = LoggerFactory.getLogger(ResourceController.class);

	@Autowired
	private ResourceService resourceService;

	@Autowired
	AppProperties appProperties;

	@Autowired
	private ResourceDao resourceDao;
	
	@Autowired
	private DrkUtils drkUtils;

	/**
	 * Get Resource Data based on resource-label For DRK
	 * 
	 * @param resource
	 * @return ResponseEntity<String>
	 */
	@GetMapping("/data/{resource}")
	public ResponseEntity<?> getData(@RequestParam(required = false) String apiKey, @PathVariable String resource,
			@RequestParam(required = false, defaultValue = "1") long unixTimestamp,
			@RequestParam(defaultValue = "1") int page, @RequestParam(required = false) String regionId,
			@RequestParam(required = false) String commodityId, @RequestParam(required = false) String varietyId,
			@RequestParam(required = false) String subRegionId) {

		if (drkUtils.checkNull(apiKey)) {
			throw new InvalidApiKeyException(ErrorConstants.REQUIRED_API_KEY,
					"Api Key is required to access this Resource");
		}

		if (drkUtils.validateApiKey(apiKey)) {
			Map<String, Object> selectQuery = resourceDao.validateResource(resource);
			if ((selectQuery.get("selectQuery") == "")) {
				Map<String, Object> responseMap = new HashMap<>();
				responseMap.put("errorCode", ErrorConstants.INVALID_RESOURCE);
				responseMap.put("error", "Invalid resource name");
				return new ResponseEntity<>(responseMap, HttpStatus.NOT_ACCEPTABLE);
			}
			String response = resourceService.getDataForResource(unixTimestamp, page, apiKey,
					String.valueOf(selectQuery.get("selectQuery")));
			return new ResponseEntity<>(response, HttpStatus.OK);
		}else {
			throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, APIConstants.RESPONSE_INVALID_APIKEY);
		}
	}// getData
	
	
	/**
	 * Get Resource Data based on resource-label for agriota
	 * 
	 * @param resource
	 * @return ResponseEntity<String>
	 */
	@GetMapping("/{resource}")
	public ResponseEntity<?> getDataForAgriota(@RequestParam(required = false) String apiKey,
			@PathVariable String resource, @RequestParam(required = false, defaultValue = "1") long unixTimestamp,
			@RequestParam(defaultValue = "1") int page) {

		if (apiKey == null) {
			throw new InvalidApiKeyException(ErrorConstants.REQUIRED_API_KEY,
					"Api Key is required to access this Resource");
		}

		if (drkUtils.validateApiKeyForAgriota(apiKey)) {
			Map<String, Object> selectQuery = resourceDao.validateResource(resource);
			if ((selectQuery.get("selectQuery") == "")) {
				Map<String, Object> responseMap = new HashMap<>();
				responseMap.put("errorCode", ErrorConstants.INVALID_RESOURCE);
				responseMap.put("error", "Invalid resource name");
				return new ResponseEntity<>(responseMap, HttpStatus.NOT_ACCEPTABLE);
			}

			String response = resourceService.getDataForAgiotaResource(unixTimestamp, page, apiKey,
					String.valueOf(selectQuery.get("selectQuery")));

			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, APIConstants.RESPONSE_INVALID_AGRIOTA_APIKEY);
		}

	}// getData
	
	
	
	/**
	 * Get Gstm-Sync
	 */
	@GetMapping("/gstmsync")
	public List<Map<String, Object>> getGstmSync(@RequestParam(required = false) String apiKey)
	{

			if (apiKey == null) {
				throw new InvalidApiKeyException(ErrorConstants.REQUIRED_API_KEY,
						"Api Key is required to access this Resource");
			}
			
			if (drkUtils.validateApiKey(apiKey)) {
				return resourceService.getGstmSync();
			} else {
				throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, APIConstants.RESPONSE_INVALID_APIKEY);
			}
	}
	
	
	
	/**
	 * Get Resource Data based on resource-label
	 * 
	 * @param resource
	 * @return ResponseEntity<String>
	 */
	@GetMapping("/media/{resource}")
	public void getMediaData(HttpServletResponse httpServletResponse, @RequestParam(required = false) String apiKey,
			@PathVariable String resource, @RequestParam(required = false, defaultValue = "0") long unixTimestamp) {

		if (apiKey == null) {
			throw new InvalidApiKeyException(ErrorConstants.REQUIRED_API_KEY,
					"Api Key is required to access this Resource");
		}

		if (resource == null) {
			throw new InvalidApiKeyException(ErrorConstants.INVALID_RESOURCE, "Resource can not be empty or null!");
		}

//		if (resourceDao.isApiKeyExists(apiKey)) {
//
//			/*
//			 * resourceService.getMediaDataForResource(httpServletResponse, resource,
//			 * unixTimestamp);
//			 */
//			resourceService.getMediaForResource(httpServletResponse, resource, unixTimestamp);
//
//		} else {
//			throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
//		}

	}// getMediaData

	/**
	 * Get All Media data as a downloadable zip file.
	 * 
	 * @param httpServletResponse the httpServletResponse to add zip file in
	 *                            downloadable form
	 * @param request
	 * @param apiKey
	 * @param unixTimestamp
	 * 
	 * @return ResponseEntity
	 */
	@GetMapping("/media/all")
	public void getMediaCombinedData(HttpServletResponse httpServletResponse, HttpServletRequest request,
			@RequestParam(required = false) String apiKey,
			@RequestParam(required = false, defaultValue = "0") long unixTimestamp) {

		if (apiKey == null) {
			throw new InvalidApiKeyException(ErrorConstants.REQUIRED_API_KEY,
					"Api Key is required to access this Resource");
		}

//		if (resourceDao.isApiKeyExists(apiKey)) {
//
////			return resourceService.getMediaDataForPlantPartAndStress(httpServletResponse, request, unixTimestamp);
//			resourceService.getMediaFile(httpServletResponse, request, unixTimestamp);
//		} else {
//			throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
//		}
	}// getMediaCombinedData

	/**
	 * Get All Media data as a downloadable zip file.
	 * 
	 * @param httpServletResponse the httpServletResponse to add zip file in
	 *                            downloadable form
	 * @param apiKey              the API key required to access the API
	 * @param unixTimestamp       the time-stamp in seconds or milliseconds
	 * 
	 * @return ResponseEntity
	 */
	@GetMapping("/media/all-files")
	public void getAllMediaCombinedData(HttpServletResponse httpServletResponse,
			@RequestParam(required = false) String apiKey,
			@RequestParam(required = false, defaultValue = "0") long unixTimestamp) {
		if (apiKey == null) {
			throw new InvalidApiKeyException(ErrorConstants.REQUIRED_API_KEY,
					"Api Key is required to access this Resource");
		}
//		if (resourceDao.isApiKeyExists(apiKey)) {
//
//			resourceService.getMediaFile(httpServletResponse, unixTimestamp);
//
//		} else {
//			throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
//		}
	}// getAllMediaCombinedData

	/**
	 * 
	 * @param apiKey
	 */
	@GetMapping("/media-cron")
	public void mediaCron(@RequestParam(required = false) String apiKey) {

		if (apiKey == null) {
			throw new InvalidApiKeyException(ErrorConstants.REQUIRED_API_KEY,
					"Api Key is required to access this Resource");
		}

//		if (apiKey != null && resourceDao.isApiKeyExists(apiKey)) {
//			resourceService.getMediaDataCron();
//		} else {
//			throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
//		}

	}// mediaCron

	@GetMapping("/data/village/tehsil/{tehsilCode}")
	public ResponseEntity<String> getVillageDataByTehsil(@RequestParam(required = false) String apiKey,
			@PathVariable int tehsilCode, @RequestParam(required = false, defaultValue = "0") long unixTimestamp) {

		if (apiKey == null) {
			throw new InvalidApiKeyException(ErrorConstants.REQUIRED_API_KEY,
					"Api Key is required to access this Resource");
		}
//
//		if (resourceDao.isApiKeyExists(apiKey)) {
//			String response = resourceService.getVillageDataByTehsil(tehsilCode, unixTimestamp);
//			return new ResponseEntity<>(response, HttpStatus.OK);
//		} else {
//			throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
//		}
		return null;
	}// getVillageDataByTehsil

	@GetMapping("/data/village/district/{districtCode}")
	public ResponseEntity<String> getVillageDataByDistrict(@RequestParam(required = false) String apiKey,
			@PathVariable int districtCode, @RequestParam(required = false, defaultValue = "0") long unixTimestamp) {

		if (apiKey == null) {
			throw new InvalidApiKeyException(ErrorConstants.REQUIRED_API_KEY,
					"Api Key is required to access this Resource");
		}

//		if (resourceDao.isApiKeyExists(apiKey)) {
//			String response = resourceService.getVillageDataByDistrict(districtCode, unixTimestamp);
//			return new ResponseEntity<>(response, HttpStatus.OK);
//		} else {
//			throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
//		}
		return null;
	}// getVillageDataByDistrict

	@GetMapping("/data/village/panchayat/{panchayatCode}")
	public ResponseEntity<String> getVillageDataByPanchayat(@RequestParam(required = false) String apiKey,
			@PathVariable int panchayatCode, @RequestParam(required = false, defaultValue = "0") long unixTimestamp) {

		if (apiKey == null) {
			throw new InvalidApiKeyException(ErrorConstants.REQUIRED_API_KEY,
					"Api Key is required to access this Resource");
		}
//
//		if (resourceDao.isApiKeyExists(apiKey)) {
//			String response = resourceService.getVillageDataByPanchayat(panchayatCode, unixTimestamp);
//			return new ResponseEntity<>(response, HttpStatus.OK);
//		} else {
//			throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
//		}
		return null;
	}// getVillageDataByDistrict

	@GetMapping("/data/panchayat/district/{districtCode}")
	public ResponseEntity<String> getPanchayatDataByDistrict(@RequestParam(required = false) String apiKey,
			@PathVariable int districtCode, @RequestParam(required = false, defaultValue = "0") long unixTimestamp) {

		if (apiKey == null) {
			throw new InvalidApiKeyException(ErrorConstants.REQUIRED_API_KEY,
					"Api Key is required to access this Resource");
		}
//
//		if (resourceDao.isApiKeyExists(apiKey)) {
//			String response = resourceService.getPanchayatDataByDistrict(districtCode, unixTimestamp);
//			return new ResponseEntity<>(response, HttpStatus.OK);
//		} else {
//			throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
//		}
		return null;
	}// getPanchayatDataByDistrict

	@GetMapping("/data/panchayat/tehsil/{tehsilCode}")
	public ResponseEntity<String> getPanchayatDataByTehsil(@RequestParam(required = false) String apiKey,
			@PathVariable int tehsilCode, @RequestParam(required = false, defaultValue = "0") long unixTimestamp) {

		if (apiKey == null) {
			throw new InvalidApiKeyException(ErrorConstants.REQUIRED_API_KEY,
					"Api Key is required to access this Resource");
		}
//		if (resourceDao.isApiKeyExists(apiKey)) {
//			String response = resourceService.getPanchayatDataByTehsil(tehsilCode, unixTimestamp);
//			return new ResponseEntity<>(response, HttpStatus.OK);
//		} else {
//			throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
//		}
return null;
	}// getPanchayatDataByTehsil

}
