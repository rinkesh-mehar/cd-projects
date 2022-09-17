package in.cropdata.toolsuite.drk.controller.ndvi;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import in.cropdata.toolsuite.drk.dao.masterdata.ResourceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.cropdata.toolsuite.drk.DrkApplication;
import in.cropdata.toolsuite.drk.constants.ErrorConstants;
import in.cropdata.toolsuite.drk.dto.ndvi.CaseNdvi;
import in.cropdata.toolsuite.drk.dto.ndvi.NdviDTO;
import in.cropdata.toolsuite.drk.exceptions.InvalidDataException;
import in.cropdata.toolsuite.drk.exceptions.tileassignment.InvalidApiKeyException;
import in.cropdata.toolsuite.drk.model.ndvi.NdviVO;
import in.cropdata.toolsuite.drk.properties.AppProperties;
import in.cropdata.toolsuite.drk.service.ndvi.CaseNdviService;
import in.cropdata.toolsuite.drk.service.ndvi.NdviService;

@RestController
@RequestMapping("/" + DrkApplication.apiVersion)
public class NdviController {

	@Autowired
	private NdviService ndviService;

	@Autowired
	private ResourceDao resourceDao;

	@Autowired
	private AppProperties appProperties;

	@Autowired
	CaseNdviService caseNdviService;

	@GetMapping("/test")
	public String dataTest() {
		return "test";
	}

	@GetMapping("/benchmark-cases")
	public List<NdviDTO> getBenchmarkNdviCases(@RequestParam(required = false) String apiKey,
			@RequestBody NdviVO ndvi) {

		if (apiKey == null) {
			throw new InvalidApiKeyException(ErrorConstants.REQUIRED_API_KEY,
					"Api Key is required to access this Resource");
		}

		if (apiKey != null && !resourceDao.isApiKeyExists(apiKey).isEmpty()) {

			if (ndvi.getYear() == null || ndvi.getYear().toString().length() != 4) {
				throw new InvalidDataException("Invalid year, please provide year in 4 digits.");
			}

			if (ndvi.getWeek() == null
					|| (ndvi.getWeek().toString().length() <= 0 && ndvi.getWeek().toString().length() >= 3)
					|| ndvi.getWeek() >= 53 || ndvi.getWeek() <= 0) {
				throw new InvalidDataException(
						"Invalid week, please provide week in 2 digits and must be in between 1-52.");
			}

			return ndviService.getBenchmarkNdviData(ndvi);

		} else {
			throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
		}

	}// getBenchmarkNdviData

	@PostMapping("/benchmark-ndvi")
	public List<NdviDTO> getBenchmarkNdviData(@RequestParam(required = false) String apiKey, @RequestBody NdviVO ndvi) {

		if (apiKey == null) {
			throw new InvalidApiKeyException(ErrorConstants.REQUIRED_API_KEY,
					"Api Key is required to access this Resource");
		}

		if (apiKey != null && !resourceDao.isApiKeyExists(apiKey).isEmpty()) {

			if (ndvi.getYear() == null || ndvi.getYear().toString().length() != 4) {
				throw new InvalidDataException("Invalid week, please provide year in 4 digits.");
			}

			if (ndvi.getWeek() == null
					|| (ndvi.getWeek().toString().length() <= 0 && ndvi.getWeek().toString().length() >= 3)
					|| ndvi.getWeek() >= 53 || ndvi.getWeek() <= 0) {
				throw new InvalidDataException(
						"Invalid week, please provide week in 2 digits and must be in between 1-52.");
			}

			return ndviService.getBenchmarkNdviData(ndvi);

		} else {
			throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
		}

	}// getBenchmarkNdviData

	@GetMapping("/simple-ndvi")
	public List<Map<String, Object>> getSimpleNdviData(@RequestParam(required = false) String apiKey,
			@RequestParam BigInteger caseId, @RequestParam int year, @RequestParam int week) {

		if (apiKey == null) {
			throw new InvalidApiKeyException(ErrorConstants.REQUIRED_API_KEY,
					"Api Key is required to access this Resource");
		}

		if (apiKey != null && !resourceDao.isApiKeyExists(apiKey).isEmpty()) {

			if (Integer.toString(year).length() != 4) {
				throw new InvalidDataException("Invalid year, please provide year in 4 digits.");
			}

			if (week <= 0 || week >= 53) {
				throw new InvalidDataException(
						"Invalid week, please provide week in 2 digits and must be in between 1-52.");
			}
			return ndviService.getSimpleNdviData(caseId, year, week);
		} else {
			throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
		}

	}// getSimpleNdviData

	@PostMapping("/get-benchmark-ndvi-image")
	public ResponseEntity<?> getNdvi(@RequestBody CaseNdvi caseNdvi) {
		List<CaseNdvi> bi = this.caseNdviService.getBI(caseNdvi);
		return ResponseEntity.status(HttpStatus.OK).body(bi);

	}

	@PostMapping("/get-ndvi-images")
	public List<CaseNdvi> getAllCases(@RequestBody CaseNdvi caseNdvi) {
		return this.caseNdviService.getndviList(caseNdvi);
	}

}