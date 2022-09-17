package in.cropdata.toolsuite.drk.controller.gt;

import java.util.List;
import java.util.Map;

import in.cropdata.toolsuite.drk.dao.masterdata.ResourceDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.cropdata.toolsuite.drk.DrkApplication;
import in.cropdata.toolsuite.drk.constants.ErrorConstants;
import in.cropdata.toolsuite.drk.exceptions.tileassignment.InvalidApiKeyException;
import in.cropdata.toolsuite.drk.model.gt.GT_ZL14;
import in.cropdata.toolsuite.drk.model.gt.GT_ZL19;
import in.cropdata.toolsuite.drk.model.gt.GT_ZL20;
import in.cropdata.toolsuite.drk.model.gt.GroundTruth;
import in.cropdata.toolsuite.drk.model.gt.GroundTruthZL14;
import in.cropdata.toolsuite.drk.model.gt.GroundTruthZL19;
import in.cropdata.toolsuite.drk.properties.AppProperties;
import in.cropdata.toolsuite.drk.service.gt.GroundTruthService;

@RestController
@RequestMapping("/" + DrkApplication.apiVersion + "/ground-truth")
public class GroundTruthController {

	Logger logger = LoggerFactory.getLogger(GroundTruthController.class);
	ResponseEntity<String> msgString = null;

	@Autowired
	private ResourceDao resourceDao;

	@Autowired
	GroundTruthService groundTruthService;

	@Autowired
	AppProperties appProperties;

//	@PostMapping("/spot")
//	private Map<String, Object> addZL20Info(@RequestParam(required = false) String apiKey,
//			@RequestBody List<GroundTruthZL20> groundTruthZL20List) {
//
//		if (apiKey == null) {
//
//			throw new InvalidApiKeyException(ErrorConstants.REQUIRED_API_KEY,
//					"Api Key is required to access this Resource");
//
//		} else if (apiKey != null && apiKey.equals(appProperties.apiKeyProperty)) {
//
//			return groundTruthService.saveZL20Info(groundTruthZL20List);
//
//		} else {
//			throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
//		}
//
//	}// addZL20Info

	@PostMapping("")
	private Map<String, Object> addGroundTruthData(@RequestParam(required = false) String apiKey,
			@RequestBody List<GroundTruth> groundTruthList) {

		if (apiKey == null) {

			throw new InvalidApiKeyException(ErrorConstants.REQUIRED_API_KEY,
					"Api Key is required to access this Resource");

		} else if (apiKey != null && !resourceDao.isApiKeyExists(apiKey).isEmpty()) {

			return groundTruthService.addGroundTruthData(groundTruthList);

		} else {
			throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
		}

	}// addZL19Info

//	@PostMapping("/village")
//	private Map<String, Object> addZL14Info(@RequestParam(required = false) String apiKey,
//			@RequestBody List<GroundTruthZL14> groundTruthZL14List) {
//
//		if (apiKey == null) {
//
//			throw new InvalidApiKeyException(ErrorConstants.REQUIRED_API_KEY,
//					"Api Key is required to access this Resource");
//
//		} else if (apiKey != null && apiKey.equals(appProperties.apiKeyProperty)) {
//
//			return groundTruthService.saveZL14Info(groundTruthZL14List);
//
//		} else {
//			throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
//		}
//
//	}// saveZL14Info

	@GetMapping("/spot/{panchayatCode}")
	private List<GroundTruth> getPanchayatwiseSpotData(@RequestParam(required = false) String apiKey,
			@PathVariable Integer panchayatCode) {

		if (apiKey == null) {

			throw new InvalidApiKeyException(ErrorConstants.REQUIRED_API_KEY,
					"Api Key is required to access this Resource");

		} else if (apiKey != null && !resourceDao.isApiKeyExists(apiKey).isEmpty()) {

			return groundTruthService.getPanchayatwiseSpotData(panchayatCode);

		} else {
			throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
		}
	}

	@GetMapping("/farm/{panchayatCode}")
	private List<GroundTruthZL19> getPanchayatwiseFarmData(@RequestParam(required = false) String apiKey,
			@PathVariable Integer panchayatCode) {

		if (apiKey == null) {

			throw new InvalidApiKeyException(ErrorConstants.REQUIRED_API_KEY,
					"Api Key is required to access this Resource");

		} else if (apiKey != null && !resourceDao.isApiKeyExists(apiKey).isEmpty()) {

			return groundTruthService.getPanchayatwiseFarmData(panchayatCode);

		} else {
			throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
		}
	}

	@GetMapping("/village/{panchayatCode}")
	private List<GroundTruthZL14> getPanchayatwiseVillageData(@RequestParam(required = false) String apiKey,
			@PathVariable Integer panchayatCode) {

		if (apiKey == null) {

			throw new InvalidApiKeyException(ErrorConstants.REQUIRED_API_KEY,
					"Api Key is required to access this Resource");

		} else if (apiKey != null && !resourceDao.isApiKeyExists(apiKey).isEmpty()) {

			return groundTruthService.getPanchayatwiseVillageData(panchayatCode);

		} else {
			throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
		}
	}

	@GetMapping("/spot")
	private List<GT_ZL20> getCasewiseSpotData(@RequestParam(required = false) String apiKey,
			@RequestParam Integer caseID) {

		if (apiKey == null) {

			throw new InvalidApiKeyException(ErrorConstants.REQUIRED_API_KEY,
					"Api Key is required to access this Resource");

		} else if (apiKey != null && !resourceDao.isApiKeyExists(apiKey).isEmpty()) {

			return groundTruthService.getCasewiseSpotData(caseID);

		} else {
			throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
		}
	}

	@GetMapping("/farm")
	private List<GT_ZL19> getCasewiseFarmData(@RequestParam(required = false) String apiKey,
			@RequestParam Integer caseID) {

		if (apiKey == null) {

			throw new InvalidApiKeyException(ErrorConstants.REQUIRED_API_KEY,
					"Api Key is required to access this Resource");

		} else if (apiKey != null && !resourceDao.isApiKeyExists(apiKey).isEmpty()) {

			return groundTruthService.getCasewiseFarmData(caseID);

		} else {
			throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
		}
	}

	@GetMapping("/village")
	private List<GT_ZL14> getCasewiseVillageData(@RequestParam(required = false) String apiKey,
			@RequestParam Integer caseID) {

		if (apiKey == null) {

			throw new InvalidApiKeyException(ErrorConstants.REQUIRED_API_KEY,
					"Api Key is required to access this Resource");

		} else if (apiKey != null && !resourceDao.isApiKeyExists(apiKey).isEmpty()) {

			return groundTruthService.getCasewiseVillageData(caseID);

		} else {
			throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
		}
	}

}
