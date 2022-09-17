/**
 * 
 */
package in.cropdata.farmers.app.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import in.cropdata.farmers.app.constants.ErrorConstants;
import in.cropdata.farmers.app.drk.model.DrkFarmer;
import in.cropdata.farmers.app.drk.model.FarmerDeviceToken;
import in.cropdata.farmers.app.drk.model.TrackingQueue;
import in.cropdata.farmers.app.drk.repository.DrkFarmerRepository;
import in.cropdata.farmers.app.drk.repository.FarmerDeviceTokenRepository;
import in.cropdata.farmers.app.drk.repository.TrackingQueueRepository;
import in.cropdata.farmers.app.utils.JwtTokenUtil;

/**
 * @author Vivek Gajbhiye
 *
 */
@Service
public class TrackingQueueService {

	private static final Logger log = LoggerFactory.getLogger(TrackingQueueService.class);

	@Autowired
	TrackingQueueRepository trackingQueueRepository;

	@Autowired
	DrkFarmerRepository drkFarmerRepository;

	@Autowired
	JwtTokenUtil jwtTokenUtil;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	private StringRedisTemplate redisTemplate;
	
	@Autowired
	FarmerDeviceTokenRepository farmerDeviceTokenRepository;

	public ResponseEntity<?> saveCurrentFarmerLocation(TrackingQueue trackingQueue, HttpServletRequest request)
			throws JsonParseException, JsonMappingException, IOException {
		Map<String, Object> response = new HashMap<>();
		String refisKey = trackingQueue.getDeviceToken() + trackingQueue.getMobileNo();
		String authToken = jwtTokenUtil.getAuthToken(request);
		String cacheRedis = findFarmer(refisKey);
		Calendar cal = Calendar.getInstance(); // creates calendar
		cal.setTime(new Date()); // sets calendar time/date
		cal.add(Calendar.HOUR_OF_DAY, 1); // adds one hour
		Date time = cal.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String matchDateTime = sdf.format(time);
		Date now = new Date();
		String nowDate = sdf.format(now);

		log.info("date time {},now date {}", matchDateTime, nowDate);
		log.info("cacheRedis {}", cacheRedis);
		int compareTo = matchDateTime.compareTo(nowDate);
		log.info("compareTo {}", compareTo);
		try {

			if (cacheRedis != null) {
				JsonNode jsonNode = objectMapper.readTree(cacheRedis);
				try {
					if (jsonNode.get("status").asText().equals("Inactive")) {
						response.put("status", false);
						response.put("message", "Stop Mobile Cron ");
						response.put("errorCode", ErrorConstants.FARMER_STOPED_CRON);
						return new ResponseEntity<>(response, HttpStatus.ALREADY_REPORTED);
					}

				} catch (NullPointerException e) {

				}
				if (jsonNode.get("jwtToken").asText().equals(authToken)) {
					Optional<FarmerDeviceToken> foundByDeviceToken = farmerDeviceTokenRepository.findByDeviceToken(trackingQueue.getDeviceToken());
					if(foundByDeviceToken.get().getStatus()!="Tracking") {
						farmerDeviceTokenRepository.updateStatusAsTracking(trackingQueue.getDeviceToken());
						return saveFarmer(trackingQueue);
					}else {
						return saveFarmer(trackingQueue);
					}
					
				} else {
					Optional<DrkFarmer> foundByAuthToken = drkFarmerRepository.findByAuthToken(authToken);
					if (foundByAuthToken.isPresent()) {
						Map<String, Object> cacheMap = new ConcurrentHashMap<>();
						cacheMap.put("jwtToken", authToken);
						cacheMap.put("interwalTime", "NOT_AVAILABLE");
						cacheMap.put("status", "Active");
						String writeValueAsString = objectMapper.writeValueAsString(cacheMap);
						String cacheRedis2 = cacheRedis(refisKey, writeValueAsString);
						log.info("cacheRedis2 {}", cacheRedis2);
						Optional<FarmerDeviceToken> foundByDeviceToken = farmerDeviceTokenRepository.findByDeviceToken(trackingQueue.getDeviceToken());
						if(foundByDeviceToken.get().getStatus()!="Tracking") {
							farmerDeviceTokenRepository.updateStatusAsTracking(trackingQueue.getDeviceToken());
							return saveFarmer(trackingQueue);
						}else {
							return saveFarmer(trackingQueue);
						}
					} else {
						Map<String, Object> cacheMap = new ConcurrentHashMap<>();
						cacheMap.put("jwtToken", authToken);
						cacheMap.put("interwalTime", matchDateTime);
						cacheMap.put("status", "Inactive");
						String writeValueAsString = objectMapper.writeValueAsString(cacheMap);
						String cacheRedis2 = cacheRedis(refisKey, writeValueAsString);
						log.info("cacheRedis2 {}", cacheRedis2);
						response.put("status", false);
						response.put("message", "Sorry, you are not authorized to use this resources");
						response.put("errorCode", ErrorConstants.UNAUTHORIZED);
						return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
					}
				}
			} else {

				Optional<DrkFarmer> foundByAuthToken = drkFarmerRepository.findByAuthToken(authToken);
				if (foundByAuthToken.isPresent()) {
					Map<String, Object> cacheMap = new ConcurrentHashMap<>();
					cacheMap.put("jwtToken", authToken);
					cacheMap.put("interwalTime", "NOT_AVAILABLE");
					cacheMap.put("status", "Active");
					String writeValueAsString = objectMapper.writeValueAsString(cacheMap);
					String cacheRedis2 = cacheRedis(refisKey, writeValueAsString);
					log.info("cacheRedis2 {}", cacheRedis2);
					Optional<FarmerDeviceToken> foundByDeviceToken = farmerDeviceTokenRepository.findByDeviceToken(trackingQueue.getDeviceToken());
					if(foundByDeviceToken.get().getStatus()!="Tracking") {
						farmerDeviceTokenRepository.updateStatusAsTracking(trackingQueue.getDeviceToken());
						return saveFarmer(trackingQueue);
					}else {
						return saveFarmer(trackingQueue);
					}
				} else {

					Map<String, Object> cacheMap = new ConcurrentHashMap<>();
					cacheMap.put("jwtToken", authToken);
					cacheMap.put("interwalTime", matchDateTime);
					cacheMap.put("status", "Inactive");
					String writeValueAsString = objectMapper.writeValueAsString(cacheMap);
					String cacheRedis2 = cacheRedis(refisKey, writeValueAsString);
					log.info("cacheRedis2 {}", cacheRedis2);
					response.put("status", false);
					response.put("message", "Sorry, you are not authorized to use this resources");
					response.put("errorCode", ErrorConstants.UNAUTHORIZED);
					return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
				}
			}

		} catch (NullPointerException e) {
			response.put("status", false);
			response.put("message", e.getMessage());
			response.put("errorCode", ErrorConstants.FARMER_STOPED_CRON);
			return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			response.put("status", false);
			response.put("message", "something went wrong finding farmer");
			response.put("errorCode", ErrorConstants.FARMER_STOPED_CRON);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	public String cacheRedis(String key, String jwtToken) {

		ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
		Boolean delete = redisTemplate.delete(key);

		opsForValue.set(key, jwtToken);
		log.info("getting from redis key {}", opsForValue.get(key));
		return opsForValue.get(key);

	}

	public String findFarmer(String key) {
		ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
		return opsForValue.get(key);

	}

	public ResponseEntity<?> saveFarmer(TrackingQueue trackingQueue) {
		Map<String, Object> response = new HashMap<>();
		trackingQueue.setStatus("PENDING");
		TrackingQueue save = this.trackingQueueRepository.save(trackingQueue);
		if (save != null) {
			response.put("status", true);
			response.put("message", "farmer current location save");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.put("status", false);
			response.put("message", "something went wrong finding farmer");
			response.put("errorCode", ErrorConstants.FARMER_STOPED_CRON);
			return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
		}
	}

	public boolean deleteKey(String key) {
		return redisTemplate.delete(key);
	}

}
