package in.cropdata.toolsuite.drk.controller.pr;

import java.util.List;
import java.util.Map;

import in.cropdata.toolsuite.drk.dao.masterdata.ResourceDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.cropdata.toolsuite.drk.DrkApplication;
import in.cropdata.toolsuite.drk.constants.ErrorConstants;
import in.cropdata.toolsuite.drk.exceptions.tileassignment.InvalidApiKeyException;
import in.cropdata.toolsuite.drk.model.pr.CCTCSchedules;
import in.cropdata.toolsuite.drk.model.pr.PrVillageCommoditySchedule;
import in.cropdata.toolsuite.drk.model.pr.PrVillageInfo;
import in.cropdata.toolsuite.drk.model.pr.PrVillageInformationSummary;
import in.cropdata.toolsuite.drk.model.pr.PrVillageSubRegionWiseScheduleSummary;
import in.cropdata.toolsuite.drk.properties.AppProperties;
import in.cropdata.toolsuite.drk.service.pr.PrGSTMVillageInfoService;

@RestController
@RequestMapping("/" + DrkApplication.apiVersion)
public class PrVillageInfoController {

	Logger logger = LoggerFactory.getLogger(PrVillageInfoController.class);
	ResponseEntity<String> msgString = null;

	@Autowired
	private PrGSTMVillageInfoService villageInfoService;

	@Autowired
	private ResourceDao resourceDao;

	@Autowired
	AppProperties appProperties;

	@PostMapping("/add-village-info")
	private Map<String, Object> addVillageInfo(@RequestParam(required = false) String apiKey,
			@RequestBody List<PrVillageInfo> prVillageInfoList) {

		if (apiKey == null) {
			throw new InvalidApiKeyException(ErrorConstants.REQUIRED_API_KEY,
					"Api Key is required to access this Resource");
		}

		if (apiKey != null && !resourceDao.isApiKeyExists(apiKey).isEmpty()) {

			return villageInfoService.saveVilageInfo(prVillageInfoList);

		} else {
			throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
		}

	}// addVillageInfo

	@PostMapping("/add-village-information-summary")
	private Map<String, Object> addVillageInformationSummaryInfo(@RequestParam(required = false) String apiKey,
			@RequestBody List<PrVillageInformationSummary> prVillageInformationSummary) {

		if (apiKey == null) {
			throw new InvalidApiKeyException(ErrorConstants.REQUIRED_API_KEY,
					"Api Key is required to access this Resource");
		}
		if (apiKey != null && !resourceDao.isApiKeyExists(apiKey).isEmpty()) {

			return villageInfoService.addVillageInformationSummary(prVillageInformationSummary);

		} else {
			throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
		}

	}// addVillageInformationSummary

}
