/**
 * 
 */
package in.cropdata.toolsuite.drk.controller.masterdata;

import java.text.ParseException;

import in.cropdata.toolsuite.drk.dao.masterdata.ResourceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.cropdata.toolsuite.drk.DrkApplication;
import in.cropdata.toolsuite.drk.constants.ErrorConstants;
import in.cropdata.toolsuite.drk.dto.PmpResponse;
import in.cropdata.toolsuite.drk.dto.QualitySpread;
import in.cropdata.toolsuite.drk.exceptions.tileassignment.InvalidApiKeyException;
import in.cropdata.toolsuite.drk.properties.AppProperties;
import in.cropdata.toolsuite.drk.service.masterdata.ResourceService;

/**
 * @author vivek-cropdata
 *
 */
@RestController
@RequestMapping("/" + DrkApplication.apiVersion)
public class PmpController {

	@Autowired
	ResourceService resourceService;

	@Autowired
	private ResourceDao resourceDao;

	@Autowired
	AppProperties appProperties;

//	@GetMapping("/pmp-discovery")
//	public PmpResponse getPmpDiscovery(@RequestParam(value = "apiKey", required = true) String apiKey) {
//
//		if (apiKey != null && !resourceDao.isApiKeyExists(apiKey).isEmpty()) {
//			return this.resourceService.getPmpDiscovery();
//		}else {
//			throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
//		}
//
//	}

	@PostMapping("/quality-spread")
	public ResponseEntity<?> getQualitySpread(@RequestParam(value = "apiKey", required = true) String apiKey,@RequestBody QualitySpread qualitySpread) throws ParseException {
		if (apiKey != null && !resourceDao.isApiKeyExists(apiKey).isEmpty()) {
			return this.resourceService.getQualitySpread(qualitySpread);
		}else {
			throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
		}
		
		
	}
}
