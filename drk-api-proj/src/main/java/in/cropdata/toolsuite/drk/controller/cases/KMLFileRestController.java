/**
 * 
 */
package in.cropdata.toolsuite.drk.controller.cases;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import in.cropdata.toolsuite.drk.dao.masterdata.ResourceDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

import in.cropdata.toolsuite.drk.DrkApplication;
import in.cropdata.toolsuite.drk.constants.ErrorConstants;
import in.cropdata.toolsuite.drk.exceptions.InvalidDataException;
import in.cropdata.toolsuite.drk.exceptions.tileassignment.InvalidApiKeyException;
import in.cropdata.toolsuite.drk.model.cases.CaseDetails;
import in.cropdata.toolsuite.drk.model.cases.GenericResponse;
import in.cropdata.toolsuite.drk.model.cases.KMLData;
import in.cropdata.toolsuite.drk.properties.AppProperties;
import in.cropdata.toolsuite.drk.properties.GstmProperties;
import in.cropdata.toolsuite.drk.service.cases.KMLFileService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author Vivek Gajbhiye - Cropdata
 *
 *         23-Nov-2019
 */
@RestController
@RequestMapping("/" + DrkApplication.apiVersion + "/case-details")
public class KMLFileRestController {

	Logger log = LoggerFactory.getLogger(KMLFileRestController.class);

	@Autowired
	private EurekaClient discoveryClient;

	@Autowired
	private ResourceDao resourceDao;

	@Autowired
	KMLFileService kmlFileService;

	@Autowired
	AppProperties appProperties;

	@Autowired
	GenericResponse genericResponse;

	@Autowired
	GstmProperties gstmProperties;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("/add")
	@ApiOperation(value = "Store KML file and data", response = String.class)
	@ResponseBody
	@ApiResponses(value = { @ApiResponse(code = 200, message = "KML file save successfully"),
			@ApiResponse(code = 401, message = "You are not Authorize to save user data"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	public ResponseEntity<Map> exctractZipFile(@RequestParam(value = "apiKey", required = false) String apiKey,
			@ModelAttribute KMLData data) throws IOException {
		log.info("apikey" + apiKey);
		if (apiKey == null) {
			throw new InvalidApiKeyException(ErrorConstants.REQUIRED_API_KEY,
					"Api Key is required to access this Resource");
		}
		if (apiKey != null && !resourceDao.isApiKeyExists(apiKey).isEmpty()) {
			if (data.getZipFile() == null || data.getZipFile().isEmpty()) {
				throw new InvalidDataException("Zip file is required");
			}
			List<CaseDetails> metadata = null;
			try {
				ObjectMapper _om = new ObjectMapper();
				metadata = _om.readValue(data.getData(), new TypeReference<List<CaseDetails>>() {
				});
			} catch (Exception e) {
				List<String> errorList = new ArrayList<String>();
				String errorMsg = "input for the required field data is not valid, expected list of Case objects.";
				errorList.add(errorMsg);
				throw new InvalidDataException(errorList.toString());
			}
			Map _res = this.kmlFileService._exctractCaseDetails(data.getZipFile(), metadata);
			return new ResponseEntity<Map>(_res, HttpStatus.OK);
		} else {
			throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
		}
	}

	@GetMapping("/spot-guidance-kml/{caseID}")
	public void spotGuidanceKml(HttpServletResponse httpServletResponse,
			@RequestParam(required = false) String apiKey, @PathVariable int caseID) {
		if (apiKey == null) {
			throw new InvalidApiKeyException(ErrorConstants.REQUIRED_API_KEY,
					"Api Key is required to access this Resource");
		}
		if (apiKey != null && !resourceDao.isApiKeyExists(apiKey).isEmpty()) {
			String _kmlURL = serviceUrl(appProperties.fileManagerServiceID) + "not-found.png";
			log.info("_kmlURL not found =>>>>>>>>>>>>>>>>> " + _kmlURL);
			Optional<CaseDetails> caseid = this.kmlFileService.getSpotGauidenceByCaseId(caseID);
			if (caseid.isPresent()) {
				_kmlURL = serviceUrl(appProperties.fileManagerServiceID) + "get-file?id="
						+ caseid.get().getSpotGuidenceKmlID();// "http://"
				log.info("found kmlURL =>>>>>>>>>>>>>>>>>>>>>>>> " + _kmlURL);
				httpServletResponse.setHeader("Location", _kmlURL);
				httpServletResponse.setStatus(302);
			} else {
				httpServletResponse.setHeader("Location", _kmlURL);
				httpServletResponse.setStatus(404);
			}
		} else {
			throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
		}
	}

	public String serviceUrl(String serviceID) {
		InstanceInfo instance = discoveryClient.getNextServerFromEureka(serviceID, false);
		return instance.getHomePageUrl();
	}

	@PutMapping("/update")
	public ResponseEntity<Map> updateCaseDetails(@RequestParam(value = "apiKey", required = true) String apiKey,
			@RequestBody List<CaseDetails> data) throws IOException {
		if (apiKey == null) {
			throw new InvalidApiKeyException(ErrorConstants.REQUIRED_API_KEY,
					"Api Key is required to access this Resource");
		}
		
		if (apiKey != null && !resourceDao.isApiKeyExists(apiKey).isEmpty()) {
			Map _caList = this.kmlFileService.updateCaseDetails(data);
			return new ResponseEntity<Map>(_caList, HttpStatus.OK);
		} else {
			throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
		}
	}

	@PutMapping("/expire-cases")
	public ResponseEntity<Map> updateStatusAndReason(@RequestParam(value = "apiKey", required = true) String apiKey,
			@RequestBody List<CaseDetails> data) throws IOException {
		if (apiKey == null) {
			throw new InvalidApiKeyException(ErrorConstants.REQUIRED_API_KEY,
					"Api Key is required to access this Resource");
		}
		if (apiKey != null && !resourceDao.isApiKeyExists(apiKey).isEmpty()) {
			Map _caList = this.kmlFileService.updateStatusAndReason(data);
			return new ResponseEntity<Map>(_caList, HttpStatus.OK);
		} else {
			throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
		}
	}
	
}
