/**
 * 
 */
package in.cropdata.farmers.app.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import in.cropdata.farmers.app.constants.APIConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.cropdata.farmers.app.DTO.FarmerLatLongDTO;
import in.cropdata.farmers.app.constants.ErrorConstants;
import in.cropdata.farmers.app.drk.model.DrkCaseCropInfo;
import in.cropdata.farmers.app.drk.model.FarmerKYC;
import in.cropdata.farmers.app.drk.model.FarmerLoginOtp;
import in.cropdata.farmers.app.exception.AuthTokenMissing;
import in.cropdata.farmers.app.exception.InvalidApiKeyException;
import in.cropdata.farmers.app.exception.InvalidAppKeyException;
import in.cropdata.farmers.app.exception.MissingDeviceToken;
import in.cropdata.farmers.app.masters.model.vo.ImageVO;
import in.cropdata.farmers.app.properties.AppProperties;
import in.cropdata.farmers.app.service.FarmerAppService;
import in.cropdata.farmers.app.service.FarmerLoginService;
import in.cropdata.farmers.app.service.MasterDataService;
import in.cropdata.farmers.app.service.NotificationService;
import in.cropdata.farmers.app.utils.FarmerAppUtils;
import in.cropdata.farmers.app.utils.JwtTokenUtil;

/**
 * @author cropdata-Aniket Naik
 *
 */
@RestController
@RequestMapping(value = "/basicFarmer")
public class FarmersAppController {

	private static final Logger logger = LoggerFactory.getLogger(FarmersAppController.class);

	@Autowired
	private FarmerLoginService farmerLoginService;

	@Autowired
	private AppProperties appProperties;

	@Autowired
	private FarmerAppUtils farmerAppUtils;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private FarmerAppService farmerAppService;

	@Autowired
	private MasterDataService masterDataService;

	@Autowired
	private NotificationService notificationService;

	@PostMapping("/getOTP")
	public Map<String, Object> getOTP(@RequestBody FarmerLoginOtp details, @RequestHeader("appKey") String appKey,
			@RequestParam("apiKey") String apiKey) throws IOException {

		if (apiKey == null || apiKey.isEmpty()) {
			throw new InvalidApiKeyException(ErrorConstants.API_KEY_IS_REQUIRED,
					"Api Key is required to access this Resource");
		} else {
			if (apiKey.equals(appProperties.getApiKeyProperty())) {
				if (farmerAppUtils.matchAppKey(appKey)) {
					return farmerLoginService.getOTP(details.getMobileNo());
				} else {
					throw new InvalidAppKeyException(ErrorConstants.INVALID_APP_KEY, "Invalid APP Key");
				}
			} else {
				throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
			}
		}
	}

	@PostMapping("/validateOTP")
	private Map<String, Object> validateOTP(@RequestBody FarmerLoginOtp details, @RequestParam("apiKey") String apiKey,
			@RequestHeader("appKey") String appKey) throws Exception {

		if (apiKey == null || apiKey.isEmpty()) {
			throw new InvalidApiKeyException(ErrorConstants.API_KEY_IS_REQUIRED,
					"Api Key is required to access this Resource");
		} else {
			if (apiKey.equals(appProperties.getApiKeyProperty())) {
				if (farmerAppUtils.matchAppKey(appKey)) {
					return farmerLoginService.validateOTP(details);
				} else {
					throw new InvalidAppKeyException(ErrorConstants.INVALID_APP_KEY, "Invalid APP Key");
				}
			} else {
				throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
			}
		}
	}
	
	
	@PostMapping("/saveDeviceToken")
//	@RequestMapping(value = "",method = RequestMethod.POST,produces = "")
	public Map<String, Object> saveDeviceToken(HttpServletRequest request, @RequestHeader("appKey") String appKey,
			@RequestBody Map<String, String> _data, @RequestParam("apiKey") String apiKey) {
		if (apiKey == null || apiKey.isEmpty()) {
			throw new InvalidApiKeyException(ErrorConstants.API_KEY_IS_REQUIRED,
					"Api Key is required to access this Resource");
		}
		if (apiKey != null && apiKey.equals(appProperties.getApiKeyProperty())) {
			if (appKey != null && !appKey.isEmpty() && farmerAppUtils.matchAppKey(appKey)) {

				if (_data.get("deviceToken") == null || _data.get("deviceToken").isEmpty()) {
					throw new MissingDeviceToken(ErrorConstants.MISSING_DEVICE_TOKEN, "Missing Device Token");
				} else {
					String authToken = jwtTokenUtil.getAuthToken(request);
//					String authToken = auth.substring(7);
					logger.info("authToken {}",authToken);
					
					if (authToken != null && !authToken.isEmpty()) {
						return farmerLoginService.saveDeviceToken(_data.get("deviceToken"), authToken);
					} else {
						throw new AuthTokenMissing(ErrorConstants.MISSING_TOKEN, "Authorization Token missing");
					}
				}
			} else {
				throw new InvalidApiKeyException(ErrorConstants.API_KEY_IS_REQUIRED,
						"Appkey is required to access this Resource");
			}
		} else {
			throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
		}
	}

