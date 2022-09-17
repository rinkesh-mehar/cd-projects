package in.cropdata.toolsuite.drk.controller.mbep;

import java.util.List;
import java.util.Map;

import in.cropdata.toolsuite.drk.dao.masterdata.ResourceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import in.cropdata.toolsuite.drk.DrkApplication;
import in.cropdata.toolsuite.drk.constants.ErrorConstants;
import in.cropdata.toolsuite.drk.exceptions.NoRecordFoundException;
import in.cropdata.toolsuite.drk.exceptions.tileassignment.InvalidApiKeyException;
import in.cropdata.toolsuite.drk.model.mbep.PmpResponse;
import in.cropdata.toolsuite.drk.properties.AppProperties;

@RestController
@RequestMapping("/" + DrkApplication.apiVersion)
public class MbepController {

	@Autowired
	private ResourceDao resourceDao;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private AppProperties appProperties;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private ObjectMapper objectMapper;

//	@GetMapping("/mbep")
//	public ResponseEntity<Object> getMbepValue(@RequestParam(required = false) String apiKey,
//			@RequestParam(value = "districtCode") Integer districtCode,
//			@RequestParam(value = "commodityID") Integer commodityID,
//			@RequestParam(value = "varietyID") Integer varietyID) {
//		if (apiKey == null) {
//			throw new InvalidApiKeyException(ErrorConstants.REQUIRED_API_KEY,
//					"Api Key is required to access this Resource");
//		}
//
//		if (apiKey != null && apiKey.equals(apiKeyProperty)) {
//			if (districtCode == null) {
//				return new ResponseEntity<Object>("districtCode not found", HttpStatus.NOT_FOUND);
//			} else if (commodityID == null) {
//				return new ResponseEntity<Object>("commodityID not found", HttpStatus.NOT_FOUND);
//			} else if (varietyID == null) {
//				return new ResponseEntity<Object>("varietyID not found", HttpStatus.NOT_FOUND);
//			}
//			String url = "http://" + mbepService + "/mbep?districtCode=" + districtCode + "&commodityID=" + commodityID
//					+ "&varietyID=" + varietyID;
//
////			String url = "http://192.168.0.62:8080/mbep?districtCode=" + districtCode + "&commodityID=" + commodityID
////					+ "&varietyID=" + varietyID;
//			try {
//				MbepResponse mbepResponse = restTemplate.getForObject(url, MbepResponse.class);
//				return ResponseEntity.ok().body(mbepResponse);
//			} catch (HttpStatusCodeException e) {
//				ResponseEntity<Object> exceptionBody = ResponseEntity.status(e.getRawStatusCode())
//						.headers(e.getResponseHeaders()).body(e.getResponseBodyAsString());
//				return exceptionBody;
//			}
//
//		} else {
//			throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
//		}
//	}// getMbepValue

//	@GetMapping("/pmp")
//	public ResponseEntity<Object> getPMPValue(@RequestParam(required = false) String apiKey,
//			@RequestParam(value = "districtCode") Integer districtCode,
//			@RequestParam(value = "commodityID") Integer commodityID,
//			@RequestParam(value = "varietyID") Integer varietyID) {
//		if (apiKey == null) {
//			throw new InvalidApiKeyException(ErrorConstants.REQUIRED_API_KEY,
//					"Api Key is required to access this Resource");
//		}
//
//		if (apiKey != null && apiKey.equals(apiKeyProperty)) {
//			if (districtCode == null) {
//				return new ResponseEntity<Object>("districtCode not found", HttpStatus.NOT_FOUND);
//			} else if (commodityID == null) {
//				return new ResponseEntity<Object>("commodityID not found", HttpStatus.NOT_FOUND);
//			} else if (varietyID == null) {
//				return new ResponseEntity<Object>("varietyID not found", HttpStatus.NOT_FOUND);
//			}
//			String url = "http://" + mbepService + "/pmp?districtCode=" + districtCode + "&commodityID=" + commodityID
//					+ "&varietyID=" + varietyID;
////			String url = "http://192.168.0.62:8080/mbep?districtCode=" + districtCode + "&commodityID=" + commodityID
////					+ "&varietyID=" + varietyID;
//			try {
//				PmpResponse mbepResponse = restTemplate.getForObject(url, PmpResponse.class);
//				return ResponseEntity.ok().body(mbepResponse);
//			} catch (HttpStatusCodeException e) {
//				ResponseEntity<Object> exceptionBody = ResponseEntity.status(e.getRawStatusCode())
//						.headers(e.getResponseHeaders()).body(e.getResponseBodyAsString());
//				return exceptionBody;
//			}
//
//		} else {
//			throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
//		}
//	}// getMbepValue

//	@GetMapping("/pmp")
//	public ResponseEntity<?> fetchAllPmp(@RequestParam(required = false, defaultValue = "0") long unixTimestamp,
//			@RequestParam(required = false) String apiKey) {
//		if (apiKey == null) {
//			throw new InvalidApiKeyException(ErrorConstants.REQUIRED_API_KEY,
//					"Api Key is required to access this Resource");
//		}
//		if (apiKey != null && apiKey.equals(appProperties.apiKeyProperty)) {
////			String url = "http://" + appProperties.mbepService + "/pmp/get-all?unixTimestamp=" + unixTimestamp;
//			String url = appProperties.mbepUrl + "pmp/get-all?unixTimestamp=" + unixTimestamp;
//
////			String url = "http://192.168.0.62:8080/pmp/get-all";
//			ResponseEntity<PmpResponse[]> _pmpList = null;
//			try {
//				_pmpList = restTemplate.getForEntity(url, PmpResponse[].class);
//				if (_pmpList.getBody() != null && _pmpList.getBody().length > 0) {
//					return ResponseEntity.status(HttpStatus.OK).body(_pmpList.getBody());
//				} else {
//					throw new NoRecordFoundException("Data not available");
//				}
//			} catch (HttpStatusCodeException e) {
//				Gson gson = new Gson();
//				Map responseError = gson.fromJson(e.getResponseBodyAsString(), Map.class);
//				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(responseError);
//			}
//
//		} else {
//			throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
//		}
//	}
//
//	@GetMapping("/mbep")
//	public ResponseEntity<?> fetchAllMbep(@RequestParam(required = false, defaultValue = "0") long unixTimestamp,
//			@RequestParam(required = false) String apiKey, @RequestParam(defaultValue = "1") int page) {
//		if (apiKey == null) {
//			throw new InvalidApiKeyException(ErrorConstants.REQUIRED_API_KEY,
//					"Api Key is required to access this Resource");
//		}
//		if (apiKey != null && apiKey.equals(appProperties.apiKeyProperty)) {
//			String resultJson = "{}";
//			try {
//
//				page = page - 1;
//				Integer start = page * 50000;
//
//				String query = "Select ID,StateCode,RegionID,SubRegionID,CommodityID,VarietyID,GradeID,Mbep,Status "
//						+ "From cdt_master_data.pricing_master where Status IN ('Active','Deleted') LIMIT " + start
//						+ ", 50000";
//				if (unixTimestamp > 0) {
//
//					if (query.contains("where")) {
//						query += " AND UNIX_TIMESTAMP(CreatedAt) > " + unixTimestamp
//								+ " or UNIX_TIMESTAMP(UpdatedAt) > " + unixTimestamp;
//					}
//				}
//
//				List<Map<String, Object>> list = jdbcTemplate.queryForList(query);
//				System.out.println("Query: " + query);
//				resultJson = objectMapper.writeValueAsString(list);
//				return ResponseEntity.status(HttpStatus.OK).body(resultJson);
//
//			} catch (Exception e) {
//				throw new RuntimeException(e.getMessage());
//			}
//		} else {
//			throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
//		}
//	}

