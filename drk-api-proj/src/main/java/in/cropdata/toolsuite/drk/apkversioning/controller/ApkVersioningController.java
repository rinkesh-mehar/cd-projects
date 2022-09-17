package in.cropdata.toolsuite.drk.apkversioning.controller;

import in.cropdata.toolsuite.drk.dao.masterdata.ResourceDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.cropdata.toolsuite.drk.apkversioning.model.ApkVersionControl;
import in.cropdata.toolsuite.drk.apkversioning.service.ApkVersioningService;
import in.cropdata.toolsuite.drk.constants.ErrorConstants;
import in.cropdata.toolsuite.drk.exceptions.VersionControlException;
import in.cropdata.toolsuite.drk.exceptions.tileassignment.InvalidApiKeyException;
import in.cropdata.toolsuite.drk.properties.AppProperties;

//@CrossOrigin(origins = "*")
@RestController
public class ApkVersioningController {

	private static final Logger log = LoggerFactory.getLogger(ApkVersioningController.class);

	@Autowired
	private ApkVersioningService versioningService;

	@Autowired
	private ResourceDao resourceDao;

	@Autowired
	AppProperties appProperties;

	/** Version Checking for APKs */
//	@GetMapping("/check-version")
	public ResponseEntity<ApkVersionControl> versionCheck(
			@RequestParam(value = "apiKey", required = false) String apiKey, @RequestHeader String appKey) {

		if (apiKey != null && !resourceDao.isApiKeyExists(apiKey).isEmpty()) {
			ApkVersionControl versionCheck = versioningService.versionCheck(appKey);
			log.info("DRK API KEY {} ", apiKey);
			return ResponseEntity.status(HttpStatus.OK).body(versionCheck);
		} else if (apiKey != null && apiKey.equals(appProperties.apiKeyAgriota)) {
			ApkVersionControl versionCheck = versioningService.versionCheck(appKey);
			log.info("AGRIOTA API KEY {} ", apiKey);
			return ResponseEntity.status(HttpStatus.OK).body(versionCheck);
		} else if (!resourceDao.isApiKeyExists(apiKey).isEmpty()) {
			ApkVersionControl versionCheck = versioningService.versionCheck(appKey);
			log.info("CROPDATA API KEY {} ", apiKey);
			return ResponseEntity.status(HttpStatus.OK).body(versionCheck);
		} else {
			throw new VersionControlException("Api Key is required to access this resource");
		}
	}
}
