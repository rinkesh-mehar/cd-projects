package in.cropdata.toolsuite.drk.controller.agdata;

import in.cropdata.toolsuite.drk.dao.masterdata.ResourceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.cropdata.toolsuite.drk.DrkApplication;
import in.cropdata.toolsuite.drk.constants.ErrorConstants;
import in.cropdata.toolsuite.drk.exceptions.tileassignment.InvalidApiKeyException;
import in.cropdata.toolsuite.drk.properties.AppProperties;
import in.cropdata.toolsuite.drk.service.agdata.AgDataService;

@RestController
@RequestMapping("/" + DrkApplication.apiVersion)
public class AgDataController {
	
	@Autowired
	private AgDataService agDataService;

	@Autowired
	AppProperties appProperties;

	@Autowired
	private ResourceDao resourceDao;

	@GetMapping("/agdata/hs-code")
	public ResponseEntity<String> getData(@RequestParam(required = false) String apiKey,
			@RequestParam(required = false, defaultValue = "0") long unixTimestamp) {

		if (apiKey == null) {
			throw new InvalidApiKeyException(ErrorConstants.REQUIRED_API_KEY,
					"Api Key is required to access this Resource");
		}


		if (apiKey != null && !resourceDao.isApiKeyExists(apiKey).isEmpty()) {
			String response = agDataService.getData(unixTimestamp);
			return new ResponseEntity<String>(response, HttpStatus.OK);
		} else {
			throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
		}

	}// getData
	
}//AgDataController
