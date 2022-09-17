package com.drk.tools.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Map;

import com.drk.tools.constants.ApplicationConstants;
import com.drk.tools.util.DrkUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.drk.tools.config.AppConfig;
import com.drk.tools.constants.ErrorConstants;
import com.drk.tools.dto.PyPayLoadBody;
import com.drk.tools.dto.PyResponseDTO;
import com.drk.tools.dto.ResponseMessage;
import com.drk.tools.exceptions.InvalidApiKeyException;
import com.drk.tools.exceptions.InvalidAppKeyException;
import com.drk.tools.service.SyncDBService;

@RestController
@RequestMapping("/drkTools")
public class SyncDBController {

	private static final Logger LOGGER = LoggerFactory.getLogger(SyncDBController.class);

	@Autowired
	SyncDBService syncDBService;

	@Autowired
	AppConfig appConfig;

	@Autowired
	DrkUtil drkUtils;

	@GetMapping("/sync-db")
	public ResponseEntity<Map<String, Object>> getAndpushFiles(
			@RequestParam(required = false, defaultValue = "0") String syncTime, @RequestParam String type,
			@RequestParam String apiKey, @RequestHeader("appKey") String appKey) throws SQLException, IOException {

		if (drkUtils.checkNull(apiKey)) {
			throw new InvalidApiKeyException(ErrorConstants.REQUIRED_API_KEY, ApplicationConstants.RESPONSE_REQUIRED_APIKEY);
		}
		if (drkUtils.validateApiKey(apiKey)) {

			if (syncDBService.matchAppKey(appKey)) {
				return syncDBService.serverUpload(type, syncTime);
			} else {
				throw new InvalidAppKeyException(ErrorConstants.INVALID_APP_KEY, ApplicationConstants.RESPONSE_INVALID_APIKEY);
			}
		} else {
			throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, ApplicationConstants.RESPONSE_INVALID_APIKEY);
		}
	}

	@GetMapping("/getAllRegion")
	public Map<String, Object> getAllRegion(@RequestParam String apiKey, @RequestHeader("appKey") String appKey)
			throws SQLException, IOException {

		if (drkUtils.checkNull(apiKey)) {
			throw new InvalidApiKeyException(ErrorConstants.REQUIRED_API_KEY,
					ApplicationConstants.RESPONSE_REQUIRED_APIKEY);
		}
		if (drkUtils.validateApiKey(apiKey)) {
			if (syncDBService.matchAppKey(appKey)) {
				return syncDBService.getAllRegion();
			} else {
				throw new InvalidAppKeyException(ErrorConstants.INVALID_APP_KEY, ApplicationConstants.RESPONSE_INVALID_APIKEY);
			}
		} else {
			throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
		}
	}

	@GetMapping("/getAllVillagesByRegion")
	public Map<String, Object> getAllVillagesByRegion(@RequestParam String apiKey,
			@RequestHeader("appKey") String appKey, @RequestParam Integer regionID) throws SQLException, IOException {

		if (drkUtils.checkNull(apiKey)) {
			throw new InvalidApiKeyException(ErrorConstants.REQUIRED_API_KEY,
					ApplicationConstants.RESPONSE_REQUIRED_APIKEY);
		}
		if (drkUtils.validateApiKey(apiKey)) {

			if (syncDBService.matchAppKey(appKey)) {
				return syncDBService.getAllVillagesByRegion(regionID);
			} else {
				throw new InvalidAppKeyException(ErrorConstants.INVALID_APP_KEY, ApplicationConstants.RESPONSE_INVALID_APIKEY);
			}
		} else {
			throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
		}
	}

	@GetMapping("/getAllRoles")
	public Map<String, Object> getAllRoles(@RequestParam String apiKey, @RequestHeader("appKey") String appKey)
			throws SQLException, IOException {

		if (drkUtils.checkNull(apiKey)) {
			throw new InvalidApiKeyException(ErrorConstants.REQUIRED_API_KEY,
					ApplicationConstants.RESPONSE_REQUIRED_APIKEY);
		}
		if (drkUtils.validateApiKey(apiKey)) {
			if (syncDBService.matchAppKey(appKey)) {
				return syncDBService.getAllRoles();
			} else {
				throw new InvalidAppKeyException(ErrorConstants.INVALID_APP_KEY, ApplicationConstants.RESPONSE_INVALID_APIKEY);
			}
		} else {
			throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
		}
	}

	@GetMapping("/getScoutByRole")
	public Map<String, Object> getScoutByRole(@RequestParam String apiKey, @RequestHeader("appKey") String appKey,
			@RequestParam Integer roleID) throws SQLException, IOException {

		if (drkUtils.checkNull(apiKey)) {
			throw new InvalidApiKeyException(ErrorConstants.REQUIRED_API_KEY,
					ApplicationConstants.RESPONSE_REQUIRED_APIKEY);
		}
		if (drkUtils.validateApiKey(apiKey)) {
			if (syncDBService.matchAppKey(appKey)) {
				return syncDBService.getScoutByRole(roleID);
			} else {
				throw new InvalidAppKeyException(ErrorConstants.INVALID_APP_KEY, ApplicationConstants.RESPONSE_INVALID_APIKEY);
			}
		} else {
			throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
		}
	}

	@GetMapping("/getVillagesByScout")
	public Map<String, Object> getVillagesByScout(@RequestParam String apiKey, @RequestHeader("appKey") String appKey,
			@RequestParam Integer scoutID, @RequestParam Integer regionID) throws SQLException, IOException {

		if (drkUtils.checkNull(apiKey)) {
			throw new InvalidApiKeyException(ErrorConstants.REQUIRED_API_KEY,
					ApplicationConstants.RESPONSE_REQUIRED_APIKEY);
		}
		if (drkUtils.validateApiKey(apiKey)) {
			if (syncDBService.matchAppKey(appKey)) {
				return syncDBService.getVillagesByScout(scoutID, regionID);
			} else {
				throw new InvalidAppKeyException(ErrorConstants.INVALID_APP_KEY, ApplicationConstants.RESPONSE_INVALID_APIKEY);
			}
		} else {
			throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
		}
	}

	@GetMapping("/getPanchayatByScout")
	public Map<String, Object> getPanchayatByScout(@RequestParam String apiKey, @RequestHeader("appKey") String appKey,
			@RequestParam Integer scoutID) throws SQLException, IOException {

		if (drkUtils.checkNull(apiKey)) {
			throw new InvalidApiKeyException(ErrorConstants.REQUIRED_API_KEY,
					ApplicationConstants.RESPONSE_REQUIRED_APIKEY);
		}
		if (drkUtils.validateApiKey(apiKey)) {
			if (syncDBService.matchAppKey(appKey)) {
				return syncDBService.getPanchayatByScout(scoutID);
			} else {
				throw new InvalidAppKeyException(ErrorConstants.INVALID_APP_KEY, ApplicationConstants.RESPONSE_INVALID_APIKEY);
			}
		} else {
			throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
		}
	}

	@GetMapping("/getAllFarmersAndPOIByVillage")
	public Map<String, Object> getAllFarmersAndPOIByVillage(@RequestParam String apiKey,
			@RequestHeader("appKey") String appKey, @RequestParam Integer villageID) throws SQLException, IOException {

		if (drkUtils.checkNull(apiKey)) {
			throw new InvalidApiKeyException(ErrorConstants.REQUIRED_API_KEY,
					ApplicationConstants.RESPONSE_REQUIRED_APIKEY);
		}
		if (drkUtils.validateApiKey(apiKey)) {
			if (syncDBService.matchAppKey(appKey)) {
				return syncDBService.getAllFarmersByVillage(villageID);
			} else {
				throw new InvalidAppKeyException(ErrorConstants.INVALID_APP_KEY, ApplicationConstants.RESPONSE_INVALID_APIKEY);
			}
		} else {
			throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
		}
	}

	@GetMapping("/getFarmerLatLong")
	public Map<String, Object> getFarmerLatLong(@RequestParam String apiKey, @RequestHeader("appKey") String appKey,
			@RequestParam String farmerID) throws SQLException, IOException {

		if (drkUtils.checkNull(apiKey)) {
			throw new InvalidApiKeyException(ErrorConstants.REQUIRED_API_KEY,
					ApplicationConstants.RESPONSE_REQUIRED_APIKEY);
		}
		if (drkUtils.validateApiKey(apiKey)) {
			if (syncDBService.matchAppKey(appKey)) {
				return syncDBService.getFarmerLatLong(farmerID);
			} else {
				throw new InvalidAppKeyException(ErrorConstants.INVALID_APP_KEY, ApplicationConstants.RESPONSE_INVALID_APIKEY);
			}
		} else {
			throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
		}
	}

	@GetMapping("/uploadImages")
	public Map<String, Object> uploadimages(@RequestParam String syncTime, @RequestHeader("appKey") String appKey,
			@RequestParam(required = false) String apiKey) throws SQLException, IOException {

		if (drkUtils.checkNull(apiKey)) {
			throw new InvalidApiKeyException(ErrorConstants.REQUIRED_API_KEY,
					ApplicationConstants.RESPONSE_REQUIRED_APIKEY);
		}

		if (drkUtils.validateApiKey(apiKey)) {
			if (syncDBService.matchAppKey(appKey)) {
				return syncDBService.uploadimages(syncTime);

			} else {
				throw new InvalidAppKeyException(ErrorConstants.INVALID_APP_KEY, ApplicationConstants.RESPONSE_INVALID_APIKEY);
			}
		} else {
			throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
		}
	}

	@PostMapping("/spot-priority")
	public ResponseEntity<?> spotPriority(@RequestHeader("appKey") String appKey,
			@RequestParam(required = false) String apiKey, @RequestBody PyPayLoadBody pyPayLoadBody) {

		LOGGER.info("pyPayLoadBody " + pyPayLoadBody);

		if (drkUtils.checkNull(apiKey)) {
			throw new InvalidApiKeyException(ErrorConstants.REQUIRED_API_KEY,
					ApplicationConstants.RESPONSE_REQUIRED_APIKEY);
		}

		if (drkUtils.validateApiKey(apiKey)) {
			if (syncDBService.matchAppKey(appKey)) {

				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
				HttpEntity<PyPayLoadBody> request = new HttpEntity<>(pyPayLoadBody, headers);
				RestTemplate restTemplate = new RestTemplate();
				PyResponseDTO response = restTemplate.postForObject(appConfig.getGstmPyUri(), request,
						PyResponseDTO.class);

				return ResponseEntity.status(HttpStatus.OK).body(response);

			} else {
				throw new InvalidAppKeyException(ErrorConstants.INVALID_APP_KEY, ApplicationConstants.RESPONSE_INVALID_APIKEY);
			}
		} else {
			throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
		}

	}

	@GetMapping("/delete-farmar-lat-long")
	public ResponseMessage deleteFarmarLatLong() {
		LOGGER.info("delete farmar lat long cron running....");
		return syncDBService.deleteFarmarLatLong();
	}
	
}