package in.cropdata.farmers.app.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import in.cropdata.farmers.app.masters.dto.CityVillagePinDTO;
import in.cropdata.farmers.app.masters.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import in.cropdata.farmers.app.DTO.StateDTO;
import in.cropdata.farmers.app.constants.ErrorConstants;
import in.cropdata.farmers.app.exception.AuthTokenMissing;
import in.cropdata.farmers.app.exception.DoesNotExistException;
import in.cropdata.farmers.app.exception.InvalidApiKeyException;
import in.cropdata.farmers.app.exception.InvalidAppKeyException;
import in.cropdata.farmers.app.properties.AppProperties;
import in.cropdata.farmers.app.service.MasterDataService;
import in.cropdata.farmers.app.utils.FarmerAppUtils;
import in.cropdata.farmers.app.utils.JwtTokenUtil;

@RestController
@RequestMapping(value = "/master")
public class MasterDataController {

	@Autowired
	private AppProperties appProperties;

	@Autowired
	private MasterDataService masterService;

	@Autowired
	private FarmerAppUtils farmerAppUtils;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@PostMapping("/get-case-masters")
	public Map<String, Object> getAllcommodityWaterSouceLandOwnershipDoc(@RequestParam("apiKey") String apiKey,
			@RequestBody Map<String,Object> _data, @RequestHeader("appKey") String appKey) throws IOException {
		if (apiKey == null || apiKey.isEmpty()) {
			throw new InvalidApiKeyException(ErrorConstants.API_KEY_IS_REQUIRED,
					"Api Key is required to access this Resource");
		} else {
			if (apiKey.equals(appProperties.getApiKeyProperty())) {
				if (farmerAppUtils.matchAppKey(appKey)) {
					return masterService.getAllCommodityWaterSourceLandOwnershipDoc(_data);
				} else {
					throw new InvalidAppKeyException(ErrorConstants.INVALID_APP_KEY, "Invalid APP Key");
				}
			} else {
				throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
			}
		}
	}

	@GetMapping("/get-all-variety")
	public ResponseEntity<Object> getAllVariety(@RequestParam("apiKey") String apiKey,
			@RequestParam("zonalCommodityID") Integer zonalCommodityID, @RequestHeader("appKey") String appKey)
			throws IOException {
		if (apiKey == null || apiKey.isEmpty()) {
			throw new InvalidApiKeyException(ErrorConstants.API_KEY_IS_REQUIRED,
					"Api Key is required to access this Resource");
		} else {
			if (apiKey.equals(appProperties.getApiKeyProperty())) {
				if (farmerAppUtils.matchAppKey(appKey)) {
					return new ResponseEntity<>(masterService.getAllVarietyByCommodityIdAndStateCode(zonalCommodityID),
							HttpStatus.OK);
				} else {
					throw new InvalidAppKeyException(ErrorConstants.INVALID_APP_KEY, "Invalid APP Key");
				}
			} else {
				throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
			}
		}
	}

	@GetMapping("/get-all-geo-states")
	public ResponseEntity<Object> getAllgeoStates(@RequestParam("apiKey") String apiKey,
			@RequestHeader("appKey") String appKey) throws IOException {

		if (apiKey == null || apiKey.isEmpty()) {
			throw new InvalidApiKeyException(ErrorConstants.API_KEY_IS_REQUIRED,
					"Api Key is required to access this Resource");
		} else {
			if (apiKey.equals(appProperties.getApiKeyProperty())) {
				if (farmerAppUtils.matchAppKey(appKey)) {
					return new ResponseEntity<>(masterService.getAllgeoStates(), HttpStatus.OK);
				} else {
					throw new InvalidAppKeyException(ErrorConstants.INVALID_APP_KEY, "Invalid APP Key");
				}
			} else {
				throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
			}
		}
	}

