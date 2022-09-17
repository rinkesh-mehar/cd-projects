package in.cropdata.farmers.app.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import in.cropdata.farmers.app.constants.APIConstants;
import in.cropdata.farmers.app.drk.model.FarmerDeviceToken;
import in.cropdata.farmers.app.drk.repository.FarmerDeviceTokenRepository;
import in.cropdata.farmers.app.properties.AppProperties;
import in.cropdata.farmers.app.repository.DAO.FarmerAppDAO;

/**
 * @author cropdata-ujwal
 * @package in.farmers.app.service
 * @date 22/02/21
 */
@Service
public class NotificationService
{
	public static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

	@Autowired
	private FarmerDeviceTokenRepository farmerDetailsRepository;
	
	@Autowired
	private FarmerAppDAO farmerAppDAO;

	@Autowired
	AppProperties appProperties;

	public ResponseEntity<Map<String, Object>> sendNotification(Integer type, String token,Object dataObj) throws IOException {
		try {

			Map<String, Object> responseMap = new HashMap<>();
			HttpClient client = HttpClientBuilder.create().build();
			HttpPost post = new HttpPost(appProperties.getNotificationUrl());
			post.setHeader("Content-type", "application/json");
			post.setHeader("Authorization", "key=" + appProperties.getNotificationServerKey());

//			Optional<FarmerDeviceToken> usr = farmerDetailsRepository.findByDeviceToken(token);
			JSONObject message = new JSONObject();
			JSONObject data = new JSONObject();

			message.put("to", token);
			message.put("priority", "high");
			switch (type){
				case  APIConstants.ADVISORY_NOTIFICATION :
					data.put("title", "Dr.Krishi");
					data.put("body", dataObj);
					data.put("type", type);
					break;
				case APIConstants.CASE_REGISTRATION:
					data.put("title", "Dr.Krishi");
					data.put("message", "New case registered for your farm. View details here");
					data.put("type", type);
				break;
				case APIConstants.LOCATION_TRACKING_START_REGISTRATION:
					data.put("title", "Dr.Krishi");
					data.put("message", "Dr.Krishi is currently accessing your location details for registration");
					data.put("type", type);
					break;
				case APIConstants.LOCATION_TRACKING_START_DELIVARY:
					data.put("title", "Dr.Krishi");
					data.put("message", "Dr.Krishi is currently accessing your location details for delivery coordination.");
					data.put("type", type);
					
					@SuppressWarnings("unchecked") 
					Map<String, Object> dataObjMap = (Map<String, Object>) dataObj;
					
					BigDecimal lhLatitude = (BigDecimal) dataObjMap.get("lhLatitude");
					BigDecimal lhLongitude = (BigDecimal) dataObjMap.get("lhLongitude");

					data.put("regionID", (Integer) dataObjMap.get("regionId"));
					data.put("mobileNo", String.valueOf(dataObjMap.get("mobileNo")));
					data.put("deviceToken", String.valueOf(dataObjMap.get("deviceToken")));
					data.put("LHLatitude", lhLatitude.doubleValue());
					data.put("LHLongitude", lhLongitude.doubleValue());
					data.put("ZLTileID", String.valueOf(dataObjMap.get("lhZl20TileId")));
					data.put("time", (Integer) dataObjMap.get("estimatedTravelTime"));
//					data.put("body", dataObj);
					break;
				case APIConstants.LOCATION_TRACKING_STOP:
					data.put("title", "Dr.Krishi");
					data.put("message", "Location Tracking Stopped");
					data.put("type", type);
					break;
					//TODO: for future
				/*case APIConstants.CASE_RECOMMENDATION:
					data.put("title", "Dr.Krishi");
					data.put("message", "Location Tracking Stopped");
					data.put("type", type);
					break;
				case APIConstants.CASE_DETAILS:
					data.put("title", "Dr.Krishi");
					data.put("message", "Location Tracking Stopped");
					data.put("type", type);
					break;*/
				default :
					data.put("title", "Dr.Krishi");
					data.put("message", "New update are available for you. Open App");
					data.put("type", type);
					break;
			}

			if(type != 3) {
			message.put("data", dataObj);
			}else {
			message.put("data", data);
			}
			
			logger.info("message :  " + message);
			
			post.setEntity(new StringEntity(message.toString(), "UTF-8"));
			HttpResponse response = client.execute(post);
			logger.info("notification sent successfully {} ", response);
			System.out.println(response);
			System.out.println(message);

			responseMap.put("message", "Notification sent successfully..");
			responseMap.put("status", true);

			return new ResponseEntity<>(responseMap, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}

	public ResponseEntity<Map<String, Object>> preparePushNotification(String farmerId, int type) throws IOException {

		try {
			Map<String, Object> responseMap = new HashMap<>();
			if (farmerId != null){
				List<String> farmerDeviceTokens = farmerDetailsRepository.findFarmerDeviceTokenByFarmerID(farmerId);
				if (farmerDeviceTokens !=null){

					for (String farmerDeviceToken : farmerDeviceTokens) {
						Map<String, Object> dataMap = new HashMap<>();
						if (type == 3) {
							dataMap.put("message", "Dr.Krishi is currently accessing your location details for delivery coordination.");
							dataMap.put("type", APIConstants.LOCATION_TRACKING_START_DELIVARY);
							dataMap.put("title", "Dr.Krishi");
//						return notificationService.sendNotification(type, deviceToken, dataMap);
						} else if(type == 6){
							dataMap.put("message", "Dr.Krishi is currently accessing your location details for registration");
							dataMap.put("type", APIConstants.LOCATION_TRACKING_START_REGISTRATION);
							dataMap.put("title", "Dr.Krishi");
//						return notificationService.sendNotification(type, deviceToken, dataMap);
						} else{

							dataMap.put("message", "Location Tracking Stopped");
							dataMap.put("type", APIConstants.LOCATION_TRACKING_STOP);
							dataMap.put("title", "Dr.Krishi");
//						return notificationService.sendNotification(type, deviceToken, dataMap);
						}
						this.sendNotification(type, farmerDeviceToken, dataMap);
					}
				}
			}

			responseMap.put("message", "Notification sent successfully..");
			responseMap.put("status", true);

			return new ResponseEntity<>(responseMap, HttpStatus.OK);
		}catch (Exception e){
			e.printStackTrace();
			throw e;
		}

	}
	
	public ResponseEntity<Map<String, Object>> farmerNotificationForDelivery() {

		Map<String, Object> responseMap = new HashMap<>();

		try {

			List<Map<String, Object>> list = farmerAppDAO.getDeliveryScheduledRecords();
			if (list != null && !list.isEmpty() && list.size() > 0) {
				int count = 0;
				List<FarmerDeviceToken> farmerDeviceTokenList = new ArrayList<>();
				for (Map<String, Object> map : list) {
//					Map<String, Object> dataMap = new HashMap<>();
//
//					BigDecimal lhLatitude = (BigDecimal) map.get("lhLatitude");
//					BigDecimal lhLongitude = (BigDecimal) map.get("lhLongitude");
//
//					dataMap.put("regionID", (Integer) map.get("regionId"));
//					dataMap.put("deviceToken", String.valueOf(map.get("deviceToken")));
//					dataMap.put("LHLatitude", lhLatitude.doubleValue());
//					dataMap.put("LHLongitude", lhLongitude.doubleValue());
//					dataMap.put("ZLTileID", String.valueOf(map.get("lhZl20TileId")));
//					dataMap.put("time", (Integer) map.get("estimatedTravelTime"));

					if (map.get("deviceToken") != null) {
						logger.info("Device Token : " + String.valueOf(map.get("deviceToken")));
						Optional<FarmerDeviceToken> optionalDeviceToken = farmerDetailsRepository
								.findByDeviceToken(String.valueOf(map.get("deviceToken")));
						if (optionalDeviceToken.isPresent()) {
							logger.info("***If Device token exist***");
							FarmerDeviceToken farmerDeviceToken = optionalDeviceToken.get();
							logger.info("Status : " + farmerDeviceToken.getStatus());
							if (farmerDeviceToken.getStatus() == null) {
								logger.info("***If Device token null***");
								farmerDeviceToken.setStatus("Notified");
								count++;
								farmerDeviceTokenList.add(farmerDeviceToken);
								this.sendNotification(APIConstants.LOCATION_TRACKING_START_DELIVARY,
										String.valueOf(map.get("deviceToken")), map);
							} else if (farmerDeviceToken.getStatus() != null
									&& "Notified".equals(farmerDeviceToken.getStatus())) {
								logger.info("***If Device toke not null and notified***");
								String minTime = String.valueOf(map.get("minTime"));
//								LocalDateTime shiftStartTime = LocalDateTime.parse(minTime, DateTimeFormatter.ofPattern("HH:mm"));
								LocalTime shiftStartTime = LocalTime.parse(minTime);
								LocalTime now = LocalTime.now();
								String time = DateTimeFormatter.ofPattern("HH:mm").format(now);
								LocalTime currentTime = LocalTime.parse(time);
								logger.info("currentTime : " + currentTime + " shiftStartTime : " + shiftStartTime);
								if (currentTime.isAfter(shiftStartTime)) {
									logger.info("***Current time is after shift time***");
									farmerDetailsRepository.updateRightAsUntracable(String.valueOf(map.get("rightId")));
								} else {
									logger.info("***Current time is before shift time***");
									count++;
									this.sendNotification(APIConstants.LOCATION_TRACKING_START_DELIVARY,
											String.valueOf(map.get("deviceToken")), map);
								}
							}

//						farmerDetailsRepository.updateStatusAsNotified(String.valueOf(map.get("deviceToken")));

						}
					}

				}
				farmerDetailsRepository.saveAll(farmerDeviceTokenList);

				logger.info("cron run successfully.." + count + " records affected");
				responseMap.put("message", "Notification sent successfully..");
				responseMap.put("status", true);
			} else {
				logger.info("No data found to send the notification..");
				responseMap.put("message", "No data found to send the notification..");
				responseMap.put("status", false);
			}

		} catch (Exception e) {
			responseMap.put("message", "Failed to run cron.. " + e.getMessage());
			responseMap.put("status", false);
			logger.error("Failed to run cron.. " + e);
		}
		return new ResponseEntity<>(responseMap, HttpStatus.OK);
	}
	
}
