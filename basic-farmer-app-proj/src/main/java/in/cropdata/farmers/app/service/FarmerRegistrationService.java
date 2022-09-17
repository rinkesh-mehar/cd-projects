/**
 * 
 */
package in.cropdata.farmers.app.service;

import java.math.BigInteger;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.persistence.NonUniqueResultException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import in.cropdata.farmers.app.DTO.DrkFarmerDTO;
import in.cropdata.farmers.app.masters.repository.GeoVillageRepository;
import in.cropdata.farmers.app.properties.AppProperties;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import in.cropdata.farmers.app.masters.repository.GeoVillageRepository;
import in.cropdata.farmers.app.properties.AppProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import in.cropdata.farmers.app.DTO.FarmerRegistrationDTO;
import in.cropdata.farmers.app.constants.APIConstants;
import in.cropdata.farmers.app.constants.EntityConstants;
import in.cropdata.farmers.app.constants.ErrorConstants;
import in.cropdata.farmers.app.drk.model.DrkFarmer;
import in.cropdata.farmers.app.drk.model.FarmerAddressBook;
import in.cropdata.farmers.app.drk.repository.DrkFarmerRepository;
import in.cropdata.farmers.app.drk.repository.FarmerAddressBookRepository;

import in.cropdata.farmers.app.model.ResponseMessage;

import in.cropdata.farmers.app.utils.GenerateFarmerAppID;
import org.springframework.web.client.RestTemplate;

/**
 * @author cropdata-Aniket Naik
 *
 */
@Service
public class FarmerRegistrationService {

	private static final Logger logger = LoggerFactory.getLogger(FarmerLoginService.class);


	@Autowired
	private DrkFarmerRepository drkFarmerRepository;

	@Autowired
	private FarmerAddressBookRepository drkFarmerAddressBookRepository;

	@Autowired
	private GenerateFarmerAppID generateFarmerAppId;
	@Autowired

	private RestTemplate restTemplate;

	@Autowired
	private GeoVillageRepository geoVillageRepository;

	@Autowired
	private AppProperties appProperties;