	@GetMapping("/kyc-document")
	public Map<String, Object> getKYCDocument(HttpServletRequest request, @RequestHeader("appKey") String appKey,
			@RequestParam("apiKey") String apiKey) {
		if (apiKey == null) {
			throw new InvalidApiKeyException(ErrorConstants.API_KEY_IS_REQUIRED,
					"Api Key is required to access this Resource");
		}
		if (apiKey != null && apiKey.equals(appProperties.getApiKeyProperty())) {
			if (appKey != null && farmerAppUtils.matchAppKey(appKey)) {

				return farmerAppService.getKYCDocument(request);
			} else {
				throw new InvalidApiKeyException(ErrorConstants.API_KEY_IS_REQUIRED,
						"Appkey is required to access this Resource");
			}
		} else {
			throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
		}
	}

	@GetMapping("/case")
	public Map<String, Object> getCaseListing(HttpServletRequest request, @RequestHeader("appKey") String appKey,
			@RequestParam("apiKey") String apiKey) {
		if (apiKey == null) {
			throw new InvalidApiKeyException(ErrorConstants.API_KEY_IS_REQUIRED,
					"Api Key is required to access this Resource");
		}
		if (apiKey != null && apiKey.equals(appProperties.getApiKeyProperty())) {
			if (appKey != null && farmerAppUtils.matchAppKey(appKey)) {
				String authToken = jwtTokenUtil.getAuthToken(request);

				if (authToken != null && !authToken.isEmpty()) {
					return farmerAppService.getCaseListing(authToken);
				} else {
					throw new AuthTokenMissing(ErrorConstants.MISSING_TOKEN, "Authorization Token missing");
				}
			} else {
				throw new InvalidApiKeyException(ErrorConstants.API_KEY_IS_REQUIRED,
						"Appkey is required to access this Resource");
			}
		} else {
			throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
		}
	}

	@PostMapping("/upload-image")
	public Map<String, Object> uploadImage(HttpServletRequest request, @RequestHeader("appKey") String appKey,
			@ModelAttribute ImageVO image, @RequestParam("apiKey") String apiKey) {
		if (apiKey == null) {
			throw new InvalidApiKeyException(ErrorConstants.API_KEY_IS_REQUIRED,
					"Api Key is required to access this Resource");
		}
		if (apiKey != null && apiKey.equals(appProperties.getApiKeyProperty())) {
			if (appKey != null && farmerAppUtils.matchAppKey(appKey)) {
				return farmerAppService.uploadImage(image.getImage());

			} else {
				throw new InvalidApiKeyException(ErrorConstants.API_KEY_IS_REQUIRED,
						"Appkey is required to access this Resource");
			}
		} else {
			throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
		}
	}

