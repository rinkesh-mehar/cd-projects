package in.cropdata.toolsuite.drk.controller.tileassignment;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import in.cropdata.toolsuite.drk.dao.masterdata.ResourceDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.cropdata.toolsuite.drk.DrkApplication;
import in.cropdata.toolsuite.drk.constants.ErrorConstants;
import in.cropdata.toolsuite.drk.dto.tileassignment.BenchmarkCasewiseSpotDto;
import in.cropdata.toolsuite.drk.exceptions.tileassignment.InvalidApiKeyException;
import in.cropdata.toolsuite.drk.model.tileassignment.BenchmarkImage;
import in.cropdata.toolsuite.drk.model.tileassignment.FileUploadWrapper;
import in.cropdata.toolsuite.drk.properties.AppProperties;
import in.cropdata.toolsuite.drk.service.tileassignment.BenchmarkService;

@RestController
@RequestMapping("/" + DrkApplication.apiVersion)
public class BenchmarkController {
	Logger logger = LoggerFactory.getLogger(GeoVillageController.class);

	@Autowired
	private BenchmarkService benchmarkService;

	@Autowired
	AppProperties appProperties;
	
	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	private ResourceDao resourceDao;

	@PostMapping("/add-benchmark-image")
	public ResponseEntity<Map<String, Object>> addBenchmarkImage(@RequestParam(required = false) String apiKey,
			@ModelAttribute FileUploadWrapper fileWrapper) {// @RequestPart BenchmarkImage metadata, @RequestPart
															// MultipartFile image
		if (apiKey == null) {
			throw new InvalidApiKeyException(ErrorConstants.REQUIRED_API_KEY,
					"Api Key is required to access this Resource");
		}

		if (apiKey != null && resourceDao.isApiKeyExistsCDT(apiKey)) {
			try {
//		System.err.println("MetaD: "+fileWrapper.getMetadata());
//		System.err.println("File:"+fileWrapper.getImage());

				BenchmarkImage metadata = objectMapper.readValue(fileWrapper.getMetadata(), BenchmarkImage.class);
				return benchmarkService.addBenchmarkImage(metadata, fileWrapper.getImage());
			} catch (IOException e) {
				logger.error("Metadata converion failed.", e);
				throw new RuntimeException("Metadata converion failed :"+e.getMessage());
			}
		} else {
			throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
		}
	}// getAllVillagesBySubRegionId

	@PostMapping("/add-benchmark-spots")
	public ResponseEntity<Map<String, Object>> addBenchmarkSpots(@RequestParam(required = false) String apiKey,
			@RequestBody List<BenchmarkCasewiseSpotDto> data) {

		if (apiKey == null) {
			throw new InvalidApiKeyException(ErrorConstants.REQUIRED_API_KEY,
					"Api Key is required to access this Resource");
		}

		if (apiKey != null && !resourceDao.isApiKeyExists(apiKey).isEmpty()) {

			try {

				return benchmarkService.addBenchmarkSpotData(data);

			} catch (Exception e) {

				throw new RuntimeException(e.getMessage());
			}
		} else {
			throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
		}
	}// addBenchmarkSpots

	@PostMapping("/remove-benchmark-spot")
	public ResponseEntity<Map<String, Object>> deleteBenchmarkSpots(@RequestParam(required = false) String apiKey) {

		if (apiKey == null) {
			throw new InvalidApiKeyException(ErrorConstants.REQUIRED_API_KEY,
					"Api Key is required to access this Resource");
		}

		if (apiKey != null && !resourceDao.isApiKeyExists(apiKey).isEmpty()) {

			try {

				return benchmarkService.deleteBenchmarkSpotData();

			} catch (Exception e) {

				throw new RuntimeException(e.getMessage());
			}
		} else {
			throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
		}
	}// deleteBenchmarkSpots

	@PostMapping("/benchmark-spot-ndvi")
	public ResponseEntity<Map<String, Object>> addBenchmarkSpotNDVI(@RequestParam(required = false) String apiKey,
			@RequestParam int subRegionId, @RequestParam int commodityId, @RequestParam int phenophaseId) {

		if (apiKey == null) {
			throw new InvalidApiKeyException(ErrorConstants.REQUIRED_API_KEY,
					"Api Key is required to access this Resource");
		}

		if (apiKey != null && !resourceDao.isApiKeyExists(apiKey).isEmpty()) {

			try {

				return benchmarkService.addBenchmarkSpotNDVI(subRegionId, commodityId, phenophaseId);

			} catch (Exception e) {

				throw new RuntimeException(e.getMessage());
			}
		} else {
			throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
		}
	}// addBenchmarkSpotNDVI

}