	@GetMapping("/dq")
	public ResponseEntity<?> getDQdata(@RequestParam(required = false) String apiKey, @RequestParam int region) {
		if (apiKey != null && !resourceDao.isApiKeyExists(apiKey).isEmpty()) {
			String resultJson = "{}";
			try {

				List<Map<String, Object>> caseList = jdbcTemplate
						.queryForList("SELECT CaseID as caseId FROM gstm_transitory.case_details");

				for (Map<String, Object> m : caseList) {

					List<Integer> symptomsList = jdbcTemplate.query(
							String.format("SELECT ID FROM cdt_master_data.flipbook_symptoms order by rand() limit 5"),
							(rs, rowNum) -> (rs.getInt("ID")));

					m.put("symptoms", symptomsList);

				}

				resultJson = objectMapper.writeValueAsString(caseList);
				return ResponseEntity.status(HttpStatus.OK).body(resultJson);

			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}

		} else {
			throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
		}
	}

//	@GetMapping("/mbep")
//	public ResponseEntity<?> fetchAllMbep(@RequestParam(required = false, defaultValue = "0") long unixTimestamp,
//			@RequestParam(required = false) String apiKey) {
//		if (apiKey == null) {
//			throw new InvalidApiKeyException(ErrorConstants.REQUIRED_API_KEY,
//					"Api Key is required to access this Resource");
//		}
//		if (apiKey != null && apiKey.equals(appProperties.apiKeyProperty)) {
////			String url = "http://" + appProperties.mbepService + "/mbep/get-all?unixTimestamp=" + unixTimestamp;
//		    String url = appProperties.mbepUrl + "mbep/get-all?unixTimestamp=" + unixTimestamp;
////			String url = "http://192.168.0.62:8080/mbep/get-all";
//			ResponseEntity<MbepResponse[]> _mbepList = null;
//			try {
//				_mbepList = restTemplate.getForEntity(url, MbepResponse[].class);
//
//				if (_mbepList.getBody() != null && _mbepList.getBody().length > 0) {
//					return ResponseEntity.status(HttpStatus.OK).body(_mbepList.getBody());
//				} else {
//					throw new NoRecordFoundException("Data not available");
//				}
//			} catch (HttpStatusCodeException e) {
//				Gson gson = new Gson();
//				Map responseError = gson.fromJson(e.getResponseBodyAsString(), Map.class);
//				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(responseError);
//			}
//
//		} else {
//			throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
//		}
//	}

}