	@PostMapping("/update-profile")
	public Map<String, Object> updateProfile(HttpServletRequest request, @RequestHeader("appKey") String appKey,
			@RequestBody FarmerKYC farmerKYC, @RequestParam("apiKey") String apiKey) {
		if (apiKey == null) {
			throw new InvalidApiKeyException(ErrorConstants.API_KEY_IS_REQUIRED,
					"Api Key is required to access this Resource");
		}
		if (apiKey != null && apiKey.equals(appProperties.getApiKeyProperty())) {
			if (appKey != null && farmerAppUtils.matchAppKey(appKey)) {
				
				String authToken = jwtTokenUtil.getAuthToken(request);
				
				if (authToken != null && !authToken.isEmpty()) {
					return farmerAppService.updateProfile(authToken, farmerKYC);
				} else {
					throw new AuthTokenMissing(ErrorConstants.MISSING_TOKEN, "Authorization Token missing");
				}
			} else {
				throw new InvalidApiKeyException(ErrorConstants.API_KEY_IS_REQUIRED,
						"Appkey is required to access this Resource");
			}
		} else {
			throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
		}
	}

	@GetMapping("/get-case-by-id")
	public Map<String, Object> getCaseById(@RequestParam(required = true, name = "ID") String ID,
			HttpServletRequest request, @RequestHeader("appKey") String appKey, @RequestParam("apiKey") String apiKey) {
		if (apiKey == null) {
			throw new InvalidApiKeyException(ErrorConstants.API_KEY_IS_REQUIRED,
					"Api Key is required to access this Resource");
		}
		if (apiKey != null && apiKey.equals(appProperties.getApiKeyProperty())) {
			if (appKey != null && farmerAppUtils.matchAppKey(appKey)) {

				return farmerAppService.getCaseById(ID, request);
			} else {
				throw new InvalidApiKeyException(ErrorConstants.API_KEY_IS_REQUIRED,
						"Appkey is required to access this Resource");
			}
		} else {
			throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
		}
	}

	@PostMapping("/add-case")
	public Map<String, Object> addCase(HttpServletRequest request, @RequestHeader("appKey") String appKey,
			@RequestParam("apiKey") String apiKey, @RequestBody DrkCaseCropInfo caseCropInfo) {
		if (apiKey == null) {
			throw new InvalidApiKeyException(ErrorConstants.API_KEY_IS_REQUIRED,
					"Api Key is required to access this Resource");
		}
		if (apiKey.equals(appProperties.getApiKeyProperty())) {
			if (appKey != null && farmerAppUtils.matchAppKey(appKey)) {
				String authToken = jwtTokenUtil.getAuthToken(request);
				if (authToken != null && !authToken.isEmpty()) {
					return farmerAppService.addCase(authToken, caseCropInfo);
				} else {
					throw new AuthTokenMissing(ErrorConstants.MISSING_TOKEN, "Authorization Token missing");
				}
			} else {
				throw new InvalidApiKeyException(ErrorConstants.API_KEY_IS_REQUIRED,
						"Appkey is required to access this Resource");
			}
		} else {
			throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
		}
	}

