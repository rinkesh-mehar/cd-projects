/**
 * 
 */
package in.cropdata.farmers.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import in.cropdata.farmers.app.DTO.FarmerRegistrationDTO;
import in.cropdata.farmers.app.constants.ErrorConstants;
import in.cropdata.farmers.app.exception.InvalidApiKeyException;
import in.cropdata.farmers.app.exception.InvalidAppKeyException;
import in.cropdata.farmers.app.model.ResponseMessage;
import in.cropdata.farmers.app.properties.AppProperties;
import in.cropdata.farmers.app.service.FarmerRegistrationService;
import in.cropdata.farmers.app.utils.FarmerAppUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author cropdata-Aniket Naik
 *
 */

@RestController
@RequestMapping("/app/farmers")
public class FarmersRegistrationController {

	@Autowired
	private FarmerRegistrationService farmerRegistrationService;

	@Autowired
	private AppProperties appProp;

	@Autowired
	private FarmerAppUtils appUtils;

	@PostMapping(value = "/registration")
	public ResponseEntity<ResponseMessage> registerFarmerDetails(@RequestBody FarmerRegistrationDTO farmers,
			@RequestHeader("appKey") String appKey, @RequestParam String apiKey) {
		ResponseMessage response = null;
		
		
		if (apiKey == null || apiKey.isBlank()) {
			throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "ApiKey is required");
		} else {
			if (apiKey.equals(appProp.getApiKeyProperty())) {
				if (appUtils.matchAppKey(appKey)) {
				   response = farmerRegistrationService.registerFarmer(farmers);
					return new ResponseEntity<>(response, HttpStatus.OK);
				} else {
					throw new InvalidAppKeyException(ErrorConstants.INVALID_APP_KEY, "Invalid APP Key");
				}
			} else {
				throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
			}
		}

	}

	/** Find DRK Farmer already exist -CDT-Ujwal*/

	@RequestMapping(path = "/farmerExist", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> checkDrkrishiFarmer(@RequestParam("primaryMobileNumber") String mobileNumber,
									  @RequestParam("regionId") Integer regionId,@RequestParam String apiKey) {
		Map<String, String> map = new HashMap<>();
		if (apiKey == null || apiKey.isBlank()) {
			throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "ApiKey is required");
		} else {
			if (mobileNumber != null && !(mobileNumber.equals(""))) {
				if (regionId != null) {

					return farmerRegistrationService.checkFarmerExist(mobileNumber, regionId);
				} else {
					map.put("success", "false");
					map.put("status", "404");
					map.put("data", null);
					return new ResponseEntity<>(map, HttpStatus.OK);
				}
			} else {
				return new ResponseEntity<>(map, HttpStatus.OK);
			}
		}
	}

}