	@GetMapping("/get-tehsil-and-cities")
	public ResponseEntity<Object> getTehsil(@RequestParam int districtCode, @RequestHeader("appKey") String appKey,
			@RequestParam String apiKey) {

		if (apiKey == null || apiKey.isBlank()) {
			throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "ApiKey is required");
		} else {
			if (apiKey.equals(appProperties.getApiKeyProperty())) {
				if (farmerAppUtils.matchAppKey(appKey)) {
					return new ResponseEntity<Object>(masterService.getTehsilByDistrictCode(districtCode),
							HttpStatus.OK);
				} else {
					throw new InvalidAppKeyException(ErrorConstants.INVALID_APP_KEY, "Invalid APP Key");
				}
			} else {
				throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
			}
		}

	}

	@GetMapping(value = "/district")
	public ResponseEntity<Object> getDistrictListByStateCode(@RequestParam Integer stateCode,
			@RequestHeader("appKey") String appKey, @RequestParam String apiKey) {
		if (apiKey == null || apiKey.isBlank()) {
			throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "ApiKey is required");
		} else {
			if (apiKey.equals(appProperties.getApiKeyProperty())) {
				if (farmerAppUtils.matchAppKey(appKey)) {
					return new ResponseEntity<>(masterService.getDistrictListByStateCode(stateCode), HttpStatus.OK);
				} else {
					throw new InvalidAppKeyException(ErrorConstants.INVALID_APP_KEY, "Invalid APP Key");
				}
			} else {
				throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
			}
		}
	}

	/**
	 * It is use to get list of panchayat and village base on tehsil code
	 * 
	 * @param tehsilCode it is hold tehsilCode
	 * @param apiKey     it is hold apiKey
	 * @param appKey     it is hold appKey
	 */

	@GetMapping("/panchayat")
	public ResponseEntity<Object> getPanchayatByTehsil(@RequestParam Integer tehsilCode,
			@RequestHeader("appKey") String appKey, @RequestParam String apiKey) {
		Map<String, Object> stringObjectMap = null;
		if (apiKey == null || apiKey.isBlank()) {
			throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "ApiKey is required");
		} else {
			if (apiKey.equals(appProperties.getApiKeyProperty())) {
				if (farmerAppUtils.matchAppKey(appKey)) {
					stringObjectMap = new HashMap<>(masterService.getPanchayatByTehsil(tehsilCode));
					return new ResponseEntity<>(stringObjectMap, HttpStatus.OK);

				} else {
					throw new InvalidAppKeyException(ErrorConstants.INVALID_APP_KEY, "Invalid APP Key");
				}
			} else {
				throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
			}
		}

	}

	@GetMapping("/village")
	public ResponseEntity<Object> getVillageByPanchayat(@RequestParam Integer panchayatCode,
			@RequestHeader("appKey") String appKey, @RequestParam String apiKey) {
		Map<String, Object> stringObjectMap = null;
		if (apiKey == null || apiKey.isBlank()) {
			throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "ApiKey is required");
		} else {
			if (apiKey.equals(appProperties.getApiKeyProperty())) {
				if (farmerAppUtils.matchAppKey(appKey)) {
					stringObjectMap = new HashMap<>(masterService.getVillageByPanchayat(panchayatCode));
					return new ResponseEntity<>(stringObjectMap, HttpStatus.OK);

				} else {
					throw new InvalidAppKeyException(ErrorConstants.INVALID_APP_KEY, "Invalid APP Key");
				}
			} else {
				throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
			}
		}

	}

	@GetMapping("/getAllCommodityByDistrict")
	public Map<String, Object> getAllcommodityByDistrictCode(HttpServletRequest request,
			@RequestParam("apiKey") String apiKey, @RequestParam("districtCode") Integer districtCode,
			@RequestHeader("appKey") String appKey) throws IOException {
		if (apiKey == null || apiKey.isEmpty()) {
			throw new InvalidApiKeyException(ErrorConstants.API_KEY_IS_REQUIRED,
					"Api Key is required to access this Resource");
		} else {
			if (apiKey.equals(appProperties.getApiKeyProperty())) {
				if (farmerAppUtils.matchAppKey(appKey)) {

					String authToken = jwtTokenUtil.getAuthToken(request);

					if (authToken != null && !authToken.isEmpty()) {
						return masterService.getAllCommodityByDistrict(districtCode,authToken);
					} else {
						throw new AuthTokenMissing(ErrorConstants.MISSING_TOKEN, "Authorization Token missing");
					}

				} else {
					throw new InvalidAppKeyException(ErrorConstants.INVALID_APP_KEY, "Invalid APP Key");
				}
			} else {
				throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
			}
		}
	}

	@GetMapping("/City/Village")
	public Map<String, Object> searchCityVillagePin(@RequestParam("apiKey") String apiKey,
			@RequestParam("searchedCharacter") String searchedCharacter,
			@RequestBody(required = false) GeoVillage geoVillage, @RequestHeader("appKey") String appKey)
			throws IOException {
		if (apiKey == null || apiKey.isEmpty()) {
			throw new InvalidApiKeyException(ErrorConstants.API_KEY_IS_REQUIRED,
					"Api Key is required to access this Resource");
		} else {
			if (apiKey.equals(appProperties.getApiKeyProperty())) {
				if (farmerAppUtils.matchAppKey(appKey)) {
					return masterService.getSearchList(searchedCharacter);
				} else {
					throw new InvalidAppKeyException(ErrorConstants.INVALID_APP_KEY, "Invalid APP Key");
				}
			} else {
				throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
			}
		}
	}

}