	@PostMapping("/logout")
	Map<String, Object> updateForlogout(HttpServletRequest request, @RequestHeader(required = false) String appKey,
			@RequestParam("apiKey") String apiKey) {
		if (apiKey == null) {
			throw new InvalidApiKeyException(ErrorConstants.API_KEY_IS_REQUIRED,
					"Api Key is required to access this Resource");
		}
		if (apiKey != null && apiKey.equals(appProperties.getApiKeyProperty())) {
			if (appKey != null && !appKey.isEmpty() && farmerAppUtils.matchAppKey(appKey)) {

				String authToken = jwtTokenUtil.getAuthToken(request);

				if (authToken != null && !appKey.isEmpty()) {
					return farmerLoginService.farmerLogout(authToken);
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

	@GetMapping("/getFarmerProfile")
	public ResponseEntity<Object> getFarmerProfile(HttpServletRequest request, @RequestHeader("appKey") String appKey,
			@RequestParam("apiKey") String apiKey) {

		if (apiKey == null) {
			throw new InvalidApiKeyException(ErrorConstants.API_KEY_IS_REQUIRED,
					"Api Key is required to access this Resource");
		}
		if (apiKey != null && apiKey.equals(appProperties.getApiKeyProperty())) {
			if (appKey != null && !appKey.isEmpty() && farmerAppUtils.matchAppKey(appKey)) {

				String authToken = jwtTokenUtil.getAuthToken(request);

				if (authToken != null && !appKey.isEmpty()) {
					return new ResponseEntity<>(farmerLoginService.getFarmerProfile(authToken), HttpStatus.OK);
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

	@GetMapping("/id-proof")
	public ResponseEntity<Object> getFarmerIdProof(@RequestHeader("appKey") String appKey,
			@RequestParam("apiKey") String apiKey) {
		if (apiKey == null) {
			throw new InvalidApiKeyException(ErrorConstants.API_KEY_IS_REQUIRED,
					"Api Key is required to access this Resource");
		}
		if (apiKey != null && apiKey.equals(appProperties.getApiKeyProperty())) {
			if (appKey != null && !appKey.isEmpty() && farmerAppUtils.matchAppKey(appKey)) {
				return new ResponseEntity<>(masterDataService.getFarmerIdProof(), HttpStatus.OK);

			} else {
				throw new InvalidAppKeyException(ErrorConstants.INVALID_APP_KEY, "Invalid APP Key");
			}
		} else {
			throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
		}
	}

	@PostMapping("/notifyFarmer")
	public ResponseEntity<Map<String, Object>> farmerNotification(@RequestHeader(required = false) String appKey,
			@RequestParam("apiKey") String apiKey, @RequestParam("farmerId") String farmerId,
			@RequestParam("type") Integer type) throws IOException {

		if (apiKey == null) {
			throw new InvalidApiKeyException(ErrorConstants.API_KEY_IS_REQUIRED,
					"Api Key is required to access this Resource");
		}

		if (apiKey != null && apiKey.equals(appProperties.getApiKeyProperty())) {

			if (appKey != null && !appKey.isEmpty() && farmerAppUtils.matchAppKey(appKey)) {

				/*Map<String, Object> dataMap = new HashMap<>();
				if (type == 3) {
					dataMap.put("message", "Dr.Krishi is currently accessing your location details for delivery coordination.");
					dataMap.put("type", APIConstants.LOCATION_TRACKING_START_DELIVARY);
					dataMap.put("title", "Dr.Krishi");
					return notificationService.sendNotification(type, deviceToken, dataMap);
				} else if(type == 6){
					dataMap.put("message", "Dr.Krishi is currently accessing your location details for registration");
					dataMap.put("type", APIConstants.LOCATION_TRACKING_START_REGISTRATION);
					dataMap.put("title", "Dr.Krishi");
					return notificationService.sendNotification(type, deviceToken, dataMap);
				} else{

					dataMap.put("message", "Location Tracking Stopped");
					dataMap.put("type", APIConstants.LOCATION_TRACKING_STOP);
					dataMap.put("title", "Dr.Krishi");
					return notificationService.sendNotification(type, deviceToken, dataMap);
				}*/

				return notificationService.preparePushNotification(farmerId,type);

			} else {
				throw new InvalidAppKeyException(ErrorConstants.INVALID_APP_KEY, "Invalid APP Key");
			}

		} else {
			throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
		}
	}

	@GetMapping("/getMarketData")
	public ResponseEntity<?> getMarketData(@RequestHeader(required = false) String appKey, HttpServletRequest request,
			@RequestParam("apiKey") String apiKey, @RequestParam("districtCode") int districtCode,
			@RequestParam("commodityID") int commodityId) {
		if (apiKey == null) {
			throw new InvalidApiKeyException(ErrorConstants.API_KEY_IS_REQUIRED,
					"Api Key is required to access this Resource");
		}
		if (apiKey != null && apiKey.equals(appProperties.getApiKeyProperty())) {
			if (appKey != null && !appKey.isEmpty() && farmerAppUtils.matchAppKey(appKey)) {
				String authToken = jwtTokenUtil.getAuthToken(request);
				if (authToken != null) {
					Map<String, Object> marketData = this.farmerAppService.getMarketData(commodityId, authToken);
					return ResponseEntity.status(HttpStatus.OK).body(marketData);
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

	@GetMapping("/getTermsAndCondition")
	public ResponseEntity<?> getTermsAndCondition(@RequestHeader(required = false) String appKey,
			@RequestParam("apiKey") String apiKey) {

		if (apiKey == null) {
			throw new InvalidApiKeyException(ErrorConstants.API_KEY_IS_REQUIRED,
					"Api Key is required to access this Resource");
		}
		if (apiKey != null && apiKey.equals(appProperties.getApiKeyProperty())) {
			if (appKey != null && !appKey.isEmpty() && farmerAppUtils.matchAppKey(appKey)) {
				Map<String, Object> marketData = this.farmerAppService.getTermsAndCondition();
				return ResponseEntity.status(HttpStatus.OK).body(marketData);

			} else {
				throw new InvalidAppKeyException(ErrorConstants.INVALID_APP_KEY, "Invalid APP Key");
			}
		} else {
			throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
		}
	}
	
	@PostMapping("/saveLatLong")
	public ResponseEntity<Map<String, Object>> saveLatLong(HttpServletRequest request, @RequestHeader("appKey") String appKey,
			@RequestParam("apiKey") String apiKey,@RequestBody FarmerLatLongDTO farmerLatLongDTO) {

		if (apiKey == null) {
			throw new InvalidApiKeyException(ErrorConstants.API_KEY_IS_REQUIRED,
					"Api Key is required to access this Resource");
		}
		if (apiKey != null && apiKey.equals(appProperties.getApiKeyProperty())) {
			if (appKey != null && !appKey.isEmpty() && farmerAppUtils.matchAppKey(appKey)) {

				String authToken = jwtTokenUtil.getAuthToken(request);

				if (authToken != null && !appKey.isEmpty()) {
					return new ResponseEntity<>(farmerAppService.saveLatLong(authToken,farmerLatLongDTO), HttpStatus.OK);
					
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
	
	@GetMapping("/notifyFarmerForDelivery")
	private ResponseEntity<Map<String, Object>> farmerNotificationForDelivery() {
		logger.info("cron run to send notification....");
		return notificationService.farmerNotificationForDelivery();

	}
	
	@GetMapping("/get-video-id")
	public ResponseEntity<Map<String, Object>> getVideoId(HttpServletRequest request,
			@RequestHeader("appKey") String appKey, @RequestParam("apiKey") String apiKey, @RequestParam(value = "videoLang",required = false) String videoLg) {

		Map<String, Object> responseMessage = new HashMap<>();

		if (apiKey == null) {
			throw new InvalidApiKeyException(ErrorConstants.API_KEY_IS_REQUIRED,
					"Api Key is required to access this Resource");
		}
		if (apiKey != null && apiKey.equals(appProperties.getApiKeyProperty())) {
			if (appKey != null && !appKey.isEmpty() && farmerAppUtils.matchAppKey(appKey)) {
				/*if (videoLg != null && !(videoLg.isEmpty()))
				{*/
					if (APIConstants.ENGLISH.equals(videoLg))
					{
						responseMessage.put("ID", APIConstants.EN);
						responseMessage.put("status", true);
						responseMessage.put("message", "Video Id Delivered Successfully..!!");
					} else if (APIConstants.HINDI.equals(videoLg))
					{
						responseMessage.put("ID", APIConstants.HI);
						responseMessage.put("status", true);
						responseMessage.put("message", "Video Id Delivered Successfully..!!");
					} else if (APIConstants.TELUGU.equals(videoLg))
					{
						responseMessage.put("ID", APIConstants.TE);
						responseMessage.put("status", true);
						responseMessage.put("message", "Video Id Delivered Successfully..!!");
					} else
					{
						responseMessage.put("ID", APIConstants.HI);
						responseMessage.put("status", true);
						responseMessage.put("message", "Video Id Delivered Successfully..!!");
					}
				/*} else {
					responseMessage.put("ID", null);
					responseMessage.put("status", false);
					responseMessage.put("message", "Video Language Is Missing.. !!");
				}*/

				return ResponseEntity.status(HttpStatus.OK).body(responseMessage);

			} else {
				throw new InvalidAppKeyException(ErrorConstants.INVALID_APP_KEY, "Invalid APP Key");
			}
		} else {
			throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
		}
	}

}
