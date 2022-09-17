package in.cropdata.toolsuite.drk.controller.cctc;

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
import in.cropdata.toolsuite.drk.model.pr.PrVillageSubRegionWiseScheduleSummary;
import in.cropdata.toolsuite.drk.properties.AppProperties;
import in.cropdata.toolsuite.drk.service.pr.PrGSTMVillageInfoService;

@RestController
@RequestMapping("/" + DrkApplication.apiVersion)
public class CCTCController {

	Logger logger = LoggerFactory.getLogger(CCTCController.class);
	ResponseEntity<String> msgString = null;

	@Autowired
	private PrGSTMVillageInfoService villageInfoService;

	@Autowired
	AppProperties appProperties;

	@Autowired
	private ResourceDao resourceDao;

	@PostMapping("/add-cctc-village-schedules-info")
	private Map<String, Object> addCCTCVillageSchedulesInfo(@RequestParam(required = false) String apiKey,
			@RequestBody List<CCTCSchedules> prCCTCSchedulesInfoList) {

		if (apiKey == null) {
			throw new InvalidApiKeyException(ErrorConstants.REQUIRED_API_KEY,
					"Api Key is required to access this Resource");
		}

		if (apiKey != null && !resourceDao.isApiKeyExists(apiKey).isEmpty()) {

			return villageInfoService.saveVillageCCTCSchedulesInfo(prCCTCSchedulesInfoList);

		} else {
			throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
		}

	}// addCCTCVillageSchedulesInfo

	@PostMapping("/add-commodity-village-schedules-info")
	private Map<String, Object> addCommodityVillageSchedulesInfo(@RequestParam(required = false) String apiKey,
			@RequestBody List<PrVillageCommoditySchedule> prVillageCommodityScheduleList) {

		if (apiKey == null) {
			throw new InvalidApiKeyException(ErrorConstants.REQUIRED_API_KEY,
					"Api Key is required to access this Resource");
		}

		if (apiKey != null && !resourceDao.isApiKeyExists(apiKey).isEmpty()) {

			return villageInfoService.saveCommodityVillageSchedulesInfo(prVillageCommodityScheduleList);

		} else {
			throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
		}

	}// addCommodityVillageSchedulesInfo

	@PostMapping("/add-subregion-village-schedules-info")
	private Map<String, Object> addSubRegionWiseVillageSchedulesSummaryInfo(
			@RequestParam(required = false) String apiKey,
			@RequestBody List<PrVillageSubRegionWiseScheduleSummary> prVillageCommodityScheduleSummary) {

		if (apiKey == null) {
			throw new InvalidApiKeyException(ErrorConstants.REQUIRED_API_KEY,
					"Api Key is required to access this Resource");
		}
		if (apiKey != null && !resourceDao.isApiKeyExists(apiKey).isEmpty()) {

			return villageInfoService.SubRegionWiseVillageSchedulesSummaryInfo(prVillageCommodityScheduleSummary);

		} else {
			throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
		}

	}// addSubRegionWiseVillageSchedulesSummaryInfo

}
