package in.cropdata.toolsuite.drk.controller.fl;

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
import in.cropdata.toolsuite.drk.model.cases.Farmer;
import in.cropdata.toolsuite.drk.properties.AppProperties;
import in.cropdata.toolsuite.drk.services.fl.FarmerService;

@RestController
@RequestMapping("/" + DrkApplication.apiVersion + "/farmer-details")
public class FarmerController {
	
	Logger logger = LoggerFactory.getLogger(FarmController.class);
	ResponseEntity<String> msgString = null;
	
	@Autowired
	private FarmerService farmerService;

	@Autowired
	private ResourceDao resourceDao;

	@Autowired
	AppProperties appProperties;
	
	@PostMapping(path = {"/add","/update"})
	private Map<String, Object> addFarmer(@RequestBody List<Farmer> farmerList, @RequestParam(required = false) String apiKey) {

		if (apiKey == null) {

			throw new InvalidApiKeyException(ErrorConstants.REQUIRED_API_KEY,
					"Api Key is required to access this Resource");

		} else if (apiKey != null && !resourceDao.isApiKeyExists(apiKey).isEmpty()) {

			return farmerService.saveFarmer(farmerList);

		} else {
			throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
		}

	}//addFarmer

}