	@Transactional(isolation = Isolation.REPEATABLE_READ)
	public synchronized ResponseMessage registerFarmer(FarmerRegistrationDTO farmerDTO) {

		Integer status =null;
		ResponseMessage response = new ResponseMessage();
		ExecutorService service = Executors.newFixedThreadPool(1);	

		String farmerId = generateFarmerAppId.generateKeysForApp(EntityConstants.FARMER);
		Optional<DrkFarmer> drkFarmer = drkFarmerRepository.findAllByPrimaryMobNumber(farmerDTO.getPrimaryMobNumber());
		try {
			if (drkFarmer.isPresent()) {

				response.setStatus(false);
				response.setMessage("Farmer Already registered");
				response.setErrorCode(ErrorConstants.FARMER_ALREADY_REGISTERED);

			} else {

				FarmerAddressBook farmerAddressBook = new FarmerAddressBook();
//					farmerAddressBook.setAddressLine1(farmerDTO.getAddressLine1());
//					farmerAddressBook.setAddressLine2(farmerDTO.getAddressLine2());
					farmerAddressBook.setDistrictCode(farmerDTO.getDistrictCode());
					farmerAddressBook.setStateCode(farmerDTO.getStateCode());
					farmerAddressBook.setPrimaryMobile(farmerDTO.getPrimaryMobNumber());
					farmerAddressBook.setLatitude(farmerDTO.getLatitude());
					farmerAddressBook.setLongitude(farmerDTO.getLongitude());
					
					  if(farmerDTO.getCityCode() > 0) {
						  farmerAddressBook.setCityCode(farmerDTO.getCityCode());
					  }else {
						  farmerAddressBook.setTehsilCode(farmerDTO.getTehsilCode());
						  farmerAddressBook.setPanchayatCode(farmerDTO.getPanchayatCode());
						  farmerAddressBook.setVillageCode(farmerDTO.getVillageCode());	  
					  }
					
//					farmerAddressBook.setPinCode(farmerDTO.getPincode());
				FarmerAddressBook farmerAddresBook = drkFarmerAddressBookRepository.save(farmerAddressBook);

				service.awaitTermination(500, TimeUnit.MILLISECONDS);

				while (true) {
					Optional<DrkFarmer> farmerID = drkFarmerRepository.findById(farmerId);
					if (farmerID.isPresent()) {
						farmerId = generateFarmerAppId.generateKeysForApp(EntityConstants.FARMER);
					} else {
						break;
					}
				}
			
				DrkFarmer farmer = new DrkFarmer();
				farmer.setId(farmerId);
				farmer.setFarmerName(farmerDTO.getFarmerName());
				farmer.setPrimaryMobNumber(farmerDTO.getPrimaryMobNumber());
				farmer.setAddressId(farmerAddresBook.getId());
				farmer.setLatitude(farmerDTO.getLatitude());
				farmer.setLongitude(farmerDTO.getLongitude());
				farmer.setCropArea(0.0); 
//				farmer.setFarmSize(farmerDTO.getFarmSize());
				farmer.setVillageCode(farmerDTO.getVillageCode() != null ? farmerDTO.getVillageCode() : 0);
				  if(farmerDTO.getVillageCode() != null) {
					  farmer.setRegionID(geoVillageRepository.findRegionIDByVillage(farmerDTO.getVillageCode()));
				  }

				/**
				 * based on village id check farmer are already exist or not in DRK
				 * */
				/*if (farmer.getVillageCode() > 0) {
						HashMap<String, Object> createDrkFarmer = new HashMap<>();
						createDrkFarmer.put("farmerId", farmer.getId());
						createDrkFarmer.put("farmerName", farmer.getFarmerName());
						createDrkFarmer.put("primaryMobNumber", farmer.getPrimaryMobNumber());
						createDrkFarmer.put("villageId", farmerAddresBook.getVillageCode());
						createDrkFarmer.put("cropArea", farmer.getCropArea());
						createDrkFarmer.put("farmSize", farmer.getCropArea());
						createDrkFarmer.put("dueAmount", 0.00);

						HttpHeaders headers = new HttpHeaders();
						headers.setContentType(MediaType.APPLICATION_JSON);
						HttpEntity<Object> request = new HttpEntity<>(createDrkFarmer, headers);
						logger.info("Farmer lead details-------> {}", request);
						logger.info("Farmer lead details2-------> {}", farmer.toString());
						ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
						String json = ow.writeValueAsString(createDrkFarmer);
						DrkFarmerDTO resp = restTemplate.postForObject(appProperties.getDrkrishUrl(), request, DrkFarmerDTO.class);

						ObjectMapper mapper = new ObjectMapper();

						status = resp.getStatus();
						logger.info("response :-----> {}", status);
						if (status == 409) {

							farmer = mapper.convertValue(resp.getData(), DrkFarmer.class);
							logger.info("DRK farmer is -------> {}", farmer.toString());
							farmerId = farmer.getId();
						}
				}*/
				drkFarmerRepository.save(farmer);
				response.setStatus(true);
				response.setMessage(APIConstants.RESPONSE_ADD_SUCCESS + farmerId);
				}
				
//			}

		} catch (DataIntegrityViolationException | NonUniqueResultException e) {

			response.setStatus(false);
			response.setMessage("Farmer already registered !!");
			response.setErrorCode(ErrorConstants.FARMER_ALREADY_REGISTERED);
		} catch (Exception e) {

			logger.error("Exception is occurred-----> {}", e.getMessage());
			response.setStatus(false);
			response.setMessage("Some error occured,Please try again !!");
			response.setErrorCode(e.getMessage());
		}
		service.shutdown();

		return response;
	}

	/**
	 * Find DRK farmer are exist or not in drkrishi_ts schema based on primary mobile number
	 * return farmerId
	 * */
	public ResponseEntity<Object> checkFarmerExist(String farmerPrimaryMobileNumber, Integer regionId){
		Map<String, String> map = new HashMap<>();
		map.put("data", drkFarmerRepository.checkFarmerIdByFarmerPrimaryMobileNumber(farmerPrimaryMobileNumber,regionId));
		map.put("status", "200");
		map.put("success", "true");
		return new ResponseEntity<>(map, HttpStatus.OK);

	}

}
